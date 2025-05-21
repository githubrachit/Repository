package com.mli.mpro.pratham.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "header", "msgInfo" ,"payload"})
public class Response {

	@JsonProperty("header")
	private Header header;
	@JsonProperty("msgInfo")
	private MsgInfo msgInfo;
	@JsonProperty("payload")
	private ResponsePayload payload;

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
	@JsonProperty("payload")
	public ResponsePayload getPayload() {
		return payload;
	}
	@JsonProperty("payload")
	public void setPayload(ResponsePayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Response [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
	}

}