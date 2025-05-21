package com.mli.mpro.agent360.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AgentV3Request {
	
	@JsonProperty("request")
	private V3Request request;

	public AgentV3Request() {
		super();
	}

	public V3Request getRequest() {
		return request;
	}

	public void setRequest(V3Request request) {
		this.request = request;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "AgentV3Request [request=" + request + "]";
	}

}
