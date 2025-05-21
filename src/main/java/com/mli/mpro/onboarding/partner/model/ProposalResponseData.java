package com.mli.mpro.onboarding.partner.model;

import java.util.List;

public class ProposalResponseData {

    private List<ProposalNumberSOAResponsePayload> proposalNumberResponse;


    public List<ProposalNumberSOAResponsePayload> getProposalNumberResponse() {
        return proposalNumberResponse;
    }

    public void setProposalNumberResponse(List<ProposalNumberSOAResponsePayload> proposalNumberResponse) {
        this.proposalNumberResponse = proposalNumberResponse;
    }
}
