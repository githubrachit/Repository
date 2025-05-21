package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.Form60Mapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.PanDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PersonalIdentification;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component("form60Document")
@EnableAsync
public class Form60Document implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(Form60Document.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    Form60Mapper form60Mapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("Starting FORM60 document creation.");
        try {
            long requestedTime = System.currentTimeMillis();
            DocumentStatusDetails documentStatusDetails = null;
            long transactionId = proposalDetails.getTransactionId();
            try {
            	//FUL2-41059 Form 60 document should not come for Form C (here we need to consider the company pan only) 
            	Boolean panFlag = false;
            	if(AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType())
                        && !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType())) {
					String pan = proposalDetails.getPartyInformation().stream().filter(Objects::nonNull)
							.filter(partyInfo -> AppConstants.COMPANY.equalsIgnoreCase(partyInfo.getPartyType()))
							.findFirst().map(PartyInformation::getPersonalIdentification)
							.map(PersonalIdentification::getPanDetails).map(PanDetails::getPanNumber).orElse("");
					panFlag = StringUtils.isNoneBlank(pan);
            	}else {
            	  panFlag = StringUtils
                        .isNotBlank(proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber());
            	}
                Boolean annuitantPanFlag = StringUtils
                        .isNotBlank(proposalDetails.getProductDetails().get(0).getProductInfo().getSecondAnnuitantPanNumber());

                //FUL2-17569 GLIP And SPP Product : Pan DOB Document Generation
                boolean isAnnuityProductJointLife = Utility.isAnnuityOptionJointLife(proposalDetails,
                        Stream.of(AppConstants.GLIP_PRODUCT_ID, AppConstants.SPP_ID,AppConstants.SWAGPP));
                if (Boolean.FALSE.equals(panFlag)) {
                    generateForm60Document(proposalDetails, 0, AppConstants.FORM60_DOCUMENT_GEN_MSG_PR,
                            AppConstants.FORM60_DOCUMENT_GEN_FAIL_MSG_PR);
                } else {
                    logger.info("panFlag is available, skipping Form60");
                }
                if (Boolean.FALSE.equals(annuitantPanFlag) && isAnnuityProductJointLife) {
                    generateForm60Document(proposalDetails, 1, AppConstants.FORM60_DOCUMENT_GEN_MSG_IN,
                            AppConstants.FORM60_DOCUMENT_GEN_FAIL_MSG_IN);
                } else {
                    logger.info("panFlag is available, skipping Form60");
                }
            } catch (UserHandledException ex) {
                logger.error("Error occurred while FORM60 Form Document generation:", ex);
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0,
                        AppConstants.FORM60_DOCUMENT,
                        proposalDetails.getApplicationDetails().getStage());
                documentHelper.updateDocumentStatus(documentStatusDetails);
            } catch (Exception ex) {
                logger.error("Error occurred while FORM60 Form Document generation:", ex);
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
                        AppConstants.FORM60_DOCUMENT,
                        proposalDetails.getApplicationDetails().getStage());
                documentHelper.updateDocumentStatus(documentStatusDetails);
            }

            long processedTime = (System.currentTimeMillis() - requestedTime);
            logger.info("Form60 document for transactionId {} took {} miliseconds ",
                    proposalDetails.getTransactionId(), processedTime);
        } catch (Exception ex) {
            logger.error("ERROR while generating Form60:", ex);
        }
    }

    private void generateForm60Document(ProposalDetails proposalDetails, int index, String docGenMessage,
                                        String failureMessage) throws UserHandledException {
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        String documentUploadStatus = "";
        int retryCount = 0;
        logger.info("panFlag is not available, generating Form60");
        Context form60DetailsContext = form60Mapper.setDataOfForm60Document(proposalDetails, index);
        String finalProcessedHtml = springTemplateEngine.process("form60\\main", form60DetailsContext);
        logger.info(docGenMessage, transactionId);
        String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
        String form60documentId = StringUtils.EMPTY;
        String form60DocumentName = StringUtils.EMPTY;

        if (index == 0) {
            form60documentId= AppConstants.FORM60_DOCUMENTID;
            form60DocumentName = AppConstants.FORM60_DOCUMENT;
        } else if (index == 1) {
            form60documentId = AppConstants.FORM60_DOCUMENTID_IR;
            form60DocumentName = AppConstants.FORM60_DOCUMENT_IR;
        }
        if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
            // update the document generation failure status in db
            logger.info("Form60 Document generation is failed so updating in DB for transactionId {}", transactionId);
            documentStatusDetails = new DocumentStatusDetails(transactionId,
                    proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0,
                    form60DocumentName,
                    proposalDetails.getApplicationDetails().getStage());
        } else {
            DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                    encodedString,
                    form60DocumentName);
            List<DocumentRequestInfo> documentpayload = new ArrayList<>();
            documentpayload.add(documentRequestInfo);
            DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
                    proposalDetails.getTransactionId(), form60documentId, form60DocumentName,
                    documentpayload);
            documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
            documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

            if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
                // update the document upload failure status in db
                logger.info(failureMessage, transactionId);
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
                        form60DocumentName,
                        proposalDetails.getApplicationDetails().getStage());
            } else {
                logger.info("Form60 Document is successfully generated and uploaded to S3 for transactionId {} {}",
                        transactionId,
                        form60DocumentName);
                // update the document upload success status in db
                documentStatusDetails = new DocumentStatusDetails(transactionId,
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
                        form60DocumentName,
                        proposalDetails.getApplicationDetails().getStage());
            }
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
    }
}
