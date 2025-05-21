package com.mli.mpro.location.services;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.docsApp.models.DocsappResponse;
import org.springframework.retry.annotation.Backoff;

public interface DocsAppService {

	@Retryable(value = {UserHandledException.class }, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public DocsappResponse callDocsAppService(InputRequest inputRequest) throws UserHandledException;

	@Recover
	public DocsappResponse getDocsApiResponseFallback(UserHandledException e,InputRequest inputRequest);

}
