package com.mli.mpro.emailsms.serviceimpl;

import com.mli.mpro.ccms.model.SmsDetails;
import com.mli.mpro.ccms.repository.SmsRepository;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.email.models.EmailDetails;
import com.mli.mpro.email.models.RequestPayload;
import com.mli.mpro.email.repository.EmailRepository;
import com.mli.mpro.emailsms.service.EmailSmsService;
import com.mli.mpro.emailsms.notification.NotificationSenderService;
import com.mli.mpro.location.models.NotificationSender;
import com.mli.mpro.productRestriction.models.ProductRestrictionPayload;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.sms.requestmodels.GeneralConsumerInformation;
import com.mli.mpro.sms.requestmodels.MliSmsService;
import com.mli.mpro.sms.requestmodels.RequestBody;
import com.mli.mpro.sms.requestmodels.RequestHeader;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.utils.UtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mli.mpro.pratham.email.models.Header;
import com.mli.mpro.pratham.email.models.Request;
import com.mli.mpro.pratham.email.models.RequestData;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Service
@EnableAsync
public class EmailSmsServiceImpl implements EmailSmsService {


    private Logger logger = LoggerFactory.getLogger(EmailSmsServiceImpl.class);
    @Autowired
    private UtilityService utilityService;
    @Value("${urlDetails.email}")
    private String email;
    @Value("${urlDetails.sms}")
    private String sms;
    private static final String ACC_ID = "MAXLI_MAPPOTP";
    private static final String APP_ID = "MAXLIT";
    private static final String EMAIL_FROM = "DoNotReply@axismaxlife.com";
    private static final String FROM_NAME = "Axis Max Life Insurance";
    public static final String BLANK = "";
    private static final String MESSAGE_BODY_SELLER_PROPOSAL_REJECTION = "Dear SELLERNAME, we regret to inform that we are unable to process your proposal no XXXXXXXXX .Kindly refer to email for further details.";
    private static final String MESSAGE_BODY_CUSTOMER_PROPOSAL_REJECTION = "Dear CUSTOMERNAME, we regret to inform that we are unable to process your proposal no XXXXXXXXX .Kindly refer to email for further details.";
    private static final String REFUSAL_MAIL_SUBJECT = "Refusal of Proposal No. ";
    private static final String SELLER_NAME = "SELLERNAME";
    private static final String CUSTOMER_NAME = "CUSTOMERNAME";
    private static final String POLICY_NUMBER = "XXXXXXXXX";
    private static final String DATE_REGEX_STRING = "*Date*";
    private static final String REFERENCENO_REGEX_STR = "*Reference No.*";
    private static final String CUSTOMER_REGEX_STR = "*customer name*";
    private static final String PROPOSALNO_REGEX_STR = "**Proposal No**";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_MOBILE_NUMBER_REGEX = Pattern.compile("^\\d{10}$");


    private EmailRepository emailRepo;
    private com.mli.mpro.productRestriction.repository.ProposalRepository proposalRepository;
    private NotificationSenderService notificationSenderService;
    private SmsRepository smsRepository;

    @Autowired
    public EmailSmsServiceImpl(EmailRepository emailRepo, ProposalRepository proposalRepository,
                               NotificationSenderService notificationSenderService, SmsRepository smsRepository) {
        this.emailRepo = emailRepo;
        this.proposalRepository = proposalRepository;
        this.notificationSenderService = notificationSenderService;
        this.smsRepository = smsRepository;
    }

    //changes done for FUL2-14801,14805; proposal rejection notification
    @Override
    @Async
    public void sendEmailAndSmsForPos(ProductRestrictionPayload productRestrictionPayload) throws UserHandledException {
        long transactionId = productRestrictionPayload.getTransactionId();
        try {
            logger.info("sendEmailAndSmsForPos method called for transactionId {}", transactionId);
            ProposalDetails proposalDetails = proposalRepository.findBySourcingDetailsAgentIdAndTransactionId(productRestrictionPayload.getAgentId(), productRestrictionPayload.getTransactionId());
            //trigger EMAIL notification to Seller & customer
            RequestPayload requestPayload = new RequestPayload();
            requestPayload.setAgentId(productRestrictionPayload.getAgentId());
            requestPayload.setTransactionId(transactionId);
            requestPayload.setDocumentType(AppConstants.PROPOSAL_REJECTION);
            EmailDetails emailDetails = getEmailTemplate(requestPayload);
            //FUL2-146253 Capital Small Finance Bank
            if(!((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
                    && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
                    ||Utility.brmsBrokerEligibility(proposalDetails))) {
                sendProposalRejectionEmail(proposalDetails, requestPayload, AppConstants.SELLER, emailDetails);
                if (Boolean.TRUE.equals(proposalDetails.getSourcingDetails().getRAJourneyApplicable())) {
                    sendProposalRejectionEmail(proposalDetails, requestPayload, AppConstants.RA_LOGIN, emailDetails);
                }
            }
            sendProposalRejectionEmail(proposalDetails, requestPayload, AppConstants.CUSTOMER, emailDetails);
            //trigger SMS notification to Seller & customer
            com.mli.mpro.sms.models.RequestPayload smsPayload = new com.mli.mpro.sms.models.RequestPayload();
            smsPayload.setType(AppConstants.PROPOSAL_REJECTION);
            smsPayload.setTransactionNumber(transactionId);
            smsPayload.setAgentId(productRestrictionPayload.getAgentId());
            //FUL2-146253 Capital Small Finance Bank
            if(!((AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsCSFBChannel())
                 && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_CSFBTAGS)))
                    ||Utility.brmsBrokerEligibility(proposalDetails))) {
                sendProposalRejectionSMS(proposalDetails, smsPayload, AppConstants.SELLER);
                if (Boolean.TRUE.equals(proposalDetails.getSourcingDetails().getRAJourneyApplicable())) {
                    sendProposalRejectionSMS(proposalDetails, smsPayload, AppConstants.RA_LOGIN);
                }
            }
            sendProposalRejectionSMS(proposalDetails, smsPayload, AppConstants.CUSTOMER);
        } catch (Exception ex) {
            logger.error("Error in sendEmailAndSmsForPos method  ", ex);
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add(MessageFormat.format("Error while fetching Data from proposalDB for transactionId {0} is {1}", transactionId, ex.getMessage()));
            throw new UserHandledException(null, errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void sendProposalRejectionEmail(ProposalDetails proposalDetails, RequestPayload payload, String emailRecipientType, EmailDetails emailDetails) {
        try {
            logger.debug("sendProposalRejectionEmail method called for transactionId {}", proposalDetails.getTransactionId());
            String emailId = null;
            String emailContent;
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            String date = formatter.format(new Date());
            //FUL2-135547_Setup_of_DCB_Bank_in_Mpro
            if (Utility.isAgencyYblChannel(proposalDetails.getChannelDetails().getChannel())
                    || Utility.checkBrokerTmbChannel(proposalDetails.getChannelDetails().getChannel(),
                    proposalDetails.getSourcingDetails().getGoCABrokerCode())|| utilityService.replicaOfUjjivanChannel(proposalDetails.getChannelDetails().getChannel(),
                    proposalDetails.getSourcingDetails().getGoCABrokerCode())) {
                payload.setReferenceNumber(String.valueOf(proposalDetails.getTransactionId()));
            } else {
                payload.setReferenceNumber(Objects.nonNull(proposalDetails.getBancaDetails()) ?
                        proposalDetails.getBancaDetails().getBancaId() : AppConstants.BLANK);
            }
            String emailBody = emailDetails.getMailBody();
            emailContent = emailBody.replace(DATE_REGEX_STRING, date).replace(REFERENCENO_REGEX_STR, payload.getReferenceNumber());
            if (AppConstants.SELLER.equalsIgnoreCase(emailRecipientType)) {
                emailId = Optional.ofNullable(proposalDetails.getSourcingDetails())
                        .map(SourcingDetails::getAgentEmail)
                        .filter(agentEmail -> !StringUtils.isEmpty(agentEmail) && VALID_EMAIL_ADDRESS_REGEX.matcher(agentEmail).matches())
                        .orElseThrow(() -> new UserHandledException(null, Collections.singletonList("Email not present for seller"), HttpStatus.INTERNAL_SERVER_ERROR));
                payload.setAgentName(Objects.nonNull(proposalDetails.getSourcingDetails()) ?
                        proposalDetails.getSourcingDetails().getAgentName() : AppConstants.BLANK);
                emailContent = emailContent.replace(CUSTOMER_REGEX_STR, payload.getAgentName());
            } else if (AppConstants.CUSTOMER.equalsIgnoreCase(emailRecipientType)) {
                emailId = Optional.ofNullable(proposalDetails.getPartyInformation())
                        .flatMap(partyInformation -> partyInformation.stream().findFirst())
                        .map(PartyInformation::getPersonalIdentification)
                        .map(PersonalIdentification::getEmail)
                        .filter(customerEmail -> !StringUtils.isEmpty(customerEmail) && VALID_EMAIL_ADDRESS_REGEX.matcher(customerEmail).matches())
                        .orElseThrow(() -> new UserHandledException(null, Collections.singletonList("Email not present for Customer"), HttpStatus.INTERNAL_SERVER_ERROR));
                String customerName = StringUtils.capitalize(proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName()) + " "
                        + StringUtils.capitalize(proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName());
                payload.setCustomerName(customerName);
                emailContent = emailContent.replace(CUSTOMER_REGEX_STR, customerName);
            } else if (AppConstants.RA_LOGIN.equalsIgnoreCase(emailRecipientType)) {
                emailId = Optional.ofNullable(proposalDetails.getSourcingDetails())
                        .map(SourcingDetails::getRegionalAdvisorEmail)
                        .filter(agentEmail -> !StringUtils.isEmpty(agentEmail) && VALID_EMAIL_ADDRESS_REGEX.matcher(agentEmail).matches())
                        .orElseThrow(() -> new UserHandledException(null, Collections.singletonList("Email not present for RA"), HttpStatus.INTERNAL_SERVER_ERROR));
                payload.setAgentName(Objects.nonNull(proposalDetails.getSourcingDetails()) ?
                        proposalDetails.getSourcingDetails().getRegionalAdvisorName() : AppConstants.BLANK);
                emailContent = emailContent.replace(CUSTOMER_REGEX_STR, payload.getAgentName());
            }
            payload.setMailId(emailId);
            payload.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
            payload.setEmailSubject(REFUSAL_MAIL_SUBJECT + proposalDetails.getApplicationDetails().getPolicyNumber());
            emailContent = emailContent.replace(PROPOSALNO_REGEX_STR, payload.getPolicyNumber());

            //CCMS task changes
            //TODO for sending CCMS notification set EmailDetails Object in NotificationSenderService.
            notificationSenderService = notificationSenderService.getInstance(false);
            String emailStatus = notificationSenderService.sendEmail(initInputRequest(payload,emailContent));

            logger.info("Email Service Status {}  for Transaction id {}", emailStatus, proposalDetails.getTransactionId());
        } catch (UserHandledException ex) {
            logger.error("Caught UserHandledException in sendProposalRejectionEmail {}", !CollectionUtils.isEmpty(ex.getErrorMessages()) ? ex.getErrorMessages().get(0) : BLANK);
        } catch (Exception e) {
            logger.error("Error in sending email ", e);
        }
    }

    //changes done for FUL2-14801,14805; proposal rejection notification
    private void sendProposalRejectionSMS(ProposalDetails proposalDetails, com.mli.mpro.sms.models.RequestPayload smsPayload, String smsRecipientType) {
        try {
            logger.debug("sendProposalRejectionEmail method called for transactionId {}", proposalDetails.getTransactionId());
            smsPayload.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
            if (smsRecipientType.equalsIgnoreCase(AppConstants.SELLER)) {
                Long mobileNumber = Optional.ofNullable(proposalDetails.getSourcingDetails())
                        .map(SourcingDetails::getAgentMobileNumber)
                        .filter(agentPhone -> VALID_MOBILE_NUMBER_REGEX.matcher(String.valueOf(agentPhone)).matches())
                        .orElseThrow(() -> new UserHandledException(null, Collections.singletonList("Phone number not present for seller"), HttpStatus.INTERNAL_SERVER_ERROR));
                smsPayload.setMessageTo(String.valueOf(mobileNumber));
                smsPayload.setAgentName(Optional.ofNullable(proposalDetails.getSourcingDetails()).map(SourcingDetails::getAgentName).orElse(BLANK));
                smsPayload.setMessageText(MESSAGE_BODY_SELLER_PROPOSAL_REJECTION.replace(SELLER_NAME, smsPayload.getAgentName()).replace(POLICY_NUMBER, smsPayload.getPolicyNumber()));
            } else if (smsRecipientType.equalsIgnoreCase(AppConstants.CUSTOMER)) {
                String phoneNumber = Optional.ofNullable(proposalDetails.getPartyInformation())
                        .flatMap(partyInformation -> partyInformation.stream().findFirst())
                        .map(PartyInformation::getPersonalIdentification)
                        .map(PersonalIdentification::getPhone)
                        .flatMap(phones -> phones.stream().findFirst())
                        .map(Phone::getPhoneNumber)
                        .filter(customerPhone -> !StringUtils.isEmpty(customerPhone) && VALID_MOBILE_NUMBER_REGEX.matcher(customerPhone).matches())
                        .orElseThrow(() -> new UserHandledException(null, Collections.singletonList("Phone number not present for Customer"), HttpStatus.INTERNAL_SERVER_ERROR));
                smsPayload.setMessageTo(phoneNumber);
                String customerName = StringUtils.capitalize(proposalDetails.getPartyInformation().get(0).getBasicDetails().getFirstName()) + " "
                        + StringUtils.capitalize(proposalDetails.getPartyInformation().get(0).getBasicDetails().getLastName());
                smsPayload.setCustomerName(customerName);
                smsPayload.setMessageText(MESSAGE_BODY_CUSTOMER_PROPOSAL_REJECTION.replace(CUSTOMER_NAME, smsPayload.getCustomerName()).replace(POLICY_NUMBER, smsPayload.getPolicyNumber()));
            } else if (smsRecipientType.equalsIgnoreCase(AppConstants.RA_LOGIN)
                    && Objects.nonNull(proposalDetails.getSourcingDetails())
                    && proposalDetails.getSourcingDetails().getRAJourneyApplicable()) {
                String phoneNumber = Optional.ofNullable(proposalDetails.getSourcingDetails())
                        .map(SourcingDetails::getRegionalAdvisorMobile)
                        .filter(agentPhone -> VALID_MOBILE_NUMBER_REGEX.matcher(agentPhone).matches())
                        .orElseThrow(() -> new UserHandledException(null, Collections.singletonList("Phone number not present for RA"), HttpStatus.INTERNAL_SERVER_ERROR));
                smsPayload.setMessageTo(phoneNumber);
                smsPayload.setAgentName(Optional.ofNullable(proposalDetails.getSourcingDetails()).map(SourcingDetails::getRegionalAdvisorName).orElse(BLANK));
                smsPayload.setMessageText(MESSAGE_BODY_SELLER_PROPOSAL_REJECTION.replace(SELLER_NAME, smsPayload.getAgentName()).replace(POLICY_NUMBER, smsPayload.getPolicyNumber()));
            }
            String smsStatus = sendSMS(smsPayload, proposalDetails);
            logger.info("SMS Service Status {}  for Transaction id {}", smsStatus, proposalDetails.getTransactionId());
        } catch (UserHandledException ex) {
            logger.error("Caught UserHandledException in sendProposalRejectionSMS {}", !CollectionUtils.isEmpty(ex.getErrorMessages()) ? ex.getErrorMessages().get(0) : BLANK);
        } catch (Exception e) {
            logger.error("Error in sending sms ", e);
        }
    }


    private EmailDetails getEmailTemplate(RequestPayload payload) throws UserHandledException {
        EmailDetails details;
        try {
            String documentType = payload.getDocumentType();
            details = emailRepo.findByDocumentType(documentType);
        } catch (Exception ex) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add("cannot load email template");
            throw new UserHandledException(null, errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return details;
    }

    private com.mli.mpro.pratham.email.models.InputRequest initInputRequest(RequestPayload payload, String emailBody){
        logger.info("Init Request payload for transactionID {}", payload.getTransactionId());
        Header header = new Header();
        com.mli.mpro.pratham.email.models.InputRequest inputRequest = new com.mli.mpro.pratham.email.models.InputRequest();
        header.setSoaAppId(AppConstants.FULFILLMENT);
        header.setSoaCorrelationId(UUID.randomUUID().toString());
        Request request = new Request();
        RequestData requestData = new RequestData();
        requestData.setConsolidate(false);
        requestData.setFileAttached(false);
        requestData.setFromEmail(EMAIL_FROM);
        requestData.setFromName(FROM_NAME);
        logger.info("EMAIL ID FOR WHICH EMAIL IS BEING SENT {}", payload.getMailId());
        requestData.setMailIdTo(payload.getMailId());
        requestData.setMailBody(emailBody);
        requestData.setMailSubject(payload.getEmailSubject());
        request.setHeader(header);
        request.setRequestData(requestData);
        inputRequest.setRequest(request);
        return inputRequest;
    }


    @Override
    public String sendSMS(com.mli.mpro.sms.models.RequestPayload payload, ProposalDetails proposalDetails) {
        logger.info("sms {}", sms);
        try {
            SmsDetails smsDetails = smsRepository.findByDocumentType(payload.getType());
            notificationSenderService = notificationSenderService.getInstance(notificationSenderService.isCCMSFlow(smsDetails));
            NotificationSender notificationSender = new NotificationSender();
            notificationSender.setSmsDetails(smsDetails);
            notificationSender.setProposalDetails(proposalDetails);
            notificationSender.setInputRequest(initInputRequestSms(payload, notificationSenderService, notificationSender));
            return notificationSenderService.aSyncSendSms();
        } catch (Exception ex) {
            logger.error("Exception stack trace for sending sms to possellers and customer is {}", Utility.getExceptionAsString(ex));
            return ex.getMessage();
        }
    }

    //Code refactoring for CCMS
    private com.mli.mpro.sms.requestmodels.OutputResponse initInputRequestSms(com.mli.mpro.sms.models.RequestPayload payload
            ,NotificationSenderService notificationSenderService, NotificationSender notificationSender){
        GeneralConsumerInformation info = new GeneralConsumerInformation();
        com.mli.mpro.sms.requestmodels.OutputResponse outputRequest = new com.mli.mpro.sms.requestmodels.OutputResponse();
        MliSmsService service = new MliSmsService();
        RequestBody request = new RequestBody();
        RequestHeader header = new RequestHeader();
        info.setConsumerId(AppConstants.FULFILLMENT);
        info.setCorrelationId((UUID.randomUUID().toString()));
        info.setMessageVersion("1.0");
        header.setGeneralConsumerInformation(info);
        service.setRequestHeader(header);
        request.setAppAccId(ACC_ID);
        request.setAppId(APP_ID);
        request.setMsgText(payload.getMessageText());
        request.setMsgTo(payload.getMessageTo());
        request.setAppAccPass("1234");
        service.setRequestBody(request);
        outputRequest.setMliSmsService(service);
        notificationSenderService.initNotificationSender(notificationSender.setSoaSmsPayload(payload).setInputRequest(outputRequest));
        return outputRequest;
    }

    @Override
    public String sendEmail(RequestPayload payload, String emailBody) throws UserHandledException {
        //CCMS task changes
        //TODO for sending CCMS notification set EmailDetails Object in NotificationSenderService.
        notificationSenderService = notificationSenderService.getInstance(false);
        String emailStatus = notificationSenderService.sendEmail(initInputRequest(payload,emailBody));
        return emailStatus;
    }
}
