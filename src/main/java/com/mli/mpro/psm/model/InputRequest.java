package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class InputRequest {

    @JsonProperty("request")
    private PsmRequest psmRequest;


    public InputRequest(PsmRequest psmRequest) {
        this.psmRequest = psmRequest;
    }

    public InputRequest() {

    }


    public PsmRequest getRequest() {
        return psmRequest;
    }


    public void setRequest(PsmRequest psmRequest) {
        this.psmRequest = psmRequest;
    }


    @Override
    public String toString() {
        return "InputRequest{" +
                "psmRequest=" + psmRequest +
                '}';
    }
}
