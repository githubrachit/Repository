package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("customerInformationSheetRiderDocument")
@EnableAsync
public class CISRiderDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(CISRiderDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private DocumentHelper documentHelper;
    @Autowired
    private CISRiderDocumentMapper cisDocumentMapper;
    private static final Map<String, String> RIDER_HTML_MAP = new HashMap<>();

    static {
         RIDER_HTML_MAP.put(AppConstants.TERM_RIDER,"TermRider");
         RIDER_HTML_MAP.put(AppConstants.ADD_RIDER,"ADDRider");
         RIDER_HTML_MAP.put(AppConstants.WOP_RIDER,"WOPRider");
         RIDER_HTML_MAP.put(AppConstants.SUPR_RIDER,"SUPRRider");
         RIDER_HTML_MAP.put(AppConstants.CIDS_RIDER,"CIDSRider");
         RIDER_HTML_MAP.put(AppConstants.CID_RIDER,"CIDRider");
    }

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        Utility.safeSleep(5000);
        Long transactionId = proposalDetails.getTransactionId();
        List<String> applicableRiders = proposalDetails.getProductDetails().get(0).getProductInfo().getApplicableCisRiders();
        String channelName = proposalDetails.getChannelDetails().getChannel();
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        DocumentStatusDetails documentStatusDetails = null;

        for (String rider : applicableRiders) {
            logger.info("Rider applicable for CIS for transactionId {} is {}", transactionId, rider);

            String encodedStringForm = generateRiderCIS(proposalDetails, rider);

            if (AppConstants.FAILED.equalsIgnoreCase(encodedStringForm)) {
                logger.info("{} CIS Document generation failed, updating DB for transactionId {}",rider, transactionId);
                createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_GENERATION_FAILED, rider);
            } else {
                //Adding documentRequestInfo for all the rider documents which are required to upload
                DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                        encodedStringForm, AppConstants.CIS_RIDER_DOCUMENT_NAME);
                documentPayload.add(documentRequest);
            }
        }
        if (!documentPayload.isEmpty()) {

            //Adding all request document info into main document object
            DocumentDetails documentDetail = new DocumentDetails(channelName,
                    proposalDetails.getTransactionId(), AppConstants.CIS_RIDER_DOCUMENT_ID, AppConstants.CIS_RIDER_DOCUMENT_NAME,
                    documentPayload);
            logger.info("Final CIS Rider documentDetails for transactionId {} {}",transactionId, Utility.getJsonRequest(documentDetail));
            //calling proposal-ms to upload all the riderCis documents
            String documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, 0);

            if (AppConstants.FAILED.equalsIgnoreCase(documentStatusUpload)) {
                logger.info("CIS Rider Document upload failed for transactionId {}", transactionId);
                documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.CIS_RIDER_DOCUMENT_NAME);
            } else {
                logger.info("CIS Rider Document successfully generated and uploaded to S3 for transactionId {}", transactionId);
                documentStatusDetails = createDocumentStatusDetails(proposalDetails, documentStatusUpload, AppConstants.CIS_RIDER_DOCUMENT_NAME);
            }
            documentHelper.updateDocumentStatus(documentStatusDetails);
        }else{
            logger.info("No CIS Rider Document to generate for transactionId {}", transactionId);
        }
    }
    public String generateRiderCIS (ProposalDetails proposalDetails,String rider) {
        int retryCount = 0;
        DocumentStatusDetails documentStatusDetails = null;
        Long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        logger.info("{} document generation is initiated for transactionId {} and at applicationStage {}",rider,
                transactionId, proposalDetails.getApplicationDetails().getStage());
        try {
            Context documentData = cisDocumentMapper.setRiderCISData(proposalDetails,rider);
            Context annexureData = new Context();
            String htmlForm = getHtmlBasedOnRider(rider);

            Map<String, Object> completeDetails = new HashMap<>();

            String processsedHtmlAnnexure = springTemplateEngine.process("CIS\\Annexure",annexureData);
            String processedHtmlForm = springTemplateEngine.process("RiderCIS\\" + htmlForm, documentData);
            String processedHtmlAnnexure2 = springTemplateEngine.process("RiderCIS\\Annexure2",annexureData);
            String processedHtmlCIDSRAnnexure2 = springTemplateEngine.process("RiderCIS\\CIDSR_Annexure2",annexureData);
            String processedHtmlCIDSRAnnexure3 = springTemplateEngine.process("RiderCIS\\CIDSR_Annexure3",annexureData);
            String processedHtmlCIDSRAnnexure4 = springTemplateEngine.process("RiderCIS\\CIDSR_Annexure4",annexureData);

            completeDetails.put("htmlForm",processedHtmlForm);
            completeDetails.put("Annexure",processsedHtmlAnnexure);
            completeDetails.put("Annexure2",processedHtmlAnnexure2);
            completeDetails.put("CIDSR_Annexure2",processedHtmlCIDSRAnnexure2);
            completeDetails.put("CIDSR_Annexure3",processedHtmlCIDSRAnnexure3);
            completeDetails.put("CIDSR_Annexure4",processedHtmlCIDSRAnnexure4);
            completeDetails.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
            completeDetails.put("isAnnexure2VisibleOrNot",Utility.isWopRider(rider));
            completeDetails.put("isCIDSRAnnexure2VisibleOrNot",Utility.isCIDSRAnnexureVisibleOrNot(rider));
            completeDetails.put("isCIDSRAnnexure3VisibleOrNot",Utility.isCIDSRAnnexureVisibleOrNot(rider));
            completeDetails.put("isCIDSRAnnexure4VisibleOrNot",Utility.isCIDSRAnnexureVisibleOrNot(rider));
            Context cisFormDetailsCtx = new Context();
            cisFormDetailsCtx.setVariables(completeDetails);

            logger.info("{} Data binding with CIS HTML is done for transactionId {}",rider, transactionId);

            String finalDocument = springTemplateEngine.process("CIS\\CIS_Form",cisFormDetailsCtx);

            long processedTime = (System.currentTimeMillis() - requestedTime);
            logger.info("{} CIS document for transactionId {} took {} milliSeconds ", rider,
                    proposalDetails.getTransactionId(), processedTime);

            return documentHelper.generatePDFDocument(finalDocument, retryCount);
        }
        catch (UserHandledException e) {
            logger.error("{} CIS Document generation failed : {}",rider, Utility.getExceptionAsString(e));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.DATA_MISSING_FAILURE, rider);
        } catch (Exception ex) {
            logger.error("{} CIS Document generation failed :{}",rider, Utility.getExceptionAsString(ex));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.TECHNICAL_FAILURE, rider);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);

        logger.info("{} document failed to process for transactionId {}", rider,
                proposalDetails.getTransactionId());

        return AppConstants.FAILED;
    }

    private DocumentStatusDetails createDocumentStatusDetails(ProposalDetails proposalDetails, String status, String documentName) {
        return new DocumentStatusDetails(
                proposalDetails.getTransactionId(),
                proposalDetails.getApplicationDetails().getPolicyNumber(),
                proposalDetails.getSourcingDetails().getAgentId(),
                status,
                0,
                documentName,
                proposalDetails.getApplicationDetails().getStage()
        );
    }
    public String getHtmlBasedOnRider(String rider) {
        return RIDER_HTML_MAP.getOrDefault(rider, "");
    }
}