package com.mli.mpro.onboarding.partner.model;

public class ReplacementOutputResponse {
    private ReplacementSaleAPIResponse response;

    public ReplacementSaleAPIResponse getResponse() {
        return response;
    }

    public void setResponse(ReplacementSaleAPIResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ReplacementOutputResponse{" +
                "response=" + response +
                '}';
    }
}
