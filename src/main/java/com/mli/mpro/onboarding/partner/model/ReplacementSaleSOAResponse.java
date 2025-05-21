package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplacementSaleSOAResponse extends SOAResponse {

	@JsonProperty("payload")
    private ReplacementSaleSOAResponsePayload payload;

	public ReplacementSaleSOAResponsePayload getPayload() {
		System.out.println("ReplacementSaleSOAResponse getPayload called");
		return payload;
	}

	public void setPayload(ReplacementSaleSOAResponsePayload payload) {
		System.out.println("ReplacementSaleSOAResponse setPayload called");
		this.payload = payload;
	}
	
}
