package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class NpsRequestPayload {
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pran")
    private String pran;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("goCABrokerCode")
    private String goCABrokerCode;
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGoCABrokerCode(){
        return goCABrokerCode;
    }

    public void setGoCABrokerCodee(String goCABrokerCode) {
        this.goCABrokerCode = goCABrokerCode;
    }
    public NpsRequestPayload() {
        //default constructor
    }


    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPran() {
        return pran;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setPran(String pran) {
        this.pran = pran;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "NpsRequestPayload{" +
                "pran='" + pran + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", channel='" + channel + '\'' +
                ", goCode='" + goCABrokerCode + '\'' +
                '}';
    }
}
