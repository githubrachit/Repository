package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProposalNumberResponsePayload extends PartnerPayload{
    @JsonProperty("proposalNo")
    private List<Long> proposalNo;

    public List<Long> getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(List<Long> proposalNo) {
        this.proposalNo = proposalNo;
    }
}
