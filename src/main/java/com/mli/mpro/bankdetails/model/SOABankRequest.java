package com.mli.mpro.bankdetails.model;

public class SOABankRequest {

    private SOABankHeader header;
    private SOABankPayload payload;

    public SOABankHeader getHeader() {
        return header;
    }

    public void setHeader(SOABankHeader header) {
        this.header = header;
    }

    public SOABankPayload getPayload() {
        return payload;
    }

    public void setPayload(SOABankPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "SOABankRequest [header=" + header + ", payload=" + payload + "]";
    }
}