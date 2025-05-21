package com.mli.mpro.onboarding.partner.model;

public class CpdSoaResponse {

    private CpdResponse response;

    public CpdResponse getResponse() {
        return response;
    }

    public void setResponse(CpdResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "CpdSoaResponse{" +
                "response=" + response +
                '}';
    }
}
