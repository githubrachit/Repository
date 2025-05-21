package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplacementSaleResponse {

	@JsonProperty("response")
	private ReplacementSaleSOAResponse response;

	public ReplacementSaleResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public ReplacementSaleSOAResponse getResponse() {
		return response;
	}

	public void setResponse(ReplacementSaleSOAResponse response) {
		this.response = response;
	}
}
