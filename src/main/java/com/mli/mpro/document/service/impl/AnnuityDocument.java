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
import com.mli.mpro.document.mapper.AnnuityDetailsMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("annuityDocument")
@EnableAsync
public class AnnuityDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(AnnuityDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private AnnuityDetailsMapper annuityDetailsMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
	logger.info("Starting Annuity document creation.");
	long requestedTime = System.currentTimeMillis();
	int retryCount = 0;
	String documentUploadStatus = "";
	DocumentStatusDetails documentStatusDetails = null;
	long transactionId = proposalDetails.getTransactionId();
	try {
	    if (StringUtils.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductName(),
		    AppConstants.FOREVER_YOUNG_PENSION_PLAN)) {
		Context annuityDetailsContext = annuityDetailsMapper.setDataOfAnnuityDocument(proposalDetails);
		String finalProcessedHtml = springTemplateEngine.process("ulip\\annuityDetails", annuityDetailsContext);

		String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

		if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
		    // update the document generation failure status in db
		    logger.info("Form60 Document generation is failed so updating in DB for transactionId {}", transactionId);
		    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
			    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.ANNUITY_DOCUMENTNAME,
			    proposalDetails.getApplicationDetails().getStage());
		} else {
		    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
			    AppConstants.ANNUITY_DOCUMENTNAME);
		    List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		    documentpayload.add(documentRequestInfo);
		    DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
			    AppConstants.ANNUITY_DOCUMENTID, AppConstants.ANNUITY_DOCUMENTNAME, documentpayload);

		    documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

		    if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
			// update the document upload failure status in db
			logger.info("Failure! Annuity Document is not uploaded to S3 for transactionId {} {}", transactionId,
				AppConstants.ANNUITY_DOCUMENTNAME);
			documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
				proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.ANNUITY_DOCUMENTNAME,
				proposalDetails.getApplicationDetails().getStage());
		    } else {
			logger.info("Annuity Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
				AppConstants.ANNUITY_DOCUMENTNAME);
			// update the document upload success status in db
			documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
				proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.ANNUITY_DOCUMENTNAME,
				proposalDetails.getApplicationDetails().getStage());
		    }
		}
	    } else {
		logger.info("AnnuityProductName is not " + AppConstants.FOREVER_YOUNG_PENSION_PLAN);
	    }

	} catch (UserHandledException ex) {
	    logger.error("Error occurred while Annuity Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, "Annuity",
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Error occurred while Annuity Form Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, "Annuity",
		    proposalDetails.getApplicationDetails().getStage());
	}
	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("Annuity document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }
}