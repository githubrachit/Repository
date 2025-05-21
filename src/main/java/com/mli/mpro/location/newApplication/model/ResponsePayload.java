package com.mli.mpro.location.newApplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePayload {

    private Header header;
    private Payload payload;
    private MsgInfo msgInfo;

    public ResponsePayload() {
    }

    public ResponsePayload(Header header, Payload payload, MsgInfo msgInfo) {
        this.header = header;
        this.payload = payload;
        this.msgInfo = msgInfo;
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

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "header=" + header +
                ", payload=" + payload +
                ", msgInfo=" + msgInfo +
                '}';
    }
}
