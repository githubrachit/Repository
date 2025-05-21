package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.location.models.IIBResponsePayload;
public interface IIBService {
    IIBResponsePayload executeIIBSerive( InputRequest inputRequest) throws UserHandledException;
}
