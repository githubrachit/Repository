package com.mli.mpro.agent360.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class TokenResponseHeader {
	
	@JsonProperty("correlationId")
	private String correlationId;
	@JsonProperty("appId")
	private String applicationId;
	
	public TokenResponseHeader() {
		super();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "TokenResponseHeader [correlationId=" + correlationId + ", applicationId=" + applicationId + "]";
	} 
}
