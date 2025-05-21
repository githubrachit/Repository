package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Request {

    @JsonProperty("header")
    private Header header;
    @JsonProperty("payload")
    private RequestPayload requestPayload;

    public Request(Header header, RequestPayload requestPayload) {
        this.header = header;
        this.requestPayload = requestPayload;
    }

    public Request() {
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public RequestPayload getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(RequestPayload requestPayload) {
        this.requestPayload = requestPayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Request{" +
                "header=" + header +
                ", requestPayload=" + requestPayload +
                '}';
    }
}
