package com.mli.mpro.document.service.impl;


import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.RiderDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.*;

@Component("neoCISRiderDocument")
public class NeoCISRiderDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoCISRiderDocument.class);

    @Autowired
    private NeoCISRiderDocumentMapper neoCISRiderDocumentMapper;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;


    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        int retryCount = 0;
        DocumentStatusDetails documentStatusDetails = null;
        Long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        String channelName = proposalDetails.getChannelDetails().getChannel();
        try {
            logger.info("NEO CIS Rider document generation is initiated for transactionId {} and at applicationStage {}",
                    transactionId, proposalDetails.getApplicationDetails().getStage());

            Map<String, Object> dataMap = new HashMap<>();
            Context annexureData = new Context();

            if (Objects.nonNull(proposalDetails.getProductDetails()) && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())
                    && Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails())) {
                List<RiderDetails> riderDetailsList = proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails();
                Context context = neoCISRiderDocumentMapper.setCISRiderData(proposalDetails, riderDetailsList);

                for (RiderDetails riderDetails : riderDetailsList) {
                    String riderType = riderDetails.getRiderInfo();
                    settingTemplateForCiRiderDocument(dataMap, context, riderType);
                    settingTemplateForWOPRiderDocument(dataMap, context, riderType);
                    settingTemplateForAddRiderDocument(dataMap, context, riderType);
                }

                Context neoCisRiderDocumentContext = new Context();
                String processsedHtmlAnnexureB = springTemplateEngine.process("neo\\CIS\\AnnexureB", annexureData);
                dataMap.put("AnnexureB", processsedHtmlAnnexureB);
                neoCisRiderDocumentContext.setVariables(dataMap);

                String neoCISRiderFinalDocument = springTemplateEngine.process("neo\\RiderCis\\CIS_Rider_Form", neoCisRiderDocumentContext);
                String encodedStringForm = documentHelper.generatePDFDocument(neoCISRiderFinalDocument, retryCount);
                logger.info("CIS Rider HTML to pdf conversation is done for transactionId {}", transactionId);
                documentStatusDetails = handleDocumentGenerationResponse(encodedStringForm, proposalDetails, channelName);
            }
        } catch (UserHandledException e) {
            logger.error("Neo CIS Rider Document generation failed : {}", Utility.getExceptionAsString(e));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.DATA_MISSING_FAILURE);
        } catch (Exception e) {
            logger.error("Neo CIS Rider Document generation failed :{}", Utility.getExceptionAsString(e));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.TECHNICAL_FAILURE);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("NEO CIS Rider Document for transactionId {} took {} milliseconds ",
                transactionId, processedTime);
    }

    private void settingTemplateForWOPRiderDocument(Map<String, Object> dataMap, Context context, String riderType) {
        if (AppConstants.WOP_RIDER_LIST.contains(riderType)) {
            String wopRiderProcessedHtml = springTemplateEngine.process("neo\\RiderCis\\wopRider", context);
            dataMap.put("wopRider", wopRiderProcessedHtml);
        }
    }

    private void settingTemplateForCiRiderDocument(Map<String, Object> dataMap, Context context, String riderType) {
        if (AppConstants.CI_RIDER_LIST.contains(riderType)) {
            String ciRiderProcessedHtml = springTemplateEngine.process("neo\\RiderCis\\ciRider", context);
            dataMap.put("ciRider", ciRiderProcessedHtml);
        }
    }

    private void settingTemplateForAddRiderDocument(Map<String, Object> dataMap, Context context, String riderType) {
        if (AppConstants.ADD_RIDER_LIST.contains(riderType)) {
            String addRiderProcessedHtml = springTemplateEngine.process("neo\\RiderCis\\addRider", context);
            dataMap.put("addRider", addRiderProcessedHtml);
        }
    }

    private DocumentStatusDetails handleDocumentGenerationResponse(String encodedStringForm, ProposalDetails proposalDetails, String channelName) {
        Long transactionId = proposalDetails.getTransactionId();

        if (AppConstants.FAILED.equalsIgnoreCase(encodedStringForm)) {
            logger.info("Neo CIS Rider Document generation failed, updating DB for transactionId {}", transactionId);
            return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_GENERATION_FAILED);
        } else {
            DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                    encodedStringForm, AppConstants.CIS_RIDER_DOCUMENT);
            List<DocumentRequestInfo> documentpayload = new ArrayList<>();
            documentpayload.add(documentRequest);

            DocumentDetails documentDetail = new DocumentDetails(channelName,
                    proposalDetails.getTransactionId(), AppConstants.NEO_CIS_RIDER_DOCUMENT_NAME, AppConstants.CIS_RIDER_DOCUMENT,
                    documentpayload);

            String documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, 0);

            if (AppConstants.FAILED.equalsIgnoreCase(documentStatusUpload)) {
                logger.info("Neo CIS Rider Document upload failed for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_UPLOAD_FAILED);
            } else {
                logger.info("Neo CIS Rider Document successfully generated and uploaded to S3 for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, documentStatusUpload);
            }
        }
    }

    private DocumentStatusDetails createDocumentStatusDetails(ProposalDetails proposalDetails, String status) {
        return new DocumentStatusDetails(
                proposalDetails.getTransactionId(),
                proposalDetails.getApplicationDetails().getPolicyNumber(),
                proposalDetails.getSourcingDetails().getAgentId(),
                status,
                0,
                AppConstants.CIS_RIDER_DOCUMENT,
                proposalDetails.getApplicationDetails().getStage()
        );
    }
}
