package com.mli.mpro.location.soa.service.impl;

import com.mli.mpro.location.endUser.models.EndUserRequest;
import com.mli.mpro.location.endUser.models.OutputResponse;
import com.mli.mpro.location.endUser.models.Result;
import com.mli.mpro.location.endUser.models.SoaRequest;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.service.EndUserService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service

public class EndUserServiceImpl implements EndUserService {
    @Value("${urldetails.x-apigw-api-id}")
    private String xApigwApiId;
    @Value("${urldetails.x-api-key}")
    private String xApiKey;

    @Value("${urlDetails.lmc.token.api}")
    private String endUserUrl;

    Logger logger = LoggerFactory.getLogger(EndUserServiceImpl.class);

    @Override
    public ResponseEntity<Object> endUserAPI(EndUserRequest inputRequest) {
        logger.info("Request received for endUserApi {} {}", endUserUrl, inputRequest);
        HttpHeaders headers = getHeaders();
        SoaRequest soaRequest = new SoaRequest();
        soaRequest.setRequest(inputRequest.getRequest().getData());
        HttpEntity<SoaRequest> requestEntity = new HttpEntity<>(soaRequest, headers);
        RestTemplate restTemplate = new RestTemplate(Utility.clientHttpRequestFactory(SoaConstants.SOA_TIMEOUT));
        ResponseEntity<Object> responseObject = restTemplate.postForEntity(endUserUrl, requestEntity, Object.class);
        logger.info("Response received from soa for end user api {}",responseObject);
        Result result = new Result();
        result.setHeaders(responseObject.getHeaders());
        result.setBody(responseObject.getBody());
        result.setStatusCode(responseObject.getStatusCodeValue());
        OutputResponse outputResponse = new OutputResponse(result);
        return new ResponseEntity<>(outputResponse, HttpStatus.OK);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AppConstants.X_APIGW_API_ID, xApigwApiId);//"x-apigw-api-id"
        headers.add(AppConstants.X_API_KEY, xApiKey);//"x-api-key"
        logger.info("Headers in endUserAPI {}",headers);
        return headers;
    }
}
