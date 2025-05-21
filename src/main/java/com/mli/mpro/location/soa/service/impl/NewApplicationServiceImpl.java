package com.mli.mpro.location.soa.service.impl;

import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;
import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.newApplication.model.NewApplicationRequest;
import com.mli.mpro.location.newApplication.model.OutputResponse;
import com.mli.mpro.location.newApplication.model.Response;
import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.location.newApplication.model.Result;
import com.mli.mpro.location.newApplication.model.SoaInputRequest;
import com.mli.mpro.location.newApplication.model.SoaOutputResponse;
import com.mli.mpro.location.newApplication.model.SoaRequest;
import com.mli.mpro.location.newApplication.model.SoaResponse;
import com.mli.mpro.location.soa.constants.NewApplicationConstants;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.service.NewApplicationService;
import com.mli.mpro.utils.Utility;

import static com.mli.mpro.productRestriction.util.AppConstants.FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST;

@Service
public class NewApplicationServiceImpl implements NewApplicationService {

	@Value("${urlDetails.brms.cloud.server}")
	private String brmsCloudServer;
	@Value("${urlDetails.brms.cloud.agent}")
	private String cloudAgent;
	@Value("${urlDetails.brms.apigw.key}")
	private String brmsApigwKey;
	@Value("${urlDetails.brms.api_key}")
	private String brmsApiKey;
	@Value("${urlDetails.agent.access.url}")
	private String accessUrl;
	@Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.secretKey}")
	private String secretKey;
	@Value("${urlDetails.brms.cloud.flag}")
	private String brmsCloudFlag;

	private static final Logger logger = LoggerFactory.getLogger(NewApplicationServiceImpl.class);

	@Override
	public ResponseEntity<OutputResponse> executeNewApplicationService(NewApplicationRequest newApplicationRequest) {
		try {
			logger.info("request received for NewApplication api: {}", newApplicationRequest );
			Utility utility= new Utility();
			String jsonString=Utility.getJsonRequest(newApplicationRequest);
			Set<ErrorResponse> errors= utility.validateJson(jsonString, AppConstants.NEW_APPLICATION);
			com.mli.mpro.location.newApplication.model.OutputResponse outputResponse = new com.mli.mpro.location.newApplication.model.OutputResponse();
			com.mli.mpro.location.newApplication.model.Response response1 = new com.mli.mpro.location.newApplication.model.Response();
			if(!CollectionUtils.isEmpty(errors)){
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, newApplicationRequest, errors);
				ResponseMsgInfo msgInfo=new ResponseMsgInfo();
				Result result = new Result();
				response1.setMsginfo(msgInfo);
				result.setResponse(response1);
				outputResponse.setResult(result);
				outputResponse.getResult().getResponse().getMsginfo().setMsgCode(Integer.parseInt("400"));
				outputResponse.getResult().getResponse().getMsginfo().setErrors(errors);
				//return new ResponseEntity<>(new Gson().toJson(outputResponse), HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(outputResponse, HttpStatus.BAD_REQUEST);
			}
			ResponseEntity<SoaOutputResponse> response = null;
			if (validateNewApplicationRequest(newApplicationRequest)) {
				SoaInputRequest soaInputRequest = new SoaInputRequest();
				SoaRequest soaRequest = new SoaRequest();
				Header header = new Header();
				header.setSoaAppId(SoaConstants.FULFILLMENT);
				header.setSoaCorrelationId(UUID.randomUUID().toString());
				soaRequest.setHeader(header);
				soaRequest.setPayload(newApplicationRequest.getRequest().getPayload());
				soaInputRequest.setRequest(soaRequest);
				logger.info("request for NewApplication soa api: {}", soaInputRequest);
				RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory(SoaConstants.SOA_TIMEOUT));
				HttpHeaders headers = new HttpHeaders();
				if (SoaConstants.TRUE.equalsIgnoreCase(brmsCloudFlag)) {
					String cloudUrl = brmsCloudServer.trim() + cloudAgent.trim();
					headers.add(SoaConstants.X_APIGW_API_ID, brmsApigwKey);
					headers.add(SoaConstants.X_API_KEY, brmsApiKey);
					HttpEntity<SoaInputRequest> httpEntity = new HttpEntity<>(soaInputRequest, headers);
					response = restTemplate.exchange(cloudUrl, HttpMethod.POST, httpEntity, SoaOutputResponse.class);
				} else {
					headers.add(NewApplicationConstants.X_IBM_CLIENT_ID, cleintID);
					headers.add(NewApplicationConstants.X_IBM_CLIENT_SECRET, secretKey);
					HttpEntity<SoaInputRequest> httpEntity = new HttpEntity<>(soaInputRequest, headers);
					response = restTemplate.exchange(accessUrl, HttpMethod.POST, httpEntity, SoaOutputResponse.class);
				}
				logger.info("response received for NewApplication api: {}", response);
				return handleResponse(response);
			} else {
				return generateResponse(HttpStatus.BAD_REQUEST, "Invalid Request", "Please validate the inputRequest");
			}
		} catch (ResourceAccessException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Timeout occurred",
					"Timeout issue occuured from soa side");
		} catch (HttpClientErrorException e) {
			logger.error("HttpClientErrorException occurs {}", Utility.getExceptionAsString(e));
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST
					|| e.getStatusCode() == HttpStatus.UNAUTHORIZED
					|| e.getStatusCode() == HttpStatus.NOT_FOUND
					|| e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
				logger.error("Bad Request error received from server");
				return generateResponse(e.getStatusCode(), "Bad Request/ Unauthorized/ Request Timed out",
						"Bad Request error received from server");
			}
		} catch (HttpServerErrorException e) {
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				logger.error("HttpServerErrorException occurs {}", Utility.getExceptionAsString(e));
				return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Request",
						"Failed to recieve a response from a server");
			}
		} catch (Exception e) {
			return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error",
					"Soemthing happend with soa call");
		}
		return null;
	}

	public ResponseEntity<OutputResponse> generateResponse(HttpStatus statusCode, String message,
			String msgDescription) {
		ResponseMsgInfo responseMsgInfo = new ResponseMsgInfo();
		responseMsgInfo.setMsg(message);
		responseMsgInfo.setMsgCode(statusCode.value());
		responseMsgInfo.setMsgDescription(msgDescription);

		OutputResponse outputResponse = new OutputResponse();
		Result result = new Result();
		Response response = new Response();
		response.setMsginfo(responseMsgInfo);
		response.setPayload(new SoaResponse());
		result.setResponse(response);
		outputResponse.setResult(result);

		return new ResponseEntity<>(outputResponse, statusCode);
	}

	private boolean validateNewApplicationRequest(NewApplicationRequest newApplicationRequest) {
		return newApplicationRequest != null && newApplicationRequest.getRequest() != null
				&& newApplicationRequest.getRequest().getPayload() != null;
	}

	private OutputResponse getOutputResponse(SoaOutputResponse soaOutputResponse, HttpStatus httpStatus) {
		if(soaOutputResponse !=null && soaOutputResponse.getResponse() !=null) {
		SoaResponse soaResponse = soaOutputResponse.getResponse();
		ResponseMsgInfo responseMsgInfo = new ResponseMsgInfo();
		responseMsgInfo.setMsgCode(httpStatus.value());
		responseMsgInfo.setMsg("Success");
		responseMsgInfo.setMsgDescription(SoaConstants.DATA_FETCH);
		Response response = new Response();
		response.setMsginfo(responseMsgInfo);
		response.setPayload(soaResponse);
		Result result = new Result(response);
		return new OutputResponse(result);
	}
		return null;
	}

	private ResponseEntity<OutputResponse> handleResponse(ResponseEntity<SoaOutputResponse> response) {
		if (response !=null && response.getBody() != null && response.getStatusCode() == HttpStatus.OK) {
			logger.info("Data Fetched successfully");
			OutputResponse outputResponse = getOutputResponse(response.getBody(), response.getStatusCode());
				return new ResponseEntity<>(outputResponse, HttpStatus.OK);
		} else {
			logger.error("Internal server error");
			return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Response failed",
					"Failed to recieve a response from a server");
		}
	}
	private ClientHttpRequestFactory clientHttpRequestFactory(int timeout) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		factory.setReadTimeout(timeout);
		return factory;
	}
}
