package com.mli.mpro.onboarding.partner.validation;

import com.mli.mpro.onboarding.partner.model.ErrorResponse;


import java.util.Set;


public class Validation {

	private Boolean isValid = true;
	
	private Set<ErrorResponse> errors;

	
	public Boolean isValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Set<ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorResponse> errors) {
		this.errors = errors;
	}
	
}
