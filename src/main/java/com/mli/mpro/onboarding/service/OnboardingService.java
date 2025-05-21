package com.mli.mpro.onboarding.service;

import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;

public interface OnboardingService {

    RequestResponse viewPolicyStatus(RequestResponse inputPayload, @RequestHeader MultiValueMap<String, String> headerMap,ErrorResponseParams errorResponseParams);
    RequestResponse getDocumentsList(RequestResponse inputPayload);
    RequestResponse getIllustrationPdf(RequestResponse inputPayload,@RequestHeader MultiValueMap<String, String> headerMap,ErrorResponseParams errorResponseParams);
    com.mli.mpro.productRestriction.models.RequestResponse getReunion(com.mli.mpro.productRestriction.models.RequestResponse inputRequest, MultiValueMap<String, String> headerMap);
    RequestResponse getMedicalgridDetails(RequestResponse inputRequest);
}
