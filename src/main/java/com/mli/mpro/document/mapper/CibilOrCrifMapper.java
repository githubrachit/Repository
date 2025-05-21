package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CibilOrCrifMapper {

    private static final Logger logger = LoggerFactory.getLogger(CibilOrCrifMapper.class);

    public static final String CONST_1500000_3000000 = "1500000 - 3000000";
    public static final String CIBIL_SCORE_DEFAULT_VALUE = "00001";


    HashMap<String, String> estimatedIncomeMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(CIBIL_SCORE_DEFAULT_VALUE, "<300000");
            put("00002", "300000 - 500000");
            put("00003", "500000 - 700000");
            put("00004", "500000 - 700000");
            put("00005", "700000 - 1000000");
            put("00006", "1000000 - 1500000");
            put("00007", "1000000 - 1500000");
            put("00008", CONST_1500000_3000000);
            put("00009", CONST_1500000_3000000);
            put("00010", CONST_1500000_3000000);
        }
    };

    public Context setCibilData(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("Starting CIBIL data population");
        Context context = new Context();
        Map<String, Object> dataMap = new HashMap<>();
        try {
            if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails())) {
                setCibilDataInMap(proposalDetails.getUnderwritingServiceDetails().getCibilDetails(), dataMap);
            }
            convertNullValuesToBlank(dataMap);
            context.setVariables(dataMap);
        } catch (Exception e) {
            logger.error("Data addition failed for CIBIL:",e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("End of CIBIL data population");
        return context;
    }

    public Context  setCrifData(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("Starting CRIF data population");
        Context context = new Context();
        try {
            Map<String, Object> dataMap = setCommonDataInDataMap(proposalDetails, AppConstants.CRIF_DOCUMENT);
            if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails())) {
                setCrifDataInMap(proposalDetails.getUnderwritingServiceDetails().getCreditBureauStatus(), dataMap);
            }
            convertNullValuesToBlank(dataMap);
            context.setVariables(dataMap);
        } catch (Exception e) {
            logger.error("Data addition failed for CRIF:",e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("End of CRIF data population");
        return context;
    }

    private Map<String, Object> setCommonDataInDataMap(ProposalDetails proposalDetails, String type) {
        Map<String, Object> dataMap = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd HH:mm:ss z yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String responseName = type.toUpperCase();
        String proposalNumber = "";
        String pincode = "";
        String mobileNumber = "";
        String emailId = "";
        String creditScore = "";
        String estimatedIncome = "";
        String trlScore = "";
        String dobValidated = "";
        String nameValidated = "";
        String panNumberValidated = "";
        String addressValidated = "";
        String occupationValidated = "";
        String creditScoreValidated = "";
        String estimatedIncomeValidated = "";
        String trlScoreValidated = "";

        BasicDetails basicDetails = null;
        if (Objects.nonNull(proposalDetails.getPartyInformation())) {
            basicDetails = proposalDetails.getPartyInformation()
                    .stream()
                    .findFirst()
                    .map(PartyInformation::getBasicDetails)
                    .orElse(null);
        }

        if (Objects.nonNull(proposalDetails.getApplicationDetails())) {
            proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
        }

        if (Objects.nonNull(basicDetails)) {
            StringBuilder nameBuilder= new StringBuilder("");
            if (Objects.nonNull(basicDetails.getFirstName())) {
                 nameBuilder = new StringBuilder(basicDetails.getFirstName());
            }
            if (Objects.nonNull(basicDetails.getMiddleName())) {
                nameBuilder.append(' ');
                nameBuilder.append(basicDetails.getMiddleName());
            }
            if (Objects.nonNull(basicDetails.getLastName())) {
                nameBuilder.append(' ');
                nameBuilder.append(basicDetails.getLastName());
            }
            if (Objects.nonNull(basicDetails.getAddress()) && !basicDetails.getAddress().isEmpty() &&
                    Objects.nonNull(basicDetails.getAddress().get(0).getAddressDetails())) {
                pincode = basicDetails.getAddress().get(0).getAddressDetails().getPinCode();
            }

            if (Objects.nonNull(basicDetails.getPersonalIdentification())) {
                emailId = basicDetails.getPersonalIdentification().getEmail();
                if (Objects.nonNull(basicDetails.getPersonalIdentification().getPhone()) &&
                        !basicDetails.getPersonalIdentification().getPhone().isEmpty()) {
                    mobileNumber = basicDetails.getPersonalIdentification().getPhone().get(0).getPhoneNumber();
                }
            }
        }
        dataMap.put(AppConstants.CB_FIELD_APP_NAME, proposalDetails.getChannelDetails().getChannel());
        dataMap.put(AppConstants.CB_FIELD_RESPONSE_NAME, responseName);
        dataMap.put(AppConstants.CB_FIELD_PROPOSAL_NUMBER, proposalNumber);
        dataMap.put(AppConstants.CB_FIELD_NAME, "");
        dataMap.put(AppConstants.CB_FIELD_DOB, "");
        dataMap.put(AppConstants.CB_FIELD_PAN_NUMBER, "");
        dataMap.put(AppConstants.CB_FIELD_ADDRESS, "");
        dataMap.put(AppConstants.CB_FIELD_PINCODE, "");
        dataMap.put(AppConstants.CB_FIELD_MOBILE_NUMBER, "");
        dataMap.put(AppConstants.CB_FIELD_EMAIL_ID, "");
        dataMap.put(AppConstants.CB_FIELD_OCCUPATION, "");
        dataMap.put(AppConstants.CB_FIELD_CREDIT_SCORE, creditScore);
        dataMap.put(AppConstants.CB_FIELD_ESTIMATED_INCOME, estimatedIncome);
        dataMap.put(AppConstants.CB_FIELD_TRL_SCORE, trlScore);
        dataMap.put(AppConstants.CB_FIELD_FORMAT, type);
        dataMap.put(AppConstants.CB_FIELD_TIMESTAMP, dateFormat.format(new Date()));
        dataMap.put(AppConstants.CB_FIELD_DOB_VALIDATED, dobValidated);
        dataMap.put(AppConstants.CB_FIELD_NAME_VALIDATED, nameValidated);
        dataMap.put(AppConstants.CB_FIELD_PAN_NUMBER_VALIDATED, panNumberValidated);
        dataMap.put(AppConstants.CB_FIELD_ADDRESS_VALIDATED, addressValidated);
        dataMap.put(AppConstants.CB_FIELD_PINCODE_VALIDATED, pincode);
        dataMap.put(AppConstants.CB_FIELD_MOBILE_NUMBER_VALIDATED, mobileNumber);
        dataMap.put(AppConstants.CB_FIELD_EMAIL_ID_VALIDATED, emailId);
        dataMap.put(AppConstants.CB_FIELD_OCCUPATION_VALIDATED, occupationValidated);
        dataMap.put(AppConstants.CB_FIELD_CREDIT_SCORE_VALIDATED, creditScoreValidated);
        dataMap.put(AppConstants.CB_FIELD_ESTIMATED_INCOME_VALIDATED, estimatedIncomeValidated);
        dataMap.put(AppConstants.CB_FIELD_TRL_SCORE_VALIDATED, trlScoreValidated);
        return dataMap;
    }

    private void setCibilDataInMap(CibilDetails cibilDetails, Map<String, Object> dataMap) {
        String creditScore = "";
        String estimatedIncome = "";
        String trlScore = "";
        String name = "";
        String nameValidated = "";
        String dob = "";
        String dobValidated = "";
        String panNumber = "";
        String panNumberValidated = "";
        String address = "";
        String pinCode = "";
        if (Objects.nonNull(cibilDetails)) {

            name = Utility.nullSafe(cibilDetails.getName());
            nameValidated = Utility.nullSafe(cibilDetails.getNameStatus());
            dob = Utility.nullSafe(cibilDetails.getDob());
            dobValidated = Utility.nullSafe(cibilDetails.getDobStatus());
            panNumber = Utility.nullSafe(cibilDetails.getPanNumber());
            panNumberValidated = Utility.nullSafe(cibilDetails.getPanNumberStatus());
            address = Utility.nullSafe(cibilDetails.getAddress());
            pinCode = Utility.nullSafe(cibilDetails.getPincode());

            BureauResponse creditScoreResponse = getBureauResponseByType(cibilDetails, AppConstants.CIBIL_CREDIT_SCORE_NAME);
            if (Objects.nonNull(creditScoreResponse)) {
                if(creditScoreResponse.getScore() !=null && creditScoreResponse.getScore().equals("000-1")){
                    creditScoreResponse.setScore("0");
                }
                creditScore = creditScoreResponse.getScore();
            }
            BureauResponse estimatedIncomeResponse = getBureauResponseByType(cibilDetails, AppConstants.CIBIL_ESTIMATED_INCOME_NAME);
            if (Objects.nonNull(estimatedIncomeResponse)) {
                estimatedIncome = estimatedIncomeMap.containsKey(estimatedIncomeResponse.getScore()) ? estimatedIncomeMap.get(estimatedIncomeResponse.getScore()) : estimatedIncomeMap.get(CIBIL_SCORE_DEFAULT_VALUE);
            }
            trlScore = cibilDetails.getTrlScore();
        }
        if(AppConstants.ZERO.equals(creditScore)){
            nameValidated = AppConstants.HYPHEN;
            dobValidated = AppConstants.HYPHEN;
            panNumberValidated = AppConstants.HYPHEN;
        }
        dataMap.put(AppConstants.CB_FIELD_CREDIT_SCORE, creditScore);
        dataMap.put(AppConstants.CB_FIELD_ESTIMATED_INCOME, estimatedIncome);
        dataMap.put(AppConstants.CB_FIELD_TRL_SCORE, trlScore);
        dataMap.put(AppConstants.CB_FIELD_NAME, name);
        dataMap.put(AppConstants.CIBIL_FIELD_NAME_VALIDATED, nameValidated);
        dataMap.put(AppConstants.CB_FIELD_DOB, dob);
        dataMap.put(AppConstants.CIBIL_FIELD_DOB_VALIDATED, dobValidated);
        dataMap.put(AppConstants.CB_FIELD_PAN_NUMBER, panNumber);
        dataMap.put(AppConstants.CIBIL_FIELD_PAN_NUMBER_VALIDATED, panNumberValidated);
        dataMap.put(AppConstants.CB_FIELD_ADDRESS, address);
        dataMap.put(AppConstants.CB_FIELD_PINCODE, pinCode);
    }

    private void setCrifDataInMap(CreditBureauStatus creditBureauStatus, Map<String, Object> dataMap) {
        String creditScore = "";
        String estimatedIncome = "";
        String dob = "";
        String dobValidated = "";
        String name = "";
        String nameValidated = "";
        String panNumber = "";
        String panNumberValidated = "";
        String address = "";
        String addressValidated = "";
        String pincode = "";
        String mobileNumber = "";
        String emailId = "";
        String occupation = "";
        if (Objects.nonNull(creditBureauStatus)) {
            creditScore = creditBureauStatus.getProposerCreditScore();
            estimatedIncome = creditBureauStatus.getProposerIncomeRange();
            dob = creditBureauStatus.getDob();
            dobValidated = AppConstants.CRIF_MATCHED_STATUS.equalsIgnoreCase(creditBureauStatus.getDobStatus()) ?
                    AppConstants.MATCHED_STATUS_TRUE : AppConstants.MATCHED_STATUS_FALSE;
            name = creditBureauStatus.getName();
            nameValidated = AppConstants.CRIF_MATCHED_STATUS.equalsIgnoreCase(creditBureauStatus.getNameStatus()) ?
                    AppConstants.MATCHED_STATUS_TRUE : AppConstants.MATCHED_STATUS_FALSE;
            panNumber = creditBureauStatus.getPan();
            panNumberValidated = AppConstants.CRIF_MATCHED_STATUS.equalsIgnoreCase(creditBureauStatus.getPanStatus()) ?
                    AppConstants.MATCHED_STATUS_TRUE : AppConstants.MATCHED_STATUS_FALSE;
            address = creditBureauStatus.getAddress();
            addressValidated = AppConstants.CRIF_MATCHED_STATUS.equalsIgnoreCase(creditBureauStatus.getAddressStatus()) ?
                    AppConstants.MATCHED_STATUS_TRUE : AppConstants.MATCHED_STATUS_FALSE;
            pincode = creditBureauStatus.getPinCode();
            mobileNumber = creditBureauStatus.getMobile();
            emailId = creditBureauStatus.getEmailId();
            occupation = creditBureauStatus.getOccuptionClass();
        }
        dataMap.put(AppConstants.CB_FIELD_NAME, name);
        dataMap.put(AppConstants.CB_FIELD_DOB, dob);
        dataMap.put(AppConstants.CB_FIELD_PAN_NUMBER, panNumber);
        dataMap.put(AppConstants.CB_FIELD_ADDRESS, address);
        dataMap.put(AppConstants.CB_FIELD_PINCODE, pincode);
        dataMap.put(AppConstants.CB_FIELD_MOBILE_NUMBER, mobileNumber);
        dataMap.put(AppConstants.CB_FIELD_EMAIL_ID, emailId);
        dataMap.put(AppConstants.CB_FIELD_OCCUPATION, occupation);
        dataMap.put(AppConstants.CB_FIELD_CREDIT_SCORE, creditScore);
        dataMap.put(AppConstants.CB_FIELD_ESTIMATED_INCOME, estimatedIncome);
        dataMap.put(AppConstants.CB_FIELD_DOB_VALIDATED, dobValidated);
        dataMap.put(AppConstants.CB_FIELD_NAME_VALIDATED, nameValidated);
        dataMap.put(AppConstants.CB_FIELD_PAN_NUMBER_VALIDATED, panNumberValidated);
        dataMap.put(AppConstants.CB_FIELD_ADDRESS_VALIDATED, addressValidated);
    }

    private BureauResponse getBureauResponseByType(CibilDetails cibilDetails, String type) {
        logger.info("type name :: cibil detail {}", type);
        logger.info("cibil detail :: {}", cibilDetails);
        if (Objects.nonNull(type) && Objects.nonNull(cibilDetails)) {
            return cibilDetails.getBureauResponse()
                    .stream()
                    .filter(bureauResponse -> type.equalsIgnoreCase(bureauResponse.getScoreName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private void convertNullValuesToBlank(Map<String, Object> dataMap) {
        Set<String> keys = dataMap.keySet();
        for (String key : keys) {
            if (Objects.isNull(dataMap.get(key))) {
                dataMap.put(key, "");
            }
        }
    }
}
