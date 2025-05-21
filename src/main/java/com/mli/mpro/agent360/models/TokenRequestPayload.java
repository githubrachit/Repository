package com.mli.mpro.agent360.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class TokenRequestPayload {
	
	@JsonProperty("clientId")
	private String clientId;

	@JsonProperty("clientSecret")
	private String clientSecret;
	
	public TokenRequestPayload() {
		super();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "TokenRequestPayload [clientId=" + clientId + ", clientSecret=" + clientSecret + "]";
	}
}
