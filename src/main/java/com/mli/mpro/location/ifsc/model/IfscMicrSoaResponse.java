package com.mli.mpro.location.ifsc.model;

import java.util.List;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

public class IfscMicrSoaResponse {
	
	private Header header;
	private MsgInfo msginfo;
	private IfscMicrPayload payload;
	
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
	public IfscMicrPayload getPayload() {
		return payload;
	}
	public void setPayload(IfscMicrPayload payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "IfscMicrSoaResponse [header=" + header + ", msginfo=" + msginfo + ", payload=" + payload + "]";
	}
}
