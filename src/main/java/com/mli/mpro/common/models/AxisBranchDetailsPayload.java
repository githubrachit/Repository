package com.mli.mpro.common.models;

public class AxisBranchDetailsPayload {

    private String transactionId;
    private String channelCode;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Override
    public String toString() {
        return "AxisBranchDetailsPayload{" +
                "transactionId='" + transactionId + '\'' +
                ", channelCode='" + channelCode + '\'' +
                '}';
    }
}
