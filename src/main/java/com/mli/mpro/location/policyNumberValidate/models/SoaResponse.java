package com.mli.mpro.location.policyNumberValidate.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoaResponse {
    @JsonProperty("header")
    private  Header header;
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;
    @JsonProperty("payload")
    private  SoaResponsePayload soaResponsePayload;

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

    public SoaResponsePayload getSoaResponsePayload() {
        return soaResponsePayload;
    }

    public void setSoaResponsePayload(SoaResponsePayload soaResponsePayload) {
        this.soaResponsePayload = soaResponsePayload;
    }
}
