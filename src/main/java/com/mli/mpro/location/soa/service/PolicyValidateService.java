package com.mli.mpro.location.soa.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.InputRequest;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;


public interface PolicyValidateService {

    @Retryable(value = {UserHandledException.class},maxAttempts = 3)
    com.mli.mpro.proposal.models.OutputResponse validatePolicyNumber(InputRequest inputRequest) throws UserHandledException;
    @Recover
    com.mli.mpro.proposal.models.OutputResponse finalPolicyStatusResponse(UserHandledException ex);
}
