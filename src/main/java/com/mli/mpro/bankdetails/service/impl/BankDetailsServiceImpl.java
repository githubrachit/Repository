package com.mli.mpro.bankdetails.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.bankdetails.model.*;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.services.SoaCloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.bankdetails.service.BankDetailsService;
import com.mli.mpro.location.ifsc.model.RequestData;
import com.mli.mpro.productRestriction.util.AppConstants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(BankDetailsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${urlDetails.authorization.username}")
    private String clientId;
    @Value("${urlDetails.authorization.password}")
    private String clientSecrete;

    @Value("${urlDetails.bankDetailsUrl}")
    private String bankDetailsURL;
    @Value("${urlDetails.bankDetailsSoaCloudUrl}")
    private String bankDetailsSoaCloudURL;

    @Autowired
    BankDetailsManager bankDetailsManager;
    @Autowired
    private SoaCloudService soaCloudService;


    @Override
    public SOABankResponse fetchBankDetails(RequestData bankDetailsRequest, String authToken) throws UserHandledException {

        SOABankResponse response = null;
        try {
            SOABankDetailsRequest soaBankDetailsRequest = prepareBankDetailsRequest(bankDetailsRequest);
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_CLOUD_BANK_API)) {
                ResponseEntity<?> responseEntity = soaCloudService.callingSoaApi(soaBankDetailsRequest, bankDetailsSoaCloudURL);
                if (responseEntity != null && responseEntity.getBody() != null) {
                    response = new ObjectMapper().convertValue(responseEntity.getBody(), SOABankDetailsResponse.class).getResponse();
                }
            } else {
                HttpEntity<SOABankDetailsRequest> httpEntity = null;
                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("X-IBM-Client-Id", clientId);
                headers.add("X-IBM-Client-Secret", clientSecrete);

                if (!StringUtils.isEmpty(authToken)) {
                    headers.add(AppConstants.AUTH, AppConstants.BEARER + authToken);
                }

                httpEntity = new HttpEntity<>(soaBankDetailsRequest, headers);

                ResponseEntity<SOABankDetailsResponse> responseEntity =
                        restTemplate.exchange(bankDetailsURL, HttpMethod.POST, httpEntity, SOABankDetailsResponse.class);
                SOABankDetailsResponse responseBody = responseEntity.getBody();
                if (null != responseBody) {
                    response = responseBody.getResponse();
                }
            }
            // validate MICR for YBL if Bank Details is fetched from Ybl itself
           response = validateMicrForYbl(bankDetailsRequest, response);

            logger.info("Getting Bank Details Response {} for transactionId {}", response, bankDetailsRequest.getTransactionId());

        } catch (Exception e) {
            logger.error("Exception {} while soaBankDetails API invocation for transactionId {}", e.getMessage(), bankDetailsRequest.getTransactionId());
            throw e;
        }
        return response;
    }

    private SOABankDetailsRequest prepareBankDetailsRequest(RequestData bankDetailsRequest) {

        SOABankDetailsRequest soaBankDetailsRequest = new SOABankDetailsRequest();
        SOABankRequest request = new SOABankRequest();

        soaBankDetailsRequest.setRequest(request);

        request.setHeader(createBankDetailsHeader(bankDetailsRequest.getTransactionId()));
        request.setPayload(createBankDetailsPayload(bankDetailsRequest));

        return soaBankDetailsRequest;
    }

    private SOABankHeader createBankDetailsHeader(Long transactionId) {

        SOABankHeader bankHeader = new SOABankHeader();
        bankHeader.setSoaAppId(AppConstants.FULFILLMENT);
        bankHeader.setSoaCorrelationId(transactionId + "");

        return bankHeader;
    }

    private SOABankPayload createBankDetailsPayload(RequestData bankDetailsRequest) {

        SOABankPayload bankPayload = new SOABankPayload();
        if (!StringUtils.isEmpty(bankDetailsRequest.getIfsc())) {
            bankPayload.setBankIfscCode(bankDetailsRequest.getIfsc());
            bankPayload.setBankMicrCode(AppConstants.BLANK);
        } else {
            bankPayload.setBankMicrCode(bankDetailsRequest.getMicr());
            bankPayload.setBankIfscCode(AppConstants.BLANK);
        }
        return bankPayload;
    }

    private SOABankResponse validateMicrForYbl(RequestData requestData, SOABankResponse response) {
        try {
            if (AppConstants.CHANNEL_YBL.equalsIgnoreCase(requestData.getChannel()) && null != response && null != response.getPayload() && null != response.getPayload().getBankDetails() && !StringUtils.isEmpty(requestData.getIfsc()) && !StringUtils.isEmpty(requestData.getMicr())) {
                // Filtering the bank details based on the MICR entered on UI
                List<BankDetails> details = response.getPayload().getBankDetails().stream().filter(bankDetails -> requestData.getMicr().equalsIgnoreCase(bankDetails.getBnkmicrCode())).collect(Collectors.toList());
                if (details.isEmpty()) {
                    logger.info("MICR is not matched for transactionId {}", requestData.getTransactionId());
                    requestData.setIfsc(AppConstants.BLANK);
                    // Calling Soa Api again on the basis of Micr if Micr not matched with any of the bank details response
                    response = bankDetailsManager.getBankDetailsByIFSCAndMICR(requestData);
                } else {
                    logger.info("MICR is matched for transactionId {}", requestData.getTransactionId());
                    response.getPayload().setBankDetails(details);
                }
            }
        } catch (Exception ex) {
            logger.error("Getting Exception {} while validating Micr for transactionId {}", ex.getMessage(), requestData.getTransactionId());
        }
        return response;
    }

}