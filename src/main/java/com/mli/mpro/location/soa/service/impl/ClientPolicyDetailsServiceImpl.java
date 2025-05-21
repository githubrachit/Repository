package com.mli.mpro.location.soa.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.clientAllPolicyDetails.model.OutputResult;
import com.mli.mpro.location.clientAllPolicyDetails.model.PolicyDetailsData;
import com.mli.mpro.location.clientAllPolicyDetails.model.PolicyDetailsRequest;
import com.mli.mpro.location.models.clientPolicyDetailsRequestModels.Request;
import com.mli.mpro.location.models.clientPolicyDetailsResponseModels.OutputResponse;
import com.mli.mpro.location.models.clientPolicyDetailsResponseModels.Response;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.service.ClientPolicyDetailsService;
import com.mli.mpro.neo.models.Header;
import com.mli.mpro.neo.models.MsgInfo;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.Payload;
import com.mli.mpro.utils.Utility;

@Service
public class ClientPolicyDetailsServiceImpl implements ClientPolicyDetailsService {

	@Autowired
	private AuditService auditingService;

	@Value("${urlDetails.clientPolicyDetails}")
	private String cpdUrl;

	private static final Logger logger = LoggerFactory.getLogger(ClientPolicyDetailsServiceImpl.class);

	@Override
	public ResponseEntity<Object> executeClientPolicyDetailsService(PolicyDetailsRequest policyDetailsRequest,
			String agentId) throws UserHandledException {
		com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest outputRequest = null;
		ResponseEntity<OutputResponse> soaOutPutResponse = null;
		try {
			logger.info("inside ClientPolicyDetailsService api");
			logger.info("request receievd for ClientPolicyDetailsService api: {}", policyDetailsRequest);
			if (!validatePolicyDetailsRequest(policyDetailsRequest)) {
				return buildErrorResponse(AppConstants.BAD_REQUEST_CODE, "Bad Request", SoaConstants.INVALID_OBJECT);
			}
			RestTemplate restTemplate = new RestTemplate(Utility.clientHttpRequestFactory(20000));
			outputRequest = setDataForClientPolicyDetails(policyDetailsRequest);
			logger.info("request for ClientPolicyDetailsService soa api: {}", outputRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.add(SoaConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest> httpEntity = new HttpEntity<>(
					outputRequest, headers);
			long requestedTime = System.currentTimeMillis();
			soaOutPutResponse = restTemplate.exchange(cpdUrl, HttpMethod.POST, httpEntity, OutputResponse.class);
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info("Time took to process the ClientPolicyDetailsService request {} miliseconds ", processedTime);
			return handleResponse(soaOutPutResponse, outputRequest, agentId, policyDetailsRequest);
		} catch (HttpServerErrorException e) {
			logger.info("Eroor Response recieved from cpd service : {}", e.getResponseBodyAsString());
			if (outputRequest != null) {
				AuditingDetails aditingDeatils = setAuditingDetails("500", String.valueOf(e.getStatusCode().value()),
						e.getResponseBodyAsString(), outputRequest, agentId, policyDetailsRequest);
				auditingService.saveAuditTransactionDetails(aditingDeatils);
			}

			return buildErrorResponse("500", SoaConstants.FAILURE, SoaConstants.UNKNOWN_ERROR);
		} catch (ResourceAccessException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			if (soaOutPutResponse != null) {
				AuditingDetails aditingDeatils = setAuditingDetails("500", "500", e.getMessage(), outputRequest,
						agentId, policyDetailsRequest);
				auditingService.saveAuditTransactionDetails(aditingDeatils);
			}
			return buildErrorResponse("500", SoaConstants.FAILURE, "Timeout issue occuured from soa side");
		} catch (Exception e) {
			logger.error("Exception occuured while processing ClientPolicyDetailsService {}",
					Utility.getExceptionAsString(e));
			if (soaOutPutResponse != null) {
				AuditingDetails aditingDeatils = setAuditingDetails("", "500", soaOutPutResponse, outputRequest,
						agentId, policyDetailsRequest);
				auditingService.saveAuditTransactionDetails(aditingDeatils);
			}
		}
		return null;
	}

	private ResponseEntity<Object> buildErrorResponse(String msgCode, String msg, String msgDescription) {
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setMsgCode(msgCode);
		msgInfo.setMsg(msg);
		msgInfo.setMsgDescription(msgDescription);
		return getOutputResult(new Header(), msgInfo, new Payload());
	}

	private boolean validatePolicyDetailsRequest(PolicyDetailsRequest policyDetailsRequest) {
		if (policyDetailsRequest != null && policyDetailsRequest.getRequest() != null
				&& policyDetailsRequest.getRequest().getData() != null) {
			PolicyDetailsData data = policyDetailsRequest.getRequest().getData();
			if (!StringUtils.isEmpty(data.getClientId()) && !StringUtils.isEmpty(data.getTransactionId())) {
				return true;
			}
		}
		return false;
	}

	private ResponseEntity<Object> getOutputResult(Header header, MsgInfo msgInfo, Payload paylaod) {
		OutputResult result = new OutputResult();
		OutputResponse outputResponse = new OutputResponse();
		Response response = new Response();
		response.setHeader(header);
		response.setMsgInfo(msgInfo);
		response.setPayload(paylaod);
		outputResponse.setResponse(response);
		result.setResult(outputResponse);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	private AuditingDetails setAuditingDetails(String statusCode, String httpStatusCode, Object outputResponse,
			Object inputRequest, String agentId, PolicyDetailsRequest policyDetailsRequest) {
		AuditingDetails auditDetails = new AuditingDetails();
		auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
		ResponseObject respoObject = new ResponseObject();
		respoObject.setAdditionalProperty(AppConstants.RESPONSE, outputResponse);
		auditDetails.setResponseObject(respoObject);
		auditDetails.setServiceName("Client All Policy Details");
		auditDetails
				.setTransactionId(!StringUtils.isBlank(policyDetailsRequest.getRequest().getData().getTransactionId())
						? Long.valueOf(policyDetailsRequest.getRequest().getData().getTransactionId())
						: 0);
		auditDetails.setAgentId(agentId);
		auditDetails.setHttpStatusCode(httpStatusCode);
		auditDetails.setStatusCode(statusCode);
		auditDetails.setRequestId(UUID.randomUUID().toString());
		return auditDetails;
	}

	public ResponseEntity<Object> handleResponse(
			ResponseEntity<com.mli.mpro.location.models.clientPolicyDetailsResponseModels.OutputResponse> soaResponse,
			com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest soaInputRequest, String agentId,
			PolicyDetailsRequest policyDetailsRequest) throws UserHandledException {
		logger.info("Response recieved from cpd service : {}", soaResponse);
		if (soaResponse !=null && soaResponse.hasBody() && soaResponse.getStatusCode() == HttpStatus.OK) {
			String msgCode = Optional.ofNullable(soaResponse.getBody()).map(OutputResponse::getResponse)
					.map(Response::getMsgInfo).map(MsgInfo::getMsgCode).orElse("");
			AuditingDetails auditingDeatils = setAuditingDetails(msgCode,
					String.valueOf(soaResponse.getStatusCode().value()), soaResponse, soaInputRequest, agentId,
					policyDetailsRequest);
			auditingService.saveAuditTransactionDetails(auditingDeatils);
			Payload payload = Optional.ofNullable(soaResponse.getBody()).map(OutputResponse::getResponse)
					.map(Response::getPayload).orElse(null);
			Header header = Optional.ofNullable(soaResponse.getBody()).map(OutputResponse::getResponse)
					.map(Response::getHeader).orElse(null);
			if (msgCode.equals("200")) {
				com.mli.mpro.neo.models.MsgInfo msginfo = new com.mli.mpro.neo.models.MsgInfo();
				msginfo.setMsgCode("200");
				msginfo.setMsg(SoaConstants.SUCCESS);
				msginfo.setMsgDescription("Client's all policy details fetched successfully.");
				return getOutputResult(header, msginfo, payload);
			} else {
				return buildErrorResponse("422", "Bad Response", SoaConstants.UNKNOWN_ERROR);
			}
		} else {
			return buildErrorResponse("500", SoaConstants.FAILURE, SoaConstants.UNKNOWN_ERROR);
		}
	}

	private com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest setDataForClientPolicyDetails(
			PolicyDetailsRequest policyDetailsRequest) {
		PolicyDetailsData data = policyDetailsRequest.getRequest().getData();
		Request policyDetailRequest = new Request();
		Header header = new Header();
		com.mli.mpro.location.models.clientPolicyDetailsRequestModels.Payload payload = new com.mli.mpro.location.models.clientPolicyDetailsRequestModels.Payload();
		com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest request = new com.mli.mpro.location.models.clientPolicyDetailsRequestModels.OutputRequest();
		payload.setClientId(data.getClientId());
		header.setSoaAppId(SoaConstants.FULFILLMENT);
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		policyDetailRequest.setHeader(header);
		policyDetailRequest.setPayload(payload);
		request.setRequest(policyDetailRequest);
		return request;
	}
}
