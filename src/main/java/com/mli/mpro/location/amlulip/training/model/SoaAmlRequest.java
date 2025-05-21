package com.mli.mpro.location.amlulip.training.model;

public class SoaAmlRequest {
	
	private AmlPayload request;

	public AmlPayload getRequest() {
		return request;
	}

	public void setRequest(AmlPayload request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "SoaAmlRequest [request=" + request + "]";
	}
}
