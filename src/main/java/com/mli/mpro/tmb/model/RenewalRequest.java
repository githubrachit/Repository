package com.mli.mpro.tmb.model;

public class RenewalRequest {
    private long transactionId;
    private RenewalPushRequest renewalPushRequest;

    public RenewalPushRequest getRenewalPushRequest() {
        return renewalPushRequest;
    }

    public void setRenewalPushRequest(RenewalPushRequest renewalPushRequest) {
        this.renewalPushRequest = renewalPushRequest;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "RenewalRequest{" +
                "transactionId=" + transactionId +
                ", renewalPushRequest=" + renewalPushRequest +
                '}';
    }
}
