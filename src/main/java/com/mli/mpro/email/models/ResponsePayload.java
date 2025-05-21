package com.mli.mpro.email.models;

import com.mli.mpro.utils.Utility;

import java.util.List;


public class ResponsePayload {


    private List<Object> message;

    public ResponsePayload() {

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
		return "ResponsePayload [message=" + message + "]";
	}

    
}