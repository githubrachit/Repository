package com.mli.mpro.onboarding.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.agent.models.*;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.mfaOauthAgent360.*;
import com.mli.mpro.location.services.impl.OauthBasedAgent360ServiceImpl;
import com.mli.mpro.location.soa.service.TrainingService;
import com.mli.mpro.location.training.model.Data;
import com.mli.mpro.location.training.model.TrainingRequest;
import com.mli.mpro.onboarding.model.CustomerDetails;
import com.mli.mpro.onboarding.model.InputPayload;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.util.EncodDecodUtil;
import com.mli.mpro.onboarding.service.SuperAppService;
import com.mli.mpro.onboarding.superApp.models.*;
import com.mli.mpro.onboarding.superApp.models.Request;
import com.mli.mpro.onboarding.superApp.models.RequestPayload;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.AgentSelfConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Service
public class SuperAppImpl implements SuperAppService {

    @Autowired
    EncodDecodUtil encodDecodUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${urlDetails.broker.agent360}")
    private String agent360;
    @Value("${urlDetails.broker.client.id}")
    private String authUserName;
    @Value("${urlDetails.broker.client.secret}")
    private String authPassword;
    @Autowired
    private OauthBasedAgent360ServiceImpl oauthBasedAgent360Service;
    @Autowired
    TrainingService trainingService;
    @Value("#{${urlDetails.superAppEnabledChannels}}")
    private List<String> superAppEnabledChannels;
    @Value("#{${urlDetails.agent360RespChannelName}}")
    private List<String> agent360RespChannelName;
    @Value("#{${urlDetails.agent360RespChannelType}}")
    private List<String> agent360RespChannelType;
    @Value("#{${urlDetails.agent360RespServBrId}}")
    private List<Character> agent360RespServBrId;
    @Value("${token.expiry.limit}")
    private String tokenExpiryLimit;
    @Value("${nj.token.secret.key}")
    private String njTokenSecretKey;
    @Value("${bypass.header.encrypt.value}")
    private String api_client_secret;
    @Value("${urlDetails.proposalUrl}")
    private String proposalUrl;
    @Value("${urlDetails.mproBaseUrl}")
    private String mproBaseUrl;

    @Value("${urlDetails.dashboardUrl}")
    private String dashboardUrl;
    
    @Autowired
	private ProposalRepository proposalRepository;


    public static Logger log = LoggerFactory.getLogger(SuperAppImpl.class);
    public static String channelName = "";
    public static String source="";
    public static String destinationScreen="";
    @Override
    public RequestResponse fetchDashboardCount(RequestResponse inputRequest, MultiValueMap<String, String> authToken) {
        RequestResponse response = new RequestResponse();
        String key = encodDecodUtil.getEncDecryptKey(authToken);
        OauthBasedAgent360Response agent360response ;
        MFAAgent360InputRequest agent360request ;
        MsgRespInfo msgInfo = new MsgRespInfo();
        InputPayload payload ;//= new InputPayload();
        payload = mapRequest(inputRequest,key);
        agent360request = getagent360Requst(payload);
            if (SUPERAPP.equalsIgnoreCase(source)) {
            Map<String,Integer> responseMap = new HashMap<>();
            try {
                agent360response = loginBypass(agent360request,source);
                if(agent360response.getResponse().getErrorResponse() != null ){
                    return sendErrorResponse(String.valueOf(agent360response.getResponse().getErrorResponse().getErrorCode())
                            ,CONNECTION_ERROR,agent360response.getResponse().getErrorResponse().getErrorDescription()
                            ,authToken);
                }else if(null != agent360response.getResponse().getResponseData() && null != agent360response.getResponse().getResponseData().getToken()) {
                    responseMap =dashBoardServiceCall(agent360request.getRequest().getPayload().getAgentId());
                }
                if(!responseMap.isEmpty()){
                    msgInfo.setMsgCode(SUCCESS_RESPONSE);
                    msgInfo.setMsg(responseMap.toString());
                    msgInfo.setToken(agent360response.getResponse().getResponseData().getToken());
                    msgInfo.setMsgDescription(SUCCESS);
                }else{
                    return sendErrorResponse(INTERNAL_SERVER_ERROR_CODE,DASHBOARD_ERR_MSG, DASHBOARD_ERR_DSC,authToken);
                }
                response.setPayload(AESEncryptDecryptUtil.encrypt(objectMapper.writeValueAsString(msgInfo), encodDecodUtil.getEncDecryptKey(authToken)));
                return response;
            } catch (Exception e) {
                log.info("Inside exception block of Dashboard Count API {}",e.getMessage());
                return sendErrorResponse(INTERNAL_SERVER_ERROR_CODE,e.getMessage(), INTERNAL_SERVER_ERROR,authToken);
            }
        }else{
            return sendErrorResponse(UNAUTHORISED_ERR_CODE,UNAUTHORISED_ERR_MSG, UNAUTHORISED_ERR_DSC,authToken);
        }
    }
    private Map<String, Integer> dashBoardServiceCall(String agentId) {
        Map<String,Integer> respMap = new HashMap<>();
        DashboardInputRequest request = mapRequestForDashboard(agentId);
        ResponseEntity<String> response;// = new ResponseEntity(String.class,HttpStatus.OK);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(API_CLIENT_SECRET, api_client_secret);
            headers.set(HttpHeaders.CONTENT_TYPE,"application/json");
            HttpEntity<DashboardInputRequest> requestEntity = new HttpEntity<>(request,headers);
            log.info("Request passing to dashboard MS {}",request);
            response = new RestTemplate().exchange(dashboardUrl, HttpMethod.POST, requestEntity, String.class);
            log.info("Response received from Dashboard service {}",response.getBody());
            if(null != response.getBody()) {
                ObjectMapper om = new ObjectMapper();
                String stageInfoJson = om.readTree(response.getBody()).path(DASHBOARD_PATH_RESPONSE)
                        .path(DASHBOARD_PATH_RESPONSEDATA).path(DASHBOARD_PATH_RESPONSEPAYLOAD).path(DASHBOARD_PATH_DASHBOARDDATA).toString();
                respMap = om.readValue(stageInfoJson,Map.class);
                log.info("Converted Json {}", stageInfoJson);
            }
            log.info("Response Map {}", respMap);
            return respMap;
        }catch (Exception e){
            log.info(e.getMessage());
            return respMap;
        }
    }

    private DashboardInputRequest mapRequestForDashboard(String agentId) {
        DashboardInputRequest req = new DashboardInputRequest();
        Request request = new Request();
        Metadata metadata = new Metadata();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        metadata.setEnv(System.getenv("env"));
        metadata.setRequestId(sdf.format(new Date()));

        RequestData requestData = new RequestData();
        RequestPayload requestPayload = new RequestPayload();
        requestPayload.setAgentId(agentId);
        requestPayload.setDashboardType(SUPERAPP);
        requestPayload.setIsDashboardCountRequired(YES);


        requestData.setRequestPayload(requestPayload);
        request.setRequestData(requestData);
        request.setMetadata(metadata);
        req.setRequest(request);

        return req;
    }

    @Override
    public RequestResponse redirectNewApplication(RequestResponse inputRequest, MultiValueMap<String, String> authToken) {
        String key = encodDecodUtil.getEncDecryptKey(authToken);
        OauthBasedAgent360Response agent360response ;//= new OauthBasedAgent360Response();
        MFAAgent360InputRequest agent360request ;//= new MFAAgent360InputRequest();
        RequestResponse response = new RequestResponse();
        MsgRespInfo msgInfo = new MsgRespInfo();
        InputPayload payload ;//= new InputPayload();
        payload = mapRequest(inputRequest,key);
        agent360request = getagent360Requst(payload);
            if (SUPERAPP.equalsIgnoreCase(source)) {
            try {
                agent360response = loginBypass(agent360request,source);
                if(agent360response.getResponse().getErrorResponse() != null ){
                    return sendErrorResponse(String.valueOf(agent360response.getResponse().getErrorResponse().getErrorCode()),CONNECTION_ERROR,
                            agent360response.getResponse().getErrorResponse().getErrorDescription(),authToken);
                } else if(null != agent360response.getResponse().getResponseData() && null != agent360response.getResponse().getResponseData().getToken()) {
                    String redirectionUrl = getRedirectionUrl(agent360response,payload);
                    if("".equalsIgnoreCase(redirectionUrl)){
                        return sendErrorResponse(INTERNAL_SERVER_ERROR_CODE,CONNECTION_ERROR,
                                agent360response.getResponse().getErrorResponse().getErrorDescription(),authToken);
                    }

                    msgInfo.setMsgCode(SUCCESS_RESPONSE);
                    msgInfo.setMsg(redirectionUrl);
                    msgInfo.setMsgDescription(SUCCESS);
                    response.setPayload(AESEncryptDecryptUtil.encrypt(objectMapper.writeValueAsString(msgInfo), encodDecodUtil.getEncDecryptKey(authToken)));
                }
            } catch (Exception e) {
                log.info("Getting exception while login by pass {}",e.getMessage());
                return sendErrorResponse(INTERNAL_SERVER_ERROR_CODE,CONNECTION_ERROR,
                        "error during login Bypass ",authToken);
            }
            return response;
        }else{
            return sendErrorResponse(UNAUTHORISED_ERR_CODE,UNAUTHORISED_ERR_MSG, UNAUTHORISED_ERR_DSC,authToken);
        }
    }
    private String getRedirectionUrl(OauthBasedAgent360Response agent360response, InputPayload payload) {
		String encodedTranId = "";
		String urlParam = "";
		Long transactionId = 0L;
		HttpHeaders headers = new HttpHeaders();
		headers.set(API_CLIENT_SECRET, api_client_secret);

		switch (destinationScreen) {
		case DASHBOARD:
			encodedTranId = Base64.getEncoder().encodeToString(DASHBOARD.getBytes());
			urlParam = "&subSource=" + Base64.getEncoder().encodeToString("dashboard".getBytes());

			
			String policyNumber = payload.getData().getPolicyNumber();
			if (policyNumber != null && !policyNumber.isEmpty()) {
				log.info("Policy number found: {}", policyNumber);
				ProposalDetails proposalDetails = proposalRepository.findByApplicationDetailsPolicyNumber(policyNumber);
				if (proposalDetails != null) {
					if (proposalDetails.getApplicationDetails() != null) {
						String stage = proposalDetails.getApplicationDetails().getStage();
						log.info("Retrieved stage: {},{}", proposalDetails.getApplicationDetails().getStage(),policyNumber);

						if (stage != null && !stage.isEmpty()) {
							if (!Arrays.asList("1", "2", "3", "4", "5", "6").contains(stage)) {

								String encodedPolicyNumber = Base64.getEncoder()
										.encodeToString(policyNumber.getBytes());  
								urlParam += "&policyNumber=" + encodedPolicyNumber;
								// Construct URL without "/journey/MQ==?source="
								String redirectionUrl = mproBaseUrl + "?source="
										+ Base64.getEncoder().encodeToString(source.getBytes()) + urlParam
										+ "&apitoken=" + agent360response.getResponse().getResponseData().getToken();
								return redirectionUrl;
							} else {
								
								setAgent360ErrorMessages(agent360response, 403,
										" Policy is still in mpro stages,Cannot proceed.");
								return "";
							}
						} else {
							log.error("Policy stage is null or empty");
							setAgent360ErrorMessages(agent360response, 403,
									"Policy stage is null or empty. Cannot proceed.");
							return "";
						}
					} else {
						log.error("ApplicationDetails is null for policy number: {}", policyNumber);
						setAgent360ErrorMessages(agent360response, 403, "ApplicationDetails is null. Cannot proceed.");
						return "";
					}
				} else {
					log.error("ProposalDetails not found for policy number: {}", policyNumber);
					setAgent360ErrorMessages(agent360response, 403, "ProposalDetails not found. Cannot proceed.");
					return "";
				}

			}
			break;
		case SCREEN1:
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			transactionId = getTransactionFromProposal(new LinkedList<>());
			encodedTranId = Base64.getEncoder().encodeToString(transactionId.toString().getBytes());
			urlParam = "&transactionId=" + encodedTranId;
			break;
		case PREFILL:
			if (FeatureFlagUtil.isFeatureFlagEnabled("enableSuperAppPrefill")) {
				if (payload.getData().getCustomerDetailsList() != null
						&& !payload.getData().getCustomerDetailsList().isEmpty()
						&& validateCustomerDetails(payload.getData().getCustomerDetailsList())) {
					transactionId = getTransactionFromProposal(payload.getData().getCustomerDetailsList());
					encodedTranId = Base64.getEncoder().encodeToString(transactionId.toString().getBytes());
					urlParam = "&transactionId=" + encodedTranId;
				} else {
					setAgent360ErrorMessages(agent360response, 500,
							"Incorrect data. Please check data to be prefilled");
					return "";
				}
			} else {
				setAgent360ErrorMessages(agent360response, 503, "Prefill feature of SuperApp is switched off");
				return "";
			}
			break;
         case REDIRECTTRANSACTION:
	        
		        transactionId = payload.getData().getTransactionId();
		        log.info("Retrieved transactionId: {}", transactionId);
		        
		        // Check if the transaction ID is present in the database
		        ProposalDetails proposalDetails = proposalRepository.findByTransactionId(transactionId);
		       log.info("Transaction ID ProposalDetails found in database: {}, {}", transactionId, proposalDetails);
		        if (proposalDetails != null) {
		            encodedTranId = Base64.getEncoder().encodeToString(transactionId.toString().getBytes());
		            urlParam = "&transactionId=" + encodedTranId;
		        } else {
		        	log.error("Transaction ID {} not found in database", transactionId);
		            setAgent360ErrorMessages(agent360response, 500, "Transaction ID not found in database. Cannot proceed.");
		            return "";
		        }
		    break;
		case INVALID:
			setAgent360ErrorMessages(agent360response, 500, "Please enter correct sub source to redirect");
			return "";
		default:
			break;
		}

		if ((destinationScreen.equalsIgnoreCase("Prefill") || destinationScreen.equalsIgnoreCase(SCREEN1))
				&& transactionId == 0L) {
			setAgent360ErrorMessages(agent360response, 500, "Error while generating transaction Id");
			return "";
		}

		// Normal flow for constructing the URL
		String redirectionUrl = mproBaseUrl + "/journey/MQ==?source="
				+ Base64.getEncoder().encodeToString(source.getBytes()) + urlParam + "&apitoken="
				+ agent360response.getResponse().getResponseData().getToken();
		return redirectionUrl;
	}

    private boolean validateCustomerDetails(List<CustomerDetails> customerDetailsList) {
        Long count = customerDetailsList.stream().filter(x->
            StringUtils.isEmpty(x.getFirstName()) || StringUtils.isEmpty(x.getLastName())
                    || StringUtils.isEmpty(x.getGender()) || StringUtils.isEmpty(x.getLeadId())
                    || null == x.getDob()).count();
        return count <= 0;
    }

    private Long getTransactionFromProposal(List<CustomerDetails> customerDetailsList) {
        ResponseEntity<List> response ;//= new ResponseEntity(List.class,HttpStatus.OK);
        Long transactionId;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(API_CLIENT_SECRET, api_client_secret);
            HttpEntity<List> requestEntity= new HttpEntity<>(customerDetailsList,headers);
            response = new RestTemplate().exchange(proposalUrl,HttpMethod.POST,requestEntity,List.class);
            Map<String, Object> responseMap = new ObjectMapper().convertValue(response.getBody().get(0), Map.class);
            transactionId = Long.valueOf(responseMap.get(TRANSACTION_ID).toString());
        }catch (Exception e){
            log.info("Exception occured while calling proposal MS {}", e.getMessage());
            return 0L;
        }
        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK && transactionId != 0L){
            return transactionId;
        }else{
            return 0L;
        }
    }


    private OauthBasedAgent360Response loginBypass(MFAAgent360InputRequest agent360request,String source) throws GeneralSecurityException, JsonProcessingException {
        OauthBasedAgent360Response agent360response;
        agent360response =  getAgent360Records(agent360request);
        log.info("Response received from agent360 for login Bypass {} ",agent360response.toString());
        if(null != agent360response.getResponse() && null != agent360response.getResponse().getResponseData()) {
            if(destinationScreen.equalsIgnoreCase(SCREEN1) || destinationScreen.equalsIgnoreCase(PREFILL)) {
                checkAmlUlipTrainings(agent360response);
            }
            if (null == agent360response.getResponse().getErrorResponse()){
                String jwTToken = generateJwt(source, agent360request.getRequest().getPayload().getAgentId());
                if (!StringUtils.isEmpty(jwTToken)) {
                    agent360response.getResponse().getResponseData().setToken(jwTToken);
                }
            }
        }
        return agent360response;
    }

    private void checkAmlUlipTrainings(OauthBasedAgent360Response agent360response) {
        Boolean isAmlDone = callTrainingApi(agent360response);
        log.info("AML tag from training API {}",isAmlDone);
        if(isAmlDone){
            if(!StringUtils.isEmpty(agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType().getAmlStatus())
                    && !StringUtils.isEmpty(agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType().getUlipStatus())){

                if((agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType().getAmlStatus().equalsIgnoreCase(TWO)
                        && agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType().getUlipStatus().equalsIgnoreCase(TWO))) {

                    setAgent360ErrorMessages(agent360response, 512, AML_ULIP_DESC);
                }
            }else{
                setAgent360ErrorMessages(agent360response, 512, AML_ULIP_DESC);
            }
            if(agent360response.getResponse().getErrorResponse() == null && (StringUtils.isEmpty(agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType().getAmlStatus())
                    || agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType().getAmlStatus().equalsIgnoreCase(TWO))){
                setAgent360ErrorMessages(agent360response,513,AML_DESC);

            }
        }else{
            setAgent360ErrorMessages(agent360response,512,AML_ULIP_DESC);
        }

    }
    private void setAgent360ErrorMessages(OauthBasedAgent360Response agent360response, int errCode, String errDesc) {
        if (!Objects.nonNull(agent360response.getResponse().getErrorResponse())) {
            ErrorResponse errorResponse = new ErrorResponse();
            agent360response.getResponse().setErrorResponse(errorResponse);
            agent360response.getResponse().getErrorResponse().setErrorCode(errCode);
            agent360response.getResponse().getErrorResponse().setErrorDescription(errDesc);
        }
    }

    private Boolean callTrainingApi(OauthBasedAgent360Response agent360response) {
        TrainingRequest trainingRequest = new TrainingRequest();
        mapRequestForTrainingApi(agent360response,trainingRequest);
        try{
            ResponseEntity<Object> resp = trainingService.executeTrainingService(trainingRequest);
            if(Objects.nonNull(resp.getBody())){
                log.info("training service response for login Bypass {}",resp.getBody());
                JsonNode jsonNode = objectMapper.convertValue(resp.getBody(),JsonNode.class);
                log.info("Json Node {}",jsonNode);
                return jsonNode.path(RESULT).path(RESPONSE).path(PAYLOAD).get(AML).asBoolean();
            }
            return false;
        }catch (Exception e){
            log.info(e.getMessage());
            return false;
        }
    }

    private void mapRequestForTrainingApi(OauthBasedAgent360Response agent360response,TrainingRequest trainingRequest) {
        com.mli.mpro.location.training.model.Request request = new com.mli.mpro.location.training.model.Request();
        Data data = new Data();
        AgtBsDtlType agtBsDtlType = agent360response.getResponse().getResponseData().getAgent360Response().getAgtBsDtlType();
        AgtContrtDtlType agtContrtDtlType = agent360response.getResponse().getResponseData().getAgent360Response().getAgtContrtDtlType();

        data.setAgentCode(agtBsDtlType.getAgentId());
        data.setSpecifiedPersonCode(agtContrtDtlType.getLicenceNum());
        data.setAgentxChannel((agtBsDtlType.getServBrId().startsWith("E") || agtBsDtlType.getServBrId().startsWith("D"))? "E" : agtBsDtlType.getChannelName());
        data.setRole(agtBsDtlType.getRole());
        data.setBranchCd(null != agtBsDtlType.getServBrId() ?agtBsDtlType.getServBrId() : "");
        request.setData(data);
        trainingRequest.setRequest(request);
        log.info("Request for training API {}",trainingRequest.toString());
    }

    public String generateJwt(String source, String agentId){
        TokenPayload payload = new TokenPayload(source,agentId,"SuperAppToken");
        String jwtToken="";
        try {
             jwtToken = Utility.generateJwtToken(njTokenSecretKey,tokenExpiryLimit,payload);
        }catch (Exception e){
            log.info("Exception occured while generating jwt token {}",e.getMessage());
        }
        return  Base64.getEncoder().encodeToString(jwtToken.getBytes());
    }

//    private RequestResponse transformresponse(OauthBasedAgent360Response agent360response, String key) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//        RequestResponse resp = new RequestResponse();
//        resp.setPayload(objectMapper.writeValueAsString(agent360response));
//        encryptResponse(resp,key);
//        return resp;
//    }

//    private void encryptResponse(RequestResponse resp, String key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//        log.info("APIServiceHandler encryptResponse request");
//        String encryptedResponse = AESEncryptDecryptUtil.encrypt(resp.getPayload(), key);
//        log.info("Response to be encrypted is : {}", resp.getPayload());
//        resp.setPayload(encryptedResponse);
//    }

    private InputPayload mapRequest(RequestResponse inputRequest, String key) {
        MFAAgent360InputRequest agent360request= new MFAAgent360InputRequest();
        InputPayload payload = new InputPayload();
        try {
            String decryptedString = decrypt(inputRequest, key);
            payload = objectMapper.readValue(decryptedString, InputPayload.class);
            channelName = payload.getData().getChannelName();
            source = payload.getData().getSource();
            if(null != payload.getData() && null != payload.getData().getSubSource()){
                destinationScreen = payload.getData().getSubSource();
            }else{
                destinationScreen = INVALID;
            }
            log.info("Request mapped for channelName {} and source {}",channelName,source);
        }catch(Exception e){
            log.info("Exception occurred while mapping request {}",e.getMessage());
        }
        return payload;
    }

    private MFAAgent360InputRequest getagent360Requst(InputPayload payload) {
        MFAAgent360InputRequest agent360request = new MFAAgent360InputRequest();
        MFARequest request = new MFARequest();
        MFAPayload data = new MFAPayload();
        log.info("received agent ID {}",payload.getData().getAgentId());
        data.setAgentId(payload.getData().getAgentId());
        data.setRequestSource(SUPERAPP);
        request.setPayload(data);
        agent360request.setRequest(request);
        log.info("agent 360 request formed {}",agent360request.toString());
        return agent360request;
    }

    private String decrypt(RequestResponse inputRequest, String key) throws GeneralSecurityException {
        String decryptedString = null;

        log.debug("Encryption key is {} ", key);
        if(null != key) {
            decryptedString = AESEncryptDecryptUtil.decrypt(inputRequest.getPayload(), key) ;
        } else {
            log.error("Encryption decryption key is null ");
        }

        log.info("SuperApp Decrypt request is {} ", decryptedString);
        return decryptedString;
    }
    public OauthBasedAgent360Response getAgent360Records(MFAAgent360InputRequest agentRequest) throws GeneralSecurityException, JsonProcessingException {
        log.info("Api request for Oauth token agent 360 triggered.");
        Boolean hasOauthEnabled = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEOAUTHBROKERAGENT360_V3);
        try {
            if (hasOauthEnabled) {
                return oauthBasedAgent360Service.oauthBasedAgent360CallV3(agentRequest.getRequest().getPayload(),
                        oauthBasedAgent360Service.getNewOauthToken(),NO);
            } else {
                return  oauthBasedAgent360Service.oauthBasedAgent360Call(agentRequest.getRequest().getPayload(),
                        oauthBasedAgent360Service.getOauthToken(),NO);
            }
        } catch (UserHandledException ex) {
            log.error(EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API, ex);
            return oauthBasedAgent360Service.setOauthBasedAgentResponse(null, ex);
        }
    }
    public void validateParameters(String agentId) throws UserHandledException {
        if(!StringUtils.isEmpty(agentId)){
            if(agentId.length()>10 || agentId.length()<6){
                oauthBasedAgent360Service.exceptionResponse("AgentId should contains 6 to 10 digits allowed", HttpStatus.BAD_REQUEST);
            }
        } else {
            oauthBasedAgent360Service.exceptionResponse("Please check agentId must have some value", HttpStatus.BAD_REQUEST);
        }
    }
//    public OauthBasedAgent360Response oauthBasedAgent360CallV3(MFAPayload payload, String token)
//            throws UserHandledException, GeneralSecurityException{
//        OauthBasedAgent360Response response = new OauthBasedAgent360Response();
//        String agentID = payload.getAgentId();
//        validateParameters(agentID);
//        log.info(INITIATE_OAUTHBASEDAGENT360CALL, agentID);
//        SoaApiRequest agent360Details = oauthBasedAgent360Service.requestForAgent360(agentID, AgentSelfConstants.MPRO);
//        try {
//            com.mli.mpro.agent.models.AgentResponse agent360response = oauthBasedAgent360Service.getAgent360Response(token, agentID,
//                    agent360Details);
//
//            boolean isResponseOk = agent360response != null && agent360response.getResponse() != null
//                    && agent360response.getResponse().getMsgInfo() != null;
//            if (isResponseOk && agent360response.getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase(
//                    AppConstants.SUCCESS_RESPONSE) && checkAgentActive(agent360response.getResponse())) {
//                ResponseData responseData = new ResponseData();
//                responseData.setToken(null);
//                responseData.setAgent360Response(agent360response.getResponse().getPayload());
//                response = oauthBasedAgent360Service.setOauthBasedAgentResponse(responseData, null);
//            } else {
//                oauthBasedAgent360Service.exceptionResponseData(agent360response);
//            }
//            log.info("Oauth Based Agent360 service successfully called with agentID {}", agentID);
//        } catch (HttpClientErrorException ex) {
//            log.error(EXCEPTION_DURING_CALL, agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, ex.getStatusCode());
//        } catch (UserHandledException ex) {
//            log.error(
//                    EXCEPTION_DURING_GENERATING,
//                    agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (JsonProcessingException ex) {
//            log.error("oauthBasedAgent360Call - Exception during parsing json to string for agentID {}", agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (NumberFormatException ex) {
//            log.error(EXCEPTION_DURING_PARSING, agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return response;
//    }

    public Boolean checkAgentActive(Response agentResponse) throws UserHandledException, JsonProcessingException {
    String agentBasedDetailsJson = Utility.printJsonRequest(agentResponse.getPayload().getAgtBsDtlType());
    log.info("Validation for agent based details response from agent360 Api {}", agentBasedDetailsJson);

    // Check if the payload or agent status is empty
    if (StringUtils.isEmpty(agentResponse.getPayload()) 
            || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType()) 
            || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType().getAgentStatus())) {
        oauthBasedAgent360Service.exceptionResponse("Agent360 - Invalid Payload", HttpStatus.INTERNAL_SERVER_ERROR);
    } else if (agentResponse.getPayload().getAgtBsDtlType().getAgentStatus().equalsIgnoreCase(AppConstants.ACTIVE_AGENT)) {
        // Removed all channel and service branch checks

        return true;
    } else {
        oauthBasedAgent360Service.exceptionResponse("Agent Not Active", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return false;
}


//    public OauthBasedAgent360Response oauthBasedAgent360Call(MFAPayload payload, String token) throws UserHandledException {
//        OauthBasedAgent360Response response = new OauthBasedAgent360Response();
//        String agentID = payload.getAgentId();
//        validateParameters(agentID);
//        log.info(INITIATE_OAUTHBASEDAGENT360CALL,agentID);
//        try{
//            SoaApiRequest agent360Details = oauthBasedAgent360Service.requestForAgent360(agentID,AgentSelfConstants.FULFILLMENT);
//            AgentResponse agent360response = null;
//            if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_STAG_CDC_ENABLE_FLAG)){
//                agent360response = oauthBasedAgent360Service.soaCloudService.fetchAgent360EncryptedData(agent360Details);
//            } else {
//                RestTemplate restTemplate = new RestTemplate();
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//                headers.add(AppConstants.AUTH, "Bearer " + token);
//                headers.add(AppConstants.X_IBM_CLIENT_ID, authUserName);
//                headers.add(AppConstants.X_IBM_CLIENT_SECRET, authPassword);
//                HttpEntity<SoaApiRequest> request = new HttpEntity<>(agent360Details,headers);
//                agent360response = restTemplate.postForEntity(agent360, request, AgentResponse.class).getBody();
//            }
//            String responseJson = Utility.printJsonRequest(agent360response);
//            log.info("Oauth based agent360 response receive successfully for agentId {} , {} ", agentID, responseJson);
//            boolean isResponseOk = agent360response!= null && agent360response.getResponse()!=null && agent360response.getResponse().getMsgInfo()!=null;
//            if(isResponseOk && agent360response.getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase(AppConstants.SUCCESS_RESPONSE)
//                    && checkAgentActive(agent360response.getResponse())){
//                ResponseData responseData = new ResponseData();
//                responseData.setToken(null);
//                responseData.setAgent360Response(agent360response.getResponse().getPayload());
//                response = oauthBasedAgent360Service.setOauthBasedAgentResponse(responseData, null);
//            } else {
//                int statusCode = 0;
//                String message ="";
//                if(agent360response != null && agent360response.getResponse()!=null && agent360response.getResponse().getMsgInfo()!=null){
//                    statusCode = Integer.parseInt(agent360response.getResponse().getMsgInfo().getMsgCode());
//                    message = agent360response.getResponse().getMsgInfo().getMsgDescription();
//                } else {
//                    statusCode = 500;
//                }
//                log.info(message);
//                oauthBasedAgent360Service.exceptionResponse(message, HttpStatus.valueOf(statusCode));
//            }
//            log.info("Oauth Based Agent360 service successfully called with agentID {}", agentID);
//        } catch (HttpClientErrorException ex ){
//            log.error(EXCEPTION_DURING_CALL, agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, ex.getStatusCode());
//        } catch (UserHandledException ex){
//            log.error(EXCEPTION_DURING_GENERATING, agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (JsonProcessingException ex){
//            log.error("oauthBasedAgent360Call - Exception during parsing json to string for agentID {}",agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (NumberFormatException ex){
//            log.error(EXCEPTION_DURING_PARSING,agentID);
//            oauthBasedAgent360Service.exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return response;
//    }

    @Override
    public RequestResponse sendErrorResponse(String msgCode,String msg,String msgDescription,MultiValueMap<String, String> headers) {
        RequestResponse requestResponse = new RequestResponse();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setMsgCode(msgCode);
        msgInfo.setMsg(msg);
        msgInfo.setMsgDescription(msgDescription);
        try {
            requestResponse.setPayload(AESEncryptDecryptUtil.encrypt(objectMapper.writeValueAsString(msgInfo), encodDecodUtil.getEncDecryptKey(headers)));
        }catch (Exception e ){
            log.info("error while encrypting error response");
        }
        return requestResponse;
    }


}
