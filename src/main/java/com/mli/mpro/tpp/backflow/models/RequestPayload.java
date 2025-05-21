package com.mli.mpro.tpp.backflow.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "agentId", "policyNumber", "tppStatus", "tppSubStatus", "requirement" })
public class RequestPayload {

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

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestPayload() {
    }

    /**
     * 
     * @param tppStatus
     * @param tppSubStatus
     * @param requirement
     * @param agentId
     * @param policyNumber
     */
    public RequestPayload(String agentId, String policyNumber, String tppStatus, String tppSubStatus, List<Requirement> requirement) {
	super();
	this.agentId = agentId;
	this.policyNumber = policyNumber;
	this.tppStatus = tppStatus;
	this.tppSubStatus = tppSubStatus;
	this.requirement = requirement;
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

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "RequestPayload [agentId=" + agentId + ", policyNumber=" + policyNumber + ", tppStatus=" + tppStatus + ", tppSubStatus=" + tppSubStatus
		+ ", requirement=" + requirement + "]";
    }
}