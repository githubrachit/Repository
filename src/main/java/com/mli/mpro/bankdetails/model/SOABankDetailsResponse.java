package com.mli.mpro.bankdetails.model;

public class SOABankDetailsResponse {

    private SOABankResponse response;

    public SOABankResponse getResponse() {
        return response;
    }

    public void setResponse(SOABankResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SOABankDetailsResponse [response=" + response + "]";
    }
}