package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentFraudCheckDetails {
    @JsonProperty("source")
    private String source;

    @JsonProperty("panMatched")
    private String panMatched;

    @JsonProperty("dobMatched")
    private String dobMatched;

    @JsonProperty("emailMatched")
    private String emailMatched;

    @JsonProperty("mobileMatched")
    private String mobileMatched;

    @JsonProperty("isAgentSelf")
    private String isAgentSelf;

    @JsonProperty("agentFraudIdentified")
    private String agentFraudIdentified;

    @JsonProperty("agentFraudCheckSkip")
    private String agentFraudCheckSkip;

    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    @JsonProperty("responseTimestamp")
    private String responseTimestamp;

    @JsonProperty("serviceStatus")
    private String serviceStatus;

    @JsonProperty("updatedByUserId")
    private String updatedByUserId;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("updatedTime")
    private String updatedTime;

    @JsonProperty("auditRequestId")
    private String auditRequestId;

    public String getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(String updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPanMatched() {
        return panMatched;
    }

    public void setPanMatched(String panMatched) {
        this.panMatched = panMatched;
    }

    public String getDobMatched() {
        return dobMatched;
    }

    public void setDobMatched(String dobMatched) {
        this.dobMatched = dobMatched;
    }

    public String getEmailMatched() {
        return emailMatched;
    }

    public void setEmailMatched(String emailMatched) {
        this.emailMatched = emailMatched;
    }

    public String getMobileMatched() {
        return mobileMatched;
    }

    public void setMobileMatched(String mobileMatched) {
        this.mobileMatched = mobileMatched;
    }

    public String getIsAgentSelf() {
        return isAgentSelf;
    }

    public void setIsAgentSelf(String isAgentSelf) {
        this.isAgentSelf = isAgentSelf;
    }

    public String getAgentFraudIdentified() {
        return agentFraudIdentified;
    }

    public void setAgentFraudIdentified(String agentFraudIdentified) {
        this.agentFraudIdentified = agentFraudIdentified;
    }

    public String getAgentFraudCheckSkip() {
        return agentFraudCheckSkip;
    }

    public void setAgentFraudCheckSkip(String agentFraudCheckSkip) {
        this.agentFraudCheckSkip = agentFraudCheckSkip;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getAuditRequestId() {
        return auditRequestId;
    }

    public void setAuditRequestId(String auditRequestId) {
        this.auditRequestId = auditRequestId;
    }

}
