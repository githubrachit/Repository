package com.mli.mpro.productRestriction.models;

import com.mli.mpro.utils.Utility;

public class OutputResponse {
    private Response response;

    public OutputResponse() {

    }

    public OutputResponse(Response response) {
	super();
	this.response = response;
    }

    public Response getResponse() {
	return response;
    }

    public void setResponse(Response response) {
	this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "OutputResponse [response=" + response + "]";
    }
}
