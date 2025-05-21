package com.mli.mpro.location.services.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.unifiedPayment.models.*;
import com.mli.mpro.location.services.UnifiedPaymentService;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ENACHDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.Receipt;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnifiedPaymentServiceImpl implements UnifiedPaymentService {

    Logger logger = LoggerFactory.getLogger(UnifiedPaymentServiceImpl.class);

    @Value("${unifiedPayment.generateTokenUrl}")
    private String generateTokenUrl;
    @Value("${unifiedPayment.paymentUrl}")
    private String unifiedPaymentUrl;
    @Value("${unifiedPayment.paymentEnv}")
    private String unifiedPaymentEnv;
    @Value("${unifiedPayment.paymentHistoryUrl}")
    private String unifiedPaymentHistoryUrl;
    @Value("${unifiedPayment.xapigw}")
    private String xapigw;
    @Value("${unifiedPayment.xapikey}")
    private String xapikey;
    @Value("${unifiedPayment.generateTokenUrlUat}")
    private String tokenUrlUat;
    @Value("${unifiedPayment.paymentUrlUat}")
    private String paymentUrlUat;
    @Value("${unifiedPayment.paymentEnvUat}")
    private String paymentEnvUat;
    @Value("${unifiedPayment.paymentHistoryUrlUat}")
    private String paymentHistoryUrlUat;

    private String tokenUrl="";
    private String paymentUrl="";
    private String paymentEnvironment="";
    private String paymentHistoryUrl="";

    private RestTemplate restTemplate;

    ProposalRepository proposalRepository;

    MongoOperations mongoOperation;
    @Autowired
    public UnifiedPaymentServiceImpl(RestTemplate restTemplate, ProposalRepository proposalRepository, MongoOperations mongoOperation) {
        this.restTemplate = restTemplate;
        this.proposalRepository = proposalRepository;
        this.mongoOperation = mongoOperation;
    }

    @Override
    public UIPaymentRequestResponse unifiedPayment(UIPaymentRequestResponse request) throws UserHandledException {
        logger.info("Entering unifiedPayment method for transactionId {}", request.getPayload().getTransactionId());
        String redirectUrl="";
        try {
            validateUIRequest(request);
            updateUrlDetails();
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.UNIFIED_PAYMENT_ENABLE)) {
                ProposalDetails proposalDetails = findProposalDetails(request);
                String token = generateToken(proposalDetails, request);
                redirectUrl = generateUnifiedPaymentUrl(token);
            }
        } catch (UserHandledException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Exception while processing UI payment request for transactionId {}, {}", request.getPayload().getTransactionId(), Utility.getExceptionAsString(ex));
            throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UIPaymentRequestResponse response = new UIPaymentRequestResponse();
        response.setPayload(request.getPayload());
        response.setMetadata(request.getMetadata());
        response.getPayload().setRedirectUrl(redirectUrl);
        return response;
    }

    private void updateUrlDetails() {
        boolean isUnifiedPaymentEnabled = FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.UNIFIED_PAYMENT_URL_CONFIG);
        tokenUrl = isUnifiedPaymentEnabled ? generateTokenUrl : tokenUrlUat;
        paymentUrl = isUnifiedPaymentEnabled ? unifiedPaymentUrl : paymentUrlUat;
        paymentEnvironment = isUnifiedPaymentEnabled ? unifiedPaymentEnv : paymentEnvUat;
        paymentHistoryUrl = isUnifiedPaymentEnabled ? unifiedPaymentHistoryUrl : paymentHistoryUrlUat;
    }


    private ProposalDetails findProposalDetails(UIPaymentRequestResponse request) throws UserHandledException {
        ProposalDetails proposalDetails = proposalRepository.findBySourcingDetailsAgentIdAndTransactionId(request.getPayload().getAgentId(), request.getPayload().getTransactionId());
        if (proposalDetails == null) {
            throw new UserHandledException(List.of(AppConstants.POLICY_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return proposalDetails;
    }

    private String generateUnifiedPaymentUrl(String token) throws UserHandledException, UnsupportedEncodingException {
        if (StringUtils.isEmpty(paymentUrl) || StringUtils.isEmpty(paymentEnvironment)) {
            throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return paymentUrl + URLEncoder.encode(token, "UTF-8") + AppConstants.ENV_PARAM_NAME + paymentEnvironment;
    }


    private void validateUIRequest(UIPaymentRequestResponse request) throws UserHandledException {
        if (request == null || request.getPayload() == null || request.getPayload().getTransactionId() == 0 || StringUtils.isEmpty(request.getPayload().getAgentId())) {
            logger.error("Bad request received for UI payment request {}", request);
            throw new UserHandledException(List.of(AppConstants.BAD_REQUEST_MESSAGE), HttpStatus.BAD_REQUEST);
        }
        logger.info("Validated UI payment request for transactionId {}", request.getPayload().getTransactionId());
    }

    @Override
    public Object unifiedPaymentStatusUpdate(UnifiedWebHookRequest unifiedWebHookRequest) {
        try {
            if (unifiedWebHookRequest == null || StringUtils.isEmpty(unifiedWebHookRequest.getPolicyNo())) {
                throw new UserHandledException(List.of("Invalid request: unifiedWebHookRequest or policy number is null"), HttpStatus.BAD_REQUEST);
            }
            logger.info("Received payment status update request for policyNo {} and request {}", unifiedWebHookRequest.getPolicyNo(), unifiedWebHookRequest);
            ProposalDetails proposalDetails = proposalRepository.findByApplicationDetailsPolicyNumber(unifiedWebHookRequest.getPolicyNo());
            if (proposalDetails == null ) {
                throw new UserHandledException(List.of(AppConstants.POLICY_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
            mapUnifiedRequest(proposalDetails,unifiedWebHookRequest);
            updatePaymentDetails(proposalDetails);
        } catch (Exception e) {
            logger.error("Error while updating payment status for policyNo {} and request {}", unifiedWebHookRequest.getPolicyNo(), unifiedWebHookRequest);
            return new WebHookResponse(AppConstants.INTERNAL_SERVER_ERROR_CODE, AppConstants.INTERNAL_SERVER_ERROR);
        }

        return new WebHookResponse(AppConstants.SUCCESS_RESPONSE, AppConstants.SUCCESS);
    }

    @Override
    public UIPaymentRequestResponse checkUnifiedPaymentStatus(UIPaymentRequestResponse request) throws UserHandledException {
        try {
            validateUIRequest(request);
            updateUrlDetails();
            ProposalDetails proposalDetails = proposalRepository.findBySourcingDetailsAgentIdAndTransactionId(request.getPayload().getAgentId(), request.getPayload().getTransactionId());
            if (proposalDetails == null) {
                throw new UserHandledException(List.of("Policy not found"), HttpStatus.NOT_FOUND);
            }
            if(AppConstants.TXN_PENDING.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getUnifiedPaymentStatus()) || AppConstants.TXN_PENDING.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getUnifiedRenewalStatus())){
                logger.info("Payment or Renewal not done for transactionId {}", request.getPayload().getTransactionId());
                List<UnifiedWebHookRequest>  unifiedWebHookData = validateUnifiedPaymentStatus(proposalDetails);
                mapUnifiedDetails(proposalDetails, unifiedWebHookData);
                updatePaymentDetails(proposalDetails);
                request.setUnifiedWebHookRequest(unifiedWebHookData);
                return request;
            }else{
                updateRequestData(proposalDetails,request);
            }
        } catch (Exception ex) {
            logger.error("Exception while processing UI payment request {}", Utility.getExceptionAsString(ex));
            throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return request;
    }

    private void updateRequestData(ProposalDetails proposalDetails, UIPaymentRequestResponse request) {
        List<UnifiedWebHookRequest> unifiedWebHookData = new ArrayList<>();
        UnifiedWebHookRequest unifiedWebHookRequest = new UnifiedWebHookRequest();
        unifiedWebHookRequest.setPolicyNo(proposalDetails.getApplicationDetails().getPolicyNumber());
        unifiedWebHookRequest.setTxnStatusDesc(proposalDetails.getAdditionalFlags().getUnifiedPaymentStatus());
        unifiedWebHookData.add(unifiedWebHookRequest);
        request.setUnifiedWebHookRequest(unifiedWebHookData);
    }

    private void mapUnifiedDetails(ProposalDetails proposalDetails, List<UnifiedWebHookRequest> data) throws UserHandledException {
        for(UnifiedWebHookRequest response : data){
            mapUnifiedRequest(proposalDetails,response);
        }
    }


    private List<UnifiedWebHookRequest> validateUnifiedPaymentStatus(ProposalDetails proposalDetails) throws UserHandledException {
        try {
            TransactionHistoryRequest transactionHistoryRequest=createPaymentHistoryRequest(proposalDetails);
            HttpHeaders headers = createHeaders();
            HttpEntity<TransactionHistoryRequest> request = new HttpEntity<>(transactionHistoryRequest, headers);
            logger.info("Request for Unified payment history API for transactionId {},{}",proposalDetails.getTransactionId(), request);
            ResponseEntity<TransactionHistoryResponse> response = restTemplate.postForEntity(paymentHistoryUrl, request, TransactionHistoryResponse.class);
            logger.info("Response for Unified payment history API for transactionId {},{}",proposalDetails.getTransactionId(), response);
            if (response.getBody() != null && response.getBody().getTransactionHistoryPayload() != null && response.getBody().getTransactionHistoryPayload().getTransactionDetailsList() != null && !response.getBody().getTransactionHistoryPayload().getTransactionDetailsList().isEmpty()) {
                List<UnifiedWebHookRequest> transactions = response.getBody().getTransactionHistoryPayload().getTransactionDetailsList();
                List<UnifiedWebHookRequest> transaction = transactions.stream()
                        .filter(x -> AppConstants.TXN_SUCCESS.equalsIgnoreCase(x.getTxnStatusDesc())).collect(Collectors.toList());
                 if(!transaction.isEmpty()){
                     return transaction;
                 }
                 return List.of(transactions.get(0));
            }
        }catch (Exception e){
            logger.error("Exception while processing payment History request {}", Utility.getExceptionAsString(e));
            throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Collections.emptyList();
    }

    private TransactionHistoryRequest createPaymentHistoryRequest(ProposalDetails proposalDetails) {
        List<String> list = List.of(AppConstants.MPRO.toUpperCase());
        return new TransactionHistoryRequest(proposalDetails.getApplicationDetails().getPolicyNumber(),list);
    }

    private void updatePaymentDetails(ProposalDetails proposalDetails) throws UserHandledException {
        try {
            Query proposalQuery = new Query();
            Update proposalUpdate = new Update();
            proposalQuery.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(proposalDetails.getTransactionId()));
            proposalUpdate.set("bank.paymentRenewedBy", proposalDetails.getBank().getPaymentRenewedBy());
            proposalUpdate.set("bank.enachDetails", proposalDetails.getBank().getEnachDetails());
            proposalUpdate.set("paymentDetails.receipt", proposalDetails.getPaymentDetails().getReceipt());
            proposalUpdate.set("additionalFlags.isPaymentDone",proposalDetails.getAdditionalFlags().isPaymentDone());
            proposalUpdate.set("additionalFlags.paymentGateway",AppConstants.PAYMENT_TYPE);
            proposalUpdate.set("additionalFlags.isRenewelPaymentDone", proposalDetails.getAdditionalFlags().isRenewelPaymentDone());
            proposalUpdate.set("additionalFlags.mandateAmountPercentage", calculateSiAmountPercentage(proposalDetails));
            proposalUpdate.set("additionalFlags.unifiedRenewalStatus", proposalDetails.getAdditionalFlags().getUnifiedRenewalStatus());
            proposalUpdate.set("additionalFlags.unifiedPaymentStatus", proposalDetails.getAdditionalFlags().getUnifiedPaymentStatus());

            mongoOperation.findAndModify(proposalQuery, proposalUpdate, ProposalDetails.class);
        } catch (Exception ex) {
            logger.error("Error while updating payment details for transactionId {} exception {}", proposalDetails.getTransactionId(), Utility.getExceptionAsString(ex));
            throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateToken(ProposalDetails proposalDetails, UIPaymentRequestResponse uiRequest) throws UserHandledException {
        int maxRetries = 3;
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                logger.info("calling token generation unified API for transactionId {}", proposalDetails.getTransactionId());
                UnifiedPaymentRequest unifiedPaymentRequest = generatePaymentDetails(proposalDetails, uiRequest);
                HttpHeaders headers = createHeaders();
                HttpEntity<UnifiedPaymentRequest> request = new HttpEntity<>(unifiedPaymentRequest, headers);
                logger.info("Request for Unified token generation API for transactionId {},{}",proposalDetails.getTransactionId(), request);
                ResponseEntity<ResponseModel> response = callTokenGenerationApi(request, proposalDetails.getTransactionId());
                if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null && response.getBody().getPayload() != null && !StringUtils.isEmpty(response.getBody().getPayload().getToken())) {
                    return response.getBody().getPayload().getToken();
                } else {
                    logger.error("Failure response received from unified payment token API for transactionId {}", proposalDetails.getTransactionId());
                    throw new UserHandledException(List.of("Unified Payment service not responding"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (UserHandledException ex) {
                throw ex;
            } catch (Exception e) {
                logger.error("Error while generating unified payment token for transactionId {} on attempt {}: {}", proposalDetails.getTransactionId(), attempt + 1, Utility.getExceptionAsString(e));
                if (attempt >= maxRetries - 1) {
                    throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        throw new UserHandledException(List.of(AppConstants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (AppConstants.ENV_UAT.equalsIgnoreCase(System.getenv(AppConstants.SYSTEM_ENV)) || AppConstants.ENV_PROD.equalsIgnoreCase(System.getenv(AppConstants.SYSTEM_ENV))) {
            headers.add(AppConstants.X_APIGW_API_ID, xapigw);
            headers.add(AppConstants.X_API_KEY, xapikey);
        }
        headers.add("uiCorrelationId", UUID.randomUUID().toString());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    private ResponseEntity<ResponseModel> callTokenGenerationApi(HttpEntity<UnifiedPaymentRequest> request, long transactionId) {
        ResponseEntity<ResponseModel> response = restTemplate.postForEntity(tokenUrl, request, ResponseModel.class);
        logger.info("Token generation API response for transactionId {} {}", transactionId, response);
        return response;
    }

    private UnifiedPaymentRequest generatePaymentDetails(ProposalDetails proposalDetails, UIPaymentRequestResponse uiRequest) {
        String amount = proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid();
        String siAmountPercentage = calculateSiAmountPercentage(proposalDetails);
        String siEndDate = calculateSiEndDate(proposalDetails);
        String sourceChannel = getSourceChannel(proposalDetails);
        UnifiedPaymentRequest unifiedPaymentRequest = new UnifiedPaymentRequest();
        unifiedPaymentRequest.setCustomerId(generateCustomerId(proposalDetails.getApplicationDetails().getPolicyNumber()));
        unifiedPaymentRequest.setTxnAmt(amount);
        unifiedPaymentRequest.setPolicyNo(proposalDetails.getApplicationDetails().getPolicyNumber());
        unifiedPaymentRequest.setJourney(AppConstants.MPRO.toUpperCase());
        if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.UNIFIED_PAYMENT_URL_CONFIG)){
            unifiedPaymentRequest.setJourneyEnv(AppConstants.ENV_QA.toUpperCase());
        }else{
            unifiedPaymentRequest.setJourneyEnv(AppConstants.UNIFIED_PAYMENT_ENV.get(System.getenv("env")));
        }
        unifiedPaymentRequest.setPaymentReason(AppConstants.INITIAL.toUpperCase());
        unifiedPaymentRequest.setPremiumDueAmount(amount);
        unifiedPaymentRequest.setSiMaxAmountIncreasePercentage(siAmountPercentage);
        unifiedPaymentRequest.setSiEndDate(siEndDate);
        unifiedPaymentRequest.setCallBackUrl(uiRequest.getPayload().getRedirectUrl());
        unifiedPaymentRequest.setAdditionalInfo(sourceChannel + "," + proposalDetails.getTransactionId() + "," + proposalDetails.getSourcingDetails().getAgentId());

        if (AppConstants.RENEWAL.equalsIgnoreCase(uiRequest.getPayload().getPaymentType())) {
            unifiedPaymentRequest.setProfile("CARD_NB_PAY_LATER");
            String siAmount=calculateRenewalAmount(proposalDetails,amount);
            unifiedPaymentRequest.setTxnAmt(siAmount);
            unifiedPaymentRequest.setPremiumDueAmount(siAmount);
        }

        return unifiedPaymentRequest;
    }

    /* In the Case of Mode of payment Monthly updating one month premium in Renewal Payment*/
    private String calculateRenewalAmount(ProposalDetails proposalDetails, String amount) {
        if(isMonthlyRenewalCase(proposalDetails) && !isYblChannel(proposalDetails)){
           return String.valueOf(AppConstants.RENEWAL_PAYMENT_MONTHLY*Double.parseDouble(amount));
        }
        return amount;
    }

    private String getSourceChannel(ProposalDetails proposalDetails) {
        return Objects.nonNull(proposalDetails.getAdditionalFlags().getSourceChannel())
                ? proposalDetails.getAdditionalFlags().getSourceChannel()
                : AppConstants.MPRO.toUpperCase();
    }

    private String calculateSiEndDate(ProposalDetails proposalDetails) {
        String premiumPaymentTermStr = proposalDetails.getProductDetails().get(0).getProductInfo().getPremiumPaymentTerm();
        int premiumPaymentTerm = Integer.parseInt(premiumPaymentTermStr);
        LocalDate endDate = LocalDate.now().plusYears(premiumPaymentTerm);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return endDate.format(formatter);
    }

    private String calculateSiAmountPercentage(ProposalDetails proposalDetails) {
        if (isYblChannel(proposalDetails)) {
            return "0";
        }
        return "10";
    }

    private boolean isMonthlyRenewalCase(ProposalDetails proposalDetails) {
        return AppConstants.MONTHLY.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModeOfPayment());
    }

    private boolean isYblChannel(ProposalDetails proposalDetails) {
        return AppConstants.CHANNEL_YBL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
    }

    public void mapUnifiedRequest(ProposalDetails proposalDetails,UnifiedWebHookRequest unifiedWebHookRequest) throws UserHandledException {
        String paymentType=determinePaymentType(unifiedWebHookRequest);
        if (AppConstants.INITIAL.equalsIgnoreCase(paymentType)) {
            updateInitialPaymentDetails(proposalDetails, unifiedWebHookRequest);
        }
        if (AppConstants.RENEWAL.equalsIgnoreCase(paymentType)) {
            updateRenewalPaymentDetails(proposalDetails,unifiedWebHookRequest);
        }
        if (AppConstants.INITIAL_AND_RENEWAL.equalsIgnoreCase(paymentType)) {
            updateInitialPaymentDetails(proposalDetails, unifiedWebHookRequest);
            updateRenewalPaymentDetails(proposalDetails, unifiedWebHookRequest);
        }
    }

    private void updateRenewalPaymentDetails(ProposalDetails proposalDetails, UnifiedWebHookRequest unifiedWebHookRequest) {
        updateENACHDetails(proposalDetails, unifiedWebHookRequest);
        proposalDetails.getAdditionalFlags().setRenewelPaymentDone(AppConstants.TXN_SUCCESS.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc()));
        proposalDetails.getAdditionalFlags().setUnifiedRenewalStatus(unifiedWebHookRequest.getTxnStatusDesc());
    }

    private void updateENACHDetails(ProposalDetails proposalDetails, UnifiedWebHookRequest unifiedWebHookRequest) {
        ENACHDetails enachDetails = new ENACHDetails();
        enachDetails.setNPCICode(AppConstants.NPCI_CODE);
        enachDetails.setUtilityCode(AppConstants.UTILITY_CODE);
        enachDetails.setServiceProviderName(AppConstants.SERVICE_PROVIDER_NAME);
        enachDetails.setRecurringFrequency(AppConstants.RECURRING_FREQUENCY);
        enachDetails.setMandateAmount(calculateMandateAmount(proposalDetails));
        enachDetails.setFirstCollectionDate(dateFormater(unifiedWebHookRequest.getPaymentDate()));
        enachDetails.setFinalCollectionDate(dateFormater(unifiedWebHookRequest.getPaymentDate()));
        enachDetails.setEnachStatus(AppConstants.TXN_SUCCESS.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc()) ? AppConstants.SUCCESS : AppConstants.FAIL_STATUS.toUpperCase());
        enachDetails.setMaxLifeRefNo(org.springframework.util.StringUtils.hasText(unifiedWebHookRequest.getCustomerId())?unifiedWebHookRequest.getCustomerId():"");
        enachDetails.setAmountType(AppConstants.AMOUNT_TYPE);
        enachDetails.setIngenicoMandateId(org.springframework.util.StringUtils.hasText(unifiedWebHookRequest.getMandateId())?unifiedWebHookRequest.getMandateId():"");
        enachDetails.setIngenicoTransactionId(org.springframework.util.StringUtils.hasText(unifiedWebHookRequest.getBpRefNo())?unifiedWebHookRequest.getBpRefNo():"");
        enachDetails.setIngenicoBankCode("");
        enachDetails.setSponsorBank(AppConstants.SPONSOR_BANK);
        enachDetails.setSponsorBankCode(AppConstants.SPONSOR_BANK_CODE);
        enachDetails.setNPCICategoryName(AppConstants.NPCI_CATEGORY_NAME);
        proposalDetails.getBank().setEnachDetails(enachDetails);
    }

    private Date dateFormater(String paymentDate) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN);
           return formatter.parse(paymentDate);
        }catch (Exception ex){
            logger.error("Error while parsing date for Unified paymentDate {} and exception {}", paymentDate, Utility.getExceptionAsString(ex));
        }
        return new Date();
    }

    private String calculateMandateAmount(ProposalDetails proposalDetails) {
        String renewalAmount=proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid();
        double amount = Double.parseDouble(renewalAmount);
        if(isMonthlyRenewalCase(proposalDetails) && !isYblChannel(proposalDetails)){
            amount = AppConstants.RENEWAL_PAYMENT_MONTHLY*amount;
        }
        if(!isYblChannel(proposalDetails)){
            amount=AppConstants.RENEWAL_PERCENTAGE*amount;
        }
        return String.valueOf(amount);
    }

    private void updateInitialPaymentDetails(ProposalDetails proposalDetails, UnifiedWebHookRequest unifiedWebHookRequest) throws UserHandledException {
        Receipt receipt = proposalDetails.getPaymentDetails().getReceipt().get(0);
        setReceiptDetails(receipt, proposalDetails, unifiedWebHookRequest);
        proposalDetails.getPaymentDetails().getReceipt().set(0, receipt);
        proposalDetails.getAdditionalFlags().setPaymentDone(AppConstants.TXN_SUCCESS.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc()));
        proposalDetails.getAdditionalFlags().setUnifiedPaymentStatus(unifiedWebHookRequest.getTxnStatusDesc());
    }

    private void setReceiptDetails(Receipt receipt, ProposalDetails proposalDetails, UnifiedWebHookRequest unifiedWebHookRequest) throws UserHandledException {
        try {
            receipt.setPaymentType(AppConstants.PAYMENT_TYPE);
            receipt.setPremiumMode(AppConstants.ONLINE);
            receipt.setPaymentReferenceCode(AppConstants.BLANK);
            receipt.setChannelName(proposalDetails.getChannelDetails().getChannel());
            receipt.setCurrency(AppConstants.CURRENCY);
            receipt.setApplicationName(AppConstants.MPRO.toUpperCase());
            receipt.setProposalNumber(unifiedWebHookRequest.getPolicyNo());
            receipt.setTransactionReferenceNumber(unifiedWebHookRequest.getTransNo());
            receipt.setTransactionDateTimeStamp(unifiedWebHookRequest.getPaymentDate());
            receipt.setPaymentDate(unifiedWebHookRequest.getPaymentDate());
            receipt.setBankMerchantId(unifiedWebHookRequest.getMerchTxn());
            receipt.setBankId(unifiedWebHookRequest.getBankName());
            receipt.setBusinessType(unifiedWebHookRequest.getPaymentMode());
            receipt.setBankReferenceNumber(unifiedWebHookRequest.getBpRefNo());
            receipt.setIntrumentOfPayment(unifiedWebHookRequest.getInstrNo());
            receipt.setBilldeskAuthStatus(getAuthStatus(unifiedWebHookRequest));
            receipt.setBillDeskPaymentStatus(updatePaymentStatus(unifiedWebHookRequest));
            receipt.setBillDeskCustomerId(unifiedWebHookRequest.getCustomerId());
            receipt.setSICheck(false);
            receipt.setModeOfPayment(proposalDetails.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModeOfPayment());
            receipt.setAmount(unifiedWebHookRequest.getAmountPaid());
        }catch (Exception ex){
            logger.error("Error while setting receipt details for transactionId {} and exception {}", proposalDetails.getTransactionId(), Utility.getExceptionAsString(ex));
            throw new UserHandledException(List.of(AppConstants.BAD_REQUEST_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    private String updatePaymentStatus(UnifiedWebHookRequest unifiedWebHookRequest) {
        if(AppConstants.TXN_SUCCESS.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc())){
            return AppConstants.UNIFIED_PAYMENT_SUBMITTED;
        }else if (AppConstants.TXN_PENDING.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc())){
            return AppConstants.TXN_PENDING.toLowerCase();
        }else {
            return  AppConstants.STATUS_FAILURE.toLowerCase();
        }
    }

    private String getAuthStatus(UnifiedWebHookRequest unifiedWebHookRequest) {
        if(AppConstants.TXN_SUCCESS.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc())){
            return AppConstants.UNIFIED_SUCCESS_STATUS_AUTH_CODE;
        }else if (AppConstants.TXN_PENDING.equalsIgnoreCase(unifiedWebHookRequest.getTxnStatusDesc())){
            return AppConstants.UNIFIED_PENDING_STATUS_AUTH_CODE_0002;
        }else {
            return  AppConstants.UNIFIED_FAILED_STATUS_AUTH_CODE_0399;
        }
    }

    public String determinePaymentType(UnifiedWebHookRequest unifiedWebHookRequest) throws UserHandledException {
        if(!org.springframework.util.StringUtils.hasText(unifiedWebHookRequest.getPaymentMode()) && !org.springframework.util.StringUtils.hasText(unifiedWebHookRequest.getAmountPaid()) ){
            logger.error("Unified Webhook Request Data is missing {}", unifiedWebHookRequest);
            throw new UserHandledException(List.of(AppConstants.BAD_REQUEST_MESSAGE), HttpStatus.BAD_REQUEST);
        }
        String paymentType = unifiedWebHookRequest.getPaymentMode();
        double amount = Double.parseDouble(unifiedWebHookRequest.getAmountPaid());
        if (isSiPayment(paymentType)) {
            return (amount == 0) ? AppConstants.RENEWAL : AppConstants.INITIAL_AND_RENEWAL;
        } else if (isNbPayment(paymentType, amount)) {
            return AppConstants.RENEWAL;
        }
        return AppConstants.INITIAL;
    }

    private boolean isSiPayment(String paymentType) {
        return paymentType != null && paymentType.contains("SI");
    }

    private boolean isNbPayment(String paymentType, double amount) {
        return paymentType != null && paymentType.contains("NB") && amount == 0;
    }

    public static String generateCustomerId(String policyNumber) {
        Instant now = Instant.now();
        String timestamp = String.valueOf(now.toEpochMilli());
        return policyNumber  + timestamp;
    }
}
