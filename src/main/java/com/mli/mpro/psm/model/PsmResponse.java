package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PsmResponse {

    @JsonProperty("msgInfo")
    private MessageInfo msgInfo;
    @JsonProperty("payload")
    private PsmResponsePayload psmResponsePayload;

    public PsmResponse(MessageInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public PsmResponse(MessageInfo msgInfo, PsmResponsePayload psmResponsePayload) {
        this.msgInfo = msgInfo;
        this.psmResponsePayload = psmResponsePayload;
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
        return "PsmResponse{" +
                "msgInfo=" + msgInfo +
                ", psmResponsePayload=" + psmResponsePayload +
                '}';
    }
}
