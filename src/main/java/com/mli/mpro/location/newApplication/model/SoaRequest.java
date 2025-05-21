package com.mli.mpro.location.newApplication.model;

import com.mli.mpro.location.common.soa.model.Header;

public class SoaRequest {

    private Header header;
    private RequestPayload payload;

    public SoaRequest() {
    }

    public SoaRequest(Header header, RequestPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public RequestPayload getPayload() {
        return payload;
    }

    public void setPayload(RequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "SoaRequest{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
