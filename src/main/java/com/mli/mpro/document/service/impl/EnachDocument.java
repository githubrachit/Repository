package com.mli.mpro.document.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.mli.mpro.proposal.models.*;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component("enachDocument")
@EnableAsync
public class EnachDocument implements DocumentGenerationservice {
	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	@Autowired
	private DocumentHelper documentHelper;

	private static final Logger logger = LoggerFactory.getLogger(EnachDocument.class);

	private static final String FAILED = "FAILED";
	private static final String DATA_ADDITION_ERROR_MSG ="Data addition failed";

	/*
	 * This is the main method where actual document is getting generated. It
	 * internally calls methods of data mapping, binding the data with html,pdf
	 * generation and uploading document to S3 and also the methods to save
	 * success and failure status in DB
	 */
	@Override
	@Async
	public void generateDocument(ProposalDetails proposalDetails) {

		long transactionId = proposalDetails.getTransactionId();
		logger.info("Initiating document Enach document generation for transactionId {}", transactionId);
		String paymentRenewedBy = proposalDetails.getBank().getPaymentRenewedBy();
		long requestedTime = System.currentTimeMillis();
		int retryCount = 0;
		String documentUploadStatus = "";
		DocumentStatusDetails documentStatusDetails = null;
		Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
		AxisDirectDebitPOSVData axisDirectDebitPOSVData = proposalDetails.getAdditionalFlags().getAxisDirectDebitPOSVData();
		if(!THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				&& !PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
			logger.info("payment renewed by: {}; isSIOpted: {} ;directDebitRenewal: {} axisDirectDebitPOSVData is empty or not :: {}", paymentRenewedBy, receipt.getIsSIOpted(), receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitRenewals(), ObjectUtils.isEmpty(axisDirectDebitPOSVData));
		}
		if(ObjectUtils.isEmpty(axisDirectDebitPOSVData)) {
			axisDirectDebitPOSVData = new AxisDirectDebitPOSVData();
			axisDirectDebitPOSVData.setIsSIOpted(AppConstants.NO);
		}
		ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		if (Utility.orTwoExpressions(
				Utility.andTwoExpressions(paymentRenewedBy != null, ENACH_PAYMENT_RENEW_MODE.equalsIgnoreCase(paymentRenewedBy))
				, Utility.andThreeExpressions(!isEmpty(receipt.getIsSIOpted()), "True".equalsIgnoreCase(receipt.getIsSIOpted()),
						Utility.andTwoExpressions(ObjectUtils.isNotEmpty(axisDirectDebitPOSVData),
						AppConstants.NO.equalsIgnoreCase(axisDirectDebitPOSVData.getIsSIOpted())))) && !THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
			try {
				Context context = null;
				String processedHtml = null;
				Date transactionDateTimeStamp=null;
				/******  F21-265 Differentiating SI or Enach Doc Generation based on latest Mode of Renewal Payment among SI Credit Card and Enach  *****/
				if (proposalDetails.getAdditionalFlags() != null && proposalDetails.getAdditionalFlags().getSourceChannel() != null && PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
					transactionDateTimeStamp = !isEmpty(proposalDetails.getPaymentDetails().getReceipt().get(0).getTransactionDateTimeStamp()) ?
							new SimpleDateFormat(AppConstants.YYYY_MM_DD_HH_MM_SS_SSS_Z).parse(proposalDetails.getPaymentDetails().getReceipt().get(0).getTransactionDateTimeStamp()) : null;

				} else {
					transactionDateTimeStamp = !isEmpty(proposalDetails.getPaymentDetails().getReceipt().get(0).getTransactionDateTimeStamp()) ?
							new SimpleDateFormat(AppConstants.YYYY_MM_DD_HH_MM_SS_HYPHEN_Z).parse(proposalDetails.getPaymentDetails().getReceipt().get(0).getTransactionDateTimeStamp()) : null;
				}
				logger.info("transaction date timestamp :: {}",transactionDateTimeStamp);

				if ((transactionDateTimeStamp!=null && !StringUtils.isEmpty(receipt.getIsSIOpted()) && receipt.getIsSIOpted().equalsIgnoreCase("True")
						&& (proposalDetails.getBank().getEnachDetails()==null || (proposalDetails.getBank().getEnachDetails()!=null &&
						proposalDetails.getBank().getEnachDetails().getFirstCollectionDate().before(transactionDateTimeStamp))))) {
					logger.info("Generating SI Mandate Doc for transactionId {}", transactionId);
					context = setDataForSIMandateDocument(proposalDetails);
					processedHtml = springTemplateEngine.process("SI_MandateDetails", context);
					logger.info("process HTML si mandate document : {}",processedHtml);
				} else {
					logger.info("Generating Enach Doc for transactionId {}", transactionId);
					context = setDataForEnachDocument(proposalDetails);
					processedHtml = springTemplateEngine.process("Enach", context);
					logger.info("processed html for enach document : {}",processedHtml);
				}
				logger.info("Data binding with HTML is done for transactionId {}", transactionId);
				long processedTime = (System.currentTimeMillis() - requestedTime);
				logger.info("Data binding with HTML for transactionId {} took {} miliseconds ", transactionId, processedTime);
				String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
				logger.info("HTML to pdf conversation is done for transactionId {}", transactionId);
				processedTime = (System.currentTimeMillis() - requestedTime);
				logger.info("HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId, processedTime);
				if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
					// update the document generation failure status in db
					logger.info(DOCUMENT_GENERATION_FAILED_LOG, transactionId);
					documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
							proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.ECS_MANDATE,
							proposalDetails.getApplicationDetails().getStage());
				} else {
					logger.info("encoded string {} ",encodedString);
					DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString, AppConstants.ECS_MANDATE);
					List<DocumentRequestInfo> documentpayload = new ArrayList<>();
					documentpayload.add(documentRequestInfo);
					DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(), transactionId, "NACH_Pr",
							AppConstants.ECS_MANDATE, documentpayload);
					documentDetails.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
					documentDetails.setDocumentType(AppConstants.DOCUMENT_TYPE);
					if (AppConstants.PROPOSAL_E2E_TRANSFORMATION.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getSourceChannel())) {
						documentDetails.setSourceChannel(proposalDetails.getAdditionalFlags().getSourceChannel());
					}
					documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
					documentStatusDetails = getDocStatusDetailsBasedOnDocUploadStatus(proposalDetails, documentUploadStatus);
				}

			} catch (UserHandledException ex) {
			logger.error("Enach Document generation failed:",ex);
				documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.ECS_MANDATE,
						proposalDetails.getApplicationDetails().getStage());
				documentHelper.updateDocumentStatus(documentStatusDetails);
			} catch (Exception ex) {
			logger.error("Enach Document generation failed:",ex);
				documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.ECS_MANDATE,
						proposalDetails.getApplicationDetails().getStage());
			}
			documentHelper.updateDocumentStatus(documentStatusDetails);
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info("Enach document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
		}

	
	//Incase of Direct Debit
	else if (isEnachDocApplicable(proposalDetails,receipt)) {
		logger.info("Starting Direct Debit Consent Form Document creation.");
		Context context = null;
		String finalProcessedHtml = null;
		try {
			context = setDataOfDirectDebitConsentFormDocument(proposalDetails);
			finalProcessedHtml = springTemplateEngine.process("directdebit\\directDebit", context);
			logger.info("final processed html :: {}",finalProcessedHtml);
			logger.info("Generating Direct Debit Consent Form Document...");

			String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);
			logger.info("encoded string for direct debit :: {}",encodedString);

			documentStatusDetails = saveDocToS3AndUpdateStatus(proposalDetails, transactionId, retryCount,
					encodedString);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		catch (Exception ex) {
			logger.error("Error occurred while Direct Debit Consent Form Document generation: {}", ex.getMessage());
			logger.error("Direct Debit Consent Form Document generation failed for transactionId {}", transactionId);
			documentStatusDetails = new DocumentStatusDetails(transactionId,
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
					AppConstants.ECS_MANDATE, proposalDetails.getApplicationDetails().getStage());
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		long processedTime = (System.currentTimeMillis() - requestedTime);
		logger.info("Direct Debit Consent Form Document for transactionId {} took {} miliseconds ",
				proposalDetails.getTransactionId(), processedTime);		
		}
	//FUL2-10115_Digital_Debit_Mandate_Registration-Axis_Channel
	//Generating Direct Debit mandate for AXIS channel - if IsSIOpted is yes in axisDirectDebitPOSVData object
	else if((AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
			&& Utility.andThreeExpressions(ObjectUtils.isNotEmpty(axisDirectDebitPOSVData),
					AppConstants.YES.equalsIgnoreCase(axisDirectDebitPOSVData.getIsSIOpted()),
					!AppConstants.SINGLE.equalsIgnoreCase(productDetails.getProductInfo().getModeOfPayment()))) || THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {

		logger.info("Starting AXIS Direct Debit Consent Form Document creation.");
		Context context = null;
		String finalProcessedHtml = null;
		try {
			context = setDataOfDirectDebitConsentFormDocumentAXIS(proposalDetails);
			finalProcessedHtml = springTemplateEngine.process("directdebit\\directDebit", context);
			logger.info("Generating AXIS Direct Debit Consent Form Document...");

			String encodedString = documentHelper.generatePDFDocument(finalProcessedHtml, 0);

			documentStatusDetails = saveAXISDocToS3AndUpdateStatus(proposalDetails, transactionId, retryCount,
					encodedString);
			documentHelper.updateDocumentStatus(documentStatusDetails);
		}
		catch (Exception ex) {
			logger.error("Error occurred while AXIS Direct Debit Consent Form Document generation: {}", ex.getMessage());
			logger.error("AXIS Direct Debit Consent Form Document generation failed for transactionId {}", transactionId);
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
	}

	private boolean isEnachDocApplicable(ProposalDetails proposalDetails, Receipt receipt) {
		try {
			if (proposalDetails == null || receipt == null) {
				return false;
			}

			String sourceChannel = proposalDetails.getAdditionalFlags().getSourceChannel();
			String channel = proposalDetails.getChannelDetails().getChannel();
			boolean isYblApplicable = AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel) && receipt.getYblPaymentDetails() != null &&
					receipt.getYblPaymentDetails().getDirectDebitDetails() != null &&
					AppConstants.YES.equalsIgnoreCase(receipt.getYblPaymentDetails().getDirectDebitDetails().getDirectDebitRenewals());

			// Check Partner Payment Details for Direct Debit Renewals
			boolean isPartnerApplicable = Utility.isTMBPartner(sourceChannel) &&
					receipt.getPartnerPaymentDetails() != null &&
					receipt.getPartnerPaymentDetails().getDirectDebitDetails() != null &&
					AppConstants.YES.equalsIgnoreCase(receipt.getPartnerPaymentDetails().getDirectDebitDetails().getDirectDebitRenewals());

			// Return true if either condition is met
			return isYblApplicable || isPartnerApplicable;

		} catch (Exception ex) {
			logger.error("Error occurred while checking if Enach Document is applicable: ", ex);
			return false;
		}
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
			DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
					encodedString, AppConstants.DIRECT_DEBIT_MANDATE);
			List<DocumentRequestInfo> documentpayload = new ArrayList<>();
			documentpayload.add(documentRequestInfo);
			DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
					proposalDetails.getTransactionId(), "DR Debit_Pr",
					AppConstants.DIRECT_DEBIT_MANDATE, documentpayload);
			documentDetails.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
			logger.info("Calling SaveDocumentToS3 for Axis DR Debit_Pr doc transactionId : {}",proposalDetails.getTransactionId());
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

	/**
	 * @param proposalDetails
	 * @param transactionId
	 * @param retryCount
	 * @param encodedString
	 * @return
	 */
	private DocumentStatusDetails saveDocToS3AndUpdateStatus(ProposalDetails proposalDetails, long transactionId,
			int retryCount, String encodedString) {
		String documentUploadStatus;
		DocumentStatusDetails documentStatusDetails;
		if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
			// update the document generation failure status in db
			logger.info(DOCUMENT_GENERATION_FAILED_LOG, transactionId);
			documentStatusDetails = new DocumentStatusDetails(transactionId,
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0,
					AppConstants.ECS_MANDATE, proposalDetails.getApplicationDetails().getStage());
			logger.info("documentdetail object {}",documentStatusDetails);
		} else {
			logger.info("Encoded string value : {} ",encodedString);
			DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
					encodedString, AppConstants.ECS_MANDATE);
			logger.info("document request info : {}",documentRequestInfo);
			List<DocumentRequestInfo> documentpayload = new ArrayList<>();
			documentpayload.add(documentRequestInfo);
			DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
					proposalDetails.getTransactionId(), "NACH_Pr",
					AppConstants.ECS_MANDATE, documentpayload);
			logger.info("doc detail object : {}",documentDetails);
			documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
			logger.info(" status of s3 upload : {}",documentUploadStatus);
			if (documentUploadStatus.equalsIgnoreCase(FAILED)) {
				logger.info("Direct Debit Consent Form Document upload to S3 failed");
				// update the document upload failure status in db
				documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
						AppConstants.ECS_MANDATE, proposalDetails.getApplicationDetails().getStage());
			} else {
				logger.info(
						"Direct Debit Consent Form Document is successfully generated and uploaded to S3 for transactionId {} {}",
						transactionId, AppConstants.ECS_MANDATE);
				// update the document upload success status in db
				documentStatusDetails = new DocumentStatusDetails(transactionId,
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0,
						AppConstants.ECS_MANDATE, proposalDetails.getApplicationDetails().getStage());
			}
		}
		return documentStatusDetails;
	}

	/*
     * This method binds the data fetching from proposalDetails received in input
	 * and context object is returned with the variable mappings
	 */
	private Context setDataForEnachDocument(ProposalDetails proposalDetails) throws UserHandledException {

		logger.info("Mapping Enach data for transactionId {}", proposalDetails.getTransactionId());
		ENACHDetails enachDetails = proposalDetails.getBank().getEnachDetails();
		Map<String, Object> enachDataVariables = new HashMap<>();
		Context enachContext = new Context();
		try {
			String npciCode = "";
			String mandateAmount = "";
			String utilityCode = "";
			String recurringFrequency = "";
			String serviceProvider = "";
			String finalCollectionDate = "";
			String firstCollectionDate = "";
			String maxLifeRefNo = "";
			String ingenicoMandateId = "";
			String ingenicoTransactionId = "";
			String ingenicoBankCode = "";
			String sponsorBank = "";
			String sponsorBankCode = "";
			String NPCICategoryName = "";
			String policyHolder = "";
			String accountType = "";
			String policyNumber = "";
			if (enachDetails != null) {
				npciCode = enachDetails.getNPCICode();
				mandateAmount = enachDetails.getMandateAmount();
				utilityCode = enachDetails.getUtilityCode();
				recurringFrequency = enachDetails.getRecurringFrequency();
				serviceProvider = enachDetails.getServiceProviderName();
				maxLifeRefNo = enachDetails.getMaxLifeRefNo();
				ingenicoMandateId = enachDetails.getIngenicoMandateId();
				ingenicoTransactionId = enachDetails.getIngenicoTransactionId();
				ingenicoBankCode = enachDetails.getIngenicoBankCode();
				sponsorBank = enachDetails.getSponsorBank();
				sponsorBankCode = enachDetails.getSponsorBankCode();
				NPCICategoryName = enachDetails.getNPCICategoryName();
				accountType = enachDetails.getAmountType();
				policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
				Optional<PartyInformation> policyHolderInfo = proposalDetails.getPartyInformation().stream().filter(people -> people.getPartyType().equalsIgnoreCase("Proposer")).findAny();
				if (policyHolderInfo.isPresent()) {
					String middleName = policyHolderInfo.get().getBasicDetails().getMiddleName();
					if (!isEmpty(middleName)) {
						policyHolder = policyHolderInfo.get().getBasicDetails().getFirstName() + " " + middleName + " " + policyHolderInfo.get().getBasicDetails().getLastName();
					} else {
						policyHolder = policyHolderInfo.get().getBasicDetails().getFirstName() + " " + policyHolderInfo.get().getBasicDetails().getLastName();
					}
				}

				finalCollectionDate = Utility.dateFormatter(enachDetails.getFinalCollectionDate());
				firstCollectionDate = Utility.dateFormatter(enachDetails.getFirstCollectionDate());
			}
			enachDataVariables.put("NPCICategoryCode", npciCode);
			enachDataVariables.put("MandateEndDate", finalCollectionDate);
			enachDataVariables.put("MandateStartDate", firstCollectionDate);
			enachDataVariables.put("MaximumAmount", mandateAmount);
			enachDataVariables.put("Frequency", recurringFrequency);
			enachDataVariables.put("ServiceProviderName", serviceProvider);
			enachDataVariables.put("UtilityCode", utilityCode);
			enachDataVariables.put("MaxLifeRefNo", maxLifeRefNo);
			enachDataVariables.put("IngenicoTxnRefNo", ingenicoTransactionId);
			enachDataVariables.put("IngenicoBankCode", ingenicoBankCode);
			enachDataVariables.put("IngenicoMandateID", ingenicoMandateId);
			enachDataVariables.put(POLICY_NUMBER, policyNumber);
			enachDataVariables.put("PolicyHolder", policyHolder);
			enachDataVariables.put("NPCICategoryName", NPCICategoryName);
			enachDataVariables.put("SponsorBank", sponsorBank);
			enachDataVariables.put("SponsorBankCode", sponsorBankCode);
			enachDataVariables.put("AccountType", accountType);
			enachDataVariables.put(IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
			logger.info("MandateStartDate {}, MandateEndDate {}",firstCollectionDate,finalCollectionDate);

			enachContext.setVariables(enachDataVariables);
		} catch (Exception ex) {
	    logger.error("Data addition failed for Enach Document:", ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add(DATA_ADDITION_ERROR_MSG);
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return enachContext;
	}

	private Context setDataForSIMandateDocument(ProposalDetails proposalDetails) throws UserHandledException {

		logger.info("Mapping SI_Mandate data for transactionId {}", proposalDetails.getTransactionId());
		PaymentDetails paymentDetails = proposalDetails.getPaymentDetails();
		logger.info("payment details empty: {}", ObjectUtils.isEmpty(paymentDetails));
		Map<String, Object> SIDataVariables = new HashMap<>();
		Context SIContext = new Context();
		try {
			String billDeskBankCode = "";
			String amount = "";
			String maxLifeRefNo = "";
			String billDeskTransactionNo = "";
			String policyHolder = "";
			String policyNumber = "";
			if (proposalDetails.getPaymentDetails() != null && paymentDetails.getReceipt() != null) {
				amount = paymentDetails.getReceipt().get(0).getAmount();
				maxLifeRefNo = paymentDetails.getReceipt().get(0).getTransactionReferenceNumber();
				billDeskBankCode = paymentDetails.getReceipt().get(0).getBankId();
				billDeskTransactionNo = paymentDetails.getReceipt().get(0).getBankReferenceNumber();
				policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
				Optional<PartyInformation> policyHolderInfo = proposalDetails.getPartyInformation().stream().filter(people -> people.getPartyType().equalsIgnoreCase("Proposer")).findAny();
				if (policyHolderInfo.isPresent()) {
					String middleName = policyHolderInfo.get().getBasicDetails().getMiddleName();
					if (!isEmpty(middleName)) {
						policyHolder = policyHolderInfo.get().getBasicDetails().getFirstName() + " " + middleName + " " + policyHolderInfo.get().getBasicDetails().getLastName();
					} else {
						policyHolder = policyHolderInfo.get().getBasicDetails().getFirstName() + " " + policyHolderInfo.get().getBasicDetails().getLastName();
					}
				}
			}
			logger.info("policyNumber {}, policyHolder {}, maxRefno {}, Amount {}, BillDeskTransactionNo {}, BillDeskBankCode {}, isYBLProposal : {}",
					policyNumber, policyHolder, maxLifeRefNo, amount, billDeskTransactionNo, billDeskBankCode, Utility.isYBLProposal(proposalDetails));
			SIDataVariables.put("PolicyHolder", policyHolder);
			SIDataVariables.put("PolicyNo", policyNumber);
			SIDataVariables.put("MaxRefNo", maxLifeRefNo);
			SIDataVariables.put("Amount", amount);
			SIDataVariables.put("BillDeskTransactionNo", billDeskTransactionNo);
			SIDataVariables.put("BillDeskBankCode", billDeskBankCode);
			SIDataVariables.put(IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));

			SIContext.setVariables(SIDataVariables);
		} catch (Exception ex) {
			logger.error("Data addition failed for SIMandate Document:", ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add(DATA_ADDITION_ERROR_MSG);
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return SIContext;
	}
	
	/*
	 * This method binds the data fetching from proposalDetails received in input
	 * and context object is returned with the variable mappings
	 */
	private Context setDataOfDirectDebitConsentFormDocument(ProposalDetails proposalDetails)
			throws UserHandledException {

		Map<String, Object> dataVariables = new HashMap<>();
		Context directDebitContext = new Context();
		logger.info("Mapping Direct Debit Consent Form data for transactionId {}", proposalDetails.getTransactionId());
		ApplicationDetails applicationDetails = proposalDetails.getApplicationDetails();
		ChannelDetails channelDetails = proposalDetails.getChannelDetails();
		logger.info("channel name {}",channelDetails.getChannel());
		List<Receipt> paymentReceipts = proposalDetails.getPaymentDetails().getReceipt();
		String sourceChannel = proposalDetails.getAdditionalFlags().getSourceChannel();
		YBLPaymentDetails yblPaymentDetails = null;
		PartnerPaymentDetails partnerPaymentDetails = null;
		if (!paymentReceipts.isEmpty()) {
			logger.info("yblPaymentReceipt not empty");
			yblPaymentDetails = paymentReceipts.get(0).getYblPaymentDetails();
			partnerPaymentDetails = paymentReceipts.get(0).getPartnerPaymentDetails();

		}

		try {
			String accountNumber = "";
			String channel = "";
			String policyNumber = "";
			String consentDate = "";
			String ifscCode = "";
			String micrCode = "";

			if (channelDetails != null && applicationDetails != null) {
				channel = channelDetails.getChannel();
				policyNumber = applicationDetails.getPolicyNumber();
			}

			if (yblPaymentDetails != null) {
					ifscCode = yblPaymentDetails.getPaymentIfsc();
					micrCode = yblPaymentDetails.getPaymentMicr();
					accountNumber = yblPaymentDetails.getPaymentAccount();
				
				// Consent Date Format conversion: 'dd-MM-yy HH:mm:ss'
				String inputDate = yblPaymentDetails.getDirectDebitDetails().getDirectDebitOtpValidated();
				consentDate = dateConverter(inputDate,proposalDetails.getTransactionId());
			} else if (Utility.isTMBPartner(sourceChannel) && partnerPaymentDetails != null) {
				ifscCode = partnerPaymentDetails.getPaymentIfsc();
				micrCode = partnerPaymentDetails.getPaymentMicr();
				accountNumber = partnerPaymentDetails.getPaymentAccount();
				String inputDate = partnerPaymentDetails.getDirectDebitDetails().getDirectDebitOtpValidated();
				consentDate = dateConverter(inputDate,proposalDetails.getTransactionId());

			}

			dataVariables.put("AccountNumber", accountNumber);
			dataVariables.put("Channel", channel);
			dataVariables.put(POLICY_NUMBER, policyNumber);
			dataVariables.put("ConsentDate", consentDate);
			dataVariables.put("IfscCode", ifscCode);
			dataVariables.put("MicrCode", micrCode);
			dataVariables.put(IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));

			directDebitContext.setVariables(dataVariables);

		} catch (Exception ex) {
			logger.info("Data addition failed for Direct Debit Consent Form Document for transactionId {}",
					proposalDetails.getTransactionId());
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add(DATA_ADDITION_ERROR_MSG);
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return directDebitContext;
	}

	private String dateConverter(String inputDate, long transactionId) {
		String consentDate = null;
		try {
			logger.info("Converting input Date - {} to dd-MM-yy HH:mm:ss format for transactionId {}", inputDate,
					transactionId);
			Date originalDate = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z").parse(inputDate);
			DateFormat outputFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			String convertedDate = outputFormat.format(originalDate);
			logger.info("Converted Date successfully - {} for transactionId {}", convertedDate,
					transactionId);
		} catch (Exception e) {
			logger.error("Error occurred while converting date for Direct Debit Consent Form Document for transactionId {} {}" ,
					transactionId , Utility.getExceptionAsString(e));
		}
		return consentDate;
	}


	private DocumentStatusDetails getDocStatusDetailsBasedOnDocUploadStatus(ProposalDetails proposalDetails, String documentUploadStatus) {
		long transactionId = proposalDetails.getTransactionId();
		DocumentStatusDetails documentStatusDetails;
		if (documentUploadStatus.equalsIgnoreCase(FAILED)) {
			// update the document upload failure status in db
			documentStatusDetails = new DocumentStatusDetails(transactionId,
					proposalDetails.getApplicationDetails().getPolicyNumber(), proposalDetails.getSourcingDetails().getAgentId(),
					AppConstants.DOCUMENT_UPLOAD_FAILED, 0, AppConstants.ECS_MANDATE, proposalDetails.getApplicationDetails().getStage());
		} else {
			logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId, AppConstants.ECS_MANDATE);
			// update the document upload success status in db
			documentStatusDetails = new DocumentStatusDetails(transactionId, proposalDetails.getApplicationDetails().getPolicyNumber(),
					proposalDetails.getSourcingDetails().getAgentId(), documentUploadStatus, 0, AppConstants.ECS_MANDATE,
					proposalDetails.getApplicationDetails().getStage());
		}
		return documentStatusDetails;
	}
	//FUL2-10115_Digital_Debit_Mandate_Registration-Axis_Channel
	//To set data for DirectDebit form
	private Context setDataOfDirectDebitConsentFormDocumentAXIS(ProposalDetails proposalDetails)
			throws UserHandledException {

		Map<String, Object> dataVariables = new HashMap<>();
		Context axisDirectDebitContext = new Context();
		logger.info("Mapping AXIS Direct Debit Consent Form data for transactionId {}", proposalDetails.getTransactionId());
		ApplicationDetails applicationDetails = proposalDetails.getApplicationDetails();
		BankDetails bankDetails = proposalDetails.getBank().getBankDetails().get(0);
		Date submittedOTPDate = proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate();
	
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
			errorMessages.add(DATA_ADDITION_ERROR_MSG);
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
		}catch(Exception ex) {
			logger.error("Enach Doc Error while converting Date for AXIS {}",ex.getMessage());
		}
		return consentDate;
	}
}
