package com.mli.mpro.onboarding.documents.model;

public class Request {
    private Payload payload;
    public Request() {

    }
    public Request(Payload payload) {
        this.payload = payload;
    }
    public Payload getPayload() {
        return payload;
    }
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}
