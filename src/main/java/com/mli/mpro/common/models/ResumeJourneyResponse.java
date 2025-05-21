package com.mli.mpro.common.models;

/**
 * ResumeJourneyResponse response contains two fields:
 * long transactionId
 * String redirectionUrl
 */
public class ResumeJourneyResponse {
    private long transactionId;
    private String redirectionUrl;

    public ResumeJourneyResponse(long transactionId, String redirectionUrl) {
        this.transactionId = transactionId;
        this.redirectionUrl = redirectionUrl;
    }

    public ResumeJourneyResponse() {
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }


    @Override
    public String toString() {
        return "ResumeJourneyResponse [redirectionUrl=" + redirectionUrl + ", transactionId=" + transactionId + "]";
    }
}
