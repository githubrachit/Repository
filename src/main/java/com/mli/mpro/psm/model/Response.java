package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Response {


    private Header header;
    @JsonProperty("msgInfo")
    private MessageInfo msgInfo;
    @JsonProperty("payload")
    private PsmResponsePayload psmResponsePayload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MessageInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MessageInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public PsmResponsePayload getPsmResponsePayload() {
        return psmResponsePayload;
    }

    public void setPsmResponsePayload(PsmResponsePayload psmResponsePayload) {
        this.psmResponsePayload = psmResponsePayload;
    }

    @Override
    public String toString() {
        return "Response{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", psmResponsePayload=" + psmResponsePayload +
                '}';
    }
}
