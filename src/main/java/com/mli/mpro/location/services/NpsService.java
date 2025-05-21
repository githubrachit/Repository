package com.mli.mpro.location.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.NpsResponsePayload;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.emailsms.notification.ApplicationContextProvider;
import com.mli.mpro.nps.model.*;
import com.mli.mpro.nps.model.Response;
import com.mli.mpro.prannumber.request.PranRequest;
import com.mli.mpro.prannumber.response.PranResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.proposal.models.NomineeDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.location.models.soaCloudModels.SoaAuthResponse;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.*;


@Service
public class NpsService {
    @Autowired
    private ProposalStreamPushService proposalStreamPushService;
    @Autowired
    private AuditService auditService;
    private static final Logger log = LoggerFactory.getLogger(NpsService.class);
    private int retryCount = 1;
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${urlDetails.npsApiId}")
    private String npsApiId;

    @Value("${urlDetails.npsApiKey}")
    private String npsApiKey;

     @Value("${urlDetails.npsUrl}")
    private String npsUrl;
    @Value("${urlDetails.policyNumber.validateUrl}")
    private String soaApiUrl;
    @Value("${soa.x-api-key}")
    private String xApiKey;
    @Value("${soa.x-apigw-api-id}")
    private String xApigwApiId;
    @Value("${soa.authToken.clientId}")
    private String clientId;
    @Value("${soa.authToken.clientSecret}")
    private String clientSecret;
    @Autowired
    private SoaCloudService soaCloudService;
    private Object UserHandledException;
    RestTemplate restTemplate = new RestTemplate();
    public NpsResponsePayload fetchNpsCustData(InputRequest inputRequest) {

        NpsResponsePayload npsResponsePayload = new NpsResponsePayload();

        try {
            Payload custData = null;
            String transactionId = inputRequest.getRequest().getRequestData().getNpsRequestPayload().getTransactionId();
            log.info("Input request for NPS API received for transaction Id{}", transactionId);
            if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLENPSAPI))) {

                com.mli.mpro.nps.model.InputRequest npsInputRequest = new com.mli.mpro.nps.model.InputRequest();
                ResponseEntity<InputResponse> npsResponse = NpsApiCall(npsInputRequest, inputRequest, transactionId);
                if (npsResponseNullCheck(npsResponse)) {
                  if(checkPranDuplicateFound(npsResponse)){
                      npsResponsePayloadset(npsResponse, npsResponsePayload);
                  }
                  else{
                    setTimestampAndServiceStatus(npsResponse, npsResponsePayload);
                  }
                    Response responseData = getResponse(npsResponse);
                    if (isDataPresent(responseData)) {
                        custData = responseData.getPayload();
                        callSaveProposalForNps(custData, transactionId);
                    } else{ saveRequestResponseInAuditDB(inputRequest, transactionId, npsResponse);
                        return npsResponsePayload;
                    }
                }
                saveRequestResponseInAuditDB(inputRequest, transactionId, npsResponse);

            } else {
                //Feature flag is false
                log.info("NPS feature flag is disabled for transactionId {}", transactionId);
                npsResponsePayload.setServiceStatus("FAILURE");
                npsResponsePayload.setRequestTimestamp(AppConstants.BLANK);
                npsResponsePayload.setResponseTimestamp(AppConstants.BLANK);
                return npsResponsePayload;
            }
        } catch (Exception e) {
            log.info("Exception occured for transactionId in fetchNpsCustData");
        }
        return npsResponsePayload;

    }

    private void npsResponsePayloadset(ResponseEntity<InputResponse> npsResponse, NpsResponsePayload npsResponsePayload) {
        npsResponsePayload.setServiceStatus(getResponse(npsResponse).getMsgInfo().getMsgCode());
        npsResponsePayload.setDataReceived(getResponse(npsResponse).getMsgInfo().getMsg());
        npsResponsePayload.setPranDuplicateFlag(true);

    }

    private boolean checkPranDuplicateFound(ResponseEntity<InputResponse> npsResponse) {
        if (npsResponse == null || npsResponse.getBody() == null ||
                npsResponse.getBody().getResponse() == null ||
                npsResponse.getBody().getResponse().getMsgInfo() == null) {
            return false;
        }

        return npsResponse.getStatusCode().value() == 409 &&
                "410".equals(npsResponse.getBody().getResponse().getMsgInfo().getMsgCode());
    }
    private Response getResponse(ResponseEntity<InputResponse> npsResponse) {
        InputResponse npsResponseBody = npsResponse.getBody();
        try {
            return Objects.nonNull(npsResponseBody) ? npsResponseBody.getResponse() : new Response();
        } catch (NullPointerException ex) {
            return new Response();
        }
    }
    public boolean isBrokerChannelNps(final String channel, String goCode) {
        log.info("Inside method isBrokerChannel {}",goCode);
        return AppConstants.CHANNEL_BROKER.equalsIgnoreCase(channel)
                && StringUtils.isNotEmpty(goCode)
                && VALID_GO_CODES.stream().map(e -> e.toUpperCase()).anyMatch(e -> e.equalsIgnoreCase(goCode));
    }

        private ResponseEntity<InputResponse> initiateNpsAPICall(com.mli.mpro.nps.model.InputRequest
                                                                     npsInputRequest, InputRequest inputRequest, String transactionId) {
        String pran = null;
        String channel = null;
        String goCode = null;
        if(inputRequest!=null && inputRequest.getRequest()!=null && inputRequest.getRequest().getRequestData()!=null && inputRequest.getRequest().getRequestData().getNpsRequestPayload()!=null)
        {
            pran = inputRequest.getRequest().getRequestData().getNpsRequestPayload().getPran();
            channel = inputRequest.getRequest().getRequestData().getNpsRequestPayload().getChannel();
            goCode = inputRequest.getRequest().getRequestData().getNpsRequestPayload().getGoCABrokerCode();
        }
        log.info("from request channel {} and goCode {} for transactionId {}", channel, goCode, transactionId);
        String resTimeStamp = "";
        String reqTimeStamp = "";
        ResponseEntity<InputResponse> npsResponse = null;
        try {
            if(isBrokerChannelNps(channel,goCode) && FeatureFlagUtil.isFeatureFlagEnabled(PRAN_NUMBER_FLAG))
            {
                log.info("feature flag value of pran number: {}", PRAN_NUMBER_FLAG);
                PranRequest pranRequest = prepareSoaApiRequest(pran);
                com.mli.mpro.prannumber.response.PranResponse response=callSoaApiForPranRestriction(pranRequest);
                if (isPranStatusExist(response)) {
                    return createErrorResponseForPran(transactionId, pran);
                }
            }
            reqTimeStamp = setTimeStamp();
            log.info("Input request for NPS customer fetch API for transactionId {} request time {} is {}", transactionId, reqTimeStamp, npsInputRequest);
            npsResponse = executeNpsApiCall(npsInputRequest, inputRequest, transactionId);
            resTimeStamp = setTimeStamp();
            setTimeStamps(npsResponse, reqTimeStamp, resTimeStamp);
        } catch (Exception e) {
            resTimeStamp = setTimeStamp();
            log.info("Exception occured in initiateNpsAPICall for transactionId-{}", transactionId);
        }

        log.info("Response for NPS customer fetch API for transactionId {} response time {} is {}", transactionId, resTimeStamp, npsResponse);
        setServiceStatus(npsResponse, transactionId);
        return npsResponse;
    }
    private ResponseEntity<InputResponse> createErrorResponseForPran(String transactionId, String pran) {
        MsgInfo msgInfo = new MsgInfo(PRAN_MSG_CODE, PRAN_MSG, PRAN_DESC);
        Response response = new Response();
        response.setMsgInfo(msgInfo);
        Payload payload= new Payload();
        payload.setPran(pran);
        response.setPayload(payload);
        InputResponse inputResponse = new InputResponse();
        inputResponse.setResponse(response);
        log.info("inputResponse {} for transactionId-{}", inputResponse, transactionId);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(inputResponse);
    }

    public boolean isPranStatusExist(com.mli.mpro.prannumber.response.PranResponse pranResponse) {
        if (pranResponse == null || pranResponse.getResponse()==null || pranResponse.getResponse().getMsgInfo() == null || pranResponse.getResponse().getPayload() == null) {
            return false;
        }
        return "200".equals(pranResponse.getResponse().getMsgInfo().getMsgCode()) && EXISTS.equals(pranResponse.getResponse().getPayload().getPranStatus());
    }

    public com.mli.mpro.prannumber.response.PranResponse callSoaApiForPranRestriction(com.mli.mpro.prannumber.request.PranRequest pranRequest) {
        com.mli.mpro.prannumber.response.PranResponse pranResponse = null;
        if (pranRequest == null) {
            log.error("Request object is null. Cannot proceed with SOA API call for PRAN restriction.");
            return createErrorResponse("Request object is null. Cannot proceed with SOA API call.");
        }
        log.info("SOA API Request for PRAN restriction: {}", pranRequest);
        try {
            ResponseEntity<?> responseEntity = soaCloudService.callingSoaApi(pranRequest, soaApiUrl);
            if (responseEntity != null && responseEntity.getBody() != null) {
                log.info("SOA api called successfully");
                pranResponse = new ObjectMapper().convertValue(responseEntity.getBody(), com.mli.mpro.prannumber.response.PranResponse.class);
                log.info("Response Received from SOA api: {}", responseEntity.getBody());
            }
            log.info("SOA API Response Entity: {}", responseEntity.getBody());
            if (responseEntity == null || responseEntity.getBody() == null) {
                log.error("Received null response from SOA API.");
                return createErrorResponse("Received null response from SOA API.");
            }
            return pranResponse;
        } catch (Exception e) {
            log.error("Error during SOA API call for PRAN restriction: {}", e.getMessage(), e);
            e.printStackTrace();
            return createErrorResponse("SOA API call failed due to an unexpected error for pran restriction.");
        }
    }

    private <T> HttpEntity<?> getHttpEntityForSoaApis(T request, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AppConstants.X_APIGW_API_ID,xApigwApiId);//"x-apigw-api-id"
        headers.add(AppConstants.X_API_KEY,xApiKey);//"x-api-key"
        headers.add(AppConstants.HEADER_APP_ID, AppConstants.APP_ID_VALUE);//"appId"
        headers.add(AppConstants.AUTH, AppConstants.BEARER + token);
        log.info("With request payload {}", request);
        return new HttpEntity<>(request, headers);
    }
    private String getSoaAuthToken(boolean isPrivateCall) throws UserHandledException {
        String redisKey = isPrivateCall ? "SoaCloudPrivateAuthRedisKey" : SOA_CLOUD_AUTH_TOKEN_REDIS_KEY;
        String token = getOauthTokenRepository().getToken(redisKey);
        if(!com.amazonaws.util.StringUtils.hasValue(token)){
            log.info("Redis token is not found, request soa to generate new token");
            SoaAuthResponse authTokenResponse = getAuthTokenService().getAuthToken(isPrivateCall);
            if(Utility.isAnyObjectNull(authTokenResponse, authTokenResponse.getResponse(),
                    authTokenResponse.getResponse().getPayload(),
                    authTokenResponse.getResponse().getPayload().getAccessToken())){
                throw new UserHandledException(Collections.singletonList("Auth token generation exception"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            token = authTokenResponse.getResponse().getPayload().getAccessToken();
            int expiryTime = Integer.valueOf(authTokenResponse.getResponse().getPayload().getExpiresIn()) - 10;
            getOauthTokenRepository().setToken(token,expiryTime,redisKey);
            return token;
        } else {
            return token;
        }
    }
    public OauthTokenRepository getOauthTokenRepository(){
        return ApplicationContextProvider.getApplicationContext().getBean(OauthTokenRepository.class);
    }
    public AuthTokenService getAuthTokenService(){
        return ApplicationContextProvider.getApplicationContext().getBean(AuthTokenService.class);
    }
    public com.mli.mpro.prannumber.response.PranResponse createErrorResponse(String errorMessage) {
        com.mli.mpro.prannumber.response.PranResponse pranResponse=new com.mli.mpro.prannumber.response.PranResponse();
        com.mli.mpro.prannumber.response.Response response = new com.mli.mpro.prannumber.response.Response();

        com.mli.mpro.prannumber.response.MsgInfo msgInfo = new com.mli.mpro.prannumber.response.MsgInfo();
        msgInfo.setMsgCode("500");
        msgInfo.setMsg("Error");
        msgInfo.setMsgDescription(errorMessage);
        response.setMsgInfo(msgInfo);
        response.setHeader(null);
        response.setPayload(null);
        pranResponse.setResponse(response);
        return pranResponse;
    }

    public PranRequest prepareSoaApiRequest(String pranNumber) {
        PranRequest pranRequest = new PranRequest();
        try {
            if (pranNumber == null) {
                log.error("pranNumber is null. Cannot prepare SOA API request.");
                return pranRequest;
            }
            com.mli.mpro.prannumber.request.Header header = new com.mli.mpro.prannumber.request.Header(UUID.randomUUID().toString(), SOAAPP_ID);
            com.mli.mpro.prannumber.request.Payload payload = new com.mli.mpro.prannumber.request.Payload(pranNumber);
            com.mli.mpro.prannumber.request.Request request = new com.mli.mpro.prannumber.request.Request(header, payload);
            pranRequest.setRequest(request);
            log.info("SOA API Request prepared for pran number restriction: {}", pranRequest);
        } catch (Exception e) {
            log.error("Error occurred while preparing SOA API Request for pranNumber: {}", pranNumber, e);
        }
        return pranRequest;
    }
    private String setTimeStamp() {
        String formattedDateTime = "";
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            formatter.setTimeZone(TimeZone.getTimeZone("IST"));
            formattedDateTime = formatter.format(today);
            log.info("timeStamp for NPS is {}", formattedDateTime);
        } catch (Exception e) {
           log.error("Exception occured in setTimeStamp-{}",e.getMessage());
        }
        return formattedDateTime;
    }

    public ResponseEntity<InputResponse> executeNpsApiCall(com.mli.mpro.nps.model.InputRequest npsInputRequest, InputRequest inputRequest, String transactionId) {
        ResponseEntity<InputResponse> response = null;
        try {
            URI npsURL = new URI(npsUrl);
            HttpEntity<com.mli.mpro.nps.model.InputRequest> httpEntity = setDataForNps(inputRequest, npsInputRequest, transactionId);
            log.info("The request sent to  NPS  service for transaction Id{}", transactionId);

            response = new RestTemplate().postForEntity(npsURL, httpEntity, InputResponse.class);

        } catch (Exception ex) {
            log.info("Error in NPS API call for transactionId {} is {}", transactionId, Utility.getExceptionAsString(ex));
        }
        log.info("Response received from Nps API for transaction Id {} with response {} ", transactionId,response);

        return response;

    }

    private HttpEntity<com.mli.mpro.nps.model.InputRequest> setDataForNps(InputRequest inputRequest, com.mli.mpro.nps.model.InputRequest npsInputRequest, String transactionId) {
        com.mli.mpro.nps.model.requestPayload.Payload payload = new com.mli.mpro.nps.model.requestPayload.Payload();
        com.mli.mpro.nps.model.Request request = new com.mli.mpro.nps.model.Request();
        com.mli.mpro.nps.model.Header header = new com.mli.mpro.nps.model.Header();
        HttpEntity<com.mli.mpro.nps.model.InputRequest> httpEntity = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            header.setSoaAppId("FULFILLMENT");
            header.setSoaCorrelationId("1-23456-7898");
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(AppConstants.NPS_API_ID, npsApiId);
            headers.add(AppConstants.NPS_API_KEY, npsApiKey);
            //nullchecks for pan and pran
            if (nullChecksForNpsPayload(inputRequest)) {
                payload.setPran(inputRequest.getRequest().getRequestData().getNpsRequestPayload().getPran());
                payload.setPanNumber(inputRequest.getRequest().getRequestData().getNpsRequestPayload().getPanNumber());
                request.setHeader(header);
                request.setPayload(payload);
                npsInputRequest.setRequest(request);
                httpEntity = new HttpEntity<>(npsInputRequest, headers);
            }
            log.info("Data set without exception for NPS transactionId {}", transactionId);
            return httpEntity;
        } catch (Exception e) {
            log.info("Exception occured in setDataForNps for transactionId-{}", transactionId);
        }
        return httpEntity;

    }

    private void callSaveProposalForNps(Payload npsCustData, String transactionId) {
        com.mli.mpro.proposal.models.InputRequest inputRequest = new com.mli.mpro.proposal.models.InputRequest();
        com.mli.mpro.proposal.models.Request request = new com.mli.mpro.proposal.models.Request();
        RequestData requestData = new RequestData();
        RequestPayload requestPayload = new RequestPayload();
        try {
            ProposalDetails proposalDetails = setProposalDetailsData(npsCustData, transactionId);
            requestPayload.setProposalDetails(proposalDetails);
            requestData.setRequestPayload(requestPayload);
            request.setRequestData(requestData);
            inputRequest.setRequest(request);
            boolean status = false;
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_ALL) && FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_PROPOSAL)) {
                status = proposalStreamPushService.produceToProposalStream(inputRequest);
            } else{
                status = proposalStreamPushService.produceToProposalQueue(inputRequest);
            }
            Thread.sleep(5000);
            String streamSentTime=setTimeStamp();
            log.info("proposalStreamPushService.produceToProposalStream Status - {} at {} ", status,streamSentTime);
        } catch (Exception ex) {
            log.error("Exception while callsaveProposalForNps {} ", Utility.getExceptionAsString(ex));
        }
    }

    private Date changeToDateFormat(String inputDate) throws ParseException {
        try {
            DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();

            LocalDateTime ldt = LocalDateTime.parse(inputDate, DATEFORMATTER);

            ZonedDateTime zonedDateTime = ZonedDateTime.of(ldt, ZoneId.of("Asia/Kolkata"));
            return Date.from(zonedDateTime.toInstant());
        }catch(Exception e){
            log.info("Exception encountered while parsing DOB");
        }
        return null;
    }

    private ResponseEntity<InputResponse> setServiceStatus(ResponseEntity<InputResponse> npsResponse, String transactionId) {
        try {
            Response response = getResponse(npsResponse);
            if (null != response && null != response.getMsgInfo() &&
                    null != response.getMsgInfo().getMsgCode() &&
                    response.getMsgInfo().getMsgCode().equalsIgnoreCase("200")) {
                response.getPayload().setServiceStatus(AppConstants.STATUS_SUCCESS);
            } else {
                if (Objects.nonNull(response)) {
                    response.getPayload().setServiceStatus(AppConstants.STATUS_FAILURE);
                }
            }
        } catch (NullPointerException e) {
            getResponse(npsResponse).getPayload().setServiceStatus(AppConstants.STATUS_FAILURE);
            log.error("null pointer exeption occured for transactionId{} {}",transactionId,e.getMessage());
        } catch (Exception e) {
            getResponse(npsResponse).getPayload().setServiceStatus(AppConstants.STATUS_FAILURE);
            log.error("Exception occured in setServiceStatus {} for transactionId",e.getMessage(),transactionId);
        }
        log.info("Service status set for transactionId {} is{}", transactionId, npsResponse);
        return npsResponse;
    }

    private void setTimestampAndServiceStatus(ResponseEntity<InputResponse> npsResponse, NpsResponsePayload npsResponsePayload) {
        if (nullchecks(npsResponse)) {
            npsResponsePayload.setServiceStatus(getResponse(npsResponse).getMsgInfo().getMsg());
            npsResponsePayload.setRequestTimestamp(getResponse(npsResponse).getPayload().getRequestTimestamp());
            npsResponsePayload.setResponseTimestamp(getResponse(npsResponse).getPayload().getResponseTimestamp());
            npsResponsePayload.setDataReceived(getResponse(npsResponse).getPayload().getDataReceived());
        }
    }

    private List<Address> setAddressData(Payload npsCustData) {
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setHouseNo(npsCustData.getPermAddrLine1());
        addressDetails.setArea(npsCustData.getPermAddrLine2());
        addressDetails.setLandmark(npsCustData.getPermAddrLine3());
        addressDetails.setCity(npsCustData.getPermAddrLine4());
        addressDetails.setState(npsCustData.getPermAddrState());
        addressDetails.setCountry(npsCustData.getPermAddrCountry());
        addressDetails.setPinCode(npsCustData.getPermAddrPincode());

        Address address = new Address();
        address.setAddressDetails(null);

        Address address2 = new Address();
        address2.setAddressDetails(addressDetails);




        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        addressList.add(address2);

        return addressList;

    }

    private List<Phone> setPhoneData(Payload npsCustData) {
        Phone phone = new Phone();
        phone.setPhoneNumber(npsCustData.getMobileNumber());
        phone.setPhoneType(AppConstants.MOBILE_NUMBER);
        phone.setStdIsdCode(AppConstants.BLANK);

        Phone phone2 = new Phone();
        phone2.setPhoneNumber(npsCustData.getAlternatePhoneNumber());
        phone2.setPhoneType(AppConstants.ALTERNATE_MOBILE_NUM);
        phone2.setStdIsdCode(AppConstants.BLANK);

        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(phone);
        phoneList.add(phone2);
        return phoneList;
    }

    private PersonalIdentification setPersonalidentificationdata(Payload npsCustData) {
        PanDetails panDetails = new PanDetails();
        panDetails.setPanNumber(npsCustData.getPan());
        AadhaarDetails aadhaarDetails = new AadhaarDetails();
        aadhaarDetails.setAadhaarNumber(npsCustData.getAadhar());
        List<Phone> phoneList = setPhoneData(npsCustData);

        PersonalIdentification personalIdentification = new PersonalIdentification();
        personalIdentification.setPranNumber(npsCustData.getPran());
        personalIdentification.setPanDetails(panDetails);
        personalIdentification.setAadhaarDetails(aadhaarDetails);
        personalIdentification.setPhone(phoneList);
        personalIdentification.setEmail(npsCustData.getEmailId());


        return personalIdentification;
    }

    private BasicDetails setBasicDetailsdata(Payload npsCustData) {
        BasicDetails basicDetails = new BasicDetails();
        try {
            String dob=Strings.isNullOrEmpty(npsCustData.getDateOfBirth())?AppConstants.BLANK:npsCustData.getDateOfBirth();
            String sex = getSex(npsCustData);
            MarriageDetails marriageDetails = setMarriageDetailsData(npsCustData);
            List<Address> address = setAddressData(npsCustData);
            basicDetails.setFirstName(npsCustData.getFirstName());
            basicDetails.setMiddleName(npsCustData.getMiddleName());
            basicDetails.setLastName(npsCustData.getLastName());

            basicDetails.setAddress(address);
            basicDetails.setGender(sex);
            basicDetails.setMarriageDetails(marriageDetails);
            basicDetails.setAreBothAddressSame(true);
            basicDetails.setEducation(AppConstants.GRADUATE_DB_CASE);
            basicDetails.setFatherName(npsCustData.getFathersFirstName() + AppConstants.WHITE_SPACE
                    + npsCustData.getFathersMiddleName() + AppConstants.WHITE_SPACE
                    + npsCustData.getFathersLastLame());
            if(!(AppConstants.BLANK.equalsIgnoreCase(dob))){
                basicDetails.setDob(changeToDateFormat(dob));

            }
            return basicDetails;
        } catch (Exception e) {
            log.info("Exception occured in setBasicDetailsdata");
        }
        return basicDetails;
    }

    private String getSex(Payload npsCustData) {
        String sex;
        sex = AppConstants.MALE.equalsIgnoreCase(npsCustData.getSex()) ? AppConstants.MALE :AppConstants.BLANK;

        if(sex.isEmpty()){
            sex =  AppConstants.FEMALE.equalsIgnoreCase(npsCustData.getSex()) ? AppConstants.FEMALE : AppConstants.BLANK;
            if(sex.isEmpty()){
                sex =  "O".equalsIgnoreCase(npsCustData.getSex()) ? AppConstants.OTHERS : AppConstants.BLANK;
            }
        }
        return sex;
    }

    private ArrayList<PartyInformation> setPartyInformationData(Payload npsCustData) {
        PersonalIdentification personalIdentification = setPersonalidentificationdata(npsCustData);
        BasicDetails basicDetails = setBasicDetailsdata(npsCustData);
        BasicDetails dependentBasicDetails = setDependentBasicDetailsdata(npsCustData);
        PartyInformation partyInformation = new PartyInformation();
        partyInformation.setPersonalIdentification(personalIdentification);
        partyInformation.setBasicDetails(basicDetails);
        partyInformation.setPartyType(AppConstants.PROPOSER_PARTY_TYPE);
        PartyInformation partyInformation2 = new PartyInformation();
        partyInformation2.setBasicDetails(dependentBasicDetails);

        ArrayList<PartyInformation> partyInformationList = new ArrayList<>();
        partyInformationList.add(partyInformation);
        partyInformationList.add(partyInformation2);
        return partyInformationList;
    }

    private Bank setBankData(Payload npsCustData) {

        BankDetails bankDetails = new BankDetails();
        bankDetails.setBankAccountNumber(npsCustData.getSubBankAccountNumber());
        bankDetails.setBankName(npsCustData.getBankName());
        bankDetails.setIfsc(npsCustData.getBankIfsc());
        bankDetails.setMicr(npsCustData.getBankMicr());
        bankDetails.setBankBranch(npsCustData.getBankBranch());
        bankDetails.setAccountHolderName(npsCustData.getFirstName()+AppConstants.WHITE_SPACE+npsCustData.getMiddleName()+AppConstants.WHITE_SPACE+npsCustData.getLastName());
        List<BankDetails> bankDetailsList = new ArrayList<>();
        bankDetailsList.add(bankDetails);
        Bank bank = new Bank();
        bank.setBankDetails(bankDetailsList);
        return bank;
    }

    private NomineeDetails setNomineeDetailsdata(Payload npsCustData,String transactionId) {
        NomineeDetails nomineeDetails = new NomineeDetails();
        try {
            ArrayList<PartyDetails> partyDetailsList = setPartyDetailsData(npsCustData,transactionId);
            nomineeDetails.setPartyDetails(partyDetailsList);
        } catch (Exception e) {
            log.info("Exception occured in setNomineeDetailsdata");
        }
        return nomineeDetails;
    }

    private CkycDetails setCkycDetailsData(Payload npsCustData) {

        CkycDetails ckyc = new CkycDetails();
        ckyc.setCkycNumber(npsCustData.getcKYC());
        String doYouHaveCkyc=AppConstants.BLANK.equalsIgnoreCase(ckyc.getCkycNumber()) ? AppConstants.NO : AppConstants.YES;
        ckyc.setDoYouHaveCKYCNumber(doYouHaveCkyc);

        return ckyc;
    }

    private ArrayList<LifeStyleDetails> setLifeStyleDetailsList(Payload npsCustData) {

        FamilyOrCriminalHistory familyOrCriminalHistory = new FamilyOrCriminalHistory();
        familyOrCriminalHistory.setAnyCriminalCharges(npsCustData.getCriminalHistory());
        if (AppConstants.YES.equalsIgnoreCase(familyOrCriminalHistory.getAnyCriminalCharges())) {
            familyOrCriminalHistory.setSpecifyDetails(npsCustData.getCriminalHistoryDetails());
        } else {
            familyOrCriminalHistory.setSpecifyDetails(AppConstants.BLANK);
        }
        LifeStyleDetails lifeStyleDetails = new LifeStyleDetails();
        lifeStyleDetails.setPartyType(AppConstants.PROPOSER_PARTY_TYPE);
        lifeStyleDetails.setFamilyOrCriminalHistory(familyOrCriminalHistory);
        ArrayList<LifeStyleDetails> lifeStyleDetailsList = new ArrayList<>();
        lifeStyleDetailsList.add(lifeStyleDetails);
        return lifeStyleDetailsList;
    }

    private AppointeeDetails setAppointeeDetailsData(Payload npsCustData,String transactionId) {

        AppointeeDetails appointeeDetails = new AppointeeDetails();
        try {
            appointeeDetails.setRelationwithNominee(npsCustData.getNomRelation());
            appointeeDetails.setGuardianName(npsCustData.getGuardianFirstName() + AppConstants.WHITE_SPACE +
                    npsCustData.getGuardianMiddleName() + AppConstants.WHITE_SPACE +
                    npsCustData.getGuardianLastName());
        }catch(Exception e){
            log.error("Exception occured while setting appointeeDetailsData for transactionId-{} {}",transactionId,e);

        }
        return appointeeDetails;
    }

    private ArrayList<PartyDetails> setPartyDetailsData(Payload npsCustData,String transactionId) {
        ArrayList<PartyDetails> partyDetailsList = new ArrayList<>();
        try {
            PartyDetails partyDetails = new PartyDetails();
            String percentageShare= Strings.isNullOrEmpty(npsCustData.getNomPercentage())
                                              ?AppConstants.BLANK
                                                 :npsCustData.getNomPercentage();
            String nomDob=Strings.isNullOrEmpty(npsCustData.getNomDOB())
                                  ?AppConstants.BLANK
                                     :npsCustData.getDateOfBirth();

            AppointeeDetails appointeeDetails = setAppointeeDetailsData(npsCustData,transactionId);
            ArrayList<Address>nomAddressList = setNomineeAddress(npsCustData,transactionId);
            ArrayList<Phone>nomPhoneList = setNomineePhone(npsCustData,transactionId);
            partyDetails.setFirstName(npsCustData.getNomFirstName());
            partyDetails.setMiddleName(npsCustData.getNomMiddleName());
            partyDetails.setLastName(npsCustData.getNomLastName());
            partyDetails.setAppointeeDetails(appointeeDetails);
            partyDetails.setNomineeAddress(nomAddressList);
            partyDetails.setNomineePhoneDetails(nomPhoneList);
            partyDetails.setNomineeEmail(npsCustData.getNomEmail());
            partyDetails.setRelationshipWithProposer(npsCustData.getNomRelation());

            if(!(percentageShare.isEmpty())){
                partyDetails.setPercentageShare(Integer.parseInt(percentageShare));
            }

            if(!(nomDob.isEmpty())){
                partyDetails.setDob(changeToDateFormat(nomDob));
            }


            partyDetailsList.add(partyDetails);
        } catch (Exception e) {
            log.error("Exception occured while setting setPartyDetailsData for transactionId-{} {}",transactionId,e);
        }
        return partyDetailsList;
    }

    private EmploymentDetails setEmployeeDetailsData(Payload npsCustData) {
        boolean politicallyExposed = AppConstants.TRUE.equalsIgnoreCase(npsCustData.getPoliticallyExposedPerson()) ? true : false;
        EmploymentDetails employmentDetails = new EmploymentDetails();
        employmentDetails.setPoliticallyExposed(politicallyExposed);
        return employmentDetails;
    }

    private ApplicationDetails setApplicationDetailsData(Payload npsCustData) {
        ApplicationDetails applicationDetails = new ApplicationDetails();
        applicationDetails.setBackendHandlerType(AppConstants.NPS_JOURNEY);
        applicationDetails.setStage(AppConstants.ONE);
        applicationDetails.setIsCkycFetchedSuccess(AppConstants.YES);
        applicationDetails.setCkycNumber(npsCustData.getcKYC());
        return applicationDetails;
    }

    private ProposalDetails setProposalDetailsData(Payload npsCustData, String transactionId) {

        ProposalDetails proposalDetails = setProposalData(npsCustData, transactionId);
        return proposalDetails;

    }

    private ProposalDetails setProposalData(Payload npsCustData, String transactionId) {

        ArrayList<PartyInformation> partyInformationList = setPartyInformationData(npsCustData);
        Bank bank = setBankData(npsCustData);
        NomineeDetails nomineeDetails = setNomineeDetailsdata(npsCustData,transactionId);
        CkycDetails ckycDetails = setCkycDetailsData(npsCustData);
        ArrayList<LifeStyleDetails> lifeStyleDetails = setLifeStyleDetailsList(npsCustData);
        EmploymentDetails employmentDetails = setEmployeeDetailsData(npsCustData);
        ApplicationDetails applicationDetails = setApplicationDetailsData(npsCustData);
        AdditionalFlags additionalFlags = setAdditionalFlags();


        ProposalDetails proposalDetails = new ProposalDetails();
        proposalDetails.setTransactionId(Long.parseLong(transactionId));
        proposalDetails.setApplicationDetails(applicationDetails);
        proposalDetails.setLifeStyleDetails(lifeStyleDetails);
        proposalDetails.setNomineeDetails(nomineeDetails);
        proposalDetails.setCkycDetails(ckycDetails);
        proposalDetails.setEmploymentDetails(employmentDetails);
        proposalDetails.setPartyInformation(partyInformationList);
        proposalDetails.setBank(bank);
        proposalDetails.setAdditionalFlags(additionalFlags);

        return proposalDetails;
    }



    private AdditionalFlags setAdditionalFlags() {

        AdditionalFlags additionalFlags = new AdditionalFlags();
        additionalFlags.setIsNpsJourney(AppConstants.YES);
        return additionalFlags;
    }

    private MarriageDetails setMarriageDetailsData(Payload npsCustData) {

        MarriageDetails marriageDetails = new MarriageDetails();
        String maritalStatus=  setmarriageDetails(npsCustData);
        marriageDetails.setMaritalStatus(maritalStatus);
        marriageDetails.setMaidenName(npsCustData.getMaidenName());
        return marriageDetails;
    }

    private boolean nullchecks(ResponseEntity<InputResponse> npsResponse) {
        if (null != getResponse(npsResponse).getPayload().getServiceStatus()
                && null != getResponse(npsResponse).getPayload().getRequestTimestamp()
                && null != getResponse(npsResponse).getPayload().getResponseTimestamp()) {
            return true;
        }
        return false;
    }

    private boolean nullChecksForNpsPayload(InputRequest inputRequest) {
        if (null != inputRequest
                && null != inputRequest.getRequest()
                && null != inputRequest.getRequest().getRequestData()
                && null != inputRequest.getRequest().getRequestData().getNpsRequestPayload()
                && null != inputRequest.getRequest().getRequestData().getNpsRequestPayload().getPran()
                && null != inputRequest.getRequest().getRequestData().getNpsRequestPayload().getPanNumber()) {
            return true;
        }
        return false;
    }

    private void setTimeStamps(ResponseEntity<InputResponse> npsResponse, String reqTimeStamp, String resTimeStamp) {
        if (null != npsResponse
                && null != npsResponse.getBody()
                && null != getResponse(npsResponse)) {
            if (null != getResponse(npsResponse).getPayload()) {
                getResponse(npsResponse).getPayload().setRequestTimestamp(reqTimeStamp);
                getResponse(npsResponse).getPayload().setResponseTimestamp(resTimeStamp);
                getResponse(npsResponse).getPayload().setDataReceived(AppConstants.YES);
            } else {
                Payload payload = new Payload();
                payload.setRequestTimestamp(reqTimeStamp);
                payload.setResponseTimestamp(resTimeStamp);
                payload.setDataReceived(AppConstants.NO);
                getResponse(npsResponse).setPayload(payload);
            }
        }
    }

    private ResponseEntity<InputResponse> NpsApiCall(com.mli.mpro.nps.model.InputRequest npsInputRequest,
                                                     InputRequest inputRequest,
                                                     String transactionId
    ) {

        ResponseEntity<InputResponse> npsResponse = null;
        try {
            npsResponse = initiateNpsAPICall(npsInputRequest, inputRequest, transactionId);
            if (npsRetryNullChecks(npsResponse) && AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(getResponse(npsResponse).getMsgInfo().getMsgCode())) {

                return npsResponse;

            }

            while (retryCount < 3) {
                retryCount++;
                NpsApiCall(npsInputRequest,
                        inputRequest,
                        transactionId);
            }
        } catch (Exception e) {
            log.info("Exception occured in NpsApiCall for transactionId-{}", transactionId);

        }
        return npsResponse;
    }

    private boolean npsRetryNullChecks(ResponseEntity<InputResponse> npsResponse) {
        if (null != npsResponse
                && null != npsResponse.getBody()
                && null != getResponse(npsResponse)
                && null != getResponse(npsResponse).getMsgInfo()
                && null != getResponse(npsResponse).getMsgInfo().getMsgCode()) {
            return true;
        }
        return false;
    }

    private boolean npsResponseNullCheck(ResponseEntity<InputResponse> npsResponse) {
        if (null != npsResponse
                && null != npsResponse.getBody()
                && null != getResponse(npsResponse)
                && null != getResponse(npsResponse).getPayload()) {
            return true;
        }
        return false;
    }

    private boolean isDataPresent(Response responseData) {
        if (null != responseData.getPayload().getFirstName()
                && null != responseData.getPayload().getAadhar()
                && null != responseData.getPayload().getPran() && null != responseData.getPayload().getPan()
                && AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(responseData.getMsgInfo().getMsgCode())) {
            return true;
        }
        return false;
    }
    private String setmarriageDetails(Payload npsCustData){
        String maritalStatus=  AppConstants.Y.equalsIgnoreCase(npsCustData.getMaritalStatus())?AppConstants.MARRIED:AppConstants.BLANK;
        if(maritalStatus.isEmpty()){
            maritalStatus=AppConstants.N.equalsIgnoreCase(npsCustData.getMaritalStatus())?AppConstants.SINGLE:AppConstants.BLANK;

        }
        return maritalStatus;

    }
    private void saveRequestResponseInAuditDB(InputRequest inputRequest, String transactionId, ResponseEntity<InputResponse> npsResponse) throws UserHandledException {
        try{
        String requestId = transactionId;
        AuditingDetails auditDetails = new AuditingDetails();
        auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
        auditDetails.setTransactionId(Long.parseLong(transactionId));
        auditDetails.setServiceName(AppConstants.NPS);
        auditDetails.setAuditId(transactionId);
        auditDetails.setRequestId(transactionId);
        ResponseObject respoObject = new ResponseObject();
        respoObject.setAdditionalProperty(AppConstants.RESPONSE, npsResponse);
        auditDetails.setResponseObject(respoObject);
        auditService.saveAuditTransactionDetailsForAgentSelf(auditDetails, requestId);
        }catch(Exception e){
            log.info("Exception occured  while saving in auditing for transactionId-{}",transactionId);

        }

    }
    private BasicDetails setDependentBasicDetailsdata(Payload npsCustData) {
        BasicDetails basicDetails = new BasicDetails();
        try {
            basicDetails.setFatherName(npsCustData.getDependentFatherFirstName() + AppConstants.WHITE_SPACE
                    + npsCustData.getDependentFatherMiddleName() + AppConstants.WHITE_SPACE
                    + npsCustData.getDependentFatherLastName());

            basicDetails.setMotherName(npsCustData.getDependentMotherFirstName() + AppConstants.WHITE_SPACE
                    + npsCustData.getDependentMotherMiddleName() + AppConstants.WHITE_SPACE
                    + npsCustData.getDependentMotherLastName());
            return basicDetails;
        } catch (Exception e) {
            log.info("Exception occured in setDependentBasicDetailsdata");
        }
        return basicDetails;
    }
    private ArrayList<Address>setNomineeAddress(Payload npsCustData,String transactionId){
        ArrayList<Address>nomAddressList = new ArrayList();
        Address address= new Address();
        AddressDetails addressDetails= new AddressDetails();
        try {
            addressDetails.setHouseNo(npsCustData.getNomAddressLine1());
            addressDetails.setArea(npsCustData.getNomAddressLine2());
            addressDetails.setLandmark(npsCustData.getNomAddressLine3());
            addressDetails.setCity(npsCustData.getNomAddressLine4());
            addressDetails.setState(npsCustData.getNomState());
            addressDetails.setPinCode(npsCustData.getNomPin());
            addressDetails.setCountry(npsCustData.getNomCountry());
            address.setAddressDetails(addressDetails);
            nomAddressList.add(address);
        }catch(Exception e){
            log.error("Exception occured while setting setNomineeAddress for transactionId-{} {}",transactionId,e);

        }
        return nomAddressList;
    }
    private ArrayList<Phone>setNomineePhone(Payload npsCustData,String transactionId){
        ArrayList<Phone>nomPhoneList= new ArrayList<>();
        try {
            Phone phone = new Phone();
            phone.setPhoneNumber(npsCustData.getNomMobile());
            nomPhoneList.add(phone);
        }catch(Exception e){
            log.error("Exception occured while setting setNomineePhone for transactionId-{} {}",transactionId,e);

        }
        return nomPhoneList;
    }

}
