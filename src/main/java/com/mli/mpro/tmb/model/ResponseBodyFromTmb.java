package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBodyFromTmb {
    @JsonProperty("Response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
