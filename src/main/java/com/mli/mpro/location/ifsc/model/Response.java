package com.mli.mpro.location.ifsc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Response {
	
	private Header header;
	private MsgInfo msginfo;
	private Transaction payload;
	
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
	public Transaction getPayload() {
		return payload;
	}
	public void setPayload(Transaction payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "Response [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + ", getHeader()="
				+ getHeader() + ", getMsginfo()=" + getMsginfo() + ", getPayload()=" + getPayload() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
