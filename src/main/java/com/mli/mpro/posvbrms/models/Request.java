package com.mli.mpro.posvbrms.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {

    @JsonProperty("payload")
    private PosvBrmsPayload payload;

    @JsonProperty("header")
    private Header header;

    public Request() {

    }

    public Request(PosvBrmsPayload payload, Header header) {
        this.payload = payload;
        this.header = header;
    }

    public PosvBrmsPayload getPayload() {
        return payload;
    }

    public void setPayload(PosvBrmsPayload payload) {
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }


    @Override
    public String toString() {
        return "Request{" +
                "payload=" + payload +
                ", header=" + header +
                '}';
    }


}
