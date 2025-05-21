package com.mli.mpro.yblAccount.models;

import com.mli.mpro.utils.Utility;

import javax.validation.Valid;

public class InputRequest {
	
	@Valid
	private Request request;

	public InputRequest() {
	}

	public InputRequest(@Valid Request request) {
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
