package com.mli.mpro.onboarding.partner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.onboarding.partner.validation.Validation;

public interface ValidationService {

	public Validation validateRequest(String inputRequest) throws JsonProcessingException;
	
}
