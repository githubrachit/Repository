package com.mli.mpro.onboarding.controller;

import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.onboarding.service.SuperAppService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.onboarding.partner.service.APIService;
import com.mli.mpro.onboarding.partner.service.handler.GatewayAgent360Handler;
import com.mli.mpro.onboarding.partner.util.EncodDecodUtil;
import com.mli.mpro.onboarding.service.SuperAppService;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.service.OnboardingService;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;

import com.mli.mpro.utils.Utility;

@RestController
@RequestMapping(path = "/locationservices/onboarding")
public class OnboardingController {
    private static final Logger logger = LoggerFactory.getLogger(OnboardingController.class);
    private static final String ERR_OCCURED = "Error occurred for request object {}";
    @Autowired
    private OnboardingService onboardingService;

    @Autowired
    private ErrorMessageConfig errorMessageConfig;

    @Autowired
    private APIService apiService;

    @Autowired
    private GatewayAgent360Handler gatewayAgent360Handler;

    @Autowired
    private EncodDecodUtil encodDecodUtil;

    @Autowired
    SuperAppService superAppService;

    @PostMapping(path = "/policy-status")
    public RequestResponse viewPolicyStatus(@RequestBody RequestResponse inputRequest,@RequestHeader MultiValueMap<String, String> headerMap) throws UserHandledException, JsonProcessingException {
        RequestResponse responsePayload = null;
        ErrorResponseParams errorResponseParams=new ErrorResponseParams();
        long requestedTime = System.currentTimeMillis();
        try {
            responsePayload = onboardingService.viewPolicyStatus(inputRequest,headerMap,errorResponseParams);

        }catch(Exception e) {
            logger.error(ERR_OCCURED, Utility.getExceptionAsString(e));
            return Util.errorResponse(HttpStatus.BAD_REQUEST, Collections.singletonList(errorMessageConfig.getErrorMessages().get(AppConstants.BAD_REQUEST_TEXT)),errorResponseParams.getEncryptionSource(),errorResponseParams.getIVandKey());
        } finally {
            logger.info("Time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responsePayload;

    }

    @PostMapping(path = "/replacementSale")
    public RequestResponse replacementSale(@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap) {
        if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_REPLACEMENTSALE_EXPOSED))) {
            logger.info("ReplacementSale API is called.");
            return apiService.handleReplacementSale(inputRequest, headerMap);
        } else {
            logger.info("Feature flag for Replacement Flag is Disabled.");
            return apiService.handleFeatureFlagDisable(headerMap);
        }

    }

    @PostMapping(path = "/dedupe")
    public RequestResponse getDedupeDetails(@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap){
        if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_DEDUPE_API_EXPOSED))) {
            return apiService.handleDedupeAPI(inputRequest, headerMap);
        } else{
            logger.info("Feature flag for Dedupe API is Disabled.");
            return apiService.handleFeatureFlagDisable(headerMap);
        }
    }
    @PostMapping(path = "agent-login/v2")
    public RequestResponse agent360Auth(@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap) throws Exception {
        RequestResponse requestResponse = new RequestResponse();
        try {
            if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled("enableGateway360"))) {
                logger.info("Agent 360 request received");
                requestResponse = apiService.handleGatewayAgent360(inputRequest, headerMap);
            } else {
                logger.info("Feature flag for Gateway agent 360 login is Disabled.");
                requestResponse = apiService.handlePBFeatureFlagDisable(headerMap);
            }
        } catch(Exception ex){
            logger.error("Getting exception {} while calling Agent 360 Auth for Policy Bazaar",ex.getMessage());
        }
        return requestResponse;
    }


    @PostMapping(path = "/proposalNumberGeneration")
    public RequestResponse getProposalNumber (@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap){
        if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_PROPOSAL_NUMBER_API))) {
            logger.info("get proposal number API is called.");
            ErrorResponseParams errorResponseParams=new ErrorResponseParams();
            return apiService.handleGetProposalNumber(inputRequest, headerMap, errorResponseParams);
        }
        else{
            logger.info("Feature flag for proposal number API is disabled.");
            return apiService.handleFeatureFlagDisable(headerMap);
        }
    }

    @PostMapping(path = "/document/list")
    public RequestResponse getDocumentsList(@RequestBody RequestResponse inputRequest) throws UserHandledException, JsonProcessingException {
        RequestResponse responsePayload = null;
        long requestedTime = System.currentTimeMillis();
        try {
            responsePayload = onboardingService.getDocumentsList(inputRequest);

        } catch (Exception e) {
            logger.error(ERR_OCCURED, Utility.getExceptionAsString(e));
            return Util.errorResponse(HttpStatus.BAD_REQUEST, Collections.singletonList(errorMessageConfig.getErrorMessages().get(AppConstants.BAD_REQUEST_TEXT)),null,null);
        } finally {
            logger.info("Time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responsePayload;
    }

    @PostMapping(path = "/document/illustration-pdf")
    public RequestResponse getIllustartionPdf(@RequestBody RequestResponse inputRequest,@RequestHeader MultiValueMap<String, String> headerMap) throws UserHandledException, JsonProcessingException {
        RequestResponse responsePayload = null;
        ErrorResponseParams errorResponseParams=new ErrorResponseParams();
        long requestedTime = System.currentTimeMillis();
        try {
            responsePayload = onboardingService.getIllustrationPdf(inputRequest,headerMap, errorResponseParams);
            logger.info("Length of response : {} ",responsePayload.getPayload().length());
        } catch (Exception e) {
            logger.error(ERR_OCCURED, Utility.getExceptionAsString(e));
            return Util.errorResponse(HttpStatus.BAD_REQUEST, Collections.singletonList(errorMessageConfig.getErrorMessages().get(AppConstants.BAD_REQUEST_TEXT)),null,null);
        } finally {
            logger.info("It took Time {} sec to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responsePayload;
    }

    @PostMapping(value = "/displayCount")
    public RequestResponse displayCount(@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String,String> authToken) throws Exception{

        if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SUPERAPP_FEATURE_FLAG))) {
            logger.info("Request received for dashboard count API");
            return superAppService.fetchDashboardCount(inputRequest, authToken);
        }else{
            return superAppService.sendErrorResponse(AppConstants.SERVICE_UNAVAILABLE_CODE,AppConstants.SERVICE_UNAVAILABLE,AppConstants.SERVICE_UNAVAILABLE_DESC,authToken);
        }
    }

    @PostMapping(value="/mpro/redirect")
    public RequestResponse redirectNewApplication(@RequestBody RequestResponse inputRequest,@RequestHeader(value="x-api-token") MultiValueMap<String,String> authToken){
        if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled("enableSuperAppIntegration"))) {
            logger.info("Request to redirect to new application received");
            RequestResponse response = new RequestResponse();
            response = superAppService.redirectNewApplication(inputRequest,authToken);
            return response;
        }else{
            return superAppService.sendErrorResponse("503","Service Unavailable","Feature flag for the service is switched off",authToken);
        }
    }

    @PostMapping(path = "/validate-product/dedupe")
    public com.mli.mpro.productRestriction.models.RequestResponse getReunion(@RequestBody com.mli.mpro.productRestriction.models.RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap){
        com.mli.mpro.productRestriction.models.RequestResponse requestResponse;
        long requestedTime = System.currentTimeMillis();
        requestResponse=onboardingService.getReunion(inputRequest,headerMap);
        logger.info("Request took Time {} sec to process the request", Utility.getProcessedTime(requestedTime));
        return requestResponse;

    }

    @PostMapping(path = "/getMedicalDetails")
    public RequestResponse getMedicalGridDetails(@RequestBody RequestResponse inputRequest) throws UserHandledException, JsonProcessingException {
        RequestResponse responsePayload = null;
        long requestedTime = System.currentTimeMillis();
        try {
            responsePayload = onboardingService.getMedicalgridDetails(inputRequest);

        } catch (Exception e) {
            logger.error(ERR_OCCURED, Utility.getExceptionAsString(e));
            return Util.errorResponse(HttpStatus.BAD_REQUEST, Collections.singletonList(errorMessageConfig.getErrorMessages().get(AppConstants.BAD_REQUEST_TEXT)),null,null);
        } finally {
            logger.info("Time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responsePayload;
    }



}
