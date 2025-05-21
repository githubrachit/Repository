package com.mli.mpro.onboarding.partner.model;

public class Payload {

	private ReplacementSalePayload replacementSalePayload;
	
	private String sourceChannel;
	
	public Payload() {
		// default constructor
	}

	public ReplacementSalePayload getReplacementSalePayload() {
		return replacementSalePayload;
	}

	public void setReplacementSalePayload(ReplacementSalePayload replacementSalePayload) {
		this.replacementSalePayload = replacementSalePayload;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	
	
}
