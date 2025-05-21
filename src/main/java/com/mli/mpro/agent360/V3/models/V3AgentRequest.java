package com.mli.mpro.agent360.V3.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agent.models.RequestPayload;
import com.mli.mpro.utils.Utility;

public class V3AgentRequest {

	@JsonProperty("payload")
	private RequestPayload payload;

	public V3AgentRequest() {
		super();
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
		return "V3AgentRequest [payload=" + payload + "]";
	}
}

