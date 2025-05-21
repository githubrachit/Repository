package com.mli.mpro.location.soa.service;

import org.springframework.http.ResponseEntity;

import com.mli.mpro.location.ifsc.model.IfscMicrRequest;

public interface IfscMicrService {

	ResponseEntity<Object> executeIfscMicrDataService(IfscMicrRequest ifscMicrRequest);
	

}
