package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestData {
    @JsonProperty("requestPayload")
    public AgentRequestPayload requestPayload;

    public AgentRequestPayload getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(AgentRequestPayload requestPayload) {
        this.requestPayload = requestPayload;
    }

    public RequestData() {
    }

    public RequestData(AgentRequestPayload requestPayload) {
        super();
        this.requestPayload = requestPayload;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "requestPayload=" + requestPayload +
                '}';
    }
}
