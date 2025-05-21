package com.mli.mpro.location.ivc.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaRequest {
    @JsonProperty("header")
    private SoaHeader header;
    @JsonProperty("payload")
    private SoaRequestPayload payload;

    public SoaHeader getHeader() {
        return header;
    }

    public void setHeader(SoaHeader header) {
        this.header = header;
    }

    public SoaRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(SoaRequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SoaRequest{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
