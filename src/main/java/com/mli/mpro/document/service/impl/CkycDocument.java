package com.mli.mpro.document.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.CkycMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("ckycDocument")
@EnableAsync
public class CkycDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(CkycDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private CkycMapper ckycMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
	logger.info("Starting CKYC document creation.");
	DocumentStatusDetails documentStatusDetails = null;
	long transactionId = proposalDetails.getTransactionId();
	long requestedTime = System.currentTimeMillis();
	String channel = proposalDetails.getChannelDetails().getChannel();
	int retryCount = 0;
	String documentUploadStatus = "";

	try {
	    Context panDobDetailsContext = ckycMapper.setDataOfCkycDocument(proposalDetails);
	    String finalProcessedHtml = springTemplateEngine.process("ckyc\\ckyc", panDobDetailsContext);
	    logger.info("Generating CKYC Document... ");

	    String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

	    logger.info("{} created and uploaded to S3 for transaction Id {} ", documentUploadStatus, proposalDetails.getTransactionId());
	    if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		// update the document generation failure status in db
		logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.CKYC_DOCUMENT,
			proposalDetails.getApplicationDetails().getStage());
	    } else {
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.CKYC_DOCUMENT);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(channel, transactionId, AppConstants.CKYC_DOCUMENTID, AppConstants.CKYC_DOCUMENT,
			documentpayload);
				documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
			if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
				documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
			}
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
		    // update the document upload failure status in db
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
			    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.CKYC_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		} else {
		    logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.CKYC_DOCUMENT);
		    // update the document upload success status in db
		    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.CKYC_DOCUMENT,
			    proposalDetails.getApplicationDetails().getStage());
		}
	    }
	} catch (UserHandledException ex) {
	    logger.error("Error occurred while CKYC Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.CKYC_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Error occurred while CKYC Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.CKYC_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	}
	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("CKYC document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

}
