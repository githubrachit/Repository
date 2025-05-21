package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AgentSelfIdentifiedSkipPayload {
    @JsonProperty("agentId")
    public String agentId;

    @JsonProperty("policies")
    public List<PolicyInfo> policies;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public List<PolicyInfo> getPolicies() {
        return policies;
    }

    public void setPolicies(List<PolicyInfo> policies) {
        this.policies = policies;
    }
}
