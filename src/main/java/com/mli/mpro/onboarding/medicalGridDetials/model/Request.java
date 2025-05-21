package com.mli.mpro.onboarding.medicalGridDetials.model;


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
