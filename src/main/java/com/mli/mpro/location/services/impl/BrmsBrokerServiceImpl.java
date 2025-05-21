package com.mli.mpro.location.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.brmsBroker.BrmsBrokerRequestPayload;
import com.mli.mpro.brmsBroker.BrmsBrokerResponsePayload;
import com.mli.mpro.brmsBroker.model.Header;
import com.mli.mpro.brmsBroker.model.InputResponse.InputResponse;
import com.mli.mpro.brmsBroker.model.Payload;
import com.mli.mpro.brmsBroker.model.Request;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.OutputResponse;
import com.mli.mpro.common.models.RequestData;
import com.mli.mpro.common.models.ResponseData;
import com.mli.mpro.location.services.BrmsBrokerService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
@Service
public class BrmsBrokerServiceImpl implements BrmsBrokerService {

    private Logger log = LoggerFactory.getLogger(BrmsBrokerServiceImpl.class);
   // @Value("${urlDetails.brmsBrokerUrl}")
    private String brmsBrokerUrl="https://gatewayclouduat.maxlifeinsurance.com/apimgm/dm/brokeronboardingrules";
    @Autowired
    AuditService auditService;
    @Retryable(value = {UserHandledException.class }, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public OutputResponse fetchBrmsResponse(InputRequest inputRequest, String uniqueId, ArrayList<String> errorMessages) throws JsonProcessingException, URISyntaxException, UserHandledException {
        ResponseEntity<com.mli.mpro.brmsBroker.model.InputResponse.InputResponse> response = null;
        OutputResponse outputResponse= new OutputResponse();
        com.mli.mpro.common.models.Response finalResponse = new com.mli.mpro.common.models.Response();
        ResponseData responseData = new ResponseData();
        BrmsBrokerResponsePayload brmsBrokerResponsePayload = new BrmsBrokerResponsePayload();
        URI brmsBrokerUri = new URI(brmsBrokerUrl);
        try {
            HttpEntity<com.mli.mpro.brmsBroker.model.InputRequest> httpEntity = setDataForBrms(inputRequest, uniqueId, errorMessages);
            log.info("Request before API call for unique Id{} is {}",uniqueId,httpEntity);
            response = new RestTemplate().postForEntity(brmsBrokerUri, httpEntity, InputResponse.class);
            brmsBrokerResponsePayload.setServiceStatus(AppConstants.STATUS_FAILURE);
            com.mli.mpro.brmsBroker.model.InputResponse.Response nullCheckResponse = Optional.ofNullable(response)
                                                                                              .map(ap -> ap.getBody())
                                                                                              .map(ap -> ap.getResponse())
                                                                                              .orElse(null);
            if (Objects.nonNull(nullCheckResponse)&& Objects.nonNull(nullCheckResponse.getPayload()) && Objects.nonNull(nullCheckResponse.getMsgInfo())
                  && AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(nullCheckResponse.getMsgInfo().getMsgCode())) {
                setBrmsBrokerPayload(brmsBrokerResponsePayload, nullCheckResponse.getPayload());
            }
            responseData.setBrmsBrokerResponsePayload(brmsBrokerResponsePayload);
            finalResponse.setResponseData(responseData);
            outputResponse.setResponse(finalResponse);
            log.info("The response received from brmsBroker service for uniqueId {} {} httpEntity - {}", uniqueId, response, httpEntity);
            AuditingDetails auditDetails = getAuditingDetails(inputRequest, uniqueId);
            ResponseObject respoObject = new ResponseObject();
            respoObject.setAdditionalProperty(AppConstants.RESPONSE, response);
            auditDetails.setResponseObject(respoObject);
            auditDetails.setRequestId(uniqueId);
            brmsBrokerResponsePayload.setAuditRequestId(uniqueId);
            auditService.saveAuditTransactionDetailsForAgentSelf(auditDetails, uniqueId);
        } catch (Exception e) {
            log.error("Exception occurred in fetchBrmsResponse for uniqueId {} ,{}", uniqueId, Utility.getExceptionAsString(e));
            errorMessages.add("Exception in fetchBrmsResponse " + e.getMessage());
            throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return outputResponse ;
    }

    private HttpEntity<com.mli.mpro.brmsBroker.model.InputRequest> setDataForBrms(InputRequest inputRequest, String uniqueId, ArrayList<String> errorMessages) throws JsonProcessingException, UserHandledException {
        Header header = new Header();
        HttpEntity<com.mli.mpro.brmsBroker.model.InputRequest> httpEntity = null;
        HttpHeaders headers = new HttpHeaders();
        com.mli.mpro.brmsBroker.model.InputRequest soaInputRequest = new com.mli.mpro.brmsBroker.model.InputRequest();
        Request request = new Request();
        Payload payload = new Payload();
        ObjectMapper requestMapper = new ObjectMapper();
        try {
            header.setSoaAppId("NEO");
            header.setSoaCorrelationId("2345689");
            headers.add("x-apigw-api-id", "xj2fplssn4");
            headers.add("x-api-key", "Sf4kOu9yOc6hctYYZxcJZ6v1nyHxuSfoaCFaxwVh");
            setPayload(inputRequest, payload);
            request.setHeader(header);
            BrmsBrokerRequestPayload brmsBrokerRequestPayload = Optional.ofNullable(inputRequest)
                                                                          .map(InputRequest::getRequest)
                                                                          .map(com.mli.mpro.common.models.Request::getRequestData)
                                                                          .map(RequestData::getBrmsBrokerRequestPayload)
                                                                          .orElse(null);
            if (Objects.nonNull(brmsBrokerRequestPayload)) {
                request.setPayload(payload);
            } else {
                log.error("null value received in brmsBrokerRequestPayload for uniqueId{}", uniqueId);
                errorMessages.add("null value received in brmsBrokerRequestPayload");
                throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            soaInputRequest.setRequest(request);
            httpEntity = new HttpEntity<>(soaInputRequest, headers);
            String inputRequestToPrint = requestMapper.writeValueAsString(soaInputRequest);
            log.info("Input request for BrmsBroker Header {}, Headers {} input req {} unique Id {}", header, headers, inputRequestToPrint, uniqueId);

        } catch (Exception e) {
            log.error("Exception occurred in setDataForBrms for uniqueId {} ,{}", uniqueId, e.getMessage());
            errorMessages.add("Exception in setDataForBrms " + e.getMessage());
            throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpEntity;
    }

    public void setPayload(InputRequest inputRequest, Payload payload) {
        payload.setChannel(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getChannel());
        payload.setBranchCode(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getBranchCode());
        payload.setSellerDesignationCode(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getSellerDesignationCode());
        payload.setCampaignID(AppConstants.ZERO);
        payload.setChannelIndicatorCAT(AppConstants.ZERO);
        payload.setChannelIndicatorDefence(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getChannelIndicatorDefence());
        payload.setCustomerClasificationCode(AppConstants.ZERO);
        payload.setDateOfAccountOpening(AppConstants.ZERO);
        payload.setDaysSinceAgentOnboarding(AppConstants.ZERO);
        payload.setLocationID(AppConstants.ZERO);
        payload.setNisSource(AppConstants.ZERO);
        payload.setSellerCode(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getSellerCode());
        payload.setSellerPersistancy(AppConstants.ZERO);
        payload.setSourcingSystem(AppConstants.ZERO);
        payload.setTag1(AppConstants.ZERO);
        payload.setTag2(AppConstants.ZERO);
        payload.setTag3(AppConstants.ZERO);
        payload.setTag4(AppConstants.ZERO);
        payload.setTag5(AppConstants.ZERO);
        if (StringUtils.hasText(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getChannelIndicatorJ3())) {
            payload.setChannelIndicatorJ3(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getChannelIndicatorJ3());
            payload.setTag1(inputRequest.getRequest().getRequestData().getBrmsBrokerRequestPayload().getTag1());
        }
    }

    private AuditingDetails getAuditingDetails(InputRequest inputRequest, String uniqueId) {
        AuditingDetails auditDetails = new AuditingDetails();
        auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
        auditDetails.setTransactionId(0);
        auditDetails.setServiceName(AppConstants.BRMS_BROKER);
        auditDetails.setAuditId(uniqueId);
        auditDetails.setRequestId(uniqueId);
        return auditDetails;
    }

    private void setBrmsBrokerPayload(BrmsBrokerResponsePayload brmsBrokerResponsePayload, com.mli.mpro.brmsBroker.model.InputResponse.Payload finalPayload) {
        brmsBrokerResponsePayload.setProductRestricted(finalPayload.getProductRestricted());
        brmsBrokerResponsePayload.setProductRestrictMessage(finalPayload.getProductRestrictMessage());
        brmsBrokerResponsePayload.setCampaignID(finalPayload.getCampaignID());
        brmsBrokerResponsePayload.setCampaignLocation(finalPayload.getCampaignLocation());
        brmsBrokerResponsePayload.setCustIdDataType(finalPayload.getCustIdDataType());
        brmsBrokerResponsePayload.setCustIdLength(finalPayload.getCustIdLength());
        brmsBrokerResponsePayload.setCustomerID(Objects.nonNull(finalPayload.getCustomerID())?finalPayload.getCustomerID():"N");
        brmsBrokerResponsePayload.setReferenceId(finalPayload.getReferenceId());
        brmsBrokerResponsePayload.setRefIdDataType(finalPayload.getRefIdDataType());
        brmsBrokerResponsePayload.setRefIdLength(finalPayload.getRefIdLength());
        brmsBrokerResponsePayload.setForm1(finalPayload.getForm1());
        brmsBrokerResponsePayload.setForm2(finalPayload.getForm2());
        brmsBrokerResponsePayload.setFormC(finalPayload.getFormC());
        brmsBrokerResponsePayload.setDigitalJourney(finalPayload.getDigitalJourney());
        brmsBrokerResponsePayload.setIsBRMSApiApplicable(finalPayload.getIsBRMSApiApplicable());
        brmsBrokerResponsePayload.setpOSPJourney(finalPayload.getpOSPJourney());
        brmsBrokerResponsePayload.setIndianNationaility(finalPayload.getIndianNationaility());
        brmsBrokerResponsePayload.setnRInationality(finalPayload.getnRInationality());
        brmsBrokerResponsePayload.setlEChannelCode(finalPayload.getlEChannelCode());
        brmsBrokerResponsePayload.setlEChannelName(finalPayload.getlEChannelName());
        brmsBrokerResponsePayload.setProductAnnualMode(finalPayload.getProductAnnualMode());
        brmsBrokerResponsePayload.setProductMinPrem(finalPayload.getProductMinPrem());
        brmsBrokerResponsePayload.setProductModeCode(finalPayload.getProductModeCode());
        brmsBrokerResponsePayload.setProductMonthlyMode(finalPayload.getProductMonthlyMode());
        brmsBrokerResponsePayload.setProductQuarterlyMode(finalPayload.getProductQuarterlyMode());
        brmsBrokerResponsePayload.setProductSemiMode(finalPayload.getProductSemiMode());
        brmsBrokerResponsePayload.setCashOption(finalPayload.getCashOption());
        brmsBrokerResponsePayload.setChequeOption(finalPayload.getChequeOption());
        brmsBrokerResponsePayload.setdDOption(finalPayload.getdDOption());
        brmsBrokerResponsePayload.setDirectDebitMannualOption(finalPayload.getDirectDebitMannualOption());
        brmsBrokerResponsePayload.setOnlineOption(finalPayload.getOnlineOption());
        brmsBrokerResponsePayload.setRenewalDirectDebit(finalPayload.getRenewalDirectDebit());
        brmsBrokerResponsePayload.setRenewalEnach(finalPayload.getRenewalEnach());
        brmsBrokerResponsePayload.setRenewalCheque(finalPayload.getRenewalCheque());
        brmsBrokerResponsePayload.setRenewalECS(finalPayload.getRenewalECS());
        brmsBrokerResponsePayload.setSplitPayment(finalPayload.getSplitPayment());
        brmsBrokerResponsePayload.setMedicalScheduling(finalPayload.getMedicalScheduling());
        brmsBrokerResponsePayload.setdNDOption(finalPayload.getdNDOption());
        brmsBrokerResponsePayload.setSellerDeclarationonMob(finalPayload.getSellerDeclarationonMob());
        brmsBrokerResponsePayload.setServiceStatus(AppConstants.SUCCESS);
        brmsBrokerResponsePayload.setCampaignIDDataType(finalPayload.getCampaignIDDataType());
        brmsBrokerResponsePayload.setCampaignIDLength(finalPayload.getCampaignIDLength());
        brmsBrokerResponsePayload.setCampaignLocationDataType(finalPayload.getCampaignLocationDataType());
        brmsBrokerResponsePayload.setCampaignLocationLength(finalPayload.getCampaignLocationLength());
        brmsBrokerResponsePayload.setJvLineApplicable(finalPayload.getJvLineApplicable());
        brmsBrokerResponsePayload.setProductMinPremAnnual(finalPayload.getProductMinPremAnnual());
        brmsBrokerResponsePayload.setProductMinPremSemi(finalPayload.getProductMinPremSemi());
        brmsBrokerResponsePayload.setProductMinPremQuarterly(finalPayload.getProductMinPremQuarterly());
        brmsBrokerResponsePayload.setProductMinPremMonthly(finalPayload.getProductMinPremMonthly());
        brmsBrokerResponsePayload.setSinglePremiumApplicable(finalPayload.getSinglePremiumApplicable());
    }
}
