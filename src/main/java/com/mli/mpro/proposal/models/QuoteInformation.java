package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuoteInformation {

    private String frequencyOfPayment;
    private String collectiveservicewithST;

    public String getCollectiveservicewithST() {
        return collectiveservicewithST;
    }

    public void setCollectiveservicewithST(String collectiveservicewithST) {
        this.collectiveservicewithST = collectiveservicewithST;
    }

    public String getFrequencyOfPayment() {
        return frequencyOfPayment;
    }

    public void setFrequencyOfPayment(String frequencyOfPayment) {
        this.frequencyOfPayment = frequencyOfPayment;
    }
}
