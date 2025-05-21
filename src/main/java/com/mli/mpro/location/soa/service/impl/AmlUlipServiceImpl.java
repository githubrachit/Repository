package com.mli.mpro.location.soa.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.amlulip.training.model.*;
import com.mli.mpro.location.models.soaCloudModels.SoaCloudResponse;
import com.mli.mpro.location.services.SoaCloudService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.location.soa.constants.AmlUlipConstants;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.constants.TrainingConstants;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import com.mli.mpro.location.soa.service.AmlUlipService;
import com.mli.mpro.location.training.model.Data;
import com.mli.mpro.location.training.model.Payload;
import com.mli.mpro.location.training.model.ResponsePayload;
import com.mli.mpro.location.training.model.TrainingRequest;
import com.mli.mpro.location.training.model.TrainingResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

@Service
public class AmlUlipServiceImpl implements AmlUlipService {

	@Autowired
	private TrainingServiceImpl trainingServiceImpl;

	@Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.secretKey}")
	private String secretKey;
	@Value("${urlDetails.trainingUlip}")
	private String trainingUlip;
	@Value("${urlDetails.trainingAml}")
	private String trainingAml;
	@Value("${urlDetails.dataLakeUlip}")
	private String dataLakeUlip;

	@Autowired
	private AuditService auditingService;

	@Autowired
	private SoaCloudService soaCloudService;

	private static final Logger logger = LoggerFactory.getLogger(AmlUlipServiceImpl.class);

	@Override
	public ResponseEntity<Object> executeAmlUlipService(TrainingRequest trainingRequest) throws SoaCustomException {
		try {
			logger.info("training api request receieved: {}", trainingRequest);
			if (trainingServiceImpl.validateTrainingRequest(trainingRequest)) {
				Data requestData = trainingRequest.getRequest().getData();
				String channel = requestData.getAgentxChannel();
				Payload payload = trainingServiceImpl.brmsAmlUlipTrainingRequest(requestData.getRole(), channel, null);
				TrainingResponse trainingResponse = trainingServiceImpl.checkBrmsRuleAndCallSoaAPi(payload);
				ResponsePayload responsepayload = trainingResponse.getResult().getResponse().getPayload();
				if (trainingResponse.getResult().getResponse().getMsginfo().getMsgCode().equals("200")) {
					logger.info("aml status {} ulip status {}", responsepayload.getAml(), responsepayload.getUlip());
					if (responsepayload.getAml() && responsepayload.getUlip()) {
						String[] keys = getKeys(channel, requestData);
						SoaAmlRequest amlPayload = brmsAmlTrainingRequest(keys,
								AmlUlipConstants.CERTIFICATIONDETAILREQUEST);
						SoaUlipRequest ulipPayload = brmsUlipTrainingRequest(keys, AmlUlipConstants.ULIPREQUEST);
						OutputResponse outputResponse = executeUlipAMlApi(amlPayload, ulipPayload, trainingRequest);
						outputResponse.getResult().getResponse().getPayload()
								.setNewApplicationButton(responsepayload.getNewApplicationButton());
						logger.info("response from amlUlipTraining {}", outputResponse);
						return new ResponseEntity<>(outputResponse, HttpStatus.OK);
					} else {
						MsgInfo msgInfo = new MsgInfo(SoaConstants.SUCCESS, "200",
								"channel regarding details does not exist");
						OutputResponse outputResponse = new OutputResponse();
						OutputPayload outputPayload = new OutputPayload();
						Result result = new Result();
						Response response = new Response();
						response.setHeader(new Header());
						response.setMsginfo(msgInfo);
						outputPayload.setNewApplicationButton(false);
						response.setPayload(outputPayload);
						result.setResponse(response);
						outputResponse.setResult(result);
						return new ResponseEntity<>(outputResponse, HttpStatus.OK);
					}
				} else {
					throw new SoaCustomException(SoaConstants.FAILURE, AmlUlipConstants.BACKEND_NOT_WORKING, "500");
				}
			} else {
				throw new SoaCustomException("bad request", "Request object is invalid !!", "400");
			}
		} catch (SoaCustomException e) {
			return trainingServiceImpl.buildErrorResponse(e.getMsg(), e.getMsgDescription(), e.getMsgCode(),
					HttpStatus.OK);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Re-interrupt the thread
			logger.error("error in amlUlipStatus in api {}", Utility.getExceptionAsString(e));
		} catch (Exception e) {
			logger.error("error in amlUlipStatus in training api {}", Utility.getExceptionAsString(e));
		}
		return null;

	}

	public OutputResponse executeUlipAMlApi(SoaAmlRequest amlPayload, SoaUlipRequest ulipPayload,
			TrainingRequest trainingRequest) throws SoaCustomException, InterruptedException, ExecutionException {
		logger.info("calling aml and ulip service in parallel with amlPayload {} ulipPayload {}", amlPayload, ulipPayload);
		CompletableFuture<ResponseEntity<SoaResult>> ulipFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return processApi(ulipPayload, trainingUlip, AmlUlipConstants.ULIP_SERVICE_NAME, trainingRequest);
			} catch (SoaCustomException | UserHandledException e) {
				logger.error("issue occurred at ulip service with {}", Utility.getExceptionAsString(e));
				return null;
			}
		});
		CompletableFuture<ResponseEntity<SoaResult>> amlFuture = CompletableFuture.supplyAsync(() -> {
			try {
				if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_ALM_TRAINING_DL_FLAG)){
					return processApiForDL(amlPayload);
				}else {
					return processApi(amlPayload, trainingAml, AmlUlipConstants.AML_SERVICE_NAME, trainingRequest);
				}
			} catch (SoaCustomException | UserHandledException e) {
				logger.error("issue occurred at aml service with {}", Utility.getExceptionAsString(e));
				return null;
			}
		});
		CompletableFuture.allOf(ulipFuture, amlFuture).join();
		ResponseEntity<SoaResult> ulipResult = ulipFuture.get();
		ResponseEntity<SoaResult> amlResult = amlFuture.get();
		if (ulipResult != null && amlResult != null) {
			return handleResponses(ulipResult, amlResult);
		} else {
			throw new SoaCustomException("faliure", AmlUlipConstants.UNKNOWN_ERROR, "500");
		}

	}

	private ResponseEntity<SoaResult> processApiForDL(SoaAmlRequest amlRequest) throws UserHandledException {
		logger.info("AML training feature flag is enable calling DL api for amlRequest {}",amlRequest);
		SoaCloudResponse<SoaResponsePayload> soaCloudResponse = soaCloudService.fetchSOAAMLTrainingDLApi(amlRequest);
		SoaResult soaResult = new SoaResult();
		SoaOutputResponse soaOutputResponse = new SoaOutputResponse();
		Header header = new Header();
		MsgInfo msgInfo = new MsgInfo();
		String msgCode = soaCloudResponse.getResponse().getMsgInfo().getMsgCode();
		msgInfo.setMsgCode(msgCode);
		msgInfo.setMsg(soaCloudResponse.getResponse().getMsgInfo().getMsg());
		msgInfo.setMsgDescription(soaCloudResponse.getResponse().getMsgInfo().getMsgDescription());
		header.setSoaAppId(soaCloudResponse.getResponse().getHeader().getSoaAppId());
		header.setSoaCorrelationId(soaCloudResponse.getResponse().getHeader().getSoaCorrelationId());
		soaOutputResponse.setPayload(soaOutputResponse.getPayload());
		soaOutputResponse.setMsginfo(msgInfo);
		soaOutputResponse.setHeader(header);
		soaResult.setResponse(soaOutputResponse);
		if(msgCode==AppConstants.SUCCESS_RESPONSE){
			return new ResponseEntity<>(soaResult,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(soaResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private OutputResponse handleResponses(ResponseEntity<SoaResult> ulipResult, ResponseEntity<SoaResult> amlResult)
			throws SoaCustomException {
		logger.info("handling response with ulip status code {} aml status code {}", ulipResult.getStatusCode(), amlResult.getStatusCode());
		if (ulipResult.getStatusCode() == HttpStatus.OK && amlResult.getStatusCode() == HttpStatus.OK) {
			return handleSuccessfulResponses(ulipResult, amlResult);
		} else if ((ulipResult.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
				&& amlResult.getStatusCode() == HttpStatus.OK) || amlResult.getStatusCode() == HttpStatus.OK) {
			return handleUlipFailureCase(ulipResult, amlResult);
		} else if ((ulipResult.getStatusCode() == HttpStatus.OK
				&& amlResult.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
				|| ulipResult.getStatusCode() == HttpStatus.OK) {
			return handleAmlFailureCase(ulipResult, amlResult);
		} else {
			return handleBothFailueCases(ulipResult, amlResult);
		}
	}

	private OutputResponse handleBothFailueCases(ResponseEntity<SoaResult> ulipResult,
			ResponseEntity<SoaResult> amlResult) {
		logger.info("handling error case for both api failures");
		OutputPayload outputPaylaod = new OutputPayload();
		String specifiedPersonName = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
				.map(SoaResult::getResponse).map(SoaOutputResponse::getPayload)
				.map(payload -> payload.getRegistrationDetails().get(0).getAgentName()).orElse("");
		MsgInfo msgInfo = new MsgInfo(SoaConstants.SUCCESS, "200", "Response Generated Successfully");
		SoaOutputResponse ulipResponse = Optional.ofNullable(ulipResult).map(ResponseEntity::getBody)
				.map(SoaResult::getResponse).orElse(null);
		SoaOutputResponse amlResponse = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
				.map(SoaResult::getResponse).orElse(null);
		String ulipexpiryDate = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getPayload)
				.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
		String amlTrainingExpirationDate = Optional.ofNullable(amlResponse).map(SoaOutputResponse::getPayload)
				.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
		logger.info("from both api failure, ulipExpiryDate {}, amlTrainingExpiryDate {}",ulipexpiryDate, amlTrainingExpirationDate);
		outputPaylaod.setAmlTrainingExpirationDate(amlTrainingExpirationDate);
		outputPaylaod.setUlipTrainingExpirationDate(ulipexpiryDate);
		outputPaylaod.setSpecifiedPersonName(specifiedPersonName);
		outputPaylaod.setUlip(false);
		outputPaylaod.setAml(false);
		Header header = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getHeader).orElse(null);
		return getSucessResponse(msgInfo, header, outputPaylaod);
	}

	private OutputResponse handleAmlFailureCase(ResponseEntity<SoaResult> ulipResult,
			ResponseEntity<SoaResult> amlResult) {
		try {
			logger.info("handling aml error response");
			OutputPayload outputPaylaod = new OutputPayload();
			String ulipTrainingExpirationDate = "";
			String specifiedPersonName = Optional.ofNullable(ulipResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getAgentName()).orElse("");
			MsgInfo msgInfo = new MsgInfo(SoaConstants.SUCCESS, "200", "Response Generated Successfully");
			SoaOutputResponse ulipResponse = Optional.ofNullable(ulipResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).orElse(null);
			SoaOutputResponse amlResponse = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).orElse(null);
			String ulipexpiryDate = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
			String amlTrainingExpirationDate = Optional.ofNullable(amlResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");

			ulipTrainingExpirationDate = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getStrreplytoquest()).orElse("");
			logger.info("from aml failure, ulipExpiryDate {}, amlTrainingExpiryDate {}",ulipexpiryDate, amlTrainingExpirationDate);
			outputPaylaod.setAmlTrainingExpirationDate(amlTrainingExpirationDate);
			outputPaylaod.setUlipTrainingExpirationDate(ulipexpiryDate);
			outputPaylaod.setSpecifiedPersonName(specifiedPersonName);

			Header header = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getHeader).orElse(null);
			if (ulipResponse != null && ulipResponse.getMsginfo() != null
					&& ulipResponse.getMsginfo().getMsgCode().equals("200")) {
				outputPaylaod = trainingStatusCalculator(outputPaylaod, null, ulipTrainingExpirationDate);
			} else {
				outputPaylaod.setUlip(false);
			}
			outputPaylaod.setAml(false);
			return getSucessResponse(msgInfo, header, outputPaylaod);
		} catch (Exception e) {
			logger.error("error in amlUlipStatus in training api {}", Utility.getExceptionAsString(e));
		}

		return null;

	}

	private OutputResponse handleUlipFailureCase(ResponseEntity<SoaResult> ulipResult,
			ResponseEntity<SoaResult> amlResult) {
		logger.info("handling ulip error response");
		try {
			OutputPayload outputPaylaod = new OutputPayload();
			String specifiedPersonName = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getAgentName()).orElse("");
			MsgInfo msgInfo = new MsgInfo(SoaConstants.SUCCESS, "200", SoaConstants.SUCCESS_RESPONSE);
			SoaOutputResponse ulipResponse = Optional.ofNullable(ulipResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).orElse(null);
			SoaOutputResponse amlResponse = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).orElse(null);
			String ulipexpiryDate = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
			String amlTrainingExpirationDate = Optional.ofNullable(amlResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
			logger.info("from ulip failure, ulipExpiryDate {}, amlTrainingExpiryDate {}",ulipexpiryDate, amlTrainingExpirationDate);
			outputPaylaod.setAmlTrainingExpirationDate(amlTrainingExpirationDate);
			outputPaylaod.setUlipTrainingExpirationDate(ulipexpiryDate);
			outputPaylaod.setSpecifiedPersonName(specifiedPersonName);
			Header header = Optional.ofNullable(amlResponse).map(SoaOutputResponse::getHeader).orElse(null);
			if (amlResponse != null && amlResponse.getMsginfo() != null
					&& amlResponse.getMsginfo().getMsgCode().equals("200")) {
				outputPaylaod = trainingStatusCalculator(outputPaylaod, amlTrainingExpirationDate, null);
			} else {
				outputPaylaod.setUlip(false);
			}
			outputPaylaod.setAml(false);
			return getSucessResponse(msgInfo, header, outputPaylaod);
		} catch (Exception e) {
			logger.error("error  in amlUlipStatus in training api {}", Utility.getExceptionAsString(e));
		}
		return null;
	}

	private OutputResponse handleSuccessfulResponses(ResponseEntity<SoaResult> ulipResult,
			ResponseEntity<SoaResult> amlResult) throws SoaCustomException {
		try {
			logger.info("handling success case");
			OutputPayload outputPaylaod = new OutputPayload();
			String ulipTrainingExpirationDate = "";
			String specifiedPersonName = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
					.map(SoaResult::getResponse).map(SoaOutputResponse::getPayload)
					.map(payload -> payload.getRegistrationDetails().get(0).getAgentName()).orElse("");
			logger.info("specified person name {}", specifiedPersonName);
			boolean amlResponseNotNull = false;
			MsgInfo msgInfo = new MsgInfo("Success", "200", SoaConstants.SUCCESS_RESPONSE);
			if (ulipResult.getStatusCode() == HttpStatus.OK && amlResult.getStatusCode() == HttpStatus.OK) {
				SoaOutputResponse ulipResponse = Optional.ofNullable(ulipResult).map(ResponseEntity::getBody)
						.map(SoaResult::getResponse).orElse(null);
				SoaOutputResponse amlResponse = Optional.ofNullable(amlResult).map(ResponseEntity::getBody)
						.map(SoaResult::getResponse).orElse(null);
				String ulipexpiryDate = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getPayload)
						.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
				logger.info("ulip expiry date {}", ulipexpiryDate);
				String amlTrainingExpirationDate = Optional.ofNullable(amlResponse).map(SoaOutputResponse::getPayload)
						.map(payload -> payload.getRegistrationDetails().get(0).getExpiryDate()).orElse("");
				logger.info("aml training expiry date {}", amlTrainingExpirationDate);
				ulipTrainingExpirationDate = Optional.ofNullable(ulipResponse).map(SoaOutputResponse::getPayload)
						.map(payload -> payload.getRegistrationDetails().get(0).getStrreplytoquest()).orElse("");
				logger.info("ulip training expiry date {}", amlTrainingExpirationDate);
				outputPaylaod.setAmlTrainingExpirationDate(amlTrainingExpirationDate);
				outputPaylaod.setUlipTrainingExpirationDate(ulipexpiryDate);
				outputPaylaod.setSpecifiedPersonName(specifiedPersonName);
				Header header = Optional.ofNullable(amlResponse).map(SoaOutputResponse::getHeader).orElse(null);
				if (ulipResponse != null && ulipResponse.getMsginfo() == null && amlResponse != null
						&& amlResponse.getPayload() != null) {
					amlResponseNotNull = !amlResponse.getPayload().getRegistrationDetails().isEmpty();
					if (amlResponseNotNull) {
						outputPaylaod.setAml(false);
						outputPaylaod.setUlip(false);
					} else {
						outputPaylaod = trainingStatusCalculator(outputPaylaod, amlTrainingExpirationDate, null);
					}
					return getSucessResponse(msgInfo, header, outputPaylaod);
				} else {
					if (amlResponse != null && amlResponse.getPayload() != null) {
						amlResponseNotNull = !amlResponse.getPayload().getRegistrationDetails().isEmpty();
					}
					outputPaylaod = handleMutilpleCombinations(amlResponse, ulipResponse, outputPaylaod,
							amlResponseNotNull, ulipTrainingExpirationDate, amlTrainingExpirationDate);
					return getSucessResponse(msgInfo, header, outputPaylaod);
				}
			}
		} catch (Exception e) {
			logger.error("error  in amlUlipStatus in training api {}", Utility.getExceptionAsString(e));
			throw new SoaCustomException("Failure", "Service not responding please try again later !!", "500");
		}

		return null;

	}

	private OutputPayload handleMutilpleCombinations(SoaOutputResponse amlResponse, SoaOutputResponse ulipResponse,
			OutputPayload outputPaylaod, boolean amlResponseNotNull, String ulipTrainingExpirationDate,
			String amlTrainingExpirationDate) {
		if (amlResponse.getMsginfo() == null || ulipResponse.getMsginfo() == null) {
			return outputPaylaod;
		}
		String ulipMsgCode = ulipResponse.getMsginfo().getMsgCode();
		String amlMsgCode = amlResponse.getMsginfo().getMsgCode();

		if (ulipMsgCode.equals("200") && amlMsgCode.equals("500")) {
			return trainingStatusCalculator(outputPaylaod, null, ulipTrainingExpirationDate);
		}
		if ((ulipMsgCode.equals("700") && amlMsgCode.equals("200"))
				&& (ulipMsgCode.equals("500") && amlMsgCode.equals("200"))) {
			if (amlResponseNotNull) {
				outputPaylaod.setAml(false);
				outputPaylaod.setUlip(false);
			} else {
				return trainingStatusCalculator(outputPaylaod, amlTrainingExpirationDate, null);
			}
		}
		if (ulipMsgCode.equals("700") && amlMsgCode.equals("500")) {
			outputPaylaod.setAml(false);
			outputPaylaod.setUlip(false);
		}
		if (ulipMsgCode.equals("200") && amlMsgCode.equals("200")) {
			return trainingStatusCalculator(outputPaylaod, amlTrainingExpirationDate, ulipTrainingExpirationDate);
		}
		outputPaylaod.setAml(false);
		outputPaylaod.setUlip(false);
		return outputPaylaod;
	}

	private OutputResponse getSucessResponse(MsgInfo msgInfo, Header header, OutputPayload outputPaylaod) {
		OutputResponse outputResponse = new OutputResponse();
		Result result = new Result();
		Response response = new Response();
		response.setHeader(header);
		response.setMsginfo(msgInfo);
		response.setPayload(outputPaylaod);
		result.setResponse(response);
		outputResponse.setResult(result);
		return outputResponse;
	}

	private ResponseEntity<SoaResult> processApi(Object payload, String apiUrl,
			String serviceName, TrainingRequest request) throws SoaCustomException, UserHandledException {
		ResponseEntity<SoaResult> soaResult = null;
		try {
			logger.info("calling service {}", serviceName);
			long requestedTime = System.currentTimeMillis();
			String url = apiUrl + "client_id=" + cleintID + "&client_secret=" + secretKey;
			RestTemplate restTemplate = new RestTemplate(Utility.clientHttpRequestFactory(AmlUlipConstants.API_TIMEOUT));
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(payload);
			ResponseEntity<SoaResult> apiResponse = restTemplate.postForEntity(url, jsonString, SoaResult.class);
			logger.info("service response {}", apiResponse);
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info(" Time took to process the tarining request {} miliseconds ", processedTime);
			if (apiResponse.getStatusCode() == HttpStatus.OK && apiResponse.getBody() != null) {
				String statusCode = Optional.ofNullable(apiResponse).map(ResponseEntity::getBody)
						.map(SoaResult::getResponse).map(SoaOutputResponse::getMsginfo).map(MsgInfo::getMsgCode)
						.orElse("");
				AuditingDetails auditingDetails = setAuditingDetails(statusCode,
						String.valueOf(apiResponse.getStatusCode().value()), apiResponse, payload, request,
						serviceName);
				auditingService.saveAuditTransactionDetails(auditingDetails);
				return apiResponse;
			} else {
				throw new SoaCustomException(TrainingConstants.FAILURE, "Something wrong at backend", "500");
			}
		} catch (ResourceAccessException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			throw new SoaCustomException(TrainingConstants.FAILURE, "Timeout occurred",
					"Timeout issue occuured from soa side");
		} catch (HttpServerErrorException e) {
			if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				logger.error("HttpServerErrorException occurs {}", Utility.getExceptionAsString(e));
				// Returning null for HTTP 500 Internal Server Error
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			AuditingDetails auditingDetails = setAuditingDetails("", "50", e, payload, request, serviceName);
			auditingService.saveAuditTransactionDetails(auditingDetails);
			logger.error("issue occurred at {} with {}", serviceName, Utility.getExceptionAsString(e));
		}
		return soaResult;

	}

	public ResponseEntity<Object> executeULIPDataLakeService(SoaUlipRequest ulipRequest) {
		try {
			logger.info("Request received for ULIP data lake service {}", ulipRequest);
			ResponseEntity ulipResponse = soaCloudService.callingSoaApi(ulipRequest, dataLakeUlip);
			logger.info("Response received from ULIP DL {}", ulipResponse);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SoaResult soaResult = objectMapper.convertValue(ulipResponse.getBody(), SoaResult.class);
			return new ResponseEntity<>(soaResult, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Exception occurred while calling data lake ULIP API for request {} exception {}", Utility.getExceptionAsString(ex));
			return trainingServiceImpl.buildErrorResponse(AppConstants.STATUS_FAILURE, AppConstants.INTERNAL_SERVER_ERROR, AppConstants.INTERNAL_SERVER_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private AuditingDetails setAuditingDetails(String statusCode, String httpStatusCode, Object outputResponse,
			Object payload, TrainingRequest trainingRequest, String serviceName) {
		AuditingDetails auditDetails = new AuditingDetails();
		auditDetails.setAdditionalProperty(AppConstants.REQUEST, payload);
		ResponseObject respoObject = new ResponseObject();
		respoObject.setAdditionalProperty(AppConstants.RESPONSE, outputResponse);
		auditDetails.setResponseObject(respoObject);
		auditDetails.setServiceName(serviceName);
		auditDetails.setTransactionId(0);
		auditDetails.setAgentId(trainingRequest.getRequest().getData().getAgentCode());
		auditDetails.setHttpStatusCode(httpStatusCode);
		auditDetails.setStatusCode(statusCode);
		auditDetails.setRequestId(UUID.randomUUID().toString());
		return auditDetails;
	}

	private SoaAmlRequest brmsAmlTrainingRequest(String[] keys, String type) {
		SoaAmlRequest request = new SoaAmlRequest();
		AmlPayload payload = new AmlPayload();
		Header header = new Header();
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		header.setSoaAppId(SoaConstants.FULFILLMENT);
		RequestData data = new RequestData();
		data.setId("");
		data.setKey1(keys[0]);
		data.setKey2(keys[1]);
		data.setKey3(keys[2]);
		data.setType(type);
		data.setTransTrackingID("");
		payload.setHeader(header);
		payload.setRequestData(data);
		request.setRequest(payload);
		return request;
	}

	private SoaUlipRequest brmsUlipTrainingRequest(String[] keys, String type) {
		SoaUlipRequest request = new SoaUlipRequest();
		UlipPayload payload = new UlipPayload();
		Header header = new Header();
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		header.setSoaAppId(SoaConstants.FULFILLMENT);
		UlipPayloadRequest ulipPayload = new UlipPayloadRequest();
		ulipPayload.setId("");
		ulipPayload.setKey1(keys[2]);
		ulipPayload.setKey2(keys[1]);
		ulipPayload.setKey3(keys[0]);
		ulipPayload.setType(type);
		ulipPayload.setTransTrackingID("");
		payload.setHeader(header);
		payload.setPayload(ulipPayload);
		request.setRequest(payload);
		return request;
	}

	public String[] getKeys(String channel, Data requestData) {
		String key1 = "";
		String key2 = "";
		String key3 = "";
		if ("X".equals(channel)) {
			// If channel is X
			key1 = requestData.getSpecifiedPersonCode().replaceAll("[^\\w\\s]", "");
			key2 = requestData.getAgentxChannel();
			key3 = "";
		} else if ("A".equals(channel) || "O".equals(channel) || "T".equals(channel)) {
			// If channel is A, O, or T
			key1 = requestData.getAgentCode();
			key2 = requestData.getAgentxChannel();
			key3 = "";
		} else {
			// Channel other than A, O, T, X
			key1 = requestData.getSpecifiedPersonCode();
			key2 = requestData.getAgentxChannel();
			key3 = requestData.getBranchCd();
		}
		return new String[] { key1, key2, key3 };
	}

	private OutputPayload trainingStatusCalculator(OutputPayload outputPaylaod, String amlexpiryDate, String ulipDate) {
		LocalDate todayDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
		if (amlexpiryDate != null && StringUtils.isNotBlank(amlexpiryDate)) {
			LocalDate amlDate = LocalDate.parse(amlexpiryDate);
			// Set the time to midnight for comparison
			amlDate = amlDate.atStartOfDay().toLocalDate();
			// Check if amlDate is today or in the future
			outputPaylaod.setAml(!amlDate.isBefore(todayDate));
		} else {
			outputPaylaod.setAml(false);
		}
		outputPaylaod.setUlip(ulipDate != null && ulipDate.equals("Y"));
		return outputPaylaod;
	}
}