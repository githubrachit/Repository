package com.mli.mpro.prannumber.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mli.mpro.location.policyNumberValidate.models.ProposalValidations;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
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

    @Override
    public String toString() {
        return "Payload{" +
                "proposalValidations='" + proposalValidations + '\'' +
                ", pranStatus='" + pranStatus + '\'' +
                '}';
    }
}
