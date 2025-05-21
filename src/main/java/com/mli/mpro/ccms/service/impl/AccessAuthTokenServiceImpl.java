package com.mli.mpro.ccms.service.impl;

import com.mli.mpro.ccms.model.AccessAuthTokenResponse;
import com.mli.mpro.ccms.service.AccessAuthTokenService;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AccessAuthTokenServiceImpl implements AccessAuthTokenService {

    @Value("${urlDetails.authCcmsTokenUrl}")
    private String accessAuthTokenUrl;

    @Value("${urlDetails.authCcmsToken.scope}")
    private String scope;

    @Value("${urlDetails.authCcmsToken.username}")
    private String userName;

    @Value("${urlDetails.authCcmsToken.password}")
    private String password;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AccessAuthTokenServiceImpl.class);

    @Override
    public AccessAuthTokenResponse getAuthToken() throws UserHandledException {
        AccessAuthTokenResponse accessAuthTokenResponse;
        try {
            logger.info("CCMS auth token url is being called {}", accessAuthTokenUrl);
            accessAuthTokenResponse = restTemplate.postForObject(accessAuthTokenUrl, getHttpEntityAuthTokenAPI(),
                    AccessAuthTokenResponse.class);
            logger.info("CCMS auth token is being called for basis auth reponse{}", accessAuthTokenResponse);
        } catch (Exception ex) {
            logger.error("CCMS Exception auth token Api and message is {} ", Utility.getExceptionAsString(ex));
            throw new UserHandledException();
        }
        return accessAuthTokenResponse;
    }

    private HttpEntity<?> getHttpEntityAuthTokenAPI() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(AppConstants.AUTH_GRANT_TYPE, AppConstants.AUTH_GRANT_TYPE_VALUE);
        requestBody.add(AppConstants.AUTH_SCOPE, scope);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(userName,password);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(requestBody,headers);
    }
}
