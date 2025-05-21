package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;



public class ResponseForOtpApi {
    public ResponseForOtpApi() {
    }

    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    @JsonProperty("header")
    private Header header;

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

}
