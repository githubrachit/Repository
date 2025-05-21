package com.mli.mpro.document.service.impl;

import com.mli.mpro.document.mapper.NeoAutoCancellationDataMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Objects;

/**
 * @author manish on 12/07/22
 */
@Component("autoCancellationDocument")
@EnableAsync
public class NeoAutoCancellationDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoAutoCancellationDocument.class);

    protected String templateName = "neo\\autoCancellationDocument.html";
    protected String autoCancellationDocumentString = "Auto Cancellation Document";

    private final SpringTemplateEngine springTemplateEngine;
    private final DocumentHelper documentHelper;
    private final NeoAutoCancellationDataMapper neoAutoCancellationDataMapper;

    @Autowired
    public NeoAutoCancellationDocument(SpringTemplateEngine springTemplateEngine, DocumentHelper documentHelper,
                                       NeoAutoCancellationDataMapper neoAutoCancellationDataMapper) {
        this.springTemplateEngine = springTemplateEngine;
        this.documentHelper = documentHelper;
        this.neoAutoCancellationDataMapper = neoAutoCancellationDataMapper;
    }

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        if (Objects.isNull(proposalDetails)) {
            logger.error("{} Proposal object cannot be null for Auto Cancellation Document Generation", autoCancellationDocumentString);
            return;
        }
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        DocumentStatusDetails documentStatusDetails;
        try {
            logger.info("{} generation is initiated for transactionId {}", autoCancellationDocumentString, transactionId);
            Context autoCancellationDocumentContext = neoAutoCancellationDataMapper.setDocumentData(proposalDetails);
            String finalProcessedHtml = springTemplateEngine.process(templateName, autoCancellationDocumentContext);
            String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, retryCount);
            if (AppConstants.FAILED.equalsIgnoreCase(encodedString)) {
                logger.info("{} generation is failed so updating in DB for transactionId {}", autoCancellationDocumentString, transactionId);
                documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId,
                        AppConstants.DOCUMENT_GENERATION_FAILED, AppConstants.AUTO_CANCELLATION_DOCUMENT);
            } else {
                logger.info("{} successfully generated for transactionId {}", autoCancellationDocumentString, transactionId);
                documentStatusDetails = documentHelper.saveGeneratedDocumentToS3(proposalDetails, encodedString, retryCount,
                        AppConstants.AUTO_CANCELLATION_DOCUMENT_PDF, AppConstants.AUTO_CANCELLATION_DOCUMENT);
            }
        }  catch (Exception ex) {
            logger.error("{} generation failed for transactionId {}:", autoCancellationDocumentString, transactionId, ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.TECHNICAL_FAILURE,
                    AppConstants.AUTO_CANCELLATION_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("{} generation for transactionId {} took {} milliseconds ", autoCancellationDocumentString, transactionId, processedTime);
    }
}
