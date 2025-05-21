package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviousPolicyDetails {

    @JsonProperty("previousPolicy")
    private String previousPolicy;

    @JsonProperty("policyStatus")
    private String policyStatus;

    @JsonProperty("policyPlanCode")
    private String policyPlanCode;

    @JsonProperty("relationshipToPolicyOrClient")
    private String relationshipToPolicyOrClient;

    public String getPreviousPolicy() {
        return previousPolicy;
    }

    public void setPreviousPolicy(String previousPolicy) {
        this.previousPolicy = previousPolicy;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getPolicyPlanCode() {
        return policyPlanCode;
    }

    public void setPolicyPlanCode(String policyPlanCode) {
        this.policyPlanCode = policyPlanCode;
    }

    public String getRelationshipToPolicyOrClient() {
        return relationshipToPolicyOrClient;
    }

    public void setRelationshipToPolicyOrClient(String relationshipToPolicyOrClient) {
        this.relationshipToPolicyOrClient = relationshipToPolicyOrClient;
    }

    @Override
    public String toString() {
        return "PreviousPolicyDetails{" +
                "previousPolicy='" + previousPolicy + '\'' +
                ", policyStatus='" + policyStatus + '\'' +
                ", policyPlanCode='" + policyPlanCode + '\'' +
                ", relationshipToPolicyOrClient='" + relationshipToPolicyOrClient + '\'' +
                '}';
    }
}
