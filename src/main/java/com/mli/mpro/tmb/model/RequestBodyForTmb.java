package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBodyForTmb {
    @JsonProperty("Request")
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
