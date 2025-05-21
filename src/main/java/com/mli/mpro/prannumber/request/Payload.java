package com.mli.mpro.prannumber.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class Payload {

    @JsonProperty("proposalNumbers")
    private List<String> proposalNumbers;

    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pranNumber")
    private String pranNumber;

    public Payload() {
        // Default constructor
    }

    public Payload(String pranNumber) {
        this.pranNumber = pranNumber;
    }

    public List<String> getProposalNumbers() {
        return proposalNumbers;
    }

    public void setProposalNumbers(List<String> proposalNumbers) {
        this.proposalNumbers = proposalNumbers;
    }

    public String getPranNumber() {
        return pranNumber;
    }

    public void setPranNumber(String pranNumber) {
        this.pranNumber = pranNumber;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "proposalNumbers=" + proposalNumbers +
                ", pranNumber='" + pranNumber + '\'' +
                '}';
    }
}
