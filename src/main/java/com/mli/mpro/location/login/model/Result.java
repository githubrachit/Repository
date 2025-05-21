package com.mli.mpro.location.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	
	@JsonProperty("response")
	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Result [response=" + response + "]";
	}
}
