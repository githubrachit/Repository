package com.mli.mpro.agent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaApiRequest<T> {
	@JsonProperty("request")
	private Request<T> request;

	public SoaApiRequest() {
		super();
	}

	public SoaApiRequest(Request request) {
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
		return "AgentRequest [request=" + request + "]";
	}

	
	
	
	
	
}
