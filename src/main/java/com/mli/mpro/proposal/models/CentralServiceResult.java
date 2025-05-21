package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.mli.mpro.location.altfinInquiry.output.Cs1;
import com.mli.mpro.utils.Utility;



public class CentralServiceResult {
    @JsonProperty("cs1")
    private Cs1 cs1;
    @JsonProperty("cs3")
    private Cs3 cs3;

    public Cs1 getCs1() {
        return cs1;
    }

    public void setCs1(Cs1 cs1) {
        this.cs1 = cs1;
    }

    public Cs3 getCs3() {
        return cs3;
    }

    public void setCs3(Cs3 cs3) {
        this.cs3 = cs3;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "CentralServiceResult{" +
                "cs1=" + cs1 +
                ", cs3=" + cs3 +
                '}';
    }
}
