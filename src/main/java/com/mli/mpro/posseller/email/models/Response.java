package com.mli.mpro.posseller.email.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Response {

	@JsonProperty("header")
	private Header header;
	@JsonProperty("msgInfo")
	private MsgInfo msgInfo;
	@JsonProperty("payload")
	private Payload payload;

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

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
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
