package com.mli.mpro.location.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.mli.mpro.ccms.model.SmsDetails;
import com.mli.mpro.ccms.repository.SmsRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.emailsms.notification.NotificationSenderService;
import com.mli.mpro.location.models.NotificationSender;
import com.mli.mpro.posseller.email.models.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.email.models.EmailDetails;
import com.mli.mpro.email.repository.EmailRepository;
import com.mli.mpro.location.services.PosSellerCommunicationService;
import com.mli.mpro.posseller.models.MessageRequest;
import com.mli.mpro.posseller.models.Payload;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PersonalIdentification;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.sms.requestmodels.GeneralConsumerInformation;
import com.mli.mpro.sms.requestmodels.MliSmsService;
import com.mli.mpro.sms.requestmodels.RequestBody;
import com.mli.mpro.sms.requestmodels.RequestHeader;
import com.mli.mpro.utils.Utility;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;

@EnableAsync
@Service
public class PosSellerCommunicationServiceImpl implements PosSellerCommunicationService {

	private static final String FINANCIAL_MANDATORY = "financialMandatory";

	private static final String ACC_ID = "MAXLI_MAPPOTP";

	private static final String APP_ID = "MAXLIT";

	private static final String MESSAGE_BODY_POS_SELLER = "Dear SELLERNAME,  we regret to inform that we are unable to process your proposal No.POLICYNUMBER Please Upload required docs for faster issuance.";

	private static final String STANDARD_DOC = "StandardDoc";

	private static final String FINANCIAL = "financial";

	@Value("${urlDetails.email}")
	private String email;

	@Value("${urlDetails.sms}")
	private String sms;

	private Logger logger = LoggerFactory.getLogger(PosSellerCommunicationServiceImpl.class);

	private EmailRepository emailRepo;

	private ProposalRepository proposalRepository;

	@Value("${sqs.posqueuename}")
	private String queueNameForPosSeller;

	@Autowired
	private SqsClient sqsClient;

	@Autowired
	MongoOperations mongoOperation;

	private NotificationSenderService notificationSenderService;

	@Autowired
	private SmsRepository smsRepository;
	
	@Autowired
	public PosSellerCommunicationServiceImpl(EmailRepository emailRepo, ProposalRepository proposalRepository, NotificationSenderService notificationSenderService) {
		super();
		this.emailRepo = emailRepo;
		this.proposalRepository = proposalRepository;
		this.notificationSenderService = notificationSenderService;

	}

	@Override
	public void findByUnderwritingStatusAndStageAndPosvJourneyStatus() {
		try {
			List<ProposalDetails> proposalList = this.proposalRepository
					.findByUnderwritingStatusAndStageAndPosvJourneyStatus();
			CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName(queueNameForPosSeller)
					.build();
			sqsClient.createQueue(createQueueRequest);
			GetQueueUrlResponse getQueueUrlResponse = sqsClient
					.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueNameForPosSeller).build());
			String queueUrl = getQueueUrlResponse.queueUrl();
			List<SendMessageBatchRequestEntry> messageEntries = new ArrayList<>();
			if (!CollectionUtils.isEmpty(proposalList)) {
				for (int i = 0; i < proposalList.size(); i++) {
					ProposalDetails proposalDetails = proposalList.get(i);
					long calDays = calculateDaysDifference(proposalDetails);
					MessageRequest sendMessage = new MessageRequest();
					com.mli.mpro.posseller.models.InputRequest inputRequest = settingInputRequest(proposalDetails);
					sendMessage.setTransactionId(proposalDetails.getTransactionId());
					sendMessage.setInputRequest(inputRequest);
					String parsedObj = parseObjectToString(sendMessage);
					if (calDays <= 30 && parsedObj != null) {
						messageEntries.add(SendMessageBatchRequestEntry.builder().id("id" + (i + 1))
								.messageBody(parsedObj).delaySeconds(5).build());
					}
					if (messageEntries.size() == 10) {
						pushBatchDataToSqs(messageEntries, queueUrl);
						messageEntries.clear();
					}
				}
			}
			if (!messageEntries.isEmpty()) {
				pushBatchDataToSqs(messageEntries, queueUrl);
				messageEntries.clear();
			}

		} catch (Exception e) {
			logger.error("Exception while Push to SQS Pos api and message is {}", Utility.getExceptionAsString(e));
		}
	}

	private long calculateDaysDifference(ProposalDetails proposalDetails) {

		Date createdTime = proposalDetails.getApplicationDetails().getCreatedTime();
		Date currentDate = new Date();
		long diffInMillies = Math.abs(currentDate.getTime() - createdTime.getTime());
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

	}

	private void pushBatchDataToSqs(List<SendMessageBatchRequestEntry> messageEntries, String standardQueueUrl) {
		try {
			SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
					.queueUrl(standardQueueUrl).entries(messageEntries).build();
			SendMessageBatchResponse sendMessageBatchResponse = sqsClient.sendMessageBatch(sendMessageBatchRequest);
			logger.info("pushed pos sqs message to queue and status is :: {}",
					sendMessageBatchResponse.sdkHttpResponse().statusCode());
		} catch (Exception e) {
			logger.error("Exception while executing sqs api and message is {}", Utility.getExceptionAsString(e));
		}
	}

	private String parseObjectToString(MessageRequest message) {
		String sqsmessage = null;
		List<String> documentNames = message.getInputRequest().getRequest().getPayload().getDocumentNames();
		if (!CollectionUtils.isEmpty(documentNames)) {
			try {
				sqsmessage = new ObjectMapper().writeValueAsString(message);
			} catch (JsonProcessingException e) {
				logger.error("There is some problem in parsing the json for queue message body");
			}
		}
		return sqsmessage;
	}

	private com.mli.mpro.posseller.models.InputRequest settingInputRequest(ProposalDetails proposalDetails) {

		logger.info("Request creation intiate for PosSeller api for transactionId {}",
				proposalDetails.getTransactionId());
		com.mli.mpro.posseller.models.InputRequest inputRequest = new com.mli.mpro.posseller.models.InputRequest();
		com.mli.mpro.posseller.models.Request requestpayload = new com.mli.mpro.posseller.models.Request();
		com.mli.mpro.posseller.models.Header header = new com.mli.mpro.posseller.models.Header();
		com.mli.mpro.posseller.models.Payload payload = new com.mli.mpro.posseller.models.Payload();

		header.setSoaAppId("FULFILLMENT");
		header.setSoaCorrelationId(UUID.randomUUID().toString());

		try {
			payload.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
			payload.setAgentName(proposalDetails.getSourcingDetails().getAgentName());
			payload.setAgentEmail(proposalDetails.getSourcingDetails().getAgentEmail());
			payload.setMobileNumber(String.valueOf(proposalDetails.getSourcingDetails().getAgentMobileNumber()));
			List<String> documentNames = null;
			List<DocumentDetails> uploadedFinancialDocs = null;
			List<DocumentDetails> requiredDocuments = proposalDetails.getUnderwritingServiceDetails()
					.getUnderwritingStatus().getRequiredDocuments();
			if (!CollectionUtils.isEmpty(requiredDocuments)) {
				List<DocumentDetails> standarddocs = requiredDocuments.stream().filter(dd -> dd.isRequiredForMproUi()
						&& !dd.ismProDocumentStatus() && (dd.getType().equalsIgnoreCase(STANDARD_DOC) || dd.getType().equalsIgnoreCase(FINANCIAL_MANDATORY)))
						.collect(Collectors.toList());
				List<DocumentDetails> financialdocs = requiredDocuments.stream()
						.filter(dd -> dd.isRequiredForMproUi() && (dd.getType().equalsIgnoreCase(FINANCIAL)))
						.collect(Collectors.toList());

				if (!CollectionUtils.isEmpty(financialdocs)) {
					uploadedFinancialDocs = financialdocs.stream()
							.filter(DocumentDetails::ismProDocumentStatus)
							.collect(Collectors.toList());
				}
				if (CollectionUtils.isEmpty(uploadedFinancialDocs) && !CollectionUtils.isEmpty(financialdocs)
						&& !CollectionUtils.isEmpty(standarddocs)) {
					standarddocs.addAll(financialdocs);
					documentNames = standarddocs.stream().map(DocumentDetails::getDocumentName)
							.collect(Collectors.toList());
				}  else if (CollectionUtils.isEmpty(uploadedFinancialDocs) && !CollectionUtils.isEmpty(financialdocs)){
					documentNames = financialdocs.stream().map(DocumentDetails::getDocumentName)
							.collect(Collectors.toList());
				} else if (!CollectionUtils.isEmpty(standarddocs)) {
					documentNames = standarddocs.stream().map(DocumentDetails::getDocumentName)
							.collect(Collectors.toList());
				}
			}
			payload.setDocumentNames(documentNames);
			requestpayload.setPayload(payload);
			requestpayload.setHeader(header);
			inputRequest.setRequest(requestpayload);
		} catch (Exception ex) {
			logger.error("Exception while calling Setting PosSeller API data request and message is {} ",
					Utility.getExceptionAsString(ex));
		}
		return inputRequest;

	}

	@Override
	@Async("ThreadPoolTaskExecutorBean")
	public void sendSms(MessageRequest messageRequest) {
		if (messageRequest != null
				&& messageRequest.getInputRequest().getRequest().getPayload().getMobileNumber().length() > 1) {
			logger.info("Making Sms Input Request for transaction id {}", messageRequest.getTransactionId());
			com.mli.mpro.sms.responsemodels.OutputResponse response = new com.mli.mpro.sms.responsemodels.OutputResponse();
			String emailStatus = null;
			try {
				//TODO for sending CCMS notification set EmailDetails Object in NotificationSenderService.
				SmsDetails smsDetails = smsRepository.findByDocumentType("POSSELLER");
				notificationSenderService = notificationSenderService.getInstance(notificationSenderService.isCCMSFlow(smsDetails));
				NotificationSender notificationSender = new NotificationSender();
				notificationSender.setSmsDetails(smsDetails);
				notificationSender.setProposalDetails(proposalRepository.findByTransactionId(messageRequest.getTransactionId()));
				notificationSender.setInputRequest(initInputRequest(messageRequest, notificationSenderService, notificationSender));
				emailStatus = notificationSenderService.aSyncSendSms();
			} catch (Exception ex) {
				logger.error("SMS API failed for transaction id {} and message is {}",
						messageRequest.getTransactionId(), Utility.getExceptionAsString(ex));
			}
			if (AppConstants.STATUS_SUCCESS.equalsIgnoreCase(emailStatus)) {
				logger.info("Sms sent successfully for transaction id {}", messageRequest.getTransactionId());
			}

		}
	}

	private com.mli.mpro.sms.requestmodels.OutputResponse initInputRequest(MessageRequest messageRequest, NotificationSenderService notificationSenderService, NotificationSender notificationSender){
		com.mli.mpro.sms.requestmodels.OutputResponse inputRequest = new com.mli.mpro.sms.requestmodels.OutputResponse();
		Payload payload = messageRequest.getInputRequest().getRequest().getPayload();
		GeneralConsumerInformation info = new GeneralConsumerInformation();
		MliSmsService service = new MliSmsService();
		RequestBody request = new RequestBody();
		RequestHeader header = new RequestHeader();
		info.setConsumerId(messageRequest.getInputRequest().getRequest().getHeader().getSoaAppId());
		info.setCorrelationId(messageRequest.getInputRequest().getRequest().getHeader().getSoaCorrelationId());
		info.setMessageVersion("1.0");
		header.setGeneralConsumerInformation(info);
		service.setRequestHeader(header);
		request.setAppAccId(ACC_ID);
		request.setAppId(APP_ID);
		com.mli.mpro.sms.models.RequestPayload smsPayload = new com.mli.mpro.sms.models.RequestPayload();
		smsPayload.setMessageText(MESSAGE_BODY_POS_SELLER);
		String distributorName = StringUtils.capitalize(payload.getAgentName());
		smsPayload.setPolicyNumber(payload.getPolicyNumber());
		smsPayload.setMessageTo(payload.getMobileNumber());
		smsPayload.setSellerName(distributorName);
		smsPayload.setType("POSSELLER");
		smsPayload.setTransactionNumber(messageRequest.getTransactionId());
		notificationSenderService.initNotificationSender(notificationSender.setSoaSmsPayload(smsPayload));
		String messageBody = smsPayload.getMessageText().replace("SELLERNAME", distributorName)
				.replace("POLICYNUMBER", payload.getPolicyNumber());
		request.setMsgText(messageBody);
		request.setMsgTo(smsPayload.getMessageTo());
		request.setAppAccPass("1234");
		service.setRequestBody(request);
		inputRequest.setMliSmsService(service);
		return inputRequest;
	}

	@Override
	@Async("ThreadPoolTaskExecutorBean")
	public void sendEmail(MessageRequest messageRequest) {
		String emailStatus = null;
		if (messageRequest != null
				&& !StringUtils.isEmpty(messageRequest.getInputRequest().getRequest().getPayload().getAgentEmail())) {
			logger.info("Making Email Input Request for transaction id {}", messageRequest.getTransactionId());
			com.mli.mpro.posseller.email.models.OutputResponse outputResponse = null;

			try {
				notificationSenderService = notificationSenderService.getInstance(false);
				emailStatus = notificationSenderService.aSyncSendEmail(initInputRequestPosEmail(messageRequest));
			} catch (Exception ex) {
				logger.info("Exception email  Posseller fail for transactionId {} is {}", messageRequest.getTransactionId(), Utility.getExceptionAsString(ex));
			}
			if (AppConstants.STATUS_SUCCESS.equalsIgnoreCase(emailStatus)) {
				logger.info("email sent successfully for posseller with transactionId {}",
						messageRequest.getTransactionId());
			}
		}

	}

	private InputRequest initInputRequestPosEmail(MessageRequest messageRequest){
		com.mli.mpro.posseller.email.models.InputRequest inputRequest = new com.mli.mpro.posseller.email.models.InputRequest();
		Payload payload = messageRequest.getInputRequest().getRequest().getPayload();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String date = formatter.format(new Date());
		Header header = new Header();
		header.setSoaAppId(messageRequest.getInputRequest().getRequest().getHeader().getSoaAppId());
		header.setSoaCorrelationId(messageRequest.getInputRequest().getRequest().getHeader().getSoaCorrelationId());
		Request request = new Request();
		RequestData requestData = new RequestData();
		requestData.setConsolidate(false);
		requestData.setFileAttached(false);
		requestData.setFromName(AppConstants.FROM_NAME);
		String distributorName = StringUtils.capitalize(payload.getAgentName());
		requestData.setFromEmail(AppConstants.EMAIL_FROM);
		EmailDetails emailData = emailRepo.findByDocumentType("Pos-Seller");
		if (emailData != null) {
			String emailBody = emailData.getMailBody();
			String emailContent = emailBody.replace("PROPOSALNO", payload.getPolicyNumber()).replace("DATE", date)
					.replace("REFERENCENO", String.valueOf(messageRequest.getTransactionId()))
					.replace("DOCUMENTCODE", "Need to fix").replace("Salutation", "Need to fix")
					.replace("DISTRIBUTORNAME", distributorName);
			StringBuilder s = new StringBuilder();
			for (String documentName : payload.getDocumentNames()) {
				s.append(".  " + documentName + "<br>");
			}
			String finalEmailBody = emailContent.replace("DOCUMENTNAME", s.toString());
			requestData.setMailBody(finalEmailBody);
		}

		requestData.setMailIdTo(payload.getAgentEmail());
		requestData.setMailSubject("Document(s) Not Submitted For Proposal No. " + payload.getPolicyNumber());
		request.setHeader(header);
		request.setRequestData(requestData);
		inputRequest.setRequest(request);
		return inputRequest;
	}

	@Async
	@Override
	public void getPosvTriggerDetailsForEmail() {
		try {
			Date startDate = new DateTime().minusHours(4).toDate();
			Date endDate = new DateTime().minusMinutes(60).toDate();
			List<ProposalDetails> proposalList = proposalRepository.findByApplicationDetailsUpdatedTime(startDate,
					endDate);

			if (!CollectionUtils.isEmpty(proposalList)) {
				for (int i = 0; i < proposalList.size(); i++) {
					ProposalDetails proposalDetails = proposalList.get(i);
					PosvPosEmailRequest posvPosEmailRequest = settingEmailData(proposalDetails);
					sendEmailToPosSellerandCustomer(posvPosEmailRequest);
				}
			} else {
				logger.error("No Data found for pos-seller after posv trigger");
			}

		} catch (Exception e) {
			logger.error("Exception while fetching the data from DB and sent email for Pos and message is {}",
					Utility.getExceptionAsString(e));
		}

	}

	private PosvPosEmailRequest settingEmailData(ProposalDetails proposalDetails) {
		PosvPosEmailRequest posvPosEmailRequest = new PosvPosEmailRequest();
		try {
			List<PartyInformation> partyInformation = proposalDetails.getPartyInformation();
			BasicDetails proposerBasicDeatails = partyInformation.get(0).getBasicDetails();
			PersonalIdentification personalIdentification = partyInformation.get(0).getPersonalIdentification();
			long transactionId = proposalDetails.getTransactionId();
			String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
			String agentName = proposalDetails.getSourcingDetails().getSpecifiedPersonName();
			String agentEmail = proposalDetails.getSourcingDetails().getAgentEmail();
			String policyHolderName = getFullName(proposerBasicDeatails.getFirstName(),
					proposerBasicDeatails.getMiddleName(), proposerBasicDeatails.getLastName());
			String policyHolderEmail = personalIdentification.getEmail();

			posvPosEmailRequest.setTransactionId(transactionId);
			posvPosEmailRequest.setPolicyNumber(policyNumber);
			posvPosEmailRequest.setAgentName(agentName);
			posvPosEmailRequest.setAgentEmail(agentEmail);
			posvPosEmailRequest.setPolicyHolderName(policyHolderName);
			posvPosEmailRequest.setPolicyHolderEmail(policyHolderEmail);
		} catch (Exception e) {
			logger.error("Exception while creating Request for Posv Email for pos journey data and message is {} ",
					Utility.getExceptionAsString(e));
		}
		return posvPosEmailRequest;
	}

	private String getFullName(String firstName, String middleName, String lastName) {
		String fullName = "";
		try {
			String revisedFirstName = firstName.trim();
			String revisedlastName = lastName.trim();
			if (!StringUtils.isEmpty(middleName)) {
				String revisedmiddleName = middleName.trim();
				fullName = revisedFirstName.concat(" ").concat(revisedmiddleName).concat(" ").concat(revisedlastName);
			} else {
				fullName = revisedFirstName.concat(" ").concat(revisedlastName);
			}
			fullName = fullName.trim();
		} catch (Exception e) {
			logger.error(
					"Exception while calling Setting full name for Posv Email for pos journey data and message is {} ",
					Utility.getExceptionAsString(e));
		}
		return fullName;

	}

	private void sendEmailToPosSellerandCustomer(PosvPosEmailRequest emailDetails) {
		logger.info("Making Pos Email Input Request for transaction id {}", emailDetails.getTransactionId());
		com.mli.mpro.posseller.email.models.OutputResponse outputResponse = null;

		String agentEmail = emailDetails.getAgentEmail();
		String customerEmail = emailDetails.getPolicyHolderEmail();
		String emailStatus = null;
		//TODO for sending CCMS notification set EmailDetails Object in NotificationSenderService.
		notificationSenderService = notificationSenderService.getInstance(false);
		if (!StringUtils.isEmpty(agentEmail)) {
			try {
				emailStatus = notificationSenderService
						.aSyncSendEmail(initInputRequestForPosSellerOrCustomer(agentEmail,customerEmail,emailDetails,AppConstants.INPUT_REQUEST_TYPE_AGENT));
			} catch (Exception ex) {
				logger.info("Exception in Trigger Posv email  Posseller agent fail for transactionId {} is {}", emailDetails.getTransactionId(), Utility.getExceptionAsString(ex));
			}
			if (AppConstants.STATUS_SUCCESS.equalsIgnoreCase(emailStatus)) {
				logger.info("Trigger Posv email sent successfully for posseller agent with transactionId {}",
						emailDetails.getTransactionId());
			}
		}

		if (!StringUtils.isEmpty(customerEmail)) {
			try {
				emailStatus = notificationSenderService
						.aSyncSendEmail(initInputRequestForPosSellerOrCustomer(agentEmail,customerEmail,emailDetails,AppConstants.INPUT_REQUEST_TYPE));
			} catch (Exception ex) {
				logger.info("Exception in Trigger Posv email  Posseller Customer fail for transactionId {} is {}", emailDetails.getTransactionId(), Utility.getExceptionAsString(ex));
			}
			if (AppConstants.STATUS_SUCCESS.equalsIgnoreCase(emailStatus)) {
				updateEmailStatus(true, emailDetails.getTransactionId());
				logger.info("Trigger Posv email sent successfully for posseller Customer with transactionId {}",
						emailDetails.getTransactionId());
			} else {
				updateEmailStatus(false, emailDetails.getTransactionId());
			}

		}

	}

	private com.mli.mpro.posseller.email.models.InputRequest initInputRequestForPosSellerOrCustomer(String agentEmail, String customerEmail, PosvPosEmailRequest emailDetails, String inputRequestType){
		com.mli.mpro.posseller.email.models.InputRequest inputRequest = new com.mli.mpro.posseller.email.models.InputRequest();
		Header header = new Header();
		Request request = new Request();
		RequestData requestData = new RequestData();
		if(AppConstants.INPUT_REQUEST_TYPE.equalsIgnoreCase(inputRequestType)){
			String customerName = emailDetails.getPolicyHolderName();
			EmailDetails emailData = emailRepo.findByDocumentType("PosSellerCustomer");
			String emailContentCustomer = getMailContent(emailData, emailDetails, customerName);
			requestData.setMailBody(emailContentCustomer);
			requestData.setMailIdTo(customerEmail);
			request.setRequestData(requestData);
			inputRequest.setRequest(request);
		} else {
			String distributorName = emailDetails.getAgentName();
			EmailDetails emailData = emailRepo.findByDocumentType("PosSellerAgent");
			String emailContentAgent = getMailContent(emailData, emailDetails,distributorName);
			header.setSoaAppId("FULFILLMENT");
			header.setSoaCorrelationId(UUID.randomUUID().toString());
			requestData.setConsolidate(false);
			requestData.setFileAttached(false);
			requestData.setFromName(AppConstants.FROM_NAME);
			requestData.setFromEmail(AppConstants.EMAIL_FROM);
			request.setHeader(header);
			requestData.setMailSubject(
					"Delay in OTP Verification for Proposal No. " + emailDetails.getPolicyNumber());
			requestData.setMailBody(emailContentAgent);
			requestData.setMailIdTo(agentEmail);
			request.setRequestData(requestData);
			inputRequest.setRequest(request);
		}
		return inputRequest;

	}

	private String getMailContent(EmailDetails emailData, PosvPosEmailRequest emailDetails, String name) {
		String emailFinalContent = "";
		SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.MONTH_FORMAT);
		String date = formatter.format(new Date());
		if (emailData != null) {
			String emailBody = emailData.getMailBody();
			emailFinalContent = emailBody.replace("PROPOSALNO", emailDetails.getPolicyNumber()).replace("DATE", date)
					.replace("REFERENCENO", emailDetails.getPolicyNumber()).replace("NAME", name);
		} else {
			logger.info("Email Data not found for transactionId {}", emailDetails.getTransactionId());
		}
		return emailFinalContent;
	}

	private void updateEmailStatus(boolean emailStatus, long transactionId) {
		try {
			Query query = null;
			Update update = null;
			query = new Query();
			update = new Update();
			FindAndModifyOptions options = new FindAndModifyOptions();
			options.returnNew(true);
			options.upsert(true);
			query.addCriteria(Criteria.where("transactionId").is(transactionId));
			update.set("additionalFlags.posEmailStatus", emailStatus);
			mongoOperation.findAndModify(query, update, options, ProposalDetails.class);
		} catch (Exception ex) {
			logger.error("Exception in updateCOIFlagInDB {}", ex.getMessage());
		}
	}

}
