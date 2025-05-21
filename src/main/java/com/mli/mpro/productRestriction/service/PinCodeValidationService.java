package com.mli.mpro.productRestriction.service;

import com.mli.mpro.productRestriction.models.InputPayload;
import com.mli.mpro.productRestriction.models.OutputPayload;

public interface PinCodeValidationService {
    OutputPayload getAllowedSumAssured(InputPayload requestPayload);
}
