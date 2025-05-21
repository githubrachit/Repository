package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.CkycMapper;
import com.mli.mpro.document.mapper.EkycMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SourcingDetails;
import com.mli.mpro.utils.Utility;

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
import java.util.Optional;

@Component("ekycDocument")
@EnableAsync
public class EkycDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(com.mli.mpro.document.service.impl.EkycDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private EkycMapper ekycMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Starting EKYC document creation.");
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        String channel = proposalDetails.getChannelDetails().getChannel();
        int retryCount = 0;
        String documentUploadStatus = "";
		String agentId = Optional.ofNullable(proposalDetails.getSourcingDetails()).map(SourcingDetails::getAgentId)
				.orElse("");
        try {
            Context ekycDetailsContext = ekycMapper.setDataOfEkycDocument(proposalDetails);
            String finalProcessedHtml = springTemplateEngine.process("ekyc\\ekyc", ekycDetailsContext);
            logger.info("Generating EKYC Document... ");

            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

            logger.info("{} created and uploaded to S3 for transaction Id {} ", documentUploadStatus, proposalDetails.getTransactionId());
            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                // update the document generation failure status in db
                logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
				documentStatusDetails = new DocumentStatusDetails(transactionId,
						proposalDetails.getApplicationDetails().getPolicyNumber(), agentId,
						AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.EKYC_DOCUMENT,
						proposalDetails.getApplicationDetails().getStage());
            } else {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.EKYC_DOCUMENT);
                List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                documentpayload.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(channel, transactionId, AppConstants.EKYC_DOCUMENTID, AppConstants.EKYC_DOCUMENT,
                        documentpayload);
                documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
				if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
					// update the document upload failure status in db
					documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
							proposalDetails.getApplicationDetails().getPolicyNumber(), agentId,
							AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.EKYC_DOCUMENT,
							proposalDetails.getApplicationDetails().getStage());
				} else {
					logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}",
							transactionId, AppConstants.EKYC_DOCUMENT);
					// update the document upload success status in db
					documentStatusDetails = new DocumentStatusDetails(transactionId,
							proposalDetails.getApplicationDetails().getPolicyNumber(), agentId, documentUploadStatus, 0,
							AppConstants.EKYC_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
				}
            }
        } catch (UserHandledException ex) {
            logger.error("Error occurred while EKYC Form Document generation: {}", Utility.getExceptionAsString(ex));
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
            		agentId, AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.EKYC_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        } catch (Exception ex) {
            logger.error("Unknown error occurred while EKYC Form Document generation: {}", Utility.getExceptionAsString(ex));
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
            		agentId, AppConstants.TECHNICAL_FAILURE, 0, AppConstants.EKYC_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("CKYC document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }
}
