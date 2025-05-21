package com.mli.mpro.location.training.model;

public class Result {
	
	private Response response;

	public Result() {
		super();
	}
	
	public Result(Response response) {
		super();
		this.response = response;
	}

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
