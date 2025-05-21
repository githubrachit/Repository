package com.mli.mpro.ekyc.serviceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.ekyc.model.*;
import com.mli.mpro.ekyc.service.EkycService;
import com.mli.mpro.location.services.PincodeMasterService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.utils.EncryptionDecryptionOnboardingUtil;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.mli.mpro.location.soa.constants.PanDOBConstants.INTERNAL_SERVER_ERROR;
import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static software.amazon.awssdk.auth.credentials.internal.CredentialSourceType.ENVIRONMENT;

@Service
public class EkycServiceImpl implements EkycService {


    private static final Logger logger = LoggerFactory.getLogger(EkycServiceImpl.class);
    private AuditService auditService;
    private List<String> missingAadhaarDetails = new ArrayList<>();

    private List<String> dummyAadhaarList =  new ArrayList<>(Arrays.asList(
            DUMMY_VIRTUAL_1,DUMMY_VIRTUAL_2,DUMMY_VIRTUAL_3,DUMMY_VIRTUAL_4,DUMMY_VIRTUAL_5,DUMMY_VIRTUAL_6,
            DUMMY_AADHAAR_5, DUMMY_AADHAAR_6, DUMMY_AADHAAR_3,
            DUMMY_AADHAAR_4, DUMMYAADHAAR2, DUMMYAADHAAR1,DUMMY_AADHAAR_7
    ));

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Value("${eKyc.url}")
    private  String eKycUrl;
    @Value("${eKyc.x-api-key}")
    private  String eKycXApiKey;
    @Value("${eKyc.x-apigw-api-id}")
    private  String eKycXApigwApiId;
    @Value("${eKyc.api.read.timeout}")
    private String readTimeOut;
    @Value("${eKyc.api.connection.timeout}")
    private String connTimeOut;
    @Value("${eKyc.api.encryptionKey}")
    private String encryptionKey;
    @Value("${aadhaar.token.encryptionKey}")
    private String aadhaarTokenEncKey;


    @Autowired
    MongoOperations mongoOperation;


    @Autowired
    private PincodeMasterService pincodeMasterService;

    @Autowired
    public EkycServiceImpl(AuditService auditService) {
        this.auditService = auditService;
    }

    public EkycResponsePayload sendingOrValidatingOtp(EkycPayload request) throws UserHandledException {
        logger.info("{} EKYC Request initiated for transactionId {}",request.getPartyType(),request.getPolicyNumber());
        String requestType =request.getRequestType();
        String aadhaarNumber=request.getAadhaarNumber();
        if(aadhaarValidation(requestType,aadhaarNumber)){
            if(requestType.equalsIgnoreCase("A") && StringUtils.hasText(request.getOtp()) && StringUtils.hasText(request.getPolicyNumber())){
                logger.info("{} EKYC Request initiated for fetching aadhaar details for transactionId {}",request.getPartyType(),request.getPolicyNumber());
                return callingEkycApi(request);
            }
            else if (requestType.equalsIgnoreCase("O")){
                logger.info("{} EKYC Request initiated for sending OTP for transactionId {}",request.getPartyType(),request.getPolicyNumber());
                return callingEkycApi(request);
            }
        }
        logger.info("{} EKYC Request failed due to requestType or aadhaar details coming null in request for transactionId {}",request.getPartyType(),request.getPolicyNumber());
        return new EkycResponsePayload(AppConstants.STATUS_FAILURE,AppConstants.EKYC_DETAILS_MISSING,aadhaarNumber,requestType,request.getPartyType(),request.getPolicyNumber(),null);
    }

    private boolean aadhaarValidation(String requestType, String aadhaarNumber) {
        return StringUtils.hasText(requestType) && StringUtils.hasText(aadhaarNumber) && validateFormat(aadhaarNumber);
    }


    private EkycResponsePayload callingEkycApi(EkycPayload ekycPayload) throws UserHandledException {
        InputRequest inputRequest=settingDataForRequest(ekycPayload);
        HttpComponentsClientHttpRequestFactory connectionOutFactory = new HttpComponentsClientHttpRequestFactory();
        connectionOutFactory.setReadTimeout(Integer.valueOf(readTimeOut) * 1000);
        connectionOutFactory.setConnectTimeout(Integer.valueOf(connTimeOut) * 1000);
        RestTemplate restTemplate=new RestTemplate(connectionOutFactory);
        EkycResponsePayload eKycResponsePayload=new EkycResponsePayload();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-api-key",eKycXApiKey);
        headers.add("x-apigw-api-id",eKycXApigwApiId);
        HttpEntity<InputRequest> request= new HttpEntity<>(inputRequest,headers);
        ResponseEntity<OutputResponse> ekycApiResponse=null;
        long reqTime = System.currentTimeMillis();

        try{
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.EKYC_AADHAAR_MOCK) && !ENVIRONMENT.equals("prod")
                    && StringUtils.hasText(ekycPayload.getAadhaarNumber())
                    && (dummyAadhaarList.contains(ekycPayload.getAadhaarNumber())))
                ekycApiResponse = getDummyAadhaarResponse(ekycPayload);
            else
                ekycApiResponse = restTemplate.postForEntity(eKycUrl,request,OutputResponse.class);
            logger.info("{} EKYC API called for transaction Id {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
            if(ekycApiResponse.getStatusCode()==HttpStatus.OK){
                if (ekycApiResponse.getBody() != null) {
                    logger.info("{} EKYC message info received for transaction Id {}  {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber(),ekycApiResponse.getBody().getResponse().getMsgInfo());
                    settingResponse(ekycApiResponse.getBody(),ekycPayload,eKycResponsePayload);
                    logger.info("{} EKYC service took overall {} ms to process the ",ekycPayload.getPartyType() ,
                            (System.currentTimeMillis() - reqTime)/1000);
                    updateAuditDetails(inputRequest,ekycApiResponse.getBody(),ekycPayload);
                    return eKycResponsePayload;
                }
            }
        }catch (HttpServerErrorException e){
            logger.error("Exception occurred while calling the {} EKYC api with statusCode {} {} ",ekycPayload.getPartyType(), e.getStatusCode(), e.getResponseBodyAsString() );
            throw  new UserHandledException();
        }
        return new EkycResponsePayload(AppConstants.STATUS_FAILURE,AppConstants.EKYC_DETAILS_MISSING,ekycPayload.getAadhaarNumber(),ekycPayload.getRequestType(),ekycPayload.getPartyType(),ekycPayload.getPolicyNumber(),null);
    }

    private ResponseEntity<OutputResponse> getDummyAadhaarResponse(EkycPayload ekycPayload) {
        MsgInfo msgInfo = new MsgInfo();
        try {
            String dummyAadhaar = DUMMYAADHAAR1.equals(ekycPayload.getAadhaarNumber()) ? "AADHAAR1" : "AADHAAR2";
            if (dummyAadhaarList.contains(ekycPayload.getAadhaarNumber())) {
                dummyAadhaar = ekycPayload.getAadhaarNumber();
            }
            boolean authRequest = "A".equalsIgnoreCase(ekycPayload.getRequestType());
            msgInfo.setMsgCode(AppConstants.SUCCESS_RESPONSE);
            msgInfo.setMsg(authRequest ? "Response Successfully Generated" : AppConstants.STATUS_SUCCESS);
            msgInfo.setMsgDescription(AppConstants.STATUS_SUCCESS);
            Configuration configurations = configurationRepository.findByKey(dummyAadhaar);
            if (ObjectUtils.isEmpty(configurations)) {
                throw new Exception("Configuration not found for Dummy Aadhaar");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            AadhaarDetail aadhaarDetail = objectMapper.readValue(configurations.getValue(), AadhaarDetail.class);
            ResponsePayload responsePayload = new ResponsePayload();
            responsePayload.setRequestType(ekycPayload.getRequestType());
            responsePayload.setAadhaarDetail(authRequest ? aadhaarDetail : null);
            Response response = new Response();
            response.setMsgInfo(msgInfo);
            response.setResponsePayload(responsePayload);
            OutputResponse outputResponse = new OutputResponse();
            outputResponse.setResponse(response);
            return new ResponseEntity<>(outputResponse, HttpStatus.OK);
        } catch (Exception e) {
            msgInfo.setMsgCode(AppConstants.INTERNAL_SERVER_ERROR_CODE);
            msgInfo.setMsg(INTERNAL_SERVER_ERROR);
            msgInfo.setMsgDescription(e.getMessage());
            Response response = new Response();
            response.setMsgInfo(msgInfo);
            OutputResponse outputResponse = new OutputResponse();
            outputResponse.setResponse(response);
            return new ResponseEntity<>(outputResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public EkycResponsePayload finalErrorResponse(Exception e,EkycPayload ekycPayload){
        logger.error("{} Retry executed for transactionId {} , {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber(), Utility.getExceptionAsString(e));
        saveEkycSkipData(ekycPayload, INTERNAL_SERVER_ERROR);
        EkycResponsePayload ekycRespo = new EkycResponsePayload(AppConstants.STATUS_FAILURE, AppConstants.INTERNAL_SERVER_ERROR, ekycPayload.getAadhaarNumber(), ekycPayload.getRequestType(), ekycPayload.getPartyType(), ekycPayload.getPolicyNumber(), null);
        updateEkysSkipFlag(ekycPayload,ekycRespo);
        return ekycRespo;

    }



    private boolean validateFormat(String aadhaarNumber) {
    if(aadhaarNumber.length()==12){
        return Pattern.matches(AppConstants.AADHAR_FORMAT_REGEX,aadhaarNumber);
    }else if (aadhaarNumber.length()==16){
        return Pattern.matches(AppConstants.VID_FORMAT_REGEX,aadhaarNumber);
    }
    return false;
}


private void settingResponse(OutputResponse ekycApiResponse, EkycPayload ekycPayload, EkycResponsePayload eKycResponsePayload) {
        try{
            MsgInfo msgInfo = ekycApiResponse.getResponse().getMsgInfo();
            if( msgInfo.getMsgCode().equalsIgnoreCase("200") &&  ekycApiResponse.getResponse().getResponsePayload().getRequestType().equalsIgnoreCase("A")){
                AadhaarDetail aadhaarDetail=ekycApiResponse.getResponse().getResponsePayload().getAadhaarDetail();
                logger.info("{} EKYC Authentication response received successfully for transactionId {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
                ResponseAadhaarDetails responseAadhaarDetails=null;
                eKycResponsePayload.setEkycStatus(AppConstants.STATUS_FAILURE);
                eKycResponsePayload.setMsg("EKYC Validation Failed");
                if(Objects.nonNull(aadhaarDetail) && validateAadhaarDetail(aadhaarDetail)){

                    savingAadhaarData(aadhaarDetail,ekycPayload);
                    eKycResponsePayload.setEkycStatus(msgInfo.getMsgDescription());
                    eKycResponsePayload.setMsg(msgInfo.getMsg());
                }else {
                    findMissingAadhaarDetail(aadhaarDetail);
                    setMissingOrInvalidAadharDetailsMsg(eKycResponsePayload);
                    saveEkycSkipData(ekycPayload, AppConstants.EKYC_DETAILS_MISSING);
                    updateEkysSkipFlag(ekycPayload,eKycResponsePayload);
                }
                updateEkycStatus(ekycPayload, eKycResponsePayload.getEkycStatus(), eKycResponsePayload.getMsg(), ekycPayload.getOtp(),false,eKycResponsePayload.getValidationErrorMsg());
                updateEkycResponse(eKycResponsePayload,ekycPayload);
                responseAadhaarDetails=transformAadhaarDetails(aadhaarDetail);
                eKycResponsePayload.setAadhaarDetail(responseAadhaarDetails);

            }else if (msgInfo.getMsgCode().equalsIgnoreCase("200") && ekycApiResponse.getResponse().getResponsePayload().getRequestType().equalsIgnoreCase("O") && msgInfo.getMsgDescription().equalsIgnoreCase("Success")) {
                logger.info("{} EKYC OTP sent successfully for transactionId {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
                updateEkycResponse(eKycResponsePayload,ekycPayload);
                eKycResponsePayload.setEkycStatus(msgInfo.getMsgDescription());
                eKycResponsePayload.setMsg(msgInfo.getMsg());
                eKycResponsePayload.setAadhaarDetail(null);
                updateEkycStatus(ekycPayload, eKycResponsePayload.getEkycStatus(), eKycResponsePayload.getMsg(), ekycPayload.getOtp(),true,null);
            }else  {
                logger.info("{} EKYC API failed for transactionId {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
                if(!msgInfo.getMsgCode().equalsIgnoreCase("500") || !AppConstants.AADHAAR_ERROR_MESSAGE.contains(msgInfo.getMsg().trim())) {
                    saveEkycSkipData(ekycPayload, msgInfo.getMsgDescription());
                    updateEkysSkipFlag(ekycPayload,eKycResponsePayload);
                }
                updateEkycStatus(ekycPayload, msgInfo.getMsgDescription(), msgInfo.getMsg(), ekycPayload.getOtp(),false,null);
                updateEkycResponse(eKycResponsePayload,ekycPayload);
                eKycResponsePayload.setEkycStatus(msgInfo.getMsgDescription());
                eKycResponsePayload.setMsg(msgInfo.getMsg());
                eKycResponsePayload.setAadhaarDetail(null);
            }
        }catch (Exception e){
            logger.error("Exception occurred while setting the {} EKYC response {} for transactionId {}",ekycPayload.getPartyType(), Utility.getExceptionAsString(e),ekycPayload.getPolicyNumber());
        }
    }

    public void setMissingOrInvalidAadharDetailsMsg(EkycResponsePayload eKycResponsePayload) {
        if (missingAadhaarDetails != null && !missingAadhaarDetails.isEmpty()) {
            String aadharMissingDetails = String.join(", ", missingAadhaarDetails);
            eKycResponsePayload.setValidationErrorMsg("EKYC Validation Failed " + aadharMissingDetails + " missing or not valid.");
            logger.info("Aadhar validation check msg for policyNumber {}, {}", eKycResponsePayload.getPolicyNumber(), eKycResponsePayload.getValidationErrorMsg());
        }
    }

    private void updateEkysSkipFlag(EkycPayload ekycPayload, EkycResponsePayload eKycResponsePayload) {
        if(AppConstants.PROPOSER.equalsIgnoreCase(ekycPayload.getPartyType())){
            eKycResponsePayload.setProposerEkycFailure(AppConstants.CAMEL_YES);
            eKycResponsePayload.setProposerEkycTypeOfFailure(AppConstants.TECHNICAL_ERROR);
        }else  if(AppConstants.INSURER.equalsIgnoreCase(ekycPayload.getPartyType())){
            eKycResponsePayload.setInsurerEkycFailure(AppConstants.CAMEL_YES);
            eKycResponsePayload.setInsurerEkycTypeOfFailure(AppConstants.TECHNICAL_ERROR);
        }else  if(AppConstants.PAYOR.equalsIgnoreCase(ekycPayload.getPartyType())){
            eKycResponsePayload.setPayorEkycFailure(AppConstants.CAMEL_YES);
            eKycResponsePayload.setPayorEkycTypeOfFailure(AppConstants.TECHNICAL_ERROR);
        }
    }

    private void findMissingAadhaarDetail(AadhaarDetail aadhaarDetail) {
        missingAadhaarDetails.clear(); // Clear previous values
        if (!StringUtils.hasText(aadhaarDetail.getName())) {
            missingAadhaarDetails.add("name");
        }
        if (!StringUtils.hasText(aadhaarDetail.getHouse())) {
            missingAadhaarDetails.add("house");
        }
        if (!StringUtils.hasText(aadhaarDetail.getGender())) {
            missingAadhaarDetails.add("gender");
        }
        if (!StringUtils.hasText(aadhaarDetail.getPinCode())) {
            missingAadhaarDetails.add("pinCode");
        }
        if (!StringUtils.hasText(aadhaarDetail.getDist())) {
            missingAadhaarDetails.add("District");
        }
        if (!StringUtils.hasText(aadhaarDetail.getState())) {
            missingAadhaarDetails.add("State");
        }
    }

    private void saveEkycSkipData(EkycPayload ekycPayload, String reason) {
        try {
            String partyType = ekycPayload.getPartyType();
            String policyNumber = ekycPayload.getPolicyNumber();
            logger.info("Updating {} eKYC skip details for transactionId {}", partyType, policyNumber);
            logger.info("Updating missing AadhaarDetails for {}", missingAadhaarDetails);
            Query query = new Query(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(policyNumber)));
            Update update = new Update()
                    .set("additionalFlags." + partyType.toLowerCase() + "EkycFailure", "Yes")
                    .set("additionalFlags." + partyType.toLowerCase() + "EkycSkipReason", reason)
                    .set("additionalFlags." + partyType.toLowerCase() + "EkycTypeOfError", "Technical")
                    .set("additionalFlags.missingAadhaarDetails", missingAadhaarDetails);

            mongoOperation.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), ProposalDetails.class);
            logger.info("{} eKYC skip details updated for transactionId {}", partyType, policyNumber);
        } catch (Exception e) {
            logger.error("Exception occurred while saving {} eKYC skip details for transactionId {}", ekycPayload.getPartyType(), ekycPayload.getPolicyNumber());
        }
    }

    private void updateEkycStatus(EkycPayload ekycPayload,String ekycStatus,String ekycStatusMsg, String otp,boolean ekycOtpTriggered , String validationErrorMsg) {
        try {
            if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_EKYC_STATUS_MAIL)) {
                logger.info("Updating the {} ekycStatus in db for transactionId {}", ekycPayload.getPartyType(), ekycPayload.getPolicyNumber());
                Update update = new Update();
                FindAndModifyOptions options = new FindAndModifyOptions();
                options.returnNew(true);
                org.springframework.data.mongodb.core.query.Query query = new Query();
                query.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(ekycPayload.getPolicyNumber())));
                if (ekycOtpTriggered) {
                    logger.info("Inside ekycOtpTriggered transactionId {}",ekycPayload.getPolicyNumber());
                    update.set(EKYC_AADHAAR_DETAILS_PATH + ekycPayload.getPartyType().toLowerCase() + "EkycOtpTriggered", "Y");
                } else {
                    update.set(EKYC_AADHAAR_DETAILS_PATH + ekycPayload.getPartyType().toLowerCase() + "EkycStatus", ekycStatus);
                    update.set(EKYC_AADHAAR_DETAILS_PATH + ekycPayload.getPartyType().toLowerCase() + "FailureMsg", ekycStatusMsg);
                    update.set("additionalFlags.ekycAadhaarDetails.verificationCode", otp);

                    if(StringUtils.hasText(validationErrorMsg)) {
                        update.set("additionalFlags."+ekycPayload.getPartyType().toLowerCase()+"EkycValidationFailMsg", validationErrorMsg);
                    }
                }
                mongoOperation.findAndModify(query, update, options, ProposalDetails.class);
            }
        } catch (Exception e) {
            logger.error("Exception occurred while updating the {} EKYC status {} for transactionId {}", ekycPayload.getPartyType(), Utility.getExceptionAsString(e), ekycPayload.getPolicyNumber());
        }
    }

    private void savingAadhaarData(AadhaarDetail aadhaarDetail, EkycPayload ekycPayload) {
        try{
            logger.info("Updating the {} aadhaarDetails in db for transactionId {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
            String encryptedUIDToken = EncryptionDecryptionUtil.encrypt(aadhaarDetail.getTokenNo(), aadhaarTokenEncKey);
            Update update = new Update();
            FindAndModifyOptions options = new FindAndModifyOptions();
            options.returnNew(true);
            org.springframework.data.mongodb.core.query.Query query = new Query();
            query.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(ekycPayload.getPolicyNumber())));
            if(AppConstants.PROPOSER.equalsIgnoreCase(ekycPayload.getPartyType())){
                update.set("additionalFlags.ekycAadhaarDetails.proposerAadhaarPdf", aadhaarDetail.getPdfByteArray());
                update.set("additionalFlags.ekycAadhaarDetails.proposerAadhaarImage", aadhaarDetail.getImage());
                update.set("additionalFlags.ekycAadhaarDetails.proposerUIDToken", encryptedUIDToken);
            }else if (AppConstants.INSURER.equalsIgnoreCase(ekycPayload.getPartyType())) {
                update.set("additionalFlags.ekycAadhaarDetails.insuredAadhaarPdf", aadhaarDetail.getPdfByteArray());
                update.set("additionalFlags.ekycAadhaarDetails.insuredAadhaarImage", aadhaarDetail.getImage());
                update.set("additionalFlags.ekycAadhaarDetails.insuredUIDToken", encryptedUIDToken);
            }else if (AppConstants.PAYOR.equalsIgnoreCase(ekycPayload.getPartyType())) {
                update.set("additionalFlags.ekycAadhaarDetails.payorAadhaarPdf", aadhaarDetail.getPdfByteArray());
                update.set("additionalFlags.ekycAadhaarDetails.payorAadhaarImage", aadhaarDetail.getImage());
                update.set("additionalFlags.ekycAadhaarDetails.payorUIDToken", encryptedUIDToken);
            }
            mongoOperation.findAndModify(query, update, options, ProposalDetails.class);
            logger.info("{} AadhaarDetails updated successfully in db for transactionId {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
        }catch (Exception e){
            logger.error("Exception occurred while saving the {} aadhaarDetails in db for transactionId {}",ekycPayload.getPartyType(),ekycPayload.getPolicyNumber());
        }
    }

    private ResponseAadhaarDetails transformAadhaarDetails(AadhaarDetail aadhaarDetail) {
        if(Objects.nonNull(aadhaarDetail)){
            ResponseAadhaarDetails responseAadhaarDetails=new ResponseAadhaarDetails();
            responseAadhaarDetails.setName(aadhaarDetail.getName());
            responseAadhaarDetails.setGender(aadhaarDetail.getGender());
            responseAadhaarDetails.setPhone(aadhaarDetail.getPhone());
            responseAadhaarDetails.setEmail(aadhaarDetail.getEmail());
            responseAadhaarDetails.setImage(aadhaarDetail.getImage());
            responseAadhaarDetails.setDob(dateFormatting(aadhaarDetail.getDob()));
            responseAadhaarDetails.setCareOf(aadhaarDetail.getCareOf());
            responseAadhaarDetails.setHouse(aadhaarDetail.getHouse());
            responseAadhaarDetails.setStreet(aadhaarDetail.getStreet());
            responseAadhaarDetails.setLandmark(aadhaarDetail.getLandmark());
            responseAadhaarDetails.setLocation(aadhaarDetail.getLocation());
            responseAadhaarDetails.setPinCode(aadhaarDetail.getPinCode());
            responseAadhaarDetails.setPostOffice(aadhaarDetail.getPostOffice());
            responseAadhaarDetails.setVillOrCity(aadhaarDetail.getVillOrCity());
            responseAadhaarDetails.setSubDist(aadhaarDetail.getSubDist());
            responseAadhaarDetails.setDist(aadhaarDetail.getDist());
            responseAadhaarDetails.setState(aadhaarDetail.getState());
           return responseAadhaarDetails;
        }
        return null;
    }

    private String dateFormatting(String dob) {
        try{
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat existingFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(existingFormat.parse(dob));
        }catch (Exception e){
            logger.error("Error occurred during the formatting the Date {}",Utility.getExceptionAsString(e));
        }
        return dob;
    }


    private void updateAuditDetails(InputRequest inputRequest, OutputResponse ekycApiResponse, EkycPayload ekycPayload) {
        try{
            inputRequest.getRequest().getRequestPayload().setAadhaarNumber(inputRequest.getRequest().getRequestPayload().getAadhaarNumber().replaceAll(AppConstants.EKYC_AADHAAR_REGEX,"X"));
            ekycApiResponse.getResponse().setResponsePayload(null);
            AuditingDetails auditDetails = new AuditingDetails();
            auditDetails.setAdditionalProperty("request", inputRequest);
            ResponseObject responseObject = new ResponseObject();
            responseObject.setAdditionalProperty("response", ekycApiResponse);
            auditDetails.setResponseObject(responseObject);
            auditDetails.setServiceName(AppConstants.EKYC_SERVICE_NAME);
            auditDetails.setTransactionId(Long.parseLong(ekycPayload.getPolicyNumber()));
            auditService.saveAuditTransactionDetails(auditDetails);
        }catch (Exception e){
            logger.error("Exception occurred while Encrypting the {} EKYC request and response {}",ekycPayload.getPartyType(), Utility.getExceptionAsString(e));
        }
    }



    private void updateEkycResponse(EkycResponsePayload eKycResponsePayload, EkycPayload ekycPayload) {
        try{
            eKycResponsePayload.setAadhaarNumber(ekycPayload.getAadhaarNumber());
            eKycResponsePayload.setRequestType(ekycPayload.getRequestType());
            eKycResponsePayload.setPolicyNumber(ekycPayload.getPolicyNumber());
            eKycResponsePayload.setPartyType(ekycPayload.getPartyType());
        }catch (Exception e){
            logger.error("Exception occurred while setting the {} EKYC response {}",ekycPayload.getPartyType(), Utility.getExceptionAsString(e));
        }
    }

    private boolean validateAadhaarDetail(AadhaarDetail aadhaarDetail) {
        return StringUtils.hasText(aadhaarDetail.getName()) &&
                StringUtils.hasText(aadhaarDetail.getHouse()) &&
                StringUtils.hasText(aadhaarDetail.getGender()) &&
                (StringUtils.hasText(aadhaarDetail.getPinCode()) && validateCityState(aadhaarDetail));

    }

    private boolean validateCityState(AadhaarDetail aadhaarDetail) {
        HashMap<String, String> stateCityByPinCode = null;
        String pinCode=aadhaarDetail.getPinCode();

        stateCityByPinCode =pincodeMasterService.getStatesAndCitiesByPincode(pinCode);

        if(Objects.nonNull(stateCityByPinCode)){
            String state = stateCityByPinCode.get(AppConstants.PINCODE_STATE);
            String city = stateCityByPinCode.get(AppConstants.PINCODE_CITY);
            if(StringUtils.hasText(state) && StringUtils.hasText(city)){
                aadhaarDetail.setState(state);
                aadhaarDetail.setDist(city);
                return true;
            }
        }
        return false;
    }

    private InputRequest settingDataForRequest(EkycPayload ekycPayload) {
        RequestPayload requestPayload=new RequestPayload(ekycPayload.getAadhaarNumber(),ekycPayload.getRequestType(),ekycPayload.getPolicyNumber().substring(1,10), ekycPayload.getOtp(),AppConstants.AADHAAR_OTP_AUTH);
        InputRequest inputRequest=new InputRequest();
        Header header=new Header(AppConstants.FULFILLMENT, UUID.randomUUID().toString());
        Request request=new Request();
        request.setHeader(header);
        request.setRequestPayload(requestPayload);
        inputRequest.setRequest(request);
        return inputRequest;
    }
}
