package com.mli.mpro.onboarding.partner.model;

public class CpdSoaRequest {
    private CpdRequest request;

    public CpdRequest getRequest() {
        return request;
    }

    public void setRequest(CpdRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "CpdSoaRequest{" +
                "request=" + request +
                '}';
    }
}
