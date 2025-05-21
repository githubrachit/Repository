package com.mli.mpro.document.service.impl;

import com.google.common.base.Strings;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.mapper.PaymentDataMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.neo.models.attachment.Payload;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("neoPaymentDocument")
public class NeoPaymentDocument extends NeoBaseDocument implements DocumentGenerationservice {

	private static final Logger logger = LoggerFactory.getLogger(NeoPaymentDocument.class);
	public static final String FREQUENCY_SINGLE = "Single";
	public static final String PAYMENT_FREQUENCY = "paymentFrequency";
	public static final String INCOME_FREQUENCY = "incomeFrequency";
	private boolean isNeoOrAggregator = true;
	private static final String PAYMENT_RECEIPT_GENERATION_ERROR_MESSAGE = "Error occurred while Payment Receipt Document generation:";
	protected String paymentReceiptTemplate = "payment\\neoPayment2";
	protected String paymentReceiptString = "Payment receipt";

	@Autowired
	private PaymentDataMapper paymentDocument;

	@Override
	public void generateDocument(ProposalDetails proposalDetails) {
		logger.info("Starting Payment Receipt document creation for transactionId {} with Neo implementaion", proposalDetails.getTransactionId());
		long requestedTime = System.currentTimeMillis();
		DocumentStatusDetails documentStatusDetails = null;
		String documentUploadStatus = "";
		int retryCount = 0;
		long transactionId = proposalDetails.getTransactionId();
		String finalProcessedHtml = null;
		try {

			String requestSource = Utility.nullSafe(proposalDetails.getAdditionalFlags().getRequestSource());
			boolean axisFlag = StringUtils.equalsIgnoreCase(requestSource, "AXIS");
			Context panDobDetailsContext = paymentDocument.setDataOfPaymentDocument(proposalDetails);
			if (panDobDetailsContext != null && panDobDetailsContext.getVariableNames() != null && !panDobDetailsContext.getVariableNames().isEmpty()) {
				if (Utility.isPaymentFrequencySingle(proposalDetails)) {
					panDobDetailsContext.setVariable(PAYMENT_FREQUENCY, FREQUENCY_SINGLE);
					panDobDetailsContext.setVariable(INCOME_FREQUENCY, FREQUENCY_SINGLE);
				} else {
					//setting income frequency key for showing customer's income return frequency
					//mode of payment shows his payment frequency.
					panDobDetailsContext.setVariable(PAYMENT_FREQUENCY, proposalDetails.getProductDetails().get(0).getProductInfo()
							.getModeOfPayment());
					panDobDetailsContext.setVariable(INCOME_FREQUENCY, proposalDetails.getProductDetails().get(0).getProductInfo()
							.getBenefitReturnFrequency());
				}
			}
			if (axisFlag) {
				finalProcessedHtml = springTemplateEngine.process("payment\\axisPayment", panDobDetailsContext);
			} else if (isNeoOrAggregator && paymentDocument.isSWPProduct(proposalDetails)) {
				finalProcessedHtml = springTemplateEngine.process("payment\\swpNeoPayment", panDobDetailsContext);
			} else if (isNeoOrAggregator && !paymentDocument.isSWPProduct(proposalDetails)) {
				finalProcessedHtml = springTemplateEngine.process("payment\\neoPayment2", panDobDetailsContext);
			} else {
				finalProcessedHtml = springTemplateEngine.process("payment\\main", panDobDetailsContext);
			}

			logger.info("Generating Payment Receipt Document for transactionId {}...", proposalDetails.getTransactionId());

			String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

			if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
				// update the document generation failure status in db
				logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
				if(isNeoOrAggregator)
					documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator, proposalDetails, transactionId, AppConstants.DOCUMENT_GENERATION_FAILED, AppConstants.PAYMENT_DOCUMENT_2);
				else
					documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator, proposalDetails, transactionId, AppConstants.DOCUMENT_GENERATION_FAILED, AppConstants.PAYMENT_DOCUMENT);

			} else {
				DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
						isNeoOrAggregator ? AppConstants.PAYMENT_DOCUMENT_2 : AppConstants.PAYMENT_DOCUMENT);
				List<DocumentRequestInfo> documentpayload = new ArrayList<>();
				documentpayload.add(documentRequestInfo);
				DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
						isNeoOrAggregator ? AppConstants.PAYMENT_DOCUMENTID_2 : AppConstants.PAYMENT_DOCUMENTID,
						isNeoOrAggregator ? AppConstants.PAYMENT_DOCUMENT_2 : AppConstants.PAYMENT_DOCUMENT, documentpayload);

				documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);

				if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
					logger.info("PAYMENT_DOCUMENT Document upload to S3 failed");
					// update the document upload failure status in db
					if (isNeoOrAggregator)
						documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator,
								proposalDetails, transactionId, AppConstants.DOCUMENT_UPLOAD_FAILED,
								AppConstants.PAYMENT_DOCUMENT_2);
					else
						documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator,
								proposalDetails, transactionId, AppConstants.DOCUMENT_UPLOAD_FAILED,
								AppConstants.PAYMENT_DOCUMENT);
				} else {
					logger.info("PAYMENT_DOCUMENT Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
							isNeoOrAggregator ? AppConstants.PAYMENT_DOCUMENT_2 : AppConstants.PAYMENT_DOCUMENT);
					// update the document upload success status in db
					if (isNeoOrAggregator)
						documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator,
								proposalDetails, transactionId, documentUploadStatus, AppConstants.PAYMENT_DOCUMENT_2);
					else
						documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator,
								proposalDetails, transactionId, documentUploadStatus, AppConstants.PAYMENT_DOCUMENT);
				}
			}
		} catch (UserHandledException ex) {
			logger.error(PAYMENT_RECEIPT_GENERATION_ERROR_MESSAGE, ex);
			if (isNeoOrAggregator)
				documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator, proposalDetails,
						transactionId, AppConstants.DATA_MISSING_FAILURE, AppConstants.PAYMENT_DOCUMENT_2);
			else
				documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator, proposalDetails,
						transactionId, AppConstants.DATA_MISSING_FAILURE, AppConstants.PAYMENT_DOCUMENT);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		} catch (Exception ex) {
			logger.error(PAYMENT_RECEIPT_GENERATION_ERROR_MESSAGE, ex);
			if (isNeoOrAggregator)
				documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator, proposalDetails,
						transactionId, AppConstants.TECHNICAL_FAILURE, AppConstants.PAYMENT_DOCUMENT_2);
			else
				documentStatusDetails = documentHelper.populateDocumentStatusObj(isNeoOrAggregator, proposalDetails,
						transactionId, AppConstants.TECHNICAL_FAILURE, AppConstants.PAYMENT_DOCUMENT);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		documentHelper.updateDocumentStatus(documentStatusDetails);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Payment Receipt document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
	}

	@Override
	public void createDocumentAndUploadToS3(Payload payload) {
		logger.info("Starting Payment Receipt document creation.");
		long requestedTime = System.currentTimeMillis();
		DocumentStatusDetails documentStatusDetails = null;
		String documentUploadStatus = "";
		int retryCount = 0;
		ProposalDetails proposalDetails = payload.getProposalDetails();
		long transactionId = proposalDetails.getTransactionId();
		String finalProcessedHtml = null;
		try {
			Context paymentContext = paymentDocument.setDataOfPaymentDocument(payload);
			if (paymentContext != null && paymentContext.getVariableNames() != null && !paymentContext.getVariableNames().isEmpty()) {
				if (Utility.isPaymentFrequencySingle(payload.getProposalDetails())) {
					paymentContext.setVariable(PAYMENT_FREQUENCY, FREQUENCY_SINGLE);
					paymentContext.setVariable(INCOME_FREQUENCY, FREQUENCY_SINGLE);
				} else {
					//setting income frequency key for showing customer's income return frequency
					//mode of payment shows his payment frequency.
					paymentContext.setVariable(PAYMENT_FREQUENCY, payload.getProposalDetails().getProductDetails().get(0).getProductInfo()
							.getModeOfPayment());
					paymentContext.setVariable(INCOME_FREQUENCY, payload.getProposalDetails().getProductDetails().get(0).getProductInfo()
							.getBenefitReturnFrequency());
				}
			}


			if (paymentDocument.isSWPProduct(proposalDetails)) {
				finalProcessedHtml = springTemplateEngine.process("payment\\swpNeoPayment", paymentContext);
			}
			else if (paymentDocument.isSWPJLProduct(proposalDetails) ||
					(!Strings.isNullOrEmpty(proposalDetails.getBankJourney()) && "ybl".equalsIgnoreCase(proposalDetails.getBankJourney()))) {
                finalProcessedHtml = springTemplateEngine.process("payment\\swpjlNeoPayment", paymentContext);
			}
			else {
				finalProcessedHtml = springTemplateEngine.process("payment\\neoPayment", paymentContext);
			}

			logger.info("Generating Payment Receipt Document...");

			String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

			if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
				// update the document generation failure status in db
				logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
				documentStatusDetails=documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.DOCUMENT_GENERATION_FAILED, AppConstants.PAYMENT_DOCUMENT);
			} else {
				DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.PAYMENT_DOCUMENT);
				List<DocumentRequestInfo> documentpayload = new ArrayList<>();
				documentpayload.add(documentRequestInfo);
				DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), proposalDetails.getTransactionId(),
						AppConstants.PAYMENT_DOCUMENTID, AppConstants.PAYMENT_DOCUMENT, documentpayload);
				documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
				if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
					logger.info("PAYMENT_DOCUMENT Document upload to S3 failed");
					// update the document upload failure status in db
					documentStatusDetails=documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.DOCUMENT_UPLOAD_FAILED, AppConstants.PAYMENT_DOCUMENT);
				} else {
					logger.info("PAYMENT_DOCUMENT Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
							AppConstants.PAYMENT_DOCUMENT);
					// update the document upload success status in db
					documentStatusDetails=documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, documentUploadStatus, AppConstants.PAYMENT_DOCUMENT);
				}
			}
		} catch (Exception ex) {
			logger.error(PAYMENT_RECEIPT_GENERATION_ERROR_MESSAGE, ex);
			documentStatusDetails=documentHelper.populateDocumentStatusObj(true, proposalDetails, transactionId, AppConstants.TECHNICAL_FAILURE, AppConstants.PAYMENT_DOCUMENT);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		documentHelper.updateDocumentStatus(documentStatusDetails);
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Payment Receipt document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
	}

	@Override
	public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
		try {
			Context context = paymentDocument.setDataOfPaymentDocument(proposalDetails);
			return getDocumentBase64String(proposalDetails, context, paymentReceiptString, paymentReceiptTemplate,
					springTemplateEngine, documentHelper);
		} catch (UserHandledException e) {
			logger.error("Payment receipt form generation failed for proposal with equote {} transactionId {}",
					proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e);
			throw new UserHandledException(Collections.singletonList(AppConstants.DATA_MISSING_FAILURE), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Payment receipt form generation failed for proposal with equote {} and transactionId {}",
					proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e);
			throw new UserHandledException(Collections.singletonList(AppConstants.TECHNICAL_FAILURE), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
