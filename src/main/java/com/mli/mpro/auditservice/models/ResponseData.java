package com.mli.mpro.auditservice.models;


import com.mli.mpro.utils.Utility;

public class ResponseData {
    private ResponsePayload responsePayload; 
    
	public ResponseData() {
		super();
	}

    
	public ResponsePayload getResponsePayload() {
	return responsePayload;
    }

    public void setResponsePayload(ResponsePayload responsePayload) {
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
