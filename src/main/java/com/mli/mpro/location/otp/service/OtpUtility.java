package com.mli.mpro.location.otp.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.emailsms.service.EmailSmsService;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class OtpUtility {

    @Autowired
    private EmailSmsService emailSmsService;
    @Autowired
    private RedisTemplate redisTemplate;
    public String updateAndSend(String deliveryMethod, com.mli.mpro.sms.models.RequestPayload smsPayload, com.mli.mpro.email.models.RequestPayload emailPayload, ProposalDetails proposalDetails) throws UserHandledException {

        switch (deliveryMethod){

            case "SMS" :
                return emailSmsService.sendSMS(smsPayload, proposalDetails);

            default:
                return "Fail";
        }
    }

    private static final SecureRandom random = new SecureRandom();
    public static String generateRandomNumber(int lengthOfOTP) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < lengthOfOTP; i++) {
            int randomNumber =random.nextInt(9);
            otp.append(randomNumber);
        }
        return otp.toString();
    }

    public String encryptOtp(String otp, String oasOtpSecretKey) {

        String encryptedOtp = null;
        try {
            encryptedOtp = EncryptionDecryptionUtil.encrypt(otp, Arrays.toString(Base64.getDecoder().decode(oasOtpSecretKey)));
        }
        catch (Exception exception){
        }
        return encryptedOtp;
    }
}
