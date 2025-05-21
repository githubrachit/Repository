package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.MoralHazardReportMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.neo.models.attachment.Payload;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SellerDeclaration;
import com.mli.mpro.utils.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component("moralHazardReportDocument")
public class MoralHazardReportDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(MoralHazardReportDocument.class);

    @Autowired
    protected SpringTemplateEngine springTemplateEngine;

    @Autowired
    protected DocumentHelper documentHelper;


    @Autowired
    private MoralHazardReportMapper moralHazardReportMapper;


    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        String documentUploadStatus = "";
        DocumentStatusDetails documentStatusDetails = null;
        int retryCount = 0;
        logger.info("Starting Moral Hazard Report Document creation for transactionId {}", transactionId);

        SellerDeclaration sellerDeclaration = (proposalDetails.getSellerDeclaration() != null) ? proposalDetails.getSellerDeclaration() : null;
        logger.info("seller deaclaration:: {}", sellerDeclaration);

        try{
            if(Objects.nonNull(sellerDeclaration)){
                Context mhrCotext = moralHazardReportMapper.setDataForMHRDocument(proposalDetails);
                String finalProcessHtmlTemplate = "";
                if(AppConstants.YES.equalsIgnoreCase(sellerDeclaration.getSellerDisclosure())){
                    finalProcessHtmlTemplate = springTemplateEngine.process("moralhazardreport\\mhrForYes", mhrCotext);
                } else {
                    finalProcessHtmlTemplate = springTemplateEngine.process("moralhazardreport\\mhrForNo", mhrCotext);
                }
                logger.info("Generating Moral Hazar Report Document for transactionId {}", transactionId);

                String encodedString = documentHelper.generatePDFDocument(finalProcessHtmlTemplate, 0);

                if(encodedString.equalsIgnoreCase(AppConstants.FAILED)){
                    logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), AppConstants.MHR_DOCUMENT_GENERATION_FAILED, 0, AppConstants.MHR_DOCUMENT,
                            proposalDetails.getApplicationDetails().getStage());
                    logger.info("documentStatusDetails 1 : {}",documentStatusDetails);
                } else {
                    logger.info("encoded string value ::: {}", encodedString);
                    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.MHR_DOCUMENT);
                    List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                    documentpayload.add(documentRequestInfo);
                    DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                            AppConstants.MHR_DOCUMENT_ID, AppConstants.MHR_DOCUMENT, documentpayload);
                    documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
                    documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
                   // FUL2-46310 For CPS product, uploading the seller declaration document to secondary policy 
                    if (AppConstants.SUCCESS.equalsIgnoreCase(documentUploadStatus) && Utility.isCapitalGuaranteeSolutionPrimaryProduct(proposalDetails)) {
                        documentDetails.setTransactionId(proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId());
                        documentDetails.setDocumentName(AppConstants.MHR_DOCUMENT);
                        documentDetails.setMproDocumentId(AppConstants.MHR_DOCUMENT_ID);
                        documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
                    }
                    if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
                        logger.info("MORAL_HAZAR_REPORT_DOCUMENT Document upload to S3 failed, transactionId {}", transactionId);
                        // update the document upload failure status in db
                        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                                proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                                AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.MHR_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
                        logger.info("documentStatusDetails 2: {}",documentStatusDetails);
                    } else {
                        logger.info("MORAL_HAZAR_REPORT_DOCUMENT Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
                                AppConstants.MHR_DOCUMENT);
                        // update the document upload success status in db
                        documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                                proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.MHR_DOCUMENT,
                                proposalDetails.getApplicationDetails().getStage());
                        logger.info("documentStatusDetails 3:: {} ",documentStatusDetails);
                    }
                }

                logger.info("{} created and uploaded to S3 for transaction Id {} ", documentUploadStatus, proposalDetails.getTransactionId());
            } else {
                logger.info("There is nothing to create document (seems older policy). for transactionId {}", transactionId);
            }
        } catch (Exception ex){
            logger.error("Error occurred while MORAL HAZAR REPORT DOCUMENT generation for transactionId {} :", transactionId, ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.MHR_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);
            logger.info("documentStatusDetails 4:: {}",documentStatusDetails);
        }
        logger.info("document status details :: {}", documentStatusDetails);
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Moral Hazar Report Document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

    @Override
    public void createDocumentAndUploadToS3(Payload payload) {
        // Empty Method
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        return null;
    }
}
