package com.mli.mpro.prannumber.request;

import com.mli.mpro.prannumber.request.Header;
import com.mli.mpro.prannumber.request.Payload;

public class Request {
    private Header header;
    private Payload payload;

    public Request(Header header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
