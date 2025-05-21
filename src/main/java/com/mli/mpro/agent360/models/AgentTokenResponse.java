package com.mli.mpro.agent360.models;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AgentTokenResponse {

	@JsonProperty("response")
	private TokenResponse response;

	public AgentTokenResponse() {
		super();
	}

	public TokenResponse getResponse() {
		return response;
	}

	public void setResponse(TokenResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "AgentTokenResponse [response=" + response + "]";
	}

}
