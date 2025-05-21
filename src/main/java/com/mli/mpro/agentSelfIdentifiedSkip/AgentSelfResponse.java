package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.MsgInfo;

public class AgentSelfResponse {
    @JsonProperty("header")
    public Header header;
    @JsonProperty("msgInfo")
    public MsgInfo msgInfo;
    @JsonProperty("responseData")
    public AgentSelfResponseData responseData;

    public AgentSelfResponse(Header header, MsgInfo msgInfo, AgentSelfResponseData responseData) {
        this.header = header;
        this.msgInfo = msgInfo;
        this.responseData = responseData;
    }

    public AgentSelfResponse() {

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

    public AgentSelfResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(AgentSelfResponseData responseData) {
        this.responseData = responseData;
    }


}

