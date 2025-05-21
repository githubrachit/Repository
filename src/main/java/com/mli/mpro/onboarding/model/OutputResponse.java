package com.mli.mpro.onboarding.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputResponse {
    
    @JsonProperty("response")
    private Response policyStatusResponse;

    public OutputResponse() {
       
    }

    public OutputResponse(Response policyStatusResponse) {
        this.policyStatusResponse = policyStatusResponse;
    }

    public Response getPolicyStatusResponse() {
        return policyStatusResponse;
    }

    public void setPolicyStatusResponse(Response policyStatusResponse) {
        this.policyStatusResponse = policyStatusResponse;
    }

    @Override
    public String toString() {
        return "OutputResponse [policyStatusResponse=" + policyStatusResponse + "]";
    } 
    
    
}
