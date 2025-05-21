package com.mli.mpro.agent360.models;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class TokenResponse {

	@JsonProperty("header")
	private TokenResponseHeader header;
	@JsonProperty("payload")
	private TokenResponsePayload payload;

	public TokenResponse() {
		super();
	}

	public TokenResponseHeader getHeader() {
		return header;
	}

	public void setHeader(TokenResponseHeader header) {
		this.header = header;
	}

	public TokenResponsePayload getPayload() {
		return payload;
	}

	public void setPayload(TokenResponsePayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "TokenResponse [header=" + header + ", payload=" + payload + "]";
	}
}
