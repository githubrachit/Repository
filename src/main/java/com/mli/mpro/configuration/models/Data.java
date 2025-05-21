package com.mli.mpro.configuration.models;

public class Data {
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Data{" +
                "payload=" + payload +
                '}';
    }
}