package com.mli.mpro.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;

public class BranchResponse {
    @JsonProperty("header")
    public Header header;

    @JsonProperty("msgInfo")
    public MsgInfo msgInfo;

    @JsonProperty("payload")
    public BranchDetailsResponsePayload payload;

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

    public BranchDetailsResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(BranchDetailsResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "BranchResponse{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
