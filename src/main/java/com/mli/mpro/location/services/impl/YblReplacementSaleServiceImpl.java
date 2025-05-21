package com.mli.mpro.location.services.impl;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.models.NewReplacementSalePayload;
import com.mli.mpro.common.models.YblPolicy;
import com.mli.mpro.common.models.YblReplacementSaleRequestPayload;
import com.mli.mpro.common.models.YblReplacementSaleResponse;
import com.mli.mpro.config.RestTemplateClient;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.services.ProposalStreamPushService;
import com.mli.mpro.location.services.YblReplacementSaleService;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@EnableAsync
public class YblReplacementSaleServiceImpl implements YblReplacementSaleService {

    private static final Logger logger = LoggerFactory.getLogger(YblReplacementSaleRequestPayload.class);
    @Value("${urlDetails.yblReplacementSaleUrl}")
    private String yblReplacementSaleAPI;
    @Value("${yblnis.apikey}")
    private String yblnisApiKey;
    @Value("${ybl.nis.secret.key}")
    private String secretKey;

    @Value("${yblnis.X-IBM-Client-Id}")
    private String yblnisClientId;
    @Value("${yblnis.X-IBM-Client-Secret}")
    private String yblnisClientSecret;

    @Value("#{${urlDetails.yblReplacementSaleStatusMap}}")
    private Map<String, List<String>> yblReplacementSaleStatusMap;
    @Autowired
    RestTemplateClient restTemplateClient;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalStreamPushService proposalStreamPushService;

    @Autowired
    private AuditService auditService;

    @Async
    @Override
    public void callYblReplacementSale(YblReplacementSaleRequestPayload yblReplacementSaleRequestPayload) throws Exception {
        // Checking Ybl Replacement Conditions
        if (checkRSConditions(yblReplacementSaleRequestPayload) && !checkYblReplacementSaleTagExist(yblReplacementSaleRequestPayload.getTransactionId())) {
            int retryCount = RetrySynchronizationManager.getContext().getRetryCount();
            YblReplacementSaleResponse response = new YblReplacementSaleResponse();
            NewReplacementSalePayload replacementSalePayload = new NewReplacementSalePayload();
            // Setting default values of Replacement Sale Tags
            replacementSalePayload.setYblReplacementPolicyStatus(AppConstants.BLANK);
            replacementSalePayload.setReplacementSale(AppConstants.N);
            String transactionId = !StringUtils.isEmpty(yblReplacementSaleRequestPayload.getTransactionId()) ? yblReplacementSaleRequestPayload.getTransactionId() : AppConstants.BLANK;
            try {
                logger.info("Ybl Replacement Api Request is {} for transactionId {}", yblReplacementSaleRequestPayload, transactionId);
                HttpHeaders header = setHeaders();
                // Encrypt the request
                String encryptedRequest = getEncryptedRequest(yblReplacementSaleRequestPayload, transactionId);
                HttpEntity<String> httpEntity = new HttpEntity<>(encryptedRequest, header);
                logger.info("Http Entity is {} for transactionId {}", httpEntity, transactionId);
                RestTemplate restTemplate = restTemplateClient.restTemplateClientHttpRequestFactory();
                logger.info("Calling url for ybl replacement sale api {} for transactionId {}", yblReplacementSaleAPI, transactionId);
                // call Ybl Replacement Sale Api and receive encoded response
                String encResponse = restTemplate.exchange(yblReplacementSaleAPI, HttpMethod.POST, httpEntity, String.class).getBody();
                response = getYblReplacementReponse(response, encResponse, transactionId);
                logger.info("Ybl Replacement Api Response is {} for transactionId {}", response, transactionId);
                if (null != response) {
                    // Set flags for ybl replacement Sale
                    setYblReplacementTags(response.getData(), replacementSalePayload, transactionId);
                    // Save request and response in auditing DB.
                    saveRequestResponseInAuditDB(yblReplacementSaleRequestPayload, transactionId, response);
                    // Save data for proposalStream push
                    callSaveProposalForYblReplacementSale(replacementSalePayload, transactionId);
                    logger.info("ReplacementSalePayload is {} for transactionId {}", replacementSalePayload, transactionId);
                }
            } catch (Exception ex) {
                logger.error("Getting exception {} while calling YBL Replacement Sale Api for transactionId {}",Utility.getExceptionAsString(ex),transactionId);
                // As per EA review comment logging this maximum 3 times exception occured in auditing DB
                saveRequestResponseInAuditDB(yblReplacementSaleRequestPayload, transactionId, response);
                if (retryCount == 2) {
                    callSaveProposalForYblReplacementSale(replacementSalePayload, transactionId);
                }
                throw ex;
            }
        }
    }

    private YblReplacementSaleResponse getYblReplacementReponse(YblReplacementSaleResponse response, String encResponse, String transactionId) {
        try {
            String jsonData = (EncryptionDecryptionUtil.doDecryption(encResponse.getBytes(), secretKey));
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            response = mapper.readValue(jsonData, YblReplacementSaleResponse.class);
        } catch (Exception e) {
            logger.error("error occurred while deserializing ybl replacement api response {} to json and error is {} for transactionId {}", encResponse, e.getMessage(), transactionId);
            Utility.getExceptionAsString(e);
        }
        return response;
    }

    // Encrypted the Ybl Replacement Sale Request
    private String getEncryptedRequest(YblReplacementSaleRequestPayload request, String transactionId) throws Exception {
        String panNumber = request.getPanNumber();
        String encryptedString = AppConstants.BLANK;
        String data = "{ \"panNumber\": \"" + panNumber + "\"}";
        try {
            encryptedString = new String(EncryptionDecryptionUtil.doEncryption(data, secretKey));
        } catch (Exception ex) {
            logger.error("Getting error {} while encrypting replacement api request for transactionId {}",Utility.getExceptionAsString(ex),transactionId);
        }
        return encryptedString;
    }

    private HttpHeaders setHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.add(AppConstants.YBLNIS_API_KEY_TEXT, yblnisApiKey);
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.add(AppConstants.YBLNIS_XIBM_CLIENT_KEY, yblnisClientId);
        header.add(AppConstants.YBLNIS_XIBM_CLIENT_SECRET, yblnisClientSecret);
        return header;
    }

    // Set Replacement Sale Flags in proposal details and push the request to Proposal Stream
    private void callSaveProposalForYblReplacementSale(NewReplacementSalePayload payload, String transactionId) {
        com.mli.mpro.proposal.models.InputRequest inputRequest = new com.mli.mpro.proposal.models.InputRequest();
        com.mli.mpro.proposal.models.Request request = new com.mli.mpro.proposal.models.Request();
        RequestData requestData = new RequestData();
        RequestPayload requestPayload = new RequestPayload();
        try {
            ProposalDetails proposalDetails = setProposalDetails(payload, transactionId);
            requestPayload.setProposalDetails(proposalDetails);
            requestData.setRequestPayload(requestPayload);
            request.setRequestData(requestData);
            inputRequest.setRequest(request);
            boolean status;
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_ALL) && FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.KINESIS_FOR_PROPOSAL)) {
                status = proposalStreamPushService.produceToProposalStream(inputRequest);
            }else{
                status = proposalStreamPushService.produceToProposalQueue(inputRequest);
            }
            String streamSentTime = setTimeStamp();
            logger.info("proposalStreamPushService.produceToProposalStream Status - {} at {} for transactionId {} ", status, streamSentTime, transactionId);
        } catch (Exception ex) {
            logger.error("Exception {} while save ybl replacement sale data for transactionId {}", Utility.getExceptionAsString(ex), transactionId);
        }
    }

    private ProposalDetails setProposalDetails(NewReplacementSalePayload payload, String transactionId) {
        ApplicationDetails applicationDetails = setApplicationDetails();
        AdditionalFlags additionalFlags = setAdditionalFlags(payload);
        ProposalDetails proposalDetails = new ProposalDetails();
        proposalDetails.setTransactionId(Long.parseLong(transactionId));
        proposalDetails.setApplicationDetails(applicationDetails);
        proposalDetails.setAdditionalFlags(additionalFlags);
        return proposalDetails;
    }

    private void setYblReplacementTags(List<YblPolicy> policyList, NewReplacementSalePayload replacementSalePayload, String transactionId) {
        if (null != policyList) {
            for (YblPolicy policy : policyList) {
                if (null != policy && AppConstants.YES.equalsIgnoreCase(policy.getFlag())) {
                    logger.info("Checking yblReplacementSale {} policyStatus {} for transactionId {} ", policy.getFlag(), policy.getCurrentStatus(), transactionId);
                    setYblReplacementSaleStatusValue(replacementSalePayload, policy.getCurrentStatus(), policy.getInsurerName(), transactionId);
                    break;
                }
            }
        }
    }

    // Set value of YBL replacement tags
    private void setYblReplacementSaleStatusValue(NewReplacementSalePayload replacementSalePayload, String yblReplacementPolicyStatus, String insurerName, String transactionId) {
        for (Map.Entry<String, List<String>> entry : yblReplacementSaleStatusMap.entrySet()) {
            List<String> values = entry.getValue();
            if (checkPolicyStatus(values, yblReplacementPolicyStatus)) {
                replacementSalePayload.setReplacementSale(AppConstants.Y);
                replacementSalePayload.setYblReplacementPolicyStatus(entry.getKey());
                logger.info("yblReplacementSale flag {}, insurerName {} and policyStatus {} for transactionId {} ", replacementSalePayload.getIsNewYblReplacementSale(), insurerName, replacementSalePayload.getYblReplacementPolicyStatus(), transactionId);
                break;
            }
        }
    }

    private boolean checkPolicyStatus(List<String> policyStatusList, String yblReplacementPolicyStatus) {
        return policyStatusList.stream().anyMatch(policyStatus -> policyStatus.trim().equalsIgnoreCase(yblReplacementPolicyStatus.trim()));
    }

    private ApplicationDetails setApplicationDetails() {
        ApplicationDetails applicationDetails = new ApplicationDetails();
        applicationDetails.setBackendHandlerType(AppConstants.YBL_REPLACEMENTSALE);
        return applicationDetails;
    }

    private AdditionalFlags setAdditionalFlags(NewReplacementSalePayload payload) {
        AdditionalFlags additionalFlags = new AdditionalFlags();
        additionalFlags.setIsNewYblReplacementSale(payload.getIsNewYblReplacementSale());
        additionalFlags.setYblReplacementPolicyStatus(payload.getYblReplacementPolicyStatus());
        return additionalFlags;
    }

    // Save the Request and Response in Auditing DB
    private void saveRequestResponseInAuditDB(YblReplacementSaleRequestPayload inputRequest, String transactionId, YblReplacementSaleResponse yblReplacementSaleResponse) {
        try {
            AuditingDetails auditDetails = new AuditingDetails();
            auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
            auditDetails.setTransactionId(Long.parseLong(transactionId));
            auditDetails.setServiceName("YblReplacementSale");
            auditDetails.setAuditId(transactionId);
            auditDetails.setRequestId(transactionId);
            ResponseObject respoObject = new ResponseObject();
            respoObject.setAdditionalProperty(AppConstants.RESPONSE, yblReplacementSaleResponse);
            auditDetails.setResponseObject(respoObject);
            auditService.saveAuditTransactionDetails(auditDetails);
        } catch (Exception e) {
            logger.info("Exception occured  while saving in auditing for transactionId {} is {}", transactionId, Utility.getExceptionAsString(e));

        }
    }

    private String setTimeStamp() {
        String formattedDateTime = "";
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            formatter.setTimeZone(TimeZone.getTimeZone("IST"));
            formattedDateTime = formatter.format(today);
            logger.info("timeStamp for replacement sale api is {}", formattedDateTime);
        } catch (Exception e) {
            logger.error("Exception occured in setTimeStamp-{}", e.getMessage());
        }
        return formattedDateTime;
    }

    private boolean checkRSConditions(YblReplacementSaleRequestPayload yblReplacementSaleRequestPayload) {
        String transactionId = yblReplacementSaleRequestPayload.getTransactionId();
        if (!StringUtils.isEmpty(yblReplacementSaleRequestPayload.getPanNumber())
                && !StringUtils.isEmpty(transactionId) && AppConstants.CHANNEL_YBL.equalsIgnoreCase(yblReplacementSaleRequestPayload.getChannel()) && !(AppConstants.FORM3.equalsIgnoreCase(yblReplacementSaleRequestPayload.getFormType()))
                && !yblReplacementSaleRequestPayload.getIsYblTelesalesCase()) {
            return true;
        }
        logger.info("Ybl Replacement Api Not called for request {} and transactionId {}", yblReplacementSaleRequestPayload, transactionId);
        return false;
    }

    /**
     * Calling Ybl Replacement Api again at 4th page proceed if ybl replacement sale tags not saved in DB because of overriden by save call
     * @param transactionId
     * @return
     */
    private boolean checkYblReplacementSaleTagExist(String transactionId) {
        boolean yblRSTagExist = false;
        if (transactionId.matches("\\d+")) {
            ProposalDetails existingProposalDetails = proposalRepository.findAdditionalFlagsByTransactionId(Long.parseLong(transactionId));
            if (null != existingProposalDetails && null != existingProposalDetails.getAdditionalFlags()) {
                // On proceed of 2nd screen below values in logs are null, and if on proceed of 4th screen values are still null i.e replacement sale tags are overriden by save call...
                logger.info("Checking newYblReplacementSaleTag {} and yblReplacementPolicyStatus {} for transactionId {} from DB", existingProposalDetails.getAdditionalFlags().getIsYblNewReplacementSale()
                        , existingProposalDetails.getAdditionalFlags().getYblReplacementPolicyStatus(), transactionId);
                if ((null != existingProposalDetails.getAdditionalFlags().getIsYblNewReplacementSale() &&
                        null != existingProposalDetails.getAdditionalFlags().getYblReplacementPolicyStatus())) {
                    logger.info("Not calling ybl replacement api again as tags are already saved in DB for transactionId {} ", transactionId);
                    yblRSTagExist = true;
                }
            }
        }
        return yblRSTagExist;
    }
}