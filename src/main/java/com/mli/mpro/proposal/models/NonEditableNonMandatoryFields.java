package com.mli.mpro.proposal.models;

import java.util.List;

public class NonEditableNonMandatoryFields {

    private List<String> tmb;

    public List<String> getTmb() {
        return tmb;
    }

    public void setTmb(List<String> tmb) {
        this.tmb = tmb;
    }

    @Override
    public String toString() {
        return "NonEditableNonMandatoryFields{" +
                "tmb=" + tmb +
                '}';
    }
}
