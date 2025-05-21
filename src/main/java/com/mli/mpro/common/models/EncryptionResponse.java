package com.mli.mpro.common.models;

public class EncryptionResponse {

    private Object payload;

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "EncryptionResponse{" +
                "payload=" + payload +
                '}';
    }
}
