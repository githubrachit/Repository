package com.mli.mpro.location.amlulip.training.model;

import com.mli.mpro.location.common.soa.model.Header;

public class AmlPayload {
	
	private Header header;
    private RequestData requestData;
    
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public RequestData getRequestData() {
		return requestData;
	}
	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}
	@Override
	public String toString() {
		return "AmlUlipPayload [header=" + header + ", requestData=" + requestData + "]";
	}
}
