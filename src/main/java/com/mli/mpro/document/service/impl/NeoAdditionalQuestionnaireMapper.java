package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DiabeticQuestionnaire;
import com.mli.mpro.proposal.models.HighBloodPressureDetails;
import com.mli.mpro.proposal.models.HighBloodPressureQuestionnaire;
import com.mli.mpro.proposal.models.HighBloodSugarAndDiabetesDetails;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.RespiratoryDisorderDetails;
import com.mli.mpro.proposal.models.RespiratoryDisorderQuestionnaire;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

/**
 * @author manish on 20/01/21
 */
@Service
public class NeoAdditionalQuestionnaireMapper {

    private static final Logger logger = LoggerFactory.getLogger(NeoAdditionalQuestionnaireMapper.class);

    public Context setDataForAdditionalQuestionnaire(ProposalDetails proposalDetails, String questionnaireType) throws UserHandledException {
        logger.info("Starting {} questionnaire annexure data population for transactionId {}", questionnaireType,
                proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        Map<String, Object> dataVariablesLI = new HashMap<>();
        boolean isAggregator = proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);
        try {
            switch (questionnaireType) {
                case "DIABETIC":
                    setDataForDiabetic(proposalDetails, dataVariables, dataVariablesLI, isAggregator);
                    break;
                case "HIGH BLOOD PRESSURE":
                    setDataForBloodPressure(proposalDetails, dataVariables, dataVariablesLI, isAggregator);
                    break;
                case "RESPIRATORY DISORDER":
                    setDataForRespiratory(proposalDetails, dataVariables, dataVariablesLI, isAggregator);

                    break;
                default:
                    logger.info("Not find any matched data population for {} questionnaire for transactionId {}", questionnaireType,
                            proposalDetails.getTransactionId());
                    break;
            }
        } catch (Exception e) {
            logger.info("Data population failed for {} questionnaire annexure for transactionId {} : ", questionnaireType,
                    proposalDetails.getTransactionId(), e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data population failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Ending {} questionnaire annexure data population for transactionId {}", questionnaireType,
                proposalDetails.getTransactionId());
        dataVariables.put("lIDataMap", dataVariablesLI);
        Context context = new Context();
        context.setVariables(dataVariables);
        return context;
    }

    private void setDataForRespiratory(ProposalDetails proposalDetails, Map<String, Object> dataVariables,
        Map<String, Object> dataVariablesLI, boolean isAggregator) {
        if (isAggregator) {
            setDataForRespiratoryDisorderQuestionnaireForAggregator(
                proposalDetails.getLifeStyleDetails().get(0), dataVariables);
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)) {
                setDataForRespiratoryDisorderQuestionnaireForAggregator(
                    proposalDetails.getLifeStyleDetails().get(1), dataVariablesLI);
            }
        } else {
            setDataForRespiratoryDisorderQuestionnaire(proposalDetails.getLifeStyleDetails().get(0),
                dataVariables);
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)) {
                setDataForRespiratoryDisorderQuestionnaire(proposalDetails.getLifeStyleDetails().get(1),
                    dataVariablesLI);
            }
        }
    }

    private void setDataForBloodPressure(ProposalDetails proposalDetails, Map<String, Object> dataVariables,
        Map<String, Object> dataVariablesLI, boolean isAggregator) {
        if (isAggregator) {
            setDataForHighBloodPressureQuestionnaireForAggregator(
                proposalDetails.getLifeStyleDetails().get(0), dataVariables);
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)) {
                setDataForHighBloodPressureQuestionnaireForAggregator(
                    proposalDetails.getLifeStyleDetails().get(1), dataVariablesLI);
            }
        } else {
            setDataForHighBloodPressureQuestionnaire(proposalDetails.getLifeStyleDetails().get(0),
                dataVariables);
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)) {
                setDataForHighBloodPressureQuestionnaire(proposalDetails.getLifeStyleDetails().get(1),
                    dataVariablesLI);
            }
        }
    }

    private void setDataForDiabetic(ProposalDetails proposalDetails, Map<String, Object> dataVariables,
        Map<String, Object> dataVariablesLI, boolean isAggregator) {
        if (isAggregator) {
            setDataForDiabeticQuestionnaireForAggregator(proposalDetails.getLifeStyleDetails().get(0),
                dataVariables);
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)) {
                setDataForDiabeticQuestionnaireForAggregator(proposalDetails.getLifeStyleDetails().get(1),
                    dataVariablesLI);
            }
        } else {
            setDataForDiabeticQuestionnaire(proposalDetails.getLifeStyleDetails().get(0),
                dataVariables);
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)) {
                setDataForDiabeticQuestionnaire(proposalDetails.getLifeStyleDetails().get(1),
                    dataVariablesLI);
            }
        }
    }

    private void setDataForDiabeticQuestionnaire(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables) {
        String diabetesDiagnosedDate;
        String areYouOnInsulin;
        String strictDiet;
        String strictDietDetail;
        String followUps;
        String followUpsOther;
        String lastBloodTestReport;
        String numbnessInFeet;
        String numbnessInFeetDetail;

        if (Objects.nonNull(lifeStyleDetails)
                && Objects.nonNull(lifeStyleDetails.getDiabeticQuestionnaire())) {

            DiabeticQuestionnaire diabeticQuestionnaire = lifeStyleDetails.getDiabeticQuestionnaire();

            diabetesDiagnosedDate = Utility.nullSafe(diabeticQuestionnaire.getDiabetesDiagnosedDate());
            areYouOnInsulin = Utility.nullSafe(diabeticQuestionnaire.getAreYouOnInsulin());
            strictDiet = Utility.convertToYesOrNoWithDefault(diabeticQuestionnaire.getStrictDiet());
            strictDietDetail = Utility.nullSafe(diabeticQuestionnaire.getStrictDietDetail());
            followUps = Utility.nullSafe(diabeticQuestionnaire.getOptionalFollowUps());
            followUpsOther = Utility.nullSafe(diabeticQuestionnaire.getFollowUpsOther());
            lastBloodTestReport = Utility.nullSafe(diabeticQuestionnaire.getLastBloodTestReport());
            numbnessInFeet = Utility.convertToYesOrNoWithDefault(diabeticQuestionnaire.getNumbnessInFeet());
            numbnessInFeetDetail = Utility.nullSafe(diabeticQuestionnaire.getNumbnessInFeetDetail());
        } else {
            diabetesDiagnosedDate = "";
            areYouOnInsulin = "";
            strictDiet = "";
            strictDietDetail = "";
            followUps = "";
            followUpsOther = "";
            lastBloodTestReport = "";
            numbnessInFeet = "";
            numbnessInFeetDetail = "";
        }

        dataVariables.put("diabetesDiagnosedDate", diabetesDiagnosedDate);
        dataVariables.put("areYouOnInsulin", areYouOnInsulin);
        dataVariables.put("strictDiet", strictDiet);
        dataVariables.put("strictDietDetail", strictDietDetail);
        dataVariables.put(AppConstants.FOLLOWUPS, followUps);
        dataVariables.put(AppConstants.FOLLOWUPSOTHER, followUpsOther);
        dataVariables.put("lastBloodTestReport", lastBloodTestReport);
        dataVariables.put("numbnessInFeet", numbnessInFeet);
        dataVariables.put("numbnessInFeetDetail", numbnessInFeetDetail);
    }

    public void setDataForDiabeticQuestionnaireForAggregator(LifeStyleDetails lifeStyleDetails,
        Map<String, Object> dataVariables) {
        HighBloodSugarAndDiabetesDetails highBloodSugarAndDiabetesDetails = lifeStyleDetails
            .getHealth().getDiagnosedOrTreatedDetails().getHormonal()
            .getHighBloodSugarAndDiabetesDetails();
        String diabetesDiagnosedDate = Utility
            .nullSafe(highBloodSugarAndDiabetesDetails.getWhenWasItDiagnosed());
        String areYouOnInsulin = Utility
            .nullSafe(highBloodSugarAndDiabetesDetails.getMedicationDetails());
        String strictDiet =
            Utility.nullSafe(highBloodSugarAndDiabetesDetails.getFollowStrictDiet());
        String followUps = Utility.nullSafe(
            highBloodSugarAndDiabetesDetails.getFollowupapappAfterLastConsultation());
        String lastBloodTestReport = Utility
            .nullSafe(highBloodSugarAndDiabetesDetails.getLastReading());
        String numbnessInFeet = Utility
            .nullSafe(highBloodSugarAndDiabetesDetails.getEverHadNumbnessOrTingling());

        dataVariables.put("diabetesDiagnosedDate", diabetesDiagnosedDate);
        dataVariables.put("areYouOnInsulin", areYouOnInsulin);
        dataVariables.put("strictDiet", strictDiet);
        dataVariables.put("strictDietDetail", "");
        dataVariables.put(AppConstants.FOLLOWUPS, followUps);
        dataVariables.put(AppConstants.FOLLOWUPSOTHER, "");
        dataVariables.put("lastBloodTestReport", lastBloodTestReport);
        dataVariables.put("numbnessInFeet", numbnessInFeet);
        dataVariables.put("numbnessInFeetDetail", "");
    }

    private void setDataForHighBloodPressureQuestionnaire(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables) {
        String diagnosedDate;
        String medicationDetails;
        String followUps;
        String followUpsOther;
        String lastBloodPressureReading;

        if (Objects.nonNull(lifeStyleDetails)
                && Objects.nonNull(lifeStyleDetails.getHighBloodPressureQuestionnaire())) {

            HighBloodPressureQuestionnaire highBloodPressureQuestionnaire = lifeStyleDetails
                    .getHighBloodPressureQuestionnaire();
            diagnosedDate = Utility.nullSafe(highBloodPressureQuestionnaire.getHighBloodPressureDiagnosedDate());
            medicationDetails = Utility.nullSafe(highBloodPressureQuestionnaire.getMedicationDetails());
            followUps = Utility.nullSafe(highBloodPressureQuestionnaire.getFollowUpsForHighBloodPressure());
            followUpsOther = Utility.nullSafe(highBloodPressureQuestionnaire.getFollowUpsOtherForHighBloodPressure());
            lastBloodPressureReading = Utility.nullSafe(highBloodPressureQuestionnaire.getLastBloodPressureReading());
        }
         else {
            diagnosedDate = "";
            medicationDetails = "";
            followUps = "";
            followUpsOther = "";
            lastBloodPressureReading = "";
        }

        dataVariables.put("highBloodPressureDiagnosedDate", diagnosedDate);
        dataVariables.put(AppConstants.MEDICATIONDETAILS, medicationDetails);
        dataVariables.put(AppConstants.FOLLOWUPS, followUps);
        dataVariables.put(AppConstants.FOLLOWUPSOTHER, followUpsOther);
        dataVariables.put("lastBloodPressureReading", lastBloodPressureReading);
    }

    public void setDataForHighBloodPressureQuestionnaireForAggregator(LifeStyleDetails lifeStyleDetails,
        Map<String, Object> dataVariables) {
        HighBloodPressureDetails highBloodPressureDetails = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getCardio()
            .getHighBloodPressureDetails();
        String diagnosedDate = Utility.nullSafe(highBloodPressureDetails.getWhenWasItDiagnosed());
        String medicationDetails = Utility
            .nullSafe(highBloodPressureDetails.getMedicationDetails());
        String followUps = Utility
            .nullSafe(highBloodPressureDetails.getFollowupAfterLastConsultation());
        String lastBloodPressureReading = Utility
            .nullSafe(highBloodPressureDetails.getLastReading());

        dataVariables.put("highBloodPressureDiagnosedDate", diagnosedDate);
        dataVariables.put(AppConstants.MEDICATIONDETAILS, medicationDetails);
        dataVariables.put(AppConstants.FOLLOWUPS, followUps);
        dataVariables.put(AppConstants.FOLLOWUPSOTHER, "");
        dataVariables.put("lastBloodPressureReading", lastBloodPressureReading);
    }

    private void setDataForRespiratoryDisorderQuestionnaire(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables) {
        String diagnosedDate;
        String symptoms;
        String followUps;
        String followUpsOther;
        String medicationDetails;
        String steroidsTaken;
        String steroidsTakenDetail;

        if (Objects.nonNull(lifeStyleDetails)
                && Objects.nonNull(lifeStyleDetails.getRespiratoryDisorderQuestionnaire())) {

            RespiratoryDisorderQuestionnaire respiratoryDisorderQuestionnaire = lifeStyleDetails
                    .getRespiratoryDisorderQuestionnaire();
            diagnosedDate = Utility.nullSafe(respiratoryDisorderQuestionnaire.getRespiratoryDisorderDiagnosedDate());
            symptoms = Utility.nullSafe(respiratoryDisorderQuestionnaire.getSymptomsDescription());
            followUps = Utility.nullSafe(respiratoryDisorderQuestionnaire.getFollowUpsForRespiratoryDisorder());
            followUpsOther = Utility.nullSafe(respiratoryDisorderQuestionnaire.getFollowUpsOtherForRespiratoryDisorder());
            medicationDetails = Utility.nullSafe(respiratoryDisorderQuestionnaire.getMedicationDetails());
            steroidsTaken = Utility.convertToYesOrNoWithDefault(respiratoryDisorderQuestionnaire.getSteroidsTaken());
            steroidsTakenDetail = Utility.nullSafe(respiratoryDisorderQuestionnaire.getSteroidsTakenDetail());
        }
        else {
            diagnosedDate = "";
            symptoms = "";
            followUps = "";
            followUpsOther = "";
            medicationDetails = "";
            steroidsTaken = "";
            steroidsTakenDetail = "";
        }

        dataVariables.put("respiratoryDisorderDiagnosedDate", diagnosedDate);
        dataVariables.put("symptomsDescription", symptoms);
        dataVariables.put("followUpsForRespiratoryDisorder", followUps);
        dataVariables.put("followUpsOtherForRespiratoryDisorder", followUpsOther);
        dataVariables.put(AppConstants.MEDICATIONDETAILS, medicationDetails);
        dataVariables.put("steroidsTaken", steroidsTaken);
        dataVariables.put("steroidsTakenDetail", steroidsTakenDetail);
    }

    private void setDataForRespiratoryDisorderQuestionnaireForAggregator(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables) {
        RespiratoryDisorderDetails respiratoryDisorderDetails = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails()
            .getRespiratory().getRespiratoryDisorderDetails();
        String diagnosedDate = Utility.nullSafe(respiratoryDisorderDetails.getWhenWasItDiagnosed());
        String symptoms = Utility.nullSafe(respiratoryDisorderDetails.getSymptomsDetails());
        String followUps = Utility.nullSafe(respiratoryDisorderDetails.getHowOftenSymptomsOccur());
        String medicationDetails = Utility
            .nullSafe(respiratoryDisorderDetails.getMedicationDetails());
        String steroidsTaken = Utility
            .nullSafe(respiratoryDisorderDetails.getHaveYouTakenSteriods());
        dataVariables.put("respiratoryDisorderDiagnosedDate", diagnosedDate);
        dataVariables.put("symptomsDescription", symptoms);
        dataVariables.put("followUpsForRespiratoryDisorder", followUps);
        dataVariables.put("followUpsOtherForRespiratoryDisorder", "");
        dataVariables.put(AppConstants.MEDICATIONDETAILS, medicationDetails);
        dataVariables.put("steroidsTaken", steroidsTaken);
        dataVariables.put("steroidsTakenDetail", "");
    }
}
