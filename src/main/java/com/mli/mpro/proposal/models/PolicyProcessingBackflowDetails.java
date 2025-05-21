package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.tpp.backflow.models.Requirement;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "agentId", "policyNumber", "tppStatus", "tppSubStatus", "requirement" })
public class PolicyProcessingBackflowDetails {
    @JsonProperty("agentId")
    private String agentId;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("tppStatus")
    private String tppStatus;
    @JsonProperty("tppSubStatus")
    private String tppSubStatus;
    @JsonProperty("requirement")
    private List<Requirement> requirement = null;
    @JsonProperty("policyStatus")
    private String policyStatus;

    public PolicyProcessingBackflowDetails(String agentId, String policyNumber, String tppStatus, String tppSubStatus, List<Requirement> requirement,
	    String policyStatus) {
	super();
	this.agentId = agentId;
	this.policyNumber = policyNumber;
	this.tppStatus = tppStatus;
	this.tppSubStatus = tppSubStatus;
	this.requirement = requirement;
	this.policyStatus = policyStatus;
    }

    public PolicyProcessingBackflowDetails() {

    }

    @JsonProperty("agentId")
    public String getAgentId() {
	return agentId;
    }

    @JsonProperty("agentId")
    public void setAgentId(String agentId) {
	this.agentId = agentId;
    }

    @JsonProperty("policyNumber")
    public String getPolicyNumber() {
	return policyNumber;
    }

    @JsonProperty("policyNumber")
    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    @JsonProperty("tppStatus")
    public String getTppStatus() {
	return tppStatus;
    }

    @JsonProperty("tppStatus")
    public void setTppStatus(String tppStatus) {
	this.tppStatus = tppStatus;
    }

    @JsonProperty("tppSubStatus")
    public String getTppSubStatus() {
	return tppSubStatus;
    }

    @JsonProperty("tppSubStatus")
    public void setTppSubStatus(String tppSubStatus) {
	this.tppSubStatus = tppSubStatus;
    }

    @JsonProperty("requirement")
    public List<Requirement> getRequirement() {
	return requirement;
    }

    @JsonProperty("requirement")
    public void setRequirement(List<Requirement> requirement) {
	this.requirement = requirement;
    }

    @JsonProperty("policyStatus")
    public String getPolicyStatus() {
	return policyStatus;
    }

    @JsonProperty("policyStatus")
    public void setPolicyStatus(String policyStatus) {
	this.policyStatus = policyStatus;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PolicyProcessingBackflowDetails [agentId=" + agentId + ", policyNumber=" + policyNumber + ", tppStatus=" + tppStatus + ", tppSubStatus="
		+ tppSubStatus + ", requirement=" + requirement + ", policyStatus=" + policyStatus + "]";
    }

}
