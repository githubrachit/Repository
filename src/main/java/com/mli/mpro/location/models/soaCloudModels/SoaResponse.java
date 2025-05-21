package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;

public class SoaResponse<T> {
    @JsonProperty("header")
    public Header getHeader() {
        return this.header; }
    public void setHeader(Header header) {
        this.header = header; }
    Header header;
    @JsonProperty("msgInfo")
    public MsgInfo getMsgInfo() {
        return this.msgInfo; }
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo; }
    MsgInfo msgInfo;

    @JsonProperty("payload")
    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
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
