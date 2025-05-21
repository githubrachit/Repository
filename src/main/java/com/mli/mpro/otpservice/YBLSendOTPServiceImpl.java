package com.mli.mpro.otpservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.RestTemplateClient;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.AESCBCAlgoCrypto;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class YBLSendOTPServiceImpl implements YBLSendOTPService {

    public static Logger logger = LoggerFactory.getLogger(YBLSendOTPServiceImpl.class);

    @Autowired
    RestTemplateClient restTemplateClient;

    @Value("${yblcustomer-details.secretKey}")
    private String yblcustomerDetailsSecretKey;

    @Override
    public Object sendOTPToYBLCustomer(Map<String, String> request) {
        Map<String,Object> map=new HashMap<>();
        try {
            HttpHeaders header = new HttpHeaders();
            logger.info("For customerId {} entered callGenerateOTPAPI ");
            Map<String, Object> sendOtp = new HashMap<>();
            sendOtp.put("otpMSG", "ezB9IGlzIFlvdXIgT25lIFRpbWUgQ29kZSBmb3IgY29uc2VudCB0byBZRVMgQmFuayBmb3Igc2hhcmluZyB5b3VyIGluZm9ybWF0aW9uIHdpdGggTWF4IExpZmUgSW5zdXJhbmNlIGZvciBwdXJwb3NlIG9mIHByb2Nlc3NpbmcgaXNzdWFuY2Ugb2YgSW5zdXJhbmNlIHBvbGljeS4=");
            Map<String, Object> sendOtpRequest = new HashMap<>();
            sendOtpRequest.put("sendOTP", sendOtp);
            logger.info("For customerId {} request sent to send OTP api : {} ",request.get("customerId"), Utility.getJsonRequest(sendOtpRequest));
            ObjectMapper mapper = new ObjectMapper();
            String yblRequestMapStr = mapper.writeValueAsString(sendOtpRequest);
            logger.info("For customerId {} decryptedStr1 response from ybl send OTP api : {} ", request.get("customerId"));
            map = mapper.readValue(yblRequestMapStr, Map.class);

            logger.info("For customerId {} decrypted response from ybl send OTP api : {} ", request.get("customerId"), Utility.getJsonRequest(map));
        }catch (Exception ex){
            logger.error("Exception occurred in callGenerateOTPAPI for customerId : {} ",Utility.getExceptionAsString(ex));
        }
        return map;
    }

}
