package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

/**
 * The type Request.
 */
public class Request {

    private Header header;
    private Payload payload;

    /**
     * Gets header.
     *
     * @return the header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets header.
     *
     * @param header the header
     */
    public void setHeader(Header header) {
        this.header = header;
    }

    /**
     * With header request.
     *
     * @param header the header
     * @return the request
     */
    public Request withHeader(Header header) {
        this.header = header;
        return this;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * With payload request.
     *
     * @param payload the payload
     * @return the request
     */
    public Request withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("header = " + header)
                .add("payload = " + payload)
                .toString();
    }
}
