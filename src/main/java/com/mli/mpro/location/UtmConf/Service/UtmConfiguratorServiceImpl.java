package com.mli.mpro.location.UtmConf.Service;

import com.mli.mpro.location.UtmConf.Payload.UtmConfiguratorResponse;
import com.mli.mpro.location.UtmConf.Payload.UtmConfiguratorResponsePayload;
import com.mli.mpro.location.models.UtmConfiguratorRepository;
import com.mli.mpro.location.UtmConf.models.BrmsInputRequest;
import com.mli.mpro.location.UtmConf.models.MsgInfo;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mli.mpro.productRestriction.util.AppConstants.SUCCCESSMSG;

@Service
public class UtmConfiguratorServiceImpl implements UtmConfiguratorService{
    @Autowired
    MongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(UtmConfiguratorServiceImpl.class);

    public UtmConfiguratorResponse getUtmCode(BrmsInputRequest utmConfiguratorInputRequest){
        UtmConfiguratorResponse utmConfiguratorResponse = new UtmConfiguratorResponse();
        MsgInfo msgInfo = new MsgInfo();

        String agentCode = utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentCode();
        String goCode = utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getGoCode();
        String channel = utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel();
        String journeyType = utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType();
        String agentSegmentation = utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentSegmentation();
        String agentDesignation = utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentDesignation();

        UtmConfiguratorResponsePayload utmConfiguratorResponsePayload = new UtmConfiguratorResponsePayload();

        utmConfiguratorResponsePayload.setAgentCode(agentCode);
        utmConfiguratorResponsePayload.setGoCode(goCode);
        utmConfiguratorResponsePayload.setChannel(channel);
        utmConfiguratorResponsePayload.setJourneyType(journeyType);
        utmConfiguratorResponsePayload.setAgentDesignation(agentDesignation);
        utmConfiguratorResponsePayload.setAgentSegmentation(agentSegmentation);

        Query query = buildQueryWithData(utmConfiguratorInputRequest);
        List<UtmConfiguratorRepository> utmConfiguratorlist = mongoTemplate.find(query, UtmConfiguratorRepository.class);

        if(utmConfiguratorlist.isEmpty()){
            query = buildQueryWithChannelAndJourneyType(utmConfiguratorInputRequest);
            utmConfiguratorlist = mongoTemplate.find(query, UtmConfiguratorRepository.class);
        }

        if (!CollectionUtils.isEmpty(utmConfiguratorlist)) {
            if(goCode==null || goCode.isEmpty()){
                log.info("UtmConfigurator details is {}", Utility.printJsonRequest(utmConfiguratorlist.get(0)));
                msgInfo.setMsg("API call successful");
                msgInfo.setMsgCode(AppConstants.SUCCESS_RESPONSE);
                msgInfo.setMsgDescription(SUCCCESSMSG);
                utmConfiguratorResponse.setMsgInfo(msgInfo);
                utmConfiguratorResponsePayload.setUtmCode(utmConfiguratorlist.get(0).getUtmCode());
                utmConfiguratorResponse.setPayload(utmConfiguratorResponsePayload);
                log.info("Final value for getUtm is : {}", Utility.printJsonRequest(utmConfiguratorResponse));
                return utmConfiguratorResponse;
            }
            for (UtmConfiguratorRepository utmConfigurator : utmConfiguratorlist) {
                if(goCode.startsWith(utmConfigurator.getGoCode().substring(0,utmConfigurator.getGoCode().length()-1))){
                    log.info("UtmConfigurator details is {}", Utility.printJsonRequest(utmConfigurator));
                    msgInfo.setMsg("API call successful");
                    msgInfo.setMsgCode(AppConstants.SUCCESS_RESPONSE);
                    msgInfo.setMsgDescription(SUCCCESSMSG);
                    utmConfiguratorResponse.setMsgInfo(msgInfo);
                    utmConfiguratorResponsePayload.setUtmCode(utmConfigurator.getUtmCode());
                    utmConfiguratorResponse.setPayload(utmConfiguratorResponsePayload);
                    log.info("Final value for getUtm is : {}", Utility.printJsonRequest(utmConfiguratorResponse));
                    return utmConfiguratorResponse;
                }
            }
        }
        msgInfo.setMsg("No Utm Found");
        msgInfo.setMsgCode("504");
        msgInfo.setMsgDescription("No Match found");
        utmConfiguratorResponse.setMsgInfo(msgInfo);
        log.info("No UTM Code found in DB for the given parameters.");
        utmConfiguratorResponsePayload.setUtmCode(AppConstants.BLANK);
        utmConfiguratorResponse.setPayload(utmConfiguratorResponsePayload);
        log.info("Final value for getUtm is : {}", Utility.printJsonRequest(utmConfiguratorResponse));
        return utmConfiguratorResponse;
    }

    private Query buildQueryWithData(BrmsInputRequest utmConfiguratorInputRequest) {
        Query query = new Query();
        if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentCode() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentCode().isEmpty())){
            query.addCriteria(Criteria.where("agentCode").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentCode().trim()));
        }
        if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel().isEmpty())) {
            query.addCriteria(Criteria.where("channel").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel().trim()));
        }
        if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType().isEmpty())) {
            query.addCriteria(Criteria.where("journeyType").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType().trim()));
        }
        //FUL2-214671 : Removed agent segmentation and designation check as data mis-match is happening
        /*if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentSegmentation() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentSegmentation().isEmpty())) {
            query.addCriteria(Criteria.where("agentSegmentation").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentSegmentation().trim()));
        }
        if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentDesignation() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentDesignation().isEmpty())) {
            query.addCriteria(Criteria.where("agentDesignation").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getAgentDesignation().trim()));
        }*/
        return query;
    }

    private Query buildQueryWithChannelAndJourneyType(BrmsInputRequest utmConfiguratorInputRequest) {
        Query query = new Query();
        if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel().isEmpty())) {
            query.addCriteria(Criteria.where("channel").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getChannel().trim()));
        }
        if(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType() != null && (!utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType().isEmpty())) {
            query.addCriteria(Criteria.where("journeyType").is(utmConfiguratorInputRequest.getRequest().getRequestData().getInputRequest().getJourneyType().trim()));
        }
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("agentCode").is(""), Criteria.where("agentCode").is(null), Criteria.where("agentCode").is("NA"), Criteria.where("agentCode").exists(false));
        query.addCriteria(criteria);
        return query;
    }
}
