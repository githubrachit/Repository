package com.mli.mpro.location.models;

import javax.validation.constraints.Pattern;

public class SarthiRequestPayload {

    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "AgentId must be exactly 6 alphanumeric characters")
    private String agentId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "SarthiRequestPayload{" +
                "agentId='" + agentId + '\'' +
                '}';
    }
}
