package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class InputRequest {
    private Request request;
    private String data;

    public InputRequest() {

    }

    public InputRequest(Request request) {
	super();
	this.request = request;
    }

    public Request getRequest() {
	return request;
    }

    public void setRequest(Request request) {
	this.request = request;
    }
    

    public String getdata() {
        return data;
    }

    public void setdata(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "InputRequest [request=" + request + "]";
    }
}
