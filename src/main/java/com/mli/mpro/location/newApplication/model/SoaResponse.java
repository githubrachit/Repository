package com.mli.mpro.location.newApplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoaResponse {

    private Header header;
    private MsgInfo msgInfo;
    private Payload payload;

    public SoaResponse() {
    }

    public SoaResponse(Header header, MsgInfo msgInfo, Payload payload) {
        this.header = header;
        this.msgInfo = msgInfo;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "SoaResponse{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
