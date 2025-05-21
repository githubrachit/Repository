package com.mli.mpro.onboarding.service;

import com.mli.mpro.onboarding.model.RequestResponse;
import org.springframework.util.MultiValueMap;

public interface MedicalService {
    RequestResponse getMedicalCenterList(RequestResponse inputPayload, MultiValueMap<String, String> headerMap);

    RequestResponse getMedicalScheduling(RequestResponse inputPayload, MultiValueMap<String, String> headerMap);

}
