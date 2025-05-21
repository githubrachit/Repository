package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.CovidQuestionnaire;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author manish on 11/11/20
 */
@Service
public class CovidQuestionnaireMapper {

    private static final Logger logger = LoggerFactory.getLogger(CovidQuestionnaireMapper.class);
    private static final String EMPTY_STRING = "";
    private static final String TEMPLATE_TYPE = "templateType";
    private static final String EVER_TESTED_POSITIVE = "everTestedPositiveForCovid";
    private static final String HAVE_COVID_VACCINATED = "haveYouBeenVaccinatedForCovid";
    private static final String EVER_TESTED_POSITIVE_JL = "everTestedPositiveForCovidJL";
    private static final String HAVE_COVID_VACCINATED_JL = "haveYouBeenVaccinatedForCovidJL";

    public Context setDataForCovidQuestionnaire(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("Starting COVID questionnaire annexure data population for transactionId {}", proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        Map<String, Object> dataVariablesLI = new HashMap<>();
        try {
            List<CovidQuestionnaire> covidQuestionnaire = proposalDetails.getLifeStyleDetails().stream()
                    .filter(lifeStyleDetails ->!"PAYOR".equalsIgnoreCase(lifeStyleDetails.getPartyType()))
                    .map(LifeStyleDetails::getCovidQuestionnaire)
                    .collect(Collectors.toList());
                if (!Utility.isNewCovidQuestionApplicable(proposalDetails)) {
                 setOldQuestionnaire(covidQuestionnaire,dataVariables, dataVariablesLI, proposalDetails);
                } else {
                    if (!CollectionUtils.isEmpty(covidQuestionnaire) && Objects.nonNull(covidQuestionnaire.get(0))) {
                        setNewQuestion1Answers(dataVariables, covidQuestionnaire.get(0));
                        setNewQuestion2Answers(dataVariables, covidQuestionnaire.get(0));
                        if (Utility.isSSPJLProduct(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails) || proposalDetails.getProductDetails().get(0).getProductType()
                                .equalsIgnoreCase(AppConstants.SWPJL) && Objects
                                .nonNull(covidQuestionnaire.get(1))) {
                            setNewQuestion1Answers(dataVariablesLI, covidQuestionnaire.get(1));
                            setNewQuestion2Answers(dataVariablesLI, covidQuestionnaire.get(1));
                        }
                    }
                }

            dataVariables.put("lIDataMap", dataVariablesLI);
            dataVariables.put("applicationNumber", setApplicationNumber(proposalDetails));
            dataVariables.put("lifeInsuredName", (Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails))
                ? setInsuredName(proposalDetails, 1) : setInsuredName(proposalDetails, 0));
            dataVariables.put("proposerName", (Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails)) ? setInsuredName(proposalDetails, 0) : EMPTY_STRING);
            dataVariables.put("currentDate", setCurrentDate(proposalDetails));

        } catch (Exception e) {
            logger.info("Data addition failed for COVID Questionnaire annexure for transactionId {} : ", proposalDetails.getTransactionId(), e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Ending COVID questionnaire annexure data population for transactionId {}", proposalDetails.getTransactionId());
        Context context = new Context();
        context.setVariables(dataVariables);
        return context;
    }

    private void setOldQuestionnaire(List<CovidQuestionnaire> covidQuestionnaire, Map<String, Object> dataVariables, Map<String, Object> dataVariablesLI, ProposalDetails proposalDetails) {
            if (!CollectionUtils.isEmpty(covidQuestionnaire) && Objects.nonNull(covidQuestionnaire.get(0))) {
                setQuestion1Answers(dataVariables, covidQuestionnaire.get(0));
                setQuestion2Answers(dataVariables, covidQuestionnaire.get(0));
                setQuestion3Answers(dataVariables, covidQuestionnaire.get(0));
                setQuestion4Answers(dataVariables, covidQuestionnaire.get(0));
                setQuestion5Answers(dataVariables, covidQuestionnaire.get(0));
                setExposureQuestionForHealthCareWorkers(dataVariables, covidQuestionnaire.get(0));
                if (Utility.isApplicationIsForm2(proposalDetails) || proposalDetails.getProductDetails().get(0).getProductType()
                        .equalsIgnoreCase(AppConstants.SWPJL) && Objects
                        .nonNull(covidQuestionnaire.get(1))) {
                    setQuestion1Answers(dataVariablesLI, covidQuestionnaire.get(1));
                    setQuestion2Answers(dataVariablesLI, covidQuestionnaire.get(1));
                    setQuestion3Answers(dataVariablesLI, covidQuestionnaire.get(1));
                    setQuestion4Answers(dataVariablesLI, covidQuestionnaire.get(1));
                    setQuestion5Answers(dataVariablesLI, covidQuestionnaire.get(1));
                    setExposureQuestionForHealthCareWorkers(dataVariablesLI,
                            covidQuestionnaire.get(1));
                }
            }
    }

    private String setApplicationNumber(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getApplicationDetails())) {
            return proposalDetails.getApplicationDetails().getPolicyNumber();
        }
        return EMPTY_STRING;
    }

    private String setInsuredName(ProposalDetails proposalDetails, int index) {
        if (Objects.nonNull(proposalDetails.getPartyInformation())
                && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(index).getBasicDetails())) {
            BasicDetails basicDetails = proposalDetails.getPartyInformation().get(index).getBasicDetails();
            return Stream.of(
                    Utility.nullSafe(basicDetails.getFirstName()),
                    Utility.nullSafe(basicDetails.getMiddleName()),
                    Utility.nullSafe(basicDetails.getLastName()))
                    .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.joining(" "));
        }
        return EMPTY_STRING;
    }

    private String setCurrentDate(ProposalDetails proposalDetails) {
        if (Objects.nonNull(proposalDetails.getApplicationDetails())
                && Objects.nonNull(proposalDetails.getPaymentDetails())
                && Objects.nonNull(proposalDetails.getPaymentDetails().getReceipt())
                && !proposalDetails.getPaymentDetails().getReceipt().isEmpty()) {

            return Utility.getDateOnTheBasisOfRateChange(proposalDetails, false);
        }
        return EMPTY_STRING;
    }

    private void setQuestion1Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String traveledAbroad = "";
        String countryTravelled = "";
        String dateOfReturn = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            traveledAbroad = Utility.nullSafe(covidQuestionnaire.getTraveledAbroad());
            countryTravelled = covidQuestionnaire.getCountryTravelled();
            dateOfReturn = Utility.dateFormatter(covidQuestionnaire.getDateOfReturnFromAbroad());
        }
        dataVariables.put("traveledAbroad", Utility.convertToYesOrNoWithDefault(traveledAbroad));
        dataVariables.put("countryTravelled", countryTravelled);
        dataVariables.put("dateOfReturn", dateOfReturn);
    }

    private void setQuestion2Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String isTravelOverseas = "";
        String countryPlanningToTravel = "";
        String dateOfTravel = "";
        String durationOfStay = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            isTravelOverseas = covidQuestionnaire.getIsTravelOverseas();
            countryPlanningToTravel = covidQuestionnaire.getCountryPlanningToTravel();
            dateOfTravel = covidQuestionnaire.getIntendDateOfTravel();
            durationOfStay = covidQuestionnaire.getDurationOfStay();
        }
        dataVariables.put("isTravelOverseas", Utility.convertToYesOrNoWithDefault(isTravelOverseas));
        dataVariables.put("countryPlanningToTravel", countryPlanningToTravel);
        dataVariables.put("dateOfTravel", dateOfTravel);
        dataVariables.put("durationOfStay", durationOfStay);
    }

    private void setNewQuestion1Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String everTestedPositiveForCovid = "";
        String madeFullFunctionalRecovery = "";
        String lastDateOfPositiveDiagnosisYear = "";
        String lastDateOfPositiveDiagnosisMonth = "";
        String modeOfTreatmentCovid = "";
        String covidRecoveryPeriod = "";
        String haveCovid19RelatedComplications = "";
        String covidComplicationDetails = "";
        String isMechanicalVentilatorRequired = "";
        if (Objects.nonNull(covidQuestionnaire)) {
            everTestedPositiveForCovid = covidQuestionnaire.getEverTestedPositiveForCovid();
            madeFullFunctionalRecovery = covidQuestionnaire.getMadeFullFunctionalRecovery();
            lastDateOfPositiveDiagnosisYear = covidQuestionnaire.getLastDateOfPositiveDiagnosisYear();
            lastDateOfPositiveDiagnosisMonth =covidQuestionnaire.getLastDateOfPositiveDiagnosisMonth();
            if(Utility.isNotNullOrEmpty(lastDateOfPositiveDiagnosisMonth)){
                lastDateOfPositiveDiagnosisMonth= lastDateOfPositiveDiagnosisMonth.concat(" /");
            }
            modeOfTreatmentCovid = covidQuestionnaire.getModeOfTreatmentCovid();
            covidRecoveryPeriod = covidQuestionnaire.getCovidRecoveryPeriod();
            haveCovid19RelatedComplications = covidQuestionnaire.getHaveCovid19RelatedComplications();
            covidComplicationDetails = covidQuestionnaire.getCovidComplicationDetails();
            isMechanicalVentilatorRequired = covidQuestionnaire.getIsMechanicalVentilatorRequired();
        }
        dataVariables.put(EVER_TESTED_POSITIVE, Utility.convertToYesOrNoWithDefault(everTestedPositiveForCovid));
        dataVariables.put("madeFullFunctionalRecovery", Utility.convertToYesOrNoWithDefault(madeFullFunctionalRecovery));
        dataVariables.put("lastDateOfPositiveDiagnosisYear", lastDateOfPositiveDiagnosisYear);
        dataVariables.put("lastDateOfPositiveDiagnosisMonth", lastDateOfPositiveDiagnosisMonth);
        dataVariables.put("modeOfTreatmentCovid", Utility.convertToYesOrNoWithDefault(modeOfTreatmentCovid));
        dataVariables.put("covidRecoveryPeriod", covidRecoveryPeriod);
        dataVariables.put("haveCovid19RelatedComplications", Utility.convertToYesOrNoWithDefault(haveCovid19RelatedComplications));
        dataVariables.put("covidComplicationDetails", covidComplicationDetails);
        dataVariables.put("isMechanicalVentilatorRequired", Utility.convertToYesOrNoWithDefault(isMechanicalVentilatorRequired));
    }

    private void setNewQuestion2Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String haveYouBeenVaccinatedForCovid = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            haveYouBeenVaccinatedForCovid = covidQuestionnaire.getHaveYouBeenVaccinatedForCovid();
        }
        dataVariables.put(HAVE_COVID_VACCINATED, Utility.convertToYesOrNoWithDefault(haveYouBeenVaccinatedForCovid));
    }

    private void setQuestion3Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String familyMemberSymptomsCovid = "";
        String selfOrFamilyMember = "";
        String exactDiagnosis = "";
        String dateOfDiagnosis = "";
        String isRecoveredFromCovid = "";
        String dateOfRecovery = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            familyMemberSymptomsCovid = covidQuestionnaire.getFamilyMemberSymptomsCovid();
            selfOrFamilyMember = covidQuestionnaire.getSelfOrFamilyMember();
            exactDiagnosis = covidQuestionnaire.getExactDiagnosis();
            dateOfDiagnosis = covidQuestionnaire.getDateOfDiagnosis();
            isRecoveredFromCovid = !StringUtils.isEmpty(covidQuestionnaire.getIsRecoveredFromCovid())
                    ? Utility.convertToYesOrNo(covidQuestionnaire.getIsRecoveredFromCovid()) : EMPTY_STRING;
            dateOfRecovery = covidQuestionnaire.getDateOfRecovery();
        }

        dataVariables.put("familyMemberSymptomsCovid", Utility.convertToYesOrNoWithDefault(familyMemberSymptomsCovid));
        dataVariables.put("selfOrFamilyMember", selfOrFamilyMember);
        dataVariables.put("exactDiagnosis", exactDiagnosis);
        dataVariables.put("dateOfDiagnosis", dateOfDiagnosis);
        dataVariables.put("isRecoveredFromCovid", isRecoveredFromCovid);
        dataVariables.put("dateOfRecovery", dateOfRecovery);
    }

    private void setQuestion4Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String isSuspectedCovid = "";
        String dateOfCovidPositive = "";
        String nameOfCovidTest = "";
        String detailOfSubsequentTest = "";
        String hospitalAdmission = "";
        String requiredMedicalUnit = "";
        String requireMecdicalOtherUnit = "";
        String supportOfVentilator = "";
        String fullPhysicalFunctionRecovery = "";
        String whenDidFullRecovery = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            isSuspectedCovid = covidQuestionnaire.getIsSuspectedCovid();
            dateOfCovidPositive = covidQuestionnaire.getDateOfCovidPositive();
            nameOfCovidTest = covidQuestionnaire.getNameOfCovidTest();
            detailOfSubsequentTest = covidQuestionnaire.getDetailOfSubsequentTest();
            hospitalAdmission = covidQuestionnaire.getAdmitHospital();
            requiredMedicalUnit = covidQuestionnaire.getRequireMedicalUnit();
            requireMecdicalOtherUnit = covidQuestionnaire.getRequireMedicalOtherUnit();
            supportOfVentilator = covidQuestionnaire.getRequireMechanicalSupport();
            fullPhysicalFunctionRecovery = covidQuestionnaire.getFullPhysicalFunctionRecovery();
            whenDidFullRecovery = Utility.dateFormatter(covidQuestionnaire.getWhenDidFullRecovery());
        }
        dataVariables.put("isSuspectedCovid", Utility.convertToYesOrNoWithDefault(isSuspectedCovid));
        dataVariables.put("dateOfCovidPositive", dateOfCovidPositive);
        dataVariables.put("nameOfCovidTest", nameOfCovidTest);
        dataVariables.put("detailOfSubsequentTest", detailOfSubsequentTest);
        dataVariables.put("hospitalAdmission", Utility.convertToYesOrNoWithDefault(hospitalAdmission));
        dataVariables.put("requiredMedicalUnit", requiredMedicalUnit);
        dataVariables.put("requireMedicalOtherUnit", requireMecdicalOtherUnit);
        dataVariables.put("supportOfVentilator", Utility.convertToYesOrNoWithDefault(supportOfVentilator));
        dataVariables.put("fullPhysicalFunctionRecovery", Utility.convertToYesOrNoWithDefault(fullPhysicalFunctionRecovery));
        dataVariables.put("whenDidFullRecovery", whenDidFullRecovery);
    }

    private void setQuestion5Answers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String servingQuarantineNotice = "";
        String detailLocation = "";
        String quarantineStartDate = "";
        String quarantineEndDate = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            servingQuarantineNotice = covidQuestionnaire.getServingQuarantineNotice();
            detailLocation = covidQuestionnaire.getDetailLocation();
            quarantineStartDate = Utility.dateFormatter(covidQuestionnaire.getQuarantineStartDate());
            quarantineEndDate = Utility.dateFormatter(covidQuestionnaire.getQuarantineEndDate());
        }
        dataVariables.put("servingQuarantineNotice", Utility.convertToYesOrNoWithDefault(servingQuarantineNotice));
        dataVariables.put("detailLocation", detailLocation);
        dataVariables.put("quarantineStartDate", quarantineStartDate);
        dataVariables.put("quarantineEndDate", quarantineEndDate);
    }

    private void setExposureQuestionForHealthCareWorkers(Map<String, Object> dataVariables, CovidQuestionnaire covidQuestionnaire) {
        String occupation = "";
        String medicalSpecialty = "";
        String natureOfDuties = "";
        String healthCareNameAndAddress = "";
        String healthAuthorityName = "";
        String healthCareFacilityPPE = "";
        String closeContactWithQuarantined = "";
        String natureOfWorkForPatients = "";
        String voluntaryOrCompulsoryLeave = "";
        String dateOfLeave = "";
        String detailLeaveInformation = "";
        String currentlyGoodHealth = "";
        String healthDetails = "";
        String covidDeclaration = "";

        if (Objects.nonNull(covidQuestionnaire)) {
            occupation = covidQuestionnaire.getOccupationOfHealthWorker();
            medicalSpecialty = covidQuestionnaire.getMedicalSpecialty();
            natureOfDuties = covidQuestionnaire.getNatureOfDuty();
            healthCareNameAndAddress = covidQuestionnaire.getAddressOfHealthcareFacility();
            healthAuthorityName = covidQuestionnaire.getHealthAuthorityName();
            healthCareFacilityPPE = covidQuestionnaire.getHealthcareFacilityHavePPE();

            closeContactWithQuarantined = covidQuestionnaire.getCloseContactWithQuarantined();
            natureOfWorkForPatients = covidQuestionnaire.getNatureOfWorkForPatients();
            voluntaryOrCompulsoryLeave = covidQuestionnaire.getVoluntaryOrCompulsoryLeave();
            dateOfLeave = Utility.dateFormatter(covidQuestionnaire.getDateOfLeave());
            detailLeaveInformation = covidQuestionnaire.getDetailLeaveInformation();
            currentlyGoodHealth = covidQuestionnaire.getCurrentlyGoodHealth();
            healthDetails = covidQuestionnaire.getHealthDetails();
            covidDeclaration = covidQuestionnaire.getCovidDeclaration();
        }
        dataVariables.put("occupation", occupation);
        dataVariables.put("medicalSpecialty", medicalSpecialty);
        dataVariables.put("natureOfDuties", natureOfDuties);
        dataVariables.put("healthCareNameAndAddress", healthCareNameAndAddress);
        dataVariables.put("healthAuthorityName", healthAuthorityName);
        dataVariables.put("healthCareFacilityPPE", Utility.convertToYesOrNoWithDefault(healthCareFacilityPPE));

        dataVariables.put("closeContactWithQuarantined", Utility.convertToYesOrNoWithDefault(closeContactWithQuarantined));
        dataVariables.put("natureOfWorkForPatients", natureOfWorkForPatients);
        dataVariables.put("voluntaryOrCompulsoryLeave", Utility.convertToYesOrNoWithDefault(voluntaryOrCompulsoryLeave));
        dataVariables.put("dateOfLeave", dateOfLeave);
        dataVariables.put("detailLeaveInformation", detailLeaveInformation);
        dataVariables.put("currentlyGoodHealth", Utility.convertToYesOrNoWithDefault(currentlyGoodHealth));
        dataVariables.put("healthDetails", healthDetails);
        dataVariables.put("covidDeclaration", Utility.convertToYesOrNoWithDefault(covidDeclaration));
    }

    public Context decideDefaultTemplate(ProposalDetails proposalDetails) {
        Context context = new Context();
        Map<String, Object> dataMap = new HashMap<>();
        if (Utility.isNewCovidQuestionApplicable(proposalDetails)) {
            LifeStyleDetails lifeStyleDetails = proposalDetails.getLifeStyleDetails().stream()
                .filter(lifeStyleDetails1 -> lifeStyleDetails1.getPartyType().equalsIgnoreCase("lifeInsured")).findFirst().orElse(null);
            dataMap.put(TEMPLATE_TYPE, "NEW");
            if (Objects.nonNull(lifeStyleDetails)) {
                dataMap.put(EVER_TESTED_POSITIVE, Utility.convertToYesOrNoWithDefault(lifeStyleDetails.getCovidQuestionnaire().getEverTestedPositiveForCovid()));
                dataMap.put(HAVE_COVID_VACCINATED, Utility.convertToYesOrNoWithDefault(lifeStyleDetails.getCovidQuestionnaire().getHaveYouBeenVaccinatedForCovid()));
            } else {
                dataMap.put(EVER_TESTED_POSITIVE, "NO");
                dataMap.put(HAVE_COVID_VACCINATED, "NO");
            }
            if(Utility.isSSPJLProduct(proposalDetails)){
                dataMap.put(EVER_TESTED_POSITIVE_JL, "NO");
                dataMap.put(HAVE_COVID_VACCINATED_JL, "NO");
            }
        } else {
            dataMap.put(TEMPLATE_TYPE, "OLD");
        }
        context.setVariables(dataMap);
        return context;
    }
}
