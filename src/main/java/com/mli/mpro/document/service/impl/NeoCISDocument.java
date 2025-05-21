package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.enums.FamilyType;
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
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("neoCISDocument")
public class NeoCISDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoCISDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private DocumentHelper documentHelper;
    @Autowired
    private NeoCISDocumentMapper neoCISDocumentMapper;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        String productId= proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
        int retryCount = 0;
        DocumentStatusDetails documentStatusDetails = null;
        Long transactionId = proposalDetails.getTransactionId();
        String channelName = proposalDetails.getChannelDetails().getChannel();
        long requestedTime = System.currentTimeMillis();
        logger.info("NEO CIS document generation is initiated for transactionId {} and at applicationStage {}",
                transactionId, proposalDetails.getApplicationDetails().getStage());
        try {
            String htmlForm = FamilyType.getHtmlFormByProductId(productId);
            Context documentData = neoCISDocumentMapper.setNeoCISData(proposalDetails, htmlForm);
            Context annexureData = new Context();

            Map<String, Object> completeDetails = new HashMap<>();

            String processsedHtmlAnnexure = springTemplateEngine.process("neo\\CIS\\Annexure", annexureData);
            String processedHtmlForm = springTemplateEngine.process("neo\\CIS\\" + htmlForm, documentData);
            String processsedHtmlAnnexureB = springTemplateEngine.process("neo\\CIS\\AnnexureB", annexureData);
            completeDetails.put("AnnexureB", processsedHtmlAnnexureB);

            completeDetails.put("htmlForm", processedHtmlForm);
            completeDetails.put("Annexure", processsedHtmlAnnexure);
            Context neoCisFormDetailsCtx = new Context();
            neoCisFormDetailsCtx.setVariables(completeDetails);
            logger.info("Data binding with CIS HTML is done for transactionId {}", transactionId);

            String neoCisFinalDocument = springTemplateEngine.process("neo\\CIS\\CIS_Form", neoCisFormDetailsCtx);
            String encodedStringForm = documentHelper.generatePDFDocument(neoCisFinalDocument, retryCount);
            logger.info("CIS HTML to pdf conversation is done for transactionId {}", transactionId);

            documentStatusDetails = handleDocumentGenerationResponse(encodedStringForm, proposalDetails, channelName);
        } catch (UserHandledException e) {
            logger.error("Neo CIS Document generation failed : {}", Utility.getExceptionAsString(e));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.DATA_MISSING_FAILURE);
        } catch (Exception ex) {
            logger.error("Neo CIS Document generation failed :{}", Utility.getExceptionAsString(ex));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.TECHNICAL_FAILURE);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Neo CIS document for transactionId {} took {} miliseconds ",
                proposalDetails.getTransactionId(), processedTime);

    }

    private DocumentStatusDetails handleDocumentGenerationResponse(String encodedStringForm, ProposalDetails proposalDetails, String channelName) {
        Long transactionId = proposalDetails.getTransactionId();

        if (AppConstants.FAILED.equalsIgnoreCase(encodedStringForm)) {
            logger.info("Neo CIS Document generation failed, updating DB for transactionId {}", transactionId);
            return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_GENERATION_FAILED);
        } else {
            DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                    encodedStringForm, AppConstants.NEO_CIS_DOCUMENT);
            List<DocumentRequestInfo> documentpayload = new ArrayList<>();
            documentpayload.add(documentRequest);

            DocumentDetails documentDetail = new DocumentDetails(channelName,
                    proposalDetails.getTransactionId(), AppConstants.CIS_DOCUMENT_ID, AppConstants.NEO_CIS_DOCUMENT,
                    documentpayload);

            String documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, 0);

            if (AppConstants.FAILED.equalsIgnoreCase(documentStatusUpload)) {
                logger.info("Neo CIS Document upload failed for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_UPLOAD_FAILED);
            } else {
                logger.info("Neo CIS Document successfully generated and uploaded to S3 for transactionId {}", transactionId);
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
                AppConstants.NEO_CIS_DOCUMENT,
                proposalDetails.getApplicationDetails().getStage()
        );
    }

}
