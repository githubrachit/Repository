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

@Component("cibilDocument")
@EnableAsync
public class CibilDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(CibilDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private CibilOrCrifMapper cibilOrCrifMapper;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Starting CIBIL document creation.");
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails = null;

        try {
            Context cibilDetailsContext = cibilOrCrifMapper.setCibilData(proposalDetails);
            String finalProcessedHtml = springTemplateEngine.process("cibil\\main", cibilDetailsContext);

            logger.info("Generating CIBIL Document... ");
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

            if (!encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.CIBIL_DOCUMENT);
                List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                documentpayload.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), transactionId,
                        AppConstants.CIBIL_DOCUMENTID, AppConstants.CIBIL_DOCUMENT, documentpayload);
                String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

                if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
                    logger.info("Failure! CIBIL Document is not uploaded to S3 for transactionId {} {}", transactionId, AppConstants.CIBIL_DOCUMENT);
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.DOCUMENT_UPLOAD_FAILED,AppConstants.CIBIL_DOCUMENT);
                }
                else
                {
                	logger.info("CIBIL Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.CIBIL_DOCUMENT);
                    // update the document upload success status in db
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,documentUploadStatus,AppConstants.CIBIL_DOCUMENT);
                }
            }
            else
            {
            	 logger.info("CIBIL Document generation is failed so updating in DB for transactionId {}", transactionId);
            	 documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.DOCUMENT_GENERATION_FAILED,AppConstants.CIBIL_DOCUMENT);
            }
            	
        } catch (Exception e) {
            logger.error("Error occurred while CIBIL Document generation:", e);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.TECHNICAL_FAILURE,AppConstants.CIBIL_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("CIBIL document for transactionId {} took {} miliseconds ", transactionId, processedTime);
    }
}
