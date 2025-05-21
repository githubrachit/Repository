package com.mli.mpro.onboarding.partner.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.OauthTokenResponse;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.model.*;
import com.mli.mpro.onboarding.partner.service.impl.ReplacementSaleValidationServiceImpl;
import com.mli.mpro.onboarding.partner.service.impl.RetryableInvokeService;
import com.mli.mpro.onboarding.partner.validation.Validation;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.onboarding.util.EncryptionDecryptionTransformUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Component("replacementSaleServiceHandler")
public class ReplacementSaleServiceHandler extends APIServiceHandler {
	@Autowired
	private RetryableInvokeService retryableInvokeService;

	@Autowired
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(ReplacementSaleServiceHandler.class);
	
	
	@Value("${urlDetails.authTokenURL}")
    private String oauthTokenUrl;
	@Value("${urlDetails.authorization.username}")
    private String authUserName;
	@Value("${urlDetails.authorization.password}")
    private String authPassword;
	@Value("${urlDetails.policySplittingURL}")
	private String policySplittingURL;
    @Value("${authToken.username}")
	private String authTokenUsername;
	@Value("${authToken.password}")
	private String authTokenPassword;

	@Autowired
	private ReplacementSaleValidationServiceImpl validationServiceImpl;

	@Autowired
	private AuditService auditService;
	
	@Override
	public InputRequest transformRequest(InputRequest inputRequest) {
		// TODO Auto-generated method stub
		logger.info("ReplacementSale transformRequest called");
		return null;
	}
	

	@Override
	public Validation validate(String inputRequest) throws JsonProcessingException {
		// TODO Auto-generated method stub
		logger.info("ReplacementSale validation called");
		
		return validationServiceImpl.validateRequest(inputRequest);
	}

	@Override
	public String setValidationErrorResponse(MsgInfo msgInfo) throws JsonProcessingException {
		ReplacementOutputResponse replacementOutputResponse = new ReplacementOutputResponse();
		ReplacementSaleAPIResponse replacementSaleAPIResponse = new ReplacementSaleAPIResponse();
		ReplacementResponseData responseData = new ReplacementResponseData();
		replacementSaleAPIResponse.setMsgInfo(msgInfo);
		replacementSaleAPIResponse.setResponseData(responseData);
		replacementOutputResponse.setResponse(replacementSaleAPIResponse);
		return objectMapper.writeValueAsString(replacementOutputResponse);

	}

	@Override
	public SOAResponse invokeService(InputRequest inputRequest) throws Exception {
		logger.info("ReplacementSale invokeService called for transactionId {}",inputRequest.getReplacementSalePayload().getTransactionId());

		SOAResponse response = invokeReplacementSaleService(inputRequest);
		return response;
	}

	@Override
	public RequestResponse processRsponse(RequestResponse requestResponse) {
		// TODO Auto-generated method stub
				logger.info("ReplacementSale processRsponse called");
				return requestResponse;
	}
	
	
	public SOAResponse invokeReplacementSaleService(InputRequest inputRequest) throws Exception {

		Request request = new Request();

		SOARequest soaRequest = new SOARequest();
		ReplacementSaleSOAResponse replacementSaleSOAResponse;

		try {
			soaRequest.setHeader(getHeaderReplacementSalePolicySplittingAPI());
			soaRequest.setPayload(getPayloadForReplacementSalePolicySplittingAPI(inputRequest));

			request.setRequest(soaRequest);

			logger.info(" ReplacementSale API soa request for transactionId {} is {} ",
					inputRequest.getReplacementSalePayload().getTransactionId(), soaRequest);

			RestTemplate restTemplate = new RestTemplate();
			replacementSaleSOAResponse = new ReplacementSaleSOAResponse();

			String responseString = retryableInvokeService.callService(policySplittingURL, getHttpEntityReplacementSalePolicySplittingAPI(request));
			ReplacementSaleResponse rsResponse = objectMapper.readValue(responseString, ReplacementSaleResponse.class);

			
			
			replacementSaleSOAResponse.setHeader(rsResponse.getResponse().getHeader());
			replacementSaleSOAResponse.setMsginfo(rsResponse.getResponse().getMsginfo());
			replacementSaleSOAResponse.setPayload(rsResponse.getResponse().getPayload());

			
			
			logger.info("Replacementale response for transactionId {} is {} ",inputRequest.getReplacementSalePayload().getTransactionId(), replacementSaleSOAResponse);
		} catch (Exception ex) {
			logger.error("Exception while calling policy splitting and replacement Api and message is {} for transactionId {}",
					ex.getMessage(),inputRequest.getReplacementSalePayload().getTransactionId());
			throw ex;
		}
		return replacementSaleSOAResponse;
	}
	
	public RequestResponse transformResponse(SOAResponse soaResponse, InputRequest inputRequest) throws JsonProcessingException, UserHandledException {
		
		RequestResponse requestResponse = new RequestResponse();
		ReplacementOutputResponse replacementOutputResponse = new ReplacementOutputResponse();
		try {
			ReplacementSaleSOAResponsePayload replacementSaleSOAResponsePayload = ((ReplacementSaleSOAResponse)soaResponse).getPayload();
			ReplacementSaleReponsePayload replacementSaleReponsePayload = new ReplacementSaleReponsePayload();

			replacementSaleReponsePayload.setReplacementSale(replacementSaleSOAResponsePayload.getReplacementSale());
			replacementSaleReponsePayload.setSplitFlag(replacementSaleSOAResponsePayload.getSplitFlag());

			ReplacementSaleAPIResponse response = new ReplacementSaleAPIResponse();
			response.setMsgInfo(soaResponse.getMsginfo());

			ReplacementResponseData responseData = new ReplacementResponseData();
			responseData.setReplacementSaleResponse(replacementSaleReponsePayload);
			response.setResponseData(responseData);
			replacementOutputResponse.setResponse(response);
			String payload = getObjectMapper().writeValueAsString(replacementOutputResponse);
			requestResponse.setPayload(payload);

		}catch (Exception ex){
			logger.info("Getting Exception {} while transforming the dedupe response",ex.getMessage());
		}finally {
			auditService.saveAuditTransactionDetails(getAuditDetails(inputRequest.getReplacementSalePayload(),replacementOutputResponse));
			logger.info("APIServiceHandler transformResponse request");
		}
		return requestResponse;
	}
	private ReplacementSaleSOARequestPayload getPayloadForReplacementSalePolicySplittingAPI(
			InputRequest inputRequest) {

		
		ReplacementSaleSOARequestPayload requestPayload = new ReplacementSaleSOARequestPayload();
		
		requestPayload.setRequestId(UUID.randomUUID().toString());
		requestPayload.setTransactionId(UUID.randomUUID().toString());
		
		ReplacementSalePayload payload = inputRequest.getReplacementSalePayload();
		requestPayload.setProposerClientId(payload.getProposerClientId());
		requestPayload.setInsuredClientId(payload.getInsuredClientId());
		requestPayload.setCurrentPolicyNumber(payload.getCurrentPolicyNumber());
		requestPayload.setCurrentPolicySignDate(payload.getCurrentPolicySignDate());
		requestPayload.setUinNo(payload.getUinNo());
		
		return requestPayload;
	}

	private Header getHeaderReplacementSalePolicySplittingAPI() {
		Header header = new Header();
		header.setSoaAppId("FULFILLMENT");
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		return header;
	}
	
	private HttpEntity getHttpEntityReplacementSalePolicySplittingAPI(Request inputRequest) {
		HttpHeaders headers = new HttpHeaders();
		OauthTokenResponse oauthTokenResponse = this.getAccessToken();
		String accessToken = oauthTokenResponse.getAccess_token();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(AppConstants.AUTH, AppConstants.BEARER + accessToken);
		headers.add("X-IBM-Client-Id", authUserName);
		headers.add("X-IBM-Client-Secret", authPassword);
		return new HttpEntity<>(inputRequest, headers);
	}
	
	
	public OauthTokenResponse getAccessToken() {
		
		RestTemplate restTemplate = new RestTemplate();
		String plainClientCredentials = authUserName + ":" + authPassword;
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.add(AppConstants.AUTH, "Basic " + base64ClientCredentials);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("grant_type", AppConstants.PASSWORD);
		multiValueMap.add("scope", "CustomerServicing");
		multiValueMap.add("username", authTokenUsername);
		multiValueMap.add(AppConstants.PASSWORD, authTokenPassword);

		HttpEntity<?> httpEntity = new HttpEntity<>(multiValueMap, headers);
		return restTemplate.postForObject(oauthTokenUrl, httpEntity, OauthTokenResponse.class);
	}

	@Override
	public String encryptErrorResponse(Exception ex, String key) throws Exception{

		ReplacementSaleAPIResponse replacementSaleResponse =  new ReplacementSaleAPIResponse();
		ReplacementOutputResponse outputResponse = new ReplacementOutputResponse();

		if(ex instanceof UserHandledException) {
			logger.error("User Handled exception occurred for request object {}", Util.getExceptionAsString(ex));

			MsgInfo msgInfo = new MsgInfo(AppConstants.BAD_REQUEST_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);
			replacementSaleResponse.setMsgInfo(msgInfo);
			outputResponse.setResponse(replacementSaleResponse);

		} else {
			MsgInfo msgInfo = new MsgInfo(AppConstants.INTERNAL_SERVER_ERROR_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);

			replacementSaleResponse.setMsgInfo(msgInfo);
			outputResponse.setResponse(replacementSaleResponse);
			logger.info("Exception Response Message : {}",replacementSaleResponse);
		}
		return EncryptionDecryptionTransformUtil.encrypt(objectMapper.writeValueAsString(replacementSaleResponse),key);
	}

	private AuditingDetails getAuditDetails(ReplacementSalePayload request, ReplacementOutputResponse response) {

		AuditingDetails auditingDetails = new AuditingDetails();
		ResponseObject responseObject = new ResponseObject();
		responseObject.setAdditionalProperty(RESPONSE, response);
		auditingDetails.setAdditionalProperty(REQUEST, request);
		auditingDetails.setResponseObject(responseObject);
		auditingDetails.setServiceName(REPLACEMENT_SALE_SERVICE_NAME);
		logger.info("Setting auditing details for replacement sale API is {}",auditingDetails);
		return auditingDetails;

	}
	
}
