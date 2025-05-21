package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuredClientIdsRelated {
    @JsonProperty("distinctPolicyInsureds")
    private String distinctPolicyInsureds;

    public String getDistinctPolicyInsureds() {
        return distinctPolicyInsureds;
    }

    public void setDistinctPolicyInsureds(String distinctPolicyInsureds) {
        this.distinctPolicyInsureds = distinctPolicyInsureds;
    }

    @Override
    public String toString() {
        return "InsuredClientIdsRelated{" +
                "distinctPolicyInsureds='" + distinctPolicyInsureds + '\'' +
                '}';
    }
}
