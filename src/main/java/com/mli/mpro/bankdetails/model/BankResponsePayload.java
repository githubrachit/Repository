package com.mli.mpro.bankdetails.model;

import java.util.List;

public class BankResponsePayload {

    List<BankDetails> bankDetails ;

    public List<BankDetails> getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(List<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
    }

    @Override
    public String toString() {
        return "BankResponsePayload{" +
                "bankDetails=" + bankDetails +
                '}';
    }
}
