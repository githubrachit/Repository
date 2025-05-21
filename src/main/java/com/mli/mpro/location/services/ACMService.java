package com.mli.mpro.location.services;

import com.amazonaws.services.certificatemanager.AWSCertificateManager;
import com.amazonaws.services.certificatemanager.model.GetCertificateResult;
import com.mli.mpro.common.exception.UserHandledException;


public interface ACMService {
    AWSCertificateManager createClient()  throws UserHandledException;
    GetCertificateResult getCertificate(AWSCertificateManager client, String certificateArn)  throws UserHandledException;
}
