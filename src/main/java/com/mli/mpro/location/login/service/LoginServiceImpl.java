package com.mli.mpro.location.login.service;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.login.model.*;
import com.mli.mpro.location.services.SoaCloudService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mli.mpro.location.auth.filter.AuthorizationException;
import com.mli.mpro.location.login.exception.ApiProcessingException;
import com.mli.mpro.location.login.exception.UserDataException;
import com.mli.mpro.location.login.exception.UserLoginException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.LoginConstants;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.Metadata;
import com.mli.mpro.auditservice.models.ResponseObject;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_VALIDATION_ERROR;

@Service
public class LoginServiceImpl implements LoginService{

	public static final String INVALID_LOGIN = "Invalid Login";
	public static final String SERVICE_DOWN = "Service Down";
	@Value("${physical.journey.disable}")
	private String physicalJourneyDisable;
	@Value("${urldetails.loginurl}")
	private String loginURL;
	@Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.secretKey}")
	private String secretKey;
	@Value("${urlDetails.login.private.key}")
	private String privateKey;
	@Value("${jwt.expiry.token}")
	private String expiryTime;
	@Value("${urlDetails.auditingService}")
	private URI auditUrl;

	@Value("${jwt.secret.key}")
	private String jwtTokenSecretKey;
	@Value("${redis.scan.count}")
	private int redisScanCount;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private SoaCloudService soaCloudService;

	@Value("${login.serviceDown.cooling.period}")
	private int serviceDownCoolingPeriod;
	@Value("${login.InvalidAttempt.cooling.period}")
	private int invalidLoginCoolingPeriod;
	@Value("${login.serviceDown.message}")
	private String serviceDownErrorMsg;
	@Value("${login.InvalidAttempt.message}")
	private String invalidLoginErrorMsg;
	@Value("${login.InvalidAttempt.max}")
	private int maxInvalidLoginAttempt;

	private int remainingCoolingPeriod;
	@Autowired
	private MongoOperations mongoTemplate;

	private static final Pattern GOCODE_BROKER_PATTERN = Pattern.compile("^[QqUu]");
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Override
	public ResponseEntity<String> executeLoginService(LoginRequest loginRequest) {
		long requestedTime = System.currentTimeMillis();
		logger.info("came to login service at {}", requestedTime);
		String url = loginURL.trim() + "?client_id=" + cleintID.trim() + "&client_secret=" + secretKey.trim();
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		String jsonInputRequest = null;
		DecryptedRequest decryptedRequest = null;
		InputRequest auditInputRequest = null;
		Result result = new Result();
		try {
			if (!validateLoginRequest(loginRequest)) {
				logger.error("exception occurred {}", LoginConstants.INVALID_OBJECT);
				throw new UserDataException("400", AppConstants.STATUS_FAILURE, LoginConstants.INVALID_OBJECT);
			}
			decryptedRequest = decrpytData(loginRequest);

			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(decryptedRequest);
			Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors= utility.validateJson(jsonString, AppConstants.LOGIN_DATA);
			if(!CollectionUtils.isEmpty(errors)){
				logger.info(FIELD_VALIDATION_ERROR,errors);
				throw new UserDataException("400", AppConstants.STATUS_FAILURE, errors.toString());
			}

			String userId = decryptedRequest.getUserId();
			if (!validateDecryptedRequest(decryptedRequest)) {
				logger.error("exception occurred {}", LoginConstants.TOKEN_DECRYPT_FAILURE);
				throw new UserDataException("400", AppConstants.STATUS_FAILURE, LoginConstants.INVALID_OBJECT);
			}
			checkLoginCoolingPeriod(decryptedRequest);
			InputRequest saoInputRequest = buildLoginSAORequest(decryptedRequest, false);
			logger.info("Request for SOA login api {}", saoInputRequest);
			auditInputRequest = buildLoginSAORequest(decryptedRequest, true);
			ResponseEntity<Result> soaResponse = null;
			if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_STAG_CDC_ENABLE_FLAG_LOGIN)){
				soaResponse = soaCloudService.soaCombinedLoginApiFetch(saoInputRequest);
			} else {
				jsonInputRequest = new Gson().toJson(saoInputRequest, InputRequest.class);
				soaResponse = new RestTemplate().postForEntity(url, jsonInputRequest, Result.class);
			}
			Result soaResponseBody = soaResponse.getBody();
			if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.BLOCKEDAGENTS)
					&& (soaResponse.getStatusCode() == HttpStatus.OK || soaResponseBody != null)) {
				Query query = new Query();
				query.addCriteria(Criteria.where("blockedAgentId").regex("^" + userId.toString() + "$", "i"));
				logger.info("Value of agent Id {}",userId);
				BlockedAgentData blockedAgent = mongoTemplate.findOne(query, BlockedAgentData.class, "blockedAgents");
				logger.info("blocked agent found from DB {}", blockedAgent);
				if (Objects.nonNull(blockedAgent))
					throw new UserLoginException(AppConstants.SUCCESS_RESPONSE, AppConstants.BANNED_AGENT_RESPONSES.get(0), AppConstants.BANNED_AGENT_RESPONSES.get(1));

			}
			if (soaResponse.getStatusCode() != HttpStatus.OK || soaResponseBody == null) {
				String statusCode = validateAndGetStatusCode(soaResponse);
				AuditingDetails auditingDetails = setAuditingDetails(statusCode,
						String.valueOf(soaResponse.getStatusCode().value()), decryptedRequest.getUserId(),
						auditInputRequest.getRequest(),
						soaResponseBody != null ? soaResponseBody.getResponse() : null);
				com.mli.mpro.auditservice.models.InputRequest auditReq = setDataForAudit(auditingDetails);
				callAuditing(auditReq);
				updateLoginFailDetails(decryptedRequest, SERVICE_DOWN);
				throw new UserLoginException("500", AppConstants.STATUS_FAILURE, LoginConstants.UNKNOWN_ERROR);
			} else {
				//	ResponseEntity<String> SUCCESS_RESPONSE = getStringResponseEntity(decryptedRequest);
				//if (SUCCESS_RESPONSE != null) return SUCCESS_RESPONSE;
				result = soaResponseBody;
				response = result.getResponse();
				String statusCode = validateAndGetStatusCode(soaResponse);
				AuditingDetails auitingDetails = setAuditingDetails(statusCode,
						String.valueOf(soaResponse.getStatusCode().value()), decryptedRequest.getUserId(),
						auditInputRequest.getRequest(), response);
				com.mli.mpro.auditservice.models.InputRequest auditReq = setDataForAudit(auitingDetails);
				callAuditing(auditReq);
				processResponse(response, decryptedRequest);
				result.setResponse(response);
				outputResponse.setResult(result);
				long processedTime = (System.currentTimeMillis() - requestedTime);
				logger.info(" Time took to process the login request {} miliseconds ", processedTime);
				logger.info("outputResponse for SOA login api {}", outputResponse);
				return new ResponseEntity<>(new Gson().toJson(outputResponse), HttpStatus.OK);
			}
		} catch (UserDataException e) {
			String errorResponse = buildErrorResponse(e.getMsgCode(), e.getMsg(), e.getMsgDescription());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (UserLoginException e) {
			String errorResponse = buildErrorResponse(e.getMsgCode(), e.getMsg(), e.getMsgDescription());
			logger.info("errorResponse for SOA login api {}", errorResponse);
			return new ResponseEntity<>(errorResponse, HttpStatus.OK);
		} catch (Exception ex) {
			AuditingDetails auditingDetails = setAuditingDetails("500", "500",
					decryptedRequest != null ? decryptedRequest.getUserId() : "",
					auditInputRequest != null && auditInputRequest.getRequest() != null ? auditInputRequest.getRequest()
							: "",
					ex);
			com.mli.mpro.auditservice.models.InputRequest auditReq = setDataForAudit(auditingDetails);
			callAuditing(auditReq);
			String errorResponse = buildErrorResponse("500", AppConstants.STATUS_FAILURE, LoginConstants.UNKNOWN_ERROR);
			logger.error("unexpected issue occur {}", Utility.getExceptionAsString(ex));
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<String> getStringResponseEntity(DecryptedRequest decryptedRequest) {
		if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.BLOCKEDAGENTS)) {
			Query query = new Query();
			String blockedAgentId = decryptedRequest.getUserId();
			query = query.addCriteria((Criteria.where("blockedAgentId").is(blockedAgentId)));
			List<BlockedAgentData> blockedAgents = mongoTemplate.find(query, BlockedAgentData.class, "blockedAgents");
			logger.info("blocked agents found from DB {}", blockedAgents);
			if (blockedAgents.size() > 0) {
				return new ResponseEntity<>(buildErrorResponse(AppConstants.SUCCESS_RESPONSE, AppConstants.BANNED_AGENT_RESPONSES.get(0), AppConstants.BANNED_AGENT_RESPONSES.get(1)), HttpStatus.OK);
			}
		}
		return null;
	}

	private void checkLoginCoolingPeriod(DecryptedRequest decryptedRequest) {
		if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_AGENT_LOGIN_REVAMP)) {
			try {
				LoginFailureDetails loginFailureDetails = (LoginFailureDetails) redisTemplate.opsForValue().get(AppConstants.LOGIN_FAILURE_DETAILS + decryptedRequest.getUserId() + SERVICE_DOWN);
				if (loginFailureDetails != null && SERVICE_DOWN.equalsIgnoreCase(loginFailureDetails.getLastLoginStatus())) {
					remainingCoolingPeriod = serviceDownCoolingPeriod - (int) ((new Date().getTime() - loginFailureDetails.getLastLoginFailureTime().getTime()) / 1000);
					loginFailureDetails.setRemainingCoolingPeriod(remainingCoolingPeriod);
					throw new UserLoginException(AppConstants.UNAUTHORISED_ERR_CODE, AppConstants.STATUS_FAILURE, serviceDownErrorMsg);
				}
				loginFailureDetails = (LoginFailureDetails) redisTemplate.opsForValue().get(AppConstants.LOGIN_FAILURE_DETAILS + decryptedRequest.getUserId() + INVALID_LOGIN);
				if (loginFailureDetails != null && INVALID_LOGIN.equalsIgnoreCase(loginFailureDetails.getLastLoginStatus()) && loginFailureDetails.getInvalidLoginFailCount() >= maxInvalidLoginAttempt) {
					remainingCoolingPeriod = invalidLoginCoolingPeriod - (int) ((new Date().getTime() - loginFailureDetails.getLastLoginFailureTime().getTime()) / 1000);
					loginFailureDetails.setRemainingCoolingPeriod(remainingCoolingPeriod);
					throw new UserLoginException(AppConstants.UNAUTHORISED_ERR_CODE, AppConstants.STATUS_FAILURE, invalidLoginErrorMsg);
				}

			} catch (UserLoginException e) {
				throw e;
			} catch (Exception e) {
				logger.error("Error while checking the login cooling period {}", Utility.getExceptionAsString(e));
			}
		}
	}

	private void updateLoginFailDetails(DecryptedRequest decryptedRequest, String failureReason) {
		try {
			if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_AGENT_LOGIN_REVAMP)) {
				remainingCoolingPeriod = 0;
				if (SERVICE_DOWN.equalsIgnoreCase(failureReason)) {
					LoginFailureDetails loginFailureDetails = new LoginFailureDetails();
					loginFailureDetails.setLastLoginStatus(SERVICE_DOWN);
					loginFailureDetails.setLastLoginFailureTime(new Date());
					loginFailureDetails.setRemainingCoolingPeriod(serviceDownCoolingPeriod);
					remainingCoolingPeriod = serviceDownCoolingPeriod;
					redisTemplate.opsForValue().set(AppConstants.LOGIN_FAILURE_DETAILS + decryptedRequest.getUserId() + SERVICE_DOWN, loginFailureDetails, serviceDownCoolingPeriod, TimeUnit.SECONDS);
					throw new UserLoginException(AppConstants.UNAUTHORISED_ERR_CODE, AppConstants.STATUS_FAILURE, serviceDownErrorMsg);
				} else if (INVALID_LOGIN.equalsIgnoreCase(failureReason)) {
					String redisKey = AppConstants.LOGIN_FAILURE_DETAILS + decryptedRequest.getUserId() + INVALID_LOGIN;
					LoginFailureDetails loginFailureDetails = (LoginFailureDetails) redisTemplate.opsForValue().get(redisKey);
					if (loginFailureDetails == null) {
						loginFailureDetails = new LoginFailureDetails();
						loginFailureDetails.setInvalidLoginFailCount(1);
						loginFailureDetails.setLastLoginStatus(INVALID_LOGIN);
					} else {
						loginFailureDetails.setInvalidLoginFailCount(loginFailureDetails.getInvalidLoginFailCount() + 1);
					}
					loginFailureDetails.setLastLoginFailureTime(new Date());
					loginFailureDetails.setRemainingCoolingPeriod(invalidLoginCoolingPeriod);
					redisTemplate.opsForValue().set(redisKey, loginFailureDetails, invalidLoginCoolingPeriod, TimeUnit.SECONDS);
					if(loginFailureDetails.getInvalidLoginFailCount() >= maxInvalidLoginAttempt) {
						remainingCoolingPeriod = invalidLoginCoolingPeriod;
						throw new UserLoginException(AppConstants.UNAUTHORISED_ERR_CODE, AppConstants.STATUS_FAILURE, invalidLoginErrorMsg);
					}
				}
			}
		} catch (UserLoginException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error while updating the login failure details {}", Utility.getExceptionAsString(e));
		}
	}

	private String validateAndGetStatusCode(ResponseEntity<Result> soaResponse) {
		return Optional.ofNullable(soaResponse).map(ResponseEntity::getBody).map(Result::getResponse)
				.map(Response::getMsginfo).map(MsgInfo::getMsgCode).orElse("");
	}

	private String buildErrorResponse(String msgCode, String msg, String msgDescription) {
		OutputResponse outputResponse = new OutputResponse();
		Result result = new Result();
		Response response = new Response();
		Header header = new Header();
		MsgInfo msgInfo = new MsgInfo(msgCode, msg, msgDescription);
		Payload payload = new Payload();
		payload.setRemainingCoolingPeriod(remainingCoolingPeriod);
		response.setHeader(header);
		response.setMsginfo(msgInfo);
		response.setPayload(payload);
		result.setResponse(response);
		outputResponse.setResult(result);
		return new Gson().toJson(outputResponse);
	}

	private boolean validateLoginRequest(LoginRequest loginRequest) {
		return loginRequest != null && loginRequest.getRequest() != null
				&& org.springframework.util.StringUtils.hasText(loginRequest.getRequest().getData());
	}

	public boolean validateDecryptedRequest(DecryptedRequest decryptedRequest) throws UserLoginException {
		return !StringUtils.isBlank(decryptedRequest.getUserId())
				&& !StringUtils.isBlank(decryptedRequest.getPassword());
	}

	public void processResponse(Response response, DecryptedRequest decryptedRequest) throws ApiProcessingException {
		try {
			Payload responsePayload = response.getPayload();
			MsgInfo responseMsginfo = response.getMsginfo();
			String msgCode = responseMsginfo.getMsgCode();
			if ("200".equals(msgCode)) {
				boolean alphaNumeric = isAlphaNumeric(decryptedRequest.getUserId());
				String channelName = responsePayload.getChannelName();
				String goCode = responsePayload.getGoCode();
				String agentActiveCode = responsePayload.getAgentActiveCode();
				logger.info("channel : {} , gocode : {} and agentActiveCode :{}", channelName, goCode, agentActiveCode);
				// Update channelName and goCode based on certain conditions
				updateChannelAndGoCode(responsePayload, channelName, goCode);
				boolean axisCondition = axisCondition(responsePayload, alphaNumeric);
				updateAgentActiveCode(responsePayload, agentActiveCode);
				if (!responsePayload.getAgentStatus().equalsIgnoreCase("active")
						|| (physicalJourneyDisable.equalsIgnoreCase("TRUE")
						&& LoginConstants.PHYSICALJOURNEYROLE.contains(responsePayload.getRole()))) {
					responseMsginfo.setMsgCode("500");
					responseMsginfo.setMsgDescription(LoginConstants.AGENT_INACTIVE);
					response.setPayload(new Payload());
				} else if (axisCondition && org.springframework.util.StringUtils.hasText(responsePayload.getSpecifiedPersonCode()) && !responsePayload.getSpecifiedPersonCode().substring(0, 7).equals("AXISPOS")
						&& !LoginConstants.PHYSICALJOURNEYROLE.contains(responsePayload.getRole())) {
					responseMsginfo.setMsgCode("500");
					responseMsginfo.setMsgDescription(LoginConstants.NON_COMMISSIONABLE_USER);
					response.setPayload(new Payload());
				} else {
					responsePayload.setIsPosSeller(false);
					String designation = responsePayload.getInformation().getDesignation();
					String branchCd = responsePayload.getInformation().getBranchCd();
					boolean isPosSeller = isPOSSeller(responsePayload.getChannelName(), designation)
							|| isPOSIMF(responsePayload.getChannelName(), designation)
							|| isPOSBroker(responsePayload.getChannelName(), designation, goCode);
					responsePayload.setIsPosSeller(isPosSeller);
					response.getPayload().setChannelName(updateChannelName(responsePayload.getChannelName(), branchCd));
					// Set ulipStatus, amlStatus, apiToken, and expiryTime
					setUlipAndAmlStatus(responsePayload);
					setApiTokenAndExpiryTime(response, decryptedRequest.getUserId(), responsePayload.getAgentCode());
				}
				clearRedisKeysForLoginFailure(decryptedRequest.getUserId());
			} else if ("500".equals(msgCode)) {
				logger.error("user is not authenticated");
				responseMsginfo.setMsg(AppConstants.STATUS_FAILURE);
				responseMsginfo.setMsgCode(msgCode);
				responseMsginfo.setMsgDescription(LoginConstants.USER_NOT_AUTHENTICATE);
				response.setPayload(new Payload());
				response.setHeader(new Header());
				updateLoginFailDetails(decryptedRequest, INVALID_LOGIN);
			} else if ("999".equals(msgCode)) {
				logger.error("unexpected error");
				responseMsginfo.setMsg(AppConstants.STATUS_FAILURE);
				responseMsginfo.setMsgCode(msgCode);
				responseMsginfo.setMsgDescription(LoginConstants.UNKNOWN_ERROR);
				response.setPayload(new Payload());
				updateLoginFailDetails(decryptedRequest, SERVICE_DOWN);
			} else {
				logger.error("unexpected status code send by soa api");
				responseMsginfo.setMsg(AppConstants.STATUS_FAILURE);
				responseMsginfo.setMsgCode(msgCode);
				responseMsginfo.setMsgDescription(LoginConstants.UNKNOWN_ERROR);
				response.setPayload(new Payload());
				updateLoginFailDetails(decryptedRequest, SERVICE_DOWN);
			}
		} catch (UserLoginException ex) {
			throw ex;
		} catch (Exception e) {
			logger.error("unexpected error during api calling {}", Utility.getExceptionAsString(e));
			throw new ApiProcessingException("500", AppConstants.STATUS_FAILURE, LoginConstants.UNKNOWN_ERROR);
		}
	}

	//Removing keys from redis after successful login
	private void clearRedisKeysForLoginFailure(String userId) {
		redisTemplate.delete(AppConstants.LOGIN_FAILURE_DETAILS + userId + INVALID_LOGIN);
		redisTemplate.delete(AppConstants.LOGIN_FAILURE_DETAILS + userId + SERVICE_DOWN);
	}

	private void updateAgentActiveCode(Payload payload, String agentActiveCode) {
		if (agentActiveCode != null && !"null".equals(agentActiveCode)) {
			payload.setAgentActiveCode(agentActiveCode);
		} else {
			payload.setAgentActiveCode("");
		}
	}

	private void updateChannelAndGoCode(Payload payload, String channelName, String goCode) {
		if ("X".equalsIgnoreCase(channelName) && goCode.startsWith("X")) {
			payload.setGoCode(goCode.substring(1).replaceFirst("^0+", ""));
		}
		if ("A".equalsIgnoreCase(channelName) && (goCode.charAt(0) == 'E' || goCode.charAt(0) == 'D')) {
			payload.setChannelName("E");
		}
		if ("B".equalsIgnoreCase(channelName) && goCode.toUpperCase().startsWith("B2")) {
			payload.setChannelName("B2");
		} else if ("B".equalsIgnoreCase(channelName) && goCode != null && goCode.charAt(0) == 'B'
				&& goCode.charAt(1) != '2' && goCode.charAt(1) != 'Y') {
			payload.setChannelName("B");
		}
		if (payload.getAgentCode() != null) {
			if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_REDIS_SCAN))) {
				clearWithPrefixUsingScan(payload.getAgentCode());
			} else {
				clearWithPrefix(payload.getAgentCode());
			}
		}
	}

	private void setUlipAndAmlStatus(Payload payload) {
		if (LoginConstants.RAROLES.contains(payload.getRole())) {
			payload.setUlipStatus("1");
			payload.setAmlStatus("1");
		}
	}

	private void setApiTokenAndExpiryTime(Response response, String userId, String agentCode) {
		logger.info("generating the token for the user : {}", userId);
		Header header = response.getHeader();
		String apiToken = generateJwtToken(userId, agentCode);
		header.setApitoken(apiToken);
		header.setExpiryTime(Integer.valueOf(expiryTime));
	}

	private InputRequest buildLoginSAORequest(DecryptedRequest decryptedRequest, boolean isAuditPayload) {
		InputRequest inputRequest = new InputRequest();
		Request request = new Request();
		RequestData requestData = new RequestData();
		RequestPayload requestPayload = new RequestPayload();
		Transactions transactions = new Transactions();
		List<Transactions> transactionsList = new ArrayList<>();
		Header header = new Header();
		header.setSoaAppId("FULFILLMENT");
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		header.setSoaMsgVersion("1.0");
		if (isAuditPayload) {
			transactions.setUserId(decryptedRequest.getUserId());
		} else {
			transactions.setUserId(decryptedRequest.getUserId());
			transactions.setPassword(decryptedRequest.getPassword());
		}
		transactionsList.add(transactions);
		requestPayload.setTransactions(transactionsList);
		requestData.setRequestPayload(requestPayload);
		request.setHeader(header);
		request.setRequestData(requestData);
		inputRequest.setRequest(request);
		return inputRequest;

	}

	private String generateJwtToken(String source, String agentId) {
		String jwtToken = "";
		Key hmacKey = new SecretKeySpec(jwtTokenSecretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

		jwtToken = Jwts.builder().claim("payload", new TokenPayload(source, agentId))
				.setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plus(Long.parseLong(expiryTime), ChronoUnit.SECONDS)))
				.signWith(SignatureAlgorithm.HS256, hmacKey).compact();
		logger.info("Token generated successfully for agentId {}", agentId);
		return jwtToken;
	}

	private boolean isPOSSeller(String channelName, String designation) {
		return (channelName.equalsIgnoreCase("A") && designation.equalsIgnoreCase("POS"))
				|| (channelName.equalsIgnoreCase("B")
				&& (designation.equalsIgnoreCase("POS") || designation.equalsIgnoreCase("PSB")))
				|| (channelName.equalsIgnoreCase("C") && designation.equalsIgnoreCase("CPS"));
	}

	private boolean isPOSIMF(String channelName, String designation) {
		return channelName.equalsIgnoreCase("F") && designation.equalsIgnoreCase("PSF");
	}

	private boolean isPOSBroker(String channelName, String designation, String goCode) {
		return (channelName.equalsIgnoreCase("J") && designation.equalsIgnoreCase("PSR")
				&& GOCODE_BROKER_PATTERN.matcher(goCode).find());
	}

	private String updateChannelName(String channelName, String branchCode) {
		if (channelName != null && !channelName.isEmpty()) {
			if (channelName.equalsIgnoreCase("P")) {
				return "P";
			} else if (channelName.equalsIgnoreCase("X") && branchCode != null && branchCode.startsWith("LX")) {
				return "LX";
			}
		}
		return channelName;
	}

	private boolean isAlphaNumeric(String userId) {
		Pattern pattern = Pattern.compile("(?!^\\d*$)(?!^[a-zA-Z]*$)^([a-zA-Z\\d]{9})$");
		Matcher matcher = pattern.matcher(userId);
		return matcher.matches();
	}

	private boolean axisCondition(Payload payload, boolean alphaNumeric) {
		return org.springframework.util.StringUtils.hasText(payload.getChannelName()) && payload.getChannelName().equalsIgnoreCase(AppConstants.CHANNEL_AXIS) &&
				((org.springframework.util.StringUtils.hasText(payload.getAgentActiveCode()) && payload.getAgentActiveCode().equals("999")) || alphaNumeric);
	}

	public void clearWithPrefix(String keyPrefix) throws ApiProcessingException {
		try {
			Set<String> keys = redisTemplate.opsForValue().getOperations().keys(keyPrefix + "*");
			if (keys != null && !keys.isEmpty()) {
				redisTemplate.delete(keys);
			}
		} catch (Exception ex) {
			logger.error("Exception in redis server clearWithPrefix: {}", Utility.getExceptionAsString(ex));
		}
	}

	public void clearWithPrefixUsingScan(String keyPrefix) {
		try {
			logger.info("clearing the key {} usingScan method", keyPrefix);
			RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
			if (connectionFactory != null) {
				deleteNewApplicationKeys(keyPrefix);
				ScanOptions options = ScanOptions.scanOptions().match(keyPrefix + "_" + "*").count(redisScanCount).build();
				RedisConnection connection = connectionFactory.getConnection();
				Cursor<byte[]> cursor = connection.scan(options);
				while (cursor.hasNext()) {
					processKeys(cursor);
				}
				cursor.close();
			}
		} catch (Exception e) {
			logger.error("Exception in redis server clearWithPrefixUsingScan: {}", Utility.getExceptionAsString(e));
		}
	}

	private void processKeys(Cursor<byte[]> cursor) {
		try {
			byte[] keyBytes = cursor.next();
			String key = new String(keyBytes);
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("Exception occurred while deleting the keys : {}",Utility.getExceptionAsString(e));
		}
	}

	private void deleteNewApplicationKeys(String keyPrefix) {
		try {
			List<String> newApplicationKeys = new ArrayList<>();
			newApplicationKeys.add(keyPrefix + AppConstants.NEWAPPLICATION);
			newApplicationKeys.add(keyPrefix + AppConstants.NEWAPPLICATION_HITCOUNT);
			redisTemplate.delete(newApplicationKeys);
		} catch (Exception e) {
			logger.error("Exception occurred while deleting the undefined keys : {}",Utility.getExceptionAsString(e));
		}
	}


	public DecryptedRequest decrpytData(LoginRequest loginRequest) throws UserDataException {
		logger.info("Decrypting the login paylaod");
		DecryptedRequest decryptedRequest = null;
		String decryptRequest = "";
		String encryptedData = loginRequest.getRequest().getData();
		try {
			decryptRequest = decrypt(encryptedData, privateKey);
			decryptedRequest = new Gson().fromJson(decryptRequest, DecryptedRequest.class);
			return decryptedRequest;
		} catch (Exception e) {
			logger.error("Error while decrypting  {}", Utility.printJsonRequest(e));
			throw new UserDataException("400", AppConstants.STATUS_FAILURE, "Request object is invalid !!");
		}
	}

	private String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
	}

	private String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public PrivateKey getPrivateKey(String base64PrivateKey) {
		PrivateKey privateSecretKey = null;
		String cleanedBase64 = base64PrivateKey.trim().replaceAll("\\s", "");
		byte[] decodedBytes = Base64.getDecoder().decode(cleanedBase64);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedBytes);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			privateSecretKey = keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateSecretKey;
	}

	@Override
	public ResponseEntity<String> executeLogoutService(String apiToken) {
		try {
			if (apiToken != null) {
				JWTInfo jwtInfo = getResponseAgent(apiToken);
				if (jwtInfo != null && jwtInfo.getResponseAgent() != null) {
					if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_REDIS_SCAN))) {
						clearWithPrefixUsingScan(jwtInfo.getResponseAgent());
					} else {
						clearWithPrefix(jwtInfo.getResponseAgent());
					}
					setTokenToRedis(apiToken, jwtInfo.getExp());
					return new ResponseEntity<>("User logout sucessfully !!", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Not a valid token !!", HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<>("No token provided !!", HttpStatus.UNAUTHORIZED);
			}
		} catch (AuthorizationException e) {
			logger.error("expection due to {} ", Utility.getExceptionAsString(e));
		} catch (Exception e) {
			logger.error("unexcpected error occurred {}", Utility.getExceptionAsString(e));
		}
		return new ResponseEntity<>("Unknown error !!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private static JWTInfo getResponseAgent(String apiToken) {
		try {
			String[] jwtParts = apiToken.split("\\.");
			String body = new String(java.util.Base64.getUrlDecoder().decode(jwtParts[1]));
			JSONObject json = new JSONObject(body);
			JSONObject payload = json.getJSONObject("payload");
			String responseAgent = payload.getString("responseAgent");
			long exp = json.getLong("exp");
			return new JWTInfo(responseAgent, exp);
		} catch (Exception e) {
			logger.error("exception while getting jwt token paylaod {}", Utility.getExceptionAsString(e));
		}
		return null;
	}

	private void setTokenToRedis(String key, long expiration) throws AuthorizationException {
		try {
			logger.info("Service initiate to save token in redis");
			redisTemplate.opsForValue().set(key, Instant.now().getEpochSecond());
			redisTemplate.expireAt(key, Instant.ofEpochSecond(expiration));
		} catch (Exception e) {
			logger.error("exception while connecting to redis server {}", Utility.getExceptionAsString(e));
		}
	}

	private com.mli.mpro.auditservice.models.InputRequest setDataForAudit(AuditingDetails serviceTransactionDetails) {
		com.mli.mpro.auditservice.models.InputRequest inputRequest = null;
		try {
			serviceTransactionDetails.setCreatedTime(new Date());
			com.mli.mpro.auditservice.models.RequestPayload requestPayload = new com.mli.mpro.auditservice.models.RequestPayload();
			requestPayload.setServiceTransactionDetails(serviceTransactionDetails);
			Metadata metadata = new Metadata("dev", UUID.randomUUID().toString());
			com.mli.mpro.auditservice.models.RequestData requestData = new com.mli.mpro.auditservice.models.RequestData(
					requestPayload);
			com.mli.mpro.auditservice.models.Request request = new com.mli.mpro.auditservice.models.Request(metadata,
					requestData);
			inputRequest = new com.mli.mpro.auditservice.models.InputRequest(request);
		} catch (Exception ex) {
			logger.info("Error occured while creating request for Auditing {}", Utility.getExceptionAsString(ex));

		}
		return inputRequest;
	}

	private AuditingDetails setAuditingDetails(String statusCode, String httpStatusCode, String agentId, Object request,
											   Object response) {
		AuditingDetails auditDetails = new AuditingDetails();
		auditDetails.setRequestId(UUID.randomUUID().toString());
		auditDetails.setServiceName("combined login service");
		auditDetails.setStatusCode(statusCode);
		auditDetails.setTransactionId(0);
		auditDetails.setHttpStatusCode(httpStatusCode);
		auditDetails.setAdditionalProperty(AppConstants.REQUEST, request);
		auditDetails.setAgentId(agentId);
		ResponseObject respoObject = new ResponseObject();
		respoObject.setAdditionalProperty(AppConstants.RESPONSE, response);
		auditDetails.setResponseObject(respoObject);
		return auditDetails;
	}

	private void callAuditing(com.mli.mpro.auditservice.models.InputRequest inputRequest) {
		try {
			logger.info("calling auditing db ");
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Use ISO 8601 format
					.create();
			String jsonRequest = gson.toJson(inputRequest, com.mli.mpro.auditservice.models.InputRequest.class);
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, httpHeaders);
			com.mli.mpro.auditservice.models.OutputResponse response = new RestTemplate().exchange(auditUrl,
					HttpMethod.POST, requestEntity, com.mli.mpro.auditservice.models.OutputResponse.class).getBody();
			logger.info("The response received from audit service {}", response);
		} catch (Exception ex) {
			logger.info("Auditing service failed to save the data {}", Utility.getExceptionAsString(ex));
		}
	}

	public Set<String> getKeysFromRedis(String keyPrefix) {
		Set<String> applicationKeys = new HashSet<>();
		try {
			keyPrefix = Optional.ofNullable(keyPrefix).orElse("");
			applicationKeys = redisTemplate.opsForValue().getOperations().keys(keyPrefix + "*");
		} catch (Exception e) {
			logger.error("Error occurred while getting keys from redis cache with prefix {} - {}", keyPrefix, Utility.getExceptionAsString(e));
			applicationKeys.add(e.getMessage());
		}
		return applicationKeys;
	}
}