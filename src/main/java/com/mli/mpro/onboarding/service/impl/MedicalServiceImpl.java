package com.mli.mpro.onboarding.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.labslist.models.*;
import com.mli.mpro.location.soa.service.impl.LabsListServiceImpl;
import com.mli.mpro.onboarding.medicalSchedule.model.Category;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.service.handler.APIServiceHandler;
import com.mli.mpro.onboarding.partner.service.handler.DedupeServiceHandler;
import com.mli.mpro.onboarding.documents.model.InputRequest;
import com.mli.mpro.onboarding.medicalSchedule.model.*;
import com.mli.mpro.onboarding.medicalSchedule.model.Request;
import com.mli.mpro.onboarding.medicalSchedule.model.RequestData;
import com.mli.mpro.onboarding.service.MedicalService;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.ErrorResponse;
import com.mli.mpro.proposal.models.LabDetails;
import com.mli.mpro.proposal.models.MedicalShedulingDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProductIllustrationResponse;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.OutputResponse;
import com.mli.mpro.proposal.models.Response;
import com.mli.mpro.onboarding.medicalSchedule.model.ResponseData ;
import com.mli.mpro.proposal.models.ResponsePayload ;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

import static com.mli.mpro.productRestriction.util.AppConstants.*;


@Service
public class MedicalServiceImpl implements MedicalService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LabsListServiceImpl labsListService;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    ProposalRepository repository;

    @Autowired
    private AuditService auditService;

    @Autowired
    DedupeServiceHandler apiServiceHandler;

    @Override
    public RequestResponse getMedicalCenterList(RequestResponse inputPayload, MultiValueMap<String, String> headerMap) {
        com.mli.mpro.onboarding.medicalCenter.model.InputRequest inputRequest;
        LabsListRequest labsListRequest;
        ProposalDetails proposalDetails;
        RequestResponse requestResponse = new RequestResponse();
        try {
            String decryptedPayload = apiServiceHandler.decrypt(inputPayload,headerMap);
            inputRequest = deserializeMedicalListRequest(decryptedPayload);
            logger.info("medical-center-list API is called with request{}",inputRequest);

            if (!validateMedicalListRequest(inputRequest)) {
                return encrytedResponse(buildMedicalListErrorResponse(AppConstants.STATUS_FAILURE, AppConstants.BAD_REQUEST_MESSAGE, AppConstants.BAD_REQUEST_CODE),headerMap);
            }
            labsListRequest =getLabListRequest(inputRequest);

        } catch (JsonProcessingException | GeneralSecurityException e) {
            logger.error("Error occured while decrypting labListRequest");
            return encrytedResponse(buildMedicalListErrorResponse(AppConstants.STATUS_FAILURE, AppConstants.BAD_REQUEST_MESSAGE, AppConstants.BAD_REQUEST_CODE),headerMap);
        }


        ResponseEntity<Object> response = labsListService.executeLabsListService(labsListRequest);
        logger.info("SOA response lablist:{}",response);

        LabslistSoaResponse labslistSoaResponse=null;
        if (response.getBody() instanceof LabslistSoaResponse) {
            labslistSoaResponse = (LabslistSoaResponse) response.getBody();
            logger.info("labslistSoaResponse:{}",labslistSoaResponse);
        }

        if(!ObjectUtils.isEmpty(labslistSoaResponse))
        {
            if(ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getPolicyNumber()))
            { proposalDetails = repository.findByApplicationDetailsQuoteId(inputRequest.getRequest().getPayload().getQuoteId());}
            else
            {proposalDetails = repository.findByApplicationDetailsPolicyNumber(inputRequest.getRequest().getPayload().getPolicyNumber());}

            logger.info("proposal details fetched for medical-center-list: {}",proposalDetails);
            if (Objects.isNull(proposalDetails)) {
                return encrytedResponse(buildMedicalListErrorResponse(AppConstants.STATUS_FAILURE, ERROR_PROPOSALDETAILS, AppConstants.BAD_REQUEST_CODE),headerMap);
            }
            else {
                if (!ObjectUtils.isEmpty(labslistSoaResponse.getResult().getResponse().getPayload())) {
                    saveMedicalListResponse(proposalDetails, labslistSoaResponse.getResult().getResponse().getPayload());
                }
            }
        }



        requestResponse.setPayload(objectToString(response.getBody()));
        return encrytedResponse(requestResponse,headerMap);
    }

    private void saveMedicalListResponse(ProposalDetails proposalDetails, List<com.mli.mpro.location.labslist.models.ResponsePayload> payload) {
        Map<String, com.mli.mpro.location.labslist.models.ResponsePayload> payloadMap = new HashMap<>();
        for (com.mli.mpro.location.labslist.models.ResponsePayload responsePayload : payload) {
            payloadMap.put(responsePayload.getLabID(), responsePayload);
        }
        proposalDetails.setMedicalListResponse(payloadMap);
        repository.save(proposalDetails);

    }

    private LabsListRequest getLabListRequest(com.mli.mpro.onboarding.medicalCenter.model.InputRequest inputRequest) {
        LabsListRequest labsListRequest=new LabsListRequest();
        com.mli.mpro.location.labslist.models.Request request=new com.mli.mpro.location.labslist.models.Request();
        Data data=new Data();
        data.setCity(BLANK);
        data.setState(BLANK);
        data.setAgentId(BLANK);
        data.setVendor(inputRequest.getRequest().getPayload().getVendor());
        data.setPincode(inputRequest.getRequest().getPayload().getPincode());
        request.setData(data);
        labsListRequest.setRequest(request);
        return labsListRequest;

    }

    private boolean validateMedicalListRequest( com.mli.mpro.onboarding.medicalCenter.model.InputRequest inputRequest) {
       if(Objects.isNull(inputRequest)||Objects.isNull(inputRequest.getRequest())
               ||Objects.isNull(inputRequest.getRequest().getPayload())
               ||ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getPincode())
               ||ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getVendor())
               ||(ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getPolicyNumber())
               && ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getQuoteId())) ){
        return false;}
       else
       {return true;}
    }
    private com.mli.mpro.onboarding.medicalCenter.model.InputRequest deserializeMedicalListRequest(String decryptedString) throws JsonProcessingException {
        return objectMapper.readValue(decryptedString, com.mli.mpro.onboarding.medicalCenter.model.InputRequest.class);
    }
    public String objectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error occured while converting object to string");
            return null;
        }
    }


    public  RequestResponse buildMedicalListErrorResponse(String msg, String msgDescription, String msgCode){
        SoaResponse response = new SoaResponse();
        RequestResponse requestResponse=new RequestResponse();
        LabslistSoaResult labslistSoaResult = new LabslistSoaResult();
        LabslistSoaResponse labslistSoaResponse=new LabslistSoaResponse();
        com.mli.mpro.location.common.soa.model.MsgInfo msgInfo = new com.mli.mpro.location.common.soa.model.MsgInfo(msg,msgCode,msgDescription);
        response.setHeader(new com.mli.mpro.location.common.soa.model.Header());
        response.setMsgInfo(msgInfo);
        response.setPayload(new ArrayList<>());
        labslistSoaResult.setResponse(response);
        labslistSoaResponse.setResult(labslistSoaResult);
        requestResponse.setPayload(objectToString(labslistSoaResponse));
        return requestResponse;
    }

    public RequestResponse encrytedResponse(RequestResponse response, MultiValueMap<String, String> headers) {
        RequestResponse requestResponse = new RequestResponse();
        try {
            requestResponse.setPayload(apiServiceHandler.encryptResponse(response, headers));
            return requestResponse;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error("Exception occured while encrypting response");
            return null;
        }
    }
    @Override
    public RequestResponse getMedicalScheduling(RequestResponse inputPayload, MultiValueMap<String, String> headerMap) {
        InputRequest inputRequest ;
        RequestResponse requestResponse=new RequestResponse();;
        OutputResponse outputResponse = new OutputResponse();
        Response response = new Response();
        com.mli.mpro.proposal.models.ResponseData responseData =new com.mli.mpro.proposal.models.ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();
        ProposalDetails proposalDetails = null;
        List<Object> errors = new ArrayList<>();
        String responseStatus = "";
        List<Object> responseMessage = new ArrayList<>();
        MedicalShedulingDetails medicalShedulingDetails=new MedicalShedulingDetails();
        LabDetails labDetails=new LabDetails();
        try {
            logger.info("Trying to schedule Medical Appointment {}");
            String decryptedPayload = apiServiceHandler.decrypt(inputPayload,headerMap);
            inputRequest = deserializeMedicalSchedulingRequest(decryptedPayload);
            logger.info("input request for Medical API decrypted successfully{}",inputRequest);
            String policyNumber = inputRequest.getRequest().getPayload().getPolicyNumber();
            String quoteId = inputRequest.getRequest().getPayload().getQuoteId();
            if (ObjectUtils.isEmpty(policyNumber) &&ObjectUtils.isEmpty(quoteId)) {
                return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE, ERROR_POLICYNOTFOUND, AppConstants.BAD_REQUEST_CODE),headerMap);
            }
            if(ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getLabId())
                    ||ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getVisitType())
                    ||ObjectUtils.isEmpty(inputRequest.getRequest().getPayload().getPreferredDate())){
                return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE,BAD_REQUEST_MESSAGE ,AppConstants.BAD_REQUEST_CODE),headerMap);
            }

            if(ObjectUtils.isEmpty(policyNumber))
            { proposalDetails = repository.findByApplicationDetailsQuoteId(quoteId);}
            else
            {proposalDetails = repository.findByApplicationDetailsPolicyNumber(policyNumber);}

            logger.info("proposal details fetched: {}",proposalDetails);
            if (Objects.isNull(proposalDetails)) {
                return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE, ERROR_PROPOSALDETAILS, AppConstants.BAD_REQUEST_CODE),headerMap);
            }
            String agentId = proposalDetails.getSourcingDetails().getAgentId();
            long transactionId = proposalDetails.getTransactionId();

            if (!validateSheduleMedicalAppointmentApplicable(agentId, transactionId)) {
                return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE, ERROR_AGENTID, AppConstants.BAD_REQUEST_CODE),headerMap);
            }

            if(ObjectUtils.isEmpty(proposalDetails.getMedicalListResponse())|| !proposalDetails.getMedicalListResponse().containsKey(inputRequest.getRequest().getPayload().getLabId())){
                return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE, ERROR_MEDICAL_LIST, AppConstants.BAD_REQUEST_CODE),headerMap);
            }

            Date preferredDate=inputRequest.getRequest().getPayload().getPreferredDate();
            String visitType=inputRequest.getRequest().getPayload().getVisitType();
            String labId=inputRequest.getRequest().getPayload().getLabId();
            String labName=proposalDetails.getMedicalListResponse().get(labId).getLabName();
            String labAddress=proposalDetails.getMedicalListResponse().get(labId).getLabAddress();

            logger.info("Medical Schedule Request received  with preferredDate {} visitType {} labId {} labName {} labAddress {}",preferredDate,visitType,labId,labName,labAddress);

            if(!validateMedicalRequest(preferredDate,visitType,labId,labName,labAddress)){
                return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE, ERROR_MEDICAL_REQUEST_NOTVALID, AppConstants.BAD_REQUEST_CODE),headerMap);
            }
            medicalShedulingDetails.setPreferredDate(preferredDate);
            medicalShedulingDetails.setVisitType(visitType);
            if(!AppConstants.HOME_VISIT.equalsIgnoreCase(visitType)){
                labDetails.setLabId(labId);
                labDetails.setName(labName);
                labDetails.setAddress(labAddress);
            }
            medicalShedulingDetails.setLabDetails(labDetails);
            proposalDetails.getUnderwritingServiceDetails().setMedicalShedulingDetails(medicalShedulingDetails);
            repository.save(proposalDetails);
            logger.info("For the transaction Id{}  Request {} received to schedule medical appointment", transactionId,
                    inputRequest);
            responseStatus = bookLabAppointment(agentId, transactionId, proposalDetails);
            if(AppConstants.MEDICAL_SUCCESS_MGS.equals(responseStatus) || AppConstants.RESPONSE_GENERATED_SUCCESSFULLY.equals(responseStatus))
            {
                responseStatus=AppConstants.APPOINTMENT_BOOKED_SUCCESSFULLY;
            }
        }catch (JsonProcessingException | GeneralSecurityException e) {
            logger.error("Error occured while decrypting Medical Schedule Request");
            return encrytedResponse(buildMedicalScheduleErrorResponse(AppConstants.STATUS_FAILURE, AppConstants.BAD_REQUEST_MESSAGE, AppConstants.BAD_REQUEST_CODE),headerMap);
        }
        catch (Exception ex) {
            responseStatus = AppConstants.FALIURE;
            ex.printStackTrace();
        }
        responseMessage.add(responseStatus);
        responsePayload.setMessage(responseMessage);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        outputResponse.setResponse(response);
        requestResponse.setPayload(objectToString(outputResponse));
        return encrytedResponse(requestResponse,headerMap);
    }

    public boolean validateSheduleMedicalAppointmentApplicable(String agentId, long transactionId) {
        if (StringUtils.hasText(agentId) && transactionId != 0L) {
            return true;
        }
        return false;
    }

    public String bookLabAppointment(String agentId, long transactionId, ProposalDetails proposalDetails) throws UserHandledException {
        String msgDescription = AppConstants.BLANK;
        if (proposalDetails == null) {
            logger.info("Data could not be retrieved for given agentId {} and transactionId {}", agentId, transactionId);
            return AppConstants.FALIURE;
        }
        RequestData requestData = setBookLabAppointmentData(proposalDetails);
        ResponseData response ;
        HttpHeaders headers = new HttpHeaders();
        headers.add(CLIENT_ID, "2ca7268e-a991-4656-823c-cf6b4900962c");
        headers.add(CLIENT_SECRET,"W5mI7eU3qT1mY4fT5hY7wW6xC0vE3tJ1mI8iH4lL3jU5lK5kD3");
        HttpEntity<RequestData> request = new HttpEntity<>(requestData, headers);
        auditService.logAuditDetails(transactionId, (Object) request, null, AppConstants.MEDICAL_SCHEDULE_SERVICE_NAME, agentId);
        logger.info("For the transactionId {} the request being sent to the medical scheduling soa service {}", transactionId, request);
        try {
            long requestedTime = System.currentTimeMillis();
            response = new RestTemplate().postForEntity("https://gatewayuat.maxlifeinsurance.com/apimgm/sb/soa/nb/medical/medicalscheduling/v2", request, ResponseData.class).getBody();
            long processedTime = (System.currentTimeMillis() - requestedTime) / 1000;
            logger.info("Book Appointment api took {} seconds", processedTime);
            logger.info("For the transactionId {} the response received from Book Appointment api {} ", transactionId, response);
            auditService.logAuditDetails(transactionId, (Object) request, response, AppConstants.MEDICAL_SCHEDULE_SERVICE_NAME, agentId);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("For the transactionId {} error occured while calling api for the input request {}", transactionId, request);
            return AppConstants.FALIURE;
        }
        if (!StringUtils.isEmpty(response)) {
            String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            String messageCode = response.getResponse().getMsgInfo().getMsgCode();
            msgDescription = response.getResponse().getMsgInfo().getMsgDescription();
            String msgDescriptionAlreadyExist = AppConstants.MEDICAL_ALREADY_EXIST.concat(policyNumber);

            if (AppConstants.MEDICAL_STATUS_FAILURE_CODE.equalsIgnoreCase(messageCode)
                    && msgDescriptionAlreadyExist.equalsIgnoreCase(msgDescription.trim())) {
                logger.info("For the transaction id{} already exist proposal message changed to{} since policy number exist in MDI db", transactionId, AppConstants.MEDICAL_SUCCESS_MGS);
                messageCode = AppConstants.MEDICAL_SUCCESS_CODE;
                msgDescription = AppConstants.MEDICAL_SUCCESS_MGS;
            }
            if (messageCode.equals("500")) {
                logger.info("Calling to Book Appointment Api Failed for the input request {} and recieved error message {}", request, msgDescription);
                return AppConstants.FALIURE;
            }
            Query updateQuery = new Query();
            Update updateDocument = new Update();
            updateQuery.addCriteria(Criteria.where("transactionId").is(transactionId).and("sourcingDetails.agentId").is(agentId));
            setUpdateDocumentObjectBasedOnMsgDescription(updateDocument,msgDescription);
            mongoOperations.updateFirst(updateQuery, updateDocument, ProposalDetails.class);

            if(isCapitalGuaranteeSolutionProduct(proposalDetails)){
                Query updateQueryForSecondary = new Query();
                updateQueryForSecondary.addCriteria(Criteria.where("transactionId").is(proposalDetails.getSalesStoriesProductDetails().getSecondaryTransactionId()).and("sourcingDetails.agentId").is(agentId));
                mongoOperations.updateFirst(updateQueryForSecondary, updateDocument, ProposalDetails.class);
            }
        }
        return msgDescription;
    }

    private com.mli.mpro.onboarding.medicalSchedule.model.RequestData setBookLabAppointmentData(ProposalDetails proposalDetails) {
        com.mli.mpro.onboarding.medicalSchedule.model.Header header = new com.mli.mpro.onboarding.medicalSchedule.model.Header(String.valueOf(proposalDetails.getTransactionId()), SOA_APPID);
        String vendor=proposalDetails.getUnderwritingServiceDetails().getMedicalGridTPAIdentifierDetails().getMedicalTpaOutput();
        Category category=new Category(CATEGORY, REQUEST_TYPE,vendor);
        SimpleDateFormat newFormatter = new SimpleDateFormat(AppConstants.DATEFORMAT_WITHOUT_TIME_DD_MM_YYYY);
        com.mli.mpro.onboarding.medicalSchedule.model.Payload requestPayload=new com.mli.mpro.onboarding.medicalSchedule.model.Payload();
        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.YYYYMMDD);
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.HH_MM_A);
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.IST_TIME_ZONE));
        try {
            logger.info("Creating request for book lap appointment for transactionId {} formType {}",proposalDetails.getTransactionId(),getFormType(proposalDetails));
            PartyInformation partyInformation =  getPartyInfoWrtFormTypeForMedSchedule(proposalDetails);
            BasicDetails proposerBasicDetails = partyInformation.getBasicDetails();
            BasicDetails insurerDetails = proposalDetails.getPartyInformation().stream().filter(partInfo -> AppConstants.INSURED.equalsIgnoreCase(partInfo.getPartyType()))
                    .filter(Objects::nonNull).map(e -> e.getBasicDetails()).findFirst().orElse(null);
            AddressDetails addressDetails = proposerBasicDetails.getAddress().get(0).getAddressDetails();
            MedicalShedulingDetails details = proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails();
            ProductIllustrationResponse productDetails = proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse();
            double decalredIncome = 0;
            double premium = productDetails.getModalPremium();
            double sumAssured = productDetails.getSumAssured();
            String houseNo = addressDetails.getHouseNo();
            String area = addressDetails.getArea();
            String village = addressDetails.getVillage();
            String landmark = addressDetails.getLandmark();
            String commHouseNo = Stream.of(houseNo, area, village, landmark).filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(", "));
            String name = proposerBasicDetails.getFirstName() + " " + proposerBasicDetails.getLastName();
            String testName;
            String packageName;
            Date preferredDate = details.getPreferredDate();
            String prefDate = formatter.format(preferredDate);
            String preferredTime = dateFormat.format(preferredDate);
            Date date = details.getPreferredDate();
            String testDate = newFormatter.format(date);
            String testTime = dateFormat.format(date);
            requestPayload.setTestdate(testDate);
            requestPayload.setTestTime(testTime);
            requestPayload.setCaseId("");
            requestPayload.setLabId(details.getLabDetails().getLabId());
            requestPayload.setProposalNo(proposalDetails.getApplicationDetails().getPolicyNumber());
            requestPayload.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
            requestPayload.setIsMedicalRequired("true");
            requestPayload.setCommCity(addressDetails.getCity());
            requestPayload.setCommPinCode(addressDetails.getPinCode());
            requestPayload.setCommState(addressDetails.getState());
            requestPayload.setCommHouseNo(commHouseNo);
            requestPayload.setEmailId(partyInformation.getPersonalIdentification().getEmail());
            requestPayload.setFirstName(name);
            requestPayload.setMobileNumber(partyInformation.getPersonalIdentification().getPhone().get(0).getPhoneNumber());
            requestPayload.setTobaccoConsumption(converter(proposalDetails.getProductDetails().get(0).getProductInfo().isSmoker()));
            requestPayload.setLeadID("");
            requestPayload.setPrefLabID(details.getLabDetails().getLabId());
            requestPayload.setPrefTestDate(prefDate);
            requestPayload.setPrefTestTime(preferredTime);
            TestCase testCase =new TestCase();
            String medicalGridResponse = proposalDetails.getUnderwritingServiceDetails().getMedicalGridDetails().getResult();
            String visitType = details.getVisitType();
            String type = "";
            if (AppConstants.HOME_VISIT.equalsIgnoreCase(visitType)) {
                type = AppConstants.HOME_VISIT;
                requestPayload.setPrefLabID("");
                if (AppConstants.MDI.equalsIgnoreCase(vendor)) {
                    requestPayload.setLabId(AppConstants.MDI_STATIC_LAB_ID);
                }
            } else if (AppConstants.CENTER_VISIT.equalsIgnoreCase(visitType)) {
                type = AppConstants.CENTER_VISIT;
                requestPayload.setPrefLabID(details.getLabDetails().getLabId());
            }
            requestPayload.setServiceType(type);
            if (medicalGridResponse.contains(",")) {
                String[] medicalResponse = medicalGridResponse.split(",", 2);
                packageName = medicalResponse[0];
                testName = medicalResponse[1];
            } else {
                packageName = medicalGridResponse;
                testName = "";
            }
            testCase.setPackageName(packageName);
            testCase.setTestName(testName);
            List<TestCase> testList = new ArrayList<>();
            testList.add(testCase);
            requestPayload.setTestlist(new Testlist(testList));
            if (AppConstants.SELF.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()) ||
                    AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()) ) {
                Date dob = proposerBasicDetails.getDob();
                String dateOfBirth = formatter.format(dob);
                requestPayload.setDateOfBirth(dateOfBirth);
                requestPayload.setGender(setGender(proposerBasicDetails.getGender()));
                decalredIncome = Double.valueOf(proposerBasicDetails.getAnnualIncome());
            } else if (AppConstants.FORM2.equalsIgnoreCase(getFormType(proposalDetails)) && insurerDetails != null){
                Date dob = insurerDetails.getDob();
                String dateOfBirth = formatter.format(dob);
                requestPayload.setDateOfBirth(dateOfBirth);
                requestPayload.setGender(setGender(insurerDetails.getGender()));
                decalredIncome = Double.valueOf(insurerDetails.getAnnualIncome());
                if(isSWPVariantFour(proposalDetails))
                {
                    name = insurerDetails.getFirstName() + " " + insurerDetails.getLastName();
                    requestPayload.setFirstName(name);

                }
            }

            if (premium > 1000000 || sumAssured > 100000 || decalredIncome > 100000) {
                requestPayload.setAmount("1");
            } else {
                requestPayload.setAmount("0");
            }

        } catch (Exception ex) {
            logger.error("Exception occured while setting data to call api",ex);
        }
        RequestData requestData=new RequestData(new Request(header, category, requestPayload));
        return requestData;

    }

    private void setUpdateDocumentObjectBasedOnMsgDescription(Update updateDocument,String msgDescription)
    {
        updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.scheduleStatus", msgDescription);
        if(AppConstants.APPOINTMENT_ALREADY_CONFIRMED.equals(msgDescription))
        {
            logger.info("For the transactionId {} mannual scheduling happened by the vendor {}",msgDescription);
            updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.preferredDate", null);
            updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.visitType", AppConstants.BLANK);
            updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.labDetails.labId", AppConstants.BLANK);
            updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.labDetails.name", AppConstants.BLANK);
            updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.labDetails.address", AppConstants.BLANK);
            updateDocument.set("underwritingServiceDetails.medicalShedulingDetails.labIndex", AppConstants.BLANK);
        }
    }
    public static boolean isCapitalGuaranteeSolutionProduct(ProposalDetails proposalDetails) {
        return proposalDetails != null
                && proposalDetails.getSalesStoriesProductDetails() != null
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())
                && AppConstants.CAPITAL_GUARANTEE_SOLUTION.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails()
                .getProductDetails().get(0).getProductInfo().getProductId());
    }
    private String setGender(String gender) {
        if (gender.equalsIgnoreCase("M")) {
            gender = "Male";
        } else if (gender.equalsIgnoreCase("F")) {
            gender = "Female";
        }
        return gender;
    }

    private String converter(boolean value) {
        if (value) {
            return "true";
        } else {
            return "false";
        }
    }

    public static boolean isSWPVariantFour(ProposalDetails proposalDetails) {
        if(proposalDetails.getProductDetails()==null ||
                proposalDetails.getProductDetails().isEmpty()) {
            return false;
        }
        return proposalDetails.getProductDetails().stream()
                .anyMatch(pd -> pd.getProductInfo()!=null &&
                        AppConstants.SMART_WEALTH_PLAN.equals(pd.getProductInfo().getProductId()) &&
                        AppConstants.WHOLE_LIFE_VARIANT.equalsIgnoreCase(pd.getProductInfo().getVariant()));
    }
    public static PartyInformation getPartyInfoWrtFormTypeForMedSchedule(ProposalDetails proposalDetails){
        Stream<PartyInformation> partyInfoStream =proposalDetails.getPartyInformation().stream().filter(Objects::nonNull);
        if(isFormCExceptAxisOrYblTeleSales(proposalDetails)){
            partyInfoStream = partyInfoStream.filter(partyInfo -> AppConstants.INSURED.equalsIgnoreCase(partyInfo.getPartyType()));
        }else{
            partyInfoStream = partyInfoStream.filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType()));
        }
        return partyInfoStream.findFirst().orElse(new PartyInformation());
    }
    public static boolean isFormCExceptAxisOrYblTeleSales(ProposalDetails proposalDetails){
        return isForm3(proposalDetails) && !isAxisOrYblTeleSales(proposalDetails);
    }
    public static boolean isForm3(ProposalDetails proposalDetails) {
        return AppConstants.FORM3.equalsIgnoreCase(getFormType(proposalDetails))
                && !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType());
    }
    public static String getFormType(ProposalDetails proposalDetails) {
        if(proposalDetails==null
                || proposalDetails.getApplicationDetails()==null
                || proposalDetails.getApplicationDetails().getFormType()==null) {
            return "";
        }
        return proposalDetails.getApplicationDetails().getFormType();
    }
    public static boolean isAxisOrYblTeleSales(ProposalDetails proposalDetails) {
        return !StringUtils.isEmpty(getRequestSource(proposalDetails)) &&
                (AppConstants.AXIS_TELESALES_REQUEST.equalsIgnoreCase(getRequestSource(proposalDetails)))
                || isYblTeleSales(proposalDetails);
    }
    public static String getRequestSource(ProposalDetails proposalDetails) {
        return proposalDetails.getAdditionalFlags().getRequestSource();
    }
    public static boolean isYblTeleSales(ProposalDetails proposalDetails) {
        return proposalDetails.getAdditionalFlags().isYblTelesalesCase();
    }
    private com.mli.mpro.onboarding.documents.model.InputRequest deserializeMedicalSchedulingRequest(String decryptedString) throws IOException {

        return objectMapper.readValue(decryptedString, com.mli.mpro.onboarding.documents.model.InputRequest.class);

    }
    private boolean validateMedicalRequest( Date preferredDate, String visitType, String labId, String labName, String labAddress){
        if (preferredDate == null) {
            return false;
        }
        if(!AppConstants.HOME_VISIT.equalsIgnoreCase(visitType) && !AppConstants.CENTER_VISIT.equalsIgnoreCase(visitType)){
            return false;
        }
        if(AppConstants.CENTER_VISIT.equalsIgnoreCase(visitType)){
            if(!StringUtils.hasText(labId) || !StringUtils.hasText(labAddress) || !StringUtils.hasText(labName)){
                return false;
            }
        }
        return true;
    }
    public  RequestResponse buildMedicalScheduleErrorResponse(String msg, String msgDescription, String msgCode){
        RequestResponse requestResponse=new RequestResponse();
        OutputResponse outputResponse = new OutputResponse();
        Response response = new Response();
        ErrorResponse errorResponse=new ErrorResponse();
        List<String> errorMessage=new ArrayList<>();
        com.mli.mpro.proposal.models.ResponseData responseData =new com.mli.mpro.proposal.models.ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();
        List<Object> responseMessage = new ArrayList<>();
        errorMessage.add(msgDescription);
        errorResponse.setErrorMessages(errorMessage);
        responseMessage.add(msg);
        responsePayload.setMessage(responseMessage);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        response.setErrorResponse(errorResponse);
        outputResponse.setResponse(response);
        requestResponse.setPayload(objectToString(outputResponse));
        return requestResponse;
    }

}
