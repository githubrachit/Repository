package com.mli.mpro.location.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.docsApp.models.MsgInfo;
import com.mli.mpro.location.altfinInquiry.auth.request.AuthInputrequest;
import com.mli.mpro.location.altfinInquiry.auth.request.AuthMetadata;
import com.mli.mpro.location.altfinInquiry.auth.request.AuthPayload;
import com.mli.mpro.location.altfinInquiry.auth.response.AuthOutputResponse;
import com.mli.mpro.location.altfinInquiry.auth.response.AuthResponsePayload;
import com.mli.mpro.location.altfinInquiry.input.AltfinInputRequest;
import com.mli.mpro.location.altfinInquiry.input.AltfinMetadata;
import com.mli.mpro.location.altfinInquiry.input.AltfinPayload;
import com.mli.mpro.location.altfinInquiry.output.AltfinSoaInputResponse;
import com.mli.mpro.location.altfinInquiry.output.AltfinSoaPayload;
import com.mli.mpro.location.altfinInquiry.output.Cs1;
import com.mli.mpro.location.services.AltfinInquiryService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.Cs3;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.CentralServiceResult;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
public class AltfinInquiryServiceImpl implements AltfinInquiryService {
    @Value("${urldetails.altfinInquiry.url}")
    private String altfinUrl;
    @Value("${urldetails.altfin.apiid}")
    private String apiid;
    @Value("${urldetails.altfin.apiKey}")
    private String apiKey;
    @Value("${urldetails.altfin.clientId}")
    private String clientId;
    @Value("${urldetails.altfin.userPool}")
    private String userPoolId;
    @Value("${urldetails.altfin.auth.correlationId}")
    private String authCorrelationId;
    @Value("${urldetails.altfin.auth.username}")
    private String authUsername;
    @Value("${urldetails.altfin.auth.password}")
    private String authPassword;
    @Value("${urldetails.altfin.auth.url}")
    private String authUrl;


    private static final Logger log = LoggerFactory.getLogger(AltfinInquiryServiceImpl.class);

    @Retryable(value = {UserHandledException.class}, maxAttempts = 3)
    @Override
    public AltfinSoaInputResponse executeAltFinInquiryApi(String policyNumber) {
            MsgInfo msgInfo = new MsgInfo();
        AltfinSoaInputResponse altfinSoaInputResponse = new AltfinSoaInputResponse();
            try {
                URI altFinUri = new URI(altfinUrl);
                HttpEntity<AltfinInputRequest> requestHttpEntity = setInputrequest(policyNumber);
                HttpEntity<AltfinSoaInputResponse> response = new RestTemplate().postForEntity(altFinUri, requestHttpEntity, AltfinSoaInputResponse.class);
                log.info("Request and response  for SOA altfin is {} and {} for policy {}",requestHttpEntity,response,policyNumber);
                String msgCode = Optional.ofNullable(response).map(response1 -> response1.getBody()).map(body -> body.getMsgInfo())
                        .map(MsgInfo::getMsgCode).orElse(AppConstants.BLANK);
                if(AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(msgCode)){
                    altfinSoaInputResponse=  response.getBody();
                    setResponseInDb(altfinSoaInputResponse, policyNumber);
                    return altfinSoaInputResponse;
                }
            } catch (Exception e) {
                log.info("Exception occurred while calling altfin api for policyNumber {} with error -{}", policyNumber, Utility.getExceptionAsString(e));
            }
            return altfinSoaInputResponse;
        }

    public HttpEntity<AltfinInputRequest> setInputrequest(String policyNumber) {
        HttpEntity<AltfinInputRequest> httpRequest = null;
        HttpHeaders headers = new HttpHeaders();
        AltfinPayload payload = new AltfinPayload();
        AltfinMetadata authMetadata = new AltfinMetadata();
        AltfinInputRequest inputrequest = new AltfinInputRequest();
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setCacheControl(AppConstants.NO_CACHE);
            headers.add(AppConstants.X_API_KEY, apiKey);
            headers.add(AppConstants.X_CLIENT_ID, clientId);
            headers.add(AppConstants.USER_POOL_ID, userPoolId);
            headers.add(AppConstants.X_APIGW_API_ID,apiid);
            String token = generatetoken(headers);
            log.info("token for the policy number is {} - {}",token,policyNumber);
            headers.add(AppConstants.AUTH, token);
            authMetadata.setAppId(AppConstants.MPRO.toUpperCase());
            authMetadata.setCorrelationId(policyNumber);
            authMetadata.setProposalNumber(policyNumber);
            payload.setPolicyNumber(policyNumber);
            inputrequest.setPayload(payload);
            inputrequest.setMetadata(authMetadata);
            httpRequest = new HttpEntity<>(inputrequest, headers);
        } catch (Exception e) {
            log.info("Exception occurred while setting input request of altFin for policy number {} with error-{}",policyNumber, Utility.getExceptionAsString(e));
        }
        return httpRequest;

    }
    private String generatetoken(HttpHeaders headers) {
        AuthInputrequest inputrequest = new AuthInputrequest();
        AuthMetadata authMetadata = new AuthMetadata();
        AuthPayload payload = new AuthPayload();
        String token = AppConstants.BLANK;
        HttpHeaders headers2 = new HttpHeaders();
        try {
            authMetadata.setAppId(AppConstants.APPID);
            authMetadata.setCorrelationId(authCorrelationId);
            payload.setUsername(authUsername);
            payload.setPassword(authPassword);
            inputrequest.setMetadata(authMetadata);
            inputrequest.setAuthPayload(payload);
            URI authUri = new URI(authUrl);
            HttpEntity<AuthInputrequest> httpRequest = new HttpEntity<>(inputrequest, headers);
            log.info("Input request for token is {} with uri {}",httpRequest,authUri.toString());
            HttpEntity<AuthOutputResponse> response = new RestTemplate().postForEntity(authUri, httpRequest, AuthOutputResponse.class);
            token = Optional.ofNullable(response).map(response1 -> response1.getBody()).map(body -> body.getPayload()).map(AuthResponsePayload::getToken).orElse(AppConstants.BLANK);
            log.info("token generated is {}",token);
        } catch (Exception e) {
            log.info("exception occurred while setting api token-{}", e.getMessage());
        }
        return token;
    }
private void setResponseInDb(AltfinSoaInputResponse altfinSoaInputResponse, String policyNumber){
    CentralServiceResult centralServiceResult = new CentralServiceResult();
    Cs1 cs1 = new Cs1();
    Cs3 cs3 = new Cs3();
    MongoTemplate mongoTemplate = BeanUtil.getBean(MongoTemplate.class);
    Query query = new Query();
    Update update = new Update();
    try{
        Criteria criteria = Criteria.where(AppConstants.POLICYNUM_PATH).is(policyNumber);
        query.addCriteria(criteria);
        cs1 = Optional.ofNullable(altfinSoaInputResponse).map(AltfinSoaInputResponse::getPayload).map(AltfinSoaPayload::getCs1).orElse(null);
        cs3 = Optional.ofNullable(altfinSoaInputResponse).map(AltfinSoaInputResponse::getPayload).map(AltfinSoaPayload::getCs3).orElse(null);
        centralServiceResult.setCs1(cs1);
        centralServiceResult.setCs3(cs3);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> centralServiceResultMap = objectMapper.convertValue(centralServiceResult,Map.class);
        update.set(AppConstants.CENTRAL_SERVICE_RESULT_PATH,centralServiceResultMap);
        log.info("before execute save ope ration for policy {} with query{} ,update{}",policyNumber, query, update);
        ProposalDetails proposalDetails = mongoTemplate.findAndModify(query,update,ProposalDetails.class);
        log.info("proposalDetails after the update operation for policy {} are {}",policyNumber,proposalDetails);
    }catch(Exception e){
        log.info("Exception occurred while saving data in proposal db for policy{}",policyNumber);
        e.printStackTrace();
    }
}

}

