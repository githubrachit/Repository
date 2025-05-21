package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

@Component("annexureDocument")
@EnableAsync
public class AnnexureMedicalQuesDetailsDocument implements DocumentGenerationservice {


    private static final Logger logger = LoggerFactory.getLogger(AnnexureMedicalQuesDetailsDocument.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    protected AnnexureMedicalQuesDetailsMapper annexureMedicalDetailsMapper;

    /*
     * This is the main method which executes the process of Annexure
     * document generation by calling necessary methods Here Spring Template Engine
     * is used to bind the data dynamically to the static HTML which is stored in
     * templates folder.
     */
    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {

        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        boolean isSsp =   AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(Utility.getProductId(proposalDetails));

        if (isSsp && proposalDetails.getAdditionalFlags() != null && ("Y").equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsSspSwissReCase())) {
            generateAnnexureDocument(proposalDetails, retryCount,
                    requestedTime);
        }else {
            logger.info("Annexure Medical document for transactionId {} not required to generate ",
                    proposalDetails.getTransactionId());
        }
    }

    private void generateAnnexureDocument(ProposalDetails proposalDetails, int retryCount,
                                       long requestedTime) {
        long transactionId = proposalDetails.getTransactionId();
        String channelName = proposalDetails.getChannelDetails().getChannel();
        DocumentStatusDetails documentStatusDetails = null;
        String documentStatusUpload = null;
        String processedHtmlForm = null;
        try {
            Context annexureContext = annexureMedicalDetailsMapper.setAnnexureData(proposalDetails);

            processedHtmlForm = springTemplateEngine.process("AnnexureMedicalDetails", annexureContext);
            logger.info("Data binding with annexure HTML is done for transactionId {}", transactionId);

            String encodedStringForm = documentHelper.generatePDFDocument(processedHtmlForm, retryCount);
            logger.info("Annexure HTML to pdf conversation is done for transactionId {}", transactionId);
            if (encodedStringForm.equalsIgnoreCase(AppConstants.FAILED)) {
                documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(),

                        AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.ANNEXURE_MEDICAL,
                        proposalDetails.getApplicationDetails().getStage());
                logger.info("Annexure Document generation is failed so updating in DB for transactionId {}",
                        transactionId);
            } else {
                DocumentRequestInfo documentRequest = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                        encodedStringForm, AppConstants.ANNEXURE_MEDICAL);
                List<DocumentRequestInfo> documentpayload = new ArrayList<>();
                documentpayload.add(documentRequest);
                DocumentDetails documentDetail = new DocumentDetails(channelName,
                        proposalDetails.getTransactionId(), "ANNEXURE_Medical", AppConstants.ANNEXURE_MEDICAL,
                        documentpayload);
                documentStatusUpload = documentHelper.executeSaveDocumentToS3(documentDetail, retryCount);
                if (documentStatusUpload.equalsIgnoreCase("FAILED")) {

                    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                            proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED,
                            0, AppConstants.ANNEXURE_MEDICAL, proposalDetails.getApplicationDetails().getStage());
                    logger.info("Annexure Document upload is failed for transactionId {} {}", transactionId,
                            AppConstants.ANNEXURE_MEDICAL);
                } else {
                    documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                            proposalDetails.getApplicationDetails().getPolicyNumber(),
                            proposalDetails.getSourcingDetails().getAgentId(), documentStatusUpload, 0,
                            AppConstants.ANNEXURE_MEDICAL, proposalDetails.getApplicationDetails().getStage());
                    logger.info(
                            "Annexure Document is successfully generated and uploaded to S3 for transactionId {} {}",
                            transactionId, AppConstants.ANNEXURE_MEDICAL);
                }
            }
        } catch (UserHandledException ex) {
            logger.error("Annexure Medical Document generation failed : {}", Utility.getExceptionAsString(ex));
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0,
                    AppConstants.ANNEXURE_MEDICAL, proposalDetails.getApplicationDetails().getStage());
        } catch (Exception ex) {
            logger.error("Annexure Medical Document generation failed :{}", Utility.getExceptionAsString(ex));
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
                    AppConstants.ANNEXURE_MEDICAL, proposalDetails.getApplicationDetails().getStage());
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Annexure Medical document for transactionId {} took {} miliseconds ",
                proposalDetails.getTransactionId(), processedTime);
    }
}
