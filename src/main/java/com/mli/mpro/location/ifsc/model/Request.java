package com.mli.mpro.location.ifsc.model;

public class Request {
	
	private RequestData data;

	public RequestData getData() {
		return data;
	}

	public void setData(RequestData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Request [data=" + data + "]";
	}
	

}
