package com.mli.mpro.samlTraceId;

public class ResponsePayload {
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "ResponsePayload{" +
                "transactionId=" + transactionId +
                '}';
    }
}
