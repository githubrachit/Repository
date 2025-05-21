package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.OasMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

@Component("oasDocument")
@EnableAsync
public class OasDocument implements DocumentGenerationservice {
    private static final Logger logger = LoggerFactory.getLogger(OasDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;
    @Autowired
    private OasMapper oasMapper;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Starting OAS Document Creation for transaction Id {}", proposalDetails.getTransactionId());
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        String channel = proposalDetails.getChannelDetails().getChannel();
        int retryCount = 0;
        String documentUploadStatus = "";
        try {
            Context contextDetail = oasMapper.setDataOfOasDocument(proposalDetails);
            String finalProcessedHtml = springTemplateEngine.process("oas\\OAS_Declaration",contextDetail);
            logger.info("Generating OAS Document for transaction ID {} ", proposalDetails.getTransactionId());
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                // update the document generation failure status in db
                logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.OAS_DOCUMENT,
                        proposalDetails.getApplicationDetails().getStage());
            } else {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.OAS_DOCUMENT);
                List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                documentpayload.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(channel, transactionId, AppConstants.OAS_DOCUMENT, AppConstants.OAS_DOCUMENT,
                        documentpayload);
                documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
                documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
                if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
                    // update the document upload failure status in db
                    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                            proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                            AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.OAS_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
                } else {
                    logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.OAS_DOCUMENT);
                    // update the document upload success status in db
                    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.OAS_DOCUMENT,
                            proposalDetails.getApplicationDetails().getStage());
                }
            }
        } catch (UserHandledException ex) {
            logger.error("Error occurred while OAS Form Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.OAS_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        } catch (Exception ex) {
            logger.error("Error occurred while OAS Form Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.OAS_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("OAS document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

}
