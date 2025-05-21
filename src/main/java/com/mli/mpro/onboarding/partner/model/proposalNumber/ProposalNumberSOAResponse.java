package com.mli.mpro.onboarding.partner.model.proposalNumber;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.partner.model.ProposalNumberSOAResponsePayload;
import com.mli.mpro.onboarding.partner.model.SOAResponse;


public class ProposalNumberSOAResponse extends SOAResponse {
    @JsonProperty("payload")
    private ProposalNumberSOAResponsePayload payload;

    public ProposalNumberSOAResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(ProposalNumberSOAResponsePayload payload) {
        this.payload = payload;
    }

}
