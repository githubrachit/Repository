package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {

    @JsonProperty("policyNumber")
    private String policyNumber;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
}
