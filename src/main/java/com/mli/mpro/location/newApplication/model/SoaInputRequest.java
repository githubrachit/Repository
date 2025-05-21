package com.mli.mpro.location.newApplication.model;

public class SoaInputRequest {
    private SoaRequest request;

    public SoaInputRequest() {
    }

    public SoaInputRequest(SoaRequest request) {
        this.request = request;
    }

    public SoaRequest getRequest() {
        return request;
    }

    public void setRequest(SoaRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SoaInputRequest{" +
                "request=" + request +
                '}';
    }
}
