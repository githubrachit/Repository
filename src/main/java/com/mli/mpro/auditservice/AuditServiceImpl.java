package com.mli.mpro.auditservice;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import com.mli.mpro.auditservice.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.utils.Utility;

@Service
@EnableAsync
public class AuditServiceImpl implements AuditService {

	@Value("${urlDetails.auditingService}")
	private URI auditUrl;


	//TODO - Update parameter store Keys
	@Value("${urlDetails.auditingServiceGetValues}")
	String auditDbUrl;

	private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
	
	@Async
	@Override
	public void saveAuditTransactionDetails(AuditingDetails auditDetails)
			throws UserHandledException {
		log.info("Calling Audit service to save request and response received by the caller for service {}",auditDetails.getServiceName());
		InputRequest inputRequest = setDataForAudit(auditDetails);
		callAuditing(inputRequest);
	}

	private InputRequest setDataForAudit(AuditingDetails serviceTransactionDetails) {
		InputRequest inputRequest = null;
		String agentId = "";
		try {
			serviceTransactionDetails.setCreatedTime(new Date());
			serviceTransactionDetails.setHttpStatusCode("200");
			serviceTransactionDetails.setRequestId(UUID.randomUUID().toString());
			if(serviceTransactionDetails.getAgentId() !=null) {
				serviceTransactionDetails.setAgentId(serviceTransactionDetails.getAgentId());	
			}else {
				serviceTransactionDetails.setAgentId(agentId);
			}
			RequestPayload requestPayload = new RequestPayload();
			requestPayload.setServiceTransactionDetails(serviceTransactionDetails);
			Metadata metadata = new Metadata("", UUID.randomUUID().toString());
			RequestData requestData = new RequestData(requestPayload);
			Request request = new Request(metadata, requestData);
			inputRequest = new InputRequest(request);
		} catch (Exception ex) {
			log.info("Error occured while creating request for Auditing {}",Utility.getExceptionAsString(ex));

		}
		return inputRequest;
	}

	private void callAuditing(InputRequest inputRequest) {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<InputRequest> request = new HttpEntity<>(inputRequest,httpHeaders);
			OutputResponse response = new RestTemplate().postForObject(auditUrl, request, OutputResponse.class);
			log.info("The response received from audit service {}", response.getResponse().getResponseData().getResponsePayload().getMessage());
		} catch (Exception ex) {
			log.info("Auditing service failed to save the data {}",Utility.getExceptionAsString(ex));
		}
	}

	@Async
	@Override
	public void saveAuditTransactionDetailsForAgentSelf(AuditingDetails auditDetails, String requestId)
			throws UserHandledException {
		log.info("Calling Audit service to save request and response received by the caller for service {}",auditDetails.getServiceName());
		InputRequest inputRequest = setDataForAgentSelfAudit(auditDetails,requestId);
		callAuditing(inputRequest);
	}
	private InputRequest setDataForAgentSelfAudit(AuditingDetails serviceTransactionDetails, String requestId) {
		InputRequest inputRequest = null;
		String agentId = "";
		try {
			serviceTransactionDetails.setCreatedTime(new Date());
			serviceTransactionDetails.setHttpStatusCode("200");
			serviceTransactionDetails.setAgentId(agentId);
			if (null != requestId) {
				serviceTransactionDetails.setRequestId(String.valueOf(requestId));
			} else {
				serviceTransactionDetails.setRequestId(UUID.randomUUID().toString());
			}
			RequestPayload requestPayload = new RequestPayload();
			requestPayload.setServiceTransactionDetails(serviceTransactionDetails);
			Metadata metadata = new Metadata("", UUID.randomUUID().toString());
			RequestData requestData = new RequestData(requestPayload);
			Request request = new Request(metadata, requestData);
			inputRequest = new InputRequest(request);
		} catch (Exception ex) {
			log.info("Error occured while creating request for Auditing {}",Utility.getExceptionAsString(ex));

		}
		return inputRequest;
	}
	@Override
	public AuditingDetails getAuditDetails(String auditId, String serviceName, String requestId) {

		ResponseEntity<AuditingDetails> response = null;
		AuditingDetails body = new AuditingDetails();
		RestTemplate restTemplate = new RestTemplate();
		try {
			StringBuilder urlbuilder = new StringBuilder(auditDbUrl);
			urlbuilder.append("?auditId=").append(auditId);
			urlbuilder.append("&serviceName=").append(serviceName);
			urlbuilder.append("&requestId=").append(requestId);
			String auditDbUrlString = urlbuilder.toString();

			response = restTemplate.exchange(auditDbUrlString, HttpMethod.GET, null, AuditingDetails.class);
			log.info("Response received from audit Service {}", response);
			body = response.getBody();
		} catch (Exception e) {
			log.info("Error while connecting to audit service {}", e.getMessage());
		}
		return body;
	}
	@Override
	public void logAuditDetails(long transactionId, Object request, Object response, String serviceName, String agentId) throws UserHandledException {
		AuditingDetails auditDetails = new AuditingDetails();
		auditDetails.setAdditionalProperty("request", request);
		ResponseObject respoObject = new ResponseObject();
		respoObject.setAdditionalProperty("response", response);
		auditDetails.setResponseObject(respoObject);
		auditDetails.setAgentId(agentId);
		auditDetails.setServiceName(serviceName);
		auditDetails.setTransactionId(transactionId);
		saveAuditTransactionDetails(auditDetails);
	}
}
