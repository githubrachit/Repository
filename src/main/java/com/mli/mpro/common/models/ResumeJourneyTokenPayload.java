package com.mli.mpro.common.models;

import com.mli.mpro.location.models.mfaOauthAgent360.TokenPayload;

public class ResumeJourneyTokenPayload extends TokenPayload {

    private String transactionId;

    public ResumeJourneyTokenPayload(String transactionId, String source, String user, String subject) {
        super(source, user, subject);
        this.transactionId = transactionId;
    }

    public ResumeJourneyTokenPayload(String source, String user, String subject) {
        super(source, user, subject);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "ResumeJourneyTokenPayload{" +
                "transactionId='" + transactionId + '\'' +
                '}';
    }
}
