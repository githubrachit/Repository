package com.mli.mpro.document.service.impl;

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

/**
 * @author manish on 06/04/20
 */
@Component("ovdDocument")
@EnableAsync
public class OvdDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(OvdDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    protected DocumentHelper documentHelper;

    protected String finalProcessedHtml = "ovd\\main";

    protected String documentName = "OVD";

    protected String S3documentName = AppConstants.OVD;

    protected String S3documentID = AppConstants.OVD_DOCUMENT_ID;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        try{
             finalProcessedHtml = springTemplateEngine.process("ovd\\main", new Context());
        }catch (Exception e) {
            logger.error("Error occurred while OVD Document generation:", e);
        }
       generatePdfDocument(proposalDetails);
    }

    protected void generatePdfDocument(ProposalDetails proposalDetails){
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
        DocumentStatusDetails documentStatusDetails = null;
        logger.info("Starting {} document creation for transactionId {}.",documentName, transactionId);

        try {

            logger.info("Generating {} Document for transactionId {}... ",documentName, transactionId);
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

            if (!encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, S3documentName);
                List<DocumentRequestInfo> documentPayload = new ArrayList<>();
                documentPayload.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), transactionId,
                        S3documentID, S3documentName, documentPayload);

                String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

                if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
                    logger.info("Failure! {} Document is not uploaded to S3 for transactionId {} {}",documentName, transactionId, S3documentName);
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.DOCUMENT_UPLOAD_FAILED,S3documentName);
                }
                else
                {
                    logger.info("{} Document is successfully generated and uploaded to S3 for transactionId {} {}",documentName, transactionId, S3documentName);
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,documentUploadStatus,S3documentName);
                }
            }
            else
            {
                logger.info("{} Document generation is failed so updating in DB for transactionId {}",documentName, transactionId);
                documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.DOCUMENT_GENERATION_FAILED,S3documentName);
            }

        } catch (Exception e) {
            logger.error("Error occurred while OVD Document generation:", e);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(true,proposalDetails, transactionId,AppConstants.TECHNICAL_FAILURE,S3documentName);

        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("{} document for transactionId {} took {} milliseconds ",documentName, proposalDetails.getTransactionId(), processedTime);
    }
}
