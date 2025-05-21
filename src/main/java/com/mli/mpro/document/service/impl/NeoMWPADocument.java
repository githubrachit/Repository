package com.mli.mpro.document.service.impl;

import com.mli.mpro.document.enums.FamilyType;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component("neoMWPADocument")
public class NeoMWPADocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoMWPADocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private DocumentHelper documentHelper;
    @Autowired
    private NeoMWPADocumentMapper neoMWPADocumentMapper;

    private List<String> productTypes = new ArrayList<>();

    @PostConstruct
    void addProductType(){
        productTypes.add("ssp");
        productTypes.add("step");
        productTypes.add("stpp");
    }

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        int retryCount = 0;
        DocumentStatusDetails documentStatusDetails = null;
        Long transactionId = proposalDetails.getTransactionId();
        String channelName = proposalDetails.getChannelDetails().getChannel();
        long requestedTime = System.currentTimeMillis();
        logger.info("NEO MWPA document generation is initiated for transactionId {} and at applicationStage {}",
                transactionId, proposalDetails.getApplicationDetails().getStage());
        try {
                Context mwpaDocumentData = neoMWPADocumentMapper.setMWPADetails(proposalDetails);
                String neoMWPAFinalDocument = springTemplateEngine.process("neo\\mwpa\\mwpaDoc", mwpaDocumentData);
                String encodedStringForm = documentHelper.generatePDFDocument(neoMWPAFinalDocument, retryCount);
                logger.info("MWPA HTML to pdf conversation is done for transactionId {}", transactionId);

                documentStatusDetails = handleDocumentGenerationResponse(encodedStringForm, proposalDetails, channelName);
        } catch (Exception ex) {
            logger.error("Neo MWPA Document generation failed :{}", Utility.getExceptionAsString(ex));
            documentStatusDetails = createDocumentStatusDetails(proposalDetails, AppConstants.TECHNICAL_FAILURE);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Neo MWPA document for transactionId {} took {} miliseconds ",
                proposalDetails.getTransactionId(), processedTime);

    }

    private DocumentStatusDetails handleDocumentGenerationResponse(String encodedStringForm, ProposalDetails proposalDetails, String channelName) {
        Long transactionId = proposalDetails.getTransactionId();

        if (AppConstants.FAILED.equalsIgnoreCase(encodedStringForm)) {
            logger.info("Neo MWPA Document generation failed, updating DB for transactionId {}", transactionId);
            return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_GENERATION_FAILED);
        } else {
            DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                    encodedStringForm, AppConstants.NEO_MWPA_DOCUMENT);
            List<DocumentRequestInfo> documentpayload = new ArrayList<>();
            documentpayload.add(documentRequest);

            DocumentDetails documentDetail = new DocumentDetails(channelName,
                    proposalDetails.getTransactionId(), AppConstants.MWPA_DOCUMENT_ID, AppConstants.NEO_MWPA_DOCUMENT,
                    documentpayload);

            String documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, 0);

            if (AppConstants.FAILED.equalsIgnoreCase(documentStatusUpload)) {
                logger.info("Neo MWPA Document upload failed for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_UPLOAD_FAILED);
            } else {
                logger.info("Neo MWPA Document successfully generated and uploaded to S3 for transactionId {}", transactionId);
                return createDocumentStatusDetails(proposalDetails, documentStatusUpload);
            }
        }
    }

    private DocumentStatusDetails createDocumentStatusDetails(ProposalDetails proposalDetails, String status) {
        return new DocumentStatusDetails(
                proposalDetails.getTransactionId(),
                proposalDetails.getApplicationDetails().getPolicyNumber(),
                proposalDetails.getSourcingDetails().getAgentId(),
                status,
                0,
                AppConstants.NEO_MWPA_DOCUMENT,
                proposalDetails.getApplicationDetails().getStage()
        );
    }
}
