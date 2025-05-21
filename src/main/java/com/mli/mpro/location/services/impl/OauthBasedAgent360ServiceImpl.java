package com.mli.mpro.location.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.agent.models.*;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.mfaOauthAgent360.*;
import com.mli.mpro.location.services.OauthBasedAgent360Service;
import com.mli.mpro.agent360.models.AgentTokenRequest;
import com.mli.mpro.agent360.models.AgentTokenResponse;
import com.mli.mpro.agent360.models.AgentV3Request;
import com.mli.mpro.agent360.models.TokenRequest;
import com.mli.mpro.agent360.models.TokenRequestHeader;
import com.mli.mpro.agent360.models.TokenRequestPayload;
import com.mli.mpro.agent360.models.V3Request;
import com.mli.mpro.agent.models.AgentResponse;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.oauthToken.Service.BrokerChOauthTokenServiceImpl;
import com.mli.mpro.oauthToken.Service.OauthTokenService;
import com.mli.mpro.onboarding.service.impl.SuperAppImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.AgentSelfConstants;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import com.mli.mpro.utils.EncryptionUtil;
import com.mli.mpro.utils.Utility;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

import static com.mli.mpro.productRestriction.util.AppConstants.EXCEPTION_OCCURED_DURING_ACCESSING_OAUTH_TOKEN;
import static com.mli.mpro.productRestriction.util.AppConstants.INITIATE_OAUTHBASEDAGENT360CALL;
import static com.mli.mpro.productRestriction.util.AppConstants.EXCEPTION_DURING_CALL;
import static com.mli.mpro.productRestriction.util.AppConstants.EXCEPTION_DURING_GENERATING;
import static com.mli.mpro.productRestriction.util.AppConstants.EXCEPTION_DURING_PARSING;

@Service
public class OauthBasedAgent360ServiceImpl implements OauthBasedAgent360Service {


    private Logger logger = LoggerFactory.getLogger(OauthBasedAgent360ServiceImpl.class);

    @Value("${urlDetails.broker.agent360}")
    private String agent360;
    @Value("${urlDetails.broker.client.id}")
    private String authUserName;
    @Value("${urlDetails.broker.client.secret}")
    private String authPassword;
    @Value("${nj.token.secret.key}")
    private String njTokenSecretKey;
    @Value("${security.broker.key}")
    private String securityKey;
    @Value("${token.expiry.limit}")
    private String tokenExpiryLimit;
    @Value("${urldetails.oauth.agent360.encr.decr.key}")
	private String encryptDecryptKey;
	@Value("${urldetails.oauth.agent360.api.key}")
	private String ouathApiKey;
	@Value("${urldetails.oauth.agent360.url}")
	private String oauthAgent360;
	@Value("${urldetails.oauth.agent360.clientid}")
	private String ouathClientId;
	@Value("${urldetails.oauthagent360.clientsecret}")
	private String ouathClientSecret;
	@Value("${urldetails.oauth.agent360.token.url}")
	private String ouathTokenurl;

    private OauthTokenService oauthTokenService;
    public SoaCloudService soaCloudService;

    @Autowired
    public OauthBasedAgent360ServiceImpl(BrokerChOauthTokenServiceImpl oauthTokenService, SoaCloudService soaCloudService){
        super();
        this.oauthTokenService = oauthTokenService;
        this.soaCloudService = soaCloudService;
    }
	@Autowired
	SuperAppImpl superAppImpl;

    @Override
    public String getOauthToken() throws UserHandledException {
        String token = oauthTokenService.getAccessToken();
        if(StringUtils.isEmpty(token)){
            logger.info(EXCEPTION_OCCURED_DURING_ACCESSING_OAUTH_TOKEN);
            exceptionResponse(EXCEPTION_OCCURED_DURING_ACCESSING_OAUTH_TOKEN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return token;
    }

	@Override
	public String getNewOauthToken() throws UserHandledException {
		String token = getNewAccessToken();
		logger.info("Oauth beared token generated : {}", token);
		if (StringUtils.isEmpty(token)) {
			logger.info(EXCEPTION_OCCURED_DURING_ACCESSING_OAUTH_TOKEN);
			exceptionResponse(EXCEPTION_OCCURED_DURING_ACCESSING_OAUTH_TOKEN, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return token;
	}


	private String getNewAccessToken() {
		logger.info("Fetching access token");
		AgentTokenRequest agentTokenRequest = new AgentTokenRequest();
		TokenRequest tokenRequest = new TokenRequest();
		TokenRequestHeader header = new TokenRequestHeader();
		header.setApplicationId(AgentSelfConstants.MPRO);
		header.setCorrelationId(UUID.randomUUID().toString());
		TokenRequestPayload tokenRequestPayload = new TokenRequestPayload();
		tokenRequestPayload.setClientId(ouathClientId);
		tokenRequestPayload.setClientSecret(ouathClientSecret);
		tokenRequest.setHeader(header);
		tokenRequest.setPayload(tokenRequestPayload);
		agentTokenRequest.setRequest(tokenRequest);
		String accessToken = "";
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-api-key", ouathApiKey);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> requestEntity = new HttpEntity<>(agentTokenRequest, headers);
			AgentTokenResponse agentResponse = restTemplate.exchange(ouathTokenurl, HttpMethod.POST, requestEntity,
					new ParameterizedTypeReference<AgentTokenResponse>() {
					}).getBody();
			if (agentResponse != null && agentResponse.getResponse() != null
					&& agentResponse.getResponse().getPayload() != null
					&& agentResponse.getResponse().getPayload().getAccessToken() != null) {
				accessToken = agentResponse.getResponse().getPayload().getAccessToken();
			}
			return accessToken;
		} catch (Exception ex) {
			logger.error("exception occurred to generate token");
			logger.error(Utility.getExceptionAsString(ex));
		}
		return accessToken;
	}

    @Override
    public OauthBasedAgent360Response oauthBasedAgent360Call(MFAPayload payload, String token, String tokenRequired) throws UserHandledException {
        OauthBasedAgent360Response response = new OauthBasedAgent360Response();
        String agentID = payload.getAgentId();
        validateParameters(agentID, tokenRequired);
        String source = AppConstants.NJ_SOURCE;
        logger.info(INITIATE_OAUTHBASEDAGENT360CALL,agentID);
        try{
            SoaApiRequest agent360Details = requestForAgent360(agentID,AgentSelfConstants.FULFILLMENT);
            AgentResponse agent360response = null;
            if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_STAG_CDC_ENABLE_FLAG)){
                agent360response = soaCloudService.fetchAgent360EncryptedData(agent360Details);
            } else {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(AppConstants.AUTH, "Bearer " + token);
                headers.add(AppConstants.X_IBM_CLIENT_ID, authUserName);
                headers.add(AppConstants.X_IBM_CLIENT_SECRET, authPassword);
                HttpEntity<SoaApiRequest> request = new HttpEntity<>(agent360Details,headers);
				logger.info("OauthBasedAgent360Response:Agent360 request object {}",request);
                agent360response = restTemplate.postForEntity(agent360, request, AgentResponse.class).getBody();
            }
			logger.info("Printing response agent360 {}",(Objects.nonNull(agent360response) && Objects.nonNull(agent360response.getResponse()))?agent360response.getResponse().getMsgInfo().toString():"Error on getting agent360 response");
			String responseJson = Utility.printJsonRequest(agent360response);
            logger.info("Oauth based agent360 response receive successfully for agentId {} , {} ", agentID, responseJson);
            boolean isResponseOk = agent360response!= null && agent360response.getResponse()!=null && agent360response.getResponse().getMsgInfo()!=null;
            if(isResponseOk && agent360response.getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase(AppConstants.SUCCESS_RESPONSE)
                    && (payload.getRequestSource().equalsIgnoreCase("SuperApp") ? superAppImpl.checkAgentActive(agent360response.getResponse()): checkAgentActive(agent360response.getResponse()))){
				//FUL2-196166  Persistency Tag Mapping
				Utility.setAgentJoiningDt(agent360response);
				logger.info("Checking agent360 response and generating token");
                ResponseData responseData = new ResponseData();
                responseData.setToken(tokenRequired.equalsIgnoreCase(AppConstants.YES) ? generateJwtToken(source, agentID) : null);
                responseData.setAgent360Response(agent360response.getResponse().getPayload());
				if (Objects.nonNull(agent360response) && Objects.nonNull(agent360response.getResponse())) {
					logger.info("agent360 response payload", agent360response.getResponse().getPayload());
				}
                response = setOauthBasedAgentResponse(responseData, null);
				if (Objects.nonNull(response)) {
					logger.info("Getting oauth based agent response {}", new ObjectMapper().writeValueAsString(response));
				}
            } else {
                int statusCode = 0;
                String message ="";
                if(agent360response != null && agent360response.getResponse()!=null && agent360response.getResponse().getMsgInfo()!=null){
                    statusCode = Integer.parseInt(agent360response.getResponse().getMsgInfo().getMsgCode());
                    message = agent360response.getResponse().getMsgInfo().getMsgDescription();
                } else {
                    statusCode = 500;
                }
                logger.info(message);
                exceptionResponse(message, HttpStatus.valueOf(statusCode));
            }
            logger.info("Oauth Based Agent360 service successfully called with agentID {}", agentID);
        } catch (HttpClientErrorException ex ){
            logger.error(EXCEPTION_DURING_CALL, agentID);
            exceptionResponse(ex, ex.getStatusCode());
        } catch (UserHandledException ex){
            logger.error(EXCEPTION_DURING_GENERATING, agentID);
            exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException ex){
            logger.error("oauthBasedAgent360Call - Exception during parsing json to string for agentID {}",agentID);
            exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException ex){
            logger.error(EXCEPTION_DURING_PARSING,agentID);
            exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
	public OauthBasedAgent360Response oauthBasedAgent360CallV3(MFAPayload payload, String token, String tokenRequired)
			throws UserHandledException, GeneralSecurityException, JsonProcessingException {
		OauthBasedAgent360Response response = new OauthBasedAgent360Response();
		String source = AppConstants.NJ_SOURCE;
		String agentID = payload.getAgentId();
		validateParameters(agentID,tokenRequired);
		logger.info(INITIATE_OAUTHBASEDAGENT360CALL, agentID);
		SoaApiRequest agent360Details = requestForAgent360(agentID, AgentSelfConstants.MPRO);
		try {
			com.mli.mpro.agent.models.AgentResponse agent360response = getAgent360Response(token, agentID,
					agent360Details);

			boolean isResponseOk = agent360response != null && agent360response.getResponse() != null
					&& agent360response.getResponse().getMsgInfo() != null;
			if (isResponseOk && agent360response.getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase(
					AppConstants.SUCCESS_RESPONSE) &&
					(payload.getRequestSource().equalsIgnoreCase("SuperApp") ? superAppImpl.checkAgentActive(agent360response.getResponse()): checkAgentActive(agent360response.getResponse()))) {
				ResponseData responseData = new ResponseData();
				responseData.setToken(
						tokenRequired.equalsIgnoreCase(AppConstants.YES) ? generateJwtToken(source, agentID) : null);
				//FUL2-196166  Persistency Tag Mapping
				Utility.setAgentJoiningDt(agent360response);
				responseData.setAgent360Response(agent360response.getResponse().getPayload());
				response = setOauthBasedAgentResponse(responseData, null);
			} else {
				exceptionResponseData(agent360response);
			}
			logger.info("Oauth Based Agent360 service successfully called with agentID {}", agentID);
		} catch (HttpClientErrorException ex) {
			logger.error(EXCEPTION_DURING_CALL, agentID);
			exceptionResponse(ex, ex.getStatusCode());
		} catch (UserHandledException ex) {
			logger.error(
					EXCEPTION_DURING_GENERATING,
					agentID);
			exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonProcessingException ex) {
			logger.error("oauthBasedAgent360Call - Exception during parsing json to string for agentID {}", agentID);
			exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NumberFormatException ex) {
			logger.error(EXCEPTION_DURING_PARSING, agentID);
			exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

    @Override
	public AgentResponse agent360V3(RequestPayload payload, String token)
			throws UserHandledException, GeneralSecurityException, JsonProcessingException {
		String agentID = payload.getAgentId();
		validateParameters(agentID);
		List<AgentServices> agentServices = payload.getServices();
		SoaApiRequest agent360Details = requestForAgent360V3(agentID, agentServices);
		logger.info("Agent360V3 request {}", agent360Details);
		logger.info(INITIATE_OAUTHBASEDAGENT360CALL, agentID);
		try {
			AgentResponse agent360response = getAgent360Response(token, agentID, agent360Details);
			boolean isResponseOk = agent360response != null && agent360response.getResponse() != null
					&& agent360response.getResponse().getMsgInfo() != null;
			if (isResponseOk && agent360response.getResponse().getMsgInfo().getMsgCode()
					.equalsIgnoreCase(AppConstants.SUCCESS_RESPONSE)) {
				//FUL2-196166  Persistency Tag Mapping
				if(Objects.nonNull(agent360response.getResponse().getPayload())
						&& Objects.nonNull(agent360response.getResponse().getPayload().getAgtBsDtlType())) {
					Utility.setAgentJoiningDt(agent360response);
				}
				logger.info("Agent360 service successfully called with agentId {} {}", agentID,
						Utility.printJsonRequest(agent360response));
				return agent360response;
			} else {
				exceptionResponseData(agent360response);
			}

		} catch (HttpClientErrorException ex) {
			logger.error(EXCEPTION_DURING_CALL, agentID);
			exceptionResponse(ex, ex.getStatusCode());
		} catch (UserHandledException ex) {
			logger.error(
					EXCEPTION_DURING_GENERATING,
					agentID);
			exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NumberFormatException ex) {
			logger.error(EXCEPTION_DURING_PARSING, agentID);
			exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	public com.mli.mpro.agent.models.AgentResponse getAgent360Response(String token, String agentID,
																	   SoaApiRequest agent360Details) throws JsonProcessingException, GeneralSecurityException {
		AgentV3Request v3request = new AgentV3Request();
		V3Request payloadV3 = new V3Request();
		Gson gson = new Gson();
		String encryptedRequest = "";
		if (!ObjectUtils.isEmpty(agent360Details)) {
			String agent360DetailsReq = new ObjectMapper().writeValueAsString(agent360Details);
			encryptedRequest = EncryptionDecryptionUtil.encrypt(agent360DetailsReq, encryptDecryptKey);
		}
		payloadV3.setPayload(encryptedRequest);
		v3request.setRequest(payloadV3);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AppConstants.AUTH, "Bearer " + token);
		headers.add("correlationId", UUID.randomUUID().toString());
		headers.add("appId", AgentSelfConstants.MPRO);
		headers.add("x-api-key", ouathApiKey);
		HttpEntity<AgentV3Request> request = new HttpEntity<>(v3request, headers);
		com.mli.mpro.agent360.models.AgentV3Response agentV3Response = restTemplate.exchange(oauthAgent360, HttpMethod.POST,
				request, new ParameterizedTypeReference<com.mli.mpro.agent360.models.AgentV3Response>() {
				}).getBody();
		String decryptResponse = "";
		if (agentV3Response != null && agentV3Response.getResponse() != null
				&& agentV3Response.getResponse().getPayload() != null) {

			decryptResponse = EncryptionDecryptionUtil.decrypt(agentV3Response.getResponse().getPayload(),
					encryptDecryptKey);
		}
		logger.info("Oauth based agent360 response receive successfully for agentId {} , {} ", agentID,
				decryptResponse);

		return gson.fromJson(decryptResponse,
				AgentResponse.class);
	}



    @Override
    public OauthBasedAgent360Response setOauthBasedAgentResponse(ResponseData responseData, UserHandledException ex) {
        OauthBasedAgent360Response response = new OauthBasedAgent360Response();
        MFAResponse mfaResponse = new MFAResponse();
        if(ex == null){
            mfaResponse.setResponseData(responseData);
            response.setResponse(mfaResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(new Date());
            errorResponse.setErrorCode(ex.getHttpstatus().value());
            errorResponse.setErrorDescription(HttpStatus.valueOf(ex.getHttpstatus().value()).getReasonPhrase());
            errorResponse.setErrorMessages(ex.getErrorMessages());
            mfaResponse.setErrorResponse(errorResponse);
            response.setResponse(mfaResponse);
        }
        return response;
    }

    private SoaApiRequest requestForAgent360V3(String agentID, List<AgentServices> agentServices) {
		SoaApiRequest agent360Details = new SoaApiRequest();
		Request agent360Request = new Request();
		RequestHeader agent360RequestHeader = new RequestHeader();
		agent360RequestHeader.setApplicationId("MPRO");
		agent360RequestHeader.setCorrelationId(UUID.randomUUID().toString());
		agent360Request.setHeader(agent360RequestHeader);
		RequestPayload agent360RequestPayload = new RequestPayload();
		agent360RequestPayload.setAgentId(agentID);
		agent360RequestPayload.setServices(agentServices);
		agent360Request.setPayload(agent360RequestPayload);
		agent360Details.setRequest(agent360Request);
		return agent360Details;
	}

    public SoaApiRequest requestForAgent360(String agentId, String appId) {
		SoaApiRequest agent360Details = new SoaApiRequest();
		Request agent360Request = new Request();
		RequestHeader agent360RequestHeader = new RequestHeader();

		agent360RequestHeader.setApplicationId(appId);
		agent360RequestHeader.setCorrelationId(UUID.randomUUID().toString());
		agent360Request.setHeader(agent360RequestHeader);
		RequestPayload agent360RequestPayload = new RequestPayload();
		agent360RequestPayload.setAgentId(agentId);

		AgentServices addServices = new AgentServices();
		addServices.setServiceName(AgentSelfConstants.AGENTADDDETAILSTYPE);
		AgentServices basicServices = new AgentServices();
		basicServices.setServiceName(AgentSelfConstants.AGENTBASICDETAILSTYPE);
		AgentServices conService = new AgentServices();
		conService.setServiceName(AgentSelfConstants.AGENTCONTDETAILSTYPE);
		AgentServices contractService = new AgentServices();
		contractService.setServiceName(AgentSelfConstants.AGENTCONTRACTDETAILSTYPE);
		AgentServices movService = new AgentServices();
		movService.setServiceName(AgentSelfConstants.AGENTMOVEDETAILSTYPE);
		AgentServices refService = new AgentServices();
		refService.setServiceName(AgentSelfConstants.AGENTREFDETAILSTYPE);
		AgentServices polService = new AgentServices();
		polService.setServiceName(AgentSelfConstants.AGENTPOLDETAILSTYPE);
		AgentServices commService = new AgentServices();
		commService.setServiceName(AgentSelfConstants.AGENTCOMMDETAILSTYPE);
		AgentServices finService = new AgentServices();
		finService.setServiceName(AgentSelfConstants.AGENTFINDETAILSTYPE);
		List<AgentServices> services = new ArrayList<AgentServices>();
		services.add(addServices);
		services.add(basicServices);
		services.add(conService);
		services.add(contractService);
		services.add(movService);
		services.add(refService);
		services.add(polService);
		services.add(commService);
		services.add(finService);
		agent360RequestPayload.setServices(services);
		agent360Request.setPayload(agent360RequestPayload);
		agent360Details.setRequest(agent360Request);
		return agent360Details;

	}

    public String generateJwtToken(String source, String agentId) throws UserHandledException{
        String jwtToken = "";
        TokenPayload payload = new TokenPayload(source,agentId,AppConstants.OAUTH_AGENT360_TOKEN_SUBJECT);
        try{
            jwtToken = Utility.generateJwtToken(njTokenSecretKey,tokenExpiryLimit,payload);
            jwtToken = EncryptionUtil.encrypt(jwtToken,securityKey);

        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException during encryption token ",e);
            exceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidKeyException e) {
            logger.error("InvalidKeyException during encryption token ",e);
            exceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchPaddingException e) {
            logger.error("NoSuchPaddingException during encryption token ",e);
            exceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadPaddingException e) {
            logger.error("BadPaddingException during encryption token ",e);
            exceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException during encryption token ",e);
            exceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalBlockSizeException e) {
            logger.error("IllegalBlockSizeException during encryption token ",e);
            exceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Token generated successfully for agentId {}", agentId);
        return jwtToken;
    }

    public void exceptionResponse(Exception ex, HttpStatus status) throws UserHandledException{
        List<String> list = new ArrayList<>();
        if(ex instanceof UserHandledException && ((UserHandledException) ex).getErrorMessages().size()>0 ){
            list.add(((UserHandledException) ex).getErrorMessages().get(0));
        } else {
            list.add(ex.getMessage());
        }
        throw new UserHandledException(list, status);
    }
    public void exceptionResponse(String msg, HttpStatus status) throws UserHandledException{
        List<String> list = new ArrayList<>();
        list.add(msg);
        throw new UserHandledException(list, status);
    }

    public void validateParameters(String agentId, String tokenRequired) throws UserHandledException {
        if(StringUtils.isEmpty(tokenRequired)){
            exceptionResponse("Invalid header token-required must have some value", HttpStatus.BAD_REQUEST);
        }
        if(!StringUtils.isEmpty(agentId)){
            if(agentId.length()>10 || agentId.length()<6){
                exceptionResponse("AgentId should contains 6 to 10 digits allowed", HttpStatus.BAD_REQUEST);
            }
        } else {
            exceptionResponse("Please check agentId must have some value", HttpStatus.BAD_REQUEST);
        }
    }

	private void validateParameters(String agentId) throws UserHandledException {
		if (!StringUtils.isEmpty(agentId)) {
			if (agentId.length() > 10 || agentId.length() < 6) {
				exceptionResponse("AgentId should contains 6 to 10 digits allowed", HttpStatus.BAD_REQUEST);
			}
		} else {
			exceptionResponse("Please check agentId must have some value", HttpStatus.BAD_REQUEST);
		}
	}

    private Boolean checkAgentActive(Response agentResponse) throws UserHandledException, JsonProcessingException {
        String agentBasedDetailsJson = Utility.printJsonRequest(agentResponse.getPayload().getAgtBsDtlType());
        logger.info("Validation for agent based details response from agent360 Api {}",agentBasedDetailsJson);
        if(StringUtils.isEmpty(agentResponse.getPayload())
                || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType())
                || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType().getAgentStatus())
                || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType().getChannelName())
                || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType().getChannelType())
                || StringUtils.isEmpty(agentResponse.getPayload().getAgtBsDtlType().getServBrId())){
            exceptionResponse("Agent360 - Invalid Payload ", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(agentResponse.getPayload().getAgtBsDtlType().getAgentStatus().equalsIgnoreCase(AppConstants.ACTIVE_AGENT)){
            if(!agentResponse.getPayload().getAgtBsDtlType().getChannelName().equalsIgnoreCase(AppConstants.CHANNEL_BROKER)
                    && !agentResponse.getPayload().getAgtBsDtlType().getChannelType().equalsIgnoreCase(AppConstants.BROKER_CHANNEL_TYPE)){
                exceptionResponse("Invalid agent - Please provide broker channel agent ID", HttpStatus.BAD_REQUEST);
            }
            String serviceBranchId = agentResponse.getPayload().getAgtBsDtlType().getServBrId();
            if(serviceBranchId.startsWith(AppConstants.BRANCH_ID_Q) || serviceBranchId.startsWith(AppConstants.BRANCH_ID_U)){
                return true;
            } else {
                exceptionResponse("Invalid agent - Please provide broker channel agent ID", HttpStatus.BAD_REQUEST);
            }
        } else {
            exceptionResponse("Agent Not Active", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

	public void exceptionResponseData(AgentResponse agent360response) throws UserHandledException {
		int statusCode = 0;
		String message = "";
		if (agent360response != null && agent360response.getResponse() != null
				&& agent360response.getResponse().getMsgInfo() != null) {
			statusCode = Integer.parseInt(agent360response.getResponse().getMsgInfo().getMsgCode());
			message = agent360response.getResponse().getMsgInfo().getMsgDescription();
		} else {
			statusCode = 500;
		}
		logger.info(message);
		exceptionResponse(message, HttpStatus.valueOf(statusCode));
	}

}
