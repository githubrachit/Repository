package com.mli.mpro.location.newApplication.model;

public class Response {

    private ResponseMsgInfo msginfo;
    private SoaResponse payload;
	public ResponseMsgInfo getMsginfo() {
		return msginfo;
	}
	public void setMsginfo(ResponseMsgInfo msginfo) {
		this.msginfo = msginfo;
	}
	public SoaResponse getPayload() {
		return payload;
	}
	public void setPayload(SoaResponse payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "Response [msginfo=" + msginfo + ", payload=" + payload + "]";
	}
}
