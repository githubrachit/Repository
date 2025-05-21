package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class CommissionShare {

    @JsonProperty("agentId")
    private String agentId;
    @Sensitive(MaskType.NAME)
    @JsonProperty("agentName")
    private String agentName;
    @JsonProperty("signature")
    private String signature;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("percentage")
    private int percentage;

    public CommissionShare() {
    }

    public CommissionShare(String agentId, String agentName,String signature, int percentage) {
        super();
        this.agentId = agentId;
        this.agentName = agentName;
        this.signature = signature;
        this.percentage = percentage;
    }


    public CommissionShare(String agentId, int percentage) {
	super();
	this.agentId = agentId;
	this.percentage = percentage;
    }

    public String getAgentId() {
	return agentId;
    }

    public void setAgentId(String agentId) {
	this.agentId = agentId;
    }

    public int getPercentage() {
	return percentage;
    }

    public void setPercentage(int percentage) {
	this.percentage = percentage;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSignature(){return signature;}

    public void setSignature(String signature)
    {
        this.signature=signature;
    }
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "CommissionShare [agentId=" + agentId + ", agentName=" + agentName + ", signature=" + signature + ", percentage=" + percentage + "]";    }
}
