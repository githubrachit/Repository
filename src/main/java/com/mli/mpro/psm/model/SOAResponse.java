package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SOAResponse {

    @JsonProperty("response")
    private Response psmResponse;

    public Response getPsmResponse() {
        return psmResponse;
    }

    public void setPsmResponse(Response psmResponse) {
        this.psmResponse = psmResponse;
    }

    @Override
    public String toString() {
        return "SOAResponse{" + "psmResponse=" + psmResponse + '}';
    }
}

