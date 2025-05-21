package com.mli.mpro.location.ifsc.model;

public class IfscMicrSoaResult {
	
	private IfscMicrSoaResponse response;

	public IfscMicrSoaResponse getResponse() {
		return response;
	}

	public void setResponse(IfscMicrSoaResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "IfscMicrSoaResult [response=" + response + "]";
	}	
}
