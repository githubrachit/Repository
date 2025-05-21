package com.mli.mpro.location.soa.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.login.service.LoginService;
import com.mli.mpro.location.newApplication.model.OutputResponse;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.location.repository.BrmsMapRuleRepository;
import com.mli.mpro.location.training.model.*;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.constants.TrainingConstants;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import com.mli.mpro.location.soa.service.TrainingService;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.configuration.repository.ConfigurationRepository;

import javax.annotation.PostConstruct;

import static com.mli.mpro.location.soa.constants.TrainingConstants.*;
import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST;

@Service
public class TrainingServiceImpl implements TrainingService {

	private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

	@Value("#{${urlDetails.gocode}}")
	Map<String, List<String>> ujjivanReplicaGocodes;
	@Value("${urlDetails.brms.cloud.flag}")
	private String brmsCloudFlag;
	@Value("${urlDetails.brms.cloud.server}")
	private String brmsCloudServer;
	@Value("${urlDetails.brms.cloud.agent.access}")
	private String brmsCloudAgentAccess;
	@Value("${urlDetails.brms.apigw.key}")
	private String brmsApigwKey;
	@Value("${urlDetails.brms.api_key}")
	private String brmsApiKey;
	@Value("${urlDetails.training.brms}")
	private String brmsurl;
	@Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.secretKey}")
	private String secretKey;
	@Value("${brmsMapRule.redis.timeout}")
	private int brmsMapRuleRedisTTL;
	@Value("${redis.scan.count}")
	private int redisScanCount;

	@Value("#{'${ra.roles.list}'.split(',')}")
	List<String> raRoles;
	@Value("#{${urlDetails.gocode}}")
	Map<String, List<String>> channelGocodes;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	BrmsMapRuleRepository brmsMapRuleRepository;

	@PostConstruct
	public void init() {
		System.out.println("Clearing brms mapp rules for training api from redis cache");
		Utility.clearWithPrefixFromRedisCache(TrainingConstants.BRMS_MAPP_RULE_PREFIX, redisTemplate, redisScanCount);
		Utility.deleteRedisDetailsByPreFixKeys(AppConstants.BRMS_EKYC_UTM_PREFIX,redisTemplate);
	}

	@Override
	public ResponseEntity<Object> executeTrainingService(TrainingRequest trainingRequest) throws SoaCustomException {
		logger.info("inside the training api");
		String channel = "";
		Payload payload = null;
		TrainingResponse trainingResponse = null;
		try {
			Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(trainingRequest);
			Set<ErrorResponse> errors= utility.validateJson(jsonString, AppConstants.TRAINING);
			com.mli.mpro.location.newApplication.model.OutputResponse outputResponse = new com.mli.mpro.location.newApplication.model.OutputResponse();
			com.mli.mpro.location.newApplication.model.Response response1 = new com.mli.mpro.location.newApplication.model.Response();
			if(!CollectionUtils.isEmpty(errors)){
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, trainingRequest, errors);
				ResponseMsgInfo msgInfo=new ResponseMsgInfo();
				com.mli.mpro.location.newApplication.model.Result result = new com.mli.mpro.location.newApplication.model.Result();
				response1.setMsginfo(msgInfo);
				result.setResponse(response1);
				outputResponse.setResult(result);
				outputResponse.getResult().getResponse().getMsginfo().setMsgCode(Integer.parseInt("400"));
				outputResponse.getResult().getResponse().getMsginfo().setErrors(errors);
				//return new ResponseEntity<>(new Gson().toJson(outputResponse), HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(outputResponse, HttpStatus.BAD_REQUEST);
			}
			if (validateTrainingRequest(trainingRequest)) {
				Data requestData = trainingRequest.getRequest().getData();
				logger.info("training api request receieved: {}", trainingRequest);
				channel = requestData.getAgentxChannel();
				String role = requestData.getRole();
				String goCode = requestData.getBranchCd();
				// FUL2-54297 Adding goCode for Broker channel
				channel = updateChannel(channel, goCode, role);
				if (GOCODE_SHRI_RAM_FINANCE.equalsIgnoreCase(channel)) {
					channel = channel + "*";
				}
				// FUL2-54297 Checking for Broker channel, FUL2-104539
				logger.info("After UpdateChannel method: channel :{},goCode:{},role :{}",channel,goCode,role);

				if (FeatureFlagUtil.isFeatureFlagEnabled(TrainingConstants.ENABLE_TRAINING_BRMS_REVAMP)) {
					BrmsMapRule brmsMapRule = fetchBrmsMapRule(channel, requestData.getRole());
					trainingResponse = processApiResponse(brmsMapRule);
				} else {
					trainingResponse = callTrainingBrmsAPI(channel, goCode, requestData);
				}

				logger.info("final response for training api {}", trainingResponse);
				return new ResponseEntity<>(trainingResponse, HttpStatus.OK);
			} else {
				throw new SoaCustomException("bad request", "Request object is invalid !!", "400");
			}
		} catch (SoaCustomException e) {
			return buildErrorResponse(e.getMsg(), e.getMsgDescription(), e.getMsgCode(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error response in training api : {}", Utility.getExceptionAsString(e));
			return buildErrorResponse("failure", "brms api failed", "500", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private TrainingResponse callTrainingBrmsAPI(String channel, String goCode, Data requestData) throws SoaCustomException {
		TrainingResponse trainingResponse;
		Payload payload;
		String trimmedGoCode= goCode.substring(0, 2).toUpperCase();

		if ((channel.equalsIgnoreCase("J") && trimmedGoCode.equals(GOCODE_BROKER))
				|| (channel.equalsIgnoreCase("B") && trimmedGoCode.equals(GOCODE_TMB))
				|| isUjjivanLike(requestData)) {
			payload = brmsAmlUlipTrainingRequest(requestData.getRole(), channel, goCode);
		} else {
			payload = brmsAmlUlipTrainingRequest(requestData.getRole(), channel, null);
		}
		logger.info("request for training soa api {}", payload);
		trainingResponse = checkBrmsRuleAndCallSoaAPi(payload);
		return trainingResponse;
	}


	public ResponseEntity<Object> buildErrorResponse(String msg, String msgDescription, String msgCode,
			HttpStatus httpStatus) {
		Response response = new Response();
		Result result = new Result();
		MsgInfo msgInfo = new MsgInfo(msg, msgCode, msgDescription);
		response.setHeader(new Header());
		response.setMsginfo(msgInfo);
		response.setPayload(new ResponsePayload());
		result.setResponse(response);
		return new ResponseEntity<>(result, httpStatus);

	}

	public boolean validateTrainingRequest(TrainingRequest trainingRequest) {
		if (trainingRequest != null && trainingRequest.getRequest() != null
				&& trainingRequest.getRequest().getData() != null) {
			Data data = trainingRequest.getRequest().getData();
			if (data.getAgentCode() != null && data.getAgentxChannel() != null && data.getRole() != null
					&& data.getSpecifiedPersonCode() != null) {
				return true;
			}
		}
		return false;
	}

	public TrainingResponse checkBrmsRuleAndCallSoaAPi(Payload payload) throws SoaCustomException {
		TrainingResponse trainingResponse = new TrainingResponse();
		try {
			long requestedTime = System.currentTimeMillis();
			ResponseEntity<SoaResult> apiResponse = brmsApiCall(payload);
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info(" Time took to process the tarining request {} miliseconds ", processedTime);
			if (Objects.nonNull(apiResponse) && apiResponse.getBody() != null && apiResponse.getStatusCode() == HttpStatus.OK) {
				return processApiResponse(apiResponse.getBody(), trainingResponse,payload.getRequest().getRequestData().getDesignation());
			} else {
				throw new SoaCustomException(TrainingConstants.FAILURE, "Something wrong at backend", "500");
			}
		} catch (Exception e) {
			logger.error("Unknown exception raised in training: {}", Utility.getExceptionAsString(e));
			throw new SoaCustomException(TrainingConstants.FAILURE, "Service not responding please try again later !!",
					"500");
		}
	}

	private TrainingResponse processApiResponse(SoaResult body, TrainingResponse trainingResponse,String role)
			throws SoaCustomException {
		logger.info("processing the  TrainingResponse");
		SaoResponse soaResponse = body.getResponse();
		Response response = new Response();
		Result result = new Result();
		if (soaResponse.getMsginfo().getMsgCode().equals("200") && soaResponse.getPayload() != null) {
			SaoResponsePayload soaPayload = soaResponse.getPayload();
			ResponsePayload responsePayload = new ResponsePayload();
			responsePayload.setAml(convertYesNoToBoolean(soaPayload.getAml()));
			responsePayload.setUlip(convertYesNoToBoolean(soaPayload.getUlip()));
			responsePayload.setNewApplicationButton(convertYesNoToBoolean(soaPayload.getNewApplicationButton()));
			responsePayload.setPhysicalApplicationEntryButton(
					convertYesNoToBoolean(soaPayload.getPhysicalApplicationEntryButton()));
			responsePayload.setSuperannuationButton(convertYesNoToBoolean(soaPayload.getSuperannuationButton()));
			responsePayload.setrAJourneyApplicable(convertYesNoToBoolean(soaPayload.getrAJourneyApplicable()));
			responsePayload.setrASourcingPopUp(convertYesNoToBoolean(soaPayload.getRaSourcingPopUp()));
			responsePayload.setVerticalFilter(convertYesNoToBoolean(soaPayload.getVerticalFilter()));
			responsePayload.setSubChannelFilter(convertYesNoToBoolean(soaPayload.getSubChannelFilter()));
			responsePayload.setRA(raRoles.contains(role) ? true : false);
			response.setHeader(soaResponse.getHeader());
			response.setMsginfo(soaResponse.getMsginfo());
			response.setPayload(responsePayload);
			result.setResponse(response);
			trainingResponse.setResult(result);
		} else {
			throw new SoaCustomException(TrainingConstants.FAILURE, "Something wrong at backend", "500");
		}
		return trainingResponse;
	}
	private TrainingResponse processApiResponse(BrmsMapRule brmsMapRule)
			throws SoaCustomException {
		logger.info("processing the  TrainingResponse for brmsRule {}", brmsMapRule);
		TrainingResponse trainingResponse = new TrainingResponse();
		Response response = new Response();
		Result result = new Result();
		if (brmsMapRule!= null) {
			ResponsePayload responsePayload = new ResponsePayload();
			responsePayload.setAml(convertYesNoToBoolean(brmsMapRule.getAml()));
			responsePayload.setUlip(convertYesNoToBoolean(brmsMapRule.getUlip()));
			responsePayload.setNewApplicationButton(convertYesNoToBoolean(brmsMapRule.getNewApplicationButton()));
			responsePayload.setPhysicalApplicationEntryButton(convertYesNoToBoolean(brmsMapRule.getPhysicalApplicationEntryButton()));
			responsePayload.setSuperannuationButton(convertYesNoToBoolean(brmsMapRule.getSuperannuationButton()));
			responsePayload.setrASourcingPopUp(convertYesNoToBoolean(brmsMapRule.getRaSourcingPopUp()));
			responsePayload.setrAJourneyApplicable(convertYesNoToBoolean(brmsMapRule.getrAJourneyApplicable()));
			responsePayload.setRA(raRoles.contains(brmsMapRule.getRole()) ? true : false);
			responsePayload.setVerticalFilter(convertYesNoToBoolean(brmsMapRule.getVerticalFilter()));
			responsePayload.setSubChannelFilter(convertYesNoToBoolean(brmsMapRule.getSubChannelFilter()));
			response.setHeader(new Header());
			response.setMsginfo(new MsgInfo(AppConstants.STATUS_SUCCESS,AppConstants.SUCCESS_RESPONSE,AppConstants.RESPONSE_GENERATED_SUCCESSFULLY));
			response.setPayload(responsePayload);
			result.setResponse(response);
			trainingResponse.setResult(result);
		} else {
			throw new SoaCustomException(TrainingConstants.FAILURE, AppConstants.SOMETHING_WENT_WRONG, AppConstants.INTERNAL_SERVER_ERROR_CODE);
		}
		return trainingResponse;
	}

	private boolean convertYesNoToBoolean(String value) {
		return AppConstants.YES.equalsIgnoreCase(value);
	}

	private ResponseEntity<SoaResult> brmsApiCall(Payload payload) throws SoaCustomException {
		String url = "";
		ResponseEntity<SoaResult> apiResponse = null;
		try {
			logger.info("inside the brmsApiCall method");
			RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory(SoaConstants.SOA_TIMEOUT));
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(payload);
			if (brmsCloudFlag.equals("TRUE")) {
				url = brmsCloudServer + brmsCloudAgentAccess;
				HttpHeaders headers = new HttpHeaders();
				headers.add("x-apigw-api-id", brmsApigwKey);
				headers.add("x-api-key", brmsApiKey);
				headers.add("Content-Type", "application/json");
				HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
				apiResponse = restTemplate.postForEntity(url, entity, SoaResult.class);
			} else {
				url = brmsurl + "client_id=" + cleintID + "&client_secret=" + secretKey;
				apiResponse = restTemplate.postForEntity(url, jsonString, SoaResult.class);
				return apiResponse;
			}
			logger.info("response received from soa api: {}", apiResponse);
		} catch (ResourceAccessException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			throw new SoaCustomException(TrainingConstants.FAILURE, "Timeout occurred",
					"Timeout issue occuured from soa side");
		} catch (HttpServerErrorException e) {
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				logger.error("HttpServerErrorException occurs {}", Utility.getExceptionAsString(e));
			}
		} catch (Exception e) {
			logger.error("Exception raised in training: {}", Utility.getExceptionAsString(e));
			throw new SoaCustomException(TrainingConstants.FAILURE, "Service not responding please try again later !!",
					"500");
		}
		return apiResponse;
	}
	
	private ClientHttpRequestFactory clientHttpRequestFactory(int timeout) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		factory.setReadTimeout(timeout);
		return factory;
	}

	private boolean isUjjivanLike(Data requestData) {
		String channel = requestData.getAgentxChannel();
		String goCode = requestData.getBranchCd();
		if (channel.equals("B") || channel.equals("C")) {
			List<String> ujjivanGocodeList = ujjivanReplicaGocodes.get(channel);
			return ujjivanGocodeList != null && ujjivanGocodeList.contains(goCode);
		}
		return false;
	}

	public String updateChannel(String channel, String goCode, String role) {
		logger.info("Inside updateChannel method:: channel :{} , goCode: {}, role: {} ", channel, goCode, role);
		if(goCode.isEmpty()){
			return channel;
		}
		if ((role.equalsIgnoreCase("PHY") || role.equalsIgnoreCase("OTE")) && channel.equalsIgnoreCase("J")) {
			return goCode.substring(0, 2);
		} else if (role.equalsIgnoreCase("CAN")) {
			return "A";
		}
		String trimmedGoCode= goCode.substring(0, 2).toUpperCase();
		logger.info("Inside updateChannel method:: trimmedGoCode: {}", trimmedGoCode);

		if (updateChannelFromGoCode(channel,trimmedGoCode) || channelToGoCodeCapability(channel,trimmedGoCode))
			return trimmedGoCode;

		return channel;
	}
	private boolean channelToGoCodeCapability(String channel, String trimmedGoCode) {
		try {
			logger.info("inside all channel gocode parameter value from aws {} " , channelGocodes);
			List<String> goCodeList = channelGocodes.get(channel);
			return goCodeList != null && goCodeList.contains(trimmedGoCode);
		}catch(Exception ex){
			logger.error("exception occured while fetching go code: {}",Utility.getExceptionAsString(ex));
			return false;
		}
	}

	private boolean updateChannelFromGoCode(String channel, String trimmedGoCode) {
		List<String> goCodeListWithChannelB = Arrays.asList(GOCODE_CSB, GOCODE_CSFB, GOCODE_DCB, GOCODE_SIB, GOCODE_TMB, GOCODE_UJJIVAN);
		List<String> goCodeListWithChannelC = Arrays.asList(GOCODE_MOTILAL, GOCODE_STOCK_HOLDING,GOCODE_CLAY_CLOVE,GOCODE_PNB_HOUSING,
				GOCODE_NAMDEV_FINVEST,GOCODE_VPK_FINANCIAL,GOCODE_OMSAT_SERVICES,GOCODE_FINOVISER_FINANCIAL,GOCODE_SHRI_RAM_FINANCE,GOCODE_SR_ASSOCIATE,GOCODE_AXIS_SECURITIE,GOCODE_WEALTHY_INSURANCE);
		List<String> goCodeListWithChannelJ = Arrays.asList(GOCODE_MAHINDRA,GOCODE_NJ,GOCODE_MUTHOOT,GOCODE_YELLA,GOCODE_BLUECHIP,GOCODE_TURTLEMINT
				,GOCODE_RENEWBUY,GOCODE_LANDMARK,GOCODE_BAJAJ,GOCODE_SWASTIK,GOCODE_POLICY_BAZAR,GOCODE_BROKER,GOCODE_PROST,GOCODE_MEDWELL_INSURANCE,GOCODE_ZFINCA);
		if ("B".equals(channel)) {
            return goCodeListWithChannelB.contains(trimmedGoCode);
		} else if ("C".equals(channel)) {
            return goCodeListWithChannelC.contains(trimmedGoCode);
		} else if ("J".equals(channel)) {
            return goCodeListWithChannelJ.contains(trimmedGoCode);
		}
		return false;
	}

	public Payload brmsAmlUlipTrainingRequest(String role, String channel, String goCode) {
		Payload payload = new Payload();
		PayloadRequest payloadRequest = new PayloadRequest();
		Header header = new Header();
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		header.setSoaAppId(SoaConstants.FULFILLMENT);
		RequestData requestData = new RequestData();
		requestData.setDesignation(role);
		requestData.setChannel(channel);
		if (goCode != null) {
			requestData.setGoCode(goCode);
		}
		payloadRequest.setHeader(header);
		payloadRequest.setRequestData(requestData);
		payload.setRequest(payloadRequest);
		return payload;
	}

	private BrmsMapRule fetchBrmsMapRule(String channel, String role) {
		logger.info("inside fetchBrmsMapRule for channel {} and role {}", channel, role);
		BrmsMapRule brmsMapRule = fetchFromCacheOrDb(channel, role);
		if (brmsMapRule == null) {
			brmsMapRule = fetchFromCacheOrDb(channel, AppConstants.DEFAULT.toLowerCase());
		}
		if (brmsMapRule == null) {
			brmsMapRule = fetchFromCacheOrDb(AppConstants.DEFAULT.toLowerCase(), role);
		}
		if (brmsMapRule == null) {
			logger.info("No rule found for channel :{} and role :{} returning default values", channel, role);
			brmsMapRule = new BrmsMapRule();

		}
		return brmsMapRule;
	}

	private BrmsMapRule fetchFromCacheOrDb(String channel, String role) {
		String ENVIRONMENT = System.getenv("env");
		String redisKey = TrainingConstants.BRMS_MAPP_RULE_PREFIX + channel + "_" + role + ENVIRONMENT;
		BrmsMapRule brmsMapRule = null;
		try {
			brmsMapRule = (BrmsMapRule) redisTemplate.opsForValue().get(redisKey);
		} catch (Exception e) {
			logger.error("Error occurred while fetching brmsMapRule from redis cache {}", Utility.getExceptionAsString(e));
		}
		if (brmsMapRule == null) {
			logger.info("fetching role from DB for channel :{} and role :{} key {}", channel, role, redisKey);
			brmsMapRule = brmsMapRuleRepository.findByChannelAndRole(channel, role);
			if (brmsMapRule != null) {
				try {
					redisTemplate.opsForValue().set(redisKey, brmsMapRule, brmsMapRuleRedisTTL, TimeUnit.HOURS);
				} catch (Exception e) {
					logger.error("Error occurred while setting brmsMapRule from redis cache {}", Utility.getExceptionAsString(e));
				}
			}
		}
		return brmsMapRule;
	}

}
