package com.mli.mpro.location.services.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.certificatemanager.AWSCertificateManager;
import com.amazonaws.services.certificatemanager.AWSCertificateManagerClientBuilder;
import com.amazonaws.services.certificatemanager.model.*;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.services.ACMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ACMServiceImpl implements ACMService {

    private static final Logger logger = LoggerFactory.getLogger(ACMServiceImpl.class);
    private final long totalTimeout = 120000l;
    private long timeSlept = 0l;
    private final long sleepInterval = 10000l;

    @Override
    public AWSCertificateManager createClient()  throws UserHandledException {
        AWSCertificateManager client = null;
        try {
            // Create a client.
            client = AWSCertificateManagerClientBuilder.standard()
                    .withRegion(Regions.AP_SOUTH_1)
                    .build();
        }
        catch (Exception ex) {
            logger.error("Cannot create AWS ACM client.", ex);
            throw new UserHandledException(List.of(ex.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("AWS ACM client created successfully.");
        return client;
    }

    @Override
    public GetCertificateResult getCertificate(AWSCertificateManager client, String certificateArn)  throws UserHandledException{
        logger.info("Retrieving certificate with ARN: " + certificateArn);
        // Create a request object and set the ARN of the certificate to be described.
        GetCertificateRequest req = new GetCertificateRequest().withCertificateArn(certificateArn);

        // Retrieve the certificate and certificate chain.
        GetCertificateResult result = null;
        while (result == null && timeSlept < totalTimeout) {
            try {
                result = client.getCertificate(req);
            }
            catch (RequestInProgressException ex) {
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    logger.error("Thread sleep interrupted.", e);
                    throw new UserHandledException(List.of(ex.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            catch (ResourceNotFoundException ex)
            {
                logger.error("Resource not found.", ex);
                throw new UserHandledException(List.of(ex.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            catch (InvalidArnException ex)
            {
                logger.error("Invalid ARN.", ex);
                throw new UserHandledException(List.of(ex.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            timeSlept += sleepInterval;
        }
        return result;
    }

}
