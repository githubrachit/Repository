package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Policies {
    @JsonProperty("policyNumber")
    public String policyNumber;
    @JsonProperty("transactionId")
    public String transactionId;
    @JsonProperty("agentFraudCheckSkip")
    public String agentFraudCheckSkip;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAgentFraudCheckSkip() {
        return agentFraudCheckSkip;
    }

    public void setAgentFraudCheckSkip(String agentFraudCheckSkip) {
        this.agentFraudCheckSkip = agentFraudCheckSkip;
    }

    public Policies(String policyNumber, String transactionId, String agentFraudCheckSkip) {
        this.policyNumber = policyNumber;
        this.transactionId = transactionId;
        this.agentFraudCheckSkip = agentFraudCheckSkip;
    }

    public Policies() {
    }

    @Override
    public String toString() {
        return "Policies{" +
                "policyNumbers='" + policyNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", agentFraudCheckSkip='" + agentFraudCheckSkip + '\'' +
                '}';
    }
}
