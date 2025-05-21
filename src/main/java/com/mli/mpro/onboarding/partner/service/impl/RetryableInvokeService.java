package com.mli.mpro.onboarding.partner.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mli.mpro.onboarding.util.Util;

@Component
public class RetryableInvokeService {

	private static final Logger logger = LoggerFactory.getLogger(RetryableInvokeService.class);
	
	@Retryable(value = {Exception.class}, maxAttemptsExpression = "${maxAttempt}")
	public String callService(String url, Object httpEntity) throws Exception {
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			return restTemplate.postForObject(url, httpEntity, String.class);
		} catch (Exception e) {
			
			logger.error("Exception occured while calling service {} ", Util.getExceptionAsString(e));
			
			throw e;
		}
		
	}
	
}
