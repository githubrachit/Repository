package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.SellerDeclarationDetailsMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component("sellerDeclarationDocument")
public class SellerDeclarationDocument implements DocumentGenerationservice {

  private static final Logger logger = LoggerFactory.getLogger(SellerDeclarationDocument.class);

  @Autowired
  private SpringTemplateEngine springTemplateEngine;

  @Autowired
  private DocumentHelper documentHelper;

  @Autowired
  private SellerDeclarationDetailsMapper sellerDeclarationDetailsMapper;

  @Override
  @Async
  public void generateDocument(ProposalDetails proposalDetails) {
    logger.info("### not needed ");
    return;
  }

  @Override
  public void generateSellerDocument(RequestPayload requestPayload, String channel) {
    ProposalDetails proposalDetails = requestPayload.getProposalDetails();
    logger.info(
        "Initiating seller document generation for transactionId {} policyNumber and for channel {}",
        proposalDetails.getTransactionId(), channel);
    long requestedTime = System.currentTimeMillis();
    DocumentStatusDetails documentStatusDetails = null;
    String documentUploadStatus = "";
    int retryCount = 0;
    long transactionId = proposalDetails.getTransactionId();
    try {
      String finalProcessedHtml = getFinalProcessedHtml(requestPayload);
      String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
      if (AppConstants.FAILED.equalsIgnoreCase(encodedString)) {
        logger.info("Document generation is failed so updating in DB for transactionId {}",
            transactionId);
        documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
            proposalDetails.getApplicationDetails().getPolicyNumber(),
            proposalDetails.getSourcingDetails().getAgentId(),
            AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.SELLER_DECLARATION_DOCUMENT,
            proposalDetails.getApplicationDetails().getStage());
      } else {
        documentUploadStatus = documentHelper
            .executeSaveDocumentToS3(getDocumentDetails(encodedString, proposalDetails),
                retryCount);
        logger.info("Document Upload Status to S3 {} for transaction id {}", documentUploadStatus,
            proposalDetails.getTransactionId());
        documentStatusDetails = getDocumentStatusDetails(documentUploadStatus, transactionId,
            proposalDetails);
        // FUL2-46310 For CPS product, uploading the seller declaration document to secondary policy 
        if (AppConstants.SUCCESS.equalsIgnoreCase(documentUploadStatus) && Utility.isCapitalGuaranteeSolutionPrimaryProduct(proposalDetails)) {
        	 documentUploadStatus = documentHelper
        	            .executeSaveDocumentToS3(getDocumentDetailsForCGSProduct(encodedString, proposalDetails),
        	                retryCount);
        	 logger.info("Document Upload Status to S3 for CGS Product{} for transaction id {}", documentUploadStatus,
        	            proposalDetails.getTransactionId());
        	 documentStatusDetails = getDocumentStatusDetailsForCGSProduct(documentUploadStatus, proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId(),
        	            proposalDetails);
        }
      }
    } catch (Exception ex) {
      logger.error("Getting exception while Seller Document generation={}", ex);
      documentStatusDetails = new DocumentStatusDetails(transactionId,
          proposalDetails.getApplicationDetails().getPolicyNumber(),
          proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
          AppConstants.SELLER_DECLARATION_DOCUMENT,
          proposalDetails.getApplicationDetails().getStage());
    } finally {
      documentHelper.updateDocumentStatus(documentStatusDetails);
      long processedTime = (System.currentTimeMillis() - requestedTime);
      logger.info("Generate SELLER_DOCUMENT for transactionId {} took {} miliseconds ",
          proposalDetails.getTransactionId(), processedTime);
    }
  }

  private String getFinalProcessedHtml(RequestPayload requestPayload) throws UserHandledException {
    Context sellerDataContext = sellerDeclarationDetailsMapper
        .setSellerDeclarationDataDocument(requestPayload);
    return springTemplateEngine
        .process("seller\\sellerDeclaration", sellerDataContext);
  }

  private DocumentDetails getDocumentDetails(String encodedString,
      ProposalDetails proposalDetails) {
    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
        AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.SELLER_DECLARATION_DOCUMENT);
    List<DocumentRequestInfo> documentPayload = new ArrayList<>();
    documentPayload.add(documentRequestInfo);
    DocumentDetails documentDetails= new DocumentDetails(
        proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
        AppConstants.SELLER_DECLARATION_DOCUMENT_ID, AppConstants.SELLER_DECLARATION_DOCUMENT,
        documentPayload);
    documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
    return documentDetails;
  }

  private DocumentStatusDetails getDocumentStatusDetails(String documentUploadStatus,
      long transactionId, ProposalDetails proposalDetails) {
    DocumentStatusDetails documentStatusDetails;
    if (StringUtils.equalsIgnoreCase(documentUploadStatus, AppConstants.FAILED)) {
      logger.info("Seller Document upload is failed for transactionId {}", transactionId);
      documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
          proposalDetails.getApplicationDetails().getPolicyNumber(),
          proposalDetails.getSourcingDetails().getAgentId(),
          AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.SELLER_DECLARATION_DOCUMENT,
          proposalDetails.getApplicationDetails().getStage());
    } else {
      logger.info("Success Document Status Details for transaction Id {}",
          proposalDetails.getTransactionId());
      documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
          proposalDetails.getApplicationDetails().getPolicyNumber(),
          proposalDetails.getSourcingDetails().getAgentId(),
          documentUploadStatus, 0, AppConstants.SELLER_DECLARATION_DOCUMENT,
          proposalDetails.getApplicationDetails().getStage());
    }
    return documentStatusDetails;
  }
  
  private DocumentDetails getDocumentDetailsForCGSProduct(String encodedString,
	      ProposalDetails proposalDetails) {
	    DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(
	        AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.SELLER_DECLARATION_DOCUMENT);
	    List<DocumentRequestInfo> documentPayload = new ArrayList<>();
	    documentPayload.add(documentRequestInfo);
	    DocumentDetails documentDetails= new DocumentDetails(
	        proposalDetails.getChannelDetails().getChannel(), proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId(),
	        AppConstants.SELLER_DECLARATION_DOCUMENT_ID, AppConstants.SELLER_DECLARATION_DOCUMENT,
	        documentPayload);
	    documentDetails.setThanosDolphinIntegrationEnabled(proposalDetails.isThanosDolphinIntegrationEnabled());
	    return documentDetails;
	  }
  
  private DocumentStatusDetails getDocumentStatusDetailsForCGSProduct(String documentUploadStatus,
	      long transactionId, ProposalDetails proposalDetails) {
	    DocumentStatusDetails documentStatusDetails = null;
			logger.info("Success Document Status Details CGS Product for transaction Id {}", transactionId);
	  	      documentStatusDetails = new DocumentStatusDetails(proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId(),
	  	          proposalDetails.getSalesStoriesProductDetails().getSecondaryPolicyNum(),
	  	          proposalDetails.getSourcingDetails().getAgentId(),
	  	          documentUploadStatus, 0, AppConstants.SELLER_DECLARATION_DOCUMENT,
	  	          proposalDetails.getApplicationDetails().getStage());
	    return documentStatusDetails;
	  }
}
