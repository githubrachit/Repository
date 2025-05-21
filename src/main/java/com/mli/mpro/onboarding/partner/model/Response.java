package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	@JsonProperty("response")
	private SOAResponse response;

	public Response() {
		// TODO Auto-generated constructor stub
	}
	
	public SOAResponse getResponse() {
		return response;
	}

	public void setResponse(SOAResponse response) {
		this.response = response;
	}
	
}

