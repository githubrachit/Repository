package com.mli.mpro.document.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.PanDobMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("payorPanDobDocument")
@EnableAsync
public class PayorPanDobDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(PayorPanDobDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private PanDobMapper panDobMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        String documentUploadStatus = "";
        DocumentDetails documentDetails = new DocumentDetails();
        logger.info("Starting Payor PAN DOB document creation for transactionId {}", transactionId);
        try {
            boolean panFlag = false;
            List<PartyInformation> proposalPartyInformationList = proposalDetails.getPartyInformation();
            if (!CollectionUtils.isEmpty(proposalPartyInformationList) && proposalPartyInformationList.size() > 2) {
                panFlag = proposalDetails.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().isPanValidated();

                Context panDobDetailsContext = panDobMapper.setDataOfPayorPanDobDocument(proposalDetails);
                String finalProcessedHtml = springTemplateEngine.process("pandob\\pandob", panDobDetailsContext);
                logger.info("Generating Payor PANDOB Document...");

                if (panFlag) {
                    String pdfDocumentStatus = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
                    if (pdfDocumentStatus.equalsIgnoreCase(AppConstants.FAILED)) {
                        // update the document generation failure status in db
                        logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                                proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                                AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.PAYER_PANDOB_DOCUMENT,
                                proposalDetails.getApplicationDetails().getStage());
                    } else {
                        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, pdfDocumentStatus,
                                AppConstants.PAYER_PANDOB_DOCUMENT);
                        List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                        documentpayload.add(documentRequestInfo);
                        documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                                AppConstants.PAYOR_DOCUMENTID, AppConstants.PAYER_PANDOB_DOCUMENT, documentpayload);
                        // S3
                        documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
                        if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
                            documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
                        }
                        documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
                        documentStatusDetails = getDocStatusDetailsBasedOnDocUploadStatus(proposalDetails,  documentUploadStatus);
                    }
                    documentHelper.updateDocumentStatus(documentStatusDetails);
                }
            } else {
                logger.error("Payor information not present for the proposal number");
            }
        } catch (UserHandledException ex) {
	    logger.error("Error occurred while Payor PANDOB Form Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PAYER_PANDOB_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);
        } catch (Exception ex) {
	    logger.error("Error occurred while Payor PANDOB Form Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PAYER_PANDOB_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);
        }

	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("Payor PANDOB created and uploaded to S3 for transaction Id {}, took {} miliseconds", proposalDetails.getTransactionId(), processedTime);
    }

    private DocumentStatusDetails getDocStatusDetailsBasedOnDocUploadStatus(ProposalDetails proposalDetails, String documentUploadStatus) {
        DocumentStatusDetails documentStatusDetails;
        if (StringUtils.equalsIgnoreCase(documentUploadStatus, AppConstants.FAILED)) {
            // update the document upload failure status in db
            logger.info("Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), AppConstants.PAYER_PANDOB_DOCUMENT);
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.PAYER_PANDOB_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        } else {
            logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                    AppConstants.PAYER_PANDOB_DOCUMENT);
            // update the document upload success status in db
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                    documentUploadStatus, 0, AppConstants.PAYER_PANDOB_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
        }
        return documentStatusDetails;
    }

}
