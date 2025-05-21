package com.mli.mpro.location.soa.service;

import com.mli.mpro.location.newApplication.model.NewApplicationRequest;
import com.mli.mpro.location.newApplication.model.OutputResponse;
import org.springframework.http.ResponseEntity;

public interface NewApplicationService {

	ResponseEntity<OutputResponse> executeNewApplicationService(NewApplicationRequest newApplicationRequest);

}