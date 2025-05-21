package com.mli.mpro.location.login.model;

public class LoginRequest {
	
	private EncrpytRequest request;

	public EncrpytRequest getRequest() {
		return request;
	}

	public void setRequest(EncrpytRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "LoginRequest []";
	}
}
