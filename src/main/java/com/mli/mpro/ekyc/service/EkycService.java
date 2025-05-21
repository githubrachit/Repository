package com.mli.mpro.ekyc.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.ekyc.model.*;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;



public interface EkycService {
     @Retryable(value = {UserHandledException.class},maxAttempts = 3)
     EkycResponsePayload sendingOrValidatingOtp(EkycPayload inputRequest) throws UserHandledException;

     @Recover
     EkycResponsePayload finalErrorResponse(Exception e,EkycPayload ekycPayload);
}
