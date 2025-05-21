package com.mli.mpro.location.newApplication.model;


public class Request {

    private RequestPayload payload;

    public Request() {
    }

    public Request(RequestPayload payload) {
        this.payload = payload;
    }

    public RequestPayload getPayload() {
        return payload;
    }

    public void setPayload(RequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Request{" +
                "payload=" + payload +
                '}';
    }
}
