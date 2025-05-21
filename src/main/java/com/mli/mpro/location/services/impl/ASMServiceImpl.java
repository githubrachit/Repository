package com.mli.mpro.location.services.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.services.ASMService;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ASMServiceImpl implements ASMService {
    private static final Logger logger = LoggerFactory.getLogger(ASMServiceImpl.class);

    @Override
    public AWSSecretsManager createClient()  throws UserHandledException {
        AWSSecretsManager client = null;
        try {
            // Create a client.
            client = AWSSecretsManagerClientBuilder.standard()
                    .withRegion(Regions.AP_SOUTH_1)
                    .build();
        } catch (Exception ex) {
            logger.error("Cannot create AWS ASM client.", ex);
            throw new UserHandledException(List.of(ex.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("AWS ASM client created successfully.");
        return client;
    }

    @Override
    public GetSecretValueResult getCertificate(AWSSecretsManager client, String secretName)  throws UserHandledException{
        GetSecretValueResult getSecretValueResult = null;
        // Create a request object and set the secret ID.
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        // Retrieve the secret.
        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception ex) {
            logger.error("Error retrieving the secret value.", ex);
            throw new UserHandledException(List.of(ex.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return getSecretValueResult;
    }

}
