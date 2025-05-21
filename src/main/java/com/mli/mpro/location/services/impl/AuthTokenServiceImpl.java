package com.mli.mpro.location.services.impl;

import com.mli.mpro.agentSelfIdentifiedSkip.Header;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.soaCloudModels.AuthPayload;
import com.mli.mpro.location.models.soaCloudModels.AuthRequest;
import com.mli.mpro.location.models.soaCloudModels.SoaAuthRequest;
import com.mli.mpro.location.models.soaCloudModels.SoaAuthResponse;
import com.mli.mpro.location.services.AuthTokenService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {
    @Value("${soa.authToken.clientId}")
    private String clientId;


    @Value("${soa.authToken.clientSecret}")
    private String clientSecret;
    @Value("${soa.authToken.url}")
    private String tokenUrl;
    @Value("${soa.x-apigw-api-id}")
    private String xApigwApiId;
    @Value("${soa.x-api-key}")
    private String xApiKey;

    @Value("${soa.authToke.private.clientID}")
    private String privateClientId;

    @Value("${soa.authToke.private.clientSecret}")
    private String privateClientSecret;
    @Value("${soa.private.x-apigw-api-id}")
    private String privateXApigwApiId;
    @Value("${soa.private.x-api-key}")
    private String privateXApiKey;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenServiceImpl.class);

    /**
     * Below method has implementation of generating token
     * @return AuthTokenResponse
     * @throws UserHandledException
     */
    @Override
    public SoaAuthResponse getAuthToken(boolean isPrivateCall) throws UserHandledException {
        SoaAuthResponse authTokenResponse;
        try {
            logger.info("Auth token generation and token url {}",tokenUrl);
            authTokenResponse = new RestTemplate().postForObject(tokenUrl, isPrivateCall ? getHttpEntityPrivateAuthTokenAPI() : getHttpEntityAuthTokenAPI(),
                    SoaAuthResponse.class);
            logger.info("Auth token is being called with response{}", Utility.printJsonRequest(authTokenResponse));
        } catch (Exception ex) {
            logger.error("Exception auth token Api and message is {} ", Utility.getExceptionAsString(ex));
            throw new UserHandledException();
        }
        return authTokenResponse;
    }

    private HttpEntity<?> getHttpEntityPrivateAuthTokenAPI() {
        AuthPayload authPayload = new AuthPayload();
        authPayload.setClientId(privateClientId);
        authPayload.setClientSecret(privateClientSecret);

        Header header = new Header();
        header.setAppId(AppConstants.FULFILLMENT);
        header.setCorrelationId(UUID.randomUUID().toString());

        AuthRequest authRequest = new AuthRequest();
        authRequest.setHeader(header);
        authRequest.setPayload(authPayload);

        SoaAuthRequest soaAuthRequest = new SoaAuthRequest();
        soaAuthRequest.setRequest(authRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AppConstants.X_APIGW_API_ID, privateXApigwApiId);
        headers.set(AppConstants.X_API_KEY, privateXApiKey);
        return new HttpEntity<>(soaAuthRequest,headers);
    }

    private HttpEntity<?> getHttpEntityAuthTokenAPI() {
        AuthPayload authPayload = new AuthPayload();
        authPayload.setClientId(clientId);
        authPayload.setClientSecret(clientSecret);

        Header header = new Header();
        header.setAppId(AppConstants.FULFILLMENT);
        header.setCorrelationId(UUID.randomUUID().toString());

        AuthRequest authRequest = new AuthRequest();
        authRequest.setHeader(header);
        authRequest.setPayload(authPayload);

        SoaAuthRequest soaAuthRequest = new SoaAuthRequest();
        soaAuthRequest.setRequest(authRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AppConstants.X_APIGW_API_ID, xApigwApiId);
        headers.set(AppConstants.X_API_KEY, xApiKey);
        return new HttpEntity<>(soaAuthRequest,headers);
    }

}
