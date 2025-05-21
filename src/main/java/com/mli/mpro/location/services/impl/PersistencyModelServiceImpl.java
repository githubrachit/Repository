package com.mli.mpro.location.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.TransformationMasterDetails;
import com.mli.mpro.location.services.PersistencyModelService;
import com.mli.mpro.location.services.TransformationMasterDetailService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.routingposv.models.PersistencyModelDetails;
import com.mli.mpro.routingposv.models.PersistencyModelApiResponse;
import com.mli.mpro.routingposv.models.PersistencyModelRequest;
import com.mli.mpro.utils.Utility;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PersistencyModelServiceImpl implements PersistencyModelService {

    private static final Logger log = LoggerFactory.getLogger(PersistencyModelServiceImpl.class);
    @Value("${persistencyModelApi.url}")
    private String persistencyModelApiUrl;
    @Value("${persistencyModelApi.x-api-key}")
    private String persistencyModelXApiKey;
    @Value("${persistencyModelApi.x-apigw-api-id}")
    private String persistencyModelXApiGWId;

    @Autowired
    private TransformationMasterDetailService transformationMasterDetailService;

    public PersistencyModelApiResponse executePersistenceModelApi(PersistencyModelRequest persistencyModelRequest, ProposalDetails proposalDetails) throws UserHandledException {
        log.info("inside executePersistenceModelApi with transactionid {} url {}", proposalDetails.getTransactionId(), persistencyModelApiUrl);
        CloseableHttpClient httpClient;
        httpClient = HttpClients.createDefault();
        PersistencyModelApiResponse response;
        try {
            HttpPost httpPost = new HttpPost(persistencyModelApiUrl);
            httpPost.addHeader("x-api-key", persistencyModelXApiKey);
            httpPost.addHeader("x-apigw-api-id", persistencyModelXApiGWId);
            ObjectMapper requestMapper = new ObjectMapper();
            String json = Utility.getJsonRequest(persistencyModelRequest);
            log.info("The request being sent to persistency model soa service for transactionid {} {}", proposalDetails.getTransactionId(), Utility.printJsonRequest(persistencyModelRequest));
            org.apache.http.HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            long requestedTime = System.currentTimeMillis();
            CloseableHttpResponse apiResponse = httpClient.execute(httpPost);
            long processedTime = (System.currentTimeMillis() - requestedTime) / 1000;
            log.info("Persistency Model API for transaction id {} took {} seconds to process the request", proposalDetails.getTransactionId(), processedTime);
            org.apache.http.HttpEntity httpResponse = apiResponse.getEntity();
            String result = EntityUtils.toString(httpResponse);
            log.info("Response received from persistency model for transactionId {} is {}", proposalDetails.getTransactionId(), result);
            response = requestMapper.readValue(result, PersistencyModelApiResponse.class);
            log.info("The response of persistency model from soa service {}", response);
        }catch (Exception exp){
            log.error("error occurred in executing PersistenceModel api with transactionId {} {} ", proposalDetails.getTransactionId(), Utility.getExceptionAsString(exp));
            throw new UserHandledException();
        }
        finally {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        return response;
    }

   
    @Override
    public void recover(UserHandledException exception, ProposalDetails proposalDetails) {
        log.info("inside persistencemodelapi after failure retries with transactionid {} ", proposalDetails.getTransactionId());
        PersistencyModelDetails persistencyModelDetails = new PersistencyModelDetails();
        persistencyModelDetails.setEntryDate(new Date());
        persistencyModelDetails.setMessagePers(AppConstants.BLANK);
        persistencyModelDetails.setNormalizedScorePers(AppConstants.BLANK);
        persistencyModelDetails.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
        persistencyModelDetails.setRiskyTagPers(AppConstants.BLANK);
        persistencyModelDetails.setScoringModelPers(AppConstants.BLANK);
        persistencyModelDetails.setStatusPers(AppConstants.BLANK);
        proposalDetails.getUnderwritingServiceDetails().setPersistencyModelDetails(persistencyModelDetails);
    }
}
