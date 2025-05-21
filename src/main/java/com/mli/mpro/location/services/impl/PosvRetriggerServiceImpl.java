package com.mli.mpro.location.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.ivc.request.InputRequest;
import com.mli.mpro.location.ivc.request.SoaHeader;
import com.mli.mpro.location.ivc.request.SoaInputRequest;
import com.mli.mpro.location.ivc.request.SoaRequest;
import com.mli.mpro.location.ivc.request.SoaRequestPayload;
import com.mli.mpro.location.ivc.response.OutputResponse;
import com.mli.mpro.location.ivc.response.Response;
import com.mli.mpro.location.ivc.response.SoaInputResponse;
import com.mli.mpro.location.ivc.response.SoaResponse;
import com.mli.mpro.location.services.PosvRetriggerService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
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
public class PosvRetriggerServiceImpl implements PosvRetriggerService {
        Logger log = LoggerFactory.getLogger(PosvRetriggerServiceImpl.class);
        @Value("${posv.retrigger.url}")
        String posvRetriggerUrl;
        @Value("${posv.retrigger.apiKey}")
        String posvRetriggerApiKey;
        @Value("${posv.retrigger.correlationId}")
        String posvRetriggerCorrelationId;
        @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
        @Override
        public OutputResponse callPosvRetriggerApi(InputRequest inputRequest, ArrayList<String> errorMessages) {
            log.info("input request inside service method is{}",inputRequest);
            String policyNumber = Optional.ofNullable(inputRequest).map(InputRequest::getPolicyNumber).orElse(AppConstants.BLANK);
            log.info("policyNumber inside service method is{}",policyNumber);
            SoaResponse soaResponse = null;
            String status = AppConstants.FAILED;
            String urlSent = null;
            OutputResponse outputResponse = new OutputResponse();
            Response response = new Response();
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (!StringUtils.hasLength(policyNumber)) {
                    errorMessages.add("No policy number found in mpro request");
                    throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                URI posvRetriggerFinalUri= new URI(posvRetriggerUrl);
                log.info("URI for retrigger is {} and url-{}",posvRetriggerFinalUri,posvRetriggerUrl);
                HttpEntity<SoaInputRequest> httpEntity = setInputRequest(policyNumber, errorMessages);
                ResponseEntity<SoaInputResponse> apiResponse = new RestTemplate().postForEntity(posvRetriggerFinalUri, httpEntity, SoaInputResponse.class);
                SoaRequestPayload payload =Optional.ofNullable(httpEntity).map(entity->entity.getBody()).map(body->body.getRequest()).map(SoaRequest::getPayload).orElse(null);
                log.info("Request and Response from posv retrigger api are request- {} | response -{} for policy {}",payload, mapper.writeValueAsString(apiResponse),policyNumber);
                soaResponse = Optional.ofNullable(apiResponse).map(entity-> entity.getBody())
                        .map(body->body.getResponse()).orElse(null);
                if(Objects.nonNull(soaResponse) && Objects.nonNull(soaResponse.getMsgInfo())){
                    status = AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(soaResponse.getMsgInfo().getMsgCode())
                             && soaResponse.getPayload().getBusinessMsg().startsWith(AppConstants.POSV) //TODO- remove this condition after posv updates their api.
                                 ?AppConstants.SUCCESS:AppConstants.FAILED;
                    urlSent = soaResponse.getPayload().getBusinessMsg();
                }
                response.setStatus(status);
                response.setUrlSent(urlSent);
            }catch(Exception e){
                log.info("Exception occurred while calling soa api for policy {} - {}",policyNumber, Utility.getExceptionAsString(e));
                errorMessages.add(e.getMessage());
                e.printStackTrace();
            }
            outputResponse.setResponse(response);
            resetPosvTags(response, policyNumber);
            return outputResponse;
        }
        public HttpEntity<SoaInputRequest> setInputRequest(String policyNumber, ArrayList<String> errorMessages){
            SoaHeader header = new SoaHeader();
            HttpEntity<SoaInputRequest> httpEntity = null;
            HttpHeaders headers = new HttpHeaders();
            SoaInputRequest soaInputRequest = new SoaInputRequest();
            SoaRequest request = new SoaRequest();
            SoaRequestPayload payload = new SoaRequestPayload();
            ObjectMapper requestMapper = new ObjectMapper();
            try{
                headers.add(AppConstants.X_API_KEY, posvRetriggerApiKey);
                header.setAppId(AppConstants.MPRO);
                header.setCorrelationId(posvRetriggerCorrelationId);
                payload.setProposalNo(policyNumber);
                payload.setRetry(true);
                payload.setIvcToIvc(AppConstants.Y);
                payload.setIvcToHybrid(AppConstants.N);
                payload.setHybridToIvc(AppConstants.N);
                request.setHeader(header);
                request.setPayload(payload);
                soaInputRequest.setRequest(request);
                httpEntity =new HttpEntity<>(soaInputRequest,headers);
                log.info("Entity request for retrigger posv with policyNumber {} is {}",policyNumber,payload);
            }catch(Exception e){
                log.info("Exception occurred while setting the request entity for policy {} and exception {}",policyNumber, Utility.getExceptionAsString(e));
                errorMessages.add(e.getMessage());
            }
            return httpEntity;
        }
        public void  resetPosvTags(Response response, String policyNumber){
            try {
                if (AppConstants.SUCCESS.equalsIgnoreCase(response.getStatus()) && StringUtils.hasLength(policyNumber)
                      && response.getUrlSent().startsWith(AppConstants.POSV)) {//TODO- remove this condition after posv updates their api.
                    Query query = new Query();
                    Update update = new Update();
                    MongoTemplate mongoTemplate = BeanUtil.getBean(MongoTemplate.class);
                    query.addCriteria(Criteria.where(AppConstants.POLICYNUM_PATH).is(policyNumber));
                    update.set(AppConstants.OVERALLPRODSTATUS_PATH, AppConstants.BLANK);
                    update.set(AppConstants.POSV_JOURNEY_STATUS, AppConstants.POSV_PUSHED_TO_POSV_STATUS_MESSAGE);
                    update.set(AppConstants.IVC_RETRIGGERED_PATH, AppConstants.Y);
                    ProposalDetails proposalDetails = mongoTemplate.findAndModify(query, update, ProposalDetails.class);
                    log.info("Tag reset for posv completed with query-{},update-{} and proposal details {}", query, update, proposalDetails);
                }
            }catch(Exception e){
                log.info("Exception occurred during mongo update query for policy {} - {}",policyNumber,Utility.getExceptionAsString(e));
            }

        }
    }