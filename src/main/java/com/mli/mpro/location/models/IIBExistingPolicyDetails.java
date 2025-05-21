package com.mli.mpro.location.models;

import java.util.List;

public class IIBExistingPolicyDetails {
    private String partyType;
    private List<ExistingPolicyDetails> existingPolicyDetails;

    public String getPartyType() { return partyType; }

    public void setPartyType(String partyType) { this.partyType = partyType; }

    public List<ExistingPolicyDetails> getExistingPolicyDetails() {
        return existingPolicyDetails;
    }

    public void setExistingPolicyDetails(List<ExistingPolicyDetails> existingPolicyDetails) {
        this.existingPolicyDetails = existingPolicyDetails;
    }

    @Override
    public String toString() {
        return "IIBExistingPolicyDetails{" +
                "existingPolicyDetails=" + existingPolicyDetails +
                '}';
    }
}
