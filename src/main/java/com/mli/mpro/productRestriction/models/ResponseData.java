package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;

public class ResponseData {
    private ResponsePayload responsePayload;

    public ResponseData() {

    }

    public ResponsePayload getResponsePayload() {
	return responsePayload;
    }

    public void setResponsePayload(ResponsePayload responsePayload) {
	this.responsePayload = responsePayload;
    }

    public ResponseData(ResponsePayload responsePayload) {
	super();
	this.responsePayload = responsePayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "ResponseData [responsePayload=" + responsePayload + "]";
    }
}