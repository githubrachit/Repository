package com.mli.mpro.onboarding.model.datalakesms;

public class Response {
    private Header header;
    private MsgInfo msgInfo;
    private PayloadResponse payload;

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

    public PayloadResponse getPayload() {
        return payload;
    }

    public void setPayload(PayloadResponse payload) {
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
