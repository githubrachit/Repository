package com.mli.mpro.onboarding.partner.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.service.SequenceService;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.model.*;
import com.mli.mpro.onboarding.partner.service.impl.ProposalNumberValidationServiceImpl;
import com.mli.mpro.onboarding.partner.service.impl.RetryableInvokeService;
import com.mli.mpro.onboarding.partner.validation.Validation;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.onboarding.util.EncryptionDecryptionTransformUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mli.mpro.productRestriction.util.AppConstants.*;


@Component("proposalNumberServiceHandler")
public class ProposalNumberServiceHandler extends APIServiceHandler {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private RetryableInvokeService retryableInvokeService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProposalNumberValidationServiceImpl proposalNumberValidationServiceImpl;

    @Autowired
    private AuditService auditService;

    private static final Logger logger = LoggerFactory.getLogger(ProposalNumberServiceHandler.class);



    @Value("${urlDetails.policyNoGenerator}")
    private String policyNumberURL;


    @Override
    public Validation validate(String inputRequest) throws JsonProcessingException {
        logger.info("Proposal form generation's request validation called");
        return proposalNumberValidationServiceImpl.validateRequest(inputRequest);
    }

    @Override
    public String setValidationErrorResponse(MsgInfo msgInfo) throws JsonProcessingException {
        ProposalNumberOutputResponse proposalNumberOutputResponse = new ProposalNumberOutputResponse();
        ProposalNumberAPIResponse proposalAPIResponse = new ProposalNumberAPIResponse();
        ProposalResponseData responseData = new ProposalResponseData();
        proposalAPIResponse.setMsgInfo(msgInfo);
        proposalAPIResponse.setResponseData(responseData);
        proposalNumberOutputResponse.setResponse(proposalAPIResponse);
        return objectMapper.writeValueAsString(proposalNumberOutputResponse);
    }

    @Override
    public SOAResponse invokeService(InputRequest inputRequest) throws Exception {
        logger.info("Proposal Number invokeService called");
        if(ObjectUtils.isEmpty(inputRequest.getProposalNumberPayload().get(0).getTransactionId())){
            inputRequest.getProposalNumberPayload().get(0).setTransactionId(DEFAULT);}
        SOAResponse response = invokeProposalNumberAPIService(inputRequest);
        return response;
    }

    private SOAResponse invokeProposalNumberAPIService(InputRequest inputRequest) throws Exception {
        Request request = new Request();
        SOARequest soaRequest = new SOARequest();
        ProposalNumberSOAResponse proposalNumberSOAResponse = new ProposalNumberSOAResponse();
        List<ProposalNumberPayload> payload = inputRequest.getProposalNumberPayload();
        com.mli.mpro.onboarding.partner.model.proposalNumber.ProposalNumberResponse responseFromSOA = new com.mli.mpro.onboarding.partner.model.proposalNumber.ProposalNumberResponse();
        ProposalNumberResponse responseSoaFinal = new ProposalNumberResponse();
        List<ProposalNumberSOAResponsePayload> proposalNumberResponseFinal = new ArrayList<>();
        MsgInfo msgInfo = new MsgInfo();


        List<Long> proposalNumberList = new ArrayList<>();
        List<ProposalNumberSOAResponsePayload> responseList = new ArrayList<>();
        try {
            for ( ProposalNumberPayload userpayload:  payload ) {
                //TODO need to handle combo here by if condition - for combo and make two payload trad and ulip
                if (null != userpayload && userpayload.getFamilyType().equalsIgnoreCase("combo")){
                    for(int i = 0; i<2 ; i++) {

                        if(i == 0){
                            userpayload.setFamilyType("ulip");
                        }else{
                            userpayload.setFamilyType("trad");
                            userpayload.setTransactionId(String.valueOf(sequenceService.getNextSequenceId(AppConstants.TRANSACTION_ID)));
                        }

                        soaRequest.setHeader(getHeaderProposalNumberAPI());
                        soaRequest.setPayload(getPayloadForCombo(userpayload));
                        request.setRequest(soaRequest);
                        proposalNumberResponseFinal.add(getProposalNumberResponse(request, responseFromSOA, userpayload));

                    }
                }else {

                    soaRequest.setHeader(getHeaderProposalNumberAPI());
                    soaRequest.setPayload(getPayloadForProposalNumberAPI(userpayload));
                    request.setRequest(soaRequest);
                    proposalNumberResponseFinal.add(getProposalNumberResponse(request, responseFromSOA, userpayload));
                }

            }
// Setting default messge for success case
            proposalNumberSOAResponse.setMsginfo(getMsgInfoForProposalNumberAPI(msgInfo));
            proposalNumberSOAResponse.setPayload(proposalNumberResponseFinal);

            logger.info("ProposalNumber API soa request for transactionId is : {} ",proposalNumberSOAResponse);

        }catch (Exception ex){
            logger.error("Exception while calling ProposalNumber API and message is {} ",
                    ex.getMessage());
            throw ex;
        }
        return proposalNumberSOAResponse;

    }

    private  MsgInfo getMsgInfoForProposalNumberAPI(MsgInfo msgInfo) {
        msgInfo.setMsgCode(SUCCESS_RESPONSE);
        msgInfo.setMsg(STATUS_SUCCESS);
        msgInfo.setMsgDescription(AppConstants.RECORD_FOUND);
        return msgInfo;

    }

    private ProposalNumberSOAResponsePayload getProposalNumberResponse(Request request, com.mli.mpro.onboarding.partner.model.proposalNumber.ProposalNumberResponse responseFromSOA, ProposalNumberPayload userpayload) throws Exception {
        ProposalNumberResponse soaResponseFinal = new ProposalNumberResponse();

        String responseString = retryableInvokeService.callService(policyNumberURL,getHttpEntityProposalNumberAPI(request));
        responseFromSOA = objectMapper.readValue(responseString,com.mli.mpro.onboarding.partner.model.proposalNumber.ProposalNumberResponse.class);
        ProposalNumberSOAResponsePayload responseformap = new ProposalNumberSOAResponsePayload();
        ProposalNumberSOAResponse proposalNumberSOAResponse = new ProposalNumberSOAResponse();


        if(null != responseFromSOA
             && null != responseFromSOA.getResponse()
             && null != responseFromSOA.getResponse().getPayload()
             && null != responseFromSOA.getResponse().getPayload().getProposalNo()
             && !responseFromSOA.getResponse().getPayload().getProposalNo().isEmpty()) {

            responseformap.setChannel(userpayload.getChannel());
            responseformap.setFormType(userpayload.getFormType());
            responseformap.setTransactionId(userpayload.getTransactionId());
            responseformap.setFamilyType(userpayload.getFamilyType());
            responseformap.setProposalNo(responseFromSOA.getResponse().getPayload().getProposalNo());

        }else{
            logger.error("Policy Number not generated for requested payload :{} ", userpayload);
        }

        return responseformap;
    }

    private ProposalNumberSOARequestPayload getPayloadForCombo(ProposalNumberPayload userpayload) {
        ProposalNumberSOARequestPayload requestPayload = new ProposalNumberSOARequestPayload();
        requestPayload.setChannel("AGENCY");
        requestPayload.setFormType("Trad Combo/0617/Ver 1.4");
        requestPayload.setTransactionId(userpayload.getTransactionId());
        requestPayload.setFamilyType(userpayload.getFamilyType());
        return requestPayload;
    }

    private HttpEntity getHttpEntityProposalNumberAPI(Request inputRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(inputRequest, headers);
    }

    private ProposalNumberSOARequestPayload getPayloadForProposalNumberAPI(ProposalNumberPayload userpayload) {
        ProposalNumberSOARequestPayload requestPayload = new ProposalNumberSOARequestPayload();

        requestPayload.setChannel("AGENCY");
        requestPayload.setFormType("Trad Combo/0617/Ver 1.4");
        requestPayload.setTransactionId(userpayload.getTransactionId());
        requestPayload.setFamilyType(userpayload.getFamilyType());

            return requestPayload;
        }




    private Header getHeaderProposalNumberAPI() {
        Header header = new Header();
        header.setSoaAppId("mpro");
        header.setSoaCorrelationId(UUID.randomUUID().toString());
        return header;
    }

    @Override
    public RequestResponse transformResponse(SOAResponse soaResponse, InputRequest inputRequest) throws JsonProcessingException, UserHandledException {
        RequestResponse requestResponse = new RequestResponse();
        ProposalNumberOutputResponse proposalOutputResponse = new ProposalNumberOutputResponse();
        try {
            List<ProposalNumberSOAResponsePayload> soaResponsePayloadList = new ArrayList<>();
            for (ProposalNumberSOAResponsePayload var:((ProposalNumberSOAResponse)soaResponse).getPayload()) {
                soaResponsePayloadList.add(var);

            }

            ProposalNumberAPIResponse response = new ProposalNumberAPIResponse();
            response.setMsgInfo(soaResponse.getMsginfo());
            ProposalResponseData responseData = new ProposalResponseData();
            responseData.setProposalNumberResponse(soaResponsePayloadList);
            response.setResponseData(responseData);
            proposalOutputResponse.setResponse(response);
            String payload = getObjectMapper().writeValueAsString(proposalOutputResponse);

            requestResponse.setPayload(payload);

        }catch (Exception ex){
            logger.error("Getting Exception {} while transforming the proposal number response",ex.getMessage());
        }
        auditService.saveAuditTransactionDetails(getAuditDetails(inputRequest.getProposalNumberPayload(),proposalOutputResponse));
        logger.info("Proposal number service handler transformResponse done");
        return requestResponse;
    }

    private AuditingDetails getAuditDetails(List<ProposalNumberPayload> request, ProposalNumberOutputResponse response) {
        AuditingDetails auditingDetails = new AuditingDetails();
        ResponseObject responseObject = new ResponseObject();
        responseObject.setAdditionalProperty(RESPONSE, response);
        auditingDetails.setAdditionalProperty(REQUEST, request);
        auditingDetails.setResponseObject(responseObject);
        auditingDetails.setServiceName(AppConstants.PROPOSAL_NUMBER_API);
        logger.info("Setting auditing details for Proposal Number API is {}",auditingDetails);
        return auditingDetails;

    }

    @Override
    public String encryptErrorResponse(Exception ex, String key) throws Exception {
        ProposalNumberOutputResponse proposalNumberOutputResponse = new ProposalNumberOutputResponse();
        ProposalNumberAPIResponse proposalNumberAPIResponse = new ProposalNumberAPIResponse();
        if(ex instanceof UserHandledException) {
            logger.error("User Handled exception occurred for request object {}", Util.getExceptionAsString(ex));

            MsgInfo msgInfo = new MsgInfo(AppConstants.BAD_REQUEST_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);
            proposalNumberAPIResponse.setMsgInfo(msgInfo);
            proposalNumberOutputResponse.setResponse(proposalNumberAPIResponse);
        }else {
            MsgInfo msgInfo = new MsgInfo(AppConstants.INTERNAL_SERVER_ERROR_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);
            proposalNumberAPIResponse.setMsgInfo(msgInfo);
            proposalNumberOutputResponse.setResponse(proposalNumberAPIResponse);
            logger.info("Exception Response Message : {}",proposalNumberOutputResponse);
        }

        return EncryptionDecryptionTransformUtil.encrypt(objectMapper.writeValueAsString(proposalNumberOutputResponse),key);
    }

    @Override
    public RequestResponse processRsponse(RequestResponse requestResponse) {
        logger.info("ProposalNumber processResponse called");
        return requestResponse;
    }

}
