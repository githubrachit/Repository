package com.mli.mpro.document.mapper;

import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MoralHazardReportMapper {
    private static final Logger logger = LoggerFactory.getLogger(MoralHazardReportMapper.class);

    public Context setDataForMHRDocument(ProposalDetails proposalDetails) {
        logger.info("START Proposer MHR Document Data population for transactionId {}", proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        SellerDeclaration sellerDeclaration = proposalDetails.getSellerDeclaration();
        SourcingDetails sourcingDetails = proposalDetails.getSourcingDetails();

        AddressDetails addressDetails = (proposalDetails.getPartyInformation() != null
                && proposalDetails.getPartyInformation().get(0).getBasicDetails() !=null
                && proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress() != null)
                ? proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails(): null;

        String proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
        List<PartyInformation> proposalPartyInformationList = proposalDetails.getPartyInformation();

        setUpDataVariables(dataVariables, sellerDeclaration, sourcingDetails, addressDetails, proposalPartyInformationList,proposalDetails);
        dataVariables.put(AppConstants.CB_FIELD_PROPOSAL_NUMBER, proposalNumber);
        dataVariables.put("isAxisOrThanosChannel",(AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) || AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())));
        Context mhrContext = new Context();
        mhrContext.setVariables(dataVariables);
        return mhrContext;

    }

    private String getCurrentTimeStamp(){
        ZoneId zoneid = ZoneId.of(AppConstants.APP_TIMEZONE);
        LocalDateTime currTime = LocalDateTime.now(zoneid);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d'" + Utility.getLastDigitSufix(currTime.getDayOfMonth()) + "' yyyy',' hh:mm:ss a");
        return dtf.format(currTime);
    }

    private void setUpDataVariables(Map<String, Object> dataVariables, SellerDeclaration sellerDeclaration,
                                    SourcingDetails sourcingDetails, AddressDetails addressDetails, List<PartyInformation> proposalPartyInformationList,ProposalDetails proposalDetails){

        SellerConsentDetails sellerConsentDetails = proposalDetails.getSellerConsentDetails();

        setCustomerNumber(dataVariables, proposalPartyInformationList);

        setSourcingDetails(dataVariables, sourcingDetails, addressDetails, proposalDetails,
            sellerConsentDetails);

        setMHRQuestionsInDoc(sellerDeclaration, dataVariables);

    }

    private void setMHRQuestionsInDoc(SellerDeclaration sellerDeclaration, Map<String, Object> dataVariables){
        if(AppConstants.YES.equalsIgnoreCase(sellerDeclaration.getSellerDisclosure())){
            List<SellerQuestion> sellerQuestions = sellerDeclaration.getMhrSellerQuestions();
            sellerQuestions.stream().forEach(sellerQuestion -> {
                dataVariables.put("ans"+sellerQuestion.getQuestionId().toUpperCase(), capitalizedAns(sellerQuestion.getAnswer()));
                if(sellerQuestion.getQuestionId().equalsIgnoreCase("Q5") || sellerQuestion.getQuestionId().equalsIgnoreCase("Q7")|| sellerQuestion.getQuestionId().equalsIgnoreCase("Q3")|| sellerQuestion.getQuestionId().equalsIgnoreCase("Q4")){
                    dataVariables.put("ans"+sellerQuestion.getQuestionId()+"additionalInformation", sellerQuestion.getAdditionalInformation());
                    if(sellerQuestion.getAnswer().equalsIgnoreCase(AppConstants.YES)){
                        dataVariables.put(sellerQuestion.getQuestionId() + AppConstants.ANSWER_AS_YES, true);
                    } else {
                        dataVariables.put(sellerQuestion.getQuestionId() + AppConstants.ANSWER_AS_YES, false);
                    }
                }
            });
            dataVariables.put(AppConstants.SELLER_CONFIRMATION, sellerDeclaration.isSellerConfirmation());
        }
    }

    private void setSourcingDetails(Map<String, Object> dataVariables, SourcingDetails sourcingDetails,
                                    AddressDetails addressDetails, ProposalDetails proposalDetails,
                                    SellerConsentDetails sellerConsentDetails) {
        logger.info("Adding seller Details into MHR document for policyNo : {}", proposalDetails.getApplicationDetails().getPolicyNumber());
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.MMM_DD_YYYY_HH_MM_SS_HYPHEN_A);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sellerConsentDetails.getLastModifiedDate());
        cal.add(Calendar.HOUR, 5);
        cal.add(Calendar.MINUTE,30);
        if (Objects.nonNull(sourcingDetails)) {
            dataVariables.put(AppConstants.SP_AGENT_NAME, sellerConsentDetails.getSellerName());
            dataVariables.put(AppConstants.SP_AGENT_CODE, sellerConsentDetails.getSpCode());
            dataVariables.put(AppConstants.CURRENT_DATE, dateFormat.format(cal.getTime()));
            dataVariables.put(AppConstants.PLACE, Objects.isNull(sellerConsentDetails.getAgentPlace()) ? "" : sellerConsentDetails.getAgentPlace());
            boolean isThanosSellerDisclosed = !sellerConsentDetails.getSellerDisclosure() && AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
            dataVariables.put(AppConstants.SELLER_DISCLOSURE_THANOS, isThanosSellerDisclosed);
          if(AppConstants.REQUEST_SOURCE_THANOS2.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
                  || AppConstants.MPRO_CHANNELS.contains(proposalDetails.getChannelDetails().getChannel())){
            dataVariables.put(AppConstants.PLACE,
                sellerConsentDetails.getAgentPlace() == null ||  "null".equalsIgnoreCase(sellerConsentDetails.getAgentPlace())? "" : sellerConsentDetails.getAgentPlace());
          }else {
            dataVariables.put(AppConstants.PLACE,
                addressDetails != null ? addressDetails.getCity() : "");
          }
        }
    }

    private void setCustomerNumber(Map<String, Object> dataVariables,
        List<PartyInformation> proposalPartyInformationList) {
        if (!CollectionUtils.isEmpty(proposalPartyInformationList)) {
            logger.info("Proposer information present, processing...");
            BasicDetails basicDetails = proposalPartyInformationList.get(0).getBasicDetails();
            String firstName = basicDetails.getFirstName();
            String lastName = basicDetails.getLastName();
            dataVariables.put(AppConstants.CUSTOMER_NUMBER, firstName + " " + lastName);
        }
    }

    private String capitalizedAns(String ans){
        if(ans==null || ans==""){
            return "";
        }
        return ans.substring(0,1).toUpperCase() + ans.substring(1);
    }
}
