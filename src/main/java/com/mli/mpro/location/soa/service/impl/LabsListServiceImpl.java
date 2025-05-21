package com.mli.mpro.location.soa.service.impl;

import static com.mli.mpro.productRestriction.util.AppConstants.STATUS_FAILURE;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.labslist.models.Category;
import com.mli.mpro.location.labslist.models.Data;
import com.mli.mpro.location.common.soa.model.Header;
import com.mli.mpro.location.labslist.models.LabsListRequest;
import com.mli.mpro.location.labslist.models.LabsListSoaPayload;
import com.mli.mpro.location.labslist.models.LabslistSoaResponse;
import com.mli.mpro.location.labslist.models.LabslistSoaResult;
import com.mli.mpro.location.common.soa.model.MsgInfo;
import com.mli.mpro.location.labslist.models.Payload;
import com.mli.mpro.location.labslist.models.PayloadRequest;
import com.mli.mpro.location.labslist.models.SoaResponse;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import com.mli.mpro.location.soa.service.LabsListService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

@Service
public class LabsListServiceImpl implements  LabsListService {
    @Value("${urlDetails.labslist}")
    private String url;
    @Value("${urlDetails.clientId}")
	private String cleintID;
	@Value("${urlDetails.secretKey}")
	private String secretKey;
    @Autowired
    private AuditService auditingService;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(LabsListServiceImpl.class);


    @Override
    public ResponseEntity<Object> executeLabsListService(LabsListRequest labsListRequest) {
        LabsListSoaPayload labsListSoaPayload = null;
        LabslistSoaResult soaResponse = null;
        LabslistSoaResponse labslistSoaResponse=new LabslistSoaResponse();
        log.info("Inside labs list soa call for request {}",labsListRequest);
        try {
            if (validateLabsListRequest(labsListRequest)) {
                Data data = labsListRequest.getRequest().getData();
                labsListSoaPayload = setLabsListPayload(data.getPincode(), data.getCity(), data.getState(), data.getVendor());
                soaResponse = labListSoaCall(labsListSoaPayload, labsListRequest);
                labslistSoaResponse.setResult(soaResponse);
                return new ResponseEntity<>(labslistSoaResponse, HttpStatus.OK);
            } else
                throw new SoaCustomException("Failed to parse Payload","Request object is invalid !!", "400");
        } catch (SoaCustomException e) {
            return  buildErrorResponse(e.getMsg(), e.getMsgDescription(), e.getMsgCode(),HttpStatus.OK);
        } catch (Exception e){
            log.error("Error in calling lab list API : {}",Utility.getExceptionAsString(e));
            return buildErrorResponse(STATUS_FAILURE,"Labs list api failed","500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateLabsListRequest(LabsListRequest labsListRequest) {
        if (labsListRequest != null && labsListRequest.getRequest() != null &&
                labsListRequest.getRequest().getData() != null) {
            Data data = labsListRequest.getRequest().getData();
            if (data.getCity() != null && data.getPincode() != null && data.getState() != null
                    && data.getVendor() != null) {
                return true;
            }
        }
        return false;
    }

    private LabsListSoaPayload setLabsListPayload(String pincode, String city, String state, String vendor) {
        LabsListSoaPayload labsListSoaPayload = new LabsListSoaPayload();
        PayloadRequest payloadRequest = new PayloadRequest();
        Header header = new Header();
        header.setSoaCorrelationId(UUID.randomUUID().toString());
        header.setSoaAppId("FULFILLMENT");
        Category category = new Category();
        category.setCategoryName("lab");
        category.setRequestType("labList");
        category.setVendor(vendor);
        Payload payload = new Payload();
        payload.setPincode(pincode);
        payload.setState(state);
        payload.setCity(city);
        payloadRequest.setHeader(header);
        payloadRequest.setCategory(category);
        payloadRequest.setPayload(payload);
        labsListSoaPayload.setRequest(payloadRequest);
        return labsListSoaPayload;
    }

    private LabslistSoaResult labListSoaCall(LabsListSoaPayload labsListSoaPayload, LabsListRequest labsListRequest) throws SoaCustomException, UserHandledException {
        ResponseEntity<LabslistSoaResult> responseEntity = null;
        log.info("Payload for lablist soa call : {}", labsListSoaPayload);
        LabslistSoaResult result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(labsListSoaPayload);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-IBM-Client-Id", cleintID);
            httpHeaders.set("X-IBM-Client-Secret", secretKey);
            httpHeaders.set("Content-Type","application/json");
            HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);
            responseEntity = restTemplate.postForEntity(url, entity, LabslistSoaResult.class);
            result = Objects.nonNull(responseEntity.getBody()) ? responseEntity.getBody() : null ;
            if (Objects.nonNull(result)  && responseEntity.getStatusCode() == HttpStatus.OK) {
                AuditingDetails auditingDetails = getAuditingDetails("200", String.valueOf(responseEntity.getStatusCode().value()), responseEntity ,labsListRequest.getRequest().getData(),labsListRequest);
                auditingService.saveAuditTransactionDetails(auditingDetails);
                log.info("Response from soa {}",responseEntity.getBody());
                return responseEntity.getBody();
            } else{
            	AuditingDetails auditingDetails = getAuditingDetails("500", String.valueOf(responseEntity.getStatusCode().value()), responseEntity ,labsListSoaPayload,labsListRequest);
                auditingService.saveAuditTransactionDetails(auditingDetails);
                throw new SoaCustomException(STATUS_FAILURE,"Failure connecting with the server","500");
            }
        } catch (Exception e) {
            AuditingDetails auditingDetails = getAuditingDetails("500", "500",e,labsListRequest.getRequest().getData(), labsListRequest);
            auditingService.saveAuditTransactionDetails(auditingDetails);
            log.error("Error while calling lab list API {}", Utility.getExceptionAsString(e));
            throw new SoaCustomException(STATUS_FAILURE,"Can not connect to SOA service","500");
        }
    }

    private AuditingDetails getAuditingDetails(String statusCode, String httpStatusCode,
                                               Object outputResponse, Object inputRequest, LabsListRequest labListRequest) {
        AuditingDetails auditDetails = new AuditingDetails();
        auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
        ResponseObject respoObject = new ResponseObject();
        respoObject.setAdditionalProperty(AppConstants.RESPONSE, outputResponse);
        auditDetails.setResponseObject(respoObject);
        auditDetails.setServiceName(SoaConstants.LAB_LIST_SERVICE_NAME);
        auditDetails.setAgentId(labListRequest.getRequest().getData().getAgentId());
        auditDetails.setHttpStatusCode(httpStatusCode);
        auditDetails.setStatusCode(statusCode);
        auditDetails.setRequestId(UUID.randomUUID().toString());
        return auditDetails;
    }

    private ResponseEntity<Object> buildErrorResponse(String msg, String msgDescription, String msgCode,
                                                      HttpStatus httpStatus){
        SoaResponse response = new SoaResponse();
        LabslistSoaResult labslistSoaResult = new LabslistSoaResult();
        MsgInfo msgInfo = new MsgInfo(msg,msgDescription,msgCode);
        response.setHeader(new Header());
        response.setMsgInfo(msgInfo);
        response.setPayload(new ArrayList<>());
        labslistSoaResult.setResponse(response);
        return new ResponseEntity<>(labslistSoaResult,httpStatus);
    }


}
