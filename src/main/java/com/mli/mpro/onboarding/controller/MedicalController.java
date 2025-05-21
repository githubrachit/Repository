package com.mli.mpro.onboarding.controller;


import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.service.MedicalService;
import com.mli.mpro.onboarding.service.OnboardingService;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(path = "/locationservices/onboarding/medical")
public class MedicalController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalController.class);

    @Autowired
    private MedicalService medicalService;

    @PostMapping(path = "/center-list")
    public RequestResponse getMedicalCenterList(@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap) {
        RequestResponse responsePayload;
        long requestedTime = System.currentTimeMillis();
        responsePayload = medicalService.getMedicalCenterList(inputRequest,headerMap);
        logger.info("Request took Time {} sec to process the request", Utility.getProcessedTime(requestedTime));
        return responsePayload;
    }
    @PostMapping(path = "/schedule")
    public RequestResponse getMedicalScheduling(@RequestBody RequestResponse inputRequest, @RequestHeader MultiValueMap<String, String> headerMap){
        RequestResponse responsePayload ;
        long requestedTime = System.currentTimeMillis();
        responsePayload=medicalService.getMedicalScheduling(inputRequest,headerMap);
        logger.info("Request took Time {} sec to process the request", Utility.getProcessedTime(requestedTime));
        return  responsePayload;
    }
}
