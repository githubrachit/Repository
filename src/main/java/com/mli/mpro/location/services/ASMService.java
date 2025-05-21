package com.mli.mpro.location.services;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.mli.mpro.common.exception.UserHandledException;

public interface ASMService {
    AWSSecretsManager createClient()  throws UserHandledException;
    GetSecretValueResult getCertificate(AWSSecretsManager client, String secretName)  throws UserHandledException;

}
