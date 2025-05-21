package com.mli.mpro.docsApp.models;

import com.mli.mpro.utils.Utility;

public class Request {
	private Payload payload;

	private Header header;

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

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ClassPojo [payload = " + payload + ", header = " + header + "]";
	}
}
