package com.mli.mpro.onboarding.medicalGridDetials.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputResponse {
    @JsonProperty("response")
    private Response response;

    public OutputResponse() {
       
    }

    public OutputResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "OutputResponse [response=" + response + "]";
    }
    
}
