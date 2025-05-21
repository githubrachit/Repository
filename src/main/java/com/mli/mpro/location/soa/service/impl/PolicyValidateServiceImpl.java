package com.mli.mpro.location.soa.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.policyNumberValidate.models.*;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.location.services.impl.SoaCloudServiceImpl;
import com.mli.mpro.location.soa.service.PolicyValidateService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.OutputResponse;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Service
public class PolicyValidateServiceImpl implements PolicyValidateService {
    private static final Logger logger = LoggerFactory.getLogger(PolicyValidateServiceImpl.class);

    @Autowired
    private SoaCloudService soaCloudService;
    @Value("${urlDetails.policyNumber.validateUrl}")
    private String validateUrl;


    @Override
    public com.mli.mpro.proposal.models.OutputResponse validatePolicyNumber(InputRequest inputRequest) throws UserHandledException {
        String policyStatus = "";
        try {
            if(!(Utility.isAnyObjectNull(inputRequest,inputRequest.getRequest(),inputRequest.getRequest().getRequestData(),inputRequest.getRequest().getRequestData().getRequestPayload(),inputRequest.getRequest().getRequestData().getRequestPayload().getPolicyNumbers()) && inputRequest.getRequest().getRequestData().getRequestPayload().getPolicyNumbers().isEmpty())){
                String policyNumber = inputRequest.getRequest().getRequestData().getRequestPayload().getPolicyNumbers().get(0);
                logger.info("Input Request Received validating the policy Number {} ",policyNumber);
                SoaInputRequest request = createRequestForPolicyValidate(policyNumber);
                ResponseEntity<?> responseEntity = soaCloudService.callingSoaApi(request, validateUrl);
                if (responseEntity != null && responseEntity.getBody() != null) {
                    logger.info("Policy Number Service called successfully for the policyNumber {} ",policyNumber);
                    SoaOutputResponse soaOutputResponse = new ObjectMapper().convertValue(responseEntity.getBody(), SoaOutputResponse.class);
                    logger.info("Response Received from Policy Number Service for the policyNumber {} , {} ",policyNumber,responseEntity.getBody());
                    if (checkingNonNullObjects(soaOutputResponse)) {
                        policyStatus= soaOutputResponse.getSoaResponse().getSoaResponsePayload().getProposalValidations().get(0).getStatus();
                    }else
                        policyStatus= AppConstants.VALIDATE_POLICY_SERVICE_ERROR;

                }else{
                    policyStatus=AppConstants.VALIDATE_POLICY_SERVICE_ERROR;
                }
            }
        } catch (Exception e) {
            throw new UserHandledException(Collections.singletonList("Exception during calling Policy Validate Api Ex - " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        com.mli.mpro.proposal.models.ResponsePayload responsePayload=new com.mli.mpro.proposal.models.ResponsePayload();
        responsePayload.setPolicyStatus(policyStatus);
        com.mli.mpro.proposal.models.ResponseData responseData=new com.mli.mpro.proposal.models.ResponseData(responsePayload);
        com.mli.mpro.proposal.models.Response response=new com.mli.mpro.proposal.models.Response();
        response.setResponseData(responseData);
        return new OutputResponse(response);
    }



    private SoaInputRequest createRequestForPolicyValidate(String policyNumber) {
        List<String> policyList=new ArrayList<>();
        policyList.add(policyNumber);
        SoaRequestPayload requestPayload=new SoaRequestPayload(policyList);
        Header header=new Header(UUID.randomUUID().toString(), AppConstants.FULFILLMENT);
        SoaRequest request= new SoaRequest(header,requestPayload);
        return new SoaInputRequest(request);
    }

    private boolean checkingNonNullObjects(SoaOutputResponse soaOutputResponse) {
        if(!Utility.isAnyObjectNull(soaOutputResponse.getSoaResponse(),soaOutputResponse.getSoaResponse().getMsgInfo(),soaOutputResponse.getSoaResponse().getMsgInfo().getMsgCode()) && (AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(soaOutputResponse.getSoaResponse().getMsgInfo().getMsgCode()))){
            return !(Utility.isAnyObjectNull(soaOutputResponse.getSoaResponse(),soaOutputResponse.getSoaResponse().getSoaResponsePayload(),soaOutputResponse.getSoaResponse().getSoaResponsePayload().getProposalValidations()) && soaOutputResponse.getSoaResponse().getSoaResponsePayload().getProposalValidations().isEmpty()) ;
        }
        return false;
    }

    public com.mli.mpro.proposal.models.OutputResponse finalPolicyStatusResponse(UserHandledException ex){
        com.mli.mpro.proposal.models.ResponsePayload responsePayload=new com.mli.mpro.proposal.models.ResponsePayload();
        responsePayload.setPolicyStatus(AppConstants.VALIDATE_POLICY_SERVICE_ERROR);
        com.mli.mpro.proposal.models.ResponseData responseData=new com.mli.mpro.proposal.models.ResponseData(responsePayload);
        com.mli.mpro.proposal.models.Response response=new com.mli.mpro.proposal.models.Response();
        response.setResponseData(responseData);
        return new OutputResponse(response);

    }

}
