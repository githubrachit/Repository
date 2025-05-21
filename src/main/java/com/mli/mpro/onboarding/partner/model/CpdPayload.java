package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CpdPayload {

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("planCode")
    private String planCode;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    @Override
    public String toString() {
        return "CpdPayload{" +
                "clientId='" + clientId + '\'' +
                "planCode='" + planCode + '\'' +
                '}';
    }
}
