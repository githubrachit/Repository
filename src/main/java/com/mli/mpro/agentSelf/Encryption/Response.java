package com.mli.mpro.agentSelf.Encryption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.agentSelf.Payload;
import com.mli.mpro.utils.Utility;

import javax.validation.Valid;

public class Response {

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("msgInfo")
    @Valid
    private MsgInfo msgInfo;

    @JsonProperty("header")
    @Valid
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "Response{" +
                "payload='" + payload + '\'' +
                ", msgInfo=" + msgInfo +
                ", header=" + header +
                '}';
    }
}
