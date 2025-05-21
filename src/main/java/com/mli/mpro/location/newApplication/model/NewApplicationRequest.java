package com.mli.mpro.location.newApplication.model;

public class NewApplicationRequest {

    private Request request;

    public NewApplicationRequest() {
    }

    public NewApplicationRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "request=" + request +
                '}';
    }
}
