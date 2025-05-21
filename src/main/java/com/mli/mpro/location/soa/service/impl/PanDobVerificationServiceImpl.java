package com.mli.mpro.location.soa.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.panDOBVerification.model.Data;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.location.panDOBVerification.model.PanDobRequest;
import com.mli.mpro.location.panDOBVerification.model.ResponsePayload;
import com.mli.mpro.location.panDOBVerification.model.Result;
import com.mli.mpro.location.panDOBVerification.model.SoaInputRequest;
import com.mli.mpro.location.panDOBVerification.model.SoaOutputResponse;
import com.mli.mpro.location.panDOBVerification.model.SoaResponse;
import com.mli.mpro.location.panDOBVerification.model.SoaResponsePayload;
import com.mli.mpro.location.soa.constants.PanDOBConstants;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.exception.InvalidRequestException;
import com.mli.mpro.location.soa.service.PanDobVerificationService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Service
public class PanDobVerificationServiceImpl implements PanDobVerificationService {

	private static final String ENVIRONMENT = System.getenv("env");

	@Value("${urlDetails.panDob.url}")
	private String panDobUrl;
	@Value("${urlDetails.pandob.x-api-key}")
	private String panDobKey;
	@Value("${urlDetails.panDob.x-apigw-api-id}")
	private String panDobAppId;
	@Value("${urlDetails.mockPanDobValidationSimulationUrl}")
	private String mockPanDobValidationSimulationUrl;
	@Autowired
	private AuditService auditService;

	private static final Logger logger = LoggerFactory.getLogger(PanDobVerificationServiceImpl.class);

	@Override
	public ResponseEntity<Object> executePanDobVerification(PanDobRequest panDobRequest) throws UserHandledException {
		logger.info("inside PanDobVerification api");
		com.mli.mpro.location.panDOBVerification.model.OutputResponse outputResponse = new com.mli.mpro.location.panDOBVerification.model.OutputResponse();

		com.mli.mpro.location.panDOBVerification.model.Result panDOBResult=new com.mli.mpro.location.panDOBVerification.model.Result();
		com.mli.mpro.location.panDOBVerification.model.Response response = new com.mli.mpro.location.panDOBVerification.model.Response();
		com.mli.mpro.location.common.soa.model.MsgInfo messageInfo=new MsgInfo();
		response.setMsginfo(messageInfo);
		panDOBResult.setResponse(response);
		outputResponse.setResult(panDOBResult);

		ResponseEntity<com.mli.mpro.location.panDOBVerification.model.SoaOutputResponse> soaResponse =null;
		HttpEntity<SoaInputRequest> httpEntity =null;

		com.mli.mpro.location.panDOBVerification.model.SoaInputRequest soaInputRequest = null;
		try {
			logger.info("request received for PanDobVerification api: {}", panDobRequest );
			Utility utility=new Utility();
			String jsonString = Utility.getJsonRequest(panDobRequest);
			Set<ErrorResponse> errors= utility.validateJson(jsonString,AppConstants.PAN_DOB_VERIFICATION);
			try {
				Date formattedDate = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HYPHEN).parse(panDobRequest.getRequest().getData().getDob());
				utility.validateDate(formattedDate, errors, AppConstants.PANDOB_VERIFICATION_DOB_PATH);
			}catch (ParseException ex){
				logger.error("ParseException occured while parding Dob {}", Utility.getExceptionAsString(ex));
			}
			if(!CollectionUtils.isEmpty(errors)){
				logger.error(FIELD_VALIDATION_ERROR_DURING_BAD_REQUEST, panDobRequest, errors);
				outputResponse.getResult().getResponse().getMsginfo().setMsgCode(AppConstants.BAD_REQUEST_CODE);
				outputResponse.getResult().getResponse().getMsginfo().setErrors(errors);
				return new ResponseEntity<>(new Gson().toJson(outputResponse), HttpStatus.BAD_REQUEST);
			}
			validateInputRequest(panDobRequest);
			soaInputRequest = buildSoaPanDobRequest(
					panDobRequest);
			logger.info("request for PanDobVerification soa api: {}", soaInputRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
			headers.add(SoaConstants.X_API_KEY, panDobKey);
			headers.add(SoaConstants.X_APIGW_API_ID, panDobAppId);
			RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory(SoaConstants.SOA_TIMEOUT));
			httpEntity = new HttpEntity<>(soaInputRequest, headers);
			long requestedTime = System.currentTimeMillis();
			String finalPanDobUrl = panDobUrl;
			if ((ENVIRONMENT.equals(ENV_DEV) || ENVIRONMENT.equals(ENV_UAT)) && Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.PAN_DOB_VALIDATION_SIMULATION))) {
				finalPanDobUrl = mockPanDobValidationSimulationUrl;
			}
			soaResponse = restTemplate
					.exchange(finalPanDobUrl, HttpMethod.POST, httpEntity,
							com.mli.mpro.location.panDOBVerification.model.SoaOutputResponse.class);
			long processedTime = (System.currentTimeMillis() - requestedTime);
			logger.info(" Time took to process the pandob request {} miliseconds ", processedTime);
			outputResponse = handleResponse(soaResponse,outputResponse,panDobRequest,soaInputRequest);
		}  catch (HttpServerErrorException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			MsgInfo msgInfo = new MsgInfo(PanDOBConstants.FAILURE,
					"500", PanDOBConstants.RESULT_DESCRIPTION);
			String errorResponse = e.getResponseBodyAsString();
			AuditingDetails auditingDetails = getAuditingDetails("500", "500",errorResponse,
					soaInputRequest != null ? soaInputRequest.getRequest() : null, panDobRequest);
			auditService.saveAuditTransactionDetails(auditingDetails);
			outputResponse = getErrorOutputResponse(msgInfo);
			return new ResponseEntity<>(new Gson().toJson(outputResponse), HttpStatus.OK);
		} catch (ResourceAccessException e) {
			logger.error("Timeout issue from server {}", Utility.getExceptionAsString(e));
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setMsg(PanDOBConstants.FAILURE);
			msgInfo.setMsgCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			msgInfo.setMsgDescription("TimeOut from server side");
			outputResponse = getErrorOutputResponse(msgInfo);
			return new ResponseEntity<>(new Gson().toJson(outputResponse), HttpStatus.OK);
		} catch (InvalidRequestException ex) {
			 Result result = setErrorResponse(HttpStatus.OK, ex.getMsg(), ex.getMsgDescription());
			 return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			MsgInfo msgInfo = new MsgInfo();
			msgInfo.setMsg(PanDOBConstants.FAILURE);
			msgInfo.setMsgCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			msgInfo.setMsgDescription(PanDOBConstants.INTERNAL_SERVER_ERROR);
			outputResponse = getErrorOutputResponse(msgInfo);
			logger.error("Internal Server Error {}", Utility.getExceptionAsString(e));
		}
		logger.info("response for PanDobVerification api: {}", outputResponse);
		return ResponseEntity.ok(outputResponse);

	}
	private boolean isValidSoaResponse(SoaOutputResponse soaOutResponse) {
	    return Objects.nonNull(soaOutResponse) && Objects.nonNull(soaOutResponse.getResponse());
	}

	private ClientHttpRequestFactory clientHttpRequestFactory(int timeout) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		factory.setReadTimeout(timeout);
		return factory;
	}

	private void validateInputRequest(PanDobRequest panDobRequest) {
		if (Objects.isNull(panDobRequest) || Objects.isNull(panDobRequest.getRequest())
				|| Objects.isNull(panDobRequest.getRequest().getData())) {
			throw new InvalidRequestException("Invalid Request", "Request object is invalid !!");
		}
		Data data = panDobRequest.getRequest().getData();
		if (!validateData(data)) {
			throw new InvalidRequestException("Invalid Object", "Request object is invalid !!");
		}
		String firstName = data.getFirstName();
		String panCardNumber = data.getPanCardNumber();
		if (StringUtils.isEmpty(panCardNumber) || !isPanValid(panCardNumber)) {
			throw new InvalidRequestException("Pan Number is invalid", "Invalid Pan Number.");
		}
		if (StringUtils.isEmpty(firstName) || firstName.length() <= 2) {
			throw new InvalidRequestException("First Name is not valid", "First Name should be at least 3 characters");
		}
	}

	private String convertedDate() {
		Date currentDate = new Date();
		Instant instant = currentDate.toInstant();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.withZone(ZoneId.of("UTC"));
		return formatter.format(instant);
	}

	private boolean validateData(Data data) {
		return data.getFirstName() != null && data.getPanCardNumber() != null && data.getDob() != null;
	}

	private AuditingDetails getAuditingDetails(String statusCode, String httpStatusCode,
			Object outputResponse, Object inputRequest, PanDobRequest panDobRequest) {
		AuditingDetails auditDetails = new AuditingDetails();
		auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
		ResponseObject respoObject = new ResponseObject();
		respoObject.setAdditionalProperty(AppConstants.RESPONSE, outputResponse);
		auditDetails.setResponseObject(respoObject);
		auditDetails.setServiceName(PanDOBConstants.PANDOB_SERVICE_NAME);
		auditDetails.setTransactionId(getTransactionId(panDobRequest));
		auditDetails.setAgentId(panDobRequest.getRequest().getData().getAgentId());
		auditDetails.setHttpStatusCode(httpStatusCode);
		auditDetails.setStatusCode(statusCode);
		auditDetails.setRequestId(UUID.randomUUID().toString());
		return auditDetails;
	}

	private long getTransactionId(PanDobRequest panDobRequest) {
		try {
			String transactionId = panDobRequest.getRequest().getData().getTransactionId();
			return Long.parseLong(transactionId);
		} catch (Exception e) {
			return 0;
		}
	}

	private com.mli.mpro.location.panDOBVerification.model.SoaInputRequest buildSoaPanDobRequest(
			PanDobRequest panDobRequest) {
		String soaCorrelationId = UUID.randomUUID().toString();
		com.mli.mpro.location.panDOBVerification.model.SoaInputRequest soaInputRequest = new com.mli.mpro.location.panDOBVerification.model.SoaInputRequest();
		com.mli.mpro.location.panDOBVerification.model.SoaRequest soaRequest = new com.mli.mpro.location.panDOBVerification.model.SoaRequest();
		Header header = new Header();
		com.mli.mpro.location.panDOBVerification.model.SoaPayload payload = new com.mli.mpro.location.panDOBVerification.model.SoaPayload();
		Data data = panDobRequest.getRequest().getData();
		header.setSoaAppId(SoaConstants.FULFILLMENT);
		header.setSoaCorrelationId(soaCorrelationId);
		soaRequest.setHeader(header);
		payload.setEquoteNumber("");
		payload.setLastName(StringUtils.isNotBlank(data.getLastName()) ? data.getLastName() : ".");
		payload.setOccupation("");
		payload.setEducation("");
		payload.setGender("");
		payload.setPostalCode("");
		payload.setValidationType(data.getValidationType());
		payload.setPolicyNo(new ArrayList<>());
		payload.setSumAssured("");
		payload.setStreet("");
		payload.setHouseNo("");
		payload.setState("");
		payload.setLandmark("");
		payload.setPan(data.getPanCardNumber());
		payload.setEmail("");
		payload.setLeadId("");
		payload.setPostOffice("");
		payload.setTransTrackingId("");
		payload.setDeclaredIncome("");
		payload.setVillCity("");
		payload.setMobileNo("");
		payload.setCrmUpdate("NO");
		payload.setFirstName(data.getFirstName());
		payload.setCareOf("");
		payload.setDob(data.getDob());
		payload.setDistrict("");
		payload.setMiddleName("");
		payload.setLocation("");
		payload.setStateCode("");
		payload.setSubDistrict("");
		soaRequest.setPayload(payload);
		soaInputRequest.setRequest(soaRequest);
		return soaInputRequest;
	}

	private com.mli.mpro.location.panDOBVerification.model.OutputResponse getOutputResponses(
			Header header,
			com.mli.mpro.location.panDOBVerification.model.ResponsePayload responsePayload,MsgInfo msgInfo) {
		com.mli.mpro.location.panDOBVerification.model.Result result = new com.mli.mpro.location.panDOBVerification.model.Result();
		com.mli.mpro.location.panDOBVerification.model.Response response = new com.mli.mpro.location.panDOBVerification.model.Response();

		response.setMsginfo(msgInfo);
		response.setPayload(responsePayload);
		response.setHeader(header);
		result.setResponse(response);
		com.mli.mpro.location.panDOBVerification.model.OutputResponse outputResponse = new com.mli.mpro.location.panDOBVerification.model.OutputResponse();
		outputResponse.setResult(result);

		return outputResponse;
	}

	private com.mli.mpro.location.panDOBVerification.model.OutputResponse getErrorOutputResponse(MsgInfo msgInfo) {
		com.mli.mpro.location.panDOBVerification.model.Result result = new com.mli.mpro.location.panDOBVerification.model.Result();
		com.mli.mpro.location.panDOBVerification.model.Response response = new com.mli.mpro.location.panDOBVerification.model.Response();

		response.setMsginfo(msgInfo);
		response.setPayload(new ResponsePayload());
		response.setHeader(new Header());
		result.setResponse(response);
		com.mli.mpro.location.panDOBVerification.model.OutputResponse outputResponse = new com.mli.mpro.location.panDOBVerification.model.OutputResponse();
		outputResponse.setResult(result);
		return outputResponse;
	}

	private boolean isPanValid(String panCardNumber) {
		String regex = "[A-Za-z]{5}\\d{4}[A-Za-z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(panCardNumber);
		return matcher.matches();
	}

	public com.mli.mpro.location.panDOBVerification.model.Result setErrorResponse(HttpStatus statusCode, String message, String msgDescription) {
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setMsgCode(String.valueOf(statusCode.value()));
		msgInfo.setMsg(message);
		msgInfo.setMsgDescription(msgDescription);
		com.mli.mpro.location.panDOBVerification.model.Result result = new com.mli.mpro.location.panDOBVerification.model.Result();
		com.mli.mpro.location.panDOBVerification.model.Response response = new com.mli.mpro.location.panDOBVerification.model.Response();
		response.setMsginfo(msgInfo);
		result.setResponse(response);
		return result;
	}
	
	private com.mli.mpro.location.panDOBVerification.model.OutputResponse handleResponse(ResponseEntity<com.mli.mpro.location.panDOBVerification.model.SoaOutputResponse> soaResponse,com.mli.mpro.location.panDOBVerification.model.OutputResponse outputResponse,PanDobRequest panDobRequest,SoaInputRequest soaInputRequest) throws UserHandledException {
		SoaResponsePayload payload = null;
		AuditingDetails auditingDetails = new AuditingDetails();
		if (soaResponse.getStatusCode() == HttpStatus.OK) {
			SoaOutputResponse soaOutResponse = soaResponse.getBody();
			logger.info("response received from PanDobVerification soa api: {}", soaOutResponse);
			if (isValidSoaResponse(soaOutResponse)) {
				SoaResponse response = soaOutResponse.getResponse();
				payload = response.getPayload();
				com.mli.mpro.location.panDOBVerification.model.ResponsePayload responsePayload = new com.mli.mpro.location.panDOBVerification.model.ResponsePayload();
				Header header = response.getHeader();
				MsgInfo msgInfo = response.getMsgInfo();
				auditingDetails = getAuditingDetails(String.valueOf(soaResponse.getStatusCode().value()), String.valueOf(soaResponse.getStatusCode().value()),soaResponse,soaInputRequest,
						panDobRequest);
				if (Objects.nonNull(payload)) {
					responsePayload.setDobStatus(payload.getDobStatus());
					responsePayload.setNameStatus(payload.getNameStatus());
					responsePayload.setPanAadhaarLinkStatus(payload.getPanAadhaarLinked());
					responsePayload.setPanStatus(payload.getPanStatus());
				} else {
					responsePayload.setDobStatus("");
					responsePayload.setNameStatus("");
					responsePayload.setPanAadhaarLinkStatus("");
					responsePayload.setPanStatus("");
				}
				responsePayload.setResponseDate(convertedDate());
				outputResponse = getOutputResponses(header, responsePayload, msgInfo);
			}
		} else if (soaResponse.getStatusCode().value() == 563) {
			payload = Optional.ofNullable(soaResponse).map(ResponseEntity::getBody)
					.map(com.mli.mpro.location.panDOBVerification.model.SoaOutputResponse::getResponse)
					.map(com.mli.mpro.location.panDOBVerification.model.SoaResponse::getPayload).orElse(null);
			String resultDescription = "";
			if (payload != null && StringUtils.isNotBlank(payload.getStatusDesc())) {
				resultDescription = payload.getStatusDesc();
			} else {
				resultDescription = "";
			}
			MsgInfo msgInfo = new MsgInfo(PanDOBConstants.FAILURE, String.valueOf(soaResponse.getStatusCode().value()),
					resultDescription);
			auditingDetails = getAuditingDetails("500", String.valueOf(soaResponse.getStatusCode().value()),
					soaInputRequest.getRequest(), soaResponse, panDobRequest);
			outputResponse = getErrorOutputResponse(msgInfo);
		} else {
			MsgInfo msgInfo = new MsgInfo(PanDOBConstants.FAILURE,
					String.valueOf(soaResponse.getStatusCode().value()), PanDOBConstants.RESULT_DESCRIPTION);
			auditingDetails = getAuditingDetails("500","500" ,soaInputRequest.getRequest(),
					soaResponse, panDobRequest);
			outputResponse = getErrorOutputResponse(msgInfo);
		}
		auditService.saveAuditTransactionDetails(auditingDetails);
		return outputResponse;
	}
}
