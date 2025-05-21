package com.mli.mpro.proposal.models;

public class NomineeAppointeePfDetails {
    private PfDetails nomineePfDetails;
    private PfDetails appointeePfDetails;

    // Getters and Setters
    public PfDetails getNomineePfDetails() {
        return nomineePfDetails;
    }

    public void setNomineePfDetails(PfDetails nomineePfDetails) {
        this.nomineePfDetails = nomineePfDetails;
    }

    public PfDetails getAppointeePfDetails() {
        return appointeePfDetails;
    }

    public void setAppointeePfDetails(PfDetails appointeePfDetails) {
        this.appointeePfDetails = appointeePfDetails;
    }
}
