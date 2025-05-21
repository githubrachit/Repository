package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SOARequest {
	
	@JsonProperty("header")
	private Header header;
	
	@JsonProperty("payload")
	private SOARequestPayload payload;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public SOARequestPayload getPayload() {
		return payload;
	}

	public void setPayload(SOARequestPayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "SOARequest [header=" + header + ", payload=" + payload + "]";
	}
	
	
}
