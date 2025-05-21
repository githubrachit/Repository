package com.mli.mpro.tmb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.tmb.model.OnboardingState;
import com.mli.mpro.tmb.model.RequestBodyForTmb;
import com.mli.mpro.tmb.model.ResponseBodyFromTmb;
import com.mli.mpro.tmb.utility.JWEEncryptionDecryptionUtil;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Component
public class TmbApiCallingUtility {
    @Value("${tmb.oauth.client.id}")
    private String clientId;
    @Value("${tmb.oauth.client.secret}")
    private String clientSecret;
    RestTemplate restTemplate;

    @Autowired
    private AuditService auditService;

    @Autowired
    public TmbApiCallingUtility(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    JWEEncryptionDecryptionUtil jweEncryptionDecryptionUtil;
    ObjectMapper objectMapper= new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    private static final Logger log = LoggerFactory.getLogger(TmbApiCallingUtility.class);

    public String callTmbApi(String apiUrl, String authToken, String requestString, OnboardingState onboardingState,String serviceName) throws UserHandledException, JsonProcessingException {
        ResponseEntity<String> response = null;
        String decryptedResponse = null;
        long requestedTime = System.currentTimeMillis();
        try {
            log.info("Decrypted request Calling TBM API with url {} and request body {}",apiUrl,requestString);
            String encryptedString= jweEncryptionDecryptionUtil.rsaEncrypt(requestString);
            log.info("Encrypted request Calling TBM API with url {} and encrypted request body {}",apiUrl,encryptedString);
            HttpHeaders headers = new HttpHeaders();
            headers.set("TMB-Client-Id", clientId);
            headers.set("TMB-Client-Secret", clientSecret);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            RequestBodyForTmb requestBodyForTmb= new RequestBodyForTmb();
            requestBodyForTmb.setRequest(encryptedString);
            HttpEntity<RequestBodyForTmb> entity = new HttpEntity<>(requestBodyForTmb, headers);
            log.info("Request sending to TMB api {} sericeName {}", objectMapper.writeValueAsString(entity) , serviceName);
            response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            log.info("Response we recieve from TMB api {} sericeName {} ", objectMapper.writeValueAsString(response) , serviceName);
            if (Objects.nonNull(response.getBody())) {
                ResponseBodyFromTmb responseBodyFromTmb = objectMapper.readValue(response.getBody(), ResponseBodyFromTmb.class);
                if (Objects.nonNull(responseBodyFromTmb.getResponse())) {
                    decryptedResponse = jweEncryptionDecryptionUtil.rsaDecrypt(responseBodyFromTmb.getResponse());
                    log.info("Decrypted response for URL {} we receive from tmb request body{} serviceName {} ", apiUrl, decryptedResponse, serviceName);
                    return decryptedResponse;
                } else {
                    log.info("Some issue occured for URL {}", apiUrl);
                }
            }
      } catch (Exception e) {
            log.info("error occured while hitting API with message :{}", Utility.getExceptionAsString(e));
        }  finally {
            log.info("ServiceName {} respone time {} sec took to process the request", serviceName,com.mli.mpro.tmb.utility.Utility.getProcessedTime(requestedTime));
            AuditingDetails auditDetails = new AuditingDetails();
            auditDetails.setAdditionalProperty("request", requestString);
            ResponseObject respoObject = new ResponseObject();
            respoObject.setAdditionalProperty("response", decryptedResponse);
            auditDetails.setResponseObject(respoObject);
            auditDetails.setAgentId(onboardingState.getAgentId());
            auditDetails.setServiceName(AppConstants.REQUEST_SOURCE_TMB+"_"+serviceName);
            auditDetails.setTransactionId(onboardingState.getTransactionId());
            auditService.saveAuditTransactionDetails(auditDetails);
        }

        return null;
    }
}
