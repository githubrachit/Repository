package com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.brmsBroker.model.Header;
import com.mli.mpro.posvbrms.models.MessageInfo;
import com.mli.mpro.utils.Utility;

public class Response {

    @JsonProperty("header")
    private Header header;
    @JsonProperty("msgInfo")
    private MessageInfo msgInfo;
    @JsonProperty("payload")
    private ResponsePayload payload;

    public Response() {
        super();
    }

    public Response(Header header, MessageInfo msgInfo, ResponsePayload payload) {
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

    public ResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(ResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Response{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
