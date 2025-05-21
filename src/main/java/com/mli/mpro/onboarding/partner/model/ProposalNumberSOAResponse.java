package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProposalNumberSOAResponse extends SOAResponse{

    @JsonProperty("payload")
    private List<ProposalNumberSOAResponsePayload> payload;

    public List<ProposalNumberSOAResponsePayload> getPayload() {
        return payload;
    }

    public void setPayload(List<ProposalNumberSOAResponsePayload> payload) {
        this.payload = payload;
    }
}
