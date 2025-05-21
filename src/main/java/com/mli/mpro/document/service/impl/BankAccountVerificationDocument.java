package com.mli.mpro.document.service.impl;

import java.time.LocalDate;
import java.util.*;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BankDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

/*
 * This document is added for the requirement of Penny Drop CR - FUL2-140549.
 * This document will only be generated when Penny Drop service is success.
 * Document template name - Account_Verification_Document.html
 */

@Component("bankAccountVerificationDocument")
@EnableAsync
public class BankAccountVerificationDocument implements DocumentGenerationservice {

	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	@Autowired
	private DocumentHelper documentHelper;

	private static final Logger logger = LoggerFactory.getLogger(BankAccountVerificationDocument.class);

	private static final String BANK_ACCOUNT_VERIFICATION_DOCUMENT = "Cancelled Cheque with name Required for NEFT- Proposer";
	private static final String DATA_ADDITION_ERROR_MSG = "Data addition failed";
	private static final int RETRY_COUNT = 0;

	/*
	 * This is the main method where Bank Account Verification document is getting  
	 * generated. It internally calls methods of data mapping, binding the data with 
	 * HTML, PDF generation and uploading document to S3 and the methods to save 
	 * success and failure status in DB
	 */
	@Override
	@Async
	public void generateDocument(ProposalDetails proposalDetails) {
		long transactionId = proposalDetails.getTransactionId();
		logger.info("Initiating Bank Account Verification Document generation for transactionId : {}", transactionId);
		long requestedTime = System.currentTimeMillis();
		DocumentStatusDetails documentStatusDetails = null;

		try {
			Context context = setDataForVerificationDocument(proposalDetails);
			String processedHtml = springTemplateEngine.process("Account_Verification_Document", context);

			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info("Data binding with HTML for Bank Account Verification document is done for transactionId : {} , in time : {} , and processed html is : {}", transactionId, processedTime, processedHtml);
			long pdfGenerationStartTime = System.currentTimeMillis();

			String encodedString = documentHelper.generatePDFDocument(processedHtml, RETRY_COUNT);

			processedTime = (System.currentTimeMillis() - pdfGenerationStartTime);
			logger.info("HTML to pdf conversation for Bank Account Verification Document is done for transactionId : {} , in time : {}", transactionId, processedTime);

			if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
				// Document generation failure, updating status in DB
				logger.info(AppConstants.DOCUMENT_GENERATION_FAILED_LOG, transactionId);
				documentStatusDetails = getDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_GENERATION_FAILED);
			} else {
				logger.info("Encoded string for Bank Account Verification Document : {} ", encodedString);
				documentStatusDetails = uploadDocumentToS3AndGetStatus(proposalDetails, encodedString);
			}
			
		} catch (UserHandledException ex) {
			logger.error("Enach Document generation failed:", ex);
			documentStatusDetails = getDocumentStatusDetails(proposalDetails, AppConstants.DATA_MISSING_FAILURE);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		} catch (Exception ex) {
			logger.error("Enach Document generation failed:", ex);
			documentStatusDetails = getDocumentStatusDetails(proposalDetails, AppConstants.TECHNICAL_FAILURE);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		
		documentHelper.updateDocumentStatus(documentStatusDetails);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Bank Account Verification Document for transactionId : {} took time : {} miliseconds ",proposalDetails.getTransactionId(), processedTime);
	}

	/*
	 * @param proposalDetails
	 * @return Context
	 * 
	 * In this method, the data to be included into the document is populated. The
	 * method binds the data fetched from proposalDetails and returns the context
	 * object.
	 */
	private Context setDataForVerificationDocument(ProposalDetails proposalDetails) throws UserHandledException {
		logger.info("Mapping Bank Account Verification data for transactionId {}", proposalDetails.getTransactionId());
		Map<String, Object> accountVerificationDataVeriables = new HashMap<>();
		Context accountVerificationContext = new Context();
		try {
			String date = Utility.dateFormatter(LocalDate.now());
			String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
			String proposerName = proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName() + " "
					+ proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName();
			if(AppConstants.FORM3.equalsIgnoreCase(Utility.getFormType(proposalDetails))) {
				proposerName = proposalDetails.getPartyInformation().get(4).getBasicDetails().getFirstName() + " "
						+ proposalDetails.getPartyInformation().get(4).getBasicDetails().getLastName();
			}
			BankDetails bankDetails = proposalDetails.getBank().getBankDetails().get(0);
			String accountNumber = bankDetails.getBankAccountNumber();
			String micrCode = bankDetails.getMicr();
			String ifscCode = bankDetails.getIfsc();
			String branchName = bankDetails.getBankBranch();

			accountVerificationDataVeriables.put("timeStamp", date);
			accountVerificationDataVeriables.put("policyNumber", policyNumber);
			accountVerificationDataVeriables.put("proposerFullName", proposerName);
			accountVerificationDataVeriables.put("bankAccountNumber", accountNumber);
			accountVerificationDataVeriables.put("micrCode", micrCode);
			accountVerificationDataVeriables.put("ifscCode", ifscCode);
			accountVerificationDataVeriables.put("branchName", branchName);

			accountVerificationContext.setVariables(accountVerificationDataVeriables);

			logger.info("End of Bank Account Verification data population");
		} catch (Exception ex) {
			logger.error("Data addition failed for Bank Account Verification Document: ", ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add(DATA_ADDITION_ERROR_MSG);
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return accountVerificationContext;
	}
	
	/*
	 * @param proposalDetails
	 * @param encodedString
	 * @return DocumentStatusDetails
	 * 
	 * In this function the generated PDF document is uploaded S3 and DocumentStatusDetails is returned.
	 */
	private DocumentStatusDetails uploadDocumentToS3AndGetStatus(ProposalDetails proposalDetails, String encodedString) {
		String documentUploadStatus = "";
		DocumentStatusDetails documentStatusDetails = null;
		DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, BANK_ACCOUNT_VERIFICATION_DOCUMENT);
		List<DocumentRequestInfo> documentpayload = new ArrayList<>();
		documentpayload.add(documentRequestInfo);
		DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(), "CHQ_Pr",
				BANK_ACCOUNT_VERIFICATION_DOCUMENT, documentpayload);
		documentDetails.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
		documentDetails.setDocumentType(AppConstants.DOCUMENT_TYPE);
		documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, RETRY_COUNT);
		if (documentUploadStatus.equalsIgnoreCase(AppConstants.FAILED)) {
			// Document upload to S3 failed, updating the status in DB
		    logger.info("Bank Account Verification Document upload to S3 failed");
		    documentStatusDetails = getDocumentStatusDetails(proposalDetails, AppConstants.DOCUMENT_UPLOAD_FAILED);
		} else {
			// Document upload to S3 success, updating the status in DB
		    logger.info("Bank Account Verification Document is successfully generated and uploaded to S3 for transactionId : {} , {}", proposalDetails.getTransactionId(), BANK_ACCOUNT_VERIFICATION_DOCUMENT);
		    documentStatusDetails = getDocumentStatusDetails(proposalDetails, documentUploadStatus);
		}
		return documentStatusDetails;
	}
	
	/**
	 * @param proposalDetails
	 * @param documentStatus
	 * @return DocumentStatusDetails
	 * 
	 * In this function, returning the DocumentStatusDetails by documentStatus
	 */
	private DocumentStatusDetails getDocumentStatusDetails(ProposalDetails proposalDetails, String documentStatus) {
		return new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
		proposalDetails.getSourcingDetails().getAgentId(), documentStatus, RETRY_COUNT, BANK_ACCOUNT_VERIFICATION_DOCUMENT,
		proposalDetails.getApplicationDetails().getStage());
	}

}
