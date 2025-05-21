package com.mli.mpro.common.models;

import com.mli.mpro.utils.Utility;

import javax.validation.Valid;

public class InputRequest {
	 @Valid
    private Request request;
    private String data;

    public InputRequest() {

    }

    public InputRequest(Request request) {
	super();
	this.request = request;
    }

   /* public InputRequest(InputRequest inputRequest) {
	this.request = new Request(inputRequest.getRequest());
	this.data = inputRequest.data;
    }*/

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
