package com.mli.mpro.location.services.impl;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.agent.models.*;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.amlulip.training.model.*;
import com.mli.mpro.location.login.model.MsgInfo;
import com.mli.mpro.location.login.model.Result;
import com.mli.mpro.location.login.model.MsgInfo;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.*;
import com.mli.mpro.location.models.soaCloudModels.SoaAuthResponse;
import com.mli.mpro.location.models.soaCloudModels.SoaClient360ResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.SoaResponse;
import com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary.AgentCommissionSummaryRequest;
import com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary.AgentCommissionSummaryResponse;
import com.mli.mpro.location.models.soaCloudModels.master360RequestModels.Request;
import com.mli.mpro.location.models.soaCloudModels.policySplittingModels.PolicySplittingRequest;
import com.mli.mpro.location.models.soaCloudModels.policySplittingModels.PolicySplittingResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels.SellerInfoPayload;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels.SellerResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.SoaCloudResponse;
import com.mli.mpro.location.repository.VerbiageMasterRepository;
import com.mli.mpro.location.services.AuthTokenService;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.otpservice.OtpServiceRequest;
import com.mli.mpro.otpservice.OtpServiceResponse;
import com.mli.mpro.onboarding.partner.model.CpdRequest;
import com.mli.mpro.onboarding.partner.model.CpdResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.LoginConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.productRestriction.util.AppConstants.AGENT_PERSONAL_DETAILS_TYPE;
import static com.mli.mpro.productRestriction.util.AppConstants.SOA_CLOUD_AUTH_TOKEN_REDIS_KEY;
import static software.amazon.awssdk.auth.credentials.internal.CredentialSourceType.ENVIRONMENT;

@Service
public class SoaCloudServiceImpl implements SoaCloudService {

    @Value("${soa.x-apigw-api-id}")
    private String xApigwApiId;
    @Value("${soa.x-api-key}")
    private String xApiKey;
    @Value("${soa.cloud.agent360Url}")
    private String agent360Url;
    @Value("${soa.cloud-loginApiUrl}")
    private String loginApiUrl;
    @Value("${soa.cloud-client360-api-Url}")
    private String client360ApiUrl;
    @Value("${soa.spCodeValidation-api-Url}")
    private String spCodeValidationApiUrl;
    @Value("${soa.master360-api-Url}")
    private String master360ApiUrl;
    @Value("${urlDetails.clientPolicyDetails.dataLakeURL}")
    private String cpdDataLakeUrl;
    @Value("${urlDetails.policySplittingDataLakeUrl}")
    private String policySplittingDataLakeUrl;
    @Value("${urlDetails.agentCommissionSummaryDataLakeUrl}")
    private String agentCommissionSummaryDataLakeUrl;

    @Value("${urlDetails.policyHistoryUrl}")
    private String policyHistoryUrl;

    @Value("${soa.private.x-apigw-api-id}")
    private String privateXApigwApiId;

    @Value("${urlDetails.private-x-api-key}")
    private String privateXApiKey;
    @Autowired
    AuthTokenService authTokenService;
    @Autowired
    OauthTokenRepository oauthTokenRepository;

    @Autowired
	private Map<String, String> headerMap;

    @Autowired
    VerbiageMasterRepository verbiageMasterRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Value("${soa.cloud.otpServiceUrl}")
    private String otpServiceUrl;

    @Value("${urlDetails.tpaHistoryUrl}")
    private String tpaHIstoryUrl;
    @Value("${soa.private.x-api-key}")
    private String tpaApiKey;
    private static Logger logger = LoggerFactory.getLogger(SoaCloudServiceImpl.class);

    @Autowired
    ConfigurationRepository configurationRepository;

    private <T> HttpEntity<?> getHttpEntityForSoaApis(T request, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AppConstants.X_APIGW_API_ID,xApigwApiId);//"x-apigw-api-id"
        headers.add(AppConstants.X_API_KEY,xApiKey);//"x-api-key"
        headers.add(AppConstants.HEADER_APP_ID, AppConstants.APP_ID_VALUE);//"appId"
        headers.add(AppConstants.AUTH, AppConstants.BEARER + token);
        logger.info("With request payload {}", request);
        return new HttpEntity<>(request, headers);
    }

    private <T> HttpEntity<?> getHttpEntityForPrivateSoaApis(T request, String token,String apikey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AppConstants.X_APIGW_API_ID,privateXApigwApiId);//"x-apigw-api-id"
        headers.add(AppConstants.X_API_KEY,apikey);//"x-api-key"
        headers.add(AppConstants.HEADER_APP_ID, "MPRO");//"appId"
        headers.add(AppConstants.AUTH, AppConstants.BEARER + token);
        logger.info("With request payload {}", request);
        return new HttpEntity<>(request, headers);
    }
    @Override
    public AgentResponse fetchAgent360EncryptedData(SoaApiRequest<RequestPayload> agentRequest) throws UserHandledException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestPayload requestPayload = objectMapper.convertValue(agentRequest.getRequest().getPayload(),RequestPayload.class);
        if (org.springframework.util.StringUtils.hasText(headerMap.get(LoginConstants.AGENTID))) {
        	requestPayload.setAgentId(headerMap.get(LoginConstants.AGENTID));
        }
        String agentId = requestPayload.getAgentId();
        String responseJson = null;
        try {
            String actualUrl = agent360Url;
            try {
                if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.AML_ULIP_MOCK)) {
                    Configuration configuration = configurationRepository.findByType(AML_ULIP_MOCK);
                    if (!ObjectUtils.isEmpty(configuration) && org.springframework.util.StringUtils.hasText(configuration.getAgentId())) {
                        List<String> agentIds = Arrays.asList(configuration.getAgentId().split(","));
                        if (agentIds.stream().anyMatch(id -> id.equalsIgnoreCase(agentId))) {
                            actualUrl = "http://internal-wiremoc-private-alb-963411266.ap-south-1.elb.amazonaws.com/simulation/api/amlulip";
                        }
                    }
                }
            } catch (Exception exception) {
                logger.info("Exception in setting Mock Agent 360 URL for request -> {} wityh exception -> {}",
                        agentRequest, exception.getMessage());
            }
            logger.info("Fetching Agent360 data with agentId {}",agentId);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
            SoaApiRequest<RequestPayload> agent360Details = Utility.createRequestForAgent360Info(requestPayload);
            ResponseEntity<?> agentResponseEntity = callingSoaApi(agent360Details, actualUrl);
            logger.info("Agent360 service successfully called with agentId {}", agentId);
            AgentResponse agentResponse = objectMapper.convertValue(agentResponseEntity.getBody(), AgentResponse.class);
            responseJson = Utility.printJsonRequest(agentResponse);
            logger.info("Fetch Agent360 response {}", responseJson);
            if(agentResponse == null || agentResponse.getResponse() == null || agentResponse.getResponse().getPayload() == null){
                logger.info("Soa Api Agent360 Responding with payload NULL not a proper response from SOA {}", agentResponseEntity.getBody());
            }
            //FUL2-196166  Persistency Tag Mapping
            else if(Objects.nonNull(agentResponse.getResponse().getPayload().getAgtBsDtlType())){
                Utility.setAgentJoiningDt(agentResponse);
            }
            return agentResponse;
        } catch (Exception ex) {
            throw new UserHandledException(Collections.singletonList(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public <T> ResponseEntity<Result> soaCombinedLoginApiFetch(T request) throws UserHandledException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ResponseEntity<?> loginApiResponseEntity = callingSoaApi(request, loginApiUrl);
            Result result = objectMapper.convertValue(loginApiResponseEntity.getBody(), Result.class);
            logger.info(AppConstants.LOGS_SUCCESS_RESPONSE, result);
            if(result == null || result.getResponse() == null || result.getResponse().getPayload() == null){
                logger.info("Soa Api combined login responding with payload NULL not a proper response from SOA {}", loginApiResponseEntity.getBody());
            }
            settingSPCodeForTrainingAPI(result);
            result.getResponse().setMsginfo(getMsgInfoObject(objectMapper.writeValueAsString(loginApiResponseEntity.getBody()),objectMapper));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex){
            throw new UserHandledException(Collections.singletonList("Exception during calling Combined LoginAPI Ex - "+ ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void settingSPCodeForTrainingAPI(Result soaCloudResponse){
        if(soaCloudResponse.getResponse()!=null && soaCloudResponse.getResponse().getPayload() != null && !StringUtils.hasValue(soaCloudResponse.getResponse().getPayload().getSpecifiedPersonCode())){
            soaCloudResponse.getResponse().getPayload().setSpecifiedPersonCode(AppConstants.NULL);
        }
    }

    private MsgInfo getMsgInfoObject(String responseJson, ObjectMapper objectMapper) throws JsonProcessingException {
        Map<String,Object> responseMapObj = objectMapper.readValue(responseJson, Map.class);
        Map<String, Object> msgInfoMapObject = (Map<String, Object>) ((Map<String, Object>)responseMapObj.get("response")).get("msgInfo");
        MsgInfo msgInfo = objectMapper.readValue(objectMapper.writeValueAsString(msgInfoMapObject), MsgInfo.class);
        return msgInfo;
    }

    public <T> ResponseEntity<?> callingSoaApi(T request, String url) throws UserHandledException {
        logger.info("INTO Calling Soa cloud API request for url {}", url);
        return new RestTemplate()
                .postForEntity(url, getHttpEntityForSoaApis(request, getSoaAuthToken(false)),Object.class);
    }

    public <T> ResponseEntity<?> callingPrivateSoaApi(T request, String url,String apiKey) throws UserHandledException {
        logger.info("INTO Calling Soa cloud API request for url {}", url);
        return new RestTemplate()
                .postForEntity(url, getHttpEntityForPrivateSoaApis(request, getSoaAuthToken(true),apiKey),Object.class);
    }

    private String getSoaAuthToken(boolean isPrivateCall) throws UserHandledException {
        String redisKey = isPrivateCall ? "SoaCloudPrivateAuthRedisKey" : SOA_CLOUD_AUTH_TOKEN_REDIS_KEY;
        String token = oauthTokenRepository.getToken(redisKey);
        if(!StringUtils.hasValue(token)){
            logger.info("Redis token is not found, request soa to generate new token");
            SoaAuthResponse authTokenResponse = authTokenService.getAuthToken(isPrivateCall);
            if(Utility.isAnyObjectNull(authTokenResponse, authTokenResponse.getResponse(),
                    authTokenResponse.getResponse().getPayload(),
                    authTokenResponse.getResponse().getPayload().getAccessToken())){
                throw new UserHandledException(Collections.singletonList("Auth token generation exception"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            token = authTokenResponse.getResponse().getPayload().getAccessToken();
            int expiryTime = Integer.valueOf(authTokenResponse.getResponse().getPayload().getExpiresIn()) - 10;
            oauthTokenRepository.setToken(token,expiryTime,redisKey);
            return token;
        } else {
            return token;
        }
    }

    public SoaCloudResponse<SoaClient360ResponsePayload> fetchSoaClient360ApiResponse(SoaApiRequest request) throws  UserHandledException{
        try {
            String json = Utility.printJsonRequest(request);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            logger.info("SP Code validation API Request {}", json);
            ResponseEntity<?> client360ResponseEntity = callingSoaApi(request,client360ApiUrl);
            SoaCloudResponse<SoaClient360ResponsePayload> soaApiSatgCdcResponse = objectMapper.convertValue(client360ResponseEntity.getBody(), SoaCloudResponse.class);
            logger.info(AppConstants.LOGS_SUCCESS_RESPONSE, soaApiSatgCdcResponse);
            if(soaApiSatgCdcResponse == null || soaApiSatgCdcResponse.getResponse() == null || soaApiSatgCdcResponse.getResponse().getPayload() == null){
                logger.info("Soa Api combined login responding with payload NULL not a proper response from SOA {}", client360ResponseEntity.getBody());
            }
            return soaApiSatgCdcResponse;
        } catch (Exception ex){
            throw new UserHandledException(Collections.singletonList("Exception during calling Client 360 Api Ex - "+ Utility.getExceptionAsString(ex)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public SoaCloudResponse<SellerResponsePayload> fetchSOASpCodeValidationApiResponse(SoaApiRequest<SellerInfoPayload> request) throws UserHandledException {
        try {
            String serviceUrl = spCodeValidationApiUrl;
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SP_CODE_VALIDATION_MOCK) && !ENVIRONMENT.equals("prod")) {
                Configuration configurations = configurationRepository.findByKey(SP_CODE_VALIDATION_MOCK);
                if(configurations.getValue().equalsIgnoreCase("1")) {
                    serviceUrl = "http://internal-wiremoc-private-alb-963411266.ap-south-1.elb.amazonaws.com/simulation/api/spcodemock";
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ResponseEntity<?> SpCodeValidationResponseEntity = callingSoaApi(request,serviceUrl);
            SoaCloudResponse<SellerResponsePayload> soaApiSatgCdcResponse = objectMapper.convertValue(SpCodeValidationResponseEntity.getBody(), SoaCloudResponse.class);
            logger.info(AppConstants.LOGS_SUCCESS_RESPONSE, soaApiSatgCdcResponse);
            if(soaApiSatgCdcResponse == null || soaApiSatgCdcResponse.getResponse() == null || soaApiSatgCdcResponse.getResponse().getPayload() == null){
                logger.info("Soa Api SP code validation responding with payload NULL not a proper response from SOA {}", SpCodeValidationResponseEntity.getBody());
            }
            return soaApiSatgCdcResponse;
        } catch (Exception ex){
            throw new UserHandledException(Collections.singletonList("Exception during calling spCodeValidation Api Ex - "+ ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public SoaCloudResponse handelErrorResponse(UserHandledException ex, SoaApiRequest request) {
        Header header = new Header();
        header.setSoaAppId(request.getRequest().getHeader().getApplicationId());
        header.setSoaCorrelationId(request.getRequest().getHeader().getCorrelationId());

        com.mli.mpro.agent.models.MsgInfo msgInfo = new com.mli.mpro.agent.models.MsgInfo();
        msgInfo.setMsgCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        msgInfo.setMsg(HttpStatus.INTERNAL_SERVER_ERROR.name());
        msgInfo.setMsgDescription(ex.toString());

        SoaResponse soaResponse = new SoaResponse<>();
        soaResponse.setMsgInfo(msgInfo);
        soaResponse.setHeader(header);
        soaResponse.setPayload(null);

        SoaCloudResponse<Result> soaCloudResponse = new SoaCloudResponse<>();
        soaCloudResponse.setResponse(soaResponse);
        return soaCloudResponse;
    }
    @Override
    public OtpServiceResponse fetchOTPServiceResponse(OtpServiceRequest request) throws UserHandledException {
        long transactionId = request.getRequest().getPayload().getTransactionId();
        try {
            logger.info("inside fetchOTPServiceResponse data with transactionID {}",transactionId);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
            ResponseEntity<?> otpResponseEntity = callingSoaApi(request,otpServiceUrl);
            logger.info("OTP service successfully called with transactionID {}", transactionId);
            OtpServiceResponse otpServiceResponse = objectMapper.convertValue(otpResponseEntity.getBody(),OtpServiceResponse.class);
            if (Utility.isAnyObjectNull(otpServiceResponse, otpServiceResponse.getResponse(), otpServiceResponse.getResponse().getPayload())){
                logger.info("Soa Api OTP service responding with payload NULL with not a proper response and response is {}",otpServiceResponse);
            }
            otpServiceResponse.getResponse().getPayload().setTransactionId(transactionId);
            String json = Utility.printJsonRequest(otpServiceResponse);
            logger.info("OTP Service response of transactionID {} is {}",transactionId,json);
            return otpServiceResponse;

        }catch (Exception ex){
            logger.error("Getting an exception while calling OTP service soa url is {}",Utility.getExceptionAsString(ex));
            throw new UserHandledException(Collections.singletonList(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public SoaCloudResponse<com.mli.mpro.location.models.soaCloudModels.master360ResponseModels.Response> fetchMaster360Response(SoaApiRequest<Request> request) throws UserHandledException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = Utility.printJsonRequest(request);
        logger.info("Master 360 validation API Request {}", json);
        ResponseEntity<?> master360ResponseEntity = callingSoaApi(request,master360ApiUrl);
        SoaCloudResponse<com.mli.mpro.location.models.soaCloudModels.master360ResponseModels.Response> soaApiSatgCdCResponse = objectMapper.convertValue(master360ResponseEntity.getBody(), SoaCloudResponse.class);
        logger.info(AppConstants.LOGS_SUCCESS_RESPONSE, soaApiSatgCdCResponse);
        return soaApiSatgCdCResponse;
    }

    @Override
    public SoaCloudResponse<CpdResponse> fetchClientPolicyDetailResponse(SoaApiRequest<CpdRequest> request) throws UserHandledException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = Utility.printJsonRequest(request);
        ResponseEntity<?> cPDResponseEntity = callingSoaApi(request,cpdDataLakeUrl);
        SoaCloudResponse<CpdResponse> soaApiSatgCdCResponse = objectMapper.convertValue(cPDResponseEntity.getBody(), SoaCloudResponse.class);
        logger.info(AppConstants.LOGS_SUCCESS_RESPONSE, soaApiSatgCdCResponse);
        return soaApiSatgCdCResponse;
    }

    @Override
    public SoaCloudResponse<PolicySplittingResponsePayload> callPolicySplittingDataLakeAPI(SoaApiRequest<PolicySplittingRequest> request) throws UserHandledException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String json = Utility.printJsonRequest(request);
            logger.info("Policy Splitting API Request for data lake {}", json);
            ResponseEntity<?> policySplittingDataLakeResponse = callingSoaApi(request, policySplittingDataLakeUrl);
            SoaCloudResponse<PolicySplittingResponsePayload> policySplittingResponse = objectMapper.convertValue(policySplittingDataLakeResponse.getBody(), SoaCloudResponse.class);
            logger.info("Policy Splitting API Response for data lake {}", policySplittingResponse);
            return policySplittingResponse;
        } catch (Exception ex) {
            throw new UserHandledException(Collections.singletonList("Exception during calling callPolicySplittingDataLakeAPI Api Ex - " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Retryable(value = {UserHandledException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public SoaCloudResponse<AgentCommissionSummaryResponse> callAgentCommissionSummaryDataLakeAPI(SoaApiRequest<AgentCommissionSummaryRequest> request) throws UserHandledException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String json = Utility.printJsonRequest(request);
            logger.info("Agent Commission Summary API Request for data lake {}", json);
            ResponseEntity<?> agentCommissionDataLakeResponse = callingSoaApi(request, agentCommissionSummaryDataLakeUrl);
            SoaCloudResponse<AgentCommissionSummaryResponse> agentCommissionResponse = objectMapper.convertValue(agentCommissionDataLakeResponse.getBody(), SoaCloudResponse.class);
            logger.info("Agent Commission Summary API Response from data lake {}", agentCommissionResponse);
            return agentCommissionResponse;
        } catch (Exception ex) {
            throw new UserHandledException(Collections.singletonList("Exception during calling callAgentCommissionSummaryDataLakeAPI Api Ex - " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public SoaCloudResponse<PolicyHistoryResponse> callPolicyHistoryApi(SoaApiRequest<PolicyHistoryRequest> request) throws UserHandledException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //String json = Utility.printJsonRequest(request);
            //logger.info("Policy History API request for policy number {} {}", json, ((Map<String, Object>) (request.getRequest().getPayload())).get("policyNumber"));
            ResponseEntity<?> policyHoistoryApiResponse = callingPrivateSoaApi(request, policyHistoryUrl,privateXApiKey);
            PolicyHistoryResponse resp = new PolicyHistoryResponse();
            if(policyHoistoryApiResponse != null){
                resp = setVaerbiageFromMasterTable(policyHoistoryApiResponse);
            }
            SoaCloudResponse<PolicyHistoryResponse> policyhsitoryResponse = objectMapper.convertValue(resp, SoaCloudResponse.class);
            logger.info("Policy History API Response  {}", policyhsitoryResponse);
            return policyhsitoryResponse;
        }catch (Exception ex){
            throw new UserHandledException(Collections.singletonList("Exception during calling Policy History Api Ex - " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PolicyHistoryResponse setVaerbiageFromMasterTable(ResponseEntity<?> policyHoistoryApiResponse) throws UserHandledException {
        try {
            logger.info("Converting request to desired format");
            PolicyHistoryResponse response = new ObjectMapper().convertValue(policyHoistoryApiResponse.getBody(), PolicyHistoryResponse.class);
            if(!response.getResponse().getPayload().getPolicyMovementDetailsSeller().isEmpty()){
                String stageCode = response.getResponse().getPayload().getPolicyMovementDetailsSeller().get(response.getResponse().getPayload().getPolicyMovementDetailsSeller().size() - 1).getSellerBucketCode();
                logger.info("Stage code received {}",stageCode);
                List<RequirementReasons> reasonList = new ArrayList<>();
                switch (stageCode) {
                    case "DR":
                        logger.info("Inside DR");
                    case "UWD":
                        logger.info("Inside UWD");
                        reasonList = response.getResponse().getPayload().getDecisionOutcome().getDiscrepancyDetails().getDiscrepancyReasons();
                        getReasonFromTable(response, reasonList);
                        response.getResponse().getPayload().getDecisionOutcome().getDiscrepancyDetails().setDiscrepancyReasons(reasonList);
                        return response;
                    case "AI":
                        logger.info("Inside AI");
                        reasonList = response.getResponse().getPayload().getDecisionOutcome().getAddInfoDetails().getPendingRequirements();
                        getReasonFromTable(response, reasonList);
                        response.getResponse().getPayload().getDecisionOutcome().getAddInfoDetails().setPendingRequirements(reasonList);
                        return response;
                    default:
                        logger.info("Inside default");
                        return response;
                }
            }else {
                return response;
            }
        }catch (Exception e){
            logger.info("Exception occurred at setVaerbiageFromMasterTable {} ", Utility.getExceptionAsString(e));
            throw new UserHandledException(List.of(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void getReasonFromTable(PolicyHistoryResponse response, List<RequirementReasons> reasonList) {
        if (!reasonList.isEmpty()) {
            logger.info("List received from resposne with size {} {}",reasonList,reasonList.size());
           for(RequirementReasons requirementReason : reasonList){
                String clientName = getClientName(requirementReason.getPartiesAssociated(), response);
                logger.info("Client name received {}",clientName);
                VerbiageMaster detailsFromDb = verbiageMasterRepository.findByRequirementId(requirementReason.getRequirementId());
                if (Objects.nonNull(detailsFromDb)) {
                    requirementReason.setReason(detailsFromDb.getReason().replaceAll("<partyType>", clientName).replaceAll("last2Months",getFormattedTime(2,"M1"))
                             .replaceAll("last3Months", getFormattedTime(3,"M1")).replaceAll("last6Months", getFormattedTime(6,"M1")).replaceAll("last5Months",getFormattedTime(5,"M1")).replaceAll("last4Months",getFormattedTime(4,"M1"))
                            .replaceAll("last2Years", getFormattedTime(2,"Y1")).replaceAll("last3Years", getFormattedTime(3,"Y1"))
                            .replaceAll("lastFY", getFormattedTime(1,"FY")).replaceAll("last2FY", getFormattedTime(2,"FY")).replaceAll("last3FY", getFormattedTime(3,"FY"))
                            .replaceAll("currentAY", getFormattedTime(0,"AY")).replaceAll("lastAY", getFormattedTime(1,"AY")).replaceAll("last2AY", getFormattedTime(2,"AY"))
                            .replaceAll("nMonth", getFormattedTime(0,"M2")).replaceAll("n1Month", getFormattedTime(1,"M2")).replaceAll("n2Month", getFormattedTime(2,"M2"))
                            .replaceAll("n3Month", getFormattedTime(3,"M2")).replaceAll("n4Month", getFormattedTime(4,"M2")).replaceAll("n5Month", getFormattedTime(5,"M2")));
//
                    requirementReason.setSubReason(detailsFromDb.getSubReason().replaceAll("<partyType>", clientName).replaceAll("last2Months",getFormattedTime(2,"M1"))
                            .replaceAll("last3Months", getFormattedTime(3,"M1")).replaceAll("last6Months", getFormattedTime(6,"M1")).replaceAll("last2Years", getFormattedTime(2,"Y1"))
                            .replaceAll("last3Years", getFormattedTime(3,"Y1")).replaceAll("lastFY", getFormattedTime(1,"FY")).replaceAll("last2FY", getFormattedTime(2,"FY")).replaceAll("last3FY", getFormattedTime(3,"FY"))
                            .replaceAll("currentAY", getFormattedTime(0,"AY")).replaceAll("lastAY", getFormattedTime(1,"AY")).replaceAll("last2AY", getFormattedTime(2,"AY"))
                            .replaceAll("nMonth", getFormattedTime(0,"M2")).replaceAll("n1Month", getFormattedTime(1,"M2")).replaceAll("n2Month", getFormattedTime(2,"M2"))
                            .replaceAll("n3Month", getFormattedTime(3,"M2")).replaceAll("n4Month", getFormattedTime(4,"M2")).replaceAll("n5Month", getFormattedTime(5,"M2")));
                }
            }
           logger.info("Reason list after mapper {} {}",Utility.printJsonRequest(reasonList),reasonList.toString());
        }
    }

    public String getFormattedTime(int timePeriod, String category){ //1
        LocalDate currentLocalDate = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM-yy");
        String connectingString = timePeriod == 2 ? " & " : " to ";
        switch (category){
            case "M2":
                return currentLocalDate.minusMonths(timePeriod).format(monthFormatter);
            case "M1":
                return currentLocalDate.minusMonths(timePeriod).format(monthFormatter) + connectingString + currentLocalDate.minusMonths(1).format(monthFormatter);
            case "FY":
                return getFinancialYear(timePeriod);
            case "AY":
                return getPreviousAssessmentYears(timePeriod);
            case "Y1":
                return currentLocalDate.minusYears(timePeriod).getYear()+connectingString+currentLocalDate.minusYears(1).getYear();
            default:
                return "default";
        }
    }

    private String getFinancialYear(int param) {
        int currentYear = LocalDate.now().getYear();
        StringBuilder financialYears = new StringBuilder();

        for (int i = param; i > 0; i--) {
            int startYear = currentYear - i;
            int endYear = startYear + 1;
            financialYears.append("FY ").append(startYear).append("-").append(String.format("%02d", endYear % 100));
            if (i > 2) {
                financialYears.append(", ");
            } else if (i == 2) {
                financialYears.append(" & ");
            }
        }
        return financialYears.toString();
    }

    public static String getPreviousAssessmentYears(int param) {
        int currentYear = LocalDate.now().getYear();
        int financialYearEnd = currentYear + 1;  // Financial year end for the current year

        StringBuilder assessmentYears = new StringBuilder();
        if(param == 0) assessmentYears.append("AY ").append(financialYearEnd).append("-").append(String.format("%02d", (financialYearEnd+1) % 100));
        for (int i = param; i > 0; i--) {
            int startYear = financialYearEnd - i;  // Calculate the start year for the assessment year
            int endYear = startYear + 1;  // Calculate the end year for the assessment year
            assessmentYears.append("AY ").append(startYear).append("-").append(String.format("%02d", endYear % 100));

            if (i > 1) {
                if (i > 2) {
                    assessmentYears.append(", ");
                } else {
                    assessmentYears.append(" & ");
                }
            }
        }

        return assessmentYears.toString();
    }
    private String getClientName(String partyAssociated,PolicyHistoryResponse response){
        logger.info("Party associated received is {}",partyAssociated);
       switch (partyAssociated){
           case "Joint Life 1":
           case "Proposer":
               return response.getResponse().getPayload().getCustomerName();
           case "Joint Life 2":
           case "L2BI":
           case "Insured":
               return response.getResponse().getPayload().getInsuredName();
           case "Payor":
               return response.getResponse().getPayload().getPayorName();
           case "Seller":
               return response.getResponse().getPayload().getAgentName();
           case "Nominee":
               return response.getResponse().getPayload().getNomineeName();
           default:
               return "Customer";
       }
    }


    @Override
    public SoaCloudResponse<SoaResponsePayload> fetchSOAAMLTrainingDLApi(SoaAmlRequest request) throws UserHandledException {
        logger.info("ALM Training Api for request");
        RequestData data = request.getRequest().getRequestData();
        data.setType(AGENT_PERSONAL_DETAILS_TYPE);
        //As AML training (V3) And SpCode Validation (V4) APIs are merged into DL agentPersonalDetails API so call same method.
        SoaCloudResponse<SellerResponsePayload> agentPersonalDetailsResponse = fetchSOASpCodeValidationApiResponse(castIntoSellerInfoPayload(data,request.getRequest().getHeader()));
        return mapAllFiledBackToAmlTrainingResponse(agentPersonalDetailsResponse);
    }

    /*
     * Convert payload to SPCodeValidation API as both APIs merged into DL agentPersonalDetails API */
    private SoaApiRequest<SellerInfoPayload> castIntoSellerInfoPayload(RequestData data, com.mli.mpro.location.common.soa.model.Header header) throws UserHandledException {
        logger.info("Into cast sellerInfoPayload..");
        if(Utility.isAnyObjectNull(header,header.getSoaCorrelationId(),header.getSoaCorrelationId())){
            throw new UserHandledException(Arrays.asList("Invalid request header !!"), HttpStatus.BAD_REQUEST);
        }
        SellerInfoPayload sellerInfoPayload = new SellerInfoPayload(data.getId(), data.getKey1(), data.getKey2(), data.getKey3(), data.getType(), data.getTransTrackingID(),"");
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setApplicationId(header.getSoaAppId());
        requestHeader.setCorrelationId(header.getSoaCorrelationId());
        com.mli.mpro.agent.models.Request<SellerInfoPayload> request = new com.mli.mpro.agent.models.Request<>();
        request.setHeader(requestHeader);
        request.setPayload(sellerInfoPayload);
        return new SoaApiRequest<>(request);
    }
    /*
    * Mapping back the response into AML training API response to smooth transaction for UI further flow */
    private SoaCloudResponse<SoaResponsePayload> mapAllFiledBackToAmlTrainingResponse(SoaCloudResponse<SellerResponsePayload> response){
        SoaResponse<SoaResponsePayload> soaResponse = new SoaResponse<>();
        SoaCloudResponse<SoaResponsePayload> soaCloudResponse = new SoaCloudResponse<>();
        if(AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(response.getResponse().getMsgInfo().getMsgCode())){
            logger.info("response succees with status code 200");
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SellerResponsePayload sellerResponsePayload = objectMapper.convertValue(response.getResponse().getPayload(), SellerResponsePayload.class);
            List<RegistrationDetails> registrationDetails = objectMapper.convertValue(sellerResponsePayload.getRegistrationDetails(), List.class);
            logger.info("Object convert successfully registrationDetails");
            List<RADetails> raDetails = objectMapper.convertValue(sellerResponsePayload.getRaDetails(),List.class);
            logger.info("Object convert successfully raDetails");
            SoaResponsePayload soaResponsePayload = new SoaResponsePayload(registrationDetails,
                    raDetails,
                    sellerResponsePayload.getType(),
                    sellerResponsePayload.getId(),
                    sellerResponsePayload.getSpNo(),
                    sellerResponsePayload.getChannel(),
                    sellerResponsePayload.getBranchCode());
            soaResponse.setPayload(soaResponsePayload);
        }
        soaResponse.setMsgInfo(response.getResponse().getMsgInfo());
        soaResponse.setHeader(response.getResponse().getHeader());
        soaCloudResponse.setResponse(soaResponse);
        return soaCloudResponse;
    }

    @Override
    public SoaCloudResponse<PolicyHistoryResponse> getTpaHistory(SoaApiRequest<PolicyHistoryRequest> request) throws UserHandledException{
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String json = Utility.printJsonRequest(request);
            logger.info("TPA History API request for policy number {} {}", json, ((Map<String, Object>) (request.getRequest().getPayload())).get("policyNumber"));
            ResponseEntity<?> policyHoistoryApiResponse = callingPrivateSoaApi(request, tpaHIstoryUrl,tpaApiKey);
            SoaCloudResponse<PolicyHistoryResponse> policyhsitoryResponse = objectMapper.convertValue(policyHoistoryApiResponse.getBody(), SoaCloudResponse.class);
            logger.info("TPA History API Response  {}", policyhsitoryResponse);
            return policyhsitoryResponse;
        }catch (Exception ex){
            throw new UserHandledException(Collections.singletonList("Exception during calling TPA History Api Ex - " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

