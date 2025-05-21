package com.mli.mpro.document.service.impl;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;

@Service
public class DocumentHelper {

    private static final Logger logger = LoggerFactory.getLogger(DocumentHelper.class);

    private static String env = System.getenv("env");

    @Value("${urlDetails.proposalService}")
    private String url;

    @Value("${urlDetails.documentService}")
    private String saveDocumentToS3Url;

    @Value("${urlDetails.neoDocumentService}")
    private String saveDocumentToS3UrlNeo;

    @Value("${s3.bucket.name}")
    private String s3bucketName;

    @Value("${bypass.header.encrypt.value}")
    private String api_client_secret;

    @Autowired
    private ErrorMessageConfig errorMessageConfig;

    @Autowired
    private MongoOperations mongoOperations;

    /*
     * This is the centralized method which makes a Rest Call to the
     * saveDocument API of proposal microservice to upload the documents to S3
     */
    public String executeSaveDocumentToS3(DocumentDetails documentDetails, int retryCount){

        logger.info("location details to check {}", documentDetails);

        String documentUploadStatus = AppConstants.FAILED;
        RequestPayload requestPayload = new RequestPayload();
        requestPayload.setDocumentDetails(documentDetails);
        Metadata metadata = new Metadata(env, UUID.randomUUID().toString());
        RequestData requestData = new RequestData(requestPayload);
        Request request = new Request(metadata, requestData);
        InputRequest inputRequest = new InputRequest(request);

        boolean isNeo = false;
        ResponseEntity<OutputResponse> outputResponse = null;
        try {

            long requestedTime = System.currentTimeMillis();
            /**
             * Neo rewiring Check for Proposal service call
             */
			try {
				String channelType = documentDetails.getChannelName();
				logger.info("channelType...{}" , channelType);

				if (AppConstants.CHANNEL_NEO.equalsIgnoreCase(channelType) || AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelType)) {
					isNeo = true;
				}
			} catch (Exception ex) {
				logger.error("Catch json conversion ex:" , ex);
			}
			if (!isNeo) {
                HttpHeaders httpHeaders = Utility.setAPISecretInHeaders(api_client_secret);
                HttpEntity<InputRequest> savedocumentrequest = new HttpEntity<>(inputRequest, httpHeaders);
				logger.info("saveDocumentToS3Url...{}" , saveDocumentToS3Url);
				outputResponse = new RestTemplate().postForEntity(saveDocumentToS3Url,
						savedocumentrequest, OutputResponse.class);

			} else {
                HttpEntity<InputRequest> savedocumentrequest = new HttpEntity<>(inputRequest);
				logger.info("saveDocumentToS3UrlNeo...{}" , saveDocumentToS3UrlNeo);
				outputResponse = new RestTemplate().postForEntity(saveDocumentToS3UrlNeo,
						savedocumentrequest, OutputResponse.class);
			}
            long processedTime = (System.currentTimeMillis() - requestedTime);
            logger.info("SaveDocument service took {} miliseconds to process the request", processedTime);
            HttpStatus statusCode = outputResponse.getStatusCode();
            logger.info("Status Code {}", statusCode);
            logger.debug("The response received from saveDocument service for transactionId {} {}", documentDetails.getTransactionId(),
                    outputResponse.getBody());
            logger.info("The response is received from saveDocument service for transactionId {}.", documentDetails.getTransactionId());
            logger.debug("Error Response {}", outputResponse.getBody().getResponse().getErrorResponse());
            List<Object> documentStatus = outputResponse.getBody().getResponse().getResponseData().getResponsePayload().getMessage();
            if (!CollectionUtils.isEmpty(documentStatus) && documentStatus.get(0).equals(AppConstants.FAILED) && retryCount < 2) {
                logger.info("Retrying document upload for transactionId {} and retryCount {}", documentDetails.getTransactionId(), retryCount);
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("Retrying document upload failed:",e);
                }
                documentUploadStatus = executeSaveDocumentToS3(documentDetails, retryCount);
            } else if (!CollectionUtils.isEmpty(documentStatus) && documentStatus.get(0).equals(AppConstants.SUCCESS)) {
                documentUploadStatus = AppConstants.SUCCESS;
            }
        } catch (Exception ex) {
            logger.error("The request send to failed saveDocument service for transactionId {} {}", documentDetails.getTransactionId(),
                    inputRequest);
            logger.error("The response received from failed saveDocument service for transactionId {} {}", documentDetails.getTransactionId(),
                    outputResponse!=null? outputResponse.getBody():"");
            logger.error("SaveDocument service failed to give response:",ex);
            if (retryCount < 2) {
                logger.info("Retrying document upload for transactionId {} and retryCount {}", documentDetails.getTransactionId(), retryCount);
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("Retrying document upload failed:",e);
                }
                documentUploadStatus = executeSaveDocumentToS3(documentDetails, retryCount);
            }
        }
        return documentUploadStatus;

    }

    /*
     * This is the centralized method for generating PDF from custom HTML of
     * different documents received as input using Itext pdfHtml library. This
     * method returns the base64 of the generated method
     */
    public String generatePDFDocument(String processedHtml, int retryCount) {
        String encodedString = AppConstants.FAILED;
        if (!StringUtils.isEmpty(processedHtml)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
                DefaultFontProvider defaultFontProvider = new DefaultFontProvider(false, true, false);
                ConverterProperties converterProperties = new ConverterProperties();
                converterProperties.setFontProvider(defaultFontProvider);
                HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);
                encodedString = com.amazonaws.util.Base64.encodeAsString(byteArrayOutputStream.toByteArray());
            } catch (Exception e) {
                logger.error("Document generation failed:",e);
                if (retryCount < 2) {
                    retryCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                       logger.error("InterruptedException:",ex);
                    }
                    encodedString = generatePDFDocument(processedHtml, retryCount);
                }
            }
        }

        return encodedString;
    }

    public void updateDocumentStatus(DocumentStatusDetails documentStatusDetails) {

        String collectionName = "documentStatus";
        int existingRetryCount = 0;
        Query query = new Query();
        Update update = new Update();
        logger.info("document status detail :: {}",documentStatusDetails);
        logger.info("TRANSACTION ID ::: {}",documentStatusDetails.getTransactionId());
        long transactionId = documentStatusDetails.getTransactionId();
        try {
            String documentName = documentStatusDetails.getDocumentName();
            String documentStatus = documentStatusDetails.getDocumentStatus();
            String stage = documentStatusDetails.getApplicationStage();
            logger.info("document details : documentName : {} documentStatus {} stage {}", documentName, documentStatus, stage);
            documentStatusDetails.setDocumentGeneratedDate(new Date());
            query.addCriteria(Criteria.where("transactionId").is(transactionId).and("documentName").is(documentName));
            DocumentStatusDetails documentDetails = mongoOperations.findOne(query, DocumentStatusDetails.class);
            if (documentDetails != null) {
                existingRetryCount = documentDetails.getRetryCount();
                update.set("documentStatus", documentStatus);
                update.set("retryCount", existingRetryCount + 1);
                if(stage!=null)
                	update.set("applicationStage", stage);
                mongoOperations.updateFirst(query, update, DocumentStatusDetails.class);
            } else {
                mongoOperations.save(documentStatusDetails, collectionName);
            }
        } catch (Exception ex) {
            logger.error("Document status updating to DB failed:",ex);
        }
    }

    // method to get S3 location URL of proposer/payor image
    public String getImageURL(long transactionId, String imageType, String channel, List<DocumentDetails> document) {
        String documentType = "";
        String url = AppConstants.DUMMY_BLANK_IMAGE_PATH;
        boolean isThanosDolphinIntegrationEnabled;
        try {
            String documentId = getPhotoType(imageType);
            documentType = getDocumentType(documentId, document);
            if (StringUtils.isNotEmpty(documentType) && StringUtils.isNotEmpty(documentId)) {
                isThanosDolphinIntegrationEnabled = getThanosDolphinIntegrationEnabled(documentId, document);
                String imageName = getImageName(imageType);
                String imageUrl = AppConstants.S3PATH + (isThanosDolphinIntegrationEnabled && AppConstants.THANOS_CHANNEL.equalsIgnoreCase(channel)
                        ? AppConstants.CHANNEL_AXIS : channel) + "/" + transactionId + "/" + imageName + documentType;
                url = getObjectPresignedUrl(imageUrl);
                logger.info("{} image url {}", imageType, imageUrl);
            }
        } catch (Exception ex) {
            logger.error("S3 location URL of {} image failed:", imageType, ex);
        }
        return url;
    }

    private static String getPhotoType(String imageType) {
        String photoType = "";
        if (imageType.equalsIgnoreCase("Proposer")) {
            photoType = "Photo_Pr";
        } else if (imageType.equalsIgnoreCase("Payor")) {
            photoType = "Photo_Pa";
        } else if (imageType.equalsIgnoreCase("secondAnnuitant") || "Insured".equalsIgnoreCase(imageType)) {
            photoType = "Photo_In";

        }
        return photoType;
    }

    private String getImageName(String imageType) {
        if (imageType.equalsIgnoreCase("Proposer")) {
            return "Photograph of Proposer_1.";
        } else if (imageType.equalsIgnoreCase("Payor")) {
            return "Photograph of Payor_1.";
        } else if (imageType.equalsIgnoreCase("secondAnnuitant")) {
            return "Photograph of Second Annuitant_7.";
        } else if ("Insured".equalsIgnoreCase(imageType)) {
            return "Photograph of Insured_7.";
        }  else {
            return "";
        }
    }


    private String getDocumentType(String documentId, List<DocumentDetails> document) {
        for (int i = 0; i < document.size(); i++) {
            if (document.get(i).getMproDocumentId().equalsIgnoreCase(documentId))
                return document.get(i).getDocumentType();
        }
        return null;
    }

    private boolean getThanosDolphinIntegrationEnabled(String documentId, List<DocumentDetails> document){
        for (int i = 0; i < document.size(); i++) {
            if (document.get(i).getMproDocumentId().equalsIgnoreCase(documentId))
                return document.get(i).isThanosDolphinIntegrationEnabled();
        }
        return false;
    }

    public String getObjectPresignedUrl(String objectKey) {

	AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
	GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(s3bucketName, objectKey);
	generatePresignedUrlRequest.setMethod(HttpMethod.GET);
	Date expiry = new Date();
	long milliseconds = expiry.getTime();
	// time limit 30 minutes
	milliseconds += 1000 * 60 * 30;
	expiry.setTime(milliseconds);
	generatePresignedUrlRequest.setExpiration(expiry);
	URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
	logger.info("Object key {} and path {}", objectKey, url);

	String finalUrl = url.toString();
	return finalUrl;
    }

    public  DocumentStatusDetails populateDocumentStatusObj(boolean isNeo,ProposalDetails proposalDetails, long transactionId,String documentStatus,String documentName) {
		DocumentStatusDetails documentStatusDetails=null;
		if(!isNeo)
			documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		        proposalDetails.getSourcingDetails().getAgentId(), documentStatus, 0, documentName,
		        proposalDetails.getApplicationDetails().getStage());
		else
			documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			        "", documentStatus, 0, documentName,
			        proposalDetails.getApplicationDetails().getStage(),proposalDetails.getChannelDetails().getChannel());
		return documentStatusDetails;
	}

    /*FUL2-7393*
    Resolve duplicate line of code by making one method of CIPProposalForm and TraditionalProposalFrom	/
     */
    public DocumentStatusDetails getDocumentStatusDetails(ProposalDetails proposalDetails, long transactionId, int retryCount, DocumentRequestInfo documentRequestInfo) {
        String documentUploadStatus;
        DocumentStatusDetails documentStatusDetails;
        List<DocumentRequestInfo> documentpayload = new ArrayList<>();
        documentpayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                "PROPOSAL_FORM_PDF", AppConstants.PROPOSAL_FORM_DOCUMENT, documentpayload);
        documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
        if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
            documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
        }
        documentUploadStatus = executeSaveDocumentToS3(documentDetails, retryCount);
        if (AppConstants.SUCCESS.equalsIgnoreCase(documentUploadStatus) && AppConstants.TRADITIONAL.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductType())
                && Utility.isCapitalGuaranteeSolutionProduct(proposalDetails)) {
            long transactionIdForCombo = proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() != 0l
                    ? proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId() : proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId();
            documentDetails.setTransactionId(transactionIdForCombo);
            documentDetails.setDocumentName(AppConstants.COMBO_PROPOSAL_FORM_DOCUMENT);
            documentDetails.setMproDocumentId("COMBO_PROPOSAL_FORM_PDF");
            documentUploadStatus = executeSaveDocumentToS3(documentDetails, retryCount);
        }
        if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
            // update the document upload failure status in db
            logger.info("Document upload is failed for transactionId {} {}", transactionId, AppConstants.PROPOSAL_FORM_DOCUMENT);
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.PROPOSAL_FORM_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
        } else {
            logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
                    AppConstants.PROPOSAL_FORM_DOCUMENT);
            // update the document upload success status in db
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus,
                    0, AppConstants.PROPOSAL_FORM_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
        }
        return documentStatusDetails;
    }

    public DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails, String pdfDocumentOrDocumentStatus, int retryCount,
                                                           String mproDocumentId, String documentName){
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
                AppConstants.DOCUMENT_TYPE,
                pdfDocumentOrDocumentStatus,
                documentName);
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        documentPayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                mproDocumentId, documentName, documentPayload);
        documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
        String documentUploadStatus = executeSaveDocumentToS3(documentDetails, retryCount);
        if (AppConstants.FAILED.equalsIgnoreCase(documentUploadStatus)) {
            // update the document upload failure status in DB
            logger.info("Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), documentName);
            return populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, documentName);
        } else {
            // update the document upload success status in DB
            logger.info("Document is successfully uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                    documentName);
            return populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    documentUploadStatus, documentName);
        }
    }

}
