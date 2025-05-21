package com.mli.mpro.location.models.soaCloudModels;

import com.mli.mpro.location.models.mfaOauthAgent360.TokenPayload;

public class OnboardingJ2OtpTokenPayload extends TokenPayload {
    private String transactionId;

    public OnboardingJ2OtpTokenPayload(String source, String user, String subject, String transactionId) {
        super(source, user, subject);
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "OnboardingJ2OtpTokenPayload{" +
                "transactionId='" + transactionId + '\'' +
                '}';
    }
}
