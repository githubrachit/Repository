package com.mli.mpro.location.services;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.routingposv.models.InputMessageRequest;

public interface RoutePosvService {
	
	String executeRoutingProcess(InputMessageRequest inputRequest) throws UserHandledException;

	

	@Retryable(value = {UserHandledException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    String callPiPosvApi(InputMessageRequest inputRequest) throws UserHandledException ;
}
