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
import com.mli.mpro.document.mapper.DigitalConsentMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("digitalConsentDocument")
@EnableAsync
public class DigitalConsentDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(DigitalConsentDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private DigitalConsentMapper digitalConsentMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
	logger.info("Starting DIGITAL_CONSENT document creation.");
	long requestedTime = System.currentTimeMillis();
	DocumentStatusDetails documentStatusDetails = null;
	int retryCount = 0;
	String documentUploadStatus = "";
	long transactionId = proposalDetails.getTransactionId();
	try {
	    Context digitalConsentContext = digitalConsentMapper.setDataOfDigitalConsentDocument(proposalDetails);
	    String finalProcessedHtml = springTemplateEngine.process("ybl\\digitalConsent", digitalConsentContext);
	    logger.info("Generating DIGITAL_CONSENT Document... ");

	    String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

	    if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		// update the document generation failure status in db
		logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.DIGITAL_CONSENT_DOCUMENT,
			proposalDetails.getApplicationDetails().getStage());
	    } else {
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
			AppConstants.DIGITAL_CONSENT_DOCUMENT);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
			AppConstants.DIGITAL_CONSENT_DOCUMENTID, AppConstants.DIGITAL_CONSENT_DOCUMENT, documentpayload);
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
		    logger.info("DIGITAL_CONSENT Document upload to S3 failed");
		    // update the document upload failure status in db
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
			    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.DIGITAL_CONSENT_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		} else {
		    logger.info("DIGITAL_CONSENT Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
			    AppConstants.DIGITAL_CONSENT_DOCUMENT);
		    // update the document upload success status in db
		    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.DIGITAL_CONSENT_DOCUMENT,
			    proposalDetails.getApplicationDetails().getStage());
		}
	    }
	} catch (UserHandledException ex) {
	    logger.error("Error occurred while DIGITAL_CONSENT Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.DIGITAL_CONSENT_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Error occurred while DIGITAL_CONSENT Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.DIGITAL_CONSENT_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	}
	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("DIGITAL_CONSENT document for transactionId {} took {} miliseconds ", transactionId, processedTime);
    }
}
