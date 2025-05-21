package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestPayload {

    @JsonProperty("request")
    public AgentSelfIdentifiedSkipRequest request;
    public AgentSelfIdentifiedSkipRequest getRequest() {
        return request;
    }

    public void setRequest(AgentSelfIdentifiedSkipRequest request) {
        this.request = request;
    }

    public RequestPayload() {
    }

    public RequestPayload(AgentSelfIdentifiedSkipRequest request) {
        super();
        this.request = request;
    }

    @Override
    public String toString() {
        return "RequestPayload{" +
                "request=" + request +
                '}';
    }
}
