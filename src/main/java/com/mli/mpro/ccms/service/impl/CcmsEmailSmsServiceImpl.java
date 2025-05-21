package com.mli.mpro.ccms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.ccms.model.*;
import com.mli.mpro.ccms.service.AccessAuthTokenService;
import com.mli.mpro.ccms.service.CcmsEmailSmsService;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.EncryptionDecryptionAESUtil;
import com.mli.mpro.utils.Utility;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CcmsEmailSmsServiceImpl implements CcmsEmailSmsService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessAuthTokenService accessAuthTokenService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${urlDetails.ccmsApiUrl}")
    private  String ccmsApiUrl;

    @Value("${urlDetails.ccmsxapigwid}")
    private String ccmsXapigwId;

    @Value("${urlDetails.ccmsxapikey}")
    private String ccmsXapiKey;

    @Value("${urlDetails.ccms-enc.key}")
    private String ccmsSecretKey;

    private static final String SUCCESS = "success";

    private static final String FAIL = "fail";

    private static final String PAYLOAD = "payload";

    private Logger logger = LoggerFactory.getLogger(CcmsEmailSmsServiceImpl.class);

    @Override
    @Async
    public String nearRealTimeNotificationGenerationAsync(DocInfo docInfo, Data ccmsSmsPayload) throws UserHandledException {
        return nearRealTimeNotificationGeneration(docInfo, ccmsSmsPayload);
    }

    @Override
    public String nearRealTimeNotificationGeneration(DocInfo docInfo, Data ccmsSmsPayload) throws UserHandledException {
        InputRequest inputRequest = new InputRequest();
        long transactionId = Long.parseLong(ccmsSmsPayload.getTransactionId());
        MDC.put(AppConstants.TRANSACTIONID,transactionId);
        logger.info("CCMS SENDING SMS for transaction id {}", transactionId);
        Request request = new Request();
        inputRequest.setRequest(request);
        request.setHeader(setHeaderForCcmsRequest());
        request.setPayload(getPayloadForCcmsAPI(docInfo, ccmsSmsPayload));
        String json = Utility.printJsonRequest(request);
        logger.info("Sending sms for transactionId {} with the request {}", transactionId, json);
        AccessAuthTokenResponse accessAuthTokenResponse = accessAuthTokenService.getAuthToken();
        OutputResponse response;
        try {
            String inputRequestJson = MAPPER.writeValueAsString(inputRequest);
            logger.info("CCMS input request {}", inputRequestJson);
            String encryptedInput = EncryptionDecryptionAESUtil.encrypt(inputRequestJson, Base64.getDecoder().decode(ccmsSecretKey));
            logger.info("CCMS sending sms with the request {}", encryptedInput);
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put(PAYLOAD, encryptedInput);
            String requestJson = MAPPER.writeValueAsString(hashMap);
            logger.info("CCMS sending sms for transactionId with the request {}", requestJson);
            ResponseEntity<String> apiResponse = null;
            logger.info("CCMS url {} ",ccmsApiUrl);
            apiResponse = restTemplate
                    .exchange(ccmsApiUrl, HttpMethod.POST, getHttpEntityForCcmsAPI(requestJson, accessAuthTokenResponse), String.class);
            logger.info("CCMS api response {}", apiResponse);
            Map<String, String> result = MAPPER.readValue(apiResponse.getBody(), HashMap.class);
            byte[] decryptedByteResponse = EncryptionDecryptionAESUtil.decrypt(Base64.getDecoder().decode(result.get(PAYLOAD)), Base64.getDecoder().decode(ccmsSecretKey));
            String responseJson = new String(decryptedByteResponse, StandardCharsets.UTF_8);
            response = MAPPER.readValue(responseJson, OutputResponse.class);
            logger.info("CCMS response {}",response);
        } catch (Exception ex) {
            logger.error("CCMS cannot connect to sms service for transactionId {} {}",transactionId, ex.getStackTrace());
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add("CCMS cannot connect to sms service");
            throw new UserHandledException(null, errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response.getResponse().getMsgInfo().getMsgCode().equals("200")) {
            logger.info("CCMS sms response SUCCESS transactionId {} ",transactionId);
            return SUCCESS;
        }
        logger.info("CCMS sms response FAIL transactionId {} ",transactionId);
        return FAIL;
    }
    private Payload getPayloadForCcmsAPI(DocInfo docInfo, Data ccmsSmsPayload) {
        Payload payload = new Payload();
        payload.setDocInfo(docInfo);
        payload.setData(ccmsSmsPayload);
        return payload;
    }

    private Header setHeaderForCcmsRequest() {
        Header header = new Header();
        header.setSoaAppId(AppConstants.APPNAME);
        header.setSoaCorrelationId(UUID.randomUUID().toString());
        return header;
    }

    private HttpEntity<?> getHttpEntityForCcmsAPI(String request, AccessAuthTokenResponse accessAuthTokenResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AppConstants.X_APIGW_API_ID, ccmsXapigwId);
        headers.add(AppConstants.X_API_KEY, ccmsXapiKey);
        headers.add(AppConstants.HEADER_APP_ID, AppConstants.APPNAME);
        headers.add(AppConstants.AUTH, AppConstants.BEARER + accessAuthTokenResponse.getAccessToken());
        logger.info("with request payload {}, with headers {}", request, headers);
        return new HttpEntity<>(request, headers);
    }
}
