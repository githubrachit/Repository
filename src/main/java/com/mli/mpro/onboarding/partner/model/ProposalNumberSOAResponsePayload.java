package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalNumberSOAResponsePayload extends SOAResponsePayload{


    @JsonProperty("channel")
    private String channel;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("familyType")
    private String familyType;
    @JsonProperty("formType")
    private String formType;
    @JsonProperty("proposalNo")
    private List<Long> proposalNo;

    public List<Long> getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(List<Long> proposalNo) {
        this.proposalNo = proposalNo;
    }

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
        return "ProposalNumberSOAResponsePayload{" +
                "channel=" + channel +
                "transactionId=" + transactionId +
                "familyType=" + familyType +
                "formType=" + formType +
                "proposalNo=" + proposalNo +
                '}';
    }
}
