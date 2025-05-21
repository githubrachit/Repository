package com.mli.mpro.onboarding.model.datalakesms;

public class RequestData {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "request=" + request +
                '}';
    }
}
