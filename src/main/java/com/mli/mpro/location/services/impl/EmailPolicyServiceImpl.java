package com.mli.mpro.location.services.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.Request;
import com.mli.mpro.common.models.RequestData;
import com.mli.mpro.emailPolicyPack.EmailPolicyPackRequestPayload;
import com.mli.mpro.emailPolicyPack.SoaHeader;
import com.mli.mpro.emailPolicyPack.SoaInputRequest;
import com.mli.mpro.emailPolicyPack.SoaInputResponse;
import com.mli.mpro.emailPolicyPack.SoaRequest;
import com.mli.mpro.emailPolicyPack.SoaRequestData;
import com.mli.mpro.emailPolicyPack.SoaResponse;
import com.mli.mpro.emailPolicyPack.SoaResponseData;
import com.mli.mpro.location.services.EmailPolicyService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmailPolicyServiceImpl implements EmailPolicyService {
    @Value("${urlDetails.emailPolicyPack.domain}")
    private String domain;
    @Value("${urlDetails.emailPolicyPack.clientId}")
    private String clientId;
    @Value("${urlDetails.emailPolicyPack.clientSecret}")
    private String clientSecret;
    Logger log =LoggerFactory.getLogger(EmailPolicyServiceImpl.class);
    @Retryable(value = {UserHandledException.class }, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public SoaResponseData fetchPolicyStatus(InputRequest inputRequest) throws UserHandledException {
        ArrayList<String> message = new ArrayList<>();
        SoaResponseData soaResponseData= new SoaResponseData();
        try{
            EmailPolicyPackRequestPayload payload = Optional.ofNullable(inputRequest).map(InputRequest::getRequest).map(Request::getRequestData).map(RequestData::getEmailPolicyPackRequestPayload).orElse(null);
            if(!(Objects.nonNull(payload) && StringUtils.hasLength(payload.getPolicyId()) && StringUtils.hasLength(payload.getType()))){
                message.add("Invalid Request");
                throw new UserHandledException(message , HttpStatus.BAD_REQUEST);
            }
            String url = domain+"?client_id="+clientId+"&client_secret="+clientSecret;
           URI requestUri = new URI(url);
           HttpEntity<SoaInputRequest> inputRequestHttpEntity = setInputRequest(inputRequest);
           ResponseEntity<SoaInputResponse> soaResponseEntity = new RestTemplate().postForEntity(requestUri,inputRequestHttpEntity,SoaInputResponse.class);
           soaResponseData = Optional.ofNullable(soaResponseEntity).map(ResponseEntity::getBody).map(ap->ap.getSoaResponse()).map(SoaResponse::getSoaResponseData).orElse(null);
          if(Objects.isNull(soaResponseData)
                  ||(Objects.nonNull(soaResponseData) && (!StringUtils.hasLength(soaResponseData.getStatusCode())
                  || !StringUtils.hasLength(soaResponseData.getStatusMessage())
                  || !AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(soaResponseData.getStatusCode())))){
                message.add("valid response not received from SOA");
                throw new UserHandledException(message , HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            log.error("Exception occurred while processing request at emailPolicyServiceImpl with exception -{} {}", Utility.getExceptionAsString(e),e.getMessage());        }
        return soaResponseData;

    }
    private  HttpEntity<SoaInputRequest> setInputRequest(InputRequest inputRequest) throws UserHandledException {
        SoaInputRequest soaInputRequest = new SoaInputRequest();
        SoaRequest soaRequest = new SoaRequest();
        SoaRequestData soaRequestData = new SoaRequestData();
        SoaHeader soaHeader = new SoaHeader();
        HttpHeaders soaHeaders = new HttpHeaders();
        HttpEntity<SoaInputRequest> httpEntity= null;
        EmailPolicyPackRequestPayload payload = inputRequest.getRequest().getRequestData().getEmailPolicyPackRequestPayload();
        try {
            soaHeader.setSoaAppId(AppConstants.FULFILLMENT);
            soaHeader.setSoaCorrelationId(AppConstants.ABHI);
            soaHeader.setSoaMsgVersion(AppConstants.VERSION);
            soaRequestData.setPolicyId(payload.getPolicyId());
            soaRequestData.setType(payload.getType());
            soaRequest.setSoaRequestData(soaRequestData);
            soaRequest.setSoaHeader(soaHeader);
            soaInputRequest.setSoaRequest(soaRequest);
            httpEntity = new HttpEntity<>(soaInputRequest, soaHeaders);
        }catch(Exception e){
            ArrayList<String> errorMessage = new ArrayList<>();
            errorMessage.add("Exception in processing request");
            log.error("Exception occurred while setting httprequest for policy{} is {}",payload.getPolicyId(),  Utility.getExceptionAsString(e));
            throw  new UserHandledException(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpEntity;
    }
}
