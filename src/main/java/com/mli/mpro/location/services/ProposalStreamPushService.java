package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.proposal.models.InputRequest;

public interface ProposalStreamPushService {
	
	 boolean produceToProposalStream(InputRequest inputRequest) throws UserHandledException;
	 boolean produceToProposalQueue(InputRequest inputRequest) throws UserHandledException;

}
