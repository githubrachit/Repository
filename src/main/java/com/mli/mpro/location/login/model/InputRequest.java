package com.mli.mpro.location.login.model;

public class InputRequest {
	
	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "InputRequest [request=" + request + "]";
	}
}
