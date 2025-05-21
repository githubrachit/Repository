package com.mli.mpro.samlTraceId;

import com.mli.mpro.common.models.Metadata;

public class Request {
    private Metadata metadata;
    private RequestData requestData;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "Request{" +
                "metadata=" + metadata +
                ", requestData=" + requestData +
                '}';
    }
}
