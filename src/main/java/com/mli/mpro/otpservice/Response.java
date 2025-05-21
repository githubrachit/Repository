package com.mli.mpro.otpservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;

public class Response {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    @JsonProperty("payload")
    private OtpResponsePayload payload;

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

    public OtpResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(OtpResponsePayload payload) {
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
