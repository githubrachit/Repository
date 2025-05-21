package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {

    @JsonProperty("payload")
    private Payload payload;
    @JsonProperty("header")
    private Header header;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
