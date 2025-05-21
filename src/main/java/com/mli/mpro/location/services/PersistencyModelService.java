package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface PersistencyModelService {
    @Recover
    void recover(UserHandledException exception, ProposalDetails proposalDetails);
}
