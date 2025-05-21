package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.pratham.models.MessageInputRequest;

public interface PrathamService {
	
	String executePrathamService(MessageInputRequest inputRequest) throws UserHandledException;
	
	String sendEmail(String proposalNo, long transactionId) throws UserHandledException;

}
