package com.mli.mpro.sellerSignature.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.amlulip.training.model.SoaAmlRequest;
import com.mli.mpro.location.amlulip.training.model.SoaResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.SoaCloudResponse;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.location.services.impl.SoaCloudServiceImpl;
import com.mli.mpro.sellerSignature.sellerResponse.MsgInfo;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.sellerSignature.sellerRequest.Header;
import com.mli.mpro.sellerSignature.sellerRequest.Request;
import com.mli.mpro.sellerSignature.sellerRequest.RequestData;
import com.mli.mpro.sellerSignature.sellerRequest.SellerRequest;
import com.mli.mpro.sellerSignature.sellerResponse.SellerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class SellerSignatureServiceImpl implements SellerSignatureService {

    private static final Logger logger = LoggerFactory.getLogger(SellerSignatureServiceImpl.class);

    @Value("${sellerSignature.url}")
    private String sellerSignatureUrl;

    @Value("${sellerSignature.clientId}")
    private String sellerSignatureClientId;

    @Value("${sellerSignature.clientIdValue}")
    private String getSellerSignatureClientIdValue;

    @Value("${sellerSignature.clientSecret}")
    private String sellerSignatureClientSecret;

    @Value("${sellerSignature.clientSecretValue}")
    private String sellerSignatureClientSecretValue;

    private SoaCloudService soaCloudService;
    private ObjectMapper objectMapper;
    @Autowired
    public SellerSignatureServiceImpl(SoaCloudService soaCloudService, ObjectMapper objectMapper) {
        this.soaCloudService = soaCloudService;
        this.objectMapper = objectMapper;
    }

    public SellerSignatureServiceImpl() {
    }

    String sellerSignature = null;

    @Override
    public String getSellerSignature(ProposalDetails proposalDetails) {

        ResponseEntity<SellerResponse> responseEntity = null;
        if(proposalDetails.getSourcingDetails()!=null) {
            if (!Utility.stringEqualCheck(proposalDetails.getChannelDetails().getChannel(), AppConstants.CHANNEL_AXIS)) {
               setSellerSignature(proposalDetails);
            } else if(Utility.isDIYJourney(proposalDetails)){
                String sourceChannel =proposalDetails.getAdditionalFlags().getSourceChannel();
                sellerSignature = Utility.diyStaticID(sourceChannel,1) + " , " + Utility.diyStaticID(sourceChannel,0);
            }
            else {
                try {
                    logger.info("Calling Seller API for transactionId {}", proposalDetails.getTransactionId());
                    SellerRequest sellerRequest = new SellerRequest();
                    Request request = new Request();
                    RequestData requestData = new RequestData();
                    Header header = new Header();
                    header.setSoaAppId(AppConstants.FULFILLMENT);
                    header.setSoaCorrelationId(String.valueOf(proposalDetails.getTransactionId()));
                    requestData.setId("");
                    requestData.setKey1(proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode());
                    requestData.setKey2("X");
                    requestData.setKey3("");
                    requestData.setType("CertificationDetailRequest");
                    requestData.setTransTrackingID("");

                    request.setRequestData(requestData);
                    request.setHeader(header);
                    sellerRequest.setRequest(request);

                    if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_ALM_TRAINING_DL_FLAG)){
                        logger.info("DL API Calling AML training to fetch Signature feature flag is enable api for amlRequest {}",sellerRequest);
                        if(objectMapper==null){
                            objectMapper = new ObjectMapper();
                        }
                        if(soaCloudService==null){
                            soaCloudService = new SoaCloudServiceImpl();
                        }
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        SoaAmlRequest soaAmlRequest = objectMapper.convertValue(sellerRequest, SoaAmlRequest.class);
                        logger.info("Request sent to SOA DL for Signature fetch from AML training API {}",soaAmlRequest);
                        SoaCloudResponse<SoaResponsePayload> soaCloudResponse = soaCloudService.fetchSOAAMLTrainingDLApi(soaAmlRequest);
                        logger.info("Response received from SOA DL {}",soaCloudResponse);
                        SellerResponse sellerResponse = objectMapper.convertValue(soaCloudResponse, SellerResponse.class);
                        logger.info("Response received from SOA DL for Signature fetch from AML training API {}",sellerResponse);
                        if(!Utility.isAnyObjectNull(sellerResponse,sellerResponse.getResponse())){
                            if(Utility.isAnyObjectNull(sellerResponse.getResponse().getMsginfo())){
                                MsgInfo msgInfo = new MsgInfo();
                                String msgCode = soaCloudResponse.getResponse().getMsgInfo().getMsgCode();
                                msgInfo.setMsgCode(msgCode);
                                msgInfo.setMsg(soaCloudResponse.getResponse().getMsgInfo().getMsg());
                                msgInfo.setMsgDescription(soaCloudResponse.getResponse().getMsgInfo().getMsgDescription());
                                sellerResponse.getResponse().setMsginfo(msgInfo);
                            }
                            responseEntity = AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(soaCloudResponse.getResponse().getMsgInfo().getMsgCode()) ?
                                    new ResponseEntity<>(sellerResponse,HttpStatus.OK) : new ResponseEntity<>(sellerResponse,HttpStatus.INTERNAL_SERVER_ERROR);
                            logger.info("responseEntity {}",responseEntity);
                        }
                    } else {
                        logger.info("Calling AML training to fetch Signature feature flag is enable api for amlRequest {}",sellerRequest);
                        MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
                        httpHeaders.add(sellerSignatureClientId, getSellerSignatureClientIdValue);
                        httpHeaders.add(sellerSignatureClientSecret, sellerSignatureClientSecretValue);
                        String json=Utility.printJsonRequest(request);
                        logger.info("Seller signature request {} for transactionId {}",json, proposalDetails.getTransactionId());
                        HttpEntity<?> httpEntity = new HttpEntity(sellerRequest, httpHeaders);
                        responseEntity = new RestTemplate().postForEntity(sellerSignatureUrl, httpEntity, SellerResponse.class);
                    }

                    SellerResponse body = responseEntity.getBody();
                    if (body != null && verifyResponse(responseEntity) ) {
                            String response=Utility.printJsonRequest(body);
                            logger.info("Seller signature reponse {} for transactionId {}", response, proposalDetails.getTransactionId());
                        sellerSignature = Utility.evaluateConditionalOperation(!StringUtils.isEmpty(body.getResponse().getPayload().getRegistrationDetails().get(0).getAgentName()), body.getResponse().getPayload().getRegistrationDetails().get(0).getAgentName(), proposalDetails.getSourcingDetails().getAgentName()) + " , " + Utility.evaluateConditionalOperation(!StringUtils.isEmpty(body.getResponse().getPayload().getRegistrationDetails().get(0).getRegistrationNo()), body.getResponse().getPayload().getRegistrationDetails().get(0).getRegistrationNo(), proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode());
                    } else {
                        logger.info("Could not recieve seller details for transactionId {}", proposalDetails.getTransactionId());
                    }
                } catch (Exception e) {
                    logger.error("Error in calling seller API: ",e);
                }
            }
            if (verifySellerSignature(sellerSignature)) {
                sellerSignature = proposalDetails.getSourcingDetails().getAgentName() + " , " + proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpCode();
                logger.info("Seller signature for transactionId {} is {}",proposalDetails.getTransactionId(),sellerSignature);
            }
        } else {
            logger.info("Seller Signature could not fetched for transactionId {} ", proposalDetails.getTransactionId());
        }
        return sellerSignature;
    }

    private boolean verifyResponse(ResponseEntity<SellerResponse> responseEntity){
        boolean result = false;
        try{
           SellerResponse body =responseEntity.getBody();
           if(body!=null)
            result = responseEntity.getStatusCode().equals(HttpStatus.OK)
                    && body.getResponse().getMsginfo().getMsgCode().equalsIgnoreCase("200")
                    && body.getResponse().getPayload().getRegistrationDetails() != null
                    && !body.getResponse().getPayload().getRegistrationDetails().isEmpty();
        }
        catch (Exception e) {
            logger.error("Error in calling seller API: ", e);
        }
        return  result;
    }

    private void setSellerSignature(ProposalDetails proposalDetails){
        if(!StringUtils.isEmpty(proposalDetails.getSourcingDetails().getAgentId())){
            sellerSignature = proposalDetails.getSourcingDetails().getAgentName() + " , " + proposalDetails.getSourcingDetails().getAgentId();
            logger.info("Seller signature for transactionId {} is {}",proposalDetails.getTransactionId(),sellerSignature);}
    }

    private boolean verifySellerSignature(String sellerSignature){
        return (sellerSignature == null || sellerSignature.equalsIgnoreCase("" + " , " + "") || sellerSignature.equalsIgnoreCase("null" + " , " + "null"));
    }
}
