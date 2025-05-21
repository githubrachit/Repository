package com.mli.mpro.location.amlulip.training.model;

public class SoaUlipRequest {

	private UlipPayload request;

	public UlipPayload getRequest() {
		return request;
	}

	public void setRequest(UlipPayload request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "SoaUlipRequest [request=" + request + "]";
	}
}
