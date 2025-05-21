package com.mli.mpro.tmb.controller;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.tmb.model.*;
import com.mli.mpro.tmb.service.TmbService;
import com.mli.mpro.tmb.utility.JWEEncryptionDecryptionUtil;
import com.mli.mpro.tmb.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@RestController
@RequestMapping(path = "/locationservices/tmb")
public class TmbController {

    private static final Logger log = LoggerFactory.getLogger(TmbController.class);
    @Autowired
    JWEEncryptionDecryptionUtil JWEEncryptionDecryptionUtil;

    @Autowired
    TmbService tmbService;


    @PostMapping(path = "/encrypt")
    public String encrypt(@RequestBody RequestBodyForTmb requestBodyForTmb) throws UserHandledException {
        String decryptedString = JWEEncryptionDecryptionUtil.rsaEncrypt(requestBodyForTmb.getRequest());
        return decryptedString;
    }
    @PostMapping(path = "/decrypt")
    public String decrypt( @RequestBody RequestBodyForTmb requestBodyForTmb) throws UserHandledException {
        String decryptedString = JWEEncryptionDecryptionUtil.rsaDecrypt(requestBodyForTmb.getRequest());
        return decryptedString;
    }
    @PostMapping(path="/sendOtp")
    public ResponseForOtpApi sendOtp(@RequestBody InputRequestOtpApi inputRequest) {
        ResponseForOtpApi responseForOtpApi = new ResponseForOtpApi();
        long requestedTime = System.currentTimeMillis();
        try {
            log.info("Send otp request {} ", inputRequest.getPayload());
            responseForOtpApi = tmbService.sendOtp(inputRequest);
        } catch (Exception e) {
            log.error("Error while sending OTP {} ", Utility.getExceptionAsString(e));
            return Utility.getErrorResponseForOtpApi(responseForOtpApi, BLANK, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR);
        } finally {
            log.info("Send otp Time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responseForOtpApi;

    }

    @PostMapping(path="/verifyOtp")
    public ResponseForOtpApi verifyOtp(@RequestBody InputRequestOtpApi inputRequest) {
        ResponseForOtpApi responseForOtpApi = new ResponseForOtpApi();
        long requestedTime = System.currentTimeMillis();
        try {
            log.info("Verify otp request {} ", inputRequest.getPayload());
            responseForOtpApi = tmbService.verifyOtp(inputRequest);

        } catch (Exception e) {
            log.error("Error while verify OTP {} ", Utility.getExceptionAsString(e));
            return Utility.getErrorResponseForOtpApi(responseForOtpApi, BLANK, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR);
        } finally {
            log.info("verify otp time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responseForOtpApi;

    }


    @PostMapping(path="/generateCustomerLink")
    public OutputRespone sendOrValidateOtp(@RequestBody RequestBodyForLink requestBody) {
        OutputRespone outputRespone = new OutputRespone();
        long requestedTime = System.currentTimeMillis();
        try {
            log.info("Generate customer link request {} ", requestBody.getRequest());
            outputRespone =  tmbService.generateLinkForCustomer(requestBody.getRequest().getRequestData().getPayload());
        } catch (Exception e) {
            log.error("Error while generate customer link {} ", Utility.getExceptionAsString(e));
            return Utility.getErrorResponseForGenerateLink(outputRespone, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR);
        }finally {
            log.info("generate customer link time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return outputRespone;

    }

    @PostMapping(path="/onboardingStateStatus")
    public OutputRespone getOnboardingStatus(@RequestBody OnboardingStatusRequest onboardingStatusRequest) {
        return tmbService.getOnboardingStateStatus(onboardingStatusRequest);

    }

    @PostMapping(path="/renewalPush")
    public void sendOrValidateOtp(@RequestBody RenewalRequest renewalRequest) {
        long requestedTime = System.currentTimeMillis();
        try {
            log.info("Renewal push request transactionId {} {} ", renewalRequest.getTransactionId() , renewalRequest.getRenewalPushRequest());
            tmbService.renewalPush(renewalRequest);
        } catch (Exception e) {
            log.error("Error while Renewal push request {} ", Utility.getExceptionAsString(e));
        }finally {
            log.info("Renewal push time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }

    }


}
