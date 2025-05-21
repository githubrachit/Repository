package com.mli.mpro.agent.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.List;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPayload {
    @JsonProperty("agentId")
    private String agentId;
    @JsonProperty("searchBy")
    private String searchBy = "";
    @JsonProperty("searchValue")
    private String searchValue = "";
    @JsonProperty("clientId")
    private String clientId;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNo")
    private String panNo;
    @JsonProperty("services")
    private List<AgentServices> services;
    @JsonProperty("policyNumber")
    private String policyNumber;

    public RequestPayload()
    {}    

    public RequestPayload(String agentId, List<AgentServices> services) {
	super();
	this.agentId = agentId;
	this.services = services;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public List<AgentServices> getServices() {
        return services;
    }

    public void setServices(List<AgentServices> services) {
        this.services = services;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "RequestPayload [agentId=" + agentId + ", dob=" + dob + ", panNo=" + panNo +
            ", services=" + services + ", policyNumber=" + policyNumber +"]";
    }
}
