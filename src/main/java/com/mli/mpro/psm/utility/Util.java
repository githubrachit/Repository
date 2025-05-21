package com.mli.mpro.psm.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.productRestriction.models.RequestResponse;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.psm.model.*;
import com.mli.mpro.utils.EncryptionDecryptionOnboardingUtil;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.mli.mpro.productRestriction.util.AppConstants.UJJIVAN;

public class Util {
    private static Logger logger = LoggerFactory.getLogger(Util.class);

    public static String decryptRequest(String payload) {
        String decryptedRequest = null;
        try {
            String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
            byte[] decryptedBytes = EncryptionDecryptionOnboardingUtil.decrypt(
                    java.util.Base64.getDecoder().decode(payload), java.util.Base64.getDecoder().decode(key));
            decryptedRequest = new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("Exception while decrypting: {}", com.mli.mpro.utils.Utility.getExceptionAsString(ex));
        }
        return decryptedRequest;


    }

    public static RequestResponse errorResponse(HttpStatus httpStatus, List<Object> errorMessages, String encryptionSource,String[] IVandKey) throws UserHandledException, JsonProcessingException {
        RequestResponse encResponse = new RequestResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        OutputResponse outputResponse;
        if(UJJIVAN.equalsIgnoreCase(encryptionSource))
        {
            outputResponse = getFailureResponse(httpStatus.value(), errorMessages);
            String response = EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse) , IVandKey[0], IVandKey[1].getBytes());
            encResponse.setPayload(response);
            return encResponse;
        }
        return encryptResponse(getFailureResponse(httpStatus.value(), errorMessages));
    }

    public static OutputResponse getFailureResponse(int code, List<Object> errorMessages) {
        MessageInfo msgInfo = new MessageInfo(String.valueOf(code), AppConstants.STATUS_FAILURE, AppConstants.RESPONSE_FAILURE, errorMessages);
        return new OutputResponse((new PsmResponse(msgInfo)));
    }

    public static RequestResponse encryptResponse(OutputResponse response) {
        RequestResponse encResponse = new RequestResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
            String payload = EncryptionDecryptionOnboardingUtil.encrypt(objectMapper.writeValueAsString(response),
                    java.util.Base64.getDecoder().decode(key));
            encResponse.setPayload(payload);
        } catch (Exception e) {
            logger.error("Exception while encrypting: {}", com.mli.mpro.utils.Utility.getExceptionAsString(e));
        }
        return encResponse;
    }

}




