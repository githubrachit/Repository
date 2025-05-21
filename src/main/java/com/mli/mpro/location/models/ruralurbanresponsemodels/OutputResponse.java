package com.mli.mpro.location.models.ruralurbanresponsemodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.location.ruralurbanrequestmodels.Header;
import com.mli.mpro.posseller.email.models.MsgInfo;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"msgInfo", "payLoad", "header"})
public class OutputResponse {

    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;
    @JsonProperty("payLoad")
    private Payload payload;
    @JsonProperty("header")
    private Header header;

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

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "OutputResponse{" +
                "msgInfo=" + msgInfo +
                ", payload=" + payload +
                ", header=" + header +
                '}';
    }
}
