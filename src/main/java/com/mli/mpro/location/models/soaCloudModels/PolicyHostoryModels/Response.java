package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

public class Response {

    public Header header;
    public MsgInfo msgInfo;
    public ResponsePayload payload;

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
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
