package com.mli.mpro.document.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SourcingDetails;

@Component("issuerConfirmationCertifiacte")
@EnableAsync
public class IssuerConfirmationDocument implements DocumentGenerationservice {

	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	@Autowired
	private DocumentHelper documentHelper;

	private static final Logger logger = LoggerFactory.getLogger(IssuerConfirmationDocument.class);

	@Override
	@Async
	public void generateDocument(ProposalDetails proposalDetails) {

		String channel = proposalDetails.getChannelDetails().getChannel();
		String issuerConfirmstatus = proposalDetails.getAdditionalFlags().getIssuerConfirmCertificateStatus();
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String commstatus = proposalDetails.getAdditionalFlags().getJourneyFieldsModificationStatus()
				.getCommunicationAddStatus();
		String namestatus = proposalDetails.getAdditionalFlags().getJourneyFieldsModificationStatus().getNameStatus();
		boolean panvalidate = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().isPanValidated();
		boolean dobvalidate = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().isDobValidated();
		
		long requestedTime = System.currentTimeMillis();
		int retryCount = 0;
		String documentUploadStatus = "";
		long transactionId = proposalDetails.getTransactionId();
		DocumentStatusDetails documentStatusDetails = null;
		String agentId = Optional.ofNullable(proposalDetails.getSourcingDetails()).map(SourcingDetails::getAgentId)
				.orElse("");
		if (StringUtils.equalsIgnoreCase(issuerConfirmstatus, "REQUIRED")) {
			try {
				Context context = setDataForDocument(proposalDetails);
				String processedHtml = springTemplateEngine.process("IssuerConfirmationCertificate", context);
				logger.info("Data binding with HTML is done for transactionId {}", transactionId);
				long processedTime = (System.currentTimeMillis() - requestedTime);
				logger.info("Data binding with HTML for transactionId {} took {} miliseconds ", transactionId,
						processedTime);
				String encodedString = documentHelper.generatePDFDocument(processedHtml, retryCount);
				logger.info("HTML to pdf conversation is done for transactionId {}", transactionId);
				processedTime = (System.currentTimeMillis() - requestedTime);
				logger.info("HTML to pdf conversation for transactionId {} took {} miliseconds ", transactionId,
						processedTime);
				if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
					// update the document generation failure status in db
					logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
					documentStatusDetails = new DocumentStatusDetails(transactionId,
							proposalDetails.getApplicationDetails().getPolicyNumber(),
							agentId, AppConstants.DOCUMENT_GENERATION_FAILED,
							0, AppConstants.ISSUER_CONFIRMATION, proposalDetails.getApplicationDetails().getStage());
				} else {
					DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
							encodedString, AppConstants.ISSUER_CONFIRMATION);
					List<DocumentRequestInfo> documentpayload = new ArrayList<>();
					documentpayload.add(documentRequestInfo);
					boolean condition1=Utility.andTwoExpressions(
							Utility.andThreeExpressions(!StringUtils.isEmpty(namestatus),
									AppConstants.FIELD_NOT_MODIFIED.equalsIgnoreCase(namestatus), !dobvalidate), !panvalidate);
					boolean dedupePanDobMatchCondition= Utility.andTwoExpressions(
							Utility.andThreeExpressions(!StringUtils.isEmpty(namestatus),
									AppConstants.FIELD_NOT_MODIFIED.equalsIgnoreCase(namestatus), dobvalidate), panvalidate);
					boolean isKYCWeaverMatchChannelAndForm =Utility.orTwoExpressions(Utility.andThreeExpressions(!AppConstants.CHANNEL_AXIS.equalsIgnoreCase(channel) ||
							!AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel),AppConstants.SELF.equalsIgnoreCase(formType) ||
							AppConstants.DEPENDENT.equalsIgnoreCase(formType),dedupePanDobMatchCondition),condition1);

					documentUploadStatus = conditonallySaveDocumentToS3(isKYCWeaverMatchChannelAndForm,channel, retryCount, transactionId, documentpayload, "ID_Pr", AppConstants.ISSUER_CONFIRMATION_ID,StringUtils.EMPTY);

					boolean condition2=Utility.andTwoExpressions(!StringUtils.isEmpty(commstatus)
							,AppConstants.FIELD_NOT_MODIFIED.equalsIgnoreCase( commstatus));

					documentUploadStatus = conditonallySaveDocumentToS3(condition2,channel, retryCount, transactionId, documentpayload, "Comm_Add_Pr", AppConstants.ISSUER_CONFIRMATION_ADD,documentUploadStatus);


					documentStatusDetails = getDocumentStatusDetails(proposalDetails, documentUploadStatus, transactionId,agentId);
				}

			} catch (UserHandledException ex) {
				logger.error("Issure Confirmation Document generation failed: {}",Utility.getExceptionAsString(ex));
				documentStatusDetails = new DocumentStatusDetails(transactionId,
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						agentId, AppConstants.DATA_MISSING_FAILURE, 0,
						AppConstants.ISSUER_CONFIRMATION, proposalDetails.getApplicationDetails().getStage());
				documentHelper.updateDocumentStatus(documentStatusDetails);
			} catch (Exception ex) {
				logger.error("Issure Confirmation generation failed: {}",Utility.getExceptionAsString(ex));
				documentStatusDetails = new DocumentStatusDetails(transactionId,
						proposalDetails.getApplicationDetails().getPolicyNumber(),
						agentId, AppConstants.TECHNICAL_FAILURE, 0,
						AppConstants.ISSUER_CONFIRMATION, proposalDetails.getApplicationDetails().getStage());
			}
			documentHelper.updateDocumentStatus(documentStatusDetails);
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info("Issure Confirmation document for transactionId {} took {} miliseconds ",
					proposalDetails.getTransactionId(), processedTime);
		}

	}

	private DocumentStatusDetails getDocumentStatusDetails(ProposalDetails proposalDetails, String documentUploadStatus,
			long transactionId,String agentId) {
		DocumentStatusDetails documentStatusDetails;
		if (documentUploadStatus.equalsIgnoreCase("FAILED")) {
			// update the document upload failure status in db
			documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					agentId, AppConstants.DOCUMENT_UPLOAD_FAILED, 0,
					AppConstants.ISSUER_CONFIRMATION, proposalDetails.getApplicationDetails().getStage());
		} else {
			logger.info("Document is successfully generated and uploaded to S3 for transactionId {} {}", transactionId,
					AppConstants.ISSUER_CONFIRMATION);
			// update the document upload success status in db
			documentStatusDetails = new DocumentStatusDetails(transactionId,
					proposalDetails.getApplicationDetails().getPolicyNumber(),
					agentId, documentUploadStatus, 0,
					AppConstants.ISSUER_CONFIRMATION, proposalDetails.getApplicationDetails().getStage());
		}
		return documentStatusDetails;
	}

	private String conditonallySaveDocumentToS3(boolean condition,String channel, int retryCount, long transactionId, List<DocumentRequestInfo> documentPayload, String mproDocumentId, String documentName,String previousUploadStatus) {
		String documentUploadStatus="";
		if(condition){
			DocumentDetails documentDetails = new DocumentDetails(channel, transactionId, mproDocumentId,
					documentName, documentPayload);
			documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, retryCount);
		}else{
			documentUploadStatus=previousUploadStatus;
		}
		return documentUploadStatus;
	}

	private Context setDataForDocument(ProposalDetails proposalDetails) throws UserHandledException {

		Context context = new Context();
		logger.info("Data Mapping is initiated for transactionId {}", proposalDetails.getTransactionId());
		try {
			Map<String, Object> dataForDocument = new HashMap<>();
			BasicDetails basicDetails = new BasicDetails();
			AddressDetails addressDetails = new AddressDetails();
			basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
			addressDetails = basicDetails.getAddress().get(0).getAddressDetails();

			String gender = basicDetails.getGender();
			String tag = "MR";
			if (gender.equals("F")) {
				tag = "MS";
			} else if (gender.equalsIgnoreCase("Others")) {
				tag = "MX";
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm a");
			SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat datereformatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateformatt = new SimpleDateFormat("MM/yyyy");

			formatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
			dateformatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));

			String panCard = StringUtils.EMPTY;
			String previsousPolicyno = StringUtils.EMPTY;
			String previPolicydate = "";
			String previousdate = StringUtils.EMPTY;
			if (!CollectionUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails())
					&& !StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0)
							.getPrevpolicydate())) {

				previPolicydate = proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0)
						.getPrevpolicydate();
				Date prevpolicydate = datereformatter.parse(previPolicydate);
				previousdate = dateformatt.format(prevpolicydate);
			}

			String firstName = basicDetails.getFirstName();
			String middleName = basicDetails.getMiddleName();
			String lastName = basicDetails.getLastName();
			String customerName = Stream.of(firstName, middleName, lastName).filter(s -> !StringUtils.isEmpty(s))
					.collect(Collectors.joining(" "));
			Date dateOfBirth = basicDetails.getDob();
			String dob = dateformatter.format(dateOfBirth);
			String todaysDate = formatter.format(new Date());
			if (!StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getPersonalIdentification()
					.getPanDetails().getPanNumber())) {
				panCard = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPanDetails()
						.getPanNumber().toUpperCase();
			}
			if (!CollectionUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails())
					&& !StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0)
							.getPreviousPolicyNumber())) {

				previsousPolicyno = proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0)
						.getPreviousPolicyNumber();
			}

			String communicationAddress = getAddress(addressDetails.getHouseNo(), addressDetails.getArea(),
					addressDetails.getLandmark(), addressDetails.getPinCode(), addressDetails.getCity(),
					addressDetails.getState(), addressDetails.getCountry());

			dataForDocument.put("customerName", customerName);
			dataForDocument.put("title", "Sir/Madam");
			dataForDocument.put("previousPolicydate", previousdate);
			dataForDocument.put("todaysDate", todaysDate);
			dataForDocument.put("tagName", tag);
			dataForDocument.put("customerDob", dob);
			dataForDocument.put("customergender", gender);
			dataForDocument.put("customerPan", panCard);
			dataForDocument.put("gender", gender);
			dataForDocument.put("commuAddress", communicationAddress);
			dataForDocument.put("previousPolicy", previsousPolicyno);
			dataForDocument.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
			context.setVariables(dataForDocument);
		} catch (Exception ex) {
			logger.error("Data addition failed for Issuer Confirmation Document:",ex);
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("Data addition failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return context;
	}

	private String getAddress(String add1, String add2, String add3, String pincode, String city, String state,
			String country) {
		return String.join(" " , add1, add2, add3, city,state, country, pincode);
	}
}
