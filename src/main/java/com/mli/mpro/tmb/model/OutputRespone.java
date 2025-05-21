
package com.mli.mpro.tmb.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputRespone {
    private Header header;

    private Response response;
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Response getResponse() {
        return response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
