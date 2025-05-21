package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AgentSelfRequestPayload {

    @JsonProperty("transactionId")
    private String transactionId;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("email")
    private String email;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("entityType")
    private String entityType;

    public String getDedupeResponse() {
        return dedupeResponse;
    }

    public void setDedupeResponse(String dedupeResponse) {
        this.dedupeResponse = dedupeResponse;
    }

    @JsonProperty("agentId")
    private String agentId;
    @JsonProperty("dedupeResponse")
    private String dedupeResponse;

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgentSelfRequestPayload{" +
                "transactionId='" + transactionId + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", entityType='" + entityType + '\'' +
                ", agentId='" + agentId + '\'' +
                ", dedupeResponse='" + dedupeResponse + '\'' +
                '}';
    }
}
