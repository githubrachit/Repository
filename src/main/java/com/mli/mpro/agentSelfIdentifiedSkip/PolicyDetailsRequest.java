package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PolicyDetailsRequest {

    @JsonProperty("policies")
    public List<Policies> policies;
    @JsonProperty("remark")
    public String remark;
    @JsonProperty("updateduserId")
    public String updateduserId;

    @JsonProperty("transactionId")
    public List<String> transactionId;
    @JsonProperty("policyNumbers")
    public List<String> policyNumbers;

    public List<String> getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(List<String> transactionId) {
        this.transactionId = transactionId;
    }

    public List<String> getPolicyNumbers() {
        return policyNumbers;
    }

    public void setPolicyNumbers(List<String> policyNumbers) {
        this.policyNumbers = policyNumbers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Policies> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policies> policies) {
        this.policies = policies;
    }

    public String getUpdateduserId() {
        return updateduserId;
    }

    public void setUpdateduserId(String updateduserId) {
        this.updateduserId = updateduserId;
    }

    public PolicyDetailsRequest(List<Policies> policies, String remark, String updateduserId, List<String> transactionId, List<String> policyNumbers) {
        this.policies = policies;
        this.remark = remark;
        this.updateduserId = updateduserId;
        this.transactionId = transactionId;
        this.policyNumbers = policyNumbers;
    }

    public PolicyDetailsRequest() {
    }
}

