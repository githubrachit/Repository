package com.mli.mpro.bankdetails.model;

public class SOABankHeader {

    private String soaCorrelationId ;
    private String soaAppId;

    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    public String getSoaAppId() {
        return soaAppId;
    }

    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    @Override
    public String toString() {
        return "BankHeader{" +
                "soaCorrelationId='" + soaCorrelationId + '\'' +
                ", soaAppId='" + soaAppId + '\'' +
                '}';
    }
}