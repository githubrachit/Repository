package com.mli.mpro.samlTraceId;

public class RequestPayload {
    private String traceId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "RequestPayload{" +
                "traceId='" + traceId + '\'' +
                '}';
    }
}
