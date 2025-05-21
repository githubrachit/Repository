package com.mli.mpro.onboarding.partner.service;

public interface ServiceRegistry {

	public ValidationService getServiceBean(String name);
	
	public SOAService getValidationService(String name);
	
	public RequestTransformationService getRequestTransformationService(String name);
}
