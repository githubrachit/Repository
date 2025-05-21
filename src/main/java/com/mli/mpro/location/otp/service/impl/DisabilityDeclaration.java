package com.mli.mpro.location.otp.service.impl;

import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.emailsms.service.EmailSmsService;
import com.mli.mpro.location.otp.models.Payload;
import com.mli.mpro.location.otp.service.OtpFlowService;
import com.mli.mpro.location.otp.service.OtpUtility;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.sms.models.RequestPayload;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DisabilityDeclaration implements OtpFlowService {
    private final EmailSmsService emailSmsService;
    private OtpUtility otpUtility;
    private OauthTokenRepository oauthTokenRepository;
    private static final Logger logger= LoggerFactory.getLogger(DisabilityDeclaration.class);


    @Autowired
    public DisabilityDeclaration(EmailSmsService emailSmsService, OtpUtility otpUtility, OauthTokenRepository oauthTokenRepository) {
        this.emailSmsService = emailSmsService;
        this.otpUtility = otpUtility;
        this.oauthTokenRepository = oauthTokenRepository;
    }

    @Override
    public String execute(Payload payload, String otp, String proposerName) {

        ProposalDetails proposalDetails = new ProposalDetails();
        com.mli.mpro.email.models.RequestPayload emailPayload = new com.mli.mpro.email.models.RequestPayload();
        com.mli.mpro.sms.models.RequestPayload smsPayload = new com.mli.mpro.sms.models.RequestPayload();

        if ("SMS".equalsIgnoreCase(payload.getOtpDeliveryMethod())) {
            setSmsPayload(smsPayload, payload, otp, proposerName);
        }
        try {
            logger.info("Sending sms with OTP for transactionId {}",payload.getTransactionId());
            return otpUtility.updateAndSend(payload.getOtpDeliveryMethod(), smsPayload, emailPayload, proposalDetails);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SendOTP exception occured while sending sms for transactionId {}", payload.getTransactionId());
        }
        return "Fail";
    }
    @Override
    public void setSmsPayload(RequestPayload smsPayload, Object requestObject, String otp, String proposerName) {
        String messageBody = AppConstants.DECLARANT_OTP_SMS_BODY;
        Payload payload = (Payload) requestObject;
        smsPayload.setCustomerName(payload.getDisabilityDeclaration().getCustomerName());
        smsPayload.setPolicyNumber(payload.getPolicyNumber());
        smsPayload.setMessageTo(payload.getDisabilityDeclaration().getMobileNo());
        smsPayload.setType(payload.getOtpDeliveryMethod());
        smsPayload.setTransactionNumber(payload.getTransactionId());

        if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NEW_SMS_API))){
            messageBody = Utility.replaceEncodedAmpersand(messageBody);
        }
        smsPayload.setMessageText(messageBody.replace("[Declarant Name]", payload.getDisabilityDeclaration().getCustomerName())
                .replace("<Proposal number>", payload.getPolicyNumber()).replace("<Customer Name>", proposerName)
                .replace("<otp>", otp));
    }
}
