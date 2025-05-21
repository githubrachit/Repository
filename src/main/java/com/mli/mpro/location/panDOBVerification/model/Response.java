package com.mli.mpro.location.panDOBVerification.model;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

public class Response {
	
    private Header header;
    private MsgInfo msginfo;
    private ResponsePayload payload;

    public Response() {
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
        return "Response{" +
                "header=" + header +
                ", msginfo=" + msginfo +
                ", payload=" + payload +
                '}';
    }
}
