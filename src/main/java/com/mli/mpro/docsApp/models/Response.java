package com.mli.mpro.docsApp.models;

import com.mli.mpro.utils.Utility;

public class Response {
	private Payload payload;

	private Header header;

	private MsgInfo msgInfo;

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

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

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ClassPojo [payload = " + payload + ", header = " + header + ", msgInfo = " + msgInfo + "]";
	}
}