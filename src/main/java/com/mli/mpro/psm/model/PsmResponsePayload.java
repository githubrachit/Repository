package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PsmResponsePayload {

    @JsonProperty("planName")
    private String planName;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }


    @Override
    public String toString() {
        return "PsmResponsePayload{" + "planName='" + planName + '\'' + '}';
    }
}
