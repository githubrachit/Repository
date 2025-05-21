package com.mli.mpro.tmb.utility;


import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.tmb.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.productRestriction.util.AppConstants.STATUS_FAILURE;

public class Utility {

    private static Logger logger = LoggerFactory.getLogger(Utility.class);

    private Utility() {
    }

    public static boolean validateInputRequestOtpApi(InputRequestOtpApi inputRequestOtpApi) {
        if (ObjectUtils.isEmpty(inputRequestOtpApi)
                || ObjectUtils.isEmpty(inputRequestOtpApi.getPayload())
                || ObjectUtils.isEmpty(inputRequestOtpApi.getHeader().getSource())) {
            return false;
        }
        return true;
    }
    public static boolean validateInputRequestVerifyOtpApi(InputRequestOtpApi inputRequestOtpApi) {
        if (ObjectUtils.isEmpty(inputRequestOtpApi)
                || ObjectUtils.isEmpty(inputRequestOtpApi.getPayload())
                || ObjectUtils.isEmpty(inputRequestOtpApi.getHeader())
                || ObjectUtils.isEmpty(inputRequestOtpApi.getHeader().getCorrelationId())
                || ObjectUtils.isEmpty(inputRequestOtpApi.getHeader().getValue())
                || ObjectUtils.isEmpty(inputRequestOtpApi.getHeader().getSource())){
            return false;
        }
        return true;
    }

    public static boolean validateInputRequestGenerateLinkApi(Payload payload) {
        return (!isNewLinkForPDF(payload) && ( payload.getTransactionId() == null || "0".equalsIgnoreCase(payload.getTransactionId())));
    }

    public static boolean isNewLinkForPDF(Payload payload) {
        return (AppConstants.PROPOSAL_DATA_FETCH.equalsIgnoreCase(payload.getEventType()))
                && SEND.equalsIgnoreCase(payload.getActionType());
    }

    public static boolean isNewLinkForIDF(Payload payload) {
        return (AppConstants.INSURED_DATA_FETCH.equalsIgnoreCase(payload.getEventType()))
                && SEND.equalsIgnoreCase(payload.getActionType());
    }

    public static String[] getTransactionIdandEventIdFromPayload(String payload) {
        String payloadDecoded = com.mli.mpro.utils.Utility.decryptRequest(payload);
        String[] payloadArray = payloadDecoded.split(":");
        return payloadArray;

    }

    public static String generateUniqueIdforTMB() {
        SecureRandom secureRandom = new SecureRandom();
        int RANDOM_PART_LENGTH = 6;
        long timestamp = Instant.now().toEpochMilli() % 1000000;
        StringBuilder randomPart = new StringBuilder(RANDOM_PART_LENGTH);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            randomPart.append(secureRandom.nextInt(10));
        }
        return String.format("%06d%s", timestamp, randomPart.toString());
    }


    public static String getExceptionAsString(Exception exp) {
        return com.mli.mpro.location.auth.filter.Utility.getExceptionAsString(exp);
    }

    public static ResponseForOtpApi getErrorResponseForOtpApi(ResponseForOtpApi responseForOtpApi, String bankCorrelationId, String messageCode, String messageDescription){
        responseForOtpApi.setHeader(new Header("TMB", bankCorrelationId));
        responseForOtpApi.setMsgInfo(new MsgInfo(messageDescription,messageCode,STATUS_FAILURE));
        return responseForOtpApi;
    }
    public static OutputRespone getErrorResponseForGenerateLink(OutputRespone outputRespone,String messageCode,String messageDescription){
        outputRespone.setHeader(new Header("TMB", BLANK));
        outputRespone.setMsgInfo(new MsgInfo(messageDescription,messageCode,STATUS_FAILURE));
        return outputRespone;
    }

    public static long getProcessedTime(long requestedTime) {
        long processingTime = System.currentTimeMillis();
        return (processingTime - requestedTime) / 1000;
    }

}