package com.mli.mpro.location.YblPasa.Payload;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class YblInputRequest {

    @Sensitive(MaskType.PAN_NUM)
    private String panNumber;
    private String custId;
    private String transactionId;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "YblInputRequest{" +
                "panNumber='" + panNumber + '\'' +
                ", custId='" + custId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
