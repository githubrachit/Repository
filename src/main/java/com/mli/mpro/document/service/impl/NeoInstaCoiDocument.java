package com.mli.mpro.document.service.impl;


import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.InstaCoiDocumentMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

@Component("instaCoiDocument")
public class NeoInstaCoiDocument implements DocumentGenerationservice {
    private static final Logger logger = LoggerFactory.getLogger(com.mli.mpro.document.service.impl.NeoInstaCoiDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    InstaCoiDocumentMapper instaCoiDocumentMapper;

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {

        logger.info("Starting Neo Insta Coi Document Creation.");

        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        String channel = proposalDetails.getChannelDetails().getChannel();
        int retryCount = 0;
        String documentUploadStatus = "";

        try{
            Context instaCoiDocumentContext = instaCoiDocumentMapper.setDocumentData(proposalDetails);
            String finalProcessedHtml = springTemplateEngine.process("neo\\instaCoiDocument.html", instaCoiDocumentContext);
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
            if(encodedString.equalsIgnoreCase(AppConstants.FAILED)){
                logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED,
                        0, AppConstants.INSTA_COI_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
            }else {
                documentStatusDetails = saveGeneratedDocumentToS3(proposalDetails, encodedString, 0);
            }
        }catch (UserHandledException ex) {
            logger.error("instaCoi Document generation failed for equoteNumber {}, transactionId {}:",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DATA_MISSING_FAILURE, AppConstants.INSTA_COI_DOCUMENT);
        } catch (Exception ex) {
            logger.error("instaCoi Document generation failed for equoteNumber {}, transactionId {}:",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.TECHNICAL_FAILURE, AppConstants.INSTA_COI_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("InstaCoi document is generated for equoteNumber {}, transactionId {} took {} milliseconds ",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), processedTime);
    }
    protected DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails, String pdfDocumentOrDocumentStatus, int retryCount) {
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
                AppConstants.DOCUMENT_TYPE,
                pdfDocumentOrDocumentStatus,
                AppConstants.INSTA_COI_DOCUMENT);
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        documentPayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(), AppConstants.INSTA_COI_DOCUMENT_PDF, AppConstants.INSTA_COI_DOCUMENT, documentPayload);

        String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
        if (AppConstants.FAILED.equalsIgnoreCase(documentUploadStatus)) {
            // update the document upload failure status in DB
            logger.info("instaCoi Document upload is failed for transactionId {} {}", proposalDetails.getTransactionId(), AppConstants.INSTA_COI_DOCUMENT);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.INSTA_COI_DOCUMENT);
        } else {
            // update the document upload success status in DB
            logger.info("instaCoi Document is successfully uploaded to S3 for transactionId {} {}", proposalDetails.getTransactionId(),
                    AppConstants.INSTA_COI_DOCUMENT);
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    documentUploadStatus, AppConstants.INSTA_COI_DOCUMENT);
        }
    }
}
