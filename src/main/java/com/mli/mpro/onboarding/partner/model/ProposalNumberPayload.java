package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalNumberPayload {

    @JsonProperty("channel")
    private String channel;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("familyType")
    private String familyType;
    @JsonProperty("formType")
    private String formType;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ProposalNumberSOARequestPayload{" +
                "channel='" + channel + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", familyType='" + familyType + '\'' +
                ", formType='" + formType + '\'' +
                '}';
    }
}
