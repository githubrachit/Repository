package com.mli.mpro.onboarding.partner.model;

public class DedupeResponse {
    private DedupeSOAResponse response;

    public DedupeSOAResponse getResponse() {
        return response;
    }

    public void setResponse(DedupeSOAResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "DedupeSOAResponse{" +
                "response=" + response +
                '}';
    }
}
