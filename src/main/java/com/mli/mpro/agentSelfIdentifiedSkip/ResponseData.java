package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseData {

    @JsonProperty("payload")
    public AgentSelfIdentifiedSkipPayload payload;


    public AgentSelfIdentifiedSkipPayload getPayload() {
        return payload;
    }

  
    public void setPayload(AgentSelfIdentifiedSkipPayload payload) {
        this.payload = payload;
    }


}
