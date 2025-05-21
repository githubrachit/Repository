package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.soaCloudModels.SoaAuthResponse;

public interface AuthTokenService {
    SoaAuthResponse getAuthToken(boolean isprivateCall) throws UserHandledException;
}
