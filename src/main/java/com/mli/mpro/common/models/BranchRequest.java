package com.mli.mpro.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;

public class BranchRequest {
    @JsonProperty("header")
    public Header header;

    @JsonProperty("payload")
    public BranchDetailsRequestPayload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public BranchDetailsRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(BranchDetailsRequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "BranchRequest{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
