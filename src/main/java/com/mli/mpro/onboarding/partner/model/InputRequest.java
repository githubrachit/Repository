package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRequest {

	/*
	 * @JsonProperty("payload") private Payload payload;
	 *
	 * public Payload getPayload() { return payload; }
	 *
	 * public void setPayload(Payload payload) { this.payload = payload; }
	 */
	@JsonProperty("replacementSalePayload")
	private ReplacementSalePayload replacementSalePayload;

	@JsonProperty("dedupeAPIPayload")
	private DedupeAPIPayload dedupeAPIPayload;

	@JsonProperty("proposalNumberPayload")
	private List<ProposalNumberPayload> proposalNumberPayload;


	public ReplacementSalePayload getReplacementSalePayload() {
		return replacementSalePayload;
	}

	public void setReplacementSalePayload(ReplacementSalePayload replacementSalePayload) {
		this.replacementSalePayload = replacementSalePayload;
	}

	public DedupeAPIPayload getDedupeAPIPayload() {
		return dedupeAPIPayload;
	}

	public void setDedupeAPIPayload(DedupeAPIPayload dedupeAPIPayload) {
		this.dedupeAPIPayload = dedupeAPIPayload;
	}

	public List<ProposalNumberPayload> getProposalNumberPayload() {
		return proposalNumberPayload;
	}

	public void setProposalNumberPayload(List<ProposalNumberPayload> proposalNumberPayload) {
		this.proposalNumberPayload = proposalNumberPayload;
	}
}
