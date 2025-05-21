package com.mli.mpro.location.panDOBVerification.model;

import com.mli.mpro.location.common.soa.model.Header;

public class SoaRequest {
	
  private Header header;
  private SoaPayload payload;

    public SoaRequest() {
    }

    public SoaRequest(Header header, SoaPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public SoaPayload getPayload() {
        return payload;
    }

    public void setPayload(SoaPayload payload) {
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
