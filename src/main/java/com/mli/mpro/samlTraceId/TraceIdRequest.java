package com.mli.mpro.samlTraceId;

public class TraceIdRequest {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "TraceIdRequest{" +
                "request=" + request +
                '}';
    }
}
