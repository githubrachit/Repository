package com.mli.mpro.location.amlulip.training.model;

public class SoaResult {
	
	private SoaOutputResponse response;

	public SoaOutputResponse getResponse() {
		return response;
	}

	public void setResponse(SoaOutputResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "SoaResult [response=" + response + "]";
	}
}
