package com.mli.mpro.location.YblPasa.Service;

import com.mli.mpro.agentSelfIdentifiedSkip.Header;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.configuration.models.*;
import com.mli.mpro.configuration.repository.ConfigurationRepository;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.YblPasaDetails;
import com.mli.mpro.location.YblPasa.Payload.YblPasaResponse;
import com.mli.mpro.location.YblPasa.Payload.YblResponsePayload;
import com.mli.mpro.location.services.ProposalStreamPushService;
import com.mli.mpro.nps.model.MsgInfo;
import com.mli.mpro.pasa.models.HashingUtility;
import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.PASAELIGIBLE_CHANNELLIST;
import static com.mli.mpro.productRestriction.util.AppConstants.SUCCCESSMSG;

@Service
public class YblPasaServiceImpl implements YblPasaService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    AuditService auditService;
    private static final Logger log = LoggerFactory.getLogger(YblPasaServiceImpl.class);

    /**
     * This method is used to
     * fetch YBL pasa Data from yblPasaDetails db
     *
     * @param yblPasaRequest
     * @return
     */
    public YblPasaResponse fetchYblPasaData(InputRequest yblPasaRequest) {
        YblPasaResponse yblPasaResponse = new YblPasaResponse();
        Header header = new Header();
        MsgInfo msgInfo = new MsgInfo();
        String transactionId = yblPasaRequest.getRequest().getRequestData().getPayload().getTransactionId();
        header.setAppId("mPro");
        header.setCorrelationId(transactionId);
        String channel = yblPasaRequest.getRequest().getRequestData().getPayload().getChannel();
        boolean channelFeatureFlag = isChannelFeatureFlagEnabled(channel);
        boolean hashingEnabled = false;

        try {
            if (isFeatureEnabled(AppConstants.ENABLEYBLPASA) && channelFeatureFlag) {
                log.info("PASA feature flag for channel {} is ON", channel);
                ConfigurationRepository configurationRepository = BeanUtil.getBean(ConfigurationRepository.class);
                Configuration configuration = configurationRepository.findByKey("IS_HASHING_ENABLE");

                //uId generation logic
                String custId = yblPasaRequest.getRequest().getRequestData().getPayload().getCustId();
                String panNumber = yblPasaRequest.getRequest().getRequestData().getPayload().getPanNumber();
                String uId = null;
                if (configuration != null) {
                    Map<String, HashingConfig> configurationHashingConfig = configuration.getHashingConfig();
                    if (configurationHashingConfig != null && configurationHashingConfig.containsKey(channel)) {
                        HashingConfig hashingConfig = configurationHashingConfig.get(channel);
                        if (hashingConfig.isHashingEnabled()) {
                            log.info("Hashing is enabled for {}.", channel);

                            //FUL2-214671 : Temporary logic for testing PASA modularity to generate uId hash based on partner channel pattern as capability and approach to finalize part of phase-2
                            String uniqueId = "";
                            if(StringUtils.hasText(custId) && StringUtils.hasText(panNumber)){
                                panNumber = panNumber.substring(0, 7);
                                uniqueId = setYblUniqueId(custId, panNumber, transactionId);
                            } else if(StringUtils.hasText(custId)){
                                uniqueId = custId;
                            } else {
                                uniqueId = panNumber;
                            }

                            uId = HashingUtility.hash(uniqueId, Optional.ofNullable(hashingConfig.getUniqueSalt()), Optional.ofNullable(hashingConfig.getAlgorithm()));
                            hashingEnabled = true;
                            log.info("Getting uId after hashing for transactionId {} is {}", transactionId, uId);
                        }
                    }
                }

                if (!hashingEnabled) {
                    panNumber = Objects.nonNull(panNumber) ? panNumber.substring(0, 7) : AppConstants.BLANK;
                    uId = setYblUniqueId(custId, panNumber, transactionId);
                    log.info("Hashing is disabled for {}. Using uId as-is.", channel);
                }

                //Get Ybl Pasa Details
                if (Objects.nonNull(uId)) {
                    findAndReturnYblPasaCustomerDetails(yblPasaResponse, uId, transactionId, custId, panNumber);
                    msgInfo.setMsg("API call successful");
                    msgInfo.setMsgCode(AppConstants.SUCCESS_RESPONSE);
                    msgInfo.setMsgDescription(SUCCCESSMSG);
                } else {
                    msgInfo.setMsg("UID Not Match in DB");
                    msgInfo.setMsgCode("504");
                    msgInfo.setMsgDescription("No Match found");
                    log.info("No match found in DB for transactionId {}", transactionId);
                    setDefaultValuesForYBLCustomer(yblPasaResponse);
                }
            } else {
                msgInfo.setMsg("Feature Flag is off");
                msgInfo.setMsgCode(AppConstants.SUCCESS_RESPONSE);
                msgInfo.setMsgDescription(SUCCCESSMSG);
                log.info("No match found in DB for transactionId {}", transactionId);
                setDefaultValuesForYBLCustomer(yblPasaResponse);
            }
            yblPasaResponse.setMsgInfo(msgInfo);
            callSaveProposalForYblPasa(transactionId, yblPasaResponse);
            AuditingDetails auditDetails = getAuditingDetails(yblPasaRequest, transactionId);
            ResponseObject respoObject = new ResponseObject();
            respoObject.setAdditionalProperty(AppConstants.RESPONSE, yblPasaResponse.getPayload());
            auditDetails.setResponseObject(respoObject);
            auditDetails.setRequestId(transactionId);
            auditService.saveAuditTransactionDetails(auditDetails);
        } catch (Exception ex) {
            log.error("Exception occurred: {}", Utility.getExceptionAsString(ex));
            MsgInfo msgInfo1 = new MsgInfo("500", Utility.getExceptionAsString(ex), "Internal Server Error");
            yblPasaResponse.setMsgInfo(msgInfo1);
        }
        yblPasaResponse.setHeader(header);
        return yblPasaResponse;
    }

    private boolean isFeatureEnabled(String featureKey) {
        return Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(featureKey));
    }

    private boolean isChannelFeatureFlagEnabled(String channel) {
        if (Objects.isNull(channel) || channel.equalsIgnoreCase(AppConstants.BLANK)) {
            return false;
        }
        ConfigurationRepository configurationRepository = BeanUtil.getBean(ConfigurationRepository.class);
        List<Configuration> configuration = configurationRepository.findByKeyIgnoreCase(PASAELIGIBLE_CHANNELLIST);
        if (!CollectionUtils.isEmpty(configuration)) {
            List<String> channelList = configuration.get(0).getPasaChannelList();
            return (channelList != null && channelList.stream().anyMatch(ch -> channel.equalsIgnoreCase(ch)));
        }
        return false;
    }

    private AuditingDetails getAuditingDetails(InputRequest inputRequest, String transactionId) {
        AuditingDetails auditDetails = new AuditingDetails();
        auditDetails.setAdditionalProperty(AppConstants.REQUEST, inputRequest.getRequest().getRequestData().getPayload());
        auditDetails.setTransactionId(Long.parseLong(transactionId));
        auditDetails.setServiceName("Ybl Pasa");
        auditDetails.setAuditId(transactionId);
        auditDetails.setRequestId(transactionId);
        return auditDetails;
    }
    @Async
    private void callSaveProposalForYblPasa(String transactionId, YblPasaResponse yblPasaResponse) {
        log.info("Calling saveProposal for YBL for transactionId {}",transactionId);
        try {
            //ProposalDetails proposalDetails = proposalRepository.findPasaDetailsByTransactionId(Long.valueOf(transactionId));
            Query query = new Query();
            Update update = new Update();
            query.addCriteria(Criteria.where(AppConstants.TRANSACTIONID).is(Long.valueOf(transactionId)));
            update.set("pasaDetails.isPasaEligible", yblPasaResponse.getPayload().getIsYblPasaEligible().toUpperCase());
            update.set("pasaDetails.isYblPasaEligible",yblPasaResponse.getPayload().getIsYblPasaEligible().toUpperCase());
            mongoOperations.findAndModify(query, update, ProposalDetails.class);
            log.info("proposalDetails  updated from db{}",transactionId);
            log.info("called proposal stream from location MS for Pasa is status {} for transactionId{}",transactionId);
        }catch (Exception ex) {
            log.error("Exception while callsaveProposalForYblPasa {} ", Utility.getExceptionAsString(ex));
        }
    }

    private void findAndReturnYblPasaCustomerDetails(YblPasaResponse yblPasaResponse, String uId, String transactionId, String custId, String panNumber) {
        YblResponsePayload yblResponsePayload = new YblResponsePayload();
        Query query = new Query();
        query.addCriteria(Criteria.where("uniqueId").is(uId));
        List<YblPasaDetails> yblPasaDetails = mongoTemplate.find(query, YblPasaDetails.class);
        log.info("Details found for YBL Pasa Customer for transactionId{} is {}", transactionId, Utility.printJsonRequest(yblPasaDetails));
        if (!CollectionUtils.isEmpty(yblPasaDetails)) {
            yblResponsePayload.setCustId(custId);
            yblResponsePayload.setPanNumber(null != panNumber ? panNumber : AppConstants.BLANK);
            yblResponsePayload.setUniqueId(yblPasaDetails.get(0).getUniqueId());
            yblResponsePayload.setPremium(yblPasaDetails.get(0).getPremium());
            yblResponsePayload.setSumAssured(yblPasaDetails.get(0).getSumAssured());
            yblResponsePayload.setPasaCategory1(yblPasaDetails.get(0).getPasaCategory1());
            yblResponsePayload.setPasaCategory2(yblPasaDetails.get(0).getPasaCategory2());
            yblResponsePayload.setOfferType(yblPasaDetails.get(0).getOfferType());
            yblResponsePayload.setCategoryCode1(yblPasaDetails.get(0).getCategoryCode1());
            yblResponsePayload.setCategoryCode2(yblPasaDetails.get(0).getCategoryCode2());
            yblResponsePayload.setChannel(yblPasaDetails.get(0).getChannel());
            yblResponsePayload.setExpiry(yblPasaDetails.get(0).getExpiry());
            yblResponsePayload.setIsYblPasaEligible(AppConstants.YES);
            yblResponsePayload.setIsYblPasaApplied(AppConstants.NO);
            yblPasaResponse.setPayload(yblResponsePayload);
        } else {
            log.info("No match found in DB for transactionId {}", transactionId);
            setDefaultValuesForYBLCustomer(yblPasaResponse);
        }
        log.info("Final values set for YBL Pasa Customer for transactionId {} is {}", transactionId, Utility.printJsonRequest(yblPasaResponse));
    }

    public void setDefaultValuesForYBLCustomer(YblPasaResponse yblPasaResponse) {
        YblResponsePayload yblResponsePayload = new YblResponsePayload();
        yblResponsePayload.setCustId(AppConstants.BLANK);
        yblResponsePayload.setPanNumber(AppConstants.BLANK);
        yblResponsePayload.setUniqueId(AppConstants.BLANK);
        yblResponsePayload.setSumAssured(AppConstants.BLANK);
        yblResponsePayload.setPasaCategory1(AppConstants.BLANK);
        yblResponsePayload.setPasaCategory2(AppConstants.BLANK);
        yblResponsePayload.setOfferType(AppConstants.BLANK);
        yblResponsePayload.setCategoryCode1(AppConstants.BLANK);
        yblResponsePayload.setCategoryCode2(AppConstants.BLANK);
        yblResponsePayload.setChannel(AppConstants.BLANK);
        yblResponsePayload.setExpiry(AppConstants.BLANK);
        yblResponsePayload.setIsYblPasaEligible(AppConstants.NO);
        yblResponsePayload.setIsYblPasaApplied(AppConstants.NO);
        yblPasaResponse.setPayload(yblResponsePayload);
    }

    /**
     * Here YBL unique ID is created for DB search
     *
     * @param custId
     * @param panNumber
     * @return
     */
    private String setYblUniqueId(String custId, String panNumber, String transactionId) {
        String uId = "";
        int startIndex = 0;
        if (Objects.isNull(panNumber) || panNumber.equalsIgnoreCase(AppConstants.BLANK)) {
            return null;
        }
        if (custId.length() > 4) {
            startIndex = Math.max(0, custId.length() - 4);
            uId = panNumber.concat(custId.substring(startIndex));
        } else {
            String letterA = setNumberOfLetterA(custId);
            uId = panNumber.concat(letterA).concat(custId.substring(1));
        }
        log.info("Unique id set for transactionId {} is {}", transactionId, uId);
        return uId;
    }

    public String setNumberOfLetterA(String custId) {
        String numberOfAToConcatenate = null;
        if (custId.length() == 4) {
            numberOfAToConcatenate = "A";
        } else if (custId.length() == 3) {
            numberOfAToConcatenate = "AA";
        } else if (custId.length() == 2) {
            numberOfAToConcatenate = "AAA";
        }
        return numberOfAToConcatenate;
    }
}
