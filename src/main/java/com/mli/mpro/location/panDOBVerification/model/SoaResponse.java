package com.mli.mpro.location.panDOBVerification.model;

import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.common.soa.model.MsgInfo;

public class SoaResponse {
	
	private Header header;
    private MsgInfo msgInfo;
    private SoaResponsePayload payload;
    
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
	public SoaResponsePayload getPayload() {
		return payload;
	}
	public void setPayload(SoaResponsePayload payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "SoaResponse [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
	}
}
