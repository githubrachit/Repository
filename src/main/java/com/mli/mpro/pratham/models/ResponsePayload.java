package com.mli.mpro.pratham.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class ResponsePayload {
	
	@JsonProperty("requestStatus")
	private String requestStatus;

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ResponsePayload [requestStatus=" + requestStatus + "]";
	}

}
