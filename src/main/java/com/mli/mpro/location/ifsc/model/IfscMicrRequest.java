package com.mli.mpro.location.ifsc.model;

public class IfscMicrRequest {
	
	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "IfscMicrRequest [request=" + request + "]";
	}
}
