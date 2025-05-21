package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;

public class Date {

    private java.util.Date startDate;
    private java.util.Date endDate;

    public java.util.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "Date{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
