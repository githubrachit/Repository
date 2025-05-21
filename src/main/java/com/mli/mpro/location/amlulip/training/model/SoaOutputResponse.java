package com.mli.mpro.location.amlulip.training.model;


import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

public class SoaOutputResponse {
	
	private Header header;
	private MsgInfo msginfo;
	private SoaResponsePayload payload;

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
	public SoaResponsePayload getPayload() {
		return payload;
	}
	public void setPayload(SoaResponsePayload payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "SoaOutputResponse [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + "]";
	}
}
