package com.mli.mpro.location.policyNumberValidate.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaRequest {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("payload")
    private SoaRequestPayload soaRequestPayload;

    public SoaRequest() {
    }

    public SoaRequest(Header header, SoaRequestPayload soaRequestPayload) {
        this.header = header;
        this.soaRequestPayload = soaRequestPayload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public SoaRequestPayload getSoaRequestPayload() {
        return soaRequestPayload;
    }

    public void setSoaRequestPayload(SoaRequestPayload soaRequestPayload) {
        this.soaRequestPayload = soaRequestPayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaRequest{" +
                "header=" + header +
                ", soaRequestPayload=" + soaRequestPayload +
                '}';
    }
}
