package com.mli.mpro.location.services;

import com.mli.mpro.bajajCapital.models.BajajSellerDataResponsePayload;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;

public interface BajajSellerDataService {
    public BajajSellerDataResponsePayload checkAgentApplicability(InputRequest inputRequest) throws UserHandledException;
}
