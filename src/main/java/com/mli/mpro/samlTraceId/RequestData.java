package com.mli.mpro.samlTraceId;

public class RequestData {
    private RequestPayload requestPayload;

    public RequestPayload getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(RequestPayload requestPayload) {
        this.requestPayload = requestPayload;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "requestPayload=" + requestPayload +
                '}';
    }
}
