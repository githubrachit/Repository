package com.mli.mpro.common.models;

import com.mli.mpro.utils.Utility;

//oauth SOA token response
public class OauthTokenResponse {

	private String token_type;
	private String access_token;
	private int expires_in;
	private String consented_on;
	private String scope;
	private String refresh_token;
	private String refresh_token_expires_in;

	public OauthTokenResponse() {
		super();
	}

	public OauthTokenResponse(String token_type, String access_token, int expires_in, String consented_on,
			String scope, String refresh_token, String refresh_token_expires_in) {
		super();
		this.token_type = token_type;
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.consented_on = consented_on;
		this.scope = scope;
		this.refresh_token = refresh_token;
		this.refresh_token_expires_in = refresh_token_expires_in;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getConsented_on() {
		return consented_on;
	}

	public void setConsented_on(String consented_on) {
		this.consented_on = consented_on;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getRefresh_token_expires_in() {
		return refresh_token_expires_in;
	}

	public void setRefresh_token_expires_in(String refresh_token_expires_in) {
		this.refresh_token_expires_in = refresh_token_expires_in;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "OauthTokenResponse [token_type=" + token_type + ", access_token=" + access_token + ", expires_in="
				+ expires_in + ", consented_on=" + consented_on + ", scope=" + scope + ", refresh_token="
				+ refresh_token + ", refresh_token_expires_in=" + refresh_token_expires_in + "]";
	}

}
