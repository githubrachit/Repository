package com.mli.mpro.agent360.models;

import com.mli.mpro.utils.Utility;

public class V3Response {
	
	private String payload;
	private MessageInfo msgInfo;

	public V3Response() {
		super();
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public MessageInfo getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(MessageInfo msgInfo) {
		this.msgInfo = msgInfo;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "V3Response [payload=" + payload + ", msgInfo=" + msgInfo + "]";
	}

}
