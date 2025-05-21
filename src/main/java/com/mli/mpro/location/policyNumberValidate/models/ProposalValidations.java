package com.mli.mpro.location.policyNumberValidate.models;

public class ProposalValidations {

    private String proposalNumber;
    private String status;
    private String app;

    public ProposalValidations() {
    }

    public ProposalValidations(String proposalNumber, String status, String app) {
        this.proposalNumber = proposalNumber;
        this.status = status;
        this.app = app;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }


}
