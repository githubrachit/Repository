package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.common.models.Metadata;

public class AgentSelfIdentifiedSkipRequest {
    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("requestData")
    public RequestData requestData;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public AgentSelfIdentifiedSkipRequest() {
    }

    public AgentSelfIdentifiedSkipRequest(Metadata metadata, RequestData requestData) {
        super();
        this.metadata = metadata;
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "AgentSelfIdentifiedSkipRequest{" +
                "metadata=" + metadata +
                ", requestData=" + requestData +
                '}';
    }
}
