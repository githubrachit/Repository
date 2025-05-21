package com.mli.mpro.location.training.model;

public class TrainingRequest {
	
	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "TrainingRequest [request=" + request + "]";
	}
}
