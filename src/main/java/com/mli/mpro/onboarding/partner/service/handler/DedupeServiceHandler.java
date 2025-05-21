package com.mli.mpro.onboarding.partner.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.partner.model.*;
import com.mli.mpro.onboarding.partner.service.impl.DedupeValidationServiceImpl;
import com.mli.mpro.onboarding.partner.service.impl.RetryableInvokeService;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.onboarding.util.EncryptionDecryptionTransformUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.validation.Validation;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.productRestriction.util.AppConstants.DEDUPE_EXACT_MATCH;

@Component("dedupeServiceHandler")
public class DedupeServiceHandler extends APIServiceHandler {

    private static final Logger logger = LoggerFactory.getLogger(DedupeServiceHandler.class);

    @Value("${urlDetails.lumiq-dedupe-x-api-key}")
    private String dedupeApiKey;

    @Value("${urlDetails.lumiq-dedupe-x-apigw-api-id}")
    private String dedupeApiId;

	@Value("${urlDetails.lumiq-dedupe-url}")
	private String dedupeUrl;

	@Value("${urlDetails.clientPolicyDetails}")
	private String cpdUrl;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DedupeValidationServiceImpl dedupeValidationServiceImpl;

	@Autowired
	private RetryableInvokeService retryableInvokeService;

    @Autowired
    private AuditService auditService;

	@Autowired
	SoaCloudService soaCloudService;
	@Value("${urlDetails.clientPolicyDetails.dataLakeURL}")
	private String cpdDataLakeUrl;

	@Override
	public Validation validate(String inputRequest) throws JsonProcessingException {
		// TODO Auto-generated method stub
		logger.info("Dedupe API validation called");
		return dedupeValidationServiceImpl.validateRequest(inputRequest);
	}

    @Override
    public String setValidationErrorResponse(MsgInfo msgInfo) throws JsonProcessingException {
        DedupeOutputResponse dedupeOutputResponse = new DedupeOutputResponse();
        DedupeAPIResponse dedupeAPIResponse = new DedupeAPIResponse();
        ResponseData responseData = new ResponseData();
        dedupeAPIResponse.setMsgInfo(msgInfo);
        dedupeAPIResponse.setResponseData(responseData);
        dedupeOutputResponse.setResponse(dedupeAPIResponse);
        return objectMapper.writeValueAsString(dedupeOutputResponse);
    }

    @Override
	public SOAResponse invokeService(InputRequest inputRequest) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Dedupe API invokeService called for transactionId is {}",inputRequest.getDedupeAPIPayload().getTransactionId());
		SOAResponse response = invokeDedupeAPIService(inputRequest);
		return response;
	}

    @Override
    public RequestResponse transformResponse(SOAResponse soaResponse,InputRequest inputRequest) throws JsonProcessingException, UserHandledException {

        RequestResponse requestResponse = new RequestResponse();
        DedupeOutputResponse dedupeOutputResponse = new DedupeOutputResponse();
        try {
            DedupeSOAResponsePayload dedupeSOAResponsePayload = ((DedupeSOAResponse) soaResponse).getPayload();
            DedupeResponsePayload dedupeResponsePayload = new DedupeResponsePayload();
            dedupeResponsePayload.setMatchProfiles(dedupeSOAResponsePayload.getMatchProfiles());
            dedupeResponsePayload.setDedupeFlag(dedupeSOAResponsePayload.getDedupeFlag());
            // set Dedupe Exact Match response
            setDedupeForExactMatch(dedupeResponsePayload);
            DedupeAPIResponse response = new DedupeAPIResponse();
            response.setMsgInfo(soaResponse.getMsginfo());
            ResponseData responseData = new ResponseData();
            responseData.setDedupeResponse(dedupeResponsePayload);
            response.setResponseData(responseData);
            dedupeOutputResponse.setResponse(response);
            // Call CPD if exact match case
            checkDedupeExactMatch(dedupeOutputResponse, dedupeResponsePayload,inputRequest);
            String payload = getObjectMapper().writeValueAsString(dedupeOutputResponse);
			requestResponse.setPayload(payload);
		} catch (Exception ex){
			logger.info("Getting Exception {} while transforming the dedupe response",ex.getMessage());
        }
        auditService.saveAuditTransactionDetails(getAuditDetails(inputRequest.getDedupeAPIPayload(),dedupeOutputResponse));
		logger.info("APIServiceHandler transformResponse request");

		return requestResponse;
	}

	@Override
	public RequestResponse processRsponse(RequestResponse requestResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	public SOAResponse invokeDedupeAPIService(InputRequest inputRequest) throws Exception {

        Request request = new Request();
        SOARequest soaRequest = new SOARequest();
        DedupeSOAResponse dedupeSOAResponse;

        try {
            soaRequest.setHeader(getHeaderDedupeAPI());
            soaRequest.setPayload(getPayloadForDedupeAPI(inputRequest));

            request.setRequest(soaRequest);

            logger.info("Dedupe API soa request for transactionId {} is {} ",
                    inputRequest.getDedupeAPIPayload().getTransactionId(), soaRequest);

            dedupeSOAResponse = new DedupeSOAResponse();
            String responseString = retryableInvokeService.callService(dedupeUrl, setHttpHeaders(request));
            DedupeResponse dedupeResponse = objectMapper.readValue(responseString, DedupeResponse.class);

            if (null != dedupeResponse && null != dedupeResponse.getResponse()) {
                dedupeSOAResponse.setHeader(dedupeResponse.getResponse().getHeader());
                dedupeSOAResponse.setMsginfo(dedupeResponse.getResponse().getMsginfo());
                dedupeSOAResponse.setPayload(dedupeResponse.getResponse().getPayload());
            }

            logger.info("Dedupe Soa response for transactionId {} is {} ", inputRequest.getDedupeAPIPayload().getTransactionId(), dedupeSOAResponse);
        } catch (Exception ex) {
            logger.error("Exception while calling Dedupe API and message is {} ",
                    ex.getMessage());
            throw ex;
        }
        return dedupeSOAResponse;
    }

    @Override
    public String encryptErrorResponse(Exception ex, String key) throws Exception {

		DedupeOutputResponse dedupeOutputResponse =  new DedupeOutputResponse();
		DedupeAPIResponse dedupeAPIResponse = new DedupeAPIResponse();

		if(ex instanceof UserHandledException) {
			logger.error("User Handled exception occurred for request object {}", Util.getExceptionAsString(ex));

			MsgInfo msgInfo = new MsgInfo(AppConstants.BAD_REQUEST_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);
			dedupeAPIResponse.setMsgInfo(msgInfo);
			dedupeOutputResponse.setResponse(dedupeAPIResponse);


		} else {
			MsgInfo msgInfo = new MsgInfo(AppConstants.INTERNAL_SERVER_ERROR_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);
			dedupeAPIResponse.setMsgInfo(msgInfo);
			dedupeOutputResponse.setResponse(dedupeAPIResponse);
			logger.info("Exception Response Message : {}",dedupeOutputResponse);
		}
		return EncryptionDecryptionTransformUtil.encrypt(objectMapper.writeValueAsString(dedupeOutputResponse),key);
	}

	private Header getHeaderDedupeAPI() {
		Header header = new Header();
		header.setSoaAppId("FULFILLMENT");
		header.setSoaCorrelationId(UUID.randomUUID().toString());
		return header;
	}

    private DedupeAPIPayload getPayloadForDedupeAPI(InputRequest inputRequest) {
        // Preparing the request for Soa Dedupe API
        DedupeAPIPayload dedupeAPIPayload = new DedupeAPIPayload();
        dedupeAPIPayload.setDob(inputRequest.getDedupeAPIPayload().getDob());
        dedupeAPIPayload.setName(inputRequest.getDedupeAPIPayload().getName());
        dedupeAPIPayload.setPanNo(inputRequest.getDedupeAPIPayload().getPanNo());
        dedupeAPIPayload.setGender(inputRequest.getDedupeAPIPayload().getGender());
        dedupeAPIPayload.setAddresses(inputRequest.getDedupeAPIPayload().getAddresses());
        dedupeAPIPayload.setBankDetails(inputRequest.getDedupeAPIPayload().getBankDetails());
        dedupeAPIPayload.setDrivingLicense(inputRequest.getDedupeAPIPayload().getDrivingLicense());
        dedupeAPIPayload.setFatherName(inputRequest.getDedupeAPIPayload().getFatherName());
        dedupeAPIPayload.setEmailId(inputRequest.getDedupeAPIPayload().getEmailId());
        dedupeAPIPayload.setMobileNo(inputRequest.getDedupeAPIPayload().getMobileNo());
        dedupeAPIPayload.setPassport(inputRequest.getDedupeAPIPayload().getPassport());
        dedupeAPIPayload.setPrevPolicy(inputRequest.getDedupeAPIPayload().getPrevPolicy());
        dedupeAPIPayload.setEiaNumber(inputRequest.getDedupeAPIPayload().getEiaNumber());
        dedupeAPIPayload.setCkycNumber(inputRequest.getDedupeAPIPayload().getCkycNumber());
        dedupeAPIPayload.setVoterId(inputRequest.getDedupeAPIPayload().getVoterId());
        return dedupeAPIPayload;
    }

	private HttpEntity setHttpHeaders(Request inputRequest){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(X_API_KEY,dedupeApiKey);
		httpHeaders.add(X_APIGW_API_ID,dedupeApiId);
		return new HttpEntity<>(inputRequest,httpHeaders);
	}

    private void checkDedupeExactMatch(DedupeOutputResponse dedupeOutputResponse, DedupeResponsePayload dedupeResponsePayload,InputRequest inputRequest) {
        if (null != dedupeResponsePayload && null != dedupeResponsePayload.getDedupeFlag() && DEDUPE_EXACT_MATCH.equalsIgnoreCase(dedupeResponsePayload.getDedupeFlag())
                && null != dedupeResponsePayload.getMatchProfiles() && null != dedupeResponsePayload.getMatchProfiles().get(0)
                && null != dedupeResponsePayload.getMatchProfiles().get(0).getProfile() && null != dedupeResponsePayload.getMatchProfiles().get(0).getProfile().getClientId()) {

            CpdSoaRequest cpdSoaRequest = new CpdSoaRequest();
            CpdRequest cpdRequest = new CpdRequest();
            CpdPayload cpdPayload = new CpdPayload();
            Header header = getHeaderDedupeAPI();

            cpdRequest.setHeader(header);
            cpdPayload.setClientId(dedupeResponsePayload.getMatchProfiles().get(0).getProfile().getClientId());
            cpdPayload.setPlanCode(inputRequest.getDedupeAPIPayload().getPlanCode());
            cpdRequest.setPayload(cpdPayload);
            cpdSoaRequest.setRequest(cpdRequest);

            try {
                HttpEntity<CpdSoaRequest> httpEntity = new HttpEntity<>(cpdSoaRequest);

                CpdSoaResponse cpdSoaResponse = null;
                cpdSoaResponse = getCpdSoaResponse(cpdRequest, cpdSoaResponse, httpEntity);
                if (null != cpdSoaResponse && null != cpdSoaResponse.getResponse() && null != cpdSoaResponse.getResponse().getPayload()) {
                    filterActivePolicyCpdResponse(cpdSoaResponse);
                    CpdSoaResponsePayload cpdSoaResponsePayload = cpdSoaResponse.getResponse().getPayload();
                    CpdResponsePayload cpdResponsePayload = new CpdResponsePayload();
                    cpdResponsePayload.setPolicyDetails(cpdSoaResponsePayload.getPolicyDetails());
                    cpdResponsePayload.setCurrentPlanType(cpdSoaResponsePayload.getCurrentPlanType());
                    dedupeOutputResponse.getResponse().getResponseData().setCpdResponse(cpdResponsePayload);
                }

            } catch (Exception ex) {
                logger.error("getting exception while calling CPD API {}", ex.getMessage());
            }
        }
    }

    private CpdSoaResponse getCpdSoaResponse(CpdRequest cpdRequest, CpdSoaResponse cpdSoaResponse, HttpEntity<CpdSoaRequest> httpEntity) throws Exception {
		if(FeatureFlagUtil.isFeatureFlagEnabled(CLIENT_POLICY_DETAILS_FEATURE_FLAG)){
			logger.info("Into data lake CPD call for checkDedupeExactMatch URL is {}", cpdDataLakeUrl);
			ResponseEntity<?> responseEntity = soaCloudService.callingSoaApi(cpdRequest, cpdDataLakeUrl);
			if (responseEntity != null && responseEntity.getBody() != null) {
				cpdSoaResponse = new ObjectMapper().convertValue(responseEntity.getBody(), CpdSoaResponse.class);
			}
			logger.info("CPD Response via data lake API is {}", cpdSoaResponse);
		} else {
			String responseString = retryableInvokeService.callService(cpdUrl, httpEntity);
			cpdSoaResponse = objectMapper.readValue(responseString, CpdSoaResponse.class);
			logger.info("CPD Response is {}", cpdSoaResponse);
		}
		return cpdSoaResponse;
	}
    private void setDedupeForExactMatch( DedupeResponsePayload dedupeResponsePayload) {
        if (null != dedupeResponsePayload && null != dedupeResponsePayload.getDedupeFlag() && DEDUPE_EXACT_MATCH.equalsIgnoreCase(dedupeResponsePayload.getDedupeFlag())) {
            Optional<MatchProfiles> optionalMatchProfiles = dedupeResponsePayload.getMatchProfiles().stream()
                    .filter(dedupeResPayload -> DEDUPE_EXACT_MATCH.equalsIgnoreCase(dedupeResPayload.getMatchStats().getMatchType())).findFirst();

            if (optionalMatchProfiles.isPresent()) {
                logger.info("Setting Dedupe previous policy Details for exact match");
                MatchProfiles matchProfiles = optionalMatchProfiles.get();
                Profile profile = matchProfiles.getProfile();
                List<PreviousPolicyDetails> previousPolicyDetails;
                previousPolicyDetails = profile.getPreviousPolicyDetails().stream().filter(previousPolicyDetailsList -> ACTIVE_POLICY_STATUS.equalsIgnoreCase(previousPolicyDetailsList.getPolicyStatus())).collect(Collectors.toList());
                profile.setPreviousPolicyDetails(previousPolicyDetails);
                dedupeResponsePayload.getMatchProfiles().get(0).setProfile(profile);
            }

        }
    }

    private AuditingDetails getAuditDetails(DedupeAPIPayload request, DedupeOutputResponse response) {

        AuditingDetails auditingDetails = new AuditingDetails();
        ResponseObject responseObject = new ResponseObject();
        //TODO Handle NPE when SOA is returning payload as null
        responseObject.setAdditionalProperty(RESPONSE, response);
        auditingDetails.setAdditionalProperty(REQUEST, request);
        auditingDetails.setResponseObject(responseObject);
        auditingDetails.setServiceName(DEDUPE_CPD_API);
        logger.info("Setting auditing details for dedupe cpd API is {}",auditingDetails);
        return auditingDetails;

    }

    private void filterActivePolicyCpdResponse(CpdSoaResponse cpdSoaResponse){
        try {
            CpdSoaResponsePayload payload = cpdSoaResponse.getResponse().getPayload();
            List<PolicyDetails> activePolicies = payload.getPolicyDetails().stream().filter(p -> getActivePolicyDetail(p.getBaseCoverStatusCode(), p.getUwDecision())).collect(Collectors.toList());
            cpdSoaResponse.getResponse().getPayload().setPolicyDetails(activePolicies);
        } catch (Exception ex){
            logger.info("Getting exception while filtering policy details from Cpd Response {}",ex.getMessage());
        }
    }

    // Filtering out cpd response based on below condition
    private boolean getActivePolicyDetail(String baseCoverStatusCode, String uwDecision) {
        boolean status = true;
        if (baseCoverStatusCode.equalsIgnoreCase("Y") || baseCoverStatusCode.equalsIgnoreCase("2")
                || uwDecision.equalsIgnoreCase("DECLINED") || uwDecision.equalsIgnoreCase("POSTPONED")
                || (baseCoverStatusCode.equalsIgnoreCase("R") && !uwDecision.equalsIgnoreCase("STANDARD"))
                || (baseCoverStatusCode.equalsIgnoreCase("A") && !uwDecision.equalsIgnoreCase("STANDARD"))) {
            logger.info("Filtering out Policies from Cpd Response based on above condition");
            status = false;
        }
        return status;
    }

}
