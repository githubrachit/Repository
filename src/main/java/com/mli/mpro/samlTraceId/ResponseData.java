package com.mli.mpro.samlTraceId;

public class ResponseData {
    private ResponsePayload responsePayload;

    public ResponsePayload getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(ResponsePayload responsePayload) {
        this.responsePayload = responsePayload;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "responsePayload=" + responsePayload +
                '}';
    }
}
