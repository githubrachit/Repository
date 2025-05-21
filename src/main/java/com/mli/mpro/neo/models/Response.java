package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

public class Response {

    private Header header;
    private MsgInfo msgInfo;
    private ResponsePayload payload;

    public Header getHeader() {
        return header;
    }

    public Response setHeader(Header header) {
        this.header = header;
        return this;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public Response setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
        return this;
    }

    public ResponsePayload getPayload() {
        return payload;
    }

    public Response setPayload(ResponsePayload payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Response{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
