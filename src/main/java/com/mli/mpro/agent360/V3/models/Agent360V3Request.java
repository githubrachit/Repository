package com.mli.mpro.agent360.V3.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Agent360V3Request {
	
	@JsonProperty("request")
	private V3AgentRequest request;

	public Agent360V3Request() {
		super();
	}

	public V3AgentRequest getRequest() {
		return request;
	}

	public void setRequest(V3AgentRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Agent360V3Request [request=" + request + "]";
	}
}
