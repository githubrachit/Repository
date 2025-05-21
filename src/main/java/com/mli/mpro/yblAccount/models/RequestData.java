package com.mli.mpro.yblAccount.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class RequestData {

	@JsonProperty("payload")
	private RequestPayload payload;

	public RequestData() {
	}

	public RequestData(RequestPayload payload) {
		super();
		this.payload = payload;
	}

	public RequestPayload getPayload() {
		return payload;
	}

	public void setPayload(RequestPayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RequestData [payload=" + payload + "]";
	}

}
