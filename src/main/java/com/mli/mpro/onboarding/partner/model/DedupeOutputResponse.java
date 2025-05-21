package com.mli.mpro.onboarding.partner.model;

public class DedupeOutputResponse  {

    private DedupeAPIResponse response;

    public DedupeAPIResponse getResponse() {
        return response;
    }

    public void setResponse(DedupeAPIResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "DedupeOutputResponse{" +
                "response=" + response +
                '}';
    }
}
