package com.mli.mpro.onboarding.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SOAResponse {
    
    @JsonProperty("response")
    private Response response;

    public SOAResponse() {
        
    }

    public SOAResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    
    
}
