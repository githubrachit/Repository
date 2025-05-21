package com.mli.mpro.tmb.service.impl;


import com.mli.mpro.tmb.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Component
public class OauthTokenTMBUtility {

    private static final Logger log = LoggerFactory.getLogger(OauthTokenTMBUtility.class);
    @Value("${tmb.oauth.client.id}")
    private String clientId;

    @Value("${tmb.oauth.client.secret}")
    private String clientSecret;

    private String scope;
    @Autowired
    RestTemplate restTemplate ;


    public String getAccessToken(String scope, String tokenUrl , String serviceName) {
        try {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        log.info("Request to TMB Token API for service {} {}", serviceName, requestEntity);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );
        log.info("For service {} Response from TMB Token API {}", serviceName, response);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            String accessToken = (String) response.getBody().get("access_token");
            if (accessToken != null) {
                log.info("Successfully retrieved access token for service: {}", serviceName);
                return accessToken;
            } else {
                log.warn("Access token not found in response for service: {}", serviceName);
            }
        } else {
            log.warn("Unsuccessful response from TMB Token API for service: {}. Status code: {}", serviceName, response.getStatusCode());
        }
        return null;
    } catch (Exception e) {
            log.error("Error while getting access token from TMB {} service {} ", Utility.getExceptionAsString(e) , serviceName);
            return null;
        }
    }
}
