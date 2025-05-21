package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

public class ResponsePayload {

    private String equoteNumber;

    public String getEquoteNumber() {
        return equoteNumber;
    }

    public ResponsePayload setEquoteNumber(String equoteNumber) {
        this.equoteNumber = equoteNumber;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResponsePayload{" +
                "equoteNumber='" + equoteNumber + '\'' +
                '}';
    }
}
