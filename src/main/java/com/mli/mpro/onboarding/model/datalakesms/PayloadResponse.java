package com.mli.mpro.onboarding.model.datalakesms;

public class PayloadResponse {
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "transactionId='" + transactionId + '\'' +
                '}';
    }
}
