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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.PaymentDataMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("paymentDocument")
@EnableAsync
public class PaymentDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private PaymentDataMapper paymentDocument;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
	logger.info("Starting Payment Receipt document creation.");
	long requestedTime = System.currentTimeMillis();
	DocumentStatusDetails documentStatusDetails = null;
	String documentUploadStatus = "";
	int retryCount = 0;
	long transactionId = proposalDetails.getTransactionId();
	String finalProcessedHtml = null;
	try {
	    String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
	    boolean axisFlag = StringUtils.equalsIgnoreCase(requestSource, "AXIS");

	    Context panDobDetailsContext = paymentDocument.setDataOfPaymentDocument(proposalDetails);
	    if (axisFlag) {
		finalProcessedHtml = springTemplateEngine.process("payment\\axisPayment", panDobDetailsContext);
	    } else {
		finalProcessedHtml = springTemplateEngine.process("payment\\main", panDobDetailsContext);
	    }

	    logger.info("Generating Payment Receipt Document...");

	    String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

	    if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		// update the document generation failure status in db
		logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.PAYMENT_DOCUMENT,
			proposalDetails.getApplicationDetails().getStage());
	    } else {
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.PAYMENT_DOCUMENT);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
			AppConstants.PAYMENT_DOCUMENTID, AppConstants.PAYMENT_DOCUMENT, documentpayload);
				documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
			if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
				documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
			}
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
		    logger.info("PAYMENT_DOCUMENT Document upload to S3 failed");
		    // update the document upload failure status in db
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
			    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.PAYMENT_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		} else {
		    logger.info("PAYMENT_DOCUMENT Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
			    AppConstants.PAYMENT_DOCUMENT);
		    // update the document upload success status in db
		    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.PAYMENT_DOCUMENT,
			    proposalDetails.getApplicationDetails().getStage());
		}
	    }
	} catch (UserHandledException ex) {
	    logger.error("Error occurred while Payment Receipt Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PAYMENT_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	    documentHelper.updateDocumentStatus(documentStatusDetails);
	} catch (Exception ex) {
	    logger.error("Error occurred while Payment Receipt Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PAYMENT_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	    documentHelper.updateDocumentStatus(documentStatusDetails);
	}
	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("Payment Receipt document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

}
