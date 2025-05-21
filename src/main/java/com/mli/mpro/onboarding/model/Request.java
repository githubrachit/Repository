package com.mli.mpro.onboarding.model;

public class Request {

    Header header;
    Payload payload;

    public Request() {
        
    }
    public Request(Header header, Payload payload) {
        this.header = header;
        this.payload = payload;
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
    
    
}
