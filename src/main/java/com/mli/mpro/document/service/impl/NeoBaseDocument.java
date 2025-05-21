package com.mli.mpro.document.service.impl;

import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class NeoBaseDocument {

    private static final Logger logger = LoggerFactory.getLogger(NeoBaseDocument.class);

    @Autowired
    protected SpringTemplateEngine springTemplateEngine;

    @Autowired
    protected DocumentHelper documentHelper;

    protected String processedHtml;

    protected String encodedString;

    protected void generateBaseDoc(ProposalDetails proposalDetails, Context context,long transactionId, int retryCount,long requestedTime, String template){
        processedHtml = springTemplateEngine.process(template, context);
        logger.info("Data binding with HTML is done for transactionId {}", transactionId);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Data binding with HTML for transactionId {} took {} miliseconds ", transactionId,
                processedTime);
        encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
        logger.info("HTML to pdf conversation is done for transactionId {}", transactionId);
        processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId,
                processedTime);
    }

    public String getDocumentBase64String(ProposalDetails proposalDetails, Context proposalFormDetailsContext,
                                          String documentString, String templateName,
                                          SpringTemplateEngine springTemplateEngine, DocumentHelper documentHelper) {
        String processedHtmlProposalForm = springTemplateEngine.process(templateName, proposalFormDetailsContext);
        logger.info("Generating document {} for transactionId {}", documentString, proposalDetails.getTransactionId());
        return documentHelper.generatePDFDocument(processedHtmlProposalForm, 0);
    }
}
