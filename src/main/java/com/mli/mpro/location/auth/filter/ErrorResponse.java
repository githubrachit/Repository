package com.mli.mpro.location.auth.filter;

import java.io.Serializable;
import java.util.Set;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors;


	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorResponse(String message) {
		super();
		this.message = message;
	}

	public ErrorResponse(String message,Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors) {
		super();
		this.message = message;
		this.errors=errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(Set<com.mli.mpro.onboarding.partner.model.ErrorResponse> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"message='" + message + '\'' +
				", errors=" + errors +
				'}';
	}
}
