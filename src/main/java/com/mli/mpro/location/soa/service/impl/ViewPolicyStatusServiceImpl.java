package com.mli.mpro.location.soa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.constants.ViewPolicyConstants;
import com.mli.mpro.location.soa.service.ViewPolicyStatusService;
import com.mli.mpro.location.viewPolicy.models.OutPutResponse;
import com.mli.mpro.location.viewPolicy.models.SoaInputPayload;
import com.mli.mpro.location.viewPolicy.models.SoaRequest;
import com.mli.mpro.location.viewPolicy.models.ViewPolicyInputRequest;
import com.mli.mpro.location.viewPolicy.models.ViewPolicySoaInputRequest;
import com.mli.mpro.onboarding.model.Header;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.model.SOAResponse;

@Service
public class ViewPolicyStatusServiceImpl implements ViewPolicyStatusService {

	private static final Logger logger = LoggerFactory.getLogger(ViewPolicyStatusServiceImpl.class);

	@Value("${urlDetails.oneviewPolicyStatusURL}")
	private String url;

	@Value("${urlDetails.oneviewstatusxapigwapiid}")
	private String xapigwapiid;

	@Value("${urlDetails.oneviewstatusxapikey}")
	private String xapikey;

	@Override
	public OutPutResponse executeViewPolicyStatusService(ViewPolicyInputRequest viewPolicyInputRequest) {
		OutPutResponse outputResponse = new OutPutResponse();
		try {
			logger.info("inside OneviewPolicyStatus api");
			logger.info("request receievd for OneviewPolicyStatus api: {}", viewPolicyInputRequest);
			ViewPolicySoaInputRequest inputRequest = buildViewPolicySoaInputRequest(viewPolicyInputRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.add(SoaConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			headers.add(SoaConstants.X_APIGW_API_ID, xapigwapiid);
			headers.add(SoaConstants.X_API_KEY, xapikey);
			HttpEntity<Object> request = new HttpEntity<>(inputRequest, headers);
			ResponseEntity<SOAResponse> response = new RestTemplate().postForEntity(url, request, SOAResponse.class);
			if (response.hasBody()) {
				logger.info("SOA response for OneviewPolicy api:{}", response.getBody());
				if (response.getStatusCode() == HttpStatus.OK) {
					outputResponse.setResult(response.getBody());
				} else {
					MsgInfo msgInfo = new MsgInfo("500", ViewPolicyConstants.SOMETHHING_WRONG,
							ViewPolicyConstants.SOMETHHING_WRONG);
					return getOutputResponse(msgInfo);
				}
			} else {
				MsgInfo msgInfo = new MsgInfo("444", ViewPolicyConstants.NO_RESPONSE, ViewPolicyConstants.NO_RESPONSE);
				return getOutputResponse(msgInfo);
			}
		} catch (HttpServerErrorException ex) {
			logger.error("Exception occured while processing viewpolicy api: {} with {}", ex.getMessage(),
					ex.getStatusCode());
			MsgInfo msgInfo = new MsgInfo(String.valueOf(ex.getStatusCode().value()),
					ViewPolicyConstants.SOMETHHING_WRONG, ViewPolicyConstants.SOMETHHING_WRONG);
			return getOutputResponse(msgInfo);
		} catch (Exception ex) {
			MsgInfo msgInfo = new MsgInfo("500", ViewPolicyConstants.SOMETHHING_WRONG,
					ViewPolicyConstants.SOMETHHING_WRONG);
			logger.error("Exception occured while processing viewpolicy api: {}", ex.getMessage());
			return getOutputResponse(msgInfo);
		}
		return outputResponse;
	}

	private ViewPolicySoaInputRequest buildViewPolicySoaInputRequest(ViewPolicyInputRequest viewPolicyInputRequest) {
		ViewPolicySoaInputRequest soaInputRequest = new ViewPolicySoaInputRequest();
		SoaRequest soaRequest = new SoaRequest();
		List<String> policyList = new ArrayList<>();
		policyList.add(viewPolicyInputRequest.getRequest().getData().getPolicyNumber());
		Header header = new Header();
		header.setCorrelationId(UUID.randomUUID().toString());
		header.setAppId(SoaConstants.MPRO);
		SoaInputPayload payload = new SoaInputPayload();
		payload.setPolicyNumber(policyList);
		payload.setInfoType("1");
		payload.setSystemType("1");
		soaRequest.setHeader(header);
		soaRequest.setPayload(payload);
		soaInputRequest.setRequest(soaRequest);
		return soaInputRequest;
	}

	public OutPutResponse getOutputResponse(MsgInfo msgInfo) {
		OutPutResponse outputResponse = new OutPutResponse();
		SOAResponse soaResponse = new SOAResponse();
		com.mli.mpro.onboarding.model.Response response = new com.mli.mpro.onboarding.model.Response();
		response.setMsginfo(msgInfo);
		soaResponse.setResponse(response);
		outputResponse.setResult(soaResponse);
		return outputResponse;

	}

}
