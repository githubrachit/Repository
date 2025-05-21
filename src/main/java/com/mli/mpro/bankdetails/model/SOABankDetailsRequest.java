package com.mli.mpro.bankdetails.model;

public class SOABankDetailsRequest {

    private SOABankRequest request;

    public SOABankRequest getRequest() {
        return request;
    }

    public void setRequest(SOABankRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SOABankDetailsRequest [request=" + request + "]";
    }

}