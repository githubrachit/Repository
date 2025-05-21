package com.mli.mpro.location.login.model;

public class Request {
	
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
		return "Request [header=" + header + ", requestData=" + requestData + "]";
	}
}
