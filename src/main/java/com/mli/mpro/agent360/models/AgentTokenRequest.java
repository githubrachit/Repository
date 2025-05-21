package com.mli.mpro.agent360.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AgentTokenRequest {

	@JsonProperty("request")
	private TokenRequest request;

	public AgentTokenRequest() {
		super();
	}

	public TokenRequest getRequest() {
		return request;
	}

	public void setRequest(TokenRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "AgentTokenRequest [request=" + request + "]";
	}
}
