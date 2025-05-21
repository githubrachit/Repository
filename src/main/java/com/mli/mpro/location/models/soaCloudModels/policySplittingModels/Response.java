package com.mli.mpro.location.models.soaCloudModels.policySplittingModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.posvbrms.models.MessageInfo;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class Response {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("msgInfo")
    private MessageInfo msgInfo;
    @JsonProperty("payload")
    private SplittingorReplacementResponsePayload payload;

    public Response() {
        super();
    }

    public Response(Header header, MessageInfo msgInfo, SplittingorReplacementResponsePayload payload) {
        super();
        this.header = header;
        this.msgInfo = msgInfo;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MessageInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MessageInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public SplittingorReplacementResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(SplittingorReplacementResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SplittingOutputResponse [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
    }
}
