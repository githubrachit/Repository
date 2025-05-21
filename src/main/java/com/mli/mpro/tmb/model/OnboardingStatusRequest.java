package com.mli.mpro.tmb.model;

public class OnboardingStatusRequest {

    private long transactionId;
    private String eventType;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "OnboardingStatusRequest{" +
                "transactionId=" + transactionId +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
