package com.mli.mpro.proposal.models;

public class PfBankDetails {
    private String accountNumber;
    private String holderName;
    private String micr;
    private String ifsc;
    private String nameAndBranch;
    private String type;
    private String bankingSince;

    public PfBankDetails() {
        // Default constructor
    }

    public PfBankDetails(String accountNumber, String holderName, String micr, String ifsc, String nameAndBranch, String type, String bankingSince) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.micr = micr;
        this.ifsc = ifsc;
        this.nameAndBranch = nameAndBranch;
        this.type = type;
        this.bankingSince = bankingSince;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getNameAndBranch() {
        return nameAndBranch;
    }

    public void setNameAndBranch(String nameAndBranch) {
        this.nameAndBranch = nameAndBranch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankingSince() {
        return bankingSince;
    }

    public void setBankingSince(String bankingSince) {
        this.bankingSince = bankingSince;
    }

    @Override
    public String toString() {
        return "PfBankDetails{" +
                "accountNumber='" + accountNumber + '\'' +
                ", holderName='" + holderName + '\'' +
                ", micr='" + micr + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", nameAndBranch='" + nameAndBranch + '\'' +
                ", type='" + type + '\'' +
                ", bankingSince='" + bankingSince + '\'' +
                '}';
    }
}
