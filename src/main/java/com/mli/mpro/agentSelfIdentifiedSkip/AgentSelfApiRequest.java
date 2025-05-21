package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class AgentSelfApiRequest {

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
    @JsonProperty("dbtype")
    private String dbtype;

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }


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
                ", agentId='" + agentId + '\'' +
                ", dedupeResponse='" + dedupeResponse + '\'' +
                '}';
    }
}
