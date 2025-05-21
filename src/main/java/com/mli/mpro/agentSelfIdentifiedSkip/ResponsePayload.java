package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponsePayload {

    @JsonProperty("response")
    public AgentSelfIdentifiedSkipResponse response;

    public AgentSelfIdentifiedSkipResponse getResponse() {
        return response;
    }

    public void setResponse(AgentSelfIdentifiedSkipResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "response=" + response +
                '}';
    }
}
