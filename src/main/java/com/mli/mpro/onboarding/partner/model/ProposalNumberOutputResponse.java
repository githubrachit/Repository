package com.mli.mpro.onboarding.partner.model;

public class ProposalNumberOutputResponse {
    private ProposalNumberAPIResponse response;


    public ProposalNumberAPIResponse getResponse() {
        return response;
    }

    public void setResponse(ProposalNumberAPIResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ProposalNumberOutputResponse{" +
                "response=" + response +
                '}';
    }
}
