package com.mli.mpro.onboarding.partner.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.controller.LocationController;
import com.mli.mpro.location.models.mfaOauthAgent360.ErrorResponse;
import com.mli.mpro.location.models.mfaOauthAgent360.MFAAgent360InputRequest;
import com.mli.mpro.location.models.mfaOauthAgent360.MFAResponse;
import com.mli.mpro.location.models.mfaOauthAgent360.OauthBasedAgent360Response;
import com.mli.mpro.location.services.impl.OauthBasedAgent360ServiceImpl;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.util.EncodDecodUtil;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

import static com.mli.mpro.productRestriction.util.AppConstants.EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API;

@Service
public class GatewayAgent360Handler{
    private Logger logger = LoggerFactory.getLogger(GatewayAgent360Handler.class);
    @Autowired
    LocationController oauthServiceController;

    @Autowired
    private EncodDecodUtil encodDecodUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OauthBasedAgent360ServiceImpl oauthBasedAgent360Service;

    @Autowired
    private AuditService auditService;

    public RequestResponse handelRequest(RequestResponse requestResponse, MultiValueMap<String, String> headers) throws Exception{
        RequestResponse response = new RequestResponse();
        String key = encodDecodUtil.getEncDecryptKey(headers);
        OauthBasedAgent360Response agent360response = new OauthBasedAgent360Response();
        MFAAgent360InputRequest request = new MFAAgent360InputRequest();
        try {
            logger.info("Entered agent360 handler");
            String decryptedString = decrypt(requestResponse, headers);
            request = deserialize(decryptedString);
            logger.info("Decrypted string received {}", request);

            agent360response = getAgent360Records(request, AppConstants.NO);
            logger.info("Response received from agent 360 service {}", agent360response);

            if (null != agent360response.getResponse() && null != agent360response.getResponse().getResponseData()) {
                String jwTToken = oauthBasedAgent360Service.generateJwtToken(AppConstants.PB_SOURCE, request.getRequest().getPayload().getAgentId());
                agent360response.getResponse().getResponseData().setToken(jwTToken);
                logger.info("jwt token generated is {}",jwTToken);
            }
            // Encrypt and transform the agent 360 response
            response = transformAndEncryptAgent360Response(agent360response, headers,key);
            return response;
        } catch (Exception ex){
            logger.error("Getting exception {} while handling request for PB agent 360",ex.getMessage());
            throw ex;
        } finally {
            auditService.saveAuditTransactionDetails(getAuditDetails(request,agent360response,AppConstants.POLICY_BAZAAR,AppConstants.PB_SOURCE));
        }
    }

    public MFAAgent360InputRequest deserialize(String decryptedString) throws Exception {
        MFAAgent360InputRequest inputRequest = null;
        try {
            inputRequest = objectMapper.readValue(decryptedString, MFAAgent360InputRequest.class);
            return inputRequest;
        } catch (JsonMappingException e) {
            logger.error("Error while mapping the string : {} to InputRequest {}", decryptedString,e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            logger.error("Error while processing the string : {} to InputRequest {}", decryptedString,e.getMessage());
            throw e;
        }
    }

    // Transforming the agent360 response and convert into request response format
    private RequestResponse transformAndEncryptAgent360Response(OauthBasedAgent360Response agent360Response, MultiValueMap<String, String> headers,String key) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        RequestResponse requestResponse = new RequestResponse();
        String payload = getObjectMapper().writeValueAsString(agent360Response);
        requestResponse.setPayload(payload);
        // Encrypt the agent360 response
        encryptResponse(requestResponse,key);
        return requestResponse;
    }

    private String decrypt(RequestResponse inputRequest, MultiValueMap<String, String> headers) throws GeneralSecurityException {
        String key = encodDecodUtil.getEncDecryptKey(headers);
        String decryptedString = null;

        logger.debug("Encryption key is {} ", key);
        if(null != key) {
            decryptedString = AESEncryptDecryptUtil.decrypt(inputRequest.getPayload(), key) ;
        } else {
            logger.error("Encryption decryption key is null ");
        }

        logger.info("APIServiceHandler Decrypt request is {} ", decryptedString);
        return decryptedString;
    }

    private String encryptResponse(RequestResponse requestResponse,String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        logger.info("APIServiceHandler encryptResponse request");
        String encryptedResponse = AESEncryptDecryptUtil.encrypt(requestResponse.getPayload(), key);
        logger.info("Response to be encrypted is : {}", requestResponse.getPayload());
        requestResponse.setPayload(encryptedResponse);
        return requestResponse.getPayload();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public String encryptErrorResponse(Exception ex, String key) throws Exception{

        OauthBasedAgent360Response oauthBasedAgent360Response =  new OauthBasedAgent360Response();
        MFAResponse mfaResponse = new MFAResponse();
        ErrorResponse errorResponse = new ErrorResponse();
        List<String> errorMessages = new ArrayList<>();

        if(ex instanceof UserHandledException) {
            logger.error("User Handled exception occurred for request object {}", Util.getExceptionAsString(ex));
            errorResponse.setErrorCode(Integer.parseInt(AppConstants.BAD_REQUEST_CODE));
            errorMessages.add(AppConstants.FAIL_STATUS);
            errorResponse.setErrorMessages(errorMessages);
            errorResponse.setErrorDescription(AppConstants.RESPONSE_FAILURE);
        } else {
            errorResponse.setErrorCode(Integer.parseInt(AppConstants.INTERNAL_SERVER_ERROR_CODE));
            errorMessages.add(AppConstants.FAIL_STATUS);
            errorResponse.setErrorMessages(errorMessages);
            errorResponse.setErrorDescription(AppConstants.RESPONSE_FAILURE);
            logger.error("Exception Response Message : {}",Util.getExceptionAsString(ex));
        }
        // set error response for PB Bazaar agent 360
        mfaResponse.setErrorResponse(errorResponse);
        oauthBasedAgent360Response.setResponse(mfaResponse);
        logger.info("Setting error response for PB agent 360 {}",oauthBasedAgent360Response);
        return AESEncryptDecryptUtil.encrypt(objectMapper.writeValueAsString(oauthBasedAgent360Response),key);
    }

    public OauthBasedAgent360Response getAgent360Records(MFAAgent360InputRequest agentRequest, String tokenRequired) throws GeneralSecurityException, JsonProcessingException {
        logger.info("Api request for Oauth token agent 360 triggered.");
        Boolean hasOuathEnabled = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEOAUTHBROKERAGENT360_V3);
        try {
            if (hasOuathEnabled) {
                return oauthBasedAgent360Service.oauthBasedAgent360CallV3(agentRequest.getRequest().getPayload(),
                        oauthBasedAgent360Service.getNewOauthToken(), tokenRequired);
            } else {
                return oauthBasedAgent360Service.oauthBasedAgent360Call(agentRequest.getRequest().getPayload(),
                        oauthBasedAgent360Service.getOauthToken(), tokenRequired);
            }
        } catch (UserHandledException ex) {
            logger.error(EXCEPTION_DURING_OAUTH_TOKEN_AGENT_API, ex);
            return oauthBasedAgent360Service.setOauthBasedAgentResponse(null, ex);
        }
    }

    private AuditingDetails getAuditDetails(MFAAgent360InputRequest request, OauthBasedAgent360Response response, String serviceName, String channelSource) {

        AuditingDetails auditingDetails = new AuditingDetails();
        ResponseObject responseObject = new ResponseObject();
        responseObject.setAdditionalProperty("response", response);
        auditingDetails.setAdditionalProperty("request", request);
        responseObject.setAdditionalProperty("channelSource", channelSource);
        auditingDetails.setResponseObject(responseObject);
        auditingDetails.setServiceName(serviceName);
        if (null != request.getRequest() && null != request.getRequest().getPayload()) {
            auditingDetails.setAgentId(request.getRequest().getPayload().getAgentId());
        }
        logger.info("Setting auditing details for PB agent 360 is {}", auditingDetails);
        return auditingDetails;
    }
}
