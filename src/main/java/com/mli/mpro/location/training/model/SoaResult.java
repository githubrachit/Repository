package com.mli.mpro.location.training.model;

public class SoaResult {
	
	private SaoResponse response;

	public SaoResponse getResponse() {
		return response;
	}

	public void setResponse(SaoResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "SoaResult [response=" + response + "]";
	}
}
