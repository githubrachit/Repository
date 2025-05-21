package com.mli.mpro.tmb.service;

import com.mli.mpro.tmb.model.AccountDetailsResponse;
import com.mli.mpro.tmb.model.CustomerDetailsResponse;
import com.mli.mpro.tmb.model.OnboardingState;
import com.mli.mpro.tmb.model.SaveProposalRequest;
import org.springframework.stereotype.Service;

@Service
public interface CustomerDetailsService {

    CustomerDetailsResponse fetchCustomerDetails(OnboardingState onboardingState,SaveProposalRequest request);

    AccountDetailsResponse fetchAccountDetails(OnboardingState onboardingState,SaveProposalRequest request);
}
