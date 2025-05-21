package com.mli.mpro.tmb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.service.SequenceService;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.location.models.OTPDetails;
import com.mli.mpro.location.repository.OTPDetailsRepository;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.tmb.model.*;
import com.mli.mpro.tmb.repository.OnboardingEventStateRepository;
import com.mli.mpro.tmb.service.CustomerDetailsService;
import com.mli.mpro.tmb.service.TmbService;
import com.mli.mpro.tmb.utility.JWEEncryptionDecryptionUtil;
import com.mli.mpro.tmb.utility.ServiceConstants;
import com.mli.mpro.tmb.utility.Utility;
import com.mli.mpro.utils.EncryptionDecryptionOnboardingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java.time.Instant;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.tmb.utility.ServiceConstants.*;

@Service
public class TmbServiceImpl implements TmbService {
    private static final Logger log = LoggerFactory.getLogger(TmbServiceImpl.class);
    @Value("${tmb.oauth.token.otp.url}")
    String cognitoTokenUrl;
    @Value("${tmb.cognito.sendOtp.request.url}")
    String sendOtpUrl;

    @Value("${tmb.cognito.otp.response.url}")
    String verifyOtpUrl;
    @Value("${tmb.oauth.token.insuranceLink.url}")
    String cognitoTokenUrlforInsuranceLink;
    @Value("${tmb.cognito.insuranceLink.request.url}")
    String insuranceLinkUrl;

    @Value("${jwt.secret.key.tmb}")
    private String jwtTokenSecretKey;
    @Value("${mpro.otp.page.url}")
    private String mproOtpUrl;  // using for mpro page otp url

    @Value("${tmb.otp.dailyLimit}")
    private Integer dailyLimit;

    @Value("${tmb.otp.instanceLimit}")
    private Integer instanceLimit;

    @Value("${tmb.otp.coolDownPeriod}")
    private Integer coolDownPeriod;

    @Value("${tmb.oauth.token.renewal.url}")
    private String renewalTokenUrl;

    @Value("${tmb.cognito.renewal.push.url}")
    private String renewalPushUrl;

    @Value("${tmb.link.expiry}")
    private String linkExpiry;

    @Autowired
    OauthTokenTMBUtility oauthToken;
    @Autowired
    TmbApiCallingUtility tmbApiCalling;

    @Autowired
    JWEEncryptionDecryptionUtil JWEEncryptionDecryptionUtil;

    @Autowired
    SequenceService sequenceService;

    @Autowired
    CustomerDetailsService customerDetailsService;

    @Autowired
    private ProposalRepository proposalRepository;


    @Autowired
    OnboardingEventStateRepository onboardingEventStateRepository;

    @Lazy
    @Autowired
    ProposerDetailsService proposerDetailsService;

    @Autowired
    private UrlShortnerService urlShortnerService;

    @Autowired
    private OTPDetailsRepository otpDetailsRepository;

    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);

    @Override
    public ResponseForOtpApi sendOtp(InputRequestOtpApi inputRequestOtpApi) {
        Long transactionId = 0L;
        String eventId = null;
        String eventType = null;
        ResponseForOtpApi responseForOtpApi = new ResponseForOtpApi();
        OnboardingState onboardingState =null;

        log.info("Request received for send otp");
        if (!Utility.validateInputRequestOtpApi(inputRequestOtpApi)) {
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_MESSAGE, BAD_REQUEST_MESSAGE);
        }

        try {
            String[] payloadArray= Utility.getTransactionIdandEventIdFromPayload(inputRequestOtpApi.getPayload());
            if(payloadArray.length==3) {
                transactionId = Long.parseLong(payloadArray[0]);
                eventId = payloadArray[1];
                eventType = payloadArray[2];}
            onboardingState = onboardingEventStateRepository.findByTransactionId(transactionId);
            log.info("send otp onboarding state fetched for transactionId: {}  onboardingState {} ", transactionId , onboardingState);
        } catch (Exception e) {
          log.error("Error occurred while fetching onboarding state for transactionId: {}", transactionId, e);
        }

        if (onboardingState == null
                || (!onboardingState.getEvents().containsKey(eventType))
                || (!onboardingState.getEvents().get(eventType).getEventId().equals(eventId))) {
            log.error("No onboarding state found for transactionId: {}", transactionId);
           return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE, INVALID_REQUEST);
        }

        if (onboardingState.getEvents().get(eventType).getOTPSubmitted()) {
            log.error("OTP already submitted for transactionId: {}", transactionId);
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,OTP_VERIFIED, OTP_ALREADY_SUBMITTED);
        }

        if (otpPageExpired(onboardingState.getEvents().get(eventType).getEventCreatedTime(), linkExpiry)) {
            log.error("OTP page expired for transactionId: {}", transactionId);
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE, OTP_PAGE_EXPIRED);
        }
        if(isOtpFloodingDetected(onboardingState.getEvents().get(eventType).getCustomerId(), dailyLimit, instanceLimit, coolDownPeriod)){
            log.error("OTP flooding detected for customer: {}", onboardingState.getEvents().get(eventType).getCustomerId());
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,OTP_FLOODING_CODE, OTP_FLOODING_MESSAGE);
        }

        return sendOtpApiCallTMB(onboardingState, eventType);

    }

    private boolean otpPageExpired(Date eventCreatedTime, String expiryTime) {
        long expiryTimeInMillis = TimeUnit.MINUTES.toMillis(Long.parseLong(expiryTime));
        long expiryTimestamp = eventCreatedTime.getTime() + expiryTimeInMillis;
        long currentTimestamp = System.currentTimeMillis();
        return currentTimestamp > expiryTimestamp;
    }

    private boolean isOtpFloodingDetected(String customerId, Integer dailyLimit, Integer instanceLimit,
                                          Integer coolDownPeriod) {

        Date fromDate = DateTimeUtils.subtractFromDate(new Date(), 30, AppConstants.TimeUnit.SECONDS);
        Date toDate = new Date();

        // Get count of OTP's for Last 30 seconds.
        List<OTPDetails> otpListLast30Seconds = otpDetailsRepository
                .findByCustomerIdAndOtpGeneratedTimeBetween(customerId, fromDate, toDate);

        if (!otpListLast30Seconds.isEmpty())
            return true;

        // Get count of OTP's within current time and cooldown period time.
        fromDate = DateTimeUtils.subtractFromDate(new Date(), coolDownPeriod, AppConstants.TimeUnit.MINUTES);
        List<OTPDetails> otpListCooldownPeriod = otpDetailsRepository
                .findByCustomerIdAndOtpGeneratedTimeBetween(customerId, fromDate, toDate);

        if (instanceLimit.equals(otpListCooldownPeriod.size()))
            return true;

        // Get count of OTP's for the current day
        fromDate = DateTimeUtils.getStartOfDayDate(new Date());
        List<OTPDetails> otpListCurrentDay = otpDetailsRepository
                .findByCustomerIdAndOtpGeneratedTimeBetween(customerId, fromDate, toDate);

        return dailyLimit.equals(otpListCurrentDay.size());

    }



    public ResponseForOtpApi sendOtpApiCallTMB(OnboardingState onboardingState,String eventType){

        TmbResponseForSentOtpApi tmbResponseForSentOtpApi=null;
        ResponseForOtpApi responseForOtpApi=new ResponseForOtpApi();
        String tmbTransactionId= Utility.generateUniqueIdforTMB() ;
        OTPDetails otpDetail = new OTPDetails();
        Date currentDate = new Date();

        try{
        String token = retryTokenGeneration("OTP", cognitoTokenUrl, SEND_OTP , onboardingState.getTransactionId());
        if (token == null) {
            log.error("Error occurred while generating token for send otp api");
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE, TMB_API_NOT_WORKING);
        }
        TmbRequestForSentOtp tmbRequestForSentOtp = new TmbRequestForSentOtp();
        tmbRequestForSentOtp.setCustomerId(onboardingState.getEvents().get(eventType).getCustomerId());
        tmbRequestForSentOtp.setTransactionId(tmbTransactionId);
        tmbRequestForSentOtp.setMode("MAX");


            String response= retryCallTmbApi(sendOtpUrl,token, objectMapper.writeValueAsString(tmbRequestForSentOtp),onboardingState,onboardingState.getTransactionId(),SEND_OTP);
            if(response!=null) {
                tmbResponseForSentOtpApi  =objectMapper.readValue(response, TmbResponseForSentOtpApi.class);
            } else {
                log.error("Error occurred while calling send otp api");
                return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE, TMB_API_NOT_WORKING);
            }

            if(tmbResponseForSentOtpApi!=null && tmbResponseForSentOtpApi.getStatuscode().equalsIgnoreCase("000")){
                log.info("OTP sent successfully to TMB for transactionId: {}",tmbTransactionId);
                onboardingState.getEvents().get(eventType).setBankCorrelationId(tmbTransactionId);
                onboardingEventStateRepository.save(onboardingState);
                otpDetail.setTransactionId(onboardingState.getTransactionId());
                otpDetail.setOtpGeneratedTime(currentDate);
                otpDetail.setCustomerId(onboardingState.getEvents().get(eventType).getCustomerId());
                log.info("For transactionId {} ,customerId {} validating OTP request saving otpValidate object {} : ",
                        otpDetail.getTransactionId(),otpDetail.getCustomerId(), otpDetail);
                otpDetailsRepository.save(otpDetail);
                responseForOtpApi.setHeader(new Header("TMB",tmbTransactionId));
                responseForOtpApi.setMsgInfo(new MsgInfo(ServiceConstants.OTP_SENT_MESSAGE,SUCCESS_RESPONSE,STATUS_SUCCESS));
                return responseForOtpApi;

            }
            else
            {
              return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE,OTP_NOT_SENT_MESSAGE);
            }
        }
        catch (JsonProcessingException ex) {
            log.error("Error occurred while sending OTP to TMB for transactionId: {}",tmbTransactionId,ex);
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE,SERVICE_UNAVAILABLE_DESC);
        }


    }

    public ResponseForOtpApi getErrorResponseForOtpApi(ResponseForOtpApi responseForOtpApi,String bankCorrelationId,String messageCode,String messageDescription){
        responseForOtpApi.setHeader(new Header("TMB", bankCorrelationId));
        responseForOtpApi.setMsgInfo(new MsgInfo(messageDescription,messageCode,STATUS_FAILURE));
        return responseForOtpApi;
    }
    public OutputRespone getErrorResponseForGenerateLink(OutputRespone outputRespone,String messageCode,String messageDescription){
        outputRespone.setHeader(new Header("TMB", BLANK));
        outputRespone.setMsgInfo(new MsgInfo(messageDescription,messageCode,STATUS_FAILURE));
        return outputRespone;
    }


    public OutputRespone generateLinkForCustomer(Payload payload) {
        OutputRespone outputRespone= new OutputRespone();
        try{
            OnboardingState onboardingState=null;
            log.info("request received for generate link for customer id {}",payload.getCustomerId());
            if(Utility.validateInputRequestGenerateLinkApi(payload)){
                return getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, BAD_REQUEST_MESSAGE);
            }
            String eventId=generateCustomUUID();
            payload.setTransactionId(getTransactionId(payload));
            boolean isNewLinkCreation = Utility.isNewLinkForPDF(payload);
            if (isNewLinkCreation) {
                onboardingState = initializeOnboardingState(payload, eventId);
            } else {
                onboardingState = onboardingEventStateRepository.findByTransactionId(Long.valueOf(payload.getTransactionId()));
                if (Utility.isNewLinkForIDF(payload)) {
                    OnboardingEvent onboardingEvent = createOnboardingEvent(payload.getCustomerId(),eventId);
                    onboardingState.getEvents().put(payload.getEventType(), onboardingEvent);
                }
                if (!validateOnboardingState(onboardingState, payload, outputRespone)) {
                    return outputRespone;
                }
                onboardingState.setLinkCreatedTime(Date.from(Instant.now()));
                onboardingState.getEvents().get(payload.getEventType()).setEventId(eventId);
            }
            onboardingEventStateRepository.save(onboardingState);
            String customerlink=generateLink(payload,eventId);
            InsuranceLinkRequest insuranceLinkRequest= insuranceLinkResponseGeneration(payload,customerlink);
            String decryptedRequestBody= objectMapper.writeValueAsString(insuranceLinkRequest);
            log.info("decrypted request body for generate link {}",decryptedRequestBody);
            String generateLinkToken= retryTokenGeneration(INSURANCE_LINK, cognitoTokenUrlforInsuranceLink, GENERATE_LINK,onboardingState.getTransactionId());
            if (generateLinkToken == null) {
                log.error("Error occurred while generating token for generate link api");
                return getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, TMB_API_NOT_WORKING);
            }
            String response = retryCallTmbApi(insuranceLinkUrl,generateLinkToken,decryptedRequestBody,onboardingState,onboardingState.getTransactionId(),GENERATE_LINK);
            if (response == null) {
                log.error("Error occurred while calling generate link api");
                outputRespone = outputResponseCreation(eventId, payload.getTransactionId());
                return getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, TMB_API_NOT_WORKING);
            }
            InsuranceLinkResponse insuranceLinkResponse = objectMapper.readValue(response,InsuranceLinkResponse.class);
            if(Objects.nonNull(insuranceLinkResponse.getStatusCode()) && !insuranceLinkResponse.getStatusCode().equalsIgnoreCase("000")){
                log.info("failure response from TMB {}", response);
                return getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, insuranceLinkResponse.getDesc());
            }
            outputRespone= outputResponseCreation(eventId,customerlink, payload.getTransactionId());
            return outputRespone;
        }catch (UserHandledException | JsonProcessingException e){
            log.error("exception occurred while calling tmb API {}",e.getMessage());
            return getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, BAD_REQUEST_MESSAGE);
        } catch (Exception e){
            log.error("Exception occurred while calling generate link api {}",e.getMessage());
            return getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, BAD_REQUEST_MESSAGE);
        }
    }

    private boolean validateOnboardingState(OnboardingState onboardingState, Payload payload, OutputRespone outputRespone) {
        boolean isValid = true;
        if (onboardingState == null) {
            log.error("No onboarding state found for transactionId: {}", payload.getTransactionId());
            outputRespone = getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, TRANSACTION_INVALID);
            return false;
        }

        if (!isLinkValid(onboardingState, payload)) {
            log.info("Cannot send another link within 30 secs for Transaction_ID {} and Event_Type {}",
                    payload.getTransactionId(), payload.getEventType());
            outputRespone = getErrorResponseForGenerateLink(outputRespone,BAD_REQUEST_CODE, LINK_INVALID);
            isValid =   false;
        }
        return isValid;
    }

    private OnboardingState initializeOnboardingState(Payload payload, String eventId) {
        OnboardingState onboardingState = new OnboardingState();
        OnboardingEvent onboardingEvent = createOnboardingEvent(payload.getCustomerId(),eventId);

        Map<String, OnboardingEvent> events = new HashMap<>();
        events.put(payload.getEventType(), onboardingEvent);
        onboardingState.setAgentId(payload.getAgentId());
        onboardingState.setCustomerId(payload.getCustomerId());
        onboardingState.setTransactionId(Long.valueOf(payload.getTransactionId()));
        onboardingState.setObjectCreatedTime(Date.from(Instant.now()));
        onboardingState.setEvents(events);

        return onboardingState;
    }

    private OnboardingEvent createOnboardingEvent(String customerId, String eventId) {

        OnboardingEvent onboardingEvent = new OnboardingEvent();
        onboardingEvent.setEventDone(false);
        onboardingEvent.setEventCreatedTime(Date.from(Instant.now()));
        onboardingEvent.setEventId(eventId);
        onboardingEvent.setOTPSubmitted(false);
        onboardingEvent.setCustomerId(customerId);
        onboardingEvent.setLinkCreatedTime(Date.from(Instant.now()));
        return onboardingEvent;
    }

    private boolean isLinkValid(OnboardingState onboardingState, Payload payload) {
        String eventType = payload.getEventType();
        Date storedTime = onboardingState.getEvents().get(eventType).getLinkCreatedTime();
        Date currentTime = Date.from(Instant.now());
        long timeDifference = (currentTime.getTime() - storedTime.getTime()) / 1000;

        if ( !Utility.isNewLinkForIDF(payload) && timeDifference <= 30) {
            return false;
        }

        Boolean otpDone = onboardingState.getEvents().get(eventType).getOTPSubmitted();
        Boolean eventDone = onboardingState.getEvents().get(eventType).getEventDone();
        return !(eventDone || otpDone);
    }

    @Override
    public ResponseForOtpApi verifyOtp(InputRequestOtpApi inputRequestOtpApi) {
        Long transactionId = 0L;
        String eventId = null;
        String eventType = null;
        ResponseForOtpApi responseForOtpApi = new ResponseForOtpApi();
        OnboardingState onboardingState =null;

        log.info("Request received for verify Otp");
        if (!Utility.validateInputRequestVerifyOtpApi(inputRequestOtpApi)) {
            return getErrorResponseForOtpApi(responseForOtpApi,BLANK,BAD_REQUEST_CODE, BAD_REQUEST_MESSAGE);
        }

        try {
            String[] payloadArray= Utility.getTransactionIdandEventIdFromPayload(inputRequestOtpApi.getPayload());
            if(payloadArray.length==3) {
                transactionId = Long.parseLong(payloadArray[0]);
                eventId = payloadArray[1];
                eventType = payloadArray[2];}
            onboardingState = onboardingEventStateRepository.findByTransactionId(transactionId);
            log.info("Verify Otp Onboarding state fetched for transactionId: {}  onboardingState {} ", transactionId , onboardingState);
        } catch (Exception e) {
            log.error("Error occurred while fetching onboarding state for transactionId: {}", transactionId, e);
        }

        if (onboardingState == null
                ||(ObjectUtils.isEmpty(onboardingState.getEvents()))
                || (!onboardingState.getEvents().containsKey(eventType))
                ||(ObjectUtils.isEmpty(onboardingState.getEvents().get(eventType)))
                || (ObjectUtils.isEmpty(onboardingState.getEvents().get(eventType).getEventId()))
                || (!onboardingState.getEvents().get(eventType).getEventId().equals(eventId))
                || (ObjectUtils.isEmpty(onboardingState.getEvents().get(eventType).getBankCorrelationId()))
                ||(!onboardingState.getEvents().get(eventType).getBankCorrelationId().equalsIgnoreCase(inputRequestOtpApi.getHeader().getCorrelationId()))
                ||(onboardingState.getEvents().get(eventType).getOTPSubmitted().equals(true) )){
            log.error("No onboarding state found for transactionId: {}", transactionId);
            return getErrorResponseForOtpApi(responseForOtpApi,inputRequestOtpApi.getHeader().getCorrelationId(),BAD_REQUEST_CODE, INVALID_REQUEST);
        }

        return verifyOtpApiCallTMB(onboardingState, eventType,inputRequestOtpApi);
    }

    public ResponseForOtpApi verifyOtpApiCallTMB(OnboardingState onboardingState,String eventType,InputRequestOtpApi inputRequestOtpApi){

        TmbResponseForSentOtpApi tmbResponseForSentOtpApi=null;
        ResponseForOtpApi responseForOtpApi=new ResponseForOtpApi();
        String tmbTransactionId= onboardingState.getEvents().get(eventType).getBankCorrelationId();

        try{
            String token = retryTokenGeneration("OTP", cognitoTokenUrl, VERIFY_OTP,onboardingState.getTransactionId());
            if (token == null) {
                log.error("Error occurred while generating token for verify otp api");
                return getErrorResponseForOtpApi(responseForOtpApi,tmbTransactionId,BAD_REQUEST_CODE, TMB_API_NOT_WORKING);
            }
            TmbRequestForSentOtp tmbRequestForSentOtp = new TmbRequestForSentOtp();
            tmbRequestForSentOtp.setCustomerId(onboardingState.getEvents().get(eventType).getCustomerId());
            tmbRequestForSentOtp.setTransactionId(tmbTransactionId);
            tmbRequestForSentOtp.setMode("MAX");
            tmbRequestForSentOtp.setOtp(inputRequestOtpApi.getHeader().getValue());


            String response= retryCallTmbApi(verifyOtpUrl,token, objectMapper.writeValueAsString(tmbRequestForSentOtp),onboardingState,onboardingState.getTransactionId(),VERIFY_OTP);
            if(response!=null) {
                tmbResponseForSentOtpApi  =objectMapper.readValue(response, TmbResponseForSentOtpApi.class);
            } else {
                log.error("Error occurred while calling verify otp api");
                return getErrorResponseForOtpApi(responseForOtpApi,tmbTransactionId,BAD_REQUEST_CODE, TMB_API_NOT_WORKING);
            }

            if(tmbResponseForSentOtpApi!=null && tmbResponseForSentOtpApi.getStatuscode().equalsIgnoreCase("000")){
                log.info("OTP verified successfully to TMB for transactionId: {}",tmbTransactionId);
                onboardingState.getEvents().get(eventType).setOTPSubmitted(true);
                onboardingEventStateRepository.save(onboardingState);
                responseForOtpApi.setHeader(new Header("TMB",tmbTransactionId));
                responseForOtpApi.setMsgInfo(new MsgInfo(ServiceConstants.OTP_VERIFIED_MESSAGE,SUCCESS_RESPONSE,STATUS_SUCCESS));
                proposerDetailsService.setProposerDetails(onboardingState, eventType);
                return responseForOtpApi;

            }
            else
            {
                return getErrorResponseForOtpApi(responseForOtpApi,tmbTransactionId,BAD_REQUEST_CODE,OTP_NOT_VERIFIED_MESSAGE);
            }
        }
        catch (JsonProcessingException ex) {
            log.error("Error occurred while sending OTP to TMB for transactionId: {}",tmbTransactionId,ex);
            return getErrorResponseForOtpApi(responseForOtpApi,tmbTransactionId,BAD_REQUEST_CODE,SERVICE_UNAVAILABLE_DESC);
        }


    }

    @Override
    public OutputRespone getOnboardingStateStatus(OnboardingStatusRequest onboardingStatusRequest){
        OutputRespone outputRespone = new OutputRespone();
        try {
            long transactionId = onboardingStatusRequest.getTransactionId();
            String eventType = onboardingStatusRequest.getEventType();
            OnboardingState onboardingState = onboardingEventStateRepository.findByTransactionId(transactionId);
            if (Objects.isNull(onboardingState) || Objects.isNull(onboardingState.getEvents()) || Objects.isNull(onboardingState.getEvents().get(eventType))) {
                log.error("No onboarding state found for transactionId: {}", onboardingStatusRequest.getTransactionId());
                return getErrorResponseForGenerateLink(outputRespone, BAD_REQUEST_CODE, INVALID_REQUEST);
            }
            Boolean eventDone = onboardingState.getEvents().get(eventType).getEventDone();
            outputRespone = onboardingStatusCreation(eventDone, String.valueOf(transactionId));
            String kyc = onboardingState.getEvents().get(eventType).getEkyc();
            outputRespone.getResponse().getResponseData().getResponsePayload().setEkyc(kyc);
        } catch (Exception e) {
            log.error("Error occurred while fetching onboarding state for transactionId: {} {}", onboardingStatusRequest.getTransactionId(), com.mli.mpro.utils.Utility.getExceptionAsString(e));
        }

        return outputRespone;
    }


    @Override
    public void renewalPush(RenewalRequest renewalRequest) {
        long transactionId  = renewalRequest.getTransactionId();
        log.info("Renewal push request received for transactionId: {}", transactionId);
        try {
            OnboardingState onboardingState = onboardingEventStateRepository.findByTransactionId(transactionId);
            if (onboardingState == null) {
                log.error("No onboarding state found for transactionId: {}", transactionId);
            }
            String token = retryTokenGeneration("inspush", renewalTokenUrl, RENEWAL_PUSH,transactionId);
            if (token == null) {
                log.error("Error occurred while generating token for renewal push api for transactionId: {}", transactionId);
                return;
            }
            String renewalString = retryCallTmbApi(renewalPushUrl, token, com.mli.mpro.utils.Utility.getJsonRequest(renewalRequest.getRenewalPushRequest()), onboardingState, transactionId, RENEWAL_PUSH);
            log.info("Renewal push response for transactionId: {} is: {}", transactionId, renewalString);
        } catch (Exception e) {
            log.error("Error occurred while updating onboarding state for transactionId: {}", transactionId, e);
        }
    }


    public String retryTokenGeneration(String tokenScope, String tokenUrl, String serviceName, long transactionId) {
        int retryCount = 0;
        int maxRetries = 3;
        String token = null;
        while (retryCount < maxRetries) {
            token = oauthToken.getAccessToken(tokenScope, tokenUrl, serviceName);
            if (token != null) {
                return token;
            }
            retryCount++;
            log.warn("Retry {} of {}: Error occurred while generating token for transactionId: {}", retryCount, maxRetries, transactionId);
        }
        return null;
    }


    public String retryCallTmbApi(String apiUrl, String token, String jsonRequest, OnboardingState onboardingState, long transactionId , String serviceName) {
        int retryCount = 0;
        int maxRetries = 3;
        String response = null;

        while (retryCount < maxRetries) {
            try {
                response = tmbApiCalling.callTmbApi(apiUrl, token, jsonRequest, onboardingState, serviceName);
                if (response == null) {
                    log.warn("Received null response for transactionId: {}. Retrying for service {} ", transactionId , serviceName);
                    retryCount++;
                    continue;
                }
                return response;
            } catch (Exception e) {
                retryCount++;
                log.warn("Retry {} of {}: Error occurred while calling TMB API for transactionId: {}", retryCount, maxRetries, transactionId, e);
            }
        }
        log.error("Failed to call TMB API after {} retries for transactionId: {}", maxRetries, transactionId);
        return null;
    }


    private OutputRespone outputResponseCreation(String eventId, String msgInfo, String transactionId) {
        OutputRespone respone = new OutputRespone();
        Response response= new Response();
        ResponseData responseData= new ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();
        responsePayload.setEventId(eventId);
        responsePayload.setMsgInfo(msgInfo);
        responsePayload.setTransactionId(transactionId);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        respone.setResponse(response);
        respone.setMsgInfo(new MsgInfo(ServiceConstants.INSURANCE_LINK_SENT,SUCCESS_RESPONSE,STATUS_SUCCESS));
        respone.setHeader(new Header("TMB", BLANK));
        return respone;
    }

    private OutputRespone outputResponseCreation(String eventId,String transactionId) {
        OutputRespone respone = new OutputRespone();
        Response response= new Response();
        ResponseData responseData= new ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();
        responsePayload.setEventId(eventId);
        responsePayload.setTransactionId(transactionId);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        respone.setResponse(response);
        return respone;
    }


    private OutputRespone onboardingStatusCreation(Boolean eventDone, String transactionId) {
        OutputRespone respone = new OutputRespone();
        Response response= new Response();
        ResponseData responseData= new ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();
        responsePayload.setTransactionId(transactionId);
        responsePayload.setEventCompleted(eventDone);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        respone.setResponse(response);
        respone.setHeader(new Header("TMB", BLANK));
        respone.setMsgInfo(new MsgInfo("","200",STATUS_SUCCESS));
        return respone;
    }
    private InsuranceLinkRequest insuranceLinkResponseGeneration(Payload payload,String customerlink){
        InsuranceLinkRequest insuranceLinkRequest= new InsuranceLinkRequest();
        insuranceLinkRequest.setCustomerId(payload.getCustomerId());
        insuranceLinkRequest.setLink(customerlink);
        return insuranceLinkRequest;

    }
    private String getTransactionId(Payload payload) throws UserHandledException {
        if(Utility.isNewLinkForPDF(payload)) {
            long transactionIdLong = sequenceService.getNextSequenceId(AppConstants.TRANSACTION_ID);
            return String.valueOf(transactionIdLong);
        }
        return payload.getTransactionId();
    }
    private String generateLink(Payload payload, String eventId){
        String customerlink=mproOtpUrl;
        String encryptLink = payload.getTransactionId()+":"+eventId+":"+payload.getEventType();
        String base64token = encrypt(encryptLink);
        return customerlink.concat(base64token);
    }

    public  String generateCustomUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        String customUUID = uuidString.substring(0, 9);
        return customUUID;
    }

    private String encrypt(String encryptLink) {
        String payload = null;
        try {
            String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
            payload = EncryptionDecryptionOnboardingUtil.encrypt(encryptLink, java.util.Base64.getDecoder().decode(key));
        } catch (Exception ex) {
            log.error("Error occurred while encrypting jwt token", ex);
        }

        return payload;
    }
}
