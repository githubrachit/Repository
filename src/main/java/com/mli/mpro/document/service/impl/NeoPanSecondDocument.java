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
@Component("neoPanSecondDocument")
public class NeoPanSecondDocument extends PanDobDocument {

    private static final Logger logger = LoggerFactory.getLogger(NeoPanSecondDocument.class);

    protected String panDocumentString = "PAN validate document";
    protected String panDocumentProposerTemplate = "neo\\pan\\proposerpandobvalidation";
    protected String documentName = AppConstants.PAN_VALIDATE_DOCUMENT_NAME;
    protected String retryCategory = AppConstants.PAN_2_DOCUMENT;

    protected String mproDocumentId = AppConstants.PAN_VALIDATE_DOCUMENT_ID;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails = null;
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        long transactionId = proposalDetails.getTransactionId();
            try {
                final String finalProcessedHtml = generatePanDocForProposer(proposalDetails);
                String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, retryCount);

                if (AppConstants.FAILED.equalsIgnoreCase(encodedString)) {
                    logger.info("{} generation is failed so updating in DB for transactionId {}", panDocumentString, transactionId);
                    documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId,
                            AppConstants.DOCUMENT_GENERATION_FAILED, retryCategory);
                } else {
                    logger.info("{} successfully generated for transactionId {}", panDocumentString, transactionId);
                    documentStatusDetails = documentHelper.saveGeneratedDocumentToS3(proposalDetails, encodedString, retryCount,
                            mproDocumentId, documentName);
                }

            } catch (UserHandledException ex) {
                logger.error("{} generation failed:", panDocumentString, ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.DATA_MISSING_FAILURE,
                        retryCategory);
            } catch (Exception ex) {
                logger.error("{} generation failed:", panDocumentString, ex);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.TECHNICAL_FAILURE,
                        retryCategory);
            }

        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("{} generation for transactionId {} took {} milliseconds ", panDocumentString, transactionId, processedTime);
        if(documentStatusDetails !=null)
            documentStatusDetails.setDocumentName(retryCategory);
        documentHelper.updateDocumentStatus(documentStatusDetails);

    }

    private String generatePanDocForProposer(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("{} generation is initiated for proposer with transactionId {} and at applicationStage {}", panDocumentString, proposalDetails.getTransactionId(),
                proposalDetails.getApplicationDetails().getStage());
        Context panDetailsContext = panDobMapper.setProposerDataOfPanDobDocument(proposalDetails);
        return springTemplateEngine.process(panDocumentProposerTemplate, panDetailsContext);
    }


}
