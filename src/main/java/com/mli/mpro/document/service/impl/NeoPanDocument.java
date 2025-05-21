package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

/**
 * @author manish on 18/12/20
 */
@Component("neoPanDocument")
public class NeoPanDocument extends PanDobDocument {

    private static final Logger logger = LoggerFactory.getLogger(NeoPanDocument.class);

    protected String panDocumentString = "PAN validate document";
    protected String panDocumentTemplate = "neo\\pan\\panvalidation";
    protected String documentName = AppConstants.PAN_VALIDATE_DOCUMENT_NAME;
    protected String mproDocumentId = AppConstants.PAN_VALIDATE_DOCUMENT_ID;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails = null;
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();

        try {
            logger.info("{} generation is initiated for transactionId {} and at applicationStage {}", panDocumentString, transactionId,
                    proposalDetails.getApplicationDetails().getStage());

            Context panDetailsContext = panDobMapper.setDataOfPanDobDocument(proposalDetails,proposalDetails.getPartyInformation().get(0).getPartyType());
            String finalProcessedHtml = springTemplateEngine.process(panDocumentTemplate, panDetailsContext);
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, retryCount);

            if (AppConstants.FAILED.equalsIgnoreCase(encodedString)) {
                logger.info("{} generation is failed so updating in DB for transactionId {}", panDocumentString, transactionId);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId,
                        AppConstants.DOCUMENT_GENERATION_FAILED, documentName);
            } else {
                logger.info("{} successfully generated for transactionId {}", panDocumentString, transactionId);
                documentStatusDetails = documentHelper.saveGeneratedDocumentToS3(proposalDetails, encodedString, retryCount,
                        mproDocumentId, documentName);
            }

        } catch (UserHandledException ex) {
            logger.error("{} generation failed:", panDocumentString, ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.DATA_MISSING_FAILURE,
                    documentName);
        } catch (Exception ex) {
            logger.error("{} generation failed:", panDocumentString, ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.TECHNICAL_FAILURE,
                    documentName);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("{} generation for transactionId {} took {} milliseconds ", panDocumentString, transactionId, processedTime);
    }
}
