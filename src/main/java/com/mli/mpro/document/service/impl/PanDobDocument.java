package com.mli.mpro.document.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.PanDobMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.CibilDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProductInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

/**
 * @author akshom4375
 */
@Component("panDobDocument")
@EnableAsync
public class PanDobDocument implements DocumentGenerationservice {

    public static final String CREDIT_SCORE = "Credit Score";
    private static final Logger logger = LoggerFactory.getLogger(PanDobDocument.class);
    @Autowired
    protected SpringTemplateEngine springTemplateEngine;

    @Autowired
    protected DocumentHelper documentHelper;

    @Autowired
    protected PanDobMapper panDobMapper;


    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        logger.info("Starting PAN DOB document creation for transactionId {} formType {}", transactionId,Utility.getFormType(proposalDetails));
        try {

            List<PartyInformation> proposalPartyInformationList = proposalDetails.getPartyInformation();
            if (!CollectionUtils.isEmpty(proposalPartyInformationList)) {
                PartyInformation partyInformation  = Utility.getPartyInfoWrtFormType(proposalDetails);
                String insuredDObDocStatus = proposalDetails.getAdditionalFlags().getInsuredDOBRequired();
                boolean proposerPanDobFlag = Utility.orTwoExpressions(partyInformation.getPersonalIdentification().getPanDetails().isPanValidated()
                        , partyInformation.getPersonalIdentification().getPanDetails().isDobValidated());
                boolean insuredPanDobFlag = Utility.orTwoExpressions(proposalDetails.getProductDetails().get(0).getProductInfo().isSecondAnnuitantPanValidated()
                        , proposalDetails.getProductDetails().get(0).getProductInfo().isSecondAnnuitantDobValidated());
                CibilDetails cibilDetails = proposalDetails.getUnderwritingServiceDetails().getCibilDetails();
                //FUL2-18649 extract Product Id and annuity Option to check product is Glip dependent case
                //FUL2-17569 GLIP And SPP Product : Pan DOB Document Generation
                boolean isAnnuityProductJointLife = Utility.isAnnuityOptionJointLife(proposalDetails, Stream.of(AppConstants.GLIP_PRODUCT_ID,AppConstants.SPP_ID,AppConstants.SWAGPP));
                
                if (Utility.orTwoExpressions(proposerPanDobFlag, null != cibilDetails)) {
                    //FUL2-18649  refracting done as some document need to be generated for Proposer and Insured if  Glip dependent case dependent case
                    //Code refactored for form C , Party would be insured in form c else it would be proposer
                    generatePanDobDocument(proposalDetails, cibilDetails, partyInformation.getPartyType(),
                            AppConstants.PANDOB_DOCUMENT_GEN_MSG_PR, AppConstants.PANDOB_DOCUMENT_GEN_FAIL_MSG_PR, isAnnuityProductJointLife, partyInformation);
                }

                //FUL2-18649 Pan DOB Document for Insured when GLIP is Selected with Joint Life Annuity Option
                if (isAnnuityProductJointLife && Utility.orTwoExpressions(insuredPanDobFlag, null != cibilDetails)) {
                    logger.info("Starting PAN DOB document creation for second annuitant of GLIP for transactionId {}", transactionId);
                    //cibilDetails = proposalDetails.getUnderwritingServiceDetails()
                    generatePanDobDocument(proposalDetails, cibilDetails, AppConstants.INSURED, AppConstants.PANDOB_DOCUMENT_GEN_MSG_IN,
                            AppConstants.PANDOB_DOCUMENT_GEN_FAIL_MSG_IN, isAnnuityProductJointLife, partyInformation);
                }

                if (StringUtils.isNotEmpty(insuredDObDocStatus) && AppConstants.CAMEL_YES.equalsIgnoreCase(insuredDObDocStatus)){
                    logger.info("Starting PAN DOB document creation for Insured DOB verified for transactionId {}", transactionId);
                    PartyInformation partyInfo = proposalDetails.getPartyInformation().stream().filter(info -> AppConstants.INSURED.equalsIgnoreCase(info.getPartyType())).findFirst().orElse(null);
                    generatePanDobDocument(proposalDetails, cibilDetails, AppConstants.INSURED, AppConstants.PANDOB_DOCUMENT_GEN_MSG_IN,
                            AppConstants.PANDOB_DOCUMENT_GEN_FAIL_MSG_IN, isAnnuityProductJointLife, partyInfo);
                }


                if (AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType())
                        && !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType())){
                    logger.info("Starting PAN DOB document creation for Authorized Signatory for transactionId {}", transactionId);
                    cibilDetails = proposalDetails.getUnderwritingServiceDetails().getAuthorizeSignatoryCibilDetails();
                     partyInformation = proposalPartyInformationList.stream().filter(partyInfo ->
                                     AppConstants.AUTHORIZED_SIGNATORY.equalsIgnoreCase(partyInfo.getPartyType())).findFirst().orElse(null);
                    generatePanDobDocument(proposalDetails, cibilDetails, AppConstants.AUTHORIZED_SIGNATORY, AppConstants.PANDOB_DOCUMENT_GEN_MSG_AS,
                            AppConstants.PANDOB_DOCUMENT_GEN_FAIL_MSG_AS, isAnnuityProductJointLife, partyInformation);
                }

            }
        } catch (UserHandledException ex) {
            logger.error("Error occurred while PANDOB Form Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PANDOB_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);

        } catch (Exception ex) {
            logger.error("Error occurred while PANDOB Form Document generation:", ex);
            documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PANDOB_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
            documentHelper.updateDocumentStatus(documentStatusDetails);

        }
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("PANDOB created and uploaded to S3 for transaction Id {}, took {} miliseconds", proposalDetails.getTransactionId(), processedTime);

    }

    //FUL2-18649  Method to do all process of generating Pan Dob document of Proposer and also if Insured if GLIP Join life
    private void generatePanDobDocument(ProposalDetails proposalDetails, CibilDetails cibilDetails, String partyType, String docGenMessage,
                                        String failureMessage, boolean isAnnuityProductJointLife, PartyInformation partyInformation) throws UserHandledException {
        long transactionId = proposalDetails.getTransactionId();
        boolean panFlag = false;
        boolean dobFlag = false;
        ProductInfo productInfo = !proposalDetails.getProductDetails().isEmpty() && proposalDetails.getProductDetails().get(0)
                != null && proposalDetails.getProductDetails().get(0).getProductInfo() != null ?
                proposalDetails.getProductDetails().get(0).getProductInfo() : null;
        logger.info("Starting Process of generating Pan Dob document for transaction Id {} for partyType {} formType {} ", transactionId,
                partyType ,Utility.getFormType(proposalDetails));
        if(isAnnuityProductJointLife && AppConstants.INSURED.equalsIgnoreCase(partyType)){
            if(productInfo != null && productInfo.isSecondAnnuitantPanValidated()){
                panFlag = productInfo.isSecondAnnuitantPanValidated();
                dobFlag = productInfo.isSecondAnnuitantDobValidated();
            }
        }else {
            panFlag = partyInformation.getPersonalIdentification().getPanDetails().isPanValidated();
            dobFlag = partyInformation.getPersonalIdentification().getPanDetails().isDobValidated();
        }
        Context panDobDetailsContext = panDobMapper.setDataOfPanDobDocument(proposalDetails, partyType);
        String finalProcessedPanDobDocumentHtml = springTemplateEngine.process("pandob\\pandob", panDobDetailsContext);
        logger.info("Final Processed Pan Dob document  transaction Id {} for partyType {} formType {}", transactionId,partyType,Utility.getFormType(proposalDetails));
        logger.info(docGenMessage, transactionId);
        String docStatus = documentHelper.generatePDFDocument(finalProcessedPanDobDocumentHtml, 0);
        if (AppConstants.FAILED.equalsIgnoreCase(docStatus)) {
            // update the document generation failure status in db
            logger.info(failureMessage, transactionId);
        } else {
            logger.info("Save Document to s3 called for transaction Id {}", proposalDetails.getTransactionId());
            saveDocumentOnS3AndUpdateStatus(proposalDetails, transactionId, panFlag, dobFlag, cibilDetails, docStatus, partyType);
        }
    }

    private void saveDocumentOnS3AndUpdateStatus(ProposalDetails proposalDetails, long transactionId, boolean panFlag, boolean dobFlag,
                                                 CibilDetails cibilDetails, String docStatus, String partyType) {
        String panRequestDocumentName;
        String panActualDocumentName;
        String dobRequestDocumentName;
        String dobActualDocumentName;
        String mproPanDocumentId;
        String mproDobDocumentId;
        String mproCreditScoreDocumentId;
        if (AppConstants.PROPOSER.equalsIgnoreCase(partyType)) {
            dobRequestDocumentName = AppConstants.DOB_DOCUMENT;
            dobActualDocumentName = AppConstants.DOB_DOCUMENT;
            mproPanDocumentId = AppConstants.PAN_DOCUMENTID;
            mproDobDocumentId = AppConstants.DOB_DOCUMENTID;
            panRequestDocumentName = AppConstants.PANDOB_DOCUMENT;
            panActualDocumentName = AppConstants.PANDOB_DOCUMENT;
            mproCreditScoreDocumentId = "CS_Pr";
        } else if (AppConstants.AUTHORIZED_SIGNATORY.equalsIgnoreCase(partyType)) {
            dobRequestDocumentName = AppConstants.DOB_DOCUMENT_AS;
            dobActualDocumentName = AppConstants.DOB_DOCUMENT_AS;
            mproPanDocumentId = AppConstants.PAN_AS;
            mproDobDocumentId = AppConstants.DOB_AS;
            panRequestDocumentName = AppConstants.PAN_DOCUMENT_AS;
            panActualDocumentName = AppConstants.PAN_DOCUMENT_AS;
            mproCreditScoreDocumentId = "";
        }  else if (AppConstants.INSURED.equalsIgnoreCase(partyType) && AppConstants.CAMEL_YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getInsuredDOBRequired())){
            dobRequestDocumentName = AppConstants.DOB_PROOF_IN;
            dobActualDocumentName = AppConstants.DOB_PROOF_IN;
            mproPanDocumentId = AppConstants.PAN_DOCUMENTID_IN;
            mproDobDocumentId = AppConstants.DOB_DOCID_IN;
            panRequestDocumentName = AppConstants.PANDOB_DOCUMENT_IN;
            panActualDocumentName = AppConstants.PANDOB_DOCUMENT_IN;
            mproCreditScoreDocumentId = "";

        }else {
            dobRequestDocumentName = AppConstants.DOB_DOCUMENT_IN;
            dobActualDocumentName = AppConstants.DOB_DOCUMENT_IN;
            mproPanDocumentId = AppConstants.PAN_DOCUMENTID_IN;
            mproDobDocumentId = AppConstants.DOB_DOCUMENTID_IN;
            panRequestDocumentName = AppConstants.PANDOB_DOCUMENT_IN;
            panActualDocumentName = AppConstants.PANDOB_DOCUMENT_IN;
            mproCreditScoreDocumentId = "CS_In";
        }
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        logger.info("Constionally save Document to s3 and Update status called {} ", docStatus);
        if (AppConstants.INSURED.equalsIgnoreCase(partyType) && AppConstants.CAMEL_YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getInsuredDOBRequired())){
            conditionallySaveDocumentOnS3AndUpdateStatus(dobFlag, proposalDetails, transactionId, docStatus, documentPayload, dobRequestDocumentName, dobActualDocumentName, mproDobDocumentId, "Dob Document upload is failed for transactionId {}", "Dob Document is successfully generated and uploaded to S3 for transactionId {}");
        }else {
            conditionallySaveDocumentOnS3AndUpdateStatus(panFlag, proposalDetails, transactionId, docStatus, documentPayload, panRequestDocumentName, panActualDocumentName, mproPanDocumentId, "pan Document upload is failed for transactionId {}", "pan Document is successfully generated and uploaded to S3 for transactionId {}");
            conditionallySaveDocumentOnS3AndUpdateStatus(dobFlag, proposalDetails, transactionId, docStatus, documentPayload, dobRequestDocumentName, dobActualDocumentName, mproDobDocumentId, "Dob Document upload is failed for transactionId {}", "Dob Document is successfully generated and uploaded to S3 for transactionId {}");
            if (!"Authorized Signatory".equalsIgnoreCase(partyType)) {
                conditionallySaveDocumentOnS3AndUpdateStatus(null != cibilDetails, proposalDetails, transactionId, docStatus, documentPayload, AppConstants.DOB_DOCUMENT, CREDIT_SCORE, mproCreditScoreDocumentId, "CS Document upload is failed for transactionId {}", "CS Document is successfully generated and uploaded to S3 for transactionId {}");
            }
        }
    }

    private void conditionallySaveDocumentOnS3AndUpdateStatus(boolean condition,ProposalDetails proposalDetails, long transactionId,
                                                              String actualDocument, List<DocumentRequestInfo> documentPayload, String requestDocumentName,
                                                              String actualDocumentName, String mproDocumentId, String failureMessage, String successMessage) {
       if(condition){
           DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, actualDocument,
                   requestDocumentName);
           documentPayload.add(documentRequestInfo);
           DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
                   mproDocumentId, actualDocumentName, documentPayload);
           documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
           if(AppConstants.CAMEL_YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getInsuredDOBRequired())){
               documentDetails.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
               documentDetails.setPartyType(AppConstants.INSURED);
           }
           if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
               documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
           }
           String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, 0);
           logger.info("Document Upload Status to S3 {} for trsaction id {}", documentUploadStatus, proposalDetails.getTransactionId());
           DocumentStatusDetails documentStatusDetails = getDocumentStatusDetails(proposalDetails, documentUploadStatus,
                   transactionId, failureMessage, actualDocumentName, successMessage);
           documentHelper.updateDocumentStatus(documentStatusDetails);
       }
    }

    private DocumentStatusDetails getDocumentStatusDetails(ProposalDetails proposalDetails, String documentUploadStatus1, long transactionId,
                                                           String failureMessage, String documentName, String successMessage) {
        DocumentStatusDetails documentStatusDetails;
        if (StringUtils.equalsIgnoreCase(documentUploadStatus1, AppConstants.FAILED)) {
            // update the document upload failure status in db
            logger.info(failureMessage, transactionId);
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                    AppConstants.DOCUMENT_UPLOAD_FAILED, 0, documentName,
                    proposalDetails.getApplicationDetails().getStage());
        } else {
            logger.info(successMessage, transactionId);
            // update the document upload success status in db
            logger.info("Success Document Status Details for transaction Id {}", proposalDetails.getTransactionId());
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
                    documentUploadStatus1, 0, documentName, proposalDetails.getApplicationDetails().getStage());
        }
        return documentStatusDetails;
    }

}