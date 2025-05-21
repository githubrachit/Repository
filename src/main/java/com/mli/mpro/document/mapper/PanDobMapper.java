package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProductInfo;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Service
public class PanDobMapper {

    public static final String NOT_APPLICABLE = "Not Applicable";
    private static final Logger logger = LoggerFactory.getLogger(PanDobMapper.class);
    public static final String DATA_ADDITION_FAILED = "Data addition failed";

    ZoneId zoneid = ZoneId.of(AppConstants.APP_TIMEZONE);
    LocalDateTime currTime = LocalDateTime.now(zoneid);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d'" + Utility.getLastDigitSufix(currTime.getDayOfMonth()) + "' yyyy',' hh:mm:ss a");
    final String timeStamp = dtf.format(currTime);

    /**
     * Proposer PANDOB mapping
     *
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfPanDobDocument(ProposalDetails proposalDetails, String partyType) throws UserHandledException {
        logger.info("START Proposer PANDOB Data population for transactionId {}", proposalDetails.getTransactionId());

        Map<String, Object> dataVariables = new HashMap<>();

        String panName = "";
        String creditScore = "";
        String dobFlag = AppConstants.NOT_VALIDATED;
        String dob = "";
        String incomeRange = "";
        String panNumber = "";
        String panValidated = AppConstants.NOT_VALIDATED;
        String proposalNumber = "";
        String responseFrom = "";
        String trlScore = "";
        boolean isNeoOrAggregator;
        String panAadharLinkStatus="";


        ProductInfo productInfo =(proposalDetails != null && !proposalDetails.getProductDetails().isEmpty() &&
                proposalDetails.getProductDetails().get(0) != null && proposalDetails.getProductDetails().get(0).getProductInfo() != null )
                ? proposalDetails.getProductDetails().get(0).getProductInfo() : null ;
        //FUL2-17569 GLIP And SPP Product : Pan DOB Document Generation
        boolean isAnnuityProductJointLife = Utility.isAnnuityOptionJointLife(proposalDetails, Stream.of(AppConstants.GLIP_PRODUCT_ID,AppConstants.SPP_ID,AppConstants.SWAGPP));
        try {
            isNeoOrAggregator = Objects.nonNull(proposalDetails.getChannelDetails())
                    && (AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                    || AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()));

            proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
    		//FUL2-46310 
			proposalNumber = Utility.getSecondaryPolicyNumber(proposalDetails, proposalNumber);
			proposalNumber = Utility.getPrimaryPolicyNumber(proposalDetails, proposalNumber);
            List<PartyInformation> proposalPartyInformationList = proposalDetails.getPartyInformation();
            if (!CollectionUtils.isEmpty(proposalPartyInformationList)) {
                logger.info("Proposer information present processing for transactionId {} partyType {} formType {}",
                        proposalDetails.getTransactionId(),partyType,Utility.getFormType(proposalDetails));
                PartyInformation partyInformation = proposalPartyInformationList.stream().filter(partyInfo -> partyInfo.getPartyType().equalsIgnoreCase(partyType))
                        .findFirst().orElse(null);
                BasicDetails basicDetails =  partyInformation.getBasicDetails();
                String firstName = basicDetails.getFirstName();
                String middleName = Utility.emptyIfNull(basicDetails.getMiddleName());
                String lastName = basicDetails.getLastName();
                panName = String.join(" ", firstName, middleName, lastName);

                if (isAnnuityProductJointLife && INSURED.equalsIgnoreCase(partyType)) {
                    panName = productInfo.getSecondAnnuitantName() != null ? productInfo.getSecondAnnuitantName() : Strings.EMPTY;
                  //FUL2-17569 GLIP And SPP Product : Pan DOB Document Generation
                    dob = Utility.stringAnnuityFormDateFormatter(productInfo.getSecondAnnuitantDob() != null ? productInfo.getSecondAnnuitantDob() :
                            Strings.EMPTY,proposalDetails.getChannelDetails().getChannel());
                    dobFlag = productInfo.isSecondAnnuitantDobValidated() ? AppConstants.VALIDATED : AppConstants.NOT_VALIDATED;
                    panNumber = productInfo.getSecondAnnuitantPanNumber();
                    panValidated = productInfo.isSecondAnnuitantPanValidated() ? AppConstants.VALIDATED : AppConstants.NOT_VALIDATED;
                    panAadharLinkStatus = StringUtils.isEmpty(panNumber) ? NOT_APPLICABLE :productInfo.getSecondAnnuitantPanAadhaarStatus();
                } else {
                    dob = Utility.dateFormatter(basicDetails.getDob());
                    dobFlag = partyInformation.getPersonalIdentification().getPanDetails().isDobValidated() ? AppConstants.VALIDATED : AppConstants.NOT_VALIDATED;
                    panNumber = partyInformation.getPersonalIdentification().getPanDetails().getPanNumber();
                    if (isNeoOrAggregator){
                        panValidated = partyInformation.getPersonalIdentification().getPanDetails().isPanValidated() ? AppConstants.VALIDATED : BLANK;
                    } else {
                        panValidated = partyInformation.getPersonalIdentification().getPanDetails().isPanValidated() ? AppConstants.VALIDATED : AppConstants.NOT_VALIDATED;
                    }
                    panAadharLinkStatus = StringUtils.isEmpty(panNumber) ? NOT_APPLICABLE :partyInformation.getPersonalIdentification().getPanDetails().getPanAadhaarLinkStatus();
                }

                if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getCkycDetails())) {
                    responseFrom = Utility.nullSafe(proposalDetails.getCkycDetails().getPanWaiverSource());
                } else {
                    responseFrom = partyInformation.getPersonalIdentification().getPanDetails().getPanValidationService();
                }
                creditScore = partyInformation.getPersonalIdentification().getPanDetails().getCreditScore();
                incomeRange = partyInformation.getPersonalIdentification().getPanDetails().getIncomeRange();
            }
            if (StringUtils.isNotBlank(creditScore) && StringUtils.isNotBlank(incomeRange)
                    && null != proposalDetails.getUnderwritingServiceDetails().getCreditBureauStatus()) {
                creditScore = proposalDetails.getUnderwritingServiceDetails().getCreditBureauStatus().getProposerCreditScore();
                incomeRange = proposalDetails.getUnderwritingServiceDetails().getCreditBureauStatus().getProposerIncomeRange();
                if (AUTHORIZED_SIGNATORY.equalsIgnoreCase(partyType) && null != proposalDetails.getUnderwritingServiceDetails().getAuthorizedCreditBureauStatus() ){
                    creditScore = proposalDetails.getUnderwritingServiceDetails().getAuthorizedCreditBureauStatus().getProposerCreditScore();
                    incomeRange = proposalDetails.getUnderwritingServiceDetails().getAuthorizedCreditBureauStatus().getProposerIncomeRange();
                }
            }
            trlScore = getTrlScore(proposalDetails, trlScore);

            // setting Data in DataMap
            getDataMapOne(dataVariables, creditScore, dobFlag, dob, incomeRange,proposalDetails.getChannelDetails().getChannel());
            getDataMapTwo(dataVariables, panName, panNumber, panValidated, proposalNumber,isAnnuityProductJointLife, partyType,panAadharLinkStatus);
            dataVariables.put(AppConstants.PANDOB_RESPONSEFROM, responseFrom);
            dataVariables.put(AppConstants.PANDOB_TIMESTAMP,getCurrentDate());
            dataVariables.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
            dataVariables.put("trlScore", trlScore);

        } catch (Exception ex) {
	        logger.error("Data addition failed for Proposer PanDob Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(DATA_ADDITION_FAILED);
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Context panDobDetailsCxt = new Context();
        panDobDetailsCxt.setVariables(dataVariables);
        logger.info("END Proposer PANDOB Data population for transactionId {}", proposalDetails.getTransactionId());
        return panDobDetailsCxt;
    }


    /**
     * Payor PANDOB Details mapping
     *
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfPayorPanDobDocument(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START Payor PANDOB Data population");
        Map<String, Object> dataVariables = new HashMap<>();

        String panName = "";
        String creditScore = "";
        String dobFlag = AppConstants.NOT_VALIDATED;
        String dob = "";
        String incomeRange = "";
        String panNumber = "";
        String panValidated = AppConstants.NOT_VALIDATED;
        String proposalNumber = "";
        String panAadharLinkStatus="";
        try {
            proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            boolean isNeoOrAggregator = Objects.nonNull(proposalDetails.getChannelDetails())
                    && (AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                    || AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()));
            //FUL2-46310 
			proposalNumber = Utility.getSecondaryPolicyNumber(proposalDetails, proposalNumber);
			proposalNumber = Utility.getPrimaryPolicyNumber(proposalDetails, proposalNumber);
            List<PartyInformation> proposalPartyInformationList = proposalDetails.getPartyInformation();
            if (null != proposalPartyInformationList && !CollectionUtils.isEmpty(proposalPartyInformationList) && proposalPartyInformationList.size() >= 3) {
                logger.info("Payor information present, processing...");
                BasicDetails basicDetails = proposalPartyInformationList.get(2).getBasicDetails();
                String firstName = basicDetails.getFirstName();

                String lastName = basicDetails.getLastName();
                panName = String.join(" ", firstName, lastName);

                dob = Utility.dateFormatter(basicDetails.getDob());
                dobFlag = proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().isDobValidated() ? AppConstants.VALIDATED
                        : AppConstants.NOT_VALIDATED;

                panNumber = proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().getPanNumber();
                if (isNeoOrAggregator) {
                    panValidated = proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().isPanValidated() ? AppConstants.VALIDATED
                            : BLANK;
                } else {
                    panValidated = proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().isPanValidated() ? AppConstants.VALIDATED
                            : AppConstants.NOT_VALIDATED;
                }
                creditScore = proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().getCreditScore();
                incomeRange = proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().getIncomeRange();
                 panAadharLinkStatus = StringUtils.isEmpty(panNumber) ? NOT_APPLICABLE : proposalPartyInformationList.get(2).getPersonalIdentification().getPanDetails().getPanAadhaarLinkStatus();
            }

            // setting Data in DataMap
            getDataMapOne(dataVariables, creditScore, dobFlag, dob, incomeRange,proposalDetails.getChannelDetails().getChannel());
            getDataMapTwo(dataVariables, panName, panNumber, panValidated, proposalNumber, false, PAYOR,panAadharLinkStatus); // NO Payor in case GLIP, so set False for isGlipJointLife flag)
            dataVariables.put(AppConstants.PANDOB_RESPONSEFROM, AppConstants.RESPONSE_FORM_NSDL);
            dataVariables.put(AppConstants.PANDOB_TIMESTAMP,getCurrentDate());

	} catch (Exception ex) {
            logger.error("Data addition failed for Payor PanDob Document:", ex);
            List<String> errorMessages = new ArrayList<>();
	        errorMessages.add(DATA_ADDITION_FAILED);
	        throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	Context payorPanDobDetailsCxt = new Context();
	payorPanDobDetailsCxt.setVariables(dataVariables);
	logger.info("END Payor PANDOB Data population");
	return payorPanDobDetailsCxt;
    }


    private void getDataMapOne(Map<String, Object> dataVariables, String creditScore, String dobFlag, String dob, String incomeRange, String channel) {
        dataVariables.put(AppConstants.PANDOB_APPNAME, channel.equalsIgnoreCase(AppConstants.THANOS_CHANNEL)?AppConstants.THANOS_CHANNEL:AppConstants.APPNAME);
        dataVariables.put(AppConstants.PANDOB_CREDIT_SCORE, StringUtils.isNotBlank(creditScore) ? creditScore : "");
        dataVariables.put(AppConstants.PANDOB_DOB, dob);
        dataVariables.put(AppConstants.PANDOB_DOBFLAG, dobFlag);
        dataVariables.put(AppConstants.PANDOB_INCOME_RANGE, StringUtils.isNotBlank(incomeRange) ? incomeRange : "");
    }


    private void getDataMapTwo(Map<String, Object> dataVariables, String panName, String panNumber, String panValidated,
                               String proposalNumber, boolean isAnnuityProductJointLife,String partyType,String panAadhaarLinkStatus) {
        if(isAnnuityProductJointLife && INSURED.equalsIgnoreCase(partyType)){
            dataVariables.put(AppConstants. PANDOB_IS_INSURED, true);
            dataVariables.put(AppConstants.PANDOB_IS_PAYOR, false);
        }else {
            dataVariables.put(AppConstants. PANDOB_IS_INSURED, false);
            dataVariables.put(AppConstants.PANDOB_IS_PAYOR, true);
        }
        dataVariables.put(AppConstants.PANDOB_PANNAME, StringUtils.isNotBlank(panName) ? panName : "");
        dataVariables.put(AppConstants.PANDOB_PANNUMBER, StringUtils.isNotBlank(panNumber) ? panNumber : "");
        dataVariables.put(AppConstants.PANDOB_PANVALIDATED, panValidated);
        dataVariables.put(AppConstants.PANDOB_PROPOSALNUMBER, StringUtils.isNotBlank(proposalNumber) ? proposalNumber : "");
        dataVariables.put(AppConstants.PANAADHAR_LINKSTATUS, panAadhaarLinkStatus);
    }

    private String getTrlScore(ProposalDetails proposalDetails, String trlScore) {
        try {
            if (null != proposalDetails.getUnderwritingServiceDetails().getCibilDetails()) {
                trlScore = proposalDetails.getUnderwritingServiceDetails().getCibilDetails().getTrlScore();
            }
        } catch (Exception ex) {
            logger.error("Error fetching trlScore:", ex);
        }
        return trlScore;
    }

    private String getCurrentDate() {
        ZoneId zoneid = ZoneId.of(AppConstants.APP_TIMEZONE);
        LocalDateTime currTime = LocalDateTime.now(zoneid);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_MMMM_D + Utility.getLastDigitSufix(currTime.getDayOfMonth()) + DATE_FORMAT_YYYY_HH_MM_SS);
        return dtf.format(currTime);
    }

    public Context setProposerDataOfPanDobDocument(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("START Proposer PANDOB Data population for transactionId {}", proposalDetails.getTransactionId());

        Map<String, Object> dataVariables = new HashMap<>();

        String panName = "";
        String dobFlag = AppConstants.NOT_VALIDATED;
        String dob = "";
        String panNumber = "";
        String panValidated = AppConstants.NOT_VALIDATED;
        try {
            dataVariables=findDataForProposer(dob, dobFlag, panName, panNumber, panValidated, proposalDetails, dataVariables);

            dataVariables.put(AppConstants.PANDOB_TIMESTAMP,  getCurrentDate());

        } catch (Exception ex) {
            logger.error("Data addition failed for Proposer PanDob Document:", ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(DATA_ADDITION_FAILED);
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Context panDobDetailsCxt = new Context();
        panDobDetailsCxt.setVariables(dataVariables);
        logger.info("END Proposer PANDOB Data population for transactionId {}", proposalDetails.getTransactionId());
        return panDobDetailsCxt;
    }

    private Map<String, Object> findDataForProposer(String dob, String dobFlag, String panName, String panNumber, String panValidated, ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        String proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
        List<PartyInformation> proposalPartyInformationList = proposalDetails.getPartyInformation();
        boolean isNeoOrAggregator = Objects.nonNull(proposalDetails.getChannelDetails())
                && (AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                || AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()));

        if (!CollectionUtils.isEmpty(proposalPartyInformationList)) {
            logger.info("Proposer information present, processing...");
            final Optional<PartyInformation> proposerInfo = proposalPartyInformationList.stream().filter(partyInformation -> PROPOSER_PARTY_TYPE.equalsIgnoreCase(partyInformation.getPartyType())).findFirst();
            BasicDetails basicDetails = null;
            PartyInformation partyInformation = null;
            if (proposerInfo.isPresent()) {
                basicDetails = proposerInfo.get().getBasicDetails();
                partyInformation = proposerInfo.get();
            }
            if (basicDetails != null) {
                String firstName = basicDetails.getFirstName();
                String lastName = basicDetails.getLastName();
                panName = String.join(" ", firstName, lastName);
                dob = Utility.dateFormatter(basicDetails.getDob());
                dobFlag = proposalDetails.getCkycDetails() != null && proposalDetails.getCkycDetails().isJLDOBwaiverFromPANValidation() ? AppConstants.VALIDATED : AppConstants.NOT_VALIDATED;
                panNumber = partyInformation.getPersonalIdentification().getPanDetails().getPanNumber();
                if (isNeoOrAggregator) {
                    panValidated = proposalDetails.getCkycDetails() != null && proposalDetails.getCkycDetails().isPanValidationForJL() ? AppConstants.VALIDATED : BLANK;
                } else {
                    panValidated = proposalDetails.getCkycDetails() != null && proposalDetails.getCkycDetails().isPanValidationForJL() ? AppConstants.VALIDATED : AppConstants.NOT_VALIDATED;
                }
            }
            return setDataInMap(dataVariables, dob,dobFlag,panName,panNumber,panValidated,proposalNumber);
        }
        return dataVariables;
    }

    private Map<String, Object> setDataInMap(Map<String, Object> dataVariables, String dob, String dobFlag, String panName, String panNumber, String panValidated, String proposalNumber) {
        dataVariables.put(AppConstants.PANDOB_DOB, dob);
        dataVariables.put(AppConstants.PANDOB_DOBFLAG, dobFlag);
        dataVariables.put(AppConstants.PANDOB_PANNAME, StringUtils.isNotBlank(panName) ? panName : "");
        dataVariables.put(AppConstants.PANDOB_PANNUMBER, StringUtils.isNotBlank(panNumber) ? panNumber : "");
        dataVariables.put(AppConstants.PANDOB_PANVALIDATED, panValidated);
        dataVariables.put(AppConstants.PANDOB_PROPOSALNUMBER, StringUtils.isNotBlank(proposalNumber) ? proposalNumber : "");
        return dataVariables;
    }


}
