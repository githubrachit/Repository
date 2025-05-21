package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CpdResponsePayload {

    @JsonProperty("currentplanType")
    private String currentPlanType;
    private List<PolicyDetails> policyDetails;

    public String getCurrentPlanType() {
        return currentPlanType;
    }

    public void setCurrentPlanType(String currentPlanType) {
        this.currentPlanType = currentPlanType;
    }

    public List<PolicyDetails> getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(List<PolicyDetails> policyDetails) {
        this.policyDetails = policyDetails;
    }

    @Override
    public String toString() {
        return "CpdResponsePayload{" +
                "currentPlanType='" + currentPlanType + '\'' +
                ", policyDetails=" + policyDetails +
                '}';
    }
}
