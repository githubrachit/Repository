package com.mli.mpro.location.login.model;

public class Response {

	private Header header;
	private MsgInfo msginfo;
	private Payload payload;

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

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Response [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + "]";
	}
}
