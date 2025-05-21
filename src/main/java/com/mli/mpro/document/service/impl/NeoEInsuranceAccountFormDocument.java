package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.EInsuranceAccountFormMapper;
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

@Component("neoEInsuranceAccountFormDocument")
public class NeoEInsuranceAccountFormDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoEInsuranceAccountFormDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private EInsuranceAccountFormMapper eInsuranceAccountFormMapper;

    /**
     * Method to Generate EIA document
     */
    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Starting EIA document creation with Neo implementaion.");
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        String documentUploadStatus = "";
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        boolean isNeoOrAggregator = true;

        try {
            Context eiaDetailsContext = eInsuranceAccountFormMapper.setDataOfEiaDocument(proposalDetails);
            String finalProcessedHtml = "";
            //NEORW: If request from NEO or Aggregator then NEO-EIA template will be populate
            finalProcessedHtml = springTemplateEngine.process("eia\\neo_main", eiaDetailsContext);

            logger.info("Generating EIA Document... ");
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                // update the document generation failure status in db
                logger.info("EIA Document generation is failed so updating in DB for transactionId {}", transactionId);
                documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.DOCUMENT_GENERATION_FAILED,AppConstants.EIA_DOCUMENT);
            }

            else {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.EIA_DOCUMENT);
                List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                documentpayload.add(documentRequestInfo);
                DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), transactionId,
                        AppConstants.EIA_DOCUMENTID, AppConstants.EIA_DOCUMENT, documentpayload);
                documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
                if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
                    // update the document upload failure status in db
                    logger.info("Failure! EIA Document is not uploaded to S3 for transactionId {} {}", transactionId, AppConstants.EIA_DOCUMENT);
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.DOCUMENT_UPLOAD_FAILED,AppConstants.EIA_DOCUMENT);
                } else {
                    logger.info("EIA Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.EIA_DOCUMENT);
                    // update the document upload success status in db
                    documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,documentUploadStatus,AppConstants.EIA_DOCUMENT);

                }
            }

        } catch (UserHandledException ex) {
            logger.error("Error occurred while EIA Form Document generation:", ex);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.DATA_MISSING_FAILURE,AppConstants.EIA_DOCUMENT);
        } catch (Exception ex) {
            logger.error("Error occurred while EIA Form Document generation:", ex);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.TECHNICAL_FAILURE,AppConstants.EIA_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("EIA document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

}
