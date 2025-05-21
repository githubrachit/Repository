package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SchemeDetails {

    @JsonProperty("netAmount")
    private String netAmount;

    @JsonProperty("schemeId")
    private String schemeId;

    @JsonProperty("pensionFreq")
    private String pensionFreq;

    @JsonProperty("amountFlag")
    private String amountFlag;

    public SchemeDetails() {
        //default constructor
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getPensionFreq() {
        return pensionFreq;
    }

    public void setPensionFreq(String pensionFreq) {
        this.pensionFreq = pensionFreq;
    }

    public String getAmountFlag() {
        return amountFlag;
    }

    public void setAmountFlag(String amountFlag) {
        this.amountFlag = amountFlag;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SchemeDetails{" +
                "netAmount='" + netAmount + '\'' +
                ", schemeId='" + schemeId + '\'' +
                ", pensionFreq='" + pensionFreq + '\'' +
                ", amountFlag='" + amountFlag + '\'' +
                '}';
    }
}
