package com.mli.mpro.location.login.model;

public class TokenPayload {
	private String user;
	private String responseAgent;

	public TokenPayload() {
	}

	public TokenPayload(String user, String responseAgent) {
		super();
		this.user = user;
		this.responseAgent = responseAgent;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getResponseAgent() {
		return responseAgent;
	}

	public void setResponseAgent(String responseAgent) {
		this.responseAgent = responseAgent;
	}

	@Override
	public String toString() {
		return "TokenPayload [user=" + user + ", responseAgent=" + responseAgent + "]";
	}
}
