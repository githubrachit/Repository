package com.mli.mpro.document.service;

import java.util.List;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.InputRequest;

public interface DocumentGenerationRetryService {

    public List<Object> executeRetryForDocumentFailure(InputRequest inputRequest) throws UserHandledException;

}
