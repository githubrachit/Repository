package com.mli.mpro.location.services;

import com.mli.mpro.location.altfinInquiry.output.AltfinSoaInputResponse;
import org.springframework.stereotype.Service;

@Service
public interface AltfinInquiryService {


    public AltfinSoaInputResponse executeAltFinInquiryApi(String policyNumber);

}
