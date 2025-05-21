package com.mli.mpro.productRestriction.service;

import com.mli.mpro.productRestriction.models.InputRequest;
import com.mli.mpro.productRestriction.models.common.ErrorResponse;

import java.util.Set;

public interface ValidationService {

    void validateRequest(InputRequest inputRequest, Set<ErrorResponse> errors);
}
