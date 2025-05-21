package com.mli.mpro.location.login.model;

public class JWTInfo {

	private String responseAgent;
	private long exp;

	public JWTInfo(String responseAgent, long exp) {
	        this.responseAgent = responseAgent;
	        this.exp = exp;
	    }

	public String getResponseAgent() {
		return responseAgent;
	}

	public long getExp() {
		return exp;
	}

	@Override
	public String toString() {
		return "JWTInfo [responseAgent=" + responseAgent + ", exp=" + exp + "]";
	}
}
