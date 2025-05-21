package com.mli.mpro.common.models.genericModels;


import com.mli.mpro.common.models.Metadata;

public class ApiRequestData<T> {
    private Metadata metadata;
    private T payload;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "metadata=" + metadata +
                ", payload=" + payload +
                '}';
    }
}
