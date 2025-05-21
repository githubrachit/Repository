package com.mli.mpro.psm.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import com.mli.mpro.productRestriction.models.RequestResponse;
import com.mli.mpro.productRestriction.models.common.ErrorResponse;
import com.mli.mpro.productRestriction.service.impl.ProductRestrictionServiceImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.psm.Exception.CustomErrorMessagePsm;
import com.mli.mpro.psm.config.SchemaConfig;
import com.mli.mpro.psm.model.*;
import com.mli.mpro.psm.service.PsmService;
import com.mli.mpro.psm.utility.Util;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import com.mli.mpro.utils.RSAEncryptionDecryptionUtil;
import com.networknt.schema.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.CBC_ALGO_PADDING1;


@Service
public class PsmServiceImpl implements PsmService {

    private static final Logger logger = LoggerFactory.getLogger(PsmServiceImpl.class);


    @Value("${urlDetails.psmDetails.url}")
    private String url;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomErrorMessagePsm customErrorMessage;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ProductRestrictionServiceImpl productRestrictionService;

    @Autowired
    private RSAEncryptionDecryptionUtil rsaEncryptionDecryptionUtil;


    @Override
    public RequestResponse getPsmDetails(RequestResponse inputPayload, @RequestHeader MultiValueMap<String, String> headerMap, ErrorResponseParams errorResponseParams) throws UserHandledException {

        String encryptionSource=null;
        String decryptedString=null;
        String kek=null;
        InputRequest inputRequest = new InputRequest();
        RequestResponse requestResponse = new RequestResponse();;
        String requestId = (UUID.randomUUID().toString());
        OutputResponse outputResponse = new OutputResponse();
        try {
            encryptionSource=productRestrictionService.getEncryptionSource(headerMap);
            if(AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)){
                kek = headerMap.getFirst("kek");
                logger.info("Inside ujjivan utility kek {} " , kek);
                errorResponseParams.setEncryptionSource(encryptionSource);
                decryptedString = productRestrictionService.decryption(inputPayload.getPayload(), kek, errorResponseParams);
                //utility code for Ujjivan
                logger.info("Inside ujjivan utility decryptedString {} " , decryptedString);
            }
            else {
                decryptedString = Util.decryptRequest(inputPayload.getPayload());
            }
            inputRequest = deserializeRequest(decryptedString);


            //Json schema validation
            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(decryptedString);
            Set<ValidationMessage> errorSets = SchemaConfig.schema.validate(jsonNode);
            Set<ErrorResponse> errors = new HashSet<>();
            for (ValidationMessage e : errorSets) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setErrorCode(customErrorMessage.getCustomErrorCodeList().get(e.getMessage().split(":")[0].substring(2)));
                errorResponse.setMessage(customErrorMessage.getCustomErrorMessageList().get(e.getMessage().split(":")[0].substring(2)));
                logger.error("Json validation error:{}", errorResponse);
                errors.add(errorResponse);
            }

            if (!errors.isEmpty()) {
                return Util.errorResponse(HttpStatus.BAD_REQUEST, new ArrayList<>(errors), errorResponseParams.getEncryptionSource(), errorResponseParams.getIVandKey());
            }

            Header header = new Header();
            requestId = inputRequest.getRequest().getHeader().getSoaCorrelationId();
            header.setSoaCorrelationId(UUID.randomUUID().toString());
            header.setSoaAppId(AppConstants.FULFILLMENT);
            inputRequest.getRequest().setHeader(header);
            logger.info("inputRequest:{},SoaCorrelationId:{},requestId:{}", inputRequest, header.getSoaCorrelationId(), requestId);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<Object> request = new HttpEntity<>(inputRequest, headers);
            ResponseEntity<SOAResponse> response = new RestTemplate().postForEntity(url, request, SOAResponse.class);
            logger.info("SOA Response:{}", response);
            SOAResponse responseBody = response.getBody();

            PsmResponse psmResponse = null;

            if (responseBody != null) {
                psmResponse = new PsmResponse(responseBody.getPsmResponse().getMsgInfo(), responseBody.getPsmResponse().getPsmResponsePayload());

            }


            outputResponse.setPsmResponse(psmResponse);
            logger.info("OutputResponse:{},SoaCorrelationId:{},requestId:{}", outputResponse, header.getSoaCorrelationId(), requestId);

            if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
                requestResponse.setPayload(EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse), errorResponseParams.getIVandKey()[0], errorResponseParams.getIVandKey()[1].getBytes()));
            }else {
                requestResponse = Util.encryptResponse(outputResponse);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            saveRequestResponseInAuditDB(inputRequest, requestId, outputResponse);
        }
        return requestResponse;
    }

    private InputRequest deserializeRequest(String decryptedString) throws IOException {

        return objectMapper.readValue(decryptedString, InputRequest.class);

    }

    private void saveRequestResponseInAuditDB(InputRequest inputRequest, String requestId, OutputResponse response) throws UserHandledException {
        try {
            AuditingDetails auditDetails = new AuditingDetails();
            auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
            auditDetails.setServiceName(AppConstants.PSM_SERVICE);
            auditDetails.setAuditId(requestId);
            auditDetails.setRequestId(requestId);
            ResponseObject responseObject = new ResponseObject();
            responseObject.setAdditionalProperty(AppConstants.RESPONSE, response);
            auditDetails.setResponseObject(responseObject);
            auditService.saveAuditTransactionDetailsForAgentSelf(auditDetails, requestId);
        } catch (Exception e) {
            logger.info("Exception occured while saving in auditing for SoaCorrelationId-{}", requestId);

        }

    }
}