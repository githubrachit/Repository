package com.mli.mpro.location.soa.service;

import org.springframework.http.ResponseEntity;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.clientAllPolicyDetails.model.PolicyDetailsRequest;

public interface ClientPolicyDetailsService {

	public ResponseEntity<Object> executeClientPolicyDetailsService(PolicyDetailsRequest policyDetailsRequest,
			String agentId) throws UserHandledException;

}
