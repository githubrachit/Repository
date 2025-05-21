package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;

public class RequestPayload {

    private String retryCategory;

    private int numberOfDays;

    private int dataLimit;

    public RequestPayload() {
	super();
    }

    public String getRetryCategory() {
	return retryCategory;
    }

    public void setRetryCategory(String retryCategory) {
	this.retryCategory = retryCategory;
    }

    public int getNumberOfDays() {
	return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
	this.numberOfDays = numberOfDays;
    }

    public int getDataLimit() {
	return dataLimit;
    }

    public void setDataLimit(int dataLimit) {
	this.dataLimit = dataLimit;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "RequestPayload [retryCategory=" + retryCategory + ", numberOfDays=" + numberOfDays + ", dataLimit=" + dataLimit + "]";
    }

}
