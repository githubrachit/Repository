package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyInfo {
    @JsonProperty("policydetails")
    public PolicyDetails policydetails;
    @JsonProperty("agentFraudCheckDetails")
    public AgentFraudCheckDetails agentFraudCheckDetails;
    @JsonProperty("agentSelfSoaApiRequest")
    public AgentSelfApiRequest agentSelfSoaApiRequest;
    @JsonProperty("agentSelfSoaApiResponse")
    public AgentSelfSoaApiResponse agentSelfSoaApiResponse;



    public AgentSelfApiRequest getAgentSelfSoaApiRequest() {
        return agentSelfSoaApiRequest;
    }

    public void setAgentSelfSoaApiRequest(AgentSelfApiRequest agentSelfSoaApiRequest) {
        this.agentSelfSoaApiRequest = agentSelfSoaApiRequest;
    }

    public AgentSelfSoaApiResponse getAgentSelfSoaApiResponse() {
        return agentSelfSoaApiResponse;
    }

    public void setAgentSelfSoaApiResponse(AgentSelfSoaApiResponse agentSelfSoaApiResponse) {
        this.agentSelfSoaApiResponse = agentSelfSoaApiResponse;
    }

    public PolicyDetails getPolicydetails() {
        return policydetails;
    }

    public void setPolicydetails(PolicyDetails policydetails) {
        this.policydetails = policydetails;
    }

    public AgentFraudCheckDetails getAgentFraudCheckDetails() {
        return agentFraudCheckDetails;
    }

    public void setAgentFraudCheckDetails(AgentFraudCheckDetails agentFraudCheckDetails) {
        this.agentFraudCheckDetails = agentFraudCheckDetails;
    }

    @Override
    public String toString() {
        return "PolicyInfo{" +
                "agentSelfSoaApiRequest=" + agentSelfSoaApiRequest +
                ", agentSelfSoaApiResponse=" + agentSelfSoaApiResponse +
                ", policydetails=" + policydetails +
                ", agentFraudCheckDetails=" + agentFraudCheckDetails +
                '}';
    }
}
