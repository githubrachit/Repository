package com.mli.mpro.agent360.models;

import com.mli.mpro.utils.Utility;

public class TokenResponsePayload {
	
	private String accessToken;
	private String expiresIn;
	private String tokenType;
	public TokenResponsePayload() {
		super();
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "TokenResponsePayload [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", tokenType="
				+ tokenType + "]";
	}
}
