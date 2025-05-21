package com.mli.mpro.agent360.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class V3Request {
	
	@JsonProperty("payload")
	private String payload;

	public V3Request() {
		super();
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "V3Request [payload=" + payload + "]";
	}

}
