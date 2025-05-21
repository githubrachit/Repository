package com.mli.mpro.onboarding.service;

import com.mli.mpro.onboarding.model.RequestResponse;
import org.springframework.util.MultiValueMap;

public interface SuperAppService {
    RequestResponse fetchDashboardCount(RequestResponse inputRequest, MultiValueMap<String, String> authToken);

    RequestResponse redirectNewApplication(RequestResponse inputRequest, MultiValueMap<String, String> authToken);

    RequestResponse sendErrorResponse(String msgCode,String msg,String msgDescription,MultiValueMap<String, String> headers);
}
