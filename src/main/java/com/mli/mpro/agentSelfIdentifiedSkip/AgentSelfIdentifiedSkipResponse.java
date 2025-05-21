package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.agentSelfIdentifiedSkip.Header;
public class AgentSelfIdentifiedSkipResponse {

    @JsonProperty("header")
    public  Header header;
    @JsonProperty("msgInfo")
    public MsgInfo msgInfo;
    @JsonProperty("responseData")
    public ResponseData responseData;


    public AgentSelfIdentifiedSkipResponse(Header header, MsgInfo msgInfo, ResponseData responseData) {
        this.header = header;
        this.msgInfo = msgInfo;
        this.responseData = responseData;
    }

    public AgentSelfIdentifiedSkipResponse() {
    }

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

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }


}
