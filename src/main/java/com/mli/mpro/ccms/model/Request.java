package com.mli.mpro.ccms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class Request {

    @JsonProperty("header")
    private Header header;

    @JsonProperty("payload")
    private Payload payload;

    /**
     * @param payload
     * @param header
     */

    public Request(Header header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    /**
     *
     */
    public Request() {
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
