package com.mli.mpro.agent360.models;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class TokenRequest {

	@JsonProperty("header")
	private TokenRequestHeader header;
	@JsonProperty("payload")
	private TokenRequestPayload payload;

	public TokenRequest() {
		super();
	}

	public TokenRequestHeader getHeader() {
		return header;
	}

	public void setHeader(TokenRequestHeader header) {
		this.header = header;
	}

	public TokenRequestPayload getPayload() {
		return payload;
	}

	public void setPayload(TokenRequestPayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "TokenRequest [header=" + header + ", payload=" + payload + "]";
	}
}
