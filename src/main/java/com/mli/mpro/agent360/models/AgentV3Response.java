package com.mli.mpro.agent360.models;

import java.util.Arrays;

import com.mli.mpro.utils.Utility;

public class AgentV3Response {
	
	private V3Response response;

	public AgentV3Response() {
		super();
	}

	public V3Response getResponse() {
		return response;
	}

	public void setResponse(V3Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "AgentV3Response [response=" + response + "]";
	}
}
