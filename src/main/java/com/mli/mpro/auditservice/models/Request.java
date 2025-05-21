package com.mli.mpro.auditservice.models;

import com.mli.mpro.utils.Utility;

public class Request {

    private Metadata metadata;

    private RequestData requestData;

	public Request() {
		super();

	}

    public Request(Metadata metadata, RequestData requestData) {
	super();
	this.metadata = metadata;
	this.requestData = requestData;
    }

    public Metadata getMetadata() {
	return metadata;
    }

    public void setMetadata(Metadata metadata) {
	this.metadata = metadata;
    }

    public RequestData getRequestData() {
	return requestData;
    }

    public void setRequestData(RequestData requestData) {
	this.requestData = requestData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Request [metadata=" + metadata + ", requestData=" + requestData + "]";
    }

}

