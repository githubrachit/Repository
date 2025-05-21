package com.mli.mpro.document.service.impl;

import com.mli.mpro.document.mapper.EbccMapper;
import com.mli.mpro.email.repository.EmailRepository;
import com.mli.mpro.proposal.models.JourneyFieldsModificationStatus;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("ebccDocument")
@EnableAsync
public class EbccDocument implements DocumentGenerationservice {

  private static final Logger logger = LoggerFactory.getLogger(EbccDocument.class);
  private  final String docUploadmsg = "Document is successfully generated and uploaded to S3 for transactionId {} {}";
  private  final String loggerSuccessmsg = "EBCC Document is successfully generated and uploaded to S3 for transactionId {} {}";
  private  final String loggerFailedmsg = "EBCC Document generation is failed so updating in DB for transactionId {} {}";
  @Autowired
  EbccMapper ebccMapper;
  @Autowired
  private SpringTemplateEngine springTemplateEngine;
  @Autowired
  private DocumentHelper documentHelper;
  @Autowired
  private EmailRepository emailRepo;

  @Override
  @Async
  public void generateDocument(ProposalDetails proposalDetails) {
    logger.info("Request to generate EBCC document is recieved for transactionId {} ",
        proposalDetails.getTransactionId());
    try {
      boolean eBCCDocStatus = proposalDetails.getAdditionalFlags().getEbcc();
      if (eBCCDocStatus) {
        generateDocTiles(proposalDetails);
      }
    } catch (Exception ex) {
      logger.error("Failed to creat PDF for document {} and transactionId {}", AppConstants.EBCC,
          proposalDetails.getTransactionId());
      ex.printStackTrace();
    }
  }

  private void generateDocTiles(ProposalDetails proposalDetails)
      throws UserHandledException {
    logger.info("EBCC Document ");
    DocumentStatusDetails documentStatusDetails = null;
    logger.info("panFlag is not avaliable, generating Form60");
    Context ebccDetailsContext = ebccMapper.setDataOfEbccDocument(proposalDetails);
    String finalProcessedHtml = springTemplateEngine.process("ebcc\\main", ebccDetailsContext);

    String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
    logger.info("Generating EBCC Document... ");
    if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
      // update the document generation failure status in db
      logger.info("Document generation is failed so updating in DB for transactionId {}",
          proposalDetails.getTransactionId());
      documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
          proposalDetails.getApplicationDetails().getPolicyNumber(),
          proposalDetails.getSourcingDetails().getAgentId(),
          AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.EBCC_DOCUMENT,
          proposalDetails.getApplicationDetails().getStage());
      documentHelper.updateDocumentStatus(documentStatusDetails);
    } else {
      generateEbccDocs(encodedString, proposalDetails);
    }
  }

  private void generateEbccDocs(String base64, ProposalDetails proposalDetails) {
    logger.info("Generating EBCC Document...");
    JourneyFieldsModificationStatus fieldsModificationStatus = proposalDetails.getAdditionalFlags()
        .getJourneyFieldsModificationStatus();
    String documentUploadStatus = "";
    DocumentStatusDetails documentStatusDetails = null;
    int retryCount = 0;

    if (fieldsModificationStatus.getCommunicationAddStatus()
        .equalsIgnoreCase(AppConstants.FIELD_NOT_MODIFIED)) {
      String docName = "Communication Address Proof";
      DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
          base64, docName);
      List<DocumentRequestInfo> documentpayload = new ArrayList<>();
      documentpayload.add(documentRequestInfo);
      DocumentDetails documentDetails = new DocumentDetails(
          proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
          "Comm_Add_Pr", docName, documentpayload);
      documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
      documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
      if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
        // update the document upload failure status in db
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(),
            AppConstants.DOCUMENT_UPLOAD_FAILED, 0, documentRequestInfo.getDocumentName(),
            proposalDetails.getApplicationDetails().getStage());
        logger.info(loggerFailedmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
      } else {
        logger.info(docUploadmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
        // update the document upload success status in db
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
            documentRequestInfo.getDocumentName(),
            proposalDetails.getApplicationDetails().getStage());
        logger.info(loggerSuccessmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
      }
      documentHelper.updateDocumentStatus(documentStatusDetails);

    }
    if (fieldsModificationStatus.getDobStatus()
        .equalsIgnoreCase(AppConstants.FIELD_NOT_MODIFIED)) {

      DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
          base64, "Identity Proof or DOB proof- Proposer");
      List<DocumentRequestInfo> documentpayload = new ArrayList<>();
      documentpayload.add(documentRequestInfo);
      DocumentDetails documentDetails = new DocumentDetails(
          proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
          "ID_Pr", "Identity Proof or DOB proof- Proposer", documentpayload);
      documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
      documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
      if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
        // update the document upload failure status in db
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(),
            AppConstants.DOCUMENT_UPLOAD_FAILED, 0, documentRequestInfo.getDocumentName(),
            proposalDetails.getApplicationDetails().getStage());
        logger.info(loggerFailedmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
      } else {
        logger.info(docUploadmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
        // update the document upload success status in db
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
            documentRequestInfo.getDocumentName(),
            proposalDetails.getApplicationDetails().getStage());
        logger.info(loggerSuccessmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
      }
      documentHelper.updateDocumentStatus(documentStatusDetails);

    }
    if (fieldsModificationStatus.getPanStatus()
        .equalsIgnoreCase(AppConstants.FIELD_NOT_MODIFIED)) {
      DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
          base64, "Copy of PAN card");
      List<DocumentRequestInfo> documentpayload = new ArrayList<>();
      documentpayload.add(documentRequestInfo);
      DocumentDetails documentDetails = new DocumentDetails(
          proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
          "PAN_F60_Pr", "Copy of PAN card", documentpayload);
      documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
      documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
      if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
        // update the document upload failure status in db
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(),
            AppConstants.DOCUMENT_UPLOAD_FAILED, 0, documentRequestInfo.getDocumentName(),
            proposalDetails.getApplicationDetails().getStage());
        logger.info(loggerFailedmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
      } else {
        logger.info(docUploadmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
        // update the document upload success status in db
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
            documentRequestInfo.getDocumentName(),
            proposalDetails.getApplicationDetails().getStage());
        logger.info(loggerSuccessmsg, proposalDetails.getTransactionId(),
            documentRequestInfo.getDocumentName());
      }
      documentHelper.updateDocumentStatus(documentStatusDetails);

    }
  }
}

