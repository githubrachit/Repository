package com.mli.mpro.location.policyNumberValidate.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoaResponsePayload {

    private List<ProposalValidations> proposalValidations;
    private String pranStatus;

    public String getPranStatus() {
        return pranStatus;
    }

    public void setPranStatus(String pranStatus) {
        this.pranStatus = pranStatus;
    }

    public List<ProposalValidations> getProposalValidations() {
        return proposalValidations;
    }

    public void setProposalValidations(List<ProposalValidations> proposalValidations) {
        this.proposalValidations = proposalValidations;
    }

}
