package com.mli.mpro.location.config;

import static com.mli.mpro.productRestriction.util.AppConstants.DOCUMENT_GENERATION_FAILED_LOG;
import static com.mli.mpro.productRestriction.util.AppConstants.IS_NOT_YBL_PROPOSAL;
import static com.mli.mpro.productRestriction.util.AppConstants.POLICY_NUMBER;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.impl.DocumentHelper;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ApplicationDetails;
import com.mli.mpro.proposal.models.BankDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

@Component("DirectDebit")
public class DirectDebit {
	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	@Autowired
	private DocumentHelper documentHelper;

	private static final Logger logger = LoggerFactory.getLogger(DirectDebit.class);

	private static final String FAILED = "FAILED";
	private static final String DATA_ADDITIONAL_ERROR_MSG = "Data addition failed";

	public void generateDocument(ProposalDetails proposalDetails) {

		long transactionId = proposalDetails.getTransactionId();
		logger.info("Initiating document Enach document generation for transactionId {}", transactionId);
		long requestedTime = System.currentTimeMillis();
		int retryCount = 0;
		DocumentStatusDetails documentStatusDetails = null;

		logger.info("Starting AXIS Direct Debit Consent Form Document creation.");
		Context context = null;
		String finalProcessedHtml = null;
		try {
			context = setDataOfDirectDebitConsentFormDocumentAXIS(proposalDetails);
			finalProcessedHtml = springTemplateEngine.process("directdebit\\directDebit", context);
			logger.info("Generating AXIS Direct Debit Consent Form Document...");

			String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
			byte[] bI = org.apache.commons.codec.binary.Base64
					.decodeBase64((encodedString.substring(encodedString.indexOf(",") + 1)).getBytes());
			logger.info("length of the document for transactionId {} is {}", proposalDetails.getTransactionId(),
					bI.length/1024.0/1000.0);
			documentStatusDetails = saveAXISDocToS3AndUpdateStatus(proposalDetails, transactionId, retryCount,
					encodedString);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		} catch (Exception ex) {
			logger.error("Error occurred while AXIS Direct Debit Consent Form Document generation: {}",
					ex.getMessage());
			logger.error("AXIS Direct Debit Consent Form Document generation failed for transactionId {}",
					transactionId);
			documentStatusDetails = new DocumentStatusDetails(transactionId,
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
					AppConstants.DIRECT_DEBIT_MANDATE, proposalDetails.getApplicationDetails().getStage());
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("AXIS Direct Debit Consent Form Document for transactionId {} took {} miliseconds ",
				proposalDetails.getTransactionId(), processedTime);

	}

	/**
	 * @param proposalDetails
	 * @param transactionId
	 * @param retryCount
	 * @param encodedString
	 * @return
	 */
	private DocumentStatusDetails saveAXISDocToS3AndUpdateStatus(ProposalDetails proposalDetails, long transactionId,
			int retryCount, String encodedString) {
		String documentUploadStatus;
		DocumentStatusDetails documentStatusDetails;
		if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
			// update the document generation failure status in db
			logger.info(DOCUMENT_GENERATION_FAILED_LOG, transactionId);
			documentStatusDetails = new DocumentStatusDetails(transactionId,
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0,
					AppConstants.DIRECT_DEBIT_MANDATE, proposalDetails.getApplicationDetails().getStage());
		} else {
			DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
					AppConstants.DIRECT_DEBIT_MANDATE);
			List<DocumentRequestInfo> documentpayload = new ArrayList<>();
			documentpayload.add(documentRequestInfo);
			DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
					proposalDetails.getTransactionId(), "DR Debit_Pr", AppConstants.DIRECT_DEBIT_MANDATE,
					documentpayload);
			documentDetails.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
			logger.info("Calling SaveDocumentToS3 for Axis DR Debit_Pr doc transactionId : {}",
					proposalDetails.getTransactionId());
			documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
			if (documentUploadStatus.equalsIgnoreCase(FAILED)) {
				logger.info("Axis Direct Debit Consent Form Document upload to S3 failed");
				// update the document upload failure status in db
				documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
						AppConstants.DIRECT_DEBIT_MANDATE, proposalDetails.getApplicationDetails().getStage());
			} else {
				logger.info(
						"AXIS Direct Debit Consent Form Document is successfully generated and uploaded to S3 for transactionId {} {}",
						transactionId, AppConstants.DIRECT_DEBIT_MANDATE);
				// update the document upload success status in db
				documentStatusDetails = new DocumentStatusDetails(transactionId,
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
						AppConstants.DIRECT_DEBIT_MANDATE, proposalDetails.getApplicationDetails().getStage());
			}
		}
		return documentStatusDetails;
	}

	// FUL2-10115_Digital_Debit_Mandate_Registration-Axis_Channel
	// To set data for DirectDebit form
	private Context setDataOfDirectDebitConsentFormDocumentAXIS(ProposalDetails proposalDetails)
			throws UserHandledException {

		Map<String, Object> dataVariables = new HashMap<>();
		Context axisDirectDebitContext = new Context();
		logger.info("Mapping AXIS Direct Debit Consent Form data for transactionId {}",
				proposalDetails.getTransactionId());
		Date submittedOTPDate = null;
		ApplicationDetails applicationDetails = proposalDetails.getApplicationDetails();
		BankDetails bankDetails = proposalDetails.getBank().getBankDetails().get(0);
		if (proposalDetails.getPosvDetails().getPosvStatus() != null) {
		submittedOTPDate = proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate();
		}

		try {
			String accountNumber = "";
			String policyNumber = "";
			String consentDate = "";
			String ifscCode = "";
			String micrCode = "";

			if (applicationDetails != null) {
				policyNumber = applicationDetails.getPolicyNumber();
			}

			if (ObjectUtils.isNotEmpty(bankDetails)) {
				ifscCode = bankDetails.getIfsc();
				micrCode = bankDetails.getMicr();
				accountNumber = bankDetails.getBankAccountNumber();
			}

			consentDate = setOTPSubmittedDate(proposalDetails, submittedOTPDate, consentDate);

			dataVariables.put("AccountNumber", accountNumber);
			dataVariables.put("Channel", AppConstants.AXIS_STRING);
			dataVariables.put(POLICY_NUMBER, policyNumber);
			dataVariables.put("ConsentDate", consentDate);
			dataVariables.put("IfscCode", ifscCode);
			dataVariables.put("MicrCode", micrCode);
			dataVariables.put(IS_NOT_YBL_PROPOSAL, true);

			axisDirectDebitContext.setVariables(dataVariables);

		} catch (Exception ex) {
			logger.info("Data addition failed for Direct Debit Consent Form Document for transactionId {}",
					proposalDetails.getTransactionId());
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add(DATA_ADDITIONAL_ERROR_MSG);
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return axisDirectDebitContext;
	}

	/**
	 * @param proposalDetails
	 * @param submittedOTPDate
	 * @param consentDate
	 * @return
	 */
	private String setOTPSubmittedDate(ProposalDetails proposalDetails, Date submittedOTPDate, String consentDate) {
		try {
			String inputDate = String.valueOf(submittedOTPDate);
			logger.info("Converting Date - {} to dd-MM-yy HH:mm:ss format for transactionId {}", inputDate,
					proposalDetails.getTransactionId());
			DateFormat outputFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			String convertedDate = outputFormat.format(submittedOTPDate);
			logger.info("Converted Date successfully - {} for transactionId {}", convertedDate,
					proposalDetails.getTransactionId());
			consentDate = convertedDate;
		} catch (Exception ex) {
			logger.error("Enach Doc Error while converting Date for AXIS {}", ex.getMessage());
		}
		return consentDate;
	}
}
