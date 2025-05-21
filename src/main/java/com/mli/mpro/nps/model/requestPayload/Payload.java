package com.mli.mpro.nps.model.requestPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class Payload {
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pran")
    private String pran;
 @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pan")
    private String panNumber;

    public Payload() {
        //default constructor
    }

    public String getPran() {
        return pran;
    }

    public void setPran(String pran) {
        this.pran = pran;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "pran='" + pran + '\'' +
                ", panNumber='" + panNumber + '\'' +
                '}';
    }
}

