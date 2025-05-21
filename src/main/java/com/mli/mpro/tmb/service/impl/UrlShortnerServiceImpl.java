package com.mli.mpro.tmb.service.impl;

import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.tmb.model.urlshortner.*;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Service
public class UrlShortnerServiceImpl  implements UrlShortnerService{

    private static final Logger log = LoggerFactory.getLogger(UrlShortnerServiceImpl.class);

    @Autowired
    private OauthTokenRepository oauthTokenRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalServiceConfig urlConfig;

    @Value("${redis.oauthkey}")
    private String oauthkey;

    @Override
    public String shortenUrl(String longUrl, String expiry) {
        log.info("url shortner method called");
        String shortUrl = null;
        try {
            String oauthToken = getOauthToken();
            if (StringUtils.hasText(oauthToken)) {
                log.info("calling url shortner service");
                shortUrl = shortenUrlServiceCall(longUrl, expiry, oauthToken);
            }
        } catch (Exception e) {
            log.error("failed to short url: {}", e.getMessage());
        }
        return shortUrl;
    }

    private String shortenUrlServiceCall(String longUrl, String expiry, String oauthToken) {
        String shortUrl = null;
        try {
            HttpHeaders httpHeaders = getUrlServiceHttpHeaders(oauthToken);
            UrlShortnerRequest urlShortnerRequest = getUrlShortnerRequest(longUrl, expiry);
            HttpEntity<UrlShortnerRequest> httpEntity = new HttpEntity<>(urlShortnerRequest, httpHeaders);
            log.info("request being sent to the url shortner service : {}", httpEntity);
            String urlShortnerServiceUrl = urlConfig.getUrlDetails().get(URL_SHORTNER_SERVICE_URL);
            UrlShortnerResponse urlShortnerResponse = restTemplate.postForObject(urlShortnerServiceUrl, httpEntity, UrlShortnerResponse.class);
            log.info("response received from url shortner service : {}", urlShortnerResponse);
            if (Objects.nonNull(urlShortnerResponse) && Objects.nonNull(urlShortnerResponse.getResponse())
                    && Objects.nonNull(urlShortnerResponse.getResponse().getPayload())
                    && StringUtils.hasText(urlShortnerResponse.getResponse().getPayload().getShortUrl())) {
                shortUrl = urlShortnerResponse.getResponse().getPayload().getShortUrl();
                log.info("short url received successfully");
            }
        } catch (RestClientException e) {
            log.error("exception occurred while calling url shortner service: {}", Utility.getExceptionAsString(e));
        }
        return shortUrl;
    }

    private HttpHeaders getUrlServiceHttpHeaders(String oauthToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-apigw-api-id", urlConfig.getUrlDetails().get(URL_SHORTNER_XAPIGW));
        httpHeaders.set("x-api-key", urlConfig.getUrlDetails().get(URL_SHORTNER_XAPIKEY));
        httpHeaders.add(AppConstants.AUTH, AppConstants.BEARER + oauthToken);
        return httpHeaders;
    }

    private UrlShortnerRequest getUrlShortnerRequest(String longUrl, String expiry) {
        Header requestHeader = new Header(UUID.randomUUID().toString(), urlConfig.getUrlDetails().get(URL_SHORTNER_SOAAPPID));
        RequestPayload requestPayload = new RequestPayload(longUrl, expiry, "");
        Request request = new Request(requestHeader, requestPayload);
        return new UrlShortnerRequest(request);
    }

    private String getOauthToken() {
        String oauthToken = "";
        try {
            String urlShortnerOauthKey = oauthkey.concat("urlShortner");
            log.info("url shortner method called for oauthkey: {}", urlShortnerOauthKey);
            oauthToken = oauthTokenRepo.getToken(urlShortnerOauthKey);
            if (!StringUtils.hasText(oauthToken)) {
                log.info("url shortner oauth token api execution started");
                oauthToken = getTokenFromService();
            }
        } catch (Exception e) {
            log.error("url shortner token fetch failed with exception: {} ", e.getMessage());
            oauthToken = getTokenFromService();
        }
        return oauthToken;
    }

    private String getTokenFromService() {
        log.info("Oauth Token Service execution started.");
        UrlShortnerTokenResponse tokenResponse = null;
        String accessToken = "";
        int expireTime = 0;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AppConstants.AUTH, urlConfig.getUrlDetails().get(URL_SHORTNER_BEARER));
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("grant_type", "client_credentials");
            multiValueMap.add("scope", "admin/read");
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, httpHeaders);
            log.info("request being sent to the url shortner oauth soa service : {}", httpEntity);
            String oauthTokenUrl = urlConfig.getUrlDetails().get(URL_SHORTNER_OAUTH_URL);
            tokenResponse = restTemplate.postForObject(oauthTokenUrl, httpEntity, UrlShortnerTokenResponse.class);
            log.info("response received from url shortner oauth soa service : {}", tokenResponse);
            if (Objects.nonNull(tokenResponse) && StringUtils.hasText(tokenResponse.getAccessToken())) {
                accessToken = tokenResponse.getAccessToken();
                expireTime = tokenResponse.getExpiresIn() - TOKEN_DELAY;
                setTokenToRedis(accessToken, expireTime);
            }
        } catch (Exception ex) {
            log.error("exception occurred while calling url shortner oauth soa service: {}", Utility.getExceptionAsString(ex));
        }
        return accessToken;
    }

    private void setTokenToRedis(String accessToken, int expireTime) {
        try {
            oauthTokenRepo.setToken(oauthkey.concat("urlShortner"), expireTime, accessToken);
            log.info("url shortner oauth token saved to redis");
        } catch (Exception e) {
            log.error("failed to save token to redis with exception: {}", e.getMessage());
        }
    }
}
