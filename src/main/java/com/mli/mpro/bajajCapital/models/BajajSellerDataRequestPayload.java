package com.mli.mpro.bajajCapital.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BajajSellerDataRequestPayload {

    @JsonProperty("agentId")
    private String agentId;

    @JsonProperty("sellerType")
    private String sellerType;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    @Override
    public String toString() {
        return "BajajSellerDataRequestPayload{" +
                "agentId='" + agentId + '\'' +
                ", sellerType='" + sellerType + '\'' +
                '}';
    }
}
