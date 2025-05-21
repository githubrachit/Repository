package com.mli.mpro.document.service.impl;

import com.mli.mpro.document.mapper.CibilOrCrifMapper;
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

@Component("crifDocument")
@EnableAsync
public class CrifDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(CrifDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private CibilOrCrifMapper cibilOrCrifMapper;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Starting CRIF document creation.");
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails = null;

        try {
            Context cibilDetailsContext = cibilOrCrifMapper.setCrifData(proposalDetails);
            String finalProcessedHtml = springTemplateEngine.process("crif\\main", cibilDetailsContext);

            logger.info("Generating CRIF Document... ");
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

            if (!encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.CRIF_DOCUMENT);
                List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                documentpayload.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), transactionId,
                        AppConstants.CRIF_DOCUMENTID, AppConstants.CRIF_DOCUMENT, documentpayload);

                String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

                if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
                    logger.info("Failure! CRIF Document is not uploaded to S3 for transactionId {} {}", transactionId, AppConstants.CRIF_DOCUMENT);
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.DOCUMENT_UPLOAD_FAILED,AppConstants.CRIF_DOCUMENT);
                }
                else
                {
                	logger.info("CRIF Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.CRIF_DOCUMENT);
                    // update the document upload success status in db
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,documentUploadStatus,AppConstants.CRIF_DOCUMENT);
                }
            }
            else
            {
            	 logger.info("CRIF Document generation is failed so updating in DB for transactionId {}", transactionId);
            	 documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.DOCUMENT_GENERATION_FAILED,AppConstants.CRIF_DOCUMENT);
            }

        } catch (Exception e) {
            logger.error("Error occurred while CRIF Document generation:", e);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.TECHNICAL_FAILURE,AppConstants.CRIF_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("CRIF document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }
}
