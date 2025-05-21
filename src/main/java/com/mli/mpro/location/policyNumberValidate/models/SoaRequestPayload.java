package com.mli.mpro.location.policyNumberValidate.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class SoaRequestPayload {
    @JsonProperty("proposalNumbers")
    private List<String> proposalNumbers;

    public SoaRequestPayload() {
    }

    public SoaRequestPayload(List<String> proposalNumbers) {
        this.proposalNumbers = proposalNumbers;
    }

    public List<String> getProposalNumbers() {
        return proposalNumbers;
    }

    public void setProposalNumbers(List<String> proposalNumbers) {
        this.proposalNumbers = proposalNumbers;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RequestPayload{" +
                "proposalNumbers=" + proposalNumbers +
                '}';
    }
}
