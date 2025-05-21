package com.mli.mpro.configuration.models;

public class SellerSaveRequest {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SellerSaveRequest{" +
                "request=" + request +
                '}';
    }
}
