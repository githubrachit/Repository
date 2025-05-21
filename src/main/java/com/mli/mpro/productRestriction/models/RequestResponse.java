package com.mli.mpro.productRestriction.models;

public class RequestResponse {
    private String payload;

    public RequestResponse() {
    }

    public RequestResponse(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "RequestResponse{" +
                "payload='" + payload + '\'' +
                '}';
    }
}

