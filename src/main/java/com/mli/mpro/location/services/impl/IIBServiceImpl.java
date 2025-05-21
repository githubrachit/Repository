package com.mli.mpro.location.services.impl;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.location.models.IIBResponsePayload;
import com.mli.mpro.location.services.IIBService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class IIBServiceImpl implements IIBService {

    private static final Logger logging = LoggerFactory.getLogger(IIBServiceImpl.class);
    @Value("${urlDetails.iibUrl}")
    private String iibUrl;

    @Override
    public IIBResponsePayload executeIIBSerive(InputRequest inputRequest) throws UserHandledException {
        IIBResponsePayload response = null;
        long transactionId = inputRequest.getRequest().getRequestData().getIibRequestPayload().getTransactionId();
        try {
            logging.info("For transactionId {} , IIB request: {}", transactionId, inputRequest);
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForObject(iibUrl, inputRequest, IIBResponsePayload.class);
            logging.debug("For transactionId {} , IIB response received: {}", transactionId, response);
            logging.info("For transactionId {} , IIB response received successfully", transactionId);
        } catch (Exception exception) {
            logging.error("For transactionId {} , request failed: {} and exception: {}", transactionId, inputRequest, Utility.getExceptionAsString(exception));
            throw new UserHandledException(Arrays.asList(AppConstants.INTERNAL_SERVER_ERROR,exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
