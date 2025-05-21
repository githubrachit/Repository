package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.mapper.GSTWaiverDeclarationMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.apache.commons.lang3.StringUtils;
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

import static com.mli.mpro.productRestriction.util.AppConstants.FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION;

@Component("nriGstWaiverDocument")
@EnableAsync
public class NRIGstWaiverDocument implements DocumentGenerationservice {
    private static final Logger logger = LoggerFactory.getLogger(NRIGstWaiverDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private GSTWaiverDeclarationMapper gstWaiverDeclarationMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Initiating pdf generation for NriGstWaiverDocument...");
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        String nriGstWaiverDocumentName = StringUtils.EMPTY;
        int retryCount = 0;
        try{
        if (FeatureFlagUtil.isFeatureFlagEnabled(FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION)
                && null != proposalDetails.getApplicationDetails().getFormType()
                && !(AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()))
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())) {
            Context context = gstWaiverDeclarationMapper.setDataOfGSTWaiverDeclarationDocument(proposalDetails);
            String processedHtml = springTemplateEngine.process("nri\\gstWaiverDeclarationDetails", context);
            String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                // update the document generation failure status in db
                logger.info("NRI GST waiver Document generation is failed so updating in DB for transactionId {}", transactionId);
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0,
                        nriGstWaiverDocumentName,
                        proposalDetails.getApplicationDetails().getStage());
            }
            generateNriGstWaiver(encodedString, proposalDetails);
        }
        } catch (Exception ex) {
            logger.error("Error occurred while NRI GST WAIVER Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId,
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
                    AppConstants.NRI_GST_WAIVER,
                    proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);
        }
    }

    private void generateNriGstWaiver(String encodedString, ProposalDetails proposalDetails) {
        logger.info("Started to generate nri gst waiver document for transactionId {}", proposalDetails.getTransactionId());
        DocumentStatusDetails documentStatusDetails=null;
        String documentUploadStatus = "";
        try {
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
                AppConstants.NRI_GST_WAIVER); // have to change the name
        List<DocumentRequestInfo> documentpayload = new ArrayList<>();
        int retryCount = 0;
        documentpayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
                proposalDetails.getTransactionId(), AppConstants.NRI_GST_WAIVER, AppConstants.NRI_GST_WAIVER_DECLARATION,
                documentpayload);
        documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
        logger.info("Saving GST Waiver document to S3 for transactionId {} with documentName {}", proposalDetails.getTransactionId(), documentDetails.getDocumentName());
        documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
        if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
                    AppConstants.NRI_GST_WAIVER, proposalDetails.getApplicationDetails().getStage());
            logger.info("Document generation is failed for NRI for transactionId {}",proposalDetails.getTransactionId());
        } else {
            logger.info("NRI GST Waiver Document is successfully generated and uploaded to S3 for transactionId {} {}",
                    proposalDetails.getTransactionId(), AppConstants.NRI_GST_WAIVER);
            // update the document upload success status in db
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
                    AppConstants.GST_WAIVER_STATUS, proposalDetails.getApplicationDetails().getStage());
        }

        documentHelper.updateDocumentStatus(documentStatusDetails);
        } catch (Exception ex) {
            logger.error("Exception occured while generating NRI GST Waiver document for transactionId {}. Exception cause {}", proposalDetails.getTransactionId(), ex.getMessage());
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
                    AppConstants.NRI_GST_WAIVER, proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);
        }
    }
}
