package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.pasa.Payload;

public class Request {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("payload")
    private Payload payload;

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

    public Request(Header header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    public Request() {
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
