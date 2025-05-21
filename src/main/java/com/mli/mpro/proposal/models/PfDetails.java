package com.mli.mpro.proposal.models;

public class PfDetails {
    private PfCommunicationAddress communicationAddress;
    private PfPermanentAddress permanentAddress;
    private PfBankDetails bankDetails;

    // Getters and Setters
    public PfCommunicationAddress getCommunicationAddress() {
        return communicationAddress;
    }

    public void setCommunicationAddress(PfCommunicationAddress communicationAddress) {
        this.communicationAddress = communicationAddress;
    }

    public PfPermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(PfPermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public PfBankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(PfBankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }
}
