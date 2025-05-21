package com.mli.mpro.ccms.service;

import com.mli.mpro.ccms.model.AccessAuthTokenResponse;
import com.mli.mpro.common.exception.UserHandledException;

public interface AccessAuthTokenService {
    AccessAuthTokenResponse getAuthToken() throws UserHandledException;
}
