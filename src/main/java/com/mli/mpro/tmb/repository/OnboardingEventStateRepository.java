package com.mli.mpro.tmb.repository;

import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.tmb.model.OnboardingState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/*To get the configuration data from DB*/
public interface OnboardingEventStateRepository extends MongoRepository<OnboardingState, String> {


    OnboardingState findByTransactionId(Long transactionId);


}
