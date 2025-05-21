package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PartnerDetails {
    @JsonProperty("accounts")
    private List<BankDetails> accounts;

    public List<BankDetails> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankDetails> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "PartnerDetails{" +
                "accounts=" + accounts +
                '}';
    }
}
