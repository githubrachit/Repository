package com.mli.mpro.posvbrms.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "header", "msgInfo", "payload" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @JsonProperty("header")
    private Header header;

    @JsonProperty("msgInfo")
    private MessageInfo msgInfo;

    @JsonProperty("payload")
    private PosvBrmsResponsePayload payload;


    public Response() {

    }

    public Response(Header header, MessageInfo msgInfo, PosvBrmsResponsePayload payload) {
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

    public PosvBrmsResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(PosvBrmsResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Response{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }


}
