package com.mli.mpro.location.services;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.emailPolicyPack.SoaResponseData;
import org.springframework.stereotype.Service;

@Service
public interface EmailPolicyService {

    public SoaResponseData fetchPolicyStatus(InputRequest inputRequest) throws UserHandledException;

}
