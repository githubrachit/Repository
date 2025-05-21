package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PosvQuestion;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

import static com.mli.mpro.utils.Utility.convertTextOrNA;
import static com.mli.mpro.utils.Utility.convertYesNoAndNA;

@Service
public class Covid19QuestionnaireDetailsMapper {
    private static final Logger logger = LoggerFactory.getLogger(Covid19QuestionnaireDetailsMapper.class);

    public Context setDataForCovid19QuestionnaireDetails(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("Starting COVID19 questionnaire updated document data population for transactionId {}",
                proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        try {
            Map<String, String> questionMap = new HashMap<>();
            List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
            for (PosvQuestion posvQuestion : posvQuestions) {
                if(StringUtils.isEmpty(questionMap.get(posvQuestion.getQuestionId()))) {
                    questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
                }
            }
                setCovidExposure(dataVariables, questionMap, proposalDetails);

        } catch (Exception e) {
            logger.info("Data addition failed for COVID19 Questionnaire updated document for transactionId {} : {} ",
                    proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Ending COVID19 questionnaire updated document data population for transactionId {}",
                proposalDetails.getTransactionId());
        Context context = new Context();
        context.setVariables(dataVariables);
        return context;
    }

    public Context setDataForCovidWOPorSWPQuestionnaire(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("Starting COVID19 WOP/SWP questionnaire updated document data population for transactionId {}",
                proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        try {
            Map<String, String> questionMap = new HashMap<>();
            List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
            for (PosvQuestion posvQuestion : posvQuestions) {
                if (StringUtils.isEmpty(questionMap.get(posvQuestion.getQuestionId()))) {
                    questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
                }
            }
            setCovidExposure(dataVariables, questionMap, proposalDetails);
            setCovidExposureForInsured(dataVariables, questionMap);


        } catch (Exception e) {
            logger.info("Data addition failed for COVID19 WOP/SWP Questionnaire updated document for transactionId {} :{} ",
                    proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Ending COVID19 WOP/SWP questionnaire updated document data population for transactionId {}",
                proposalDetails.getTransactionId());
        Context context = new Context();
        context.setVariables(dataVariables);
        return context;
    }

    private void setCovidExposure(Map<String, Object> dataVariables, Map<String, String> questionMap,
                                  ProposalDetails proposalDetails) throws UserHandledException {

        String proposalName = null;
        String formType = proposalDetails.getApplicationDetails().getFormType();
        BasicDetails proposalBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
        proposalName = proposalBasicDetails.getFirstName() + (StringUtils.isEmpty(proposalBasicDetails.getMiddleName()) ? "" : " "
                + proposalBasicDetails.getMiddleName()) + " " + proposalBasicDetails.getLastName();

        dataVariables.put(AppConstants.APPLICATION_NUMBER, proposalDetails.getApplicationDetails().getPolicyNumber());
        dataVariables.put(AppConstants.LIFE_INSURED_NAME, Utility.getInsuredName(formType, proposalDetails));
        dataVariables.put(AppConstants.PROPOSED_POLICY_HOLDER, proposalName);


        String hc16 = convertYesNoAndNA(questionMap.get(AppConstants.HC16));
        String hc40 = "";
        if(!"NA".equalsIgnoreCase(hc16)){
            dataVariables.put(AppConstants.COVID_POSITIVE_CHECK_PROPOSER, hc16);
        } else{
            hc40 = convertYesNoAndNA(questionMap.get(AppConstants.HC40));
            dataVariables.put(AppConstants.COVID_POSITIVE_CHECK_PROPOSER, hc40);
        }

        if (AppConstants.YES.equals(hc16)) {
            setDataForSelf(dataVariables, questionMap);
        }
        else if (AppConstants.YES.equals(hc40)) {
            setDateForDependent(dataVariables, questionMap);
        }
        String hc17 = convertYesNoAndNA(questionMap.get(AppConstants.HC17));
        if("NA".equalsIgnoreCase(hc17)){
            hc17 = convertYesNoAndNA(questionMap.get(AppConstants.HC41));
        }
        dataVariables.put(AppConstants.VACCINATED_FOR_COVID19_PROPOSER, hc17);

        dataVariables.put(AppConstants.DATE_PROPOSER, Utility.currentDate());

    }

    private void setDataForSelf(Map<String, Object> dataVariables, Map<String, String> questionMap) throws UserHandledException {
        dataVariables.put(AppConstants.FULL_FUNCTIONAL_RECOVERY_PROPOSER, convertYesNoAndNA(questionMap.get(AppConstants.HC16A)));
        String hc16B = convertTextOrNA(questionMap.get(AppConstants.HC16B));
        dataVariables.put(AppConstants.LAST_POSITIVE_DIAGNOSIS_PROPOSER,
                (hc16B.matches(AppConstants.MM_YYYY_SLASH_REGEX)) ? hc16B : Utility.dateFormatterWithTimeZone(hc16B,
        		AppConstants.YYYY_MM_DD_HH_MM_SS_HYPHEN_Z, AppConstants.MM_YYYY_SLASH));
        String hc16C = convertYesNoAndNA(questionMap.get(AppConstants.HC16C));
        dataVariables.put(AppConstants.HOSPITALIZED_PROPOSER, hc16C);
        String hc16D = convertYesNoAndNA(questionMap.get(AppConstants.HC16D));
        dataVariables.put(AppConstants.HOME_QUARANTINE_PROPOSER, hc16D);
        String recoveryPeriod = "";
        try{
            recoveryPeriod = convertTextOrNA(questionMap.get(AppConstants.HC16E));
            recoveryPeriod = Math.round(Double.parseDouble(recoveryPeriod))>1 ?
                    Math.round(Double.parseDouble(recoveryPeriod)) + AppConstants.MONTHS : Math.round(Double.parseDouble(recoveryPeriod)) + AppConstants.MONTH;
            dataVariables.put(AppConstants.RECOVERY_PERIOD_PROPOSER, recoveryPeriod);
        } catch (NumberFormatException ex){
            logger.error("Exception during parsing proposer recovery period {}", Utility.getExceptionAsString(ex));
        }

        String hc16F = convertYesNoAndNA(questionMap.get(AppConstants.HC16F));
        dataVariables.put(AppConstants.SUFFER_FROM_COVID19_PROPOSER, hc16F);
        if (AppConstants.YES.equals(hc16F)) {
            dataVariables.put(AppConstants.PROVIDE_DETAILS_PROPOSER, convertTextOrNA(questionMap.get(AppConstants.HC16G)));
        }
    }

    private void setDateForDependent(Map<String, Object> dataVariables, Map<String, String> questionMap) throws UserHandledException {
        dataVariables.put(AppConstants.FULL_FUNCTIONAL_RECOVERY_PROPOSER, convertYesNoAndNA(questionMap.get(AppConstants.HC40A)));
        String hc40B = convertTextOrNA(questionMap.get(AppConstants.HC40B)); 
        dataVariables.put(AppConstants.LAST_POSITIVE_DIAGNOSIS_PROPOSER,
                (hc40B.matches(AppConstants.MM_YYYY_SLASH_REGEX)) ? hc40B : Utility.dateFormatterWithTimeZone(hc40B,
        		AppConstants.YYYY_MM_DD_HH_MM_SS_HYPHEN_Z, AppConstants.MM_YYYY_SLASH));
        String hc40C = convertYesNoAndNA(questionMap.get(AppConstants.HC40C));
        dataVariables.put(AppConstants.HOSPITALIZED_PROPOSER, hc40C);
        String hc40D = convertYesNoAndNA(questionMap.get(AppConstants.HC40D));
        dataVariables.put(AppConstants.HOME_QUARANTINE_PROPOSER, hc40D);
        String recoveryPeriod = "";
        try{
            recoveryPeriod = convertTextOrNA(questionMap.get(AppConstants.HC40E));
            recoveryPeriod = Math.round(Double.parseDouble(recoveryPeriod))>1 ?
                    Math.round(Double.parseDouble(recoveryPeriod)) + AppConstants.MONTHS : Math.round(Double.parseDouble(recoveryPeriod)) + AppConstants.MONTH;
            dataVariables.put(AppConstants.RECOVERY_PERIOD_PROPOSER, recoveryPeriod);
        } catch (NumberFormatException ex){
            logger.error("Exception during parsing proposer recovery period {}", Utility.getExceptionAsString(ex));
        }

        String hc40F = convertYesNoAndNA(questionMap.get(AppConstants.HC40F));
        dataVariables.put(AppConstants.SUFFER_FROM_COVID19_PROPOSER, hc40F);
        if (AppConstants.YES.equals(hc40F)) {
            dataVariables.put(AppConstants.PROVIDE_DETAILS_PROPOSER, convertTextOrNA(questionMap.get(AppConstants.HC40G)));
        }
    }

    private void setCovidExposureForInsured(Map<String, Object> dataVariablesInsured, Map<String, String> questionMap) throws UserHandledException {

        String hc40 = convertYesNoAndNA(questionMap.get(AppConstants.HC40));
        dataVariablesInsured.put(AppConstants.COVID_POSITIVE_CHECK_INSUED, hc40);

        if (AppConstants.YES.equals(hc40)) {
            dataVariablesInsured.put(AppConstants.FULL_FUNCTIONAL_RECOVERY_INSURED, convertYesNoAndNA(questionMap.get(AppConstants.HC40A)));
            String hc40B = convertTextOrNA(questionMap.get(AppConstants.HC40B)); 
            dataVariablesInsured.put(AppConstants.LAST_POSITIVE_DIAGNOSIS_INSURED,
                    (hc40B.matches(AppConstants.MM_YYYY_SLASH_REGEX)) ? hc40B : Utility.dateFormatterWithTimeZone(hc40B,
            		AppConstants.YYYY_MM_DD_HH_MM_SS_HYPHEN_Z, AppConstants.MM_YYYY_SLASH));
            String hc40C = convertYesNoAndNA(questionMap.get(AppConstants.HC40C));
            dataVariablesInsured.put(AppConstants.HOSPITALIZED_INSURED, hc40C);
            String hc40D = convertYesNoAndNA(questionMap.get(AppConstants.HC40D));
            dataVariablesInsured.put(AppConstants.HOME_QUARANTINE_INSURED, hc40D);
            try{
                    String recoveryPeriod = convertTextOrNA(questionMap.get(AppConstants.HC40E));
                    recoveryPeriod = Math.round(Double.parseDouble(recoveryPeriod))>1 ?
                            Math.round(Double.parseDouble(recoveryPeriod)) + AppConstants.MONTHS : Math.round(Double.parseDouble(recoveryPeriod)) + AppConstants.MONTH;
                    dataVariablesInsured.put(AppConstants.RECOVERY_PERIOD_INSURED, recoveryPeriod);
                } catch (NumberFormatException ex){
                    logger.error("Exception during parsing insured recovery period {}", Utility.getExceptionAsString(ex));
            }

            String hc40F = convertYesNoAndNA(questionMap.get(AppConstants.HC40F));
            dataVariablesInsured.put(AppConstants.SUFFER_FROM_COVID19_INSURED, hc40F);
            if (AppConstants.YES.equals(hc40F)) {
                dataVariablesInsured.put(AppConstants.PROVIDE_DETAILS_INSURED, convertTextOrNA(questionMap.get(AppConstants.HC40G)));
            }
        }
        String hc41 = convertYesNoAndNA(questionMap.get(AppConstants.HC41));
        dataVariablesInsured.put(AppConstants.VACCINATED_FOR_COVID19_INSURED, hc41);

        dataVariablesInsured.put(AppConstants.DATE_INSURED, Utility.currentDate());
    }
}
