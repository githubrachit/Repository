package com.mli.mpro.tmb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.tmb.model.RequestBodyForInsuranceInquery;
import com.mli.mpro.tmb.model.*;
import com.mli.mpro.tmb.service.CustomerDetailsService;
import com.mli.mpro.tmb.utility.ServiceConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomerDetailsServiceImpl.class);

    @Value("${tmb.inquery.token.url}")
    private String tokenUrl;
    @Value("${tmb.inquery.api.url}")
    private String apiUrl;

    @Value("${tmb.account.token.url}")
    private String accountTokenUrl;

    @Value("${tmb.account.api.url}")
    private String accountAPIUrl;

    @Autowired
    OauthTokenTMBUtility oauthTokenTMBUtility;

    @Autowired
    TmbApiCallingUtility tmbApiCallingUtility;

    @Autowired
    TmbServiceImpl tmbServiceImpl;

    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);


    @Override
    public CustomerDetailsResponse fetchCustomerDetails(OnboardingState onboardingState,SaveProposalRequest request) {
        long requestedTime = System.currentTimeMillis();
        CustomerDetailsResponse customerDetailsResponse = null;
        try {
            String response = callCustomerDetailsAPI(onboardingState,request);
            customerDetailsResponse = objectMapper.readValue(response, CustomerDetailsResponse.class);
            log.info("For transactionId {} customerDetailsResponse is {}", request.getTransactionId(), Utility.getJsonRequest(customerDetailsResponse));
        } catch (Exception e) {
            log.error("Error while fetching customer details {} ", Utility.getExceptionAsString(e));
        } finally {
            log.info("Time {} sec for processing to get customer Details", Utility.getProcessedTime(requestedTime));
        }
        return customerDetailsResponse;
    }

    private String callCustomerDetailsAPI(OnboardingState onboardingState,SaveProposalRequest request) {
        String customerDetailsResponseStr = null;
        try {
            TMBRequest customerDetailsRequest = new TMBRequest();
            setCustomerDetailsRequest(request, customerDetailsRequest);
            String token = tmbServiceImpl.retryTokenGeneration("inquiry", tokenUrl,ServiceConstants.CUSTOMER_DATA_FETCH,onboardingState.getTransactionId());
            if (token!=null) {
                customerDetailsResponseStr = tmbServiceImpl.retryCallTmbApi(apiUrl, token, Utility.getJsonRequest(customerDetailsRequest), onboardingState,onboardingState.getTransactionId(), ServiceConstants.CUSTOMER_DATA_FETCH);
            } else {
                log.error("Token is null for transactionId {} ", request.getTransactionId());
            }
            log.info("For transactionId {} customerDetailsResponse is {}", request.getTransactionId(), customerDetailsResponseStr);
        } catch (Exception e){
            log.error("Error while fetching customer details {} ", Utility.getExceptionAsString(e));
        }
        return customerDetailsResponseStr;

    }

    private void setCustomerDetailsRequest(SaveProposalRequest request, TMBRequest customerDetailsRequest) {
        customerDetailsRequest.setCustomerId(request.getCustomerId());
        customerDetailsRequest.setTransactionId(request.getCorrelationId());
    }


    @Override
    public AccountDetailsResponse fetchAccountDetails(OnboardingState onboardingState,SaveProposalRequest request) {
        long requestedTime = System.currentTimeMillis();
        AccountDetailsResponse accountDetailsResponse = null;
        try {
            String response = callAccountDetailsAPI(onboardingState,request);
            accountDetailsResponse = objectMapper.readValue(response, AccountDetailsResponse.class);
            log.info("For transactionId {} accountDetailsResponse is {}", request.getTransactionId(), Utility.getJsonRequest(accountDetailsResponse));
        } catch (Exception e) {
            log.error("Error while fetching account details {} ", Utility.getExceptionAsString(e));
        } finally {
            log.info("Time {} sec for processing to get account Details", Utility.getProcessedTime(requestedTime));
        }
        return accountDetailsResponse;
    }

    private String callAccountDetailsAPI(OnboardingState onboardingState,SaveProposalRequest request) {
        String accountDetailsResponseStr = null;
        try {
            TMBRequest accountDetailsRequest = new TMBRequest();
            setAccountDetailsRequest(request, accountDetailsRequest);
            String token = tmbServiceImpl.retryTokenGeneration("acctdtlscid", accountTokenUrl, ServiceConstants.ACCOUNT_DATA_FETCH,onboardingState.getTransactionId());
            if (token!=null) {
                accountDetailsResponseStr = tmbServiceImpl.retryCallTmbApi(accountAPIUrl, token, Utility.getJsonRequest(accountDetailsRequest), onboardingState, onboardingState.getTransactionId(), ServiceConstants.ACCOUNT_DATA_FETCH);
            } else {
                log.error("Token is null for transactionId {} ", request.getTransactionId());
            }
            log.info("For transactionId {} accountDetailsResponse is {}", request.getTransactionId(), accountDetailsResponseStr);
        } catch (Exception e){
            log.error("Error while fetching account details {} ", Utility.getExceptionAsString(e));
        }
        return accountDetailsResponseStr;

    }

    private void setAccountDetailsRequest(SaveProposalRequest request, TMBRequest accountDetailsRequest) {
        accountDetailsRequest.setCustomerId(request.getCustomerId());
        accountDetailsRequest.setTransactionId(request.getCorrelationId());
    }
}
