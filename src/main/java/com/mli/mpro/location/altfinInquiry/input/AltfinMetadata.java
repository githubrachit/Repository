package com.mli.mpro.location.altfinInquiry.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AltfinMetadata {

    @JsonProperty("X-Correlation-ID")
    private String correlationId;
    @JsonProperty("X-App-ID")
    private String appId;
    @JsonProperty("proposalNumber")
    private String proposalNumber;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AltfinMetadata{" +
                "correlationId='" + correlationId + '\'' +
                ", appId='" + appId + '\'' +
                ", proposalNumber='" + proposalNumber + '\'' +
                '}';
    }
}
