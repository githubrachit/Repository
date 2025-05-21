package com.mli.mpro.common.models;

import com.mli.mpro.utils.Utility;

import java.util.List;

public class DocumentGenerationResponse {

    private List<Object> message;

    public DocumentGenerationResponse() {
	super();
    }

    public List<Object> getMessage() {
	return message;
    }

    public void setMessage(List<Object> message) {
	this.message = message;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "DocumentGenerationResponse [message=" + message + "]";
    }

}
