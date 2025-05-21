package com.mli.mpro.tmb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.tmb.model.*;

public interface TmbService {

    ResponseForOtpApi sendOtp(InputRequestOtpApi inputRequestOtpApi);
     OutputRespone generateLinkForCustomer(Payload payload);

    ResponseForOtpApi verifyOtp(InputRequestOtpApi inputRequestOtpApi);

    OutputRespone getOnboardingStateStatus(OnboardingStatusRequest onboardingStatusRequest);

    void renewalPush(RenewalRequest renewalRequest) ;
}
