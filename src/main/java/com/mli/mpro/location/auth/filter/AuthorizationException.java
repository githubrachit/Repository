package com.mli.mpro.location.auth.filter;

public class AuthorizationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public AuthorizationException(String message) {
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "AuthorizationException [message=" + message + "]";
	}

}
