package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;

import javax.validation.Valid;

public class Response {
    @JsonProperty("header")
    @Valid
    private Header header;
    @JsonProperty("msgInfo")
    @Valid
    private MsgInfo msgInfo;
    @JsonProperty("payload")
    @Valid
    private Payload payload;

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

}
