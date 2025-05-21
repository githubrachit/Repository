package com.mli.mpro.nps.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.nps.model.requestPayload.Payload;
import com.mli.mpro.utils.Utility;

public class Request {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("payload")
    Payload payload;

    public Request() {
        //default constructor
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
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Request{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}

