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
import com.mli.mpro.document.mapper.MwpaMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("mwpaDocument")
@EnableAsync
public class MwpaDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(MwpaDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    MwpaMapper mwpaMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
	logger.info("Starting FORM60 document creation.");
	int retryCount = 0;
	String documentUploadStatus = "";
	DocumentStatusDetails documentStatusDetails = null;
	long requestedTime = System.currentTimeMillis();
	long transactionId = proposalDetails.getTransactionId();
	try {
	    String mwpaProcessedHtml = "";
	    Context mwpaDetailsContext = mwpaMapper.setDataOfMwpaDocument(proposalDetails);
	    
	    if (StringUtils.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance(), AppConstants.OBJECTIVETYPE_MWPA)) {
		if (StringUtils.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getGender(), "F")) {
		    mwpaProcessedHtml = springTemplateEngine.process("mwpa\\section5", mwpaDetailsContext);
		} else {
		    mwpaProcessedHtml = springTemplateEngine.process("mwpa\\section6", mwpaDetailsContext);
		}
	    }

	    logger.info("Generating MwpaDocument Document... ");

	    String pdfDocumentOrDocumentStatus = documentHelper.generatePDFDocument(mwpaProcessedHtml, 0);
	    if (pdfDocumentOrDocumentStatus.equalsIgnoreCase(AppConstants.FAILED)) {
		// update the document generation failure status in db
		logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
		documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
			proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.MWPA_DOCUMENT,
			proposalDetails.getApplicationDetails().getStage());
	    } else {
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, pdfDocumentOrDocumentStatus,
			AppConstants.MWPA_DOCUMENT);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
			AppConstants.MWPA_DOCUMENTID, AppConstants.MWPA_DOCUMENT, documentpayload);
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
		    // update the document upload failure status in db
		    logger.info("MWPA Document upload is failed for transactionId {} {}", transactionId, AppConstants.MWPA_DOCUMENT);
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
			    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.MWPA_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		} else {
		    logger.info("MWPA Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
			    AppConstants.MWPA_DOCUMENT);
		    // update the document upload success status in db
		    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
			    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus,
			    0, AppConstants.MWPA_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
		}
	    }

	} catch (UserHandledException ex) {
	    logger.error("Error occurred while MWPA Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.FORM60_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	} catch (Exception ex) {
	    logger.error("Error occurred while MWPA Document generation:", ex);
	    documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
		    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.FORM60_DOCUMENT,
		    proposalDetails.getApplicationDetails().getStage());
	}

	documentHelper.updateDocumentStatus(documentStatusDetails);
	long processedTime = (System.currentTimeMillis() - requestedTime);
	logger.info("MWPA document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }
}
