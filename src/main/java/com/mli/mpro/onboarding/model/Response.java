package com.mli.mpro.onboarding.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    Header header; 
    @JsonProperty("msgInfo")
    MsgInfo msginfo;
    @JsonProperty("payload")
    ResponsePayload payload;

    public Response() {
       
    }

    public Response(MsgInfo msginfo) {
        this.msginfo = msginfo;
    }

    public Response(MsgInfo msginfo, ResponsePayload payload) {
        this.msginfo = msginfo;
        this.payload = payload;
    }

    public Response(Header header, MsgInfo msginfo, ResponsePayload payload) {
        this.header = header;
        this.msginfo = msginfo;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MsgInfo getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(MsgInfo msginfo) {
        this.msginfo = msginfo;
    }

    public ResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(ResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Response [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + "]";
    }
    
    
}
