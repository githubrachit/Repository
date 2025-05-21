package com.mli.mpro.auditservice.models;

import com.mli.mpro.utils.Utility;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class InputRequest {

    @NotNull(message = "request must not be null")
    @Valid
    private Request request;

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

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "InputRequest [request=" + request + "]";
    }

}
