package com.mli.mpro.location.panDOBVerification.model;

public class PanDobRequest {

    private InputRequest request;

    public PanDobRequest() {
    }

    public PanDobRequest(InputRequest request) {
        this.request = request;
    }

    public InputRequest getRequest() {
        return request;
    }

    public void setRequest(InputRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "PanDobRequest{" +
                "request=" + request +
                '}';
    }
}
