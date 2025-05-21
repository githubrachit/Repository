package com.mli.mpro.location.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {
	
	private PayloadRequest request;

	public PayloadRequest getRequest() {
		return request;
	}

	public void setRequest(PayloadRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "Payload [request=" + request + "]";
	}
}
