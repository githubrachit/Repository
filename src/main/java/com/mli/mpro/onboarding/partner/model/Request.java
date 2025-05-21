package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {

	@JsonProperty("request")
	private SOARequest request;

	public SOARequest getRequest() {
		return request;
	}

	public void setRequest(SOARequest request) {
		this.request = request;
	}
	
}
