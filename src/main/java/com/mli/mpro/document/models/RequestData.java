package com.mli.mpro.document.models;

import com.mli.mpro.utils.Utility;

public class RequestData {

    private RequestPayload requestPayload;

    public RequestData() {
	super();
    }

    public RequestPayload getRequestPayload() {
	return requestPayload;
    }

    public void setRequestPayload(RequestPayload requestPayload) {
	this.requestPayload = requestPayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "RequestData [requestPayload=" + requestPayload + "]";
    }

}
