package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentRequestPayload {

    @JsonProperty("policyDetails")
    public PolicyDetailsRequest policyDetails;
    public PolicyDetailsRequest getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(PolicyDetailsRequest policyDetails) {
        this.policyDetails = policyDetails;
    }

    public AgentRequestPayload() {
    }

    public AgentRequestPayload(PolicyDetailsRequest policyDetails) {
        super();
        this.policyDetails = policyDetails;
    }

    @Override
    public String toString() {
        return "AgentRequestPayload{" +
                "policyDetails=" + policyDetails +
                '}';
    }
}
