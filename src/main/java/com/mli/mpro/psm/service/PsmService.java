package com.mli.mpro.psm.service;


import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import com.mli.mpro.productRestriction.models.RequestResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;

public interface PsmService {

    RequestResponse getPsmDetails(RequestResponse inputPayload, @RequestHeader MultiValueMap<String, String> headerMap, ErrorResponseParams errorResponseParams) throws UserHandledException;

}
