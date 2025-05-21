package com.mli.mpro.otpservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;

public class Request {
    @JsonProperty("header")
    private Header header;

    @JsonProperty("payload")
    private OtpRequestPayload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public OtpRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(OtpRequestPayload payload) {
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
