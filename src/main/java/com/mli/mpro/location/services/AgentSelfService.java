package com.mli.mpro.location.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.agent.models.Header;
import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.agentSelf.*;
import com.mli.mpro.agentSelf.Payload;
import com.mli.mpro.agentSelfIdentifiedSkip.*;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.OauthTokenResponse;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.SourcingDetails;
import com.mli.mpro.proposal.models.SpeicifiedPersonDetails;
import com.mli.mpro.utils.EncryptionDecryptionOnboardingUtil;
import com.mli.mpro.utils.Utility;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static java.util.Objects.nonNull;

@Service
public class AgentSelfService {

    private static final Logger log = LoggerFactory.getLogger(AgentSelfService.class);
    @Value("${urlDetails.datalake.agntSlfUrl}")
    private String datalakeurl;

    @Value("${urlDetails.datalake.apiid}")
    private String dataLakeApiId;
    @Value("${urlDetails.datalake.apiKey}")
    private String dataLakeApiKey;
    @Value("${urlDetails.datalake.authClientId}")
    private String oAuthClientId;
    @Value("${urlDetails.datalake.correlationId}")
    private String correlationId;
    @Value("${urlDetails.datalake.authClientSecret}")
    private String oAuthClientSecret;
    @Value("${urlDetails.datalake.authUrl}")
    private String oAuthUrl;
    @Value("${urlDetails.agntSlfUrl}")
    private String agntSlfUrl;
    @Value("${urlDetails.agntSlfApiId}")
    private String agntSlfApiId;
    @Value("${urlDetails.agntSlfApiKey}")
    private String agntSlfApiKey;

    @Value("${urlDetails.agntSlfEncryptUrl}")
    private String agntSlfEncryptUrl;
    @Value("${urlDetails.agntSlfEncryptApiId}")
    private String agntSlfEncryptApiId;
    @Value("${urlDetails.agntSlfEncryptApiKey}")
    private String agntSlfEncryptApiKey;

    //FUL2-160608 AgentSelf Encryption
    @Value("${urlDetails.agntSlf.oauthUrl}")
    private String oauthTokenUrl;
    @Value("${urlDetails.agntSlf.oauthUsername}")
    private String authClientId;
   @Value("${urlDetails.agntSlf.oauthPassword}")
    private String authClientSecret;
   @Value("${urlDetails.agntSlfRedisKey}")
    private static String redisKey;
    @Value("${urlDetails.authUserName}")
    private String authTokenUsername;
    @Value("${urlDetails.authPassword}")
    private String authTokenPassword;
    @Value("${urldetails.oauth.agentSelf.encr.decr.key}")
    private String encryptDecryptKey;

    @Autowired
    private OauthTokenRepository oauthTokenRepo;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoOperations mongoOperation;
    @Autowired
    AuditService auditService;

    public AgentSelfResponsePayload validateAgentSelfCheck(InputRequest inputRequest) throws UserHandledException, JSONException {

        String transactionId = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getTransactionId();
        log.info("Input request for agentSelf API {} for transaction Id{}", Utility.getJsonRequest(inputRequest), transactionId);
        AgentSelfResponsePayload agentSelfResponsePayload = new AgentSelfResponsePayload();
        if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEAGENTSELFAPI))) {
            com.mli.mpro.agentSelf.InputRequest agentSelfInputRequest = new com.mli.mpro.agentSelf.InputRequest();
            com.mli.mpro.agentSelf.Encryption.InputRequest agentSelfEncryptInputRequest= new  com.mli.mpro.agentSelf.Encryption.InputRequest();
            ResponseEntity<InputResponse> agentSelfResponse = executeAgentCheckAPICall(agentSelfResponsePayload, agentSelfInputRequest,agentSelfEncryptInputRequest, inputRequest, transactionId);
            if (null == agentSelfResponse) {
                agentSelfResponse = executeAgentCheckAPICall(agentSelfResponsePayload, agentSelfInputRequest,agentSelfEncryptInputRequest, inputRequest, transactionId);
            }
            if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLE_AUDITING_FOR_AGENT_SELF_CHECK_API))) {
                AuditingDetails auditingDetails = setAuditingDetails(agentSelfResponse.getStatusCode().name(),
                        String.valueOf(agentSelfResponse.getStatusCode().value()), transactionId,
                        inputRequest.getRequest(), agentSelfResponse);
                auditService.saveAuditTransactionDetails(auditingDetails);
            }
            if (getAgentExistance(agentSelfResponse)) {
                String agntId = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getAgentId();
                ArrayList<AgentCheck> agentCheck = getAgentCheckList(agentSelfResponse);
                AgentSelfResponsePayload agentSelfResponsePayload1 = getSelfOrOtherAgentOrEmployeeOrCustomer(inputRequest, transactionId, agentSelfResponsePayload, agntId, agentCheck);
                if (agentSelfResponsePayload1 != null) return agentSelfResponsePayload1;
            } else {
                //Agent Existance is false
                AgentSelfResponsePayload agentSelfResponsePayload1 = checkMliEmployee(inputRequest, agentSelfResponsePayload, transactionId);
                if (agentSelfResponsePayload1 != null) {
                    agentSelfResponsePayload1.setSource(AppConstants.EMPLOYEE);
                    agentSelfResponsePayload1.setIsAgentSelf(AppConstants.YES);
                    return agentSelfResponsePayload1;
                }
                return checkCustomer(inputRequest, agentSelfResponsePayload);
            }
        } else {
            //Feature flag is false
            log.info("Agent Self check feature flag is disabled");
            agentSelfResponsePayload.setServiceStatus("Agent Self check feature flag is disabled for transactionId");
            return agentSelfResponsePayload;
        }
        return agentSelfResponsePayload;
    }

    private AgentSelfResponsePayload checkCustomer(InputRequest inputRequest, AgentSelfResponsePayload agentSelfResponsePayload) {
        AgentSelfResponsePayload agentSelfResponsePayload4 = checkForCustomer(inputRequest, agentSelfResponsePayload);
        if (agentSelfResponsePayload4 != null) return agentSelfResponsePayload4;
        return getAgentSelfDefaultResponsePayload(agentSelfResponsePayload,inputRequest);
    }

    private AgentSelfResponsePayload getAgentSelfDefaultResponsePayload(AgentSelfResponsePayload agentSelfResponsePayload1, InputRequest inputRequest) {
        AgentSelfResponsePayload agentSelfResponsePayload = new AgentSelfResponsePayload();
        try {
            agentSelfResponsePayload.setIsAgentSelf(AppConstants.NO);
            agentSelfResponsePayload.setSource(AppConstants.BLANK);
            agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.NO);
            agentSelfResponsePayload.setAgentFraudCheckSkip(AppConstants.NO);
            agentSelfResponsePayload.setPanMatched(AppConstants.NO);
            agentSelfResponsePayload.setMobileMatched(AppConstants.NO);
            agentSelfResponsePayload.setEmailMatched(AppConstants.NO);
            agentSelfResponsePayload.setDobMatched(AppConstants.NO);
            if (null != agentSelfResponsePayload1) {
                agentSelfResponsePayload.setRequestTimestamp(agentSelfResponsePayload1.getRequestTimestamp());
                agentSelfResponsePayload.setResponseTimestamp(agentSelfResponsePayload1.getResponseTimestamp());
                agentSelfResponsePayload.setServiceStatus(agentSelfResponsePayload1.getServiceStatus());
            } else {
                agentSelfResponsePayload.setRequestTimestamp("");
                agentSelfResponsePayload.setResponseTimestamp("");
                agentSelfResponsePayload.setServiceStatus("");
            }
            ResponseObject respoObject = new ResponseObject();
            AuditingDetails auditDetails = getAuditingDetails(inputRequest, inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getTransactionId());
            respoObject.setAdditionalProperty(AppConstants.RESPONSE, agentSelfResponsePayload);
            auditDetails.setResponseObject(respoObject);
            String requestId = UUID.randomUUID().toString();
            agentSelfResponsePayload.setAuditRequestId(requestId);
            auditService.saveAuditTransactionDetailsForAgentSelf(auditDetails, requestId);
        } catch (Exception e) {
            log.info("Exception in getAgentSelfDefaultResponsePayload: {}", Utility.getExceptionAsString(e));
        }
        return agentSelfResponsePayload;
    }

    private AgentSelfResponsePayload getSelfOrOtherAgentOrEmployeeOrCustomer(InputRequest inputRequest, String transactionId, AgentSelfResponsePayload agentSelfResponsePayload, String agntId, ArrayList<AgentCheck> agentCheck) {
        try {
            if (null != agentCheck) {
                AgentSelfResponsePayload agentSelfResponsePayload2 = checkForSelfAgent(inputRequest, transactionId, agentSelfResponsePayload, agntId, agentCheck);
                if (agentSelfResponsePayload2 != null) return agentSelfResponsePayload2;
                //Other Agent
                log.info("Checking for other agent for transaction Id{}", transactionId);
                AgentSelfResponsePayload agentSelfResponsePayload3 = checkForOtherAgent(inputRequest, transactionId, agentSelfResponsePayload, agntId, agentCheck);
                if (agentSelfResponsePayload3 != null) return agentSelfResponsePayload3;
                //Agent Existance is false
                AgentSelfResponsePayload agentSelfResponsePayload1 = getMliEmployeeCheck(inputRequest, transactionId, agentSelfResponsePayload);
                if (agentSelfResponsePayload1 != null) return agentSelfResponsePayload1;
                //Customer Check
                AgentSelfResponsePayload agentSelfResponsePayload4 = checkForCustomer(inputRequest, agentSelfResponsePayload);
                if (agentSelfResponsePayload4 != null) return agentSelfResponsePayload4;
            }
        } catch (Exception e) {
            log.info("Exception occurred during getSelfOrOtherAgentOrEmployeeOrCustomer {}",Utility.getExceptionAsString(e));
        }
        return null;
    }

    private AgentSelfResponsePayload checkForCustomer(InputRequest inputRequest, AgentSelfResponsePayload agentSelfResponsePayload) {
        try {
            if (isNonNullDedupeResponse(inputRequest)) {
                String dedupeResponse = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getDedupeResponse();
                if (AppConstants.PARTIALMATCH.equalsIgnoreCase(dedupeResponse)) {
                    agentSelfResponsePayload = getAgentSelfDefaultResponsePayload(agentSelfResponsePayload,inputRequest);
                    agentSelfResponsePayload.setSource("Customer");
                    agentSelfResponsePayload.setIsAgentSelf(AppConstants.NO);
                    return agentSelfResponsePayload;
                } else {
                    return getAgentSelfDefaultResponsePayload(agentSelfResponsePayload,inputRequest);
                }

            }
        } catch (Exception e) {
           log.info("Exception in checkForCustomer: {}", Utility.getExceptionAsString(e));
        }
        return null;
    }

    private AgentSelfResponsePayload checkForOtherAgent(InputRequest inputRequest, String transactionId, AgentSelfResponsePayload agentSelfResponsePayload, String agntId, ArrayList<AgentCheck> agentCheck) {
        try {
            Date dt = getCurrentDate();
            List<AgentCheck> otherAgntCheck = agentCheck.stream()
                    .filter(actvagnt -> !(actvagnt.getAgentId().equalsIgnoreCase(agntId))
                            && AppConstants.ACTIVE_AGENT.equalsIgnoreCase(actvagnt.getAgentStatus())
                            && (Objects.isNull(actvagnt.getAmlTrainingDateInternal()) || actvagnt.getAmlTrainingDateInternal().after(dt) || actvagnt.getAmlTrainingDateInternal().equals(dt)))
                    .collect(Collectors.toList());
            if (otherAgntCheck.size() > 0) {
                checkOtherAgent(agentSelfResponsePayload, inputRequest, otherAgntCheck, transactionId);
                if (isMatch(agentSelfResponsePayload)) {
                    agentSelfResponsePayload.setIsAgentSelf(AppConstants.YES);
                    return agentSelfResponsePayload;
                } else {
                    AgentSelfResponsePayload agentSelfResponsePayload1 = getMliEmployeeCheck(inputRequest, transactionId, agentSelfResponsePayload);
                    if (agentSelfResponsePayload1 != null) return agentSelfResponsePayload1;
                }
            } else {
                AgentSelfResponsePayload agentSelfResponsePayload1 = getMliEmployeeCheck(inputRequest, transactionId, agentSelfResponsePayload);
                if (agentSelfResponsePayload1 != null) return agentSelfResponsePayload1;
            }
        } catch (Exception e) {
            log.info("Exception occurred while checkForOtherAgent {}",Utility.getExceptionAsString(e));
        }

        return null;
    }

    private AgentSelfResponsePayload checkForSelfAgent(InputRequest inputRequest, String transactionId, AgentSelfResponsePayload agentSelfResponsePayload, String agntId, ArrayList<AgentCheck> agentCheck) {
        try {
            List<AgentCheck> selfAgent = agentCheck.stream().filter(actvagnt -> actvagnt.getAgentId().equalsIgnoreCase(agntId)).collect(Collectors.toList());
            if (selfAgent.size() > 0) {
                checkAgentSelf(agentSelfResponsePayload, inputRequest, selfAgent, transactionId);
                if (isMatch(agentSelfResponsePayload)) {
                    agentSelfResponsePayload.setIsAgentSelf(AppConstants.YES);
                    return agentSelfResponsePayload;
                }
            }
        } catch (Exception e) {
            log.info("Exception occurred while calling checkForSelfAgent {}",Utility.getExceptionAsString(e));
        }
        return null;
    }

    private boolean isNonNullDedupeResponse(InputRequest inputRequest) {
        return null != inputRequest.getRequest() && null != inputRequest.getRequest().getRequestData()
                && null != inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload() &&
                null != inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getDedupeResponse();
    }

    private AgentSelfResponsePayload getMliEmployeeCheck(InputRequest inputRequest, String transactionId, AgentSelfResponsePayload agentSelfResponsePayload) {
        checkMliEmployee(inputRequest, agentSelfResponsePayload, transactionId);
        try {
            if (isMatch(agentSelfResponsePayload)) {
                agentSelfResponsePayload.setSource(AppConstants.EMPLOYEE);
                agentSelfResponsePayload.setIsAgentSelf(AppConstants.YES);
                return agentSelfResponsePayload;
            }

        } catch (Exception e) {
            log.info("Exception occurred while getMliEmployeeCheck {}",Utility.getExceptionAsString(e));
        }
        return null;
    }

    private boolean isMatch(AgentSelfResponsePayload agentSelfResponsePayload) {
        return (AppConstants.YES.equalsIgnoreCase(agentSelfResponsePayload.getEmailMatched()))
                || (AppConstants.YES.equalsIgnoreCase(agentSelfResponsePayload.getMobileMatched()))
                || (AppConstants.YES.equalsIgnoreCase(agentSelfResponsePayload.getPanMatched()));
    }


    /**
     * @param inputRequest
     * @param agentSelfResponsePayload
     * @param transactionId
     * @return
     */
    private AgentSelfResponsePayload checkMliEmployee(InputRequest inputRequest, AgentSelfResponsePayload agentSelfResponsePayload, String transactionId) {
        //for MLI Employee
        try {
            if (null != inputRequest) {
                log.info("checking for MLIEmployee for transactionId{}", transactionId);
                String panNumber = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPanNumber();
                String phoneNumber = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPhoneNumber();
                String emailId = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getEmail();
                Query query;
                query = new Query();
                query.addCriteria(Criteria.where("panNumber").is(panNumber));
                List<MaxlifeEmployeeDetails> maxlifeEmployeePanMatchedList = mongoTemplate.find(query, MaxlifeEmployeeDetails.class);
                if (maxlifeEmployeePanMatchedList.size() > 0) {
                    List<MaxlifeEmployeeDetails> maxlifeEmployeePanMatchedList1 = dateComparisonEmployeeList(maxlifeEmployeePanMatchedList);
                    return setEmployeeResponsePayload(AppConstants.EMPLOYEE, agentSelfResponsePayload, inputRequest, maxlifeEmployeePanMatchedList1, transactionId);

                } else {
                    query = new Query();
                    query.addCriteria(Criteria.where("mobileNumber").is(phoneNumber));
                    List<MaxlifeEmployeeDetails> maxlifeEmployeeMobileMatchedList = mongoTemplate.find(query, MaxlifeEmployeeDetails.class);
                    if (maxlifeEmployeeMobileMatchedList.size() > 0) {
                        List<MaxlifeEmployeeDetails> maxlifeEmployeeMobileMatchedList1 = dateComparisonEmployeeList(maxlifeEmployeeMobileMatchedList);
                        return setEmployeeResponsePayload(AppConstants.EMPLOYEE, agentSelfResponsePayload, inputRequest, maxlifeEmployeeMobileMatchedList1, transactionId);

                    } else {
                        query = new Query();
                        query.addCriteria(Criteria.where("emailId").is(emailId));
                        List<MaxlifeEmployeeDetails> maxlifeEmployeeEmailMatchedList = mongoTemplate.find(query, MaxlifeEmployeeDetails.class);
                        if (maxlifeEmployeeEmailMatchedList.size() > 0) {
                            List<MaxlifeEmployeeDetails> maxlifeEmployeeEmailMatchedList1 = dateComparisonEmployeeList(maxlifeEmployeeEmailMatchedList);
                            return setEmployeeResponsePayload(AppConstants.EMPLOYEE, agentSelfResponsePayload, inputRequest, maxlifeEmployeeEmailMatchedList1, transactionId);
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            log.info("Exception occurred during checkMliEmployee {}",Utility.getExceptionAsString(e));
        }
        return agentSelfResponsePayload;
    }

    private AgentSelfResponsePayload checkOtherAgent(AgentSelfResponsePayload agentSelfResponsePayload, InputRequest inputRequest, List<AgentCheck> otherAgntCheck, String transactionId) {
        return setAgentSelfResponsePayload(OTHER_AGENT, agentSelfResponsePayload, inputRequest, otherAgntCheck, transactionId);
    }

    /**
     * This method checks for
     * agentself
     *
     * @param agentSelfResponsePayload
     * @param inputRequest
     * @param selfAgent
     * @param transactionId
     * @return
     */
    private AgentSelfResponsePayload checkAgentSelf(AgentSelfResponsePayload agentSelfResponsePayload, InputRequest inputRequest, List<AgentCheck> selfAgent, String transactionId) {

        //Self Agent sourced
        log.info("checking for selfSourcedAgent for transactionId{}", transactionId);
        Date dt = getCurrentDate();
        List<AgentCheck> activeAgentcheck = selfAgent.stream()
                .filter(actvagnt -> actvagnt.getAgentStatus().equalsIgnoreCase(AppConstants.ACTIVE_AGENT))
                .collect(Collectors.toList());

        if (activeAgentcheck.size() > 0) {
            List<AgentCheck> amlTrainingDateMatchFailedList = activeAgentcheck.stream()
                    .filter(aml -> Objects.nonNull(aml.getAmlTrainingDateInternal()) &&aml.getAmlTrainingDateInternal().before(dt))
                    .collect(Collectors.toList());
            if (amlTrainingDateMatchFailedList.size() > 0) {
                log.info("Case is stopped here for transactionId {}", transactionId);
            } else {
                List<AgentCheck> amlTrainingDateMatchSuccessList = activeAgentcheck.stream()
                        .filter(aml -> Objects.isNull(aml.getAmlTrainingDateInternal())||aml.getAmlTrainingDateInternal().after(dt) || aml.getAmlTrainingDateInternal().equals(dt))
                        .collect(Collectors.toList());
                if (amlTrainingDateMatchSuccessList.size() > 0) {
                    setAgentSelfResponsePayload("selfAgent", agentSelfResponsePayload, inputRequest, amlTrainingDateMatchSuccessList, transactionId);
                    return agentSelfResponsePayload;
                }
            }
        } else {
            log.info("Case is stopped here for transactionId {}", transactionId);

        }
        return agentSelfResponsePayload;
    }

    private Date getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * This method checks for
     * agentExistance and returns in boolean
     *
     * @param agentSelfResponse
     * @return
     */
    private boolean getAgentExistance(ResponseEntity<InputResponse> agentSelfResponse) {
        InputResponse body = null;
        boolean agentCheckFlag=false;
        try {
            if (Objects.nonNull(agentSelfResponse))
                body = agentSelfResponse.getBody();
            if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DATALAKE_AGNTSLF))) {
                ArrayList<AgentCheck> agentCheckList = Optional.ofNullable(body).map(b -> b.getResponse()).map(Response::getPayload).map(Payload::getAgentCheck).orElse(null);
                if (Objects.nonNull(agentCheckList)) {
                    agentCheckFlag =agentCheckList.stream().filter(ap -> "YES".equalsIgnoreCase(ap.getAgentExistance()))
                                                           .findFirst().isPresent();
                }
            }
            else{
                agentCheckFlag =AppConstants.YES.equalsIgnoreCase(body.getResponse().getPayload().getAgentExistance());
            }
            return Objects.nonNull(body) &&
                    Objects.nonNull(body.getResponse()) &&
                    Objects.nonNull(body.getResponse().getPayload()) &&
                    agentCheckFlag;
        } catch (Exception e) {
            log.info("Exception occurred during getAgentExistance is {}", Utility.getExceptionAsString(e));
            return false;
        }

    }

    private ArrayList<AgentCheck> getAgentCheckList(ResponseEntity<InputResponse> agentSelfResponse) {
        ArrayList<AgentCheck> agentCheckList = null;
        try {
            InputResponse body = agentSelfResponse.getBody();
            if (Objects.nonNull(body)
                    && Objects.nonNull(body.getResponse())
                    && Objects.nonNull(body.getResponse().getPayload())
                    && Objects.nonNull(body.getResponse().getPayload().getAgentCheck())) {

                agentCheckList =body.getResponse().getPayload().getAgentCheck();
                return dateComparisonList(agentCheckList);
            }
        } catch (Exception e) {
            log.info("Exception in getAgentCheckList: {}", Utility.getExceptionAsString(e));
        }
        return agentCheckList;
    }

    /**
     * This method sets agentSelf response payload
     *
     * @param source
     * @param agentSelfResponsePayload
     * @param inputRequest
     * @param amlTrainingDateMatchSuccessList
     * @param transactionId
     * @return
     */
    private AgentSelfResponsePayload setAgentSelfResponsePayload(String source, AgentSelfResponsePayload agentSelfResponsePayload, InputRequest inputRequest, List<AgentCheck> amlTrainingDateMatchSuccessList, String transactionId) {
        if (null != amlTrainingDateMatchSuccessList) {
            String inputPanNum = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPanNumber();
            String inputPhoneNum = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPhoneNumber();
            String inputEmail = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getEmail();
            Date inputDob = getConvertedDate(inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getDob());

            List<AgentCheck> panNumMatch = amlTrainingDateMatchSuccessList.stream().filter(pnmtch -> pnmtch.getPanNumber().equalsIgnoreCase(inputPanNum)).collect(Collectors.toList());
            List<AgentCheck> phoneNumMatch = amlTrainingDateMatchSuccessList.stream().filter(mobmtch -> mobmtch.getMobileNumber().equalsIgnoreCase(inputPhoneNum)).collect(Collectors.toList());
            List<AgentCheck> emailMatch = amlTrainingDateMatchSuccessList.stream().filter(ematch -> ematch.getEmailId().equalsIgnoreCase(inputEmail)).collect(Collectors.toList());
            List<AgentCheck> dobMatch = amlTrainingDateMatchSuccessList.stream().filter(dobmatch -> dobmatch.getDobInternal().equals(inputDob)).collect(Collectors.toList());
            log.info(AppConstants.DOBMATCHLOG, transactionId, dobMatch);

            if (panNumMatch.size() > 0) {
                log.info(AppConstants.PANMATCHLOG, transactionId, panNumMatch);
                agentSelfResponsePayload.setPanMatched(AppConstants.YES);
                agentSelfResponsePayload.setMobileMatched(AppConstants.NO);
                agentSelfResponsePayload.setEmailMatched(AppConstants.NO);
            } else if (phoneNumMatch.size() > 0) {
                log.info(AppConstants.PHONEMATCHLOG, transactionId, phoneNumMatch);
                agentSelfResponsePayload.setMobileMatched(AppConstants.YES);
                agentSelfResponsePayload.setPanMatched(AppConstants.NO);
                agentSelfResponsePayload.setEmailMatched(AppConstants.NO);
            } else if (emailMatch.size() > 0) {
                log.info(AppConstants.EMAILMATCHLOG, transactionId, emailMatch);
                agentSelfResponsePayload.setMobileMatched(AppConstants.NO);
                agentSelfResponsePayload.setPanMatched(AppConstants.NO);
                agentSelfResponsePayload.setEmailMatched(AppConstants.YES);
            }

            setAgentSelfTags(source, agentSelfResponsePayload, transactionId, panNumMatch, phoneNumMatch, emailMatch, dobMatch
                    ,inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload());
        }

        log.info("AgentSelf response payload set for transactionIs{} is {}", transactionId, agentSelfResponsePayload);
        return agentSelfResponsePayload;

    }

    private void setAgentSelfTags(String source, AgentSelfResponsePayload agentSelfResponsePayload, String transactionId, List<AgentCheck> panNumMatch
            , List<AgentCheck> phoneNumMatch, List<AgentCheck> emailMatch, List<AgentCheck> dobMatch,AgentSelfRequestPayload agentSelfRequestPayload) {
        if (dobMatch.size() > 0) {
            log.info(AppConstants.DOBMATCHLOG, transactionId, emailMatch);
            agentSelfResponsePayload.setDobMatched(AppConstants.YES);
        } else {
            agentSelfResponsePayload.setDobMatched(AppConstants.NO);
        }
        if (source.equals(OTHER_AGENT) && (panNumMatch.size() > 0 || phoneNumMatch.size() > 0 || emailMatch.size() > 0) && (dobMatch.size() > 0)) {
            agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.YES);
        } else {
            if ((panNumMatch.size() > 0 || phoneNumMatch.size() > 0 || emailMatch.size() > 0) && !(dobMatch.size() > 0)) {
                agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.YES);
            } else {
                agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.NO);
            }
        }
        setAgentFraudIdentifierForNomineeAppointee(agentSelfRequestPayload,agentSelfResponsePayload, source, panNumMatch, phoneNumMatch, emailMatch, dobMatch);
        agentSelfResponsePayload.setAgentFraudCheckSkip(AppConstants.NO);
        if ((dobMatch.size() > 0) && (panNumMatch.size() > 0 || phoneNumMatch.size() > 0 || emailMatch.size() > 0)) {
            agentSelfResponsePayload.setSource(source);
        } else {
            agentSelfResponsePayload.setSource(OTHER_AGENT);
        }
    }

    private void setAgentFraudIdentifierForNomineeAppointee(AgentSelfRequestPayload agentSelfRequestPayload, AgentSelfResponsePayload agentSelfResponsePayload, String source, List<AgentCheck> panNumMatch, List<AgentCheck> phoneNumMatch, List<AgentCheck> emailMatch, List<AgentCheck> dobMatch) {
        if (StringUtils.hasLength(agentSelfRequestPayload.getEntityType())
                && NOMINEE_APPOINTEE_TYPES.contains(agentSelfRequestPayload.getEntityType()) && OTHER_AGENT.equalsIgnoreCase(source)) {
            log.info("checking for nominee and appointee for transactionId {}", agentSelfRequestPayload.getTransactionId());
            if (isCasePassed(agentSelfRequestPayload, panNumMatch, emailMatch, phoneNumMatch, dobMatch)) {
                log.info("AgentSelf is passed for transactionId {} for EntityType {}", agentSelfRequestPayload.getTransactionId(), agentSelfRequestPayload.getEntityType());
                agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.NO);
            } else
                agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.YES);
        }
    }

    private boolean isCasePassed(AgentSelfRequestPayload agentSelfRequestPayload, List<AgentCheck> panNumMatch, List<AgentCheck> emailMatch, List<AgentCheck> phoneNumMatch, List<AgentCheck> dobMatch) {
        boolean isPanMatched = true;
        boolean isEmailMatched = true;
        if (StringUtils.hasLength(agentSelfRequestPayload.getPanNumber())) {
            isPanMatched = (panNumMatch.size() > 0);
        }
        if (StringUtils.hasLength(agentSelfRequestPayload.getEmail())) {
            isEmailMatched = (emailMatch.size() > 0);
        }
        log.info("isPanMatched {} and isEmailMatched {} for transactionId {}", isPanMatched, isEmailMatched, agentSelfRequestPayload.getTransactionId());
        return isPanMatched && isEmailMatched && (phoneNumMatch.size() > 0) && (dobMatch.size() > 0);
    }
    /**
     * @param source
     * @param agentSelfResponsePayload
     * @param inputRequest
     * @param maxlifeEmployeeList
     * @param transactionId
     * @return
     */
    private AgentSelfResponsePayload  setEmployeeResponsePayload(String source, AgentSelfResponsePayload agentSelfResponsePayload, InputRequest inputRequest, List<MaxlifeEmployeeDetails> maxlifeEmployeeList, String transactionId) throws UserHandledException {
        if (null != maxlifeEmployeeList) {
            String inputPanNum = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPanNumber();
            String inputPhoneNum = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPhoneNumber();
            String inputEmail = inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getEmail();
            Date inputDob = getConvertedDate(inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getDob());

            List<MaxlifeEmployeeDetails> panNumMatch = maxlifeEmployeeList.stream().filter(pnmtch -> pnmtch.getPanNumber().equalsIgnoreCase(inputPanNum)).collect(Collectors.toList());

            List<MaxlifeEmployeeDetails> phoneNumMatch = maxlifeEmployeeList.stream().filter(mobmtch -> mobmtch.getMobileNumber().equalsIgnoreCase(inputPhoneNum)).collect(Collectors.toList());

            List<MaxlifeEmployeeDetails> emailMatch = maxlifeEmployeeList.stream().filter(ematch -> ematch.getEmailId().equalsIgnoreCase(inputEmail)).collect(Collectors.toList());

            List<MaxlifeEmployeeDetails> dobMatch = maxlifeEmployeeList.stream().filter(dobmatch -> dobmatch.getDobInternal().equals(inputDob)).collect(Collectors.toList());

            if (panNumMatch.size() > 0) {
                log.info(AppConstants.PANMATCHLOG, transactionId, panNumMatch);
                agentSelfResponsePayload.setPanMatched(AppConstants.YES);
                agentSelfResponsePayload.setMobileMatched(AppConstants.NO);
                agentSelfResponsePayload.setEmailMatched(AppConstants.NO);
            } else if (phoneNumMatch.size() > 0) {
                log.info(AppConstants.PHONEMATCHLOG, transactionId, phoneNumMatch);
                agentSelfResponsePayload.setMobileMatched(AppConstants.YES);
                agentSelfResponsePayload.setPanMatched(AppConstants.NO);
                agentSelfResponsePayload.setEmailMatched(AppConstants.NO);
            } else if (emailMatch.size() > 0) {
                log.info(AppConstants.EMAILMATCHLOG, transactionId, emailMatch);
                agentSelfResponsePayload.setMobileMatched(AppConstants.NO);
                agentSelfResponsePayload.setPanMatched(AppConstants.NO);
                agentSelfResponsePayload.setEmailMatched(AppConstants.YES);
            }

            if (dobMatch.size() > 0) {
                log.info(AppConstants.DOBMATCHLOG, transactionId, dobMatch);
                agentSelfResponsePayload.setDobMatched(AppConstants.YES);
            } else {
                agentSelfResponsePayload.setDobMatched(AppConstants.NO);
            }
            if ((panNumMatch.size() > 0 || phoneNumMatch.size() > 0 || emailMatch.size() > 0) && !(dobMatch.size() > 0)) {
                agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.YES);
            } else {
                agentSelfResponsePayload.setAgentFraudIdentified(AppConstants.NO);
            }
            agentSelfResponsePayload.setAgentFraudCheckSkip(AppConstants.NO);
            agentSelfResponsePayload.setSource(source);
            log.info("AgentSelf response payload set after DB call for transactionIs{} is {}", transactionId, agentSelfResponsePayload);
            ResponseObject respoObject = new ResponseObject();
            AuditingDetails auditDetails = getAuditingDetails(inputRequest, transactionId);
            respoObject.setAdditionalProperty(AppConstants.RESPONSE, maxlifeEmployeeList);
            auditDetails.setResponseObject(respoObject);
            String requestId = UUID.randomUUID().toString();
            agentSelfResponsePayload.setAuditRequestId(requestId);
            auditService.saveAuditTransactionDetailsForAgentSelf(auditDetails, requestId);
        }
        return agentSelfResponsePayload;

    }

    private ResponseEntity<InputResponse> executeAgentCheckAPICall(AgentSelfResponsePayload agentSelfResponsePayload,com.mli.mpro.agentSelf.InputRequest agentSelfInputRequest,com.mli.mpro.agentSelf.Encryption.InputRequest agentSelfEncryptInputRequest, InputRequest inputRequest, String transactionId) throws UserHandledException {
        String resTimeStamp = "";
        String reqTimeStamp = "";
        ResponseEntity<InputResponse> agentSelfResponse = null;
        try {
            reqTimeStamp = setTimeStamp();
            log.info("request time for agent self API for transactionId {} is {}", transactionId, reqTimeStamp);
            agentSelfResponsePayload.setRequestTimestamp(reqTimeStamp);
            agentSelfResponse = executeAgentSelfCheck(agentSelfInputRequest,agentSelfEncryptInputRequest, inputRequest, transactionId, agentSelfResponsePayload);
            resTimeStamp = setTimeStamp();
            log.info("Response time and response for agent self API for transactionId {} at {} is {}", transactionId, resTimeStamp, agentSelfResponse);
        } catch (Exception e) {
            resTimeStamp = setTimeStamp();
            log.info("Exception occurred while executeAgentCheckAPICall is {}", Utility.getExceptionAsString(e));
        }
        agentSelfResponsePayload.setResponseTimestamp(resTimeStamp);
        setServiceStatus(agentSelfResponsePayload, Objects.nonNull(agentSelfResponse) ? agentSelfResponse : null, transactionId);
        return agentSelfResponse;
    }


    private AgentSelfResponsePayload setServiceStatus(AgentSelfResponsePayload agentSelfResponsePayload, ResponseEntity<InputResponse> agentSelfResponse, String transactionId) {
        InputResponse body = null;
        try {
            if (Objects.nonNull(agentSelfResponse))
                body = agentSelfResponse.getBody();
            if (Objects.nonNull(body) && Objects.nonNull(body.getResponse()) &&
                    Objects.nonNull(body.getResponse().getMsgInfo()) &&
                    Objects.nonNull(body.getResponse().getMsgInfo().getMsgCode()) &&
                    body.getResponse().getMsgInfo().getMsgCode().equalsIgnoreCase("200")) {
                agentSelfResponsePayload.setServiceStatus(AppConstants.SUCCESS);
            } else {
                agentSelfResponsePayload.setServiceStatus(AppConstants.FAILED);
            }
        } catch (Exception e) {
            agentSelfResponsePayload.setServiceStatus(AppConstants.FAILED);
            log.info("Exception occurred during setServiceStatus is {}", Utility.getExceptionAsString(e));
        }
        return agentSelfResponsePayload;
    }

    /**
     * This method sets data for
     * agentself inputrequest
     *
     * @param inputRequest
     * @param agentSelfInputRequest
     * @param transactionId
     * @return
     * @throws UserHandledException
     */

    private HttpEntity<? extends Object> setDataForAgentSelf(InputRequest inputRequest, com.mli.mpro.agentSelf.InputRequest agentSelfInputRequest,
                                                                                com.mli.mpro.agentSelf.Encryption.InputRequest agentSelfEncryptInputRequest,String transactionId) throws UserHandledException {
        com.mli.mpro.pasa.Payload payload = new com.mli.mpro.pasa.Payload();
        Request request = new Request();
        com.mli.mpro.agentSelf.Encryption.Request encryptRequest = new com.mli.mpro.agentSelf.Encryption.Request();
        Header header = new Header();
        HttpEntity<com.mli.mpro.agentSelf.InputRequest> httpEntity = null;
        HttpEntity<com.mli.mpro.agentSelf.Encryption.InputRequest> httpEntityWithEncryption = null;
        ObjectMapper requestMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        try {
            if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DATALAKE_AGNTSLF))) {
                log.info("inside else condition of setdata feature flag  for transactionId{}",transactionId);
                String token = Utility.getNewOauthAccessToken(oAuthUrl,oAuthClientId,oAuthClientSecret,dataLakeApiId,dataLakeApiKey,transactionId);
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(AppConstants.AGNTSLF_X_APIID, dataLakeApiId);
                headers.add(AppConstants.AGNTSLF_X_APIKEY,dataLakeApiKey );
                headers.add(AppConstants.AGNTSLF_X_APPID, FULFILLMENT);
                headers.add(AppConstants.AUTH, AppConstants.BEARER + token);
                header.setSoaAppId(FULFILLMENT);
                header.setSoaCorrelationId(correlationId);
            }else{
                String token = getAgentSelfAccessToken(oauthTokenUrl, authClientId, authClientSecret, authTokenUsername, authTokenPassword, oauthTokenRepo);
                header.setSoaAppId("MPRO");
                header.setSoaCorrelationId("Test_AgentSelf");
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(AppConstants.AGNTSLF_X_APIID, agntSlfEncryptApiId);
                headers.add(AppConstants.AGNTSLF_X_APIKEY, agntSlfEncryptApiKey);
                headers.add(AppConstants.AGNTSLF_X_APPID, "MPRO");
                headers.add(AppConstants.AUTH, AppConstants.BEARER + token);
            }
            payload.setDbMatchType("1");
            payload.setPanNumber(inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPanNumber());
            payload.setPhoneNumber(inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getPhoneNumber());
            payload.setEmail(inputRequest.getRequest().getRequestData().getAgentSelfRequestPayload().getEmail());
            request.setPayload(payload);
            request.setHeader(header);
            agentSelfInputRequest.setRequest(request);
            if(Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DATALAKE_AGNTSLF))) {
                httpEntity = new HttpEntity<>(agentSelfInputRequest, headers);
                String inputRequestToPrint = requestMapper.writeValueAsString(agentSelfInputRequest);
                log.info("Header {}, Headers {} input req {} transactionId {}", header, headers, agentSelfInputRequest, transactionId);
                log.info("Data set for agent self inputRequest is {} {}", inputRequestToPrint, transactionId);
                return httpEntity;
            }else {
                //FUL2-610608 agentCheck Encryption
                String encryptedRequest = encryptData(agentSelfInputRequest);
                encryptRequest.setPayload(encryptedRequest);
                agentSelfEncryptInputRequest.setRequest(encryptRequest);
                httpEntityWithEncryption = new HttpEntity<>(agentSelfEncryptInputRequest, headers);
                String inputRequestToPrint = requestMapper.writeValueAsString(agentSelfEncryptInputRequest);
                log.info("Encrypted Header {}, Headers {} input req {} transactionId {}", header, headers, agentSelfEncryptInputRequest, transactionId);
                log.info("Encrypted Data set for agent self inputRequest is {} {}", inputRequestToPrint, transactionId);
                return httpEntityWithEncryption;
            }

        } catch (Exception e) {
            log.info("Exception occurred during setDataForAgentSelf {}",Utility.getExceptionAsString(e));
        }
        return httpEntity;
    }

    public ResponseEntity<InputResponse>executeAgentSelfCheck(com.mli.mpro.agentSelf.InputRequest agentSelfInputRequest,com.mli.mpro.agentSelf.Encryption.InputRequest agentSelfEncryptInputRequest ,InputRequest inputRequest, String transactionId, AgentSelfResponsePayload agentSelfResponsePayload) throws UserHandledException {
        ResponseEntity<InputResponse> response = null;
        log.info("inside executeagentSelfCheck for transactionId{}",transactionId);
        try {
            log.info("inside try block of executeAgentSelfCheck for transactionId{}",transactionId);
            long requestedTime = System.currentTimeMillis();
            URI agentselfurl=Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DATALAKE_AGNTSLF))
                                ?new URI(datalakeurl)
                                :new URI(agntSlfEncryptUrl);
            log.info("URI value  for transactionId{} is {}",transactionId,agentselfurl);
            HttpEntity<? extends Object> httpEntity = setDataForAgentSelf(inputRequest, agentSelfInputRequest,agentSelfEncryptInputRequest, transactionId);
            log.info("The request sent to  agentSelf service {} for transaction Id{}", httpEntity, transactionId);

            response= Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.DATALAKE_AGNTSLF))
                        ? new RestTemplate().postForEntity(agentselfurl, httpEntity, InputResponse.class)
                        : getAgentSelfResponse(agentselfurl, httpEntity, transactionId);
            long processedTime = (System.currentTimeMillis() - requestedTime);
            log.info("The response received from agentSelf service for transactionId {} {} httpEntity - {}", transactionId, response, httpEntity);
            log.info("AgentSelf API for transactionId {} took {} milliseconds with input request {} and response {} for url {}", transactionId, processedTime, agentSelfInputRequest, response, agntSlfUrl);
            AuditingDetails auditDetails = getAuditingDetails(inputRequest, transactionId);
            ResponseObject respoObject = new ResponseObject();
            respoObject.setAdditionalProperty(AppConstants.RESPONSE, response);
            auditDetails.setResponseObject(respoObject);
            auditDetails.setRequestId(transactionId);
            String requestId = UUID.randomUUID().toString();
            agentSelfResponsePayload.setAuditRequestId(requestId);
            auditService.saveAuditTransactionDetailsForAgentSelf(auditDetails, requestId);
        } catch (Exception ex) {
            log.info("Error in agentSelf API call for transactionId {} {}", transactionId,ex.toString());
            ex.printStackTrace();
        }
        log.info("Response received from agentself API is {} for transaction Id {} ", response, transactionId);
        return response;

    }

    private String setTimeStamp() {
        String formattedDateTime = "";
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            formatter.setTimeZone(TimeZone.getTimeZone("IST"));
            formattedDateTime = formatter.format(today);
            log.info("timeStamp for agentSelf is {}", formattedDateTime);
        } catch (Exception e) {
            log.info("Exception occurred while calling setTimeStamp {}",Utility.getExceptionAsString(e));
        }
        return formattedDateTime;
    }

    public ResponseEntity<com.mli.mpro.agentSelf.Encryption.InputResponse> getMockResponse() throws JSONException {

        AgentCheck a1 = new AgentCheck();
        a1.setAgentId("100834");
        a1.setAgentStatus("ACTIVE");
        a1.setAgentRole("AGT");
        a1.setFirstName("ALPHA");
        a1.setLastName("Gabriel");
        a1.setPanNumber("APJPS2081P");
        a1.setMobileNumber("9841802049");
        a1.setEmailId("sudhakargabriel@gmail.com");
        a1.setDob("1970-11-20 00:00:00.0");
        a1.setDesignation("Agent");
        a1.setAmlTrainingDate("2023-09-13 00:00:00.0");
        a1.setUlipTrainingDate("2023-09-13 00:00:00.0");
        com.mli.mpro.agentSelf.Encryption.InputResponse ir = new com.mli.mpro.agentSelf.Encryption.InputResponse();
        com.mli.mpro.agentSelf.Encryption.Response response = new com.mli.mpro.agentSelf.Encryption.Response();
        ResponseEntity<com.mli.mpro.agentSelf.Encryption.InputResponse> ri =null;

       /* Header h = new Header();
        h.setSoaAppId("mpro_aws");
        h.setSoaCorrelationId("Test_AgentSelf");
        response.setHeader(h);

        MsgInfo mi = new MsgInfo();
        mi.setMsg("Success");
        mi.setMsgCode("200");
        mi.setMsgDescription("Success");
        response.setMsgInfo(mi);*/

       /* ArrayList<AgentCheck> agentCheck = new ArrayList<>();
        agentCheck.add(a1);
        com.mli.mpro.agentSelf.ResponsePayload pl = new com.mli.mpro.agentSelf.ResponsePayload();
        pl.setAgentExistance("YES");
        pl.setPanMatched("Y");
        pl.setAgentCheck(agentCheck);*/
        response.setPayload("HdklprmTuZCWzk1feOqyU1R18rOY9SSAgTkaj/1JdZKV7tx/I6Zn3JrNxHxCJYvczLb0kqlQsOrl5ueL0bVidOM1BuIxflZAjzPAuPyCiNH9NEziCvvy40rK9x1x5olaoTXBezrd3EWR5mgDguud9SF6Mehdap3qbmTklWfsRGuIhAM06+hUvT4VAOFY/mX1IuGu/WuagVfp/WzMeAiefjembhkoZbxPcepaGqHwn243JB5huJL2vJGM9dKWBHaTRwRsXQ0jDGa00t4JXDLcqVuvdhNlB0hTB3AkxxQoF3yTlMXey6gNLwk7VnaPNrOFYpilbVmxQrMP9xHSYVqD+eJdFQeOBTjhhog3bqD+RMful1/R1bEowEjx1D6dvAoOwyiy4J6IvGc++E6OaJJRwdGOLs6fOlYsf/cWs1gZOwT35pK/NCvnAUpkEUtkm9dVT9iZeyaSpVYtgcTBZZEZUHFheOn9hPskKVCeaKNcnDJdMSrgrfkP3DVkWeFVqCJz/c4r6Rcwrl7r7ofpEZqDEfZt3b+hqqNHvCeCWxXdD8vYd0Ph/F72tJKxuie4WhYcill+f+HauUAbJeOj2U4UgzsLLZQsWOejmW2VjdBgqSNR/YYW2rrbnTBKnV1sV18XbvfU+PS66w752+iveqclArYcEpwSQZkFa2lJj3pi2yl3lp5VLpa7yZS9F2NpushTulvALw8QkB+cImwgamM+U+EyirvuhZUL2qpiOp/qhaIUGV4YoIRbNXKECL8Sw+L2AMG5erROMnIkZFTXQZoZSkfox2D3chjDjE6QBtOXlJMCyT8hoZ8LjuOUrNoN9QIf51IDXpe8me3tm0dmXBPeLFCocfPaYwLmqkuT7eBkv8yCInEkXIh0MsSC3lHZw955LVueonsL4fKCoBynIpjbDBJinggMGBImAzPCBLMaTCMJzh83Dzymv8KxFWcQwh/0G1QiEfdG5c/7II7t6KLOXNAKzi5OtQ/l5BTca/A60ZUHMv2RTZ+ltXPLWFzEO2mpHDBNJ7+kPVTBvu9jIco+aeVc0AvwfiiQZu/rYzzw8wl6arZLrtK74YkGkb6VttSVrP6Lw0JSUm9tFEnoFHKztT7YeStDMBQxFH9qQ9Rafe5RGam5fQCSOf4Ef/zQGVkCKI3hF6oDwYHWOssNjUTRpBzvuj1YDt3sWUS2RVb8VAK25ogVI+dPlQ4TNrx8or/+Kz2BsnSujkq6tgERa8OeKgno6nyRJRfFV08dTnXhDcdMTWp86NeulWcEox+O4NF8jYsbNtOxnkpptojbgyPruG4gDHxZJXaZvMs9cjhvyvTj/wq/4ZD7Y82HznqrT4Go+gJ+bZ8QdoUk64AyoLuFbdAqM8Fnax0uxMsbQ5zXmnRtp8NjWOXdGuJwoIDuLU83jpLfthBP0emKoHPB3MfUJ9U5AZyOYtRugTcbbGZdVqfu9qcqaEMdEWMk5rghcZN0rg==");
        ir.setResponse(response);

       ResponseEntity<com.mli.mpro.agentSelf.Encryption.InputResponse> mer=ResponseEntity.status(200).body(ir);
       return mer;
    }

    private ArrayList<AgentCheck> dateComparisonList(ArrayList<AgentCheck> agentCheck) {
        try {
            ArrayList<AgentCheck> agentCheckWithDate = new ArrayList<>();
            for (AgentCheck aCheck : agentCheck) {
                AgentCheck nAgentCheckObject = aCheck;
                log.info("SOA reponse : getAmlTrainingDate() - {}", aCheck.getAmlTrainingDate());
                log.info("SOA reponse : getUlipTrainingDate() - {}", aCheck.getUlipTrainingDate());
                log.info("SOA reponse : getDob() - {}", aCheck.getDob());
                if (StringUtils.hasLength(aCheck.getAmlTrainingDate() )) {
                    nAgentCheckObject.setAmlTrainingDateInternal(getConvertedDate(aCheck.getAmlTrainingDate()));
                }
                if (StringUtils.hasLength(aCheck.getUlipTrainingDate() )) {
                    nAgentCheckObject.setUlipTrainingDateInternal(getConvertedDate(aCheck.getUlipTrainingDate()));
                }
                if (StringUtils.hasLength(aCheck.getDob() )) {
                    nAgentCheckObject.setDobInternal(getConvertedDate(aCheck.getDob()));
                }
                log.info("Coverted date : getAmlTrainingDateInternal() - {}", nAgentCheckObject.getAmlTrainingDateInternal());
                log.info("Coverted date : getUlipTrainingDateInternal() - {}", nAgentCheckObject.getUlipTrainingDateInternal());
                log.info("Coverted date : getDobInternal() - {}", nAgentCheckObject.getDobInternal());
                agentCheckWithDate.add(nAgentCheckObject);
            }

            return agentCheckWithDate;
        } catch (Exception e) {
            log.info("error in making agentCheckDateList {}", Utility.getExceptionAsString(e));
        }
        return (ArrayList<AgentCheck>) Collections.EMPTY_LIST;
    }

    private List<MaxlifeEmployeeDetails> dateComparisonEmployeeList(List<MaxlifeEmployeeDetails> employeeCheck) {
        try {
            List<MaxlifeEmployeeDetails> agentCheckWithDate = new ArrayList<>();
            for (MaxlifeEmployeeDetails eCheck : employeeCheck) {

                MaxlifeEmployeeDetails nEmployeeCheckObject = eCheck;
                log.info("SOA reponse : getDob() - {}", eCheck.getDob());
                if (eCheck.getDob() != null) {
                    nEmployeeCheckObject.setDobInternal(getConvertedDate(eCheck.getDob()));
                }
                log.info("Coverted date : getDobInternal() - {}", nEmployeeCheckObject.getDobInternal());
                agentCheckWithDate.add(nEmployeeCheckObject);
            }
            return agentCheckWithDate;
        } catch (Exception e) {
            log.info("error in making agentCheckDateList {}", Utility.getExceptionAsString(e));
        }
        return Collections.emptyList();
    }

    private Date getConvertedDate(String date) {
        String DobWithBackslash = date.replace('-', '/');
        Date finalDate = formatAndParseDate(DobWithBackslash);
        if (null != finalDate)
            return finalDate;
        else
            return null;
    }

    public Date formatAndParseDate(String date) {
        try {
            SimpleDateFormat dateMonthYearFormat = null;
            SimpleDateFormat yearMonthDateFormat = null;
            String modifiedDate = "";
            if (date.length() > 10) {
                modifiedDate = date.substring(0, 10);
            } else {
                modifiedDate = date;
            }

            List<SimpleDateFormat> dateFormatSelectorList = dateFormatSelector(modifiedDate, dateMonthYearFormat, yearMonthDateFormat);

            Date unformattedDate = dateFormatSelectorList.get(0).parse(modifiedDate);

            String stringFinalDate = dateFormatSelectorList.get(1).format(unformattedDate);
            Date finalDate = dateFormatSelectorList.get(1).parse(stringFinalDate);

            return finalDate;
        } catch (Exception e) {
            log.info("Exception occured while formatting and parsing date {}", Utility.getExceptionAsString(e));
        }
        return null;
    }

    private List<SimpleDateFormat> dateFormatSelector(String modifiedDate, SimpleDateFormat dateMonthYearFormat, SimpleDateFormat yearMonthDateFormat) {
        List<SimpleDateFormat> dateFormat = new ArrayList<>();
        if (modifiedDate.indexOf('/') == 4) {
            yearMonthDateFormat = new SimpleDateFormat(AppConstants.YEAR_MONTH_DATE);
            dateMonthYearFormat = new SimpleDateFormat(AppConstants.DATE_MONTH_YEAR);
        } else if (modifiedDate.indexOf('/') == 2) {
            yearMonthDateFormat = new SimpleDateFormat(AppConstants.DATE_MONTH_YEAR);
            dateMonthYearFormat = new SimpleDateFormat(AppConstants.DATE_MONTH_YEAR);
        }
        dateFormat.add(yearMonthDateFormat);
        dateFormat.add(dateMonthYearFormat);
        return dateFormat;
    }

    //FUL2-133671
    public AgentSelfResponse updateAgentEmpFraudflagSkip(RequestPayload request) {
        AgentSelfResponse response = new AgentSelfResponse();
        com.mli.mpro.agentSelfIdentifiedSkip.Header header = new com.mli.mpro.agentSelfIdentifiedSkip.Header();
        com.mli.mpro.agentSelfIdentifiedSkip.Payload payload = new com.mli.mpro.agentSelfIdentifiedSkip.Payload();
        AgentSelfResponseData responseData = new AgentSelfResponseData();
        //To Find and modify agentSelfIdentifiedSkip Tags
        Update update = new Update();
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        ArrayList<String> transactionIds = new ArrayList<>();
        ArrayList<String> policyNos = new ArrayList<>();
        try {
            findAndUpdatePolicyTags(request, update, options, transactionIds, policyNos);
            header.setAppId("MPRO");
            header.setCorrelationId("Test_updateAgentEmployeeFraudSkipflag");
            response.setHeader(header);
            payload.setPolicyNumbers(policyNos);
            payload.setTransactionId(transactionIds);
            responseData.setPayload(payload);
            response.setResponseData(responseData);
        } catch (Exception e) {
            log.info("error setting the Tags in agentSelfIdentifiedSkip object {}", Utility.getExceptionAsString(e));
        }
        return response;
    }

    private void findAndUpdatePolicyTags(RequestPayload request, Update update, FindAndModifyOptions options, ArrayList<String> transactionIds, ArrayList<String> policyNumbers) {
        List<Policies> policies = request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().getPolicies();
        String transactionId = null;
        for (Policies policy : policies) {
            Query query = new Query();
            transactionId = policy.getTransactionId();
            String agentCheck = policy.getAgentFraudCheckSkip();
            String agentCheckRemark = request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().getRemark();
            String userId = request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().getUpdateduserId();
            update.set("additionalFlags.agentFraudCheckDetails.agentFraudCheckSkip", agentCheck);
            update.set("additionalFlags.agentFraudCheckDetails.updatedByUserId", userId);
            update.set("additionalFlags.agentFraudCheckDetails.remark", agentCheckRemark);
            update.set("additionalFlags.agentFraudCheckDetails.updatedTime", setTimeStamp());
            findByTransactionIdOrPolicyNumber(transactionId, policy, query);
            mongoOperation.findAndModify(query, update, options, ProposalDetails.class);
            transactionIds.add(policy.transactionId);
            policyNumbers.add(policy.policyNumber);
        }
    }

    private static void findByTransactionIdOrPolicyNumber(String transactionId, Policies p, Query query) {
        String policyNumber;
        if (null!=transactionId && !p.getTransactionId().isEmpty()) {
            query.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(transactionId)));
        } else {
            policyNumber = p.getPolicyNumber();
            query.addCriteria(Criteria.where("applicationDetails.policyNumber").is(policyNumber));
        }
    }

    /**
     * This method makes a call
     * to Auditing service to save the
     * request in Auditing DB
     *
     * @param inputRequest
     * @param transactionId
     * @return
     */
    private AuditingDetails getAuditingDetails(InputRequest inputRequest, String transactionId) {
        AuditingDetails auditDetails = new AuditingDetails();
        auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest);
        auditDetails.setTransactionId(Long.parseLong(transactionId));
        auditDetails.setServiceName(AppConstants.AGENT_SELF);
        auditDetails.setAuditId(transactionId);
        auditDetails.setRequestId(transactionId);
        return auditDetails;
    }


    //FUL2-133670

    /**
     * This Method is used to get
     * the customer details for the
     * given transaction ID or policy number
     *
     * @param request
     * @return
     * @throws Exception
     */
    public ResponsePayload getAgentSelfIdentifiedSkip(RequestPayload request) throws UserHandledException {
       ResponseData responseData = new ResponseData();
        ResponsePayload response = new ResponsePayload();
        AgentSelfIdentifiedSkipPayload payload = new AgentSelfIdentifiedSkipPayload();
        List<PolicyInfo> policies = new ArrayList<>();
        AgentSelfIdentifiedSkipResponse apiResponse = new AgentSelfIdentifiedSkipResponse();
        com.mli.mpro.agentSelfIdentifiedSkip.Header header = new com.mli.mpro.agentSelfIdentifiedSkip.Header();
        List<String> searchList = new ArrayList<>(),transactionIds = null,policyNos = null;
        transactionIds = request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().getTransactionId();
        getTransactionIdsOrPolicyNumber(request, searchList, transactionIds);

        for (String inputParameter : searchList) {
            PolicyInfo policy = new PolicyInfo();
            AgentFraudCheckDetails agentFraudCheckDetails = new AgentFraudCheckDetails();
            ProposalDetails proposalDetails = getProposalDetails(transactionIds, inputParameter);
            ResponsePayload response1 = checkProposalDetailsValue(response, proposalDetails);
            if (response1 != null) return response1;
            Long transactionId = proposalDetails.getTransactionId();
            log.info("Input request received for getAgentSelfIdentifiedSkip is {} for transaction Id {}", request, inputParameter);

            if (proposalDetails != null) {

                setAgentId(proposalDetails, payload);

                //Fetching details for Policy details
                setPolicyDetails( proposalDetails, inputParameter, policy);

                // Agent Fraud Check Details
                setAgentFraudCheckDetails(proposalDetails, agentFraudCheckDetails, policy);

                //SOA payload from Audit
                setSoaApiRequestAndResponse(proposalDetails,transactionId , agentFraudCheckDetails, policy);

                policies.add(policy);


            }
        }
        header.setAppId("MPRO");
        header.setCorrelationId("Test_getAgentSelfPolicyData");
        payload.setPolicies(policies);
        responseData.setPayload(payload);
        apiResponse.setResponseData(responseData);
        apiResponse.setHeader(header);
        response.setResponse(apiResponse);
        log.info("Response received for getAgentSelfIdentifiedSkip: {}", response);
        return response;
    }

    private static void setAgentId(ProposalDetails proposalDetails, AgentSelfIdentifiedSkipPayload payload) {
        try {
            String agentId = null;
            if (proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(CHANNEL_AXIS)) {
                agentId = Optional.of(proposalDetails).map(ProposalDetails::getSourcingDetails).map(SourcingDetails::getSpecifiedPersonDetails).map(SpeicifiedPersonDetails::getSpAgentId).orElse(null);
                if (nonNull(agentId)) {
                    payload.setAgentId(agentId);
                }
            } else {
                agentId = Optional.of(proposalDetails).map(ProposalDetails::getSourcingDetails).map(SourcingDetails::getAgentId).orElse(null);
                if (nonNull(agentId)) {
                    payload.setAgentId(agentId);
                }
            }
            if (Y.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getPhysicalJourneyEnabled()) ||
                    Y.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPhysicalAxisCase()) ||
                    YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getPhysicalJourneyEnabled()) ||
                    YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPhysicalAxisCase())&&
                            proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(CHANNEL_AXIS)) {
                log.info("Initiated setAgentId for transaction Id{}", proposalDetails.getTransactionId());
                String auditReqId = proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getAuditRequestId();
                AuditService auditService1 = BeanUtil.getBean(AuditService.class);
                AuditingDetails auditDetails = auditService1.getAuditDetails(String.valueOf(proposalDetails.getTransactionId()), "Agent Self", auditReqId);
                if (nonNull(auditDetails)) {
                    Map<String, Object> requestMap = auditDetails.getAdditionalProperties();
                    Map<String, Object> requestMap1 = (Map<String, Object>) requestMap.get(REQUEST);
                    Map<String, Object> requestMap2 = (Map<String, Object>) requestMap1.get(REQUEST);
                    Map<String, Object> requestMap3 = (Map<String, Object>) requestMap2.get("requestData");
                    Map<String, Object> requestMap4 = (Map<String, Object>) requestMap3.get("agentSelfRequestPayload");
                    payload.setAgentId((String) requestMap4.get("agentId"));
                }
            }
            log.info(AGENTIDLOGS.get("info"), proposalDetails.getTransactionId(), agentId);

        } catch (Exception ex) {
            log.error(AGENTIDLOGS.get("error"), proposalDetails.getTransactionId(), ex.getMessage());
            log.info("Exception in setAgentId: {}", Utility.getExceptionAsString(ex));
        }
    }

    private static ResponsePayload checkProposalDetailsValue(ResponsePayload response, ProposalDetails proposalDetails) {
        if (null== proposalDetails){
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setMsg("Failed");
            msgInfo.setMsgCode("500");
            msgInfo.setMsgDescription("No data records found in database");
            AgentSelfIdentifiedSkipResponse msgInfoResponse = new AgentSelfIdentifiedSkipResponse();
            msgInfoResponse.setMsgInfo(msgInfo);
            response.setResponse(msgInfoResponse);
            return response;
        }
        return null;
    }

    private ProposalDetails getProposalDetails(List<String> transactionIds, String inputParameter) {
        ProposalDetails proposalDetails = null;
        try {
            Query query = new Query();
            if (!transactionIds.isEmpty() && !transactionIds.contains(null) &&
                    !transactionIds.contains("") && !("".equalsIgnoreCase(inputParameter))) {
                query.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(inputParameter)));
            } else {
                query.addCriteria(Criteria.where("applicationDetails.policyNumber").is(inputParameter));
            }
            proposalDetails = mongoTemplate.find(query, ProposalDetails.class).get(0);
        } catch (Exception e) {
            log.info("Exception occurred while calling getProposalDetails {}",Utility.getExceptionAsString(e));
        }
        return proposalDetails;
    }

    private static void getTransactionIdsOrPolicyNumber(RequestPayload request, List<String> searchList, List<String> transactionIds) {
        List<String> policyNos;
        if (!transactionIds.isEmpty() && !transactionIds.contains(null) && !(transactionIds.contains("")
                &&!("".equalsIgnoreCase(transactionIds.toString())))) {
            searchList.addAll(transactionIds);
        } else {
            policyNos = request.getRequest().getRequestData().getRequestPayload().getPolicyDetails().getPolicyNumbers();
            searchList.addAll(policyNos);
        }
    }


    /**
     * This method gets SOA API
     * request response from Auditing
     * DB and sets in response
     *
     *
     * @param proposalDetails
     * @param transactionId
     */
    private void setSoaApiRequestAndResponse( ProposalDetails proposalDetails, Long transactionId, AgentFraudCheckDetails agentFraudCheckDetails, PolicyInfo policy) {
        try {
            log.info("Initiated setSoaApiRequest for transaction Id{}", transactionId);
            String auditReqId = proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getAuditRequestId();
            AuditingDetails auditDetails = auditService.getAuditDetails(transactionId.toString(), "Agent Self", auditReqId);
            Map<String, Object> requestMap = auditDetails.getAdditionalProperties();
            Map<String, Object> requestMap1 = (Map<String, Object>) requestMap.get(REQUEST);
            Map<String, Object> requestMap2 = (Map<String, Object>) requestMap1.get(REQUEST);
            Map<String, Object> requestMap3 = (Map<String, Object>) requestMap2.get("requestData");
            Map<String, Object> requestMap4 = (Map<String, Object>) requestMap3.get("agentSelfRequestPayload");
            AgentSelfApiRequest agentSelfApiRequest = new AgentSelfApiRequest();
            agentSelfApiRequest.setPanNumber((String) requestMap4.get("panNumber"));
            agentSelfApiRequest.setPhoneNumber((String) requestMap4.get("phoneNumber"));
            agentSelfApiRequest.setTransactionId((String) requestMap4.get("transactionId"));
            agentSelfApiRequest.setDedupeResponse((String) requestMap4.get("dedupeResponse"));
            agentSelfApiRequest.setAgentId((String) requestMap4.get("agentId"));
            agentSelfApiRequest.setEmail((String) requestMap4.get("email"));
            agentSelfApiRequest.setDob((String) requestMap4.get("dob"));
            agentSelfApiRequest.setDbtype("1");
            policy.setAgentSelfSoaApiRequest(agentSelfApiRequest);

            log.info("Initiated setSoaApiResponse for transaction Id{}", transactionId);
            AgentSelfSoaApiResponse agentSelfSoaApiResponse = new AgentSelfSoaApiResponse();
            Map<String, Object> responseMap = auditDetails.getResponseObject().getAdditionalProperties();
            if (agentFraudCheckDetails.getSource().equalsIgnoreCase("Employee")) {
                ArrayList<MliEmployeeCheck> response7 = (ArrayList<MliEmployeeCheck>) responseMap.get(AppConstants.RESPONSE);
                agentSelfSoaApiResponse.setMliEmployeeCheck(response7);
               policy.setAgentSelfSoaApiResponse(agentSelfSoaApiResponse);
            } else {
                Map<String, Object> responseMap1 = (Map<String, Object>) responseMap.get(AppConstants.RESPONSE);
                Map<String, Object> responseMap2 = (Map<String, Object>) responseMap1.get("body");
                Map<String, Object> responseMap3 = (Map<String, Object>) responseMap2.get(AppConstants.RESPONSE);
                Map<String, Object> responseMap4 = (Map<String, Object>) responseMap3.get("payload");

                agentSelfSoaApiResponse.setAgentExistance((String) responseMap4.get("agentExistance"));
                agentSelfSoaApiResponse.setEmailMatched((String) responseMap4.get("emailMatched"));
                agentSelfSoaApiResponse.setPanMatched((String) responseMap4.get("panMatched"));
                agentSelfSoaApiResponse.setDbMatchType((String) responseMap4.get("dbMatchType"));
                agentSelfSoaApiResponse.setMliEmployeeExistance((String) responseMap4.get("mliEmployeeExistance"));
                agentSelfSoaApiResponse.setPhoneNumberMatched((String) responseMap4.get("phoneNumberMatched"));
                agentSelfSoaApiResponse.setAgentCheck((ArrayList<AgentCheck>) responseMap4.get("agentCheck"));
               policy.setAgentSelfSoaApiResponse(agentSelfSoaApiResponse);
            }
        } catch (Exception e) {
            log.info("Exception occurred while calling setSoaApiRequestAndResponse {}",Utility.getExceptionAsString(e));
        }
    }

    /**
     * This method sets data for
     * agentFraudCheckDetails in response
     *
     *
     * @param proposalDetails
     * @param agentFraudCheckDetails
     *
     *
     */
    private void setAgentFraudCheckDetails( ProposalDetails proposalDetails, AgentFraudCheckDetails agentFraudCheckDetails, PolicyInfo policy) {
        try {
            log.info("Initiated setAgentFraudCheckDetails for transaction Id{}", proposalDetails.getTransactionId());
            agentFraudCheckDetails.setSource(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getSource());
            agentFraudCheckDetails.setDobMatched(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getDobMatched());
            agentFraudCheckDetails.setEmailMatched(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getEmailMatched());
            agentFraudCheckDetails.setMobileMatched(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getMobileMatched());
            agentFraudCheckDetails.setPanMatched(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getPanMatched());
            agentFraudCheckDetails.setAgentFraudCheckSkip(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getAgentFraudCheckSkip());
            agentFraudCheckDetails.setAgentFraudIdentified(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getAgentFraudIdentified());
            agentFraudCheckDetails.setIsAgentSelf(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getIsAgentSelf());
            agentFraudCheckDetails.setRequestTimestamp(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getRequestTimestamp());
            agentFraudCheckDetails.setResponseTimestamp(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getResponseTimestamp());
            agentFraudCheckDetails.setServiceStatus(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getServiceStatus());
           if(updateAPiTagsNullCheck(proposalDetails)) {
               agentFraudCheckDetails.setUpdatedByUserId(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getUpdatedByUserId());
               agentFraudCheckDetails.setRemark(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getRemark());
               agentFraudCheckDetails.setUpdatedTime(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getUpdatedTime());
           }
            policy.setAgentFraudCheckDetails(agentFraudCheckDetails);
        } catch (Exception e) {
            log.info("Exception occurred while calling setAgentFraudCheckDetails {}",Utility.getExceptionAsString(e));
        }
    }

    /**
     * This method sets policyDetails in
     * response
     *
     * @param proposalDetails
     * @param inputParameter
     */
    private void setPolicyDetails( ProposalDetails proposalDetails, String inputParameter, PolicyInfo policy) {
        try {
            PolicyDetails policyDetails = new PolicyDetails();
            AgentSelfIdentifiedSkipPayload agentSelfIdentifiedSkipPayload = new AgentSelfIdentifiedSkipPayload();
            log.info("Initiated setPolicyDetails for policyNo {} with input request {}", inputParameter, agentSelfIdentifiedSkipPayload);
            PartyInformation proposerInfo
                    = proposalDetails
                    .getPartyInformation()
                    .stream()
                    .filter(partyInfo -> partyInfo.getPartyType().equalsIgnoreCase("Proposer"))
                    .collect(Collectors.toList())
                    .get(0);

            Date date = proposerInfo.getBasicDetails().getDob();
            DateFormat dobDate = new SimpleDateFormat(AppConstants.DATETIMEFORMAT);
            String dob = dobDate.format(date);

            //setting policy Details
            policyDetails.setPolicyNumber(inputParameter);
            policyDetails.setFirstName(proposerInfo.getBasicDetails().getFirstName());
            policyDetails.setLastName(proposerInfo.getBasicDetails().getLastName());
            policyDetails.setDob(dob);
            policyDetails.setMobileNumber(proposerInfo.getPersonalIdentification().getPhone().get(0).getPhoneNumber());
            policyDetails.setEmail(proposerInfo.getPersonalIdentification().getEmail());
            policyDetails.setPanNumber(proposerInfo.getPersonalIdentification().getPanDetails().getPanNumber());
            if(updateAPiTagsNullCheck(proposalDetails)) {
                policyDetails.setRemark(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getRemark());
                policyDetails.setUpdatedTime(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getUpdatedTime());
                policyDetails.setUpdatedByUserId(proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getUpdatedByUserId());
            }
            policy.setPolicydetails(policyDetails);
        } catch (Exception e) {
            log.info("Exception occurred while calling setPolicyDetails {}",Utility.getExceptionAsString(e));
        }
    }
    private static boolean updateAPiTagsNullCheck(ProposalDetails proposalDetails) {
        return null != proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getUpdatedTime() &&
                null != proposalDetails.getAdditionalFlags().getAgentFraudCheckDetails().getUpdatedByUserId();
    }
private String encryptData(Object data) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    if (!ObjectUtils.isEmpty(data)) {
        String dataString = new ObjectMapper().writeValueAsString(data);
        return EncryptionDecryptionOnboardingUtil.encrypt(dataString, com.amazonaws.util.Base64.decode(encryptDecryptKey));
    }
    return null;
}
private ResponseEntity<InputResponse>getAgentSelfResponse(URI agentselfurl,HttpEntity<? extends Object>httpEntity
                                                           ,String transactionId)
        throws JsonProcessingException, GeneralSecurityException, UserHandledException {
    ResponseEntity<com.mli.mpro.agentSelf.Encryption.InputResponse> decryptResponse =null;
    ResponseEntity<InputResponse> inputResponse = null;
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    String responseJson = "";
    decryptResponse = new RestTemplate().postForEntity(agentselfurl, httpEntity, com.mli.mpro.agentSelf.Encryption.InputResponse.class);
    log.info("Encrypted response received for transactionId{} is {}",transactionId,decryptResponse);
    if (decryptResponse != null && decryptResponse.getBody().getResponse()!=null
            && decryptResponse.getBody().getResponse().getPayload()!= null) {
        log.info("Inside if condition to decrypt data for transactionId{} with response{}",transactionId,decryptResponse.getBody().getResponse());
        byte[] decryptedByteResponse = EncryptionDecryptionOnboardingUtil.decrypt(java.util.Base64.getDecoder().decode(decryptResponse.getBody().getResponse().getPayload()), java.util.Base64.getDecoder().decode(encryptDecryptKey));
        responseJson = new String(decryptedByteResponse, StandardCharsets.UTF_8);
        log.info("decrypted response for transactionId{} with response{}",transactionId,responseJson);
    }else{
        List<String> errorMessages = new ArrayList<>();
        log.info("Error occured in decrypting data for transactionId{}",transactionId);
        errorMessages.add("Null reponse received");
        throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    InputResponse decryptedResponse = objectMapper.readValue(responseJson, InputResponse.class);
    if(AppConstants.SUCCESS_RESPONSE.equalsIgnoreCase(decryptedResponse.getResponse().getMsgInfo().getMsgCode())){
        inputResponse=ResponseEntity.status(200).body(decryptedResponse);
    }else{
        List<String> errorMessages = new ArrayList<>();
        log.info("Success response not received for transactionId{}",transactionId);
        errorMessages.add("msg code not 200");
        throw new UserHandledException(new com.mli.mpro.common.models.Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        log.info("Oauth based agentSelf payload response receive successfully for transactionId {} , {} ", transactionId,decryptedResponse);
        return inputResponse;
    }
    public static String getAgentSelfAccessToken(String oauthTokenUrl, String authClientID, String authClientSecret, String authTokenUsername, String authTokenPassword, OauthTokenRepository tokenRepository){
        String accessToken = "";
        OauthTokenResponse oauthTokenResponse = new OauthTokenResponse();
        int expireTime = 0;
        log.info("Called SOA Token Service");
        try {
            RestTemplate restTemplate = new RestTemplate();
            String plainClientCredentials = authClientID + ":" + authClientSecret;
            String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
            HttpHeaders headers = new HttpHeaders();
            headers.add(AppConstants.AUTH, "Basic " + base64ClientCredentials);
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

            LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("grant_type", "client_credentials");
            multiValueMap.add("scope", "admin/read");
            multiValueMap.add("username", authTokenUsername);
            multiValueMap.add("password", authTokenPassword);

            HttpEntity<?> httpEntity = new HttpEntity<>(multiValueMap, headers);
            oauthTokenResponse = restTemplate.postForObject(oauthTokenUrl, httpEntity, OauthTokenResponse.class);

                if (oauthTokenResponse != null && !org.springframework.util.StringUtils.isEmpty(oauthTokenResponse.getAccess_token())) {
                    accessToken = oauthTokenResponse.getAccess_token();
                    expireTime = oauthTokenResponse.getExpires_in() - 10;
                    Utility.setTokenToRedis(accessToken, expireTime, tokenRepository,redisKey);
                }else
                {
                    List<String> errorMessages = new ArrayList<>();
                    errorMessages.add("Null response received");
                    throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
                }

        } catch (Exception e) {
            log.info("exception occured to generate token");
            log.error(Utility.getExceptionAsString(e));
        }

        return accessToken;
    }

    public AuditingDetails setAuditingDetails(String statusCode, String httpStatusCode, String agentId, Object request,
                                              Object response) {
        AuditingDetails auditDetails = new AuditingDetails();
        auditDetails.setRequestId(UUID.randomUUID().toString());
        auditDetails.setServiceName("Agent Self check service");
        auditDetails.setStatusCode(statusCode);
        auditDetails.setTransactionId(0);
        auditDetails.setHttpStatusCode(httpStatusCode);
        auditDetails.setAdditionalProperty(AppConstants.REQUEST, request);
        auditDetails.setAgentId(agentId);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setAdditionalProperty(AppConstants.RESPONSE, response);
        auditDetails.setResponseObject(responseObject);
        return auditDetails;
    }
}