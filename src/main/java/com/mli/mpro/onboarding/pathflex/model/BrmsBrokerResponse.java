package com.mli.mpro.onboarding.pathflex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BrmsBrokerResponse {

    @JsonProperty("Status")
    private String status;
    @JsonProperty("Output")
    private BrmsBrokerOutput output;
    @JsonProperty("rulesExecuted")
    private List<String> rulesExecuted;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BrmsBrokerOutput getOutput() {
        return output;
    }

    public void setOutput(BrmsBrokerOutput output) {
        this.output = output;
    }

    public List<String> getRulesExecuted() {
        return rulesExecuted;
    }

    public void setRulesExecuted(List<String> rulesExecuted) {
        this.rulesExecuted = rulesExecuted;
    }

    @Override
    public String toString() {
        return "BrmsBrokerResponse{" +
                "status='" + status + '\'' +
                ", output=" + output +
                ", rulesExecuted=" + rulesExecuted +
                '}';
    }
}
