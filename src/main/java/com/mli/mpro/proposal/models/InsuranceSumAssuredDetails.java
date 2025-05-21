package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceSumAssuredDetails {

    @JsonProperty("sumAssuredForSpouseAndChild")
    private String sumAssuredForSpouseAndChild;

    public String getSumAssuredForSpouseAndChild() {
        return sumAssuredForSpouseAndChild;
    }

    public void setSumAssuredForSpouseAndChild(String sumAssuredForSpouseAndChild) {
        this.sumAssuredForSpouseAndChild = sumAssuredForSpouseAndChild;
    }
}
