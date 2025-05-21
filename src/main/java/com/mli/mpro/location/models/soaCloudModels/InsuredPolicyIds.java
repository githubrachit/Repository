package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuredPolicyIds {
    @JsonProperty("policyIdsForInsured")
    private Object policyIdsForInsured;

    public Object getPolicyIdsForInsured() {
        return policyIdsForInsured;
    }

    public void setPolicyIdsForInsured(Object policyIdsForInsured) {
        this.policyIdsForInsured = policyIdsForInsured;
    }

    @Override
    public String toString() {
        return "InsuredPolicyIds{" +
                "policyIdsForInsured=" + policyIdsForInsured +
                '}';
    }
}
