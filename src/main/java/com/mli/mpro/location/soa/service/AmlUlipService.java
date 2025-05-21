package com.mli.mpro.location.soa.service;

import com.mli.mpro.location.amlulip.training.model.SoaUlipRequest;
import org.springframework.http.ResponseEntity;

import com.mli.mpro.location.soa.exception.SoaCustomException;
import com.mli.mpro.location.training.model.TrainingRequest;

public interface AmlUlipService {
	
	ResponseEntity<Object> executeAmlUlipService(TrainingRequest trainingRequest) throws SoaCustomException;
	ResponseEntity<Object> executeULIPDataLakeService(SoaUlipRequest ulipRequest);

}
