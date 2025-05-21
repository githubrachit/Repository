package com.mli.mpro.location.soa.service;

import org.springframework.http.ResponseEntity;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.panDOBVerification.model.PanDobRequest;

public interface PanDobVerificationService {

	ResponseEntity<Object> executePanDobVerification(PanDobRequest panDobRequest) throws UserHandledException;

}
