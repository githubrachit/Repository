package com.mli.mpro.neo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status {

    private String isPaymentFirst;
    private String journeyStatus;

    public String getIsPaymentFirst() {
        return isPaymentFirst;
    }

    public Status setIsPaymentFirst(String isPaymentFirst) {
        this.isPaymentFirst = isPaymentFirst;
        return this;
    }

    public String getJourneyStatus() {
        return journeyStatus;
    }

    public Status setJourneyStatus(String journeyStatus) {
        this.journeyStatus = journeyStatus;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("isPaymentFirst = " + isPaymentFirst)
                .add("journeyStatus = " + journeyStatus)
                .toString();
    }
}
