package com.mli.mpro.tmb.model.urlshortner;

public class Request {

    private Header header;
    private RequestPayload payload;

    public Request() {
    }

    public Request(Header header, RequestPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public RequestPayload getPayload() {
        return payload;
    }

    public void setPayload(RequestPayload payload) {
        this.payload = payload;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
