package com.mli.mpro.onboarding.partner.model;

public class CpdRequest {
    private Header header;
    private CpdPayload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public CpdPayload getPayload() {
        return payload;
    }

    public void setPayload(CpdPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "CpdRequest{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
