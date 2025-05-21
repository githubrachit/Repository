package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.onboarding.model.MsgInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "header", "msgInfo" })
public class Response {

    @JsonProperty("header")
    private Header header;
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    public Response() {
	super();
    }

    @JsonProperty("header")
    public Header getHeader() {
	return header;
    }

    @JsonProperty("header")
    public void setHeader(Header header) {
	this.header = header;
    }

    @JsonProperty("msgInfo")
    public MsgInfo getMsgInfo() {
	return msgInfo;
    }

    @JsonProperty("msgInfo")
    public void setMsgInfo(MsgInfo msgInfo) {
	this.msgInfo = msgInfo;
    }


}