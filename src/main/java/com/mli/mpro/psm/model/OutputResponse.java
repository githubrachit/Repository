package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class OutputResponse {

    @JsonProperty("response")
    private PsmResponse psmResponse;


    public OutputResponse() {

    }
    public OutputResponse(PsmResponse psmResponse) {
        this.psmResponse = psmResponse;
    }


    public PsmResponse getPsmResponse() {
        return psmResponse;
    }

    public void setPsmResponse(PsmResponse psmResponse) {
        this.psmResponse = psmResponse;
    }

    @Override
    public String toString() {
        return "OutputResponse{" +
                "psmResponse=" + psmResponse +
                '}';
    }
}

