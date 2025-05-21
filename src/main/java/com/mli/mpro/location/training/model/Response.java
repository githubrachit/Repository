package com.mli.mpro.location.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	
	private Header header;
	
	private MsgInfo msginfo;
	
	private ResponsePayload payload;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public MsgInfo getMsginfo() {
		return msginfo;
	}

	public void setMsginfo(MsgInfo msginfo) {
		this.msginfo = msginfo;
	}

	public ResponsePayload getPayload() {
		return payload;
	}

	public void setPayload(ResponsePayload payload) {
		this.payload = payload;
	}

	public Response(Header header, MsgInfo msginfo, ResponsePayload payload) {
		super();
		this.header = header;
		this.msginfo = msginfo;
		this.payload = payload;
	}

	public Response() {
		super();
	}

	public Response(MsgInfo msginfo) {
		super();
		this.msginfo = msginfo;
	}

	@Override
	public String toString() {
		return "Response [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + "]";
	}
}
