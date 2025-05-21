package com.mli.mpro.onboarding.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.mli.mpro.agent.models.Request;
import com.mli.mpro.agent.models.SoaApiRequest;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.configuration.models.Configuration;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.location.models.Stage;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.PolicyHistoryRequest;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.PolicyHistoryResponse;
import com.mli.mpro.location.models.soaCloudModels.SoaCloudResponse;
import com.mli.mpro.location.repository.SellerConsentDetailsRepository;
import com.mli.mpro.location.services.SoaCloudService;
import com.mli.mpro.onboarding.documents.model.RequiredDocuments;
import com.mli.mpro.onboarding.illustration.pdf.model.DeclarationData;
import com.mli.mpro.onboarding.illustration.pdf.model.IllustrationPdfRequest;
import com.mli.mpro.onboarding.medicalSchedule.model.Category;
import com.mli.mpro.onboarding.medicalSchedule.model.TestCase;
import com.mli.mpro.onboarding.medicalSchedule.model.Testlist;
import com.mli.mpro.onboarding.model.*;
import com.mli.mpro.onboarding.model.InputRequest;
import com.mli.mpro.onboarding.model.OutputResponse;
import com.mli.mpro.onboarding.model.Response;
import com.mli.mpro.onboarding.model.ResponsePayload;
import com.mli.mpro.onboarding.partner.model.DedupeAPIPayload;
import com.mli.mpro.onboarding.partner.service.APIService;
import com.mli.mpro.onboarding.partner.service.handler.DedupeServiceHandler;
import com.mli.mpro.onboarding.util.S3Utility;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.service.impl.ProductRestrictionServiceImpl;
import com.mli.mpro.productRestriction.service.ProductRestrictionService;
import com.mli.mpro.productRestriction.service.impl.ProductRestrictionServiceImpl;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import com.mli.mpro.utils.RSAEncryptionDecryptionUtil;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.onboarding.service.OnboardingService;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import software.amazon.awssdk.utils.IoUtils;

import static com.mli.mpro.productRestriction.util.AppConstants.CBC_ALGO_PADDING1;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.mli.mpro.productRestriction.util.AppConstants.*;


@Service
public class OnboardingServiceImpl implements OnboardingService{

    private static final Logger logger = LoggerFactory.getLogger(OnboardingServiceImpl.class);

    @Value("${urlDetails.onboardingPolicyStatusURL}")
    private String url;

    @Value("${bypass.header.encrypt.value}")
    private String api_client_secret;

    @Value("${urlDetails.policystatusxapigwapiid}")
    private String xapigwapiid;
    
    @Value("${urlDetails.policystatusxapikey}")
    private String xapikey;

    @Value("${urlDetails.IllustarionPdf}")
    private String lifeEngagePdfCreationUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuditService auditService;
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    ProposalRepository repository;

    @Value("${urlDetails.proposalService}")
    private String getProposalData;



    @Autowired
    private SellerConsentDetailsRepository sellerConsentDetailsRepository;

    @Autowired
    public ConfigurationRepository configurationRepository;


    @Autowired
    private S3Utility s3Utility;

    @Autowired
    private ProductRestrictionServiceImpl productRestrictionService;
    @Autowired
    DedupeServiceHandler apiServiceHandler;

    @Autowired
    private RSAEncryptionDecryptionUtil rsaEncryptionDecryptionUtil;
    @Autowired
    private APIService apiService;

    @Autowired
    private MedicalServiceImpl medicalService;

    @Autowired
    private SoaCloudService soaCloudService;

    String[] keyAndIVParts = null;

    @Override
    public RequestResponse viewPolicyStatus(RequestResponse inputPayload, @RequestHeader MultiValueMap<String, String> headerMap, ErrorResponseParams errorResponseParams) {

        logger.info("Inside the view policy status ");
        String encryptionSource=null;
        String decryptedString=null;
        String kek=null;
        InputRequest inputRequest = new InputRequest();
        RequestResponse requestResponse = new RequestResponse();
        String requestId = (UUID.randomUUID().toString());
        OutputResponse outputResponse = new OutputResponse();
        List<CurrentStatus> currentStatusListPreDolphin = new ArrayList<CurrentStatus>();
        ArrayList<String> proposalNumbers = new ArrayList<String>();
        try {
            encryptionSource= productRestrictionService.getEncryptionSource(headerMap);
            if(AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)){
                kek = headerMap.getFirst("kek");
                logger.info("Inside ujjivan utility kek {} " , kek);
                errorResponseParams.setEncryptionSource(encryptionSource);
                decryptedString = productRestrictionService.decryption(inputPayload.getPayload(), kek, errorResponseParams);
                //utility code for Ujjivan
                logger.info("Inside ujjivan utility decryptedString {} " , decryptedString);
            }
            else{
                decryptedString = Util.decryptRequest(inputPayload.getPayload());
            }
            inputRequest = deserializeRequest(decryptedString);
            boolean failedResponse = false;
            logger.info("Before entering ");
            ProposalDetails proposalDetailsObj = getProposalDetails(inputRequest);
            String goCABrokerCode = proposalDetailsObj.getSourcingDetails().getGoCABrokerCode();
            logger.info("policy status goCABrokerCode is {}",goCABrokerCode);
            boolean goCodeFeatureFlag = isGoCodeFeatureFlagEnabled(goCABrokerCode);
            logger.info("goCodeFeatureFlag : {}",goCodeFeatureFlag);
            logger.info("Feature flag for policy status {}",FeatureFlagUtil.isFeatureFlagEnabled(ENABLE_POST_DOLPHINE_POLICY_STATUS));
            if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(ENABLE_POST_DOLPHINE_POLICY_STATUS)
                    && goCodeFeatureFlag
                    && hasValidPolicyNoOrQuoteId(inputRequest))
                    && Boolean.TRUE.equals(isDolphinPushed(inputRequest,proposalDetailsObj)))
            {
                logger.info("Validations checked ");

                SoaCloudResponse<PolicyHistoryResponse> saralResponse = saralPolicyHistory(inputRequest);
                logger.info("Policy history response inside policy status {}", saralResponse);
                return setResponsePayload(encryptionSource, errorResponseParams, setDolphinResponse(saralResponse, outputResponse, inputRequest), requestResponse);
            }else{
                logger.info("Mpro policy status ");
                if(inputRequest.getRequest().getPayload().getPolicyNumber()!=null){
                    logger.info("Input request receive for policyNumber {}", inputRequest.getRequest().getPayload().getPolicyNumber());
                    List<String> policyNum = inputRequest.getRequest().getPayload().getPolicyNumber();

                    for(String singlePolicyNum : policyNum) {
                        ProposalDetails proposalDetails = repository.findByApplicationDetailsPolicyNumber(singlePolicyNum);
                        logger.info("proposal details fetched for policyNum {} {}", singlePolicyNum,  proposalDetails);
                        if (Objects.nonNull(proposalDetails)) {
                            String stage = proposalDetails.getApplicationDetails().getStage();
                            if (AppConstants.STAGE_SEVEN.equals(stage)) {
                                proposalNumbers.add(proposalDetails.getApplicationDetails().getPolicyNumber());
                            } else {
                                //String status = checkPreDolphinPushStatus(proposalDetails);
                                Stage stageObject = deriveStage(proposalDetails);
                                logger.info("StageObject for policyNum {} {}", policyNum, stageObject);
                                CurrentStatus currentStatus = findCurrentStatus(proposalDetails,stageObject);
                                logger.info("CurrentStatus object for policyNum {} {}", policyNum, currentStatus);
                                currentStatusListPreDolphin.add(currentStatus);
                            }
                        }
                    }
                }
                else{
                    logger.info("Input request receive for quoteId {}", inputRequest.getRequest().getPayload().getQuoteId());
                    List<String> quoteId = inputRequest.getRequest().getPayload().getQuoteId();

                    for(String singleQuoteId : quoteId) {
                        ProposalDetails proposalDetails = repository.findByApplicationDetailsQuoteId(singleQuoteId);
                        logger.info("proposal details fetched for quoteId {} {}", singleQuoteId,  proposalDetails);
                        if (Objects.nonNull(proposalDetails)) {
                            String stage = proposalDetails.getApplicationDetails().getStage();
                            if (AppConstants.STAGE_SEVEN.equals(stage)) {
                                proposalNumbers.add(proposalDetails.getApplicationDetails().getPolicyNumber());
                            } else {
                                //String status = checkPreDolphinPushStatus(proposalDetails);
                                Stage stageObject = deriveStage(proposalDetails);
                                logger.info("StageObject for quoteId {} {}", quoteId, stageObject);
                                CurrentStatus currentStatus = findCurrentStatus(proposalDetails,stageObject);
                                logger.info("CurrentStatus object for quoteId {} {}", quoteId, currentStatus);
                                currentStatusListPreDolphin.add(currentStatus);
                            }
                        }
                    }
                }
                if(!proposalNumbers.isEmpty()) {
                    Header header = new Header();
                    header.setCorrelationId(UUID.randomUUID().toString());
                    header.setAppId(AppConstants.FULFILLMENT);
                    inputRequest.getRequest().setHeader(header);
                    logger.info("inputRequest:{}", inputRequest);
                    requestId = inputRequest.getRequest().getHeader().getCorrelationId();

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    headers.add("x-apigw-api-id", xapigwapiid);
                    headers.add("x-api-key", xapikey);
                    inputRequest.getRequest().getPayload().setQuoteId(null);
                    inputRequest.getRequest().getPayload().setPolicyNumber(proposalNumbers);
                    HttpEntity<Object> request = new HttpEntity<>(inputRequest, headers);
                    ResponseEntity<SOAResponse> response = new RestTemplate().postForEntity(url, request, SOAResponse.class);
                    if (response.getBody().getResponse().getMsginfo().getMsgCode().equals(AppConstants.SUCCESS_RESPONSE)) {
                        List<CurrentStatus> currentStatus = response.getBody().getResponse().getPayload().getCurrentStatus();
                        currentStatus.addAll(currentStatusListPreDolphin);
                        response.getBody().getResponse().getPayload().setCurrentStatus(currentStatus);
                    } else {
                        failedResponse = true;
                    }
                    logger.info("SOA Response:{}", response.getBody());

                    Response policyStatusResponse = null;

                    if (response.getBody() != null) {
                        policyStatusResponse = new Response(response.getBody().getResponse().getHeader(), response.getBody().getResponse().getMsginfo(), response.getBody().getResponse().getPayload());
                    }

                    logger.info("policyStatusResponse: {}", policyStatusResponse);
                    outputResponse.setPolicyStatusResponse(policyStatusResponse);
                    logger.info("OutputResponse:{},SoaCorrelationId:{},requestId:{}", outputResponse, header.getCorrelationId(), requestId);

                    requestResponse = setResponsePayload(encryptionSource,errorResponseParams,outputResponse,requestResponse);
                }
                if (proposalNumbers.isEmpty() || failedResponse) {
                    ResponsePayload responsePayload = new ResponsePayload();
                    responsePayload.setCurrentStatus(currentStatusListPreDolphin);
                    Response policyStatusResponse = new Response(new Header(inputRequest.getRequest().getHeader().getCorrelationId(),
                            inputRequest.getRequest().getHeader().getAppId()),
                            new MsgInfo(AppConstants.SUCCESS_RESPONSE, AppConstants.SUCCESS, AppConstants.DATA_FOUND),
                            responsePayload);
                    outputResponse.setPolicyStatusResponse(policyStatusResponse);

                    if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
                        requestResponse.setPayload(EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse), errorResponseParams.getIVandKey()[0], errorResponseParams.getIVandKey()[1].getBytes()));
                    }else {
                        requestResponse = Util.encryptResponse(outputResponse);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
//            saveRequestResponseInAuditDB(inputRequest, requestId, outputResponse);
        }
        return requestResponse;
    }

    public boolean isMedicalSchedulingDone(ProposalDetails proposalDetails) {
        try {

            ProductInfo productInfo = proposalDetails.getProductDetails().get(0).getProductInfo();
            logger.info("inside isMedicalSchedulingDone for transactionId {} with scheduling {} and product details {}",
                    proposalDetails.getTransactionId(),
                    proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails(),
                    productInfo);
            if ((AppConstants.MEDICAL_SCHEDULING_ENABLED_PRODUCT_IDS.contains(productInfo.getProductId())
                    || (AppConstants.SMART_WEALTH_PLAN.equalsIgnoreCase(productInfo.getProductId())
                    && AppConstants.WHOLE_LIFE_VARIANT.equalsIgnoreCase(productInfo.getVariant())))
                    && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getMedicalGridStatus())
                    && proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getMedicalGridStatus().contains(AppConstants.CAT_MEDICAL_CATEGORY)
                    && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0))
                    && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry())
                    && AppConstants.INDIA.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry())
            ) {
                return AppConstants.MEDICAL_SCHEDULING.stream().anyMatch(s -> s.equalsIgnoreCase(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getScheduleStatus()));
            }else{
                return true;
            }
        }catch (Exception ex){
            logger.info("error in isMedicalSchedulingDone with msg {}", Utility.getExceptionAsString(ex));
        }
        return true;
    }

    private InputRequest deserializeRequest(String decryptedString) throws IOException {

        return objectMapper.readValue(decryptedString, InputRequest.class);

    }

    private com.mli.mpro.onboarding.documents.model.InputRequest deserializeDocumentsRequest(String decryptedString) throws IOException {

        return objectMapper.readValue(decryptedString, com.mli.mpro.onboarding.documents.model.InputRequest.class);

    }
    private com.mli.mpro.onboarding.medicalGridDetials.model.InputRequest deserializeMedicalGridCaseRequest(String decryptedString) throws IOException {

        return objectMapper.readValue(decryptedString, com.mli.mpro.onboarding.medicalGridDetials.model.InputRequest.class);

    }

    private void saveRequestResponseInAuditDB(InputRequest inputRequest, String requestId, OutputResponse response) {
        try {
            AuditingDetails auditDetails = new AuditingDetails();
            auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
            auditDetails.setServiceName(AppConstants.POLICYSTATUS_SERVICE);
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


    private Stage deriveStage(ProposalDetails proposalDetails) {

        try {
            if(Objects.isNull(proposalDetails) || Objects.isNull(proposalDetails.getApplicationDetails())){
                return null;
            }
            logger.info("For policyStatus API,  proposalDetails.applicationDetails.stage: {}, policyNumber: {}",
                    proposalDetails.getApplicationDetails().getStage(),
                    proposalDetails.getApplicationDetails().getPolicyNumber());
            switch (proposalDetails.getApplicationDetails().getStage()) {
                case AppConstants.STAGE_A:
                    return new Stage("43", AppConstants.ADDITIONAL_INFORMATION);
                case AppConstants.STAGE_B:
                    return new Stage("82", AppConstants.DISCREPANT);
                case AppConstants.STAGE_C:
                    return new Stage("48", AppConstants.COUNTER_OFFER);
                case AppConstants.STAGE_8:
                    return new Stage("46", AppConstants.ISSUED);
                case AppConstants.STAGE_9:
                    return new Stage("45", AppConstants.REJECTED);
                default:
                    break;
            }

            String stage = proposalDetails.getApplicationDetails().getStage();
            String sellerConsentStatus = Utility.isSellerDeclarationCompleted(proposalDetails, sellerConsentDetailsRepository, configurationRepository)?AppConstants.COMPLETED : AppConstants.PENDING;
            String posvJourneyStatus = proposalDetails.getApplicationDetails().getPosvJourneyStatus();
            if (Utility.isPaymentCompleted(proposalDetails)) {
                return new Stage ("32", "Payment completed");
            }

            if (org.apache.commons.lang3.StringUtils.isNotEmpty(stage)) {
                int stageInNumber = Integer.parseInt(stage.trim());
                if(AppConstants.STAGE_ONE.equals(stage) || AppConstants.STAGE_TWO.equals(stage)){
                    return new Stage ("12", "Proposal form pending");
                }
                if(AppConstants.STAGE_THREE.equals(stage) && Objects.nonNull(proposalDetails.getApplicationDetails().getbIGeneratedDateOriginal())){
                    return new Stage ("13", "BI Finalized");
                }
                boolean isAllDocumentUploaded = documentUploadStatus(proposalDetails, stageInNumber);
                logger.info("Deriving lead update stage for policyNumber: {}, Stage: {}, SellerConsentStatus: {} and PosvJourneyStatus: {}, DocumentUploadStatus: {}",
                        proposalDetails.getApplicationDetails().getPolicyNumber(), stage, sellerConsentStatus, posvJourneyStatus, isAllDocumentUploaded);
                if(!isMedicalSchedulingDone(proposalDetails)){
                    return new Stage("42", "Medicals pending");
                }
                Stage stageObject = getStage(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded, proposalDetails.getAdditionalFlags().isPaymentDone());
                if (Objects.nonNull(stageObject)) {
                    return stageObject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured while fetching the stage for policyStatus api for PolicyNumber: {}",
                    proposalDetails.getApplicationDetails().getPolicyNumber());
        }
        return null;
    }

    private String getSellerConsentStatus(String policyNumber) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(policyNumber))
            return AppConstants.NOT_INITIATED;
        Optional<SellerConsentDetails> sellerConsentDetails = Optional.ofNullable(sellerConsentDetailsRepository.findByPolicyNumber(policyNumber));
        return sellerConsentDetails.map(consentDetails -> consentDetails.getSellerConsentStatus().name()).orElse(AppConstants.NOT_INITIATED);
    }

    private boolean documentUploadStatus(ProposalDetails proposalDetails, int stageNumber) {
        try {
            if (stageNumber >= 5) {
                logger.info("Underwriting status for policyStatus api is :{}", proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus());
                return Utility.isAllDocumentUploaded(proposalDetails);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private Stage getStage(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded, boolean isPaymentDone) {
        if (getStageFourValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("93", "POSV triggered, Document upload pending, seller declaration triggered");
        }
        if (getStageFiveValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("94", "POSV completed, Document upload pending, seller declaration triggered");
        }
        if (getStageSixValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("95", "POSV triggered, Document upload completed, seller declaration triggered");
        }
        if (getStageSevenValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("96", "POSV completed,Document upload completed,seller declaration triggered");
        }
        if (getStageEightValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("97", "POSV triggered,Document upload pending,seller declaration completed");
        }
        if (getStageNineValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("98", "POSV completed, Document upload pending, seller declaration completed");
        }
        if (getStageTenValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded)) {
            return new Stage ("99", "POSV triggered, Document upload completed, seller declaration completed");
        }
        if (getStageElevenValidation(posvJourneyStatus, sellerConsentStatus, isAllDocumentUploaded, isPaymentDone)) {
            return new Stage ("31", "Payment pending");
        }
        return null;
    }
    private boolean getStageFourValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return (AppConstants.POSV_TRIGGERED_STATUS_LIST.contains(posvJourneyStatus) &&
                !isAllDocumentUploaded && AppConstants.PENDING.equalsIgnoreCase(sellerConsentStatus));
    }

    private boolean getStageFiveValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return AppConstants.POSV_BACKFLOW_MESSAGE.equalsIgnoreCase(posvJourneyStatus) &&
                !isAllDocumentUploaded && AppConstants.PENDING.equalsIgnoreCase(sellerConsentStatus);
    }

    private boolean getStageSixValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return (AppConstants.POSV_TRIGGERED_STATUS_LIST.contains(posvJourneyStatus) &&
                isAllDocumentUploaded && AppConstants.PENDING.equalsIgnoreCase(sellerConsentStatus));
    }

    private boolean getStageSevenValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return AppConstants.POSV_BACKFLOW_MESSAGE.equalsIgnoreCase(posvJourneyStatus) &&
                isAllDocumentUploaded && AppConstants.PENDING.equalsIgnoreCase(sellerConsentStatus);
    }

    private boolean getStageEightValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return (AppConstants.POSV_TRIGGERED_STATUS_LIST.contains(posvJourneyStatus) &&
                !isAllDocumentUploaded && AppConstants.COMPLETED.equalsIgnoreCase(sellerConsentStatus));
    }

    private boolean getStageNineValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return AppConstants.POSV_BACKFLOW_MESSAGE.equalsIgnoreCase(posvJourneyStatus) &&
                !isAllDocumentUploaded && AppConstants.COMPLETED.equalsIgnoreCase(sellerConsentStatus);
    }

    private boolean getStageTenValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded) {
        return (AppConstants.POSV_TRIGGERED_STATUS_LIST.contains(posvJourneyStatus) &&
                isAllDocumentUploaded && AppConstants.COMPLETED.equalsIgnoreCase(sellerConsentStatus));
    }

    private boolean getStageElevenValidation(String posvJourneyStatus, String sellerConsentStatus, boolean isAllDocumentUploaded, boolean isPaymentDone) {
        return (AppConstants.POSV_BACKFLOW_MESSAGE.equalsIgnoreCase(posvJourneyStatus) &&
                isAllDocumentUploaded && AppConstants.COMPLETED.equalsIgnoreCase(sellerConsentStatus) && !isPaymentDone);
    }

    @Override
    public RequestResponse getDocumentsList(RequestResponse inputPayload) {
        com.mli.mpro.onboarding.documents.model.InputRequest inputRequest = new com.mli.mpro.onboarding.documents.model.InputRequest();
        RequestResponse requestResponse = new RequestResponse();
        com.mli.mpro.onboarding.documents.model.OutputResponse outputResponse = new com.mli.mpro.onboarding.documents.model.OutputResponse();
        com.mli.mpro.onboarding.documents.model.Response response = new com.mli.mpro.onboarding.documents.model.Response();
        com.mli.mpro.onboarding.documents.model.ResponsePayload responsePayload = new com.mli.mpro.onboarding.documents.model.ResponsePayload();
        List<DocumentDetails> documentDetails = new ArrayList<>();
        List<RequiredDocuments> requiredDocumentsList = new ArrayList<>();
        ProposalDetails proposalDetails = null;
        List<Object> errors = new ArrayList<>();
        try {
            String decryptedPayload = Util.decryptRequest(inputPayload.getPayload());
            inputRequest = deserializeDocumentsRequest(decryptedPayload);
            String policyNumber = inputRequest.getRequest().getPayload().getPolicyNumber();
            String quoteId = inputRequest.getRequest().getPayload().getQuoteId();

            if(ObjectUtils.isEmpty(policyNumber) && ObjectUtils.isEmpty(quoteId)) {
                    errors.add(AppConstants.NO_POLICY_EXIST);
                    return Util.errorResponse(HttpStatus.BAD_REQUEST, errors,null,null);
            }

            if(ObjectUtils.isEmpty(policyNumber))
            { proposalDetails = repository.findByApplicationDetailsQuoteId(quoteId);}
            else
            {proposalDetails = repository.findByApplicationDetailsPolicyNumber(policyNumber);}



            logger.info("input request for document API {}",proposalDetails);
            if(Objects.isNull(proposalDetails)) {
                errors.add(AppConstants.NO_POLICY_EXIST);
                return Util.errorResponse(HttpStatus.BAD_REQUEST,errors,null,null);
            }
            if((Objects.nonNull(proposalDetails.getAdditionalFlags().getSourceChannel()) && !proposalDetails.getAdditionalFlags().getSourceChannel().equalsIgnoreCase(PROPOSAL_E2E_TRANSFORMATION))&&!documentListEligibility(proposalDetails)) {
                errors.add("Documents not generated");
                return Util.errorResponse(HttpStatus.BAD_REQUEST,errors,null,null);
            }
            documentDetails = proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments();
            for(DocumentDetails documentDetails1:documentDetails) {
                RequiredDocuments requiredDocuments = new RequiredDocuments();
                Map<String, String> updatedDocMap = new HashMap<>();
                String docId = documentDetails1.getMproDocumentId().length() > 2 ? documentDetails1.getMproDocumentId().substring(documentDetails1.getMproDocumentId().length() - 2):documentDetails1.getMproDocumentId();
                if(documentDetails1.isRequiredForMproUi() && validateDocumentId(docId)) {
                    if(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId().equals(AppConstants.SSP_PRODUCT_ID) &&
                            proposalDetails.getPartyInformation().get(0).getBasicDetails().getOccupation().equalsIgnoreCase(AppConstants.SALARIED)) {
                            updatedDocMap = swissReDocReNaming(updatedDocMap);
                            if(updatedDocMap.containsKey(documentDetails1.getMproDocumentId())) {
                            requiredDocuments.setDocumentName(updatedDocMap.get(documentDetails1.getMproDocumentId()));
                            }
                            else {
                                requiredDocuments.setDocumentName(documentDetails1.getDocumentName());
                            }
                }
                else {
                        requiredDocuments.setDocumentName(documentDetails1.getDocumentName());
                    }
                    requiredDocuments.setDocumentId(documentDetails1.getMproDocumentId());
                    requiredDocuments.setDocumentType(documentDetails1.getType());
                    if(documentDetails1.ismProDocumentStatus()) {
                        requiredDocuments.setDocumentStatus(AppConstants.UPLOADED);
                    }
                    else{
                        requiredDocuments.setDocumentStatus(AppConstants.REQUIRED);
                    }
                    requiredDocumentsList.add(requiredDocuments);
                }

            }
            responsePayload.setRequiredDocuments(requiredDocumentsList);
            response.setPayload(responsePayload);
            response.setMsginfo(new MsgInfo(AppConstants.SUCCESS_RESPONSE, AppConstants.SUCCESS, AppConstants.DATA_FOUND));
            outputResponse.setResponse(response);
            requestResponse = Util.encryptDocumentResponse(outputResponse);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return requestResponse;
    }

    private boolean validateDocumentId(String docId) {
        if(docId.equalsIgnoreCase("PR") ||docId.equalsIgnoreCase("IN")||docId.equalsIgnoreCase("PA")
                ||docId.equalsIgnoreCase("CO")||docId.equalsIgnoreCase("AS")) {
            return true;
        }
        return false;
    }

    @Override
    public RequestResponse getIllustrationPdf(RequestResponse inputPayload,@RequestHeader MultiValueMap<String, String> headerMap,ErrorResponseParams errorResponseParams) {
        com.mli.mpro.onboarding.documents.model.InputRequest inputRequest;
        RequestResponse requestResponse = new RequestResponse();
        com.mli.mpro.onboarding.illustration.pdf.model.OutputResponse outputResponse = new com.mli.mpro.onboarding.illustration.pdf.model.OutputResponse();
        com.mli.mpro.onboarding.illustration.pdf.model.Response response = new com.mli.mpro.onboarding.illustration.pdf.model.Response();
        com.mli.mpro.onboarding.illustration.pdf.model.ResponsePayload responsePayload = new com.mli.mpro.onboarding.illustration.pdf.model.ResponsePayload();
        String responseByQuoteId=null;
        String responseByPolicyNumber=null;
        ProposalDetails proposalDetails = null;
        List<Object> errors = new ArrayList<>();
        String encryptionSource=null;
        String kek=null;
        String decryptedPayload = null;
        logger.info("HeaderMap : ", headerMap);

        try {
            encryptionSource=productRestrictionService.getEncryptionSource(headerMap);
            if(AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)){
                kek = headerMap.getFirst("kek");
                logger.info("Inside ujjivan utility kek {} " , kek);
                errorResponseParams.setEncryptionSource(encryptionSource);
                decryptedPayload = productRestrictionService.decryption(inputPayload.getPayload(), kek, errorResponseParams);
                //utility code for Ujjivan
                logger.info("Inside ujjivan utility decryptedString {} " , decryptedPayload);
            }else {
                decryptedPayload = Util.decryptRequest(inputPayload.getPayload());
            }
            inputRequest = deserializeDocumentsRequest(decryptedPayload);
            String policyNumber = inputRequest.getRequest().getPayload().getPolicyNumber();
            String quoteId = inputRequest.getRequest().getPayload().getQuoteId();

            logger.info("Request for illustration-pdf API for policyNumber:{} or quoteID:{}",policyNumber,quoteId);

            if (ObjectUtils.isEmpty(policyNumber) && ObjectUtils.isEmpty(quoteId)) {
                errors.add(AppConstants.NO_DATA_FOUND);
                return Util.errorResponse(HttpStatus.BAD_REQUEST, errors,errorResponseParams.getEncryptionSource(),errorResponseParams.getIVandKey());
            }

            if(!ObjectUtils.isEmpty(policyNumber))
            { proposalDetails=repository.findByApplicationDetailsPolicyNumber(policyNumber);
                logger.info("proposalDetails for illustration-pdf API {}", proposalDetails);
            }

            if(!ObjectUtils.isEmpty(proposalDetails)){
                responseByPolicyNumber=getPdfFromS3(proposalDetails);
                logger.info("response by policyNumber {} for illustration-pdf API {}", policyNumber,responseByPolicyNumber);
            }

            if(!ObjectUtils.isEmpty(quoteId)&&ObjectUtils.isEmpty(responseByPolicyNumber)){
                responseByQuoteId=getPdfByQuoteId(quoteId,policyNumber);
                logger.info("response by quote-id {} for illustration-pdf API {}",quoteId, responseByQuoteId);

            }


            if(!ObjectUtils.isEmpty(responseByPolicyNumber)){

                responsePayload.setPayloadBody(responseByPolicyNumber);
            }
            else if(!ObjectUtils.isEmpty(responseByQuoteId)&&(ObjectUtils.isEmpty(responseByPolicyNumber))){
                responsePayload.setPayloadBody(responseByQuoteId);
            }
            else{
                errors.add(AppConstants.NO_DATA_FOUND);
                return Util.errorResponse(HttpStatus.BAD_REQUEST, errors,errorResponseParams.getEncryptionSource(),errorResponseParams.getIVandKey());
            }

            response.setPayload(responsePayload);
            response.setMsginfo(new MsgInfo(AppConstants.SUCCESS_RESPONSE, AppConstants.SUCCESS, AppConstants.DATA_FOUND));
            outputResponse.setResponse(response);
            logger.info("output-Response for illustration-pdf API {}", outputResponse);

            if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
                requestResponse.setPayload(EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse), errorResponseParams.getIVandKey()[0], errorResponseParams.getIVandKey()[1].getBytes()));
            }else {
                requestResponse = Util.encryptDocumentResponse(outputResponse);
            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }


      return requestResponse;

    }

    private String getPdfFromS3( ProposalDetails proposalDetails) {

        DocumentDetails details = new DocumentDetails();
        String encodedString=null;
        details.setDocumentName(AppConstants.ILLUSTRATION_PATH);
        details.setDocumentType(AppConstants.DOCUMENT_TYPE);
        details.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
        details.setChannelName(proposalDetails.getChannelDetails().getChannel());
        details.setTransactionId(proposalDetails.getTransactionId());

        logger.info("DocumentDetails passing for S3 {}", details);

        try{
        S3Object obj = s3Utility.fetchDocumentFromBucket(details);

        if (obj != null) {
            S3ObjectInputStream stream = obj.getObjectContent();
            byte[] byteArray = IoUtils.toByteArray(stream);
            encodedString = com.amazonaws.util.Base64.encodeAsString(byteArray);
        }

        } catch (Exception e) {
            logger.error("Exception Occured :{}",e.getMessage());
        }

        return encodedString;

    }

    private String getPdfByQuoteId(String quoteId,String PolicyNumber) {
        String encodedString = null;
        try {
            String response = s3Utility.getDataS3(quoteId);
            ObjectMapper mapper = new ObjectMapper();
            logger.info("soa response for QuoteID:{} {}", quoteId, response);
            if (response != null)
            {
                IllustrationPdfRequest illPdfRequest = new IllustrationPdfRequest();
                DeclarationData declarationData = new DeclarationData();
                declarationData.setAgentName(AppConstants.BLANK);
                illPdfRequest.setDeclarationData(declarationData);
                illPdfRequest.setProposalNo(ObjectUtils.isEmpty(PolicyNumber)?AppConstants.BLANK:PolicyNumber);
                Map<String, Object> responseFromQuote = new Gson().fromJson(response, HashMap.class);

                suppressProductCodeDecimalSSP(responseFromQuote);
                illPdfRequest.setIllustrationOutput(responseFromQuote);

                logger.info("Sending request for PDF creation service for QuoteId:{}:{}", quoteId, illPdfRequest);

                HttpEntity<Object> request = new HttpEntity<>(illPdfRequest);
                ResponseEntity<Object> result = new RestTemplate().postForEntity(lifeEngagePdfCreationUrl, request, Object.class);
                logger.info("respose for PDF creation service for QuoteId {}",result.getBody());

                if (result.getStatusCode().equals(HttpStatus.OK)) {
                    Map<String, Object> body = (Map<String, Object>) result.getBody();

                    if (Objects.nonNull(body)) {
                        if ("S".equalsIgnoreCase(body.get("status").toString())) {
                            encodedString = body.get("responseParam1").toString();
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("Exception Occured:{}",e.getMessage());
        }
        return encodedString;
    }

    private void suppressProductCodeDecimalSSP(Map<String, Object> responseFromQuote) {

        Map policyDetails = (Map)responseFromQuote.get("PolicyDetails");
        if(AppConstants.SSP_CODE_DOUBLE.equals(policyDetails.get("productCode"))) {
            policyDetails.put("productCode",AppConstants.SSP_PRODUCT_ID);
            responseFromQuote.put("PolicyDetails", policyDetails);
        }
    }


    public boolean documentListEligibility(ProposalDetails mergedProposalDetails) {
        try {
            logger.info("inside checkAutoSubmitConditions for policyNumber {} with proposalDetails {}",
                    mergedProposalDetails.getApplicationDetails().getPolicyNumber(), mergedProposalDetails);
            UnderwritingServiceDetails underwritingServiceDetails = mergedProposalDetails
                    .getUnderwritingServiceDetails();
            return underwritingServiceDetails != null
                    && AppConstants.POSV_BACKFLOW_MESSAGE
                    .equalsIgnoreCase(mergedProposalDetails.getApplicationDetails().getPosvJourneyStatus())
                    && Utility.isPaymentCompleted(mergedProposalDetails)
                    && Utility.isFinancialGridPassed(underwritingServiceDetails)
                    && Utility.isMedicalGridPassed(mergedProposalDetails)
                    && Utility.isMedicalSchedulingDone(mergedProposalDetails)
                    && Utility.isSellerDeclarationCompleted(mergedProposalDetails, sellerConsentDetailsRepository,
                    configurationRepository);
        } catch (Exception ex) {
            logger.error("error occured inside checkAutoSubmitConditions for policyNumber {} with message {}",
                    mergedProposalDetails.getApplicationDetails().getPolicyNumber(), Utility.getExceptionAsString(ex));
        }
        return false;
    }
    public Map<String,String> swissReDocReNaming(Map<String, String> updatedDocMap) {

        updatedDocMap.put("Form16_Pr", "Latest 1 years Form 16 with Employer Name");
        updatedDocMap.put("ITR_Pr", "Last 1 year ITR with computation of Income");
        updatedDocMap.put("BankS_Pr", "Salary Credited bank statement of last 6 months");

        return updatedDocMap;
    }
    private CurrentStatus findCurrentStatus(ProposalDetails proposalDetails,Stage stageObject){
        CurrentStatus currentStatus = new CurrentStatus();
        currentStatus.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber());
        currentStatus.setSystemType(AppConstants.ONE);
        currentStatus.setInfoType(AppConstants.ONE);
        currentStatus.setWiStatus(stageObject.getStageNumber());
        currentStatus.setWiSubStatus(stageObject.getStageName());
        currentStatus.setWiStatusDate(Utility.getCurrentDateInString());
        return currentStatus;
    }

    @Override
    public com.mli.mpro.productRestriction.models.RequestResponse getReunion(com.mli.mpro.productRestriction.models.RequestResponse inputRequest, MultiValueMap<String, String> headerMap) {
        ReunionInputRequest reunionInputRequest;
        String decryptedPayload;

        try {
            decryptedPayload = Utility.decryptRequest(inputRequest.getPayload());
            reunionInputRequest = objectMapper.readValue(decryptedPayload, ReunionInputRequest.class);
            logger.info("Reunion API Request Received:{}", reunionInputRequest);

            if (!isReunionRequestValid(reunionInputRequest)) {
                 return getErrorResponse(new MsgInfo(AppConstants.BAD_REQUEST_CODE, AppConstants.STATUS_FAILURE, AppConstants.BAD_REQUEST_MESSAGE), headerMap);
            }

            com.mli.mpro.productRestriction.models.OutputResponse productValidatorResponse = getProductValidatorResponse(inputRequest);
            logger.info("productValidatorResponse for reunion:{}", productValidatorResponse);

            if (Objects.nonNull(productValidatorResponse)
                    && Objects.nonNull(productValidatorResponse.getResponse().getResponseData())
                    && Objects.nonNull(productValidatorResponse.getResponse().getResponseData().getResponsePayload().isEligible())
                    && productValidatorResponse.getResponse().getResponseData().getResponsePayload().isEligible()) {
                return getDedupeResponse(decryptedPayload, headerMap);
            } else {
                return getproductErrorResponse(productValidatorResponse, headerMap);
            }


        } catch (JsonProcessingException|NullPointerException e) {
          logger.error("Error occured while processing the reunion request:{}", e.getMessage());
            return getErrorResponse(new MsgInfo(AppConstants.BAD_REQUEST_CODE, AppConstants.STATUS_FAILURE, AppConstants.BAD_REQUEST_TEXT), headerMap);
        } catch (UserHandledException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public RequestResponse getMedicalgridDetails(RequestResponse inputPayload) {
        com.mli.mpro.onboarding.medicalGridDetials.model.InputRequest inputRequest = new com.mli.mpro.onboarding.medicalGridDetials.model.InputRequest();
        RequestResponse requestResponse = new RequestResponse();
        com.mli.mpro.onboarding.medicalGridDetials.model.OutputResponse outputResponse = new com.mli.mpro.onboarding.medicalGridDetials.model.OutputResponse();
        com.mli.mpro.onboarding.medicalGridDetials.model.Response response = new com.mli.mpro.onboarding.medicalGridDetials.model.Response();
        com.mli.mpro.onboarding.medicalGridDetials.model.ResponsePayload responsePayload = new com.mli.mpro.onboarding.medicalGridDetials.model.ResponsePayload();
        MedicalGridDetails medicalGridDetails = new MedicalGridDetails();
        ProposalDetails proposalDetails = null;
        List<Object> errors = new ArrayList<>();
        try {
            String decryptedPayload = Util.decryptRequest(inputPayload.getPayload());
            inputRequest = deserializeMedicalGridCaseRequest(decryptedPayload);
            String policyNumber = inputRequest.getRequest().getPayload().getPolicyNumber();
            logger.info("Request of medical grid details request after dycryption for policy number {}, request {}", policyNumber, inputRequest);
            if (ObjectUtils.isEmpty(policyNumber)) {
                errors.add(AppConstants.NO_POLICY_EXIST);
                return Util.errorResponse(HttpStatus.BAD_REQUEST, errors, null, null);
            }
            if(!policyNumber.matches(AppConstants.POLICY_NO_REGEX)){
                errors.add(AppConstants.INVALID_POLICY_NO);
                return Util.errorResponse(HttpStatus.BAD_REQUEST, errors, null, null);
            }
            proposalDetails = repository.findByApplicationDetailsPolicyNumber(policyNumber);
            logger.info("input request for medical grid detials API {}", proposalDetails);
            if (Objects.isNull(proposalDetails)) {
                errors.add(AppConstants.NO_POLICY_EXIST);
                return Util.errorResponse(HttpStatus.BAD_REQUEST, errors, null, null);
            }
            if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) && Objects.isNull(proposalDetails.getUnderwritingServiceDetails().getMedicalGridDetails())) {
                errors.add("Medical grid details not generated");
                return Util.errorResponse(HttpStatus.BAD_REQUEST, errors, null, null);
            }
            logger.info("Before setting medical grid details object from proposal details for policy number {}", inputRequest.getRequest().getPayload().getPolicyNumber());
            medicalGridDetails = proposalDetails.getUnderwritingServiceDetails().getMedicalGridDetails();
            logger.info("medicalGridDetails for policy number {}, and details", inputRequest.getRequest().getPayload().getPolicyNumber(), medicalGridDetails);
            responsePayload.setMedicalGridDetails(medicalGridDetails);
            response.setPayload(responsePayload);
            response.setMsginfo(new MsgInfo(AppConstants.SUCCESS_RESPONSE, AppConstants.SUCCESS, AppConstants.DATA_FOUND));
            outputResponse.setResponse(response);
            requestResponse = Util.encryptDocumentResponse(outputResponse);
        } catch (Exception e) {
            logger.info("Exception occured while deserializing the medical grid details request", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return requestResponse;
    }

    private boolean isReunionRequestValid(ReunionInputRequest reunionInputRequest) {
        if (Objects.isNull(reunionInputRequest)
                || Objects.isNull(reunionInputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload())
                || Objects.isNull(reunionInputRequest.getDedupeAPIPayload())) {
            return false;
        } else {
            return true;
        }
    }

    private com.mli.mpro.productRestriction.models.RequestResponse getDedupeResponse(String decryptedPayload, MultiValueMap<String, String> headerMap) {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setPayload(decryptedPayload);
        requestResponse = medicalService.encrytedResponse(requestResponse, headerMap);

        if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_DEDUPE_API_EXPOSED))) {
            requestResponse= apiService.handleDedupeAPI(requestResponse, headerMap);
        } else {
            logger.info("Feature flag for Dedupe API is Disabled.");
            requestResponse=apiService.handleFeatureFlagDisable(headerMap);
        }
        return encryptReunionResponse(requestResponse, headerMap);

    }

    private com.mli.mpro.productRestriction.models.RequestResponse encryptReunionResponse(RequestResponse requestResponse, MultiValueMap<String, String> headerMap) {
       com.mli.mpro.productRestriction.models.RequestResponse responsePayload = new com.mli.mpro.productRestriction.models.RequestResponse();
        try {
           String response= apiServiceHandler.decrypt(requestResponse, headerMap);
           responsePayload=Utility.encryptRequest(response);
        } catch (GeneralSecurityException e) {
            logger.error("Error occured while encrypting reunion API Response");
        }
        return responsePayload;
    }

    private com.mli.mpro.productRestriction.models.RequestResponse getproductErrorResponse(com.mli.mpro.productRestriction.models.OutputResponse productValidatorResponse, MultiValueMap<String, String> headerMap) {
        MsgInfo msgInfo = new MsgInfo();

        if (Objects.nonNull(productValidatorResponse.getResponse().getResponseData())
                && Objects.nonNull(productValidatorResponse.getResponse().getResponseData().getResponsePayload().isEligible())
                && !productValidatorResponse.getResponse().getResponseData().getResponsePayload().isEligible()) {
            msgInfo.setMsgCode(AppConstants.BAD_REQUEST_CODE);
            msgInfo.setMsg(AppConstants.STATUS_FAILURE);
            msgInfo.setMsgDescription(AppConstants.PRODUCT_NOT_ELIGIBLE);
            msgInfo.setErrors(new ArrayList<Object>(productValidatorResponse.getResponse().getResponseData().getResponsePayload().getMessages()));


        } else if (Objects.nonNull(productValidatorResponse.getResponse().getErrorResponse())
                && Objects.nonNull(productValidatorResponse.getResponse().getErrorResponse().getErrors())) {
            msgInfo.setMsgCode(AppConstants.BAD_REQUEST_CODE);
            msgInfo.setMsg(AppConstants.STATUS_FAILURE);
            msgInfo.setMsgDescription(AppConstants.PRODUCT_VALIDATOR_PAYLOAD_INVALID);
            msgInfo.setErrors(productValidatorResponse.getResponse().getErrorResponse().getErrors());
        } else {
            msgInfo.setMsgCode(AppConstants.BAD_REQUEST_CODE);
            msgInfo.setMsg(AppConstants.STATUS_FAILURE);
            msgInfo.setMsgDescription(AppConstants.PRODUCT_VALIDATOR_BAD_REQUEST);
        }

        return getErrorResponse(msgInfo, headerMap);

    }

    private com.mli.mpro.productRestriction.models.RequestResponse getErrorResponse(MsgInfo msgInfo, MultiValueMap<String, String> headerMap) {
        RequestResponse responsePayload = new RequestResponse();
        try {
            responsePayload.setPayload(apiServiceHandler.setValidationErrorResponse(msgInfo));
            responsePayload.setPayload(apiServiceHandler.encryptResponse(responsePayload, headerMap));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
                 JsonProcessingException e) {
           logger.error("Error occured while setting the error response for reunion:{}", e);
        }

        return encryptReunionResponse(responsePayload, headerMap);
    }

    private com.mli.mpro.productRestriction.models.OutputResponse getProductValidatorResponse(com.mli.mpro.productRestriction.models.RequestResponse requestResponse) throws UserHandledException, JsonProcessingException {
        ErrorResponseParams errorResponseParams=new ErrorResponseParams();
        com.mli.mpro.productRestriction.models.RequestResponse productValidatorResponse = productRestrictionService.validateProduct(requestResponse,null,null);

        String decryptedPayload = Utility.decryptRequest(productValidatorResponse.getPayload());
        com.mli.mpro.productRestriction.models.OutputResponse outputResponse = null;
        try {
            outputResponse = objectMapper.readValue(decryptedPayload, com.mli.mpro.productRestriction.models.OutputResponse.class);
            logger.info("outputResponse for productValidator:{}", outputResponse);
        } catch (JsonProcessingException e) {
         logger.error("Error occured while deserializing the productValidatorResponse");
         return outputResponse;
        }

        return outputResponse;
    }
    private ProposalDetails getProposalDetails(InputRequest inputRequest){
        ProposalDetails proposalDetails = null;
        logger.info("inside policy status isDolphinPushed ");
        if(Objects.nonNull(inputRequest.getRequest())
                && Objects.nonNull(inputRequest.getRequest().getPayload())
                && Objects.nonNull(inputRequest.getRequest().getPayload().getPolicyNumber())
                && !inputRequest.getRequest().getPayload().getPolicyNumber().isEmpty()
                && StringUtils.hasLength(inputRequest.getRequest().getPayload().getPolicyNumber().get(0))){
            proposalDetails = repository.findByApplicationDetailsPolicyNumber(inputRequest.getRequest().getPayload().getPolicyNumber().get(0));
        }else if(Objects.nonNull(inputRequest.getRequest())
                && Objects.nonNull(inputRequest.getRequest().getPayload())
                && Objects.nonNull(inputRequest.getRequest().getPayload().getQuoteId())
                && StringUtils.hasLength(inputRequest.getRequest().getPayload().getQuoteId().get(0))){
            proposalDetails = repository.findByApplicationDetailsQuoteId(inputRequest.getRequest().getPayload().getQuoteId().get(0));
        }
        return proposalDetails;
    }
    private SoaCloudResponse<PolicyHistoryResponse> saralPolicyHistory(InputRequest inputRequest) throws UserHandledException {

        String policyNumber =inputRequest.getRequest().getPayload().getPolicyNumber().get(0);
        logger.info("Creating the request payload for Saral API for policyNumber {}", policyNumber);
        com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.Payload payload =
                new com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.Payload();
        payload.setPolicyNumber(policyNumber);
        com.mli.mpro.agent.models.Request request = new Request();
        request.setPayload(payload);
        com.mli.mpro.agent.models.RequestHeader header = new com.mli.mpro.agent.models.RequestHeader();
        header.setApplicationId(inputRequest.getRequest().getHeader().getAppId());
        header.setCorrelationId(inputRequest.getRequest().getHeader().getCorrelationId());
        request.setHeader(header);
        SoaApiRequest<PolicyHistoryRequest> policyHistoryRequest = new SoaApiRequest<>(request);
        String policyHistoryReq = Utility.getJsonRequest(policyHistoryRequest);
        logger.info("Policy history request payload inside the policy status {}",policyHistoryReq);
        SoaCloudResponse<PolicyHistoryResponse> soaCloudResponse;
        try {
            logger.info("Calling Policy History API inside policy status");
            soaCloudResponse = soaCloudService.callPolicyHistoryApi(policyHistoryRequest);
            List<String> errorMsgList = new ArrayList<>();
            if (soaCloudResponse == null || soaCloudResponse.getResponse() == null
                    || soaCloudResponse.getResponse().getPayload() == null) {
                String errorMsg = "Policy History API returned a null or incomplete response";
                logger.error(errorMsg);
                errorMsgList.add(errorMsg);
                throw new UserHandledException(errorMsgList,HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex) {
            String errorMsg ="currently policy history api is down, please try again later";
            logger.error(errorMsg, ex);
            throw new UserHandledException(Collections.singletonList(errorMsg), HttpStatus.BAD_GATEWAY);
        }
        return soaCloudResponse;
    }
    private boolean hasValidPolicyNoOrQuoteId(InputRequest inputRequest) {
        return (Objects.nonNull(inputRequest.getRequest())
                && ((Objects.nonNull(inputRequest.getRequest().getPayload().getPolicyNumber())
                && (inputRequest.getRequest().getPayload().getPolicyNumber().size() == 1))
                || (Objects.nonNull(inputRequest.getRequest().getPayload().getQuoteId())
                && (inputRequest.getRequest().getPayload().getQuoteId().size() == 1))));
    }
    private RequestResponse setResponsePayload(String encryptionSource, ErrorResponseParams errorResponseParams, OutputResponse outputResponse, RequestResponse requestResponse)
            throws JsonProcessingException, UserHandledException {
        logger.info("inside the policy status setResponsePayload");
        if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
            requestResponse.setPayload(EncryptionDecryptionUtil.encrypt(objectMapper.writeValueAsString(outputResponse), errorResponseParams.getIVandKey()[0], errorResponseParams.getIVandKey()[1].getBytes()));
        } else {
            requestResponse = Util.encryptResponse(outputResponse);
        }
        return requestResponse;
    }
    private OutputResponse setDolphinResponse(SoaCloudResponse<PolicyHistoryResponse> saralResponse, OutputResponse outputResponse, InputRequest inputRequest) {
        logger.info("Inside setDolphinResponse for policy status");
        Response response = new Response();
        if (isValidSaralResponse(saralResponse)) {
            Object payload = saralResponse.getResponse().getPayload();
            com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload  policyHistoryResponse;
            if (payload instanceof com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload) {
                policyHistoryResponse = (com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload ) payload;
            } else if (payload instanceof LinkedHashMap) {
                logger.info("Payload is of type LinkedHashMap, converting to PolicyHistoryResponse and payload is {}",payload);
                ObjectMapper mapper = new ObjectMapper();
                policyHistoryResponse = mapper.convertValue(payload, com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload .class);
                logger.info("policy history response inside the setDolphinResponse {}",policyHistoryResponse);
            } else {
                logger.error("Unexpected payload type: {}", payload.getClass().getName());
                com.mli.mpro.onboarding.model.MsgInfo msgInfo = new MsgInfo();
                msgInfo.setMsgDescription("Unexpected payload type in Policy history API response");
                response.setMsginfo(msgInfo);
                outputResponse.setPolicyStatusResponse(response);
                return outputResponse;
            }
            ResponsePayload responsePayload = new ResponsePayload();
            CurrentStatus currentStatus = new CurrentStatus();
            setPolicyMovementDetails(policyHistoryResponse, currentStatus);
            currentStatus.setDolphinResponse(policyHistoryResponse);
            currentStatus.setPolicyNumber(inputRequest.getRequest().getPayload().getPolicyNumber().get(0));
            responsePayload.setCurrentStatus(Collections.singletonList(currentStatus));
            com.mli.mpro.onboarding.model.MsgInfo msgInfo = new MsgInfo();
            com.mli.mpro.onboarding.model.Header header = new Header();
            msgInfo.setMsgDescription(saralResponse.getResponse().getMsgInfo().getMsgDescription());
            msgInfo.setMsgCode(saralResponse.getResponse().getMsgInfo().getMsgCode());
            response.setMsginfo(msgInfo);
            header.setCorrelationId(saralResponse.getResponse().getHeader().getSoaCorrelationId());
            header.setAppId(saralResponse.getResponse().getHeader().getSoaAppId());
            response.setHeader(header);
            response.setPayload(responsePayload);
        } else {
            response.setMsginfo(createErrorMsgInfo(saralResponse));
        }
        outputResponse.setPolicyStatusResponse(response);
        String outputRes = Utility.getJsonRequest(outputResponse);
        logger.info("OutputResponse inside the setDolphinResponse {}",outputRes);
        return outputResponse;
    }
    private boolean isValidSaralResponse(SoaCloudResponse<PolicyHistoryResponse> saralResponse) {
        return saralResponse != null
                && saralResponse.getResponse() != null
                && saralResponse.getResponse().getMsgInfo() != null
                && StringUtils.hasLength(saralResponse.getResponse().getMsgInfo().getMsgCode())
               && "200".equalsIgnoreCase(saralResponse.getResponse().getMsgInfo().getMsgCode());
    }
    private void setPolicyMovementDetails(
            com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload dolphinResponse,
            CurrentStatus currentStatus) {
        if (dolphinResponse != null && dolphinResponse.getPolicyMovementDetailsSeller() != null) {
            dolphinResponse.getPolicyMovementDetailsSeller().stream()
                    .reduce((first, second) -> second) // Get the last element
                    .ifPresentOrElse(
                            details -> {
                                currentStatus.setWiStatusDate(details.getDateTimeStamp());
                                currentStatus.setWiSubStatus(details.getSellerSubStatus());
                            },
                            () -> {
                                currentStatus.setWiStatusDate("");
                                currentStatus.setWiSubStatus("");
                            }
                    );
        } else {
            currentStatus.setWiStatusDate("");
            currentStatus.setWiSubStatus("");
        }
    }

    private MsgInfo createErrorMsgInfo(SoaCloudResponse<PolicyHistoryResponse> soaCloudResponse) {
        MsgInfo msgInfo = new MsgInfo();
        if(soaCloudResponse != null && soaCloudResponse.getResponse() != null){
            msgInfo.setMsgDescription(soaCloudResponse.getResponse().getMsgInfo().getMsgDescription());
            msgInfo.setMsgCode(soaCloudResponse.getResponse().getMsgInfo().getMsgCode());
        }
        return msgInfo;
    }
    private boolean isGoCodeFeatureFlagEnabled(String goCABrokerCode) {
        logger.info("Insdide isGoCodeFeatureFlagEnabled ");
        if (Objects.isNull(goCABrokerCode) || goCABrokerCode.equalsIgnoreCase(AppConstants.BLANK)) {
            return false;
        }
        goCABrokerCode = goCABrokerCode.substring(0,2);
        logger.info("Go code for policy status {}",goCABrokerCode);
        List<Configuration> configuration = configurationRepository.findByKeyIgnoreCase(POLICYSTATUS_CHANNELLIST);
        logger.info("configurationRepository policy status {}",configuration);
        if (!CollectionUtils.isEmpty(configuration)) {
            List<String> channelList = configuration.get(0).getPolicyStatusChannelList();
            logger.info("Channel list for policy status  {}",channelList);
            return (channelList != null && channelList.stream().anyMatch(goCABrokerCode::equalsIgnoreCase));
        }
        return false;
    }
    private Boolean isDolphinPushed(InputRequest inputRequest,ProposalDetails proposalDetails){
        if(proposalDetails != null && proposalDetails.getApplicationDetails().getStage().matches(POLICY_STAGE_CHECK_REGEX)
                && PUSHED_TO_TPP.equalsIgnoreCase(proposalDetails.getApplicationDetails().getPolicyProcessingJourneyStatus())){
            List<String> policyNumber = new ArrayList<>();
            policyNumber.add(proposalDetails.getApplicationDetails().getPolicyNumber());
            inputRequest.getRequest().getPayload().setPolicyNumber(policyNumber);
            logger.info("isDolphinPushed policy status : true");
            return true;
        }
        logger.info("isDolphinPushed policy status : false");
        return false;
    }

}




