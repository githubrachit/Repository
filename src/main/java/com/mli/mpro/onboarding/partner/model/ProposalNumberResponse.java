package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposalNumberResponse {
    @JsonProperty("response")
    private ProposalNumberSOAResponse response;

    public ProposalNumberSOAResponse getResponse() {
        return response;
    }

    public void setResponse(ProposalNumberSOAResponse response) {
        this.response = response;
    }
}
