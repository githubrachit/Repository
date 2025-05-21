package com.mli.mpro.bankdetails.service;

import com.mli.mpro.bankdetails.model.SOABankResponse;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.ifsc.model.RequestData;
import org.springframework.retry.annotation.Retryable;


public interface BankDetailsService {

    @Retryable(value = {Exception.class}, maxAttempts = 3)
    public SOABankResponse fetchBankDetails(RequestData bankDetailsRequest, String authToken) throws UserHandledException;

}