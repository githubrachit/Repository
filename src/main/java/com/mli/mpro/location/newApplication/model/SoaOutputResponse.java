package com.mli.mpro.location.newApplication.model;

public class SoaOutputResponse {

    private SoaResponse response;

    public SoaOutputResponse() {
    }

    public SoaOutputResponse(SoaResponse response) {
        this.response = response;
    }

    public SoaResponse getResponse() {
        return response;
    }

    public void setResponse(SoaResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SoaOutputResponse{" +
                "response=" + response +
                '}';
    }
}
