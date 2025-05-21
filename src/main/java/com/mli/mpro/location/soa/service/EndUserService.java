package com.mli.mpro.location.soa.service;

import com.mli.mpro.location.endUser.models.EndUserRequest;
import org.springframework.http.ResponseEntity;

public interface EndUserService {
    ResponseEntity<Object> endUserAPI(EndUserRequest endUserRequest);
}

