package com.mli.mpro.bankdetails.model;

public class SOABankPayload {
    private String bankIfscCode;
    private String bankMicrCode;

    public String getBankIfscCode() {
        return bankIfscCode;
    }

    public void setBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
    }

    public String getBankMicrCode() {
        return bankMicrCode;
    }

    public void setBankMicrCode(String bankMicrCode) {
        this.bankMicrCode = bankMicrCode;
    }

    @Override
    public String toString() {
        return "SOABankPayload [bankIfscCode=" + bankIfscCode + ", bankMicrCode=" + bankMicrCode + "]";
    }
}