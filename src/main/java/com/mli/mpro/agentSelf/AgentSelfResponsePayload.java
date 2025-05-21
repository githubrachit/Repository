package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

public class AgentSelfResponsePayload {

    @JsonProperty("isAgentSelf")
    private String isAgentSelf;
    @JsonProperty("source")
    private String source;
    @JsonProperty("agentFraudIdentified")
    private String agentFraudIdentified;
    @JsonProperty("agentFraudCheckSkip")
    private String agentFraudCheckSkip;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panMatched")
    private String panMatched;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileMatched")
    private String mobileMatched;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailMatched")
    private String emailMatched;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dobMatched")
    private String dobMatched;
    @JsonProperty("requestTimestamp")
    private String requestTimestamp;
    @JsonProperty("responseTimestamp")
    private String responseTimestamp;
    @JsonProperty("serviceStatus")
    private String serviceStatus;
    @JsonProperty("auditRequestId")
    private String auditRequestId;

    public String getAuditRequestId() {
        return auditRequestId;
    }

    public void setAuditRequestId(String auditRequestId) {
        this.auditRequestId = auditRequestId;
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

    public String getIsAgentSelf() {
        return isAgentSelf;
    }

    public void setIsAgentSelf(String isAgentSelf) {
        this.isAgentSelf = isAgentSelf;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getPanMatched() {
        return panMatched;
    }

    public void setPanMatched(String panMatched) {
        this.panMatched = panMatched;
    }

    public String getMobileMatched() {
        return mobileMatched;
    }

    public void setMobileMatched(String mobileMatched) {
        this.mobileMatched = mobileMatched;
    }

    public String getEmailMatched() {
        return emailMatched;
    }

    public void setEmailMatched(String emailMatched) {
        this.emailMatched = emailMatched;
    }

    public String getDobMatched() {
        return dobMatched;
    }

    public void setDobMatched(String dobMatched) {
        this.dobMatched = dobMatched;
    }

    @Override
    public String toString() {
        return "AgentSelfResponsePayload{" +
                "isAgentSelf='" + isAgentSelf + '\'' +
                ", source='" + source + '\'' +
                ", agentFraudIdentified='" + agentFraudIdentified + '\'' +
                ", agentFraudCheckSkip='" + agentFraudCheckSkip + '\'' +
                ", panMatched='" + panMatched + '\'' +
                ", mobileMatched='" + mobileMatched + '\'' +
                ", emailMatched='" + emailMatched + '\'' +
                ", dobMatched='" + dobMatched + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", responseTimestamp='" + responseTimestamp + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", auditRequestId='" + auditRequestId + '\'' +
                '}';
    }
}
