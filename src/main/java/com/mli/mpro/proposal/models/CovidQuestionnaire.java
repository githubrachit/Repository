package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.StringJoiner;

/**
 * @author manish on 11/11/20
 */
public class CovidQuestionnaire {

    @JsonProperty("planToTravelOverseas")
    private String planToTravelOverseas;
    @JsonProperty("areYouCovidWarrior")
    private String areYouCovidWarrior;
    @JsonProperty("traveledAbroad")
    private String traveledAbroad;
    @JsonProperty("countryTravelled")
    private String countryTravelled;
    @JsonProperty("dateOfReturnFromAbroad")
    private Date dateOfReturnFromAbroad;
    @JsonProperty("isTravelOverseas")
    private String isTravelOverseas;
    @JsonProperty("countryPlanningToTravel")
    private String countryPlanningToTravel;
    @JsonProperty("intendDateOfTravel")
    private String intendDateOfTravel;
    @JsonProperty("durationOfStay")
    private String durationOfStay;
    @JsonProperty("familyMemberSymptomsCovid")
    private String familyMemberSymptomsCovid;
    @JsonProperty("selfOrFamilyMember")
    private String selfOrFamilyMember;
    @JsonProperty("exactDiagnosis")
    private String exactDiagnosis;
    @JsonProperty("dateOfDiagnosis")
    private String dateOfDiagnosis;
    @JsonProperty("isRecoveredFromCovid")
    private String isRecoveredFromCovid;
    @JsonProperty("dateOfRecovery")
    private String dateOfRecovery;
    @JsonProperty("isSuspectedCovid")
    private String isSuspectedCovid;
    @JsonProperty("dateOfCovidPositive")
    private String dateOfCovidPositive;
    @JsonProperty("nameOfCovidTest")
    private String nameOfCovidTest;
    @JsonProperty("detailOfSubsequentTest")
    private String detailOfSubsequentTest;
    @JsonProperty("admitHospital")
    private String admitHospital;
    @JsonProperty("requireMedicalUnit")
    private String requireMedicalUnit;
    @JsonProperty("requireMedicalOtherUnit")
    private String requireMedicalOtherUnit;
    @JsonProperty("requireMechanicalSupport")
    private String requireMechanicalSupport;
    @JsonProperty("fullPhysicalFunctionRecovery")
    private String fullPhysicalFunctionRecovery;
    @JsonProperty("whenDidFullRecovery")
    private Date whenDidFullRecovery;
    @JsonProperty("servingQuarantineNotice")
    private String servingQuarantineNotice;
    @JsonProperty("detailLocation")
    private String detailLocation;
    @JsonProperty("quarantineStartDate")
    private Date quarantineStartDate;
    @JsonProperty("quarantineEndDate")
    private Date quarantineEndDate;
    @JsonProperty("isHealthWorker")
    private String isHealthWorker;
    @JsonProperty("occupationOfHealthWorker")
    private String occupationOfHealthWorker;
    @JsonProperty("medicalSpecialty")
    private String medicalSpecialty;
    @JsonProperty("natureOfDuty")
    private String natureOfDuty;
    @JsonProperty("addressOfHealthcareFacility")
    private String addressOfHealthcareFacility;
    @JsonProperty("healthAuthorityName")
    private String healthAuthorityName;
    @JsonProperty("healthcareFacilityHavePPE")
    private String healthcareFacilityHavePPE;
    @JsonProperty("closeContactWithQuarantined")
    private String closeContactWithQuarantined;
    @JsonProperty("natureOfWorkForPatients")
    private String natureOfWorkForPatients;
    @JsonProperty("voluntaryOrCompulsoryLeave")
    private String voluntaryOrCompulsoryLeave;
    @JsonProperty("dateOfLeave")
    private Date dateOfLeave;
    @JsonProperty("detailLeaveInformation")
    private String detailLeaveInformation;
    @JsonProperty("currentlyGoodHealth")
    private String currentlyGoodHealth;
    @JsonProperty("healthDetails")
    private String healthDetails;
    @JsonProperty("covidAdditionalDetail")
    private String covidAdditionalDetail;
    @JsonProperty("covidDeclaration")
    private String covidDeclaration;
    @JsonProperty("everTestedPositiveForCovid")
    private String everTestedPositiveForCovid;
    @JsonProperty("madeFullFunctionalRecovery")
    private String madeFullFunctionalRecovery;
    @JsonProperty("lastDateOfPositiveDiagnosisYear")
    private String lastDateOfPositiveDiagnosisYear;
    @JsonProperty("lastDateOfPositiveDiagnosisMonth")
    private String lastDateOfPositiveDiagnosisMonth;
    @JsonProperty("modeOfTreatmentCovid")
    private String modeOfTreatmentCovid;
    @JsonProperty("covidRecoveryPeriod")
    private String covidRecoveryPeriod;
    @JsonProperty("haveCovid19RelatedComplications")
    private String haveCovid19RelatedComplications;
    @JsonProperty("covidComplicationDetails")
    private String covidComplicationDetails;
    @JsonProperty("haveYouBeenVaccinatedForCovid")
    private String haveYouBeenVaccinatedForCovid;
    @JsonProperty("covidVaccinationStatus")
    private String covidVaccinationStatus;
    @JsonProperty("vaccinatedSince")
    private String vaccinatedSince;
    @JsonProperty("haveComplicationPostVaccination")
    private String haveComplicationPostVaccination;
    @JsonProperty("covidPostVaccinationComplicationDetails")
    private String covidPostVaccinationComplicationDetails;
    @JsonProperty("isNewCovidQuestionApplicable")
    private String isNewCovidQuestionApplicable;
    @JsonProperty("covidFlag")
    private String covidFlag;
    @JsonProperty("isMechanicalVentilatorRequired")
    private String isMechanicalVentilatorRequired;
    @JsonProperty("covidAnnexureApplicable")
    private String covidAnnexureApplicable;


    public CovidQuestionnaire() {
        // public constructor
         }

    public String getPlanToTravelOverseas() {
        return planToTravelOverseas;
    }

    public void setPlanToTravelOverseas(String planToTravelOverseas) {
        this.planToTravelOverseas = planToTravelOverseas;
    }

    public String getAreYouCovidWarrior() {
        return areYouCovidWarrior;
    }

    public void setAreYouCovidWarrior(String areYouCovidWarrior) {
        this.areYouCovidWarrior = areYouCovidWarrior;
    }

    public String getTraveledAbroad() {
        return traveledAbroad;
    }

    public void setTraveledAbroad(String traveledAbroad) {
        this.traveledAbroad = traveledAbroad;
    }

    public String getCountryTravelled() {
        return countryTravelled;
    }

    public void setCountryTravelled(String countryTravelled) {
        this.countryTravelled = countryTravelled;
    }

    public Date getDateOfReturnFromAbroad() {
        return dateOfReturnFromAbroad;
    }

    public void setDateOfReturnFromAbroad(Date dateOfReturnFromAbroad) {
        this.dateOfReturnFromAbroad = dateOfReturnFromAbroad;
    }

    public String getIsTravelOverseas() {
        return isTravelOverseas;
    }

    public void setIsTravelOverseas(String isTravelOverseas) {
        this.isTravelOverseas = isTravelOverseas;
    }

    public String getCountryPlanningToTravel() {
        return countryPlanningToTravel;
    }

    public void setCountryPlanningToTravel(String countryPlanningToTravel) {
        this.countryPlanningToTravel = countryPlanningToTravel;
    }

    public String getIntendDateOfTravel() {
        return intendDateOfTravel;
    }

    public void setIntendDateOfTravel(String intendDateOfTravel) {
        this.intendDateOfTravel = intendDateOfTravel;
    }

    public String getDurationOfStay() {
        return durationOfStay;
    }

    public void setDurationOfStay(String durationOfStay) {
        this.durationOfStay = durationOfStay;
    }

    public String getFamilyMemberSymptomsCovid() {
        return familyMemberSymptomsCovid;
    }

    public void setFamilyMemberSymptomsCovid(String familyMemberSymptomsCovid) {
        this.familyMemberSymptomsCovid = familyMemberSymptomsCovid;
    }

    public String getSelfOrFamilyMember() {
        return selfOrFamilyMember;
    }

    public void setSelfOrFamilyMember(String selfOrFamilyMember) {
        this.selfOrFamilyMember = selfOrFamilyMember;
    }

    public String getExactDiagnosis() {
        return exactDiagnosis;
    }

    public void setExactDiagnosis(String exactDiagnosis) {
        this.exactDiagnosis = exactDiagnosis;
    }

    public String getDateOfDiagnosis() {
        return dateOfDiagnosis;
    }

    public void setDateOfDiagnosis(String dateOfDiagnosis) {
        this.dateOfDiagnosis = dateOfDiagnosis;
    }

    public String getIsRecoveredFromCovid() {
        return isRecoveredFromCovid;
    }

    public void setIsRecoveredFromCovid(String isRecoveredFromCovid) {
        this.isRecoveredFromCovid = isRecoveredFromCovid;
    }

    public String getDateOfRecovery() {
        return dateOfRecovery;
    }

    public void setDateOfRecovery(String dateOfRecovery) {
        this.dateOfRecovery = dateOfRecovery;
    }

    public String getIsSuspectedCovid() {
        return isSuspectedCovid;
    }

    public void setIsSuspectedCovid(String isSuspectedCovid) {
        this.isSuspectedCovid = isSuspectedCovid;
    }

    public String getDateOfCovidPositive() {
        return dateOfCovidPositive;
    }

    public void setDateOfCovidPositive(String dateOfCovidPositive) {
        this.dateOfCovidPositive = dateOfCovidPositive;
    }

    public String getNameOfCovidTest() {
        return nameOfCovidTest;
    }

    public void setNameOfCovidTest(String nameOfCovidTest) {
        this.nameOfCovidTest = nameOfCovidTest;
    }

    public String getDetailOfSubsequentTest() {
        return detailOfSubsequentTest;
    }

    public void setDetailOfSubsequentTest(String detailOfSubsequentTest) {
        this.detailOfSubsequentTest = detailOfSubsequentTest;
    }

    public String getAdmitHospital() {
        return admitHospital;
    }

    public void setAdmitHospital(String admitHospital) {
        this.admitHospital = admitHospital;
    }

    public String getRequireMedicalUnit() {
        return requireMedicalUnit;
    }

    public void setRequireMedicalUnit(String requireMedicalUnit) {
        this.requireMedicalUnit = requireMedicalUnit;
    }

    public String getRequireMedicalOtherUnit() {
        return requireMedicalOtherUnit;
    }

    public void setRequireMedicalOtherUnit(String requireMedicalOtherUnit) {
        this.requireMedicalOtherUnit = requireMedicalOtherUnit;
    }

    public String getRequireMechanicalSupport() {
        return requireMechanicalSupport;
    }

    public void setRequireMechanicalSupport(String requireMechanicalSupport) {
        this.requireMechanicalSupport = requireMechanicalSupport;
    }

    public String getFullPhysicalFunctionRecovery() {
        return fullPhysicalFunctionRecovery;
    }

    public void setFullPhysicalFunctionRecovery(String fullPhysicalFunctionRecovery) {
        this.fullPhysicalFunctionRecovery = fullPhysicalFunctionRecovery;
    }

    public Date getWhenDidFullRecovery() {
        return whenDidFullRecovery;
    }

    public void setWhenDidFullRecovery(Date whenDidFullRecovery) {
        this.whenDidFullRecovery = whenDidFullRecovery;
    }

    public String getServingQuarantineNotice() {
        return servingQuarantineNotice;
    }

    public void setServingQuarantineNotice(String servingQuarantineNotice) {
        this.servingQuarantineNotice = servingQuarantineNotice;
    }

    public String getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(String detailLocation) {
        this.detailLocation = detailLocation;
    }

    public Date getQuarantineStartDate() {
        return quarantineStartDate;
    }

    public void setQuarantineStartDate(Date quarantineStartDate) {
        this.quarantineStartDate = quarantineStartDate;
    }

    public Date getQuarantineEndDate() {
        return quarantineEndDate;
    }

    public void setQuarantineEndDate(Date quarantineEndDate) {
        this.quarantineEndDate = quarantineEndDate;
    }

    public String getIsHealthWorker() {
        return isHealthWorker;
    }

    public void setIsHealthWorker(String isHealthWorker) {
        this.isHealthWorker = isHealthWorker;
    }

    public String getOccupationOfHealthWorker() {
        return occupationOfHealthWorker;
    }

    public void setOccupationOfHealthWorker(String occupationOfHealthWorker) {
        this.occupationOfHealthWorker = occupationOfHealthWorker;
    }

    public String getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public void setMedicalSpecialty(String medicalSpecialty) {
        this.medicalSpecialty = medicalSpecialty;
    }

    public String getNatureOfDuty() {
        return natureOfDuty;
    }

    public void setNatureOfDuty(String natureOfDuty) {
        this.natureOfDuty = natureOfDuty;
    }

    public String getAddressOfHealthcareFacility() {
        return addressOfHealthcareFacility;
    }

    public void setAddressOfHealthcareFacility(String addressOfHealthcareFacility) {
        this.addressOfHealthcareFacility = addressOfHealthcareFacility;
    }

    public String getHealthAuthorityName() {
        return healthAuthorityName;
    }

    public void setHealthAuthorityName(String healthAuthorityName) {
        this.healthAuthorityName = healthAuthorityName;
    }

    public String getHealthcareFacilityHavePPE() {
        return healthcareFacilityHavePPE;
    }

    public void setHealthcareFacilityHavePPE(String healthcareFacilityHavePPE) {
        this.healthcareFacilityHavePPE = healthcareFacilityHavePPE;
    }

    public String getCloseContactWithQuarantined() {
        return closeContactWithQuarantined;
    }

    public void setCloseContactWithQuarantined(String closeContactWithQuarantined) {
        this.closeContactWithQuarantined = closeContactWithQuarantined;
    }

    public String getNatureOfWorkForPatients() {
        return natureOfWorkForPatients;
    }

    public void setNatureOfWorkForPatients(String natureOfWorkForPatients) {
        this.natureOfWorkForPatients = natureOfWorkForPatients;
    }

    public String getVoluntaryOrCompulsoryLeave() {
        return voluntaryOrCompulsoryLeave;
    }

    public void setVoluntaryOrCompulsoryLeave(String voluntaryOrCompulsoryLeave) {
        this.voluntaryOrCompulsoryLeave = voluntaryOrCompulsoryLeave;
    }

    public Date getDateOfLeave() {
        return dateOfLeave;
    }

    public void setDateOfLeave(Date dateOfLeave) {
        this.dateOfLeave = dateOfLeave;
    }

    public String getDetailLeaveInformation() {
        return detailLeaveInformation;
    }

    public void setDetailLeaveInformation(String detailLeaveInformation) {
        this.detailLeaveInformation = detailLeaveInformation;
    }

    public String getCurrentlyGoodHealth() {
        return currentlyGoodHealth;
    }

    public void setCurrentlyGoodHealth(String currentlyGoodHealth) {
        this.currentlyGoodHealth = currentlyGoodHealth;
    }

    public String getHealthDetails() {
        return healthDetails;
    }

    public void setHealthDetails(String healthDetails) {
        this.healthDetails = healthDetails;
    }

    public String getCovidAdditionalDetail() {
        return covidAdditionalDetail;
    }

    public void setCovidAdditionalDetail(String covidAdditionalDetail) {
        this.covidAdditionalDetail = covidAdditionalDetail;
    }

    public String getCovidDeclaration() {
        return covidDeclaration;
    }

    public void setCovidDeclaration(String covidDeclaration) {
        this.covidDeclaration = covidDeclaration;
    }


	public void setHaveComplicationPostVaccination(String haveComplicationPostVaccination) {
		this.haveComplicationPostVaccination = haveComplicationPostVaccination;
	}

	public String getCovidPostVaccinationComplicationDetails() {
		return covidPostVaccinationComplicationDetails;
	}

	public void setCovidPostVaccinationComplicationDetails(String covidPostVaccinationComplicationDetails) {
		this.covidPostVaccinationComplicationDetails = covidPostVaccinationComplicationDetails;
	}

    public String getEverTestedPositiveForCovid() {
        return everTestedPositiveForCovid;
    }

    public void setEverTestedPositiveForCovid(String everTestedPositiveForCovid) {
        this.everTestedPositiveForCovid = everTestedPositiveForCovid;
    }

    public String getMadeFullFunctionalRecovery() {
        return madeFullFunctionalRecovery;
    }

    public void setMadeFullFunctionalRecovery(String madeFullFunctionalRecovery) {
        this.madeFullFunctionalRecovery = madeFullFunctionalRecovery;
    }

    public String getLastDateOfPositiveDiagnosisYear() {
        return lastDateOfPositiveDiagnosisYear;
    }

    public void setLastDateOfPositiveDiagnosisYear(String lastDateOfPositiveDiagnosisYear) {
        this.lastDateOfPositiveDiagnosisYear = lastDateOfPositiveDiagnosisYear;
    }

    public String getLastDateOfPositiveDiagnosisMonth() {
        return lastDateOfPositiveDiagnosisMonth;
    }

    public void setLastDateOfPositiveDiagnosisMonth(String lastDateOfPositiveDiagnosisMonth) {
        this.lastDateOfPositiveDiagnosisMonth = lastDateOfPositiveDiagnosisMonth;
    }

    public String getModeOfTreatmentCovid() {
        return modeOfTreatmentCovid;
    }

    public void setModeOfTreatmentCovid(String modeOfTreatmentCovid) {
        this.modeOfTreatmentCovid = modeOfTreatmentCovid;
    }

    public String getCovidRecoveryPeriod() {
        return covidRecoveryPeriod;
    }

    public void setCovidRecoveryPeriod(String covidRecoveryPeriod) {
        this.covidRecoveryPeriod = covidRecoveryPeriod;
    }

    public String getHaveCovid19RelatedComplications() {
        return haveCovid19RelatedComplications;
    }

    public void setHaveCovid19RelatedComplications(String haveCovid19RelatedComplications) {
        this.haveCovid19RelatedComplications = haveCovid19RelatedComplications;
    }

    public String getCovidComplicationDetails() {
        return covidComplicationDetails;
    }

    public void setCovidComplicationDetails(String covidComplicationDetails) {
        this.covidComplicationDetails = covidComplicationDetails;
    }

    public String getHaveYouBeenVaccinatedForCovid() {
        return haveYouBeenVaccinatedForCovid;
    }

    public void setHaveYouBeenVaccinatedForCovid(String haveYouBeenVaccinatedForCovid) {
        this.haveYouBeenVaccinatedForCovid = haveYouBeenVaccinatedForCovid;
    }

    public String getCovidVaccinationStatus() {
        return covidVaccinationStatus;
    }

    public void setCovidVaccinationStatus(String covidVaccinationStatus) {
        this.covidVaccinationStatus = covidVaccinationStatus;
    }

    public String getVaccinatedSince() {
        return vaccinatedSince;
    }

    public void setVaccinatedSince(String vaccinatedSince) {
        this.vaccinatedSince = vaccinatedSince;
    }

    public String getHaveComplicationPostVaccination() {
        return haveComplicationPostVaccination;
    }

    public String getIsNewCovidQuestionApplicable() {
        return isNewCovidQuestionApplicable;
    }

    public void setIsNewCovidQuestionApplicable(String isNewCovidQuestionApplicable) {
        this.isNewCovidQuestionApplicable = isNewCovidQuestionApplicable;
    }

    public String getCovidFlag() {
        return covidFlag;
    }

    public void setCovidFlag(String covidFlag) {
        this.covidFlag = covidFlag;
    }

    public String getIsMechanicalVentilatorRequired() {
        return isMechanicalVentilatorRequired;
    }

    public void setIsMechanicalVentilatorRequired(String isMechanicalVentilatorRequired) {
        this.isMechanicalVentilatorRequired = isMechanicalVentilatorRequired;
    }

    public String getCovidAnnexureApplicable() {
        return covidAnnexureApplicable;
    }

    public void setCovidAnnexureApplicable(String covidAnnexureApplicable) {
        this.covidAnnexureApplicable = covidAnnexureApplicable;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", CovidQuestionnaire.class.getSimpleName() + "[", "]")
                .add("planToTravelOverseas='" + planToTravelOverseas + "'")
                .add("areYouCovidWarrior='" + areYouCovidWarrior + "'")
                .add("traveledAbroad='" + traveledAbroad + "'")
                .add("countryTravelled='" + countryTravelled + "'")
                .add("dateOfReturnFromAbroad=" + dateOfReturnFromAbroad)
                .add("isTravelOverseas='" + isTravelOverseas + "'")
                .add("countryPlanningToTravel='" + countryPlanningToTravel + "'")
                .add("intendDateOfTravel=" + intendDateOfTravel)
                .add("durationOfStay='" + durationOfStay + "'")
                .add("familyMemberSymptomsCovid='" + familyMemberSymptomsCovid + "'")
                .add("selfOrFamilyMember='" + selfOrFamilyMember + "'")
                .add("exactDiagnosis='" + exactDiagnosis + "'")
                .add("dateOfDiagnosis='" + dateOfDiagnosis + "'")
                .add("isRecoveredFromCovid='" + isRecoveredFromCovid + "'")
                .add("dateOfRecovery=" + dateOfRecovery)
                .add("isSuspectedCovid='" + isSuspectedCovid + "'")
                .add("dateOfCovidPositive=" + dateOfCovidPositive)
                .add("nameOfCovidTest='" + nameOfCovidTest + "'")
                .add("detailOfSubsequentTest='" + detailOfSubsequentTest + "'")
                .add("admitHospital='" + admitHospital + "'")
                .add("requireMedicalUnit='" + requireMedicalUnit + "'")
                .add("requireMedicalOtherUnit='" + requireMedicalOtherUnit + "'")
                .add("requireMechanicalSupport='" + requireMechanicalSupport + "'")
                .add("fullPhysicalFunctionRecovery='" + fullPhysicalFunctionRecovery + "'")
                .add("whenDidFullRecovery=" + whenDidFullRecovery)
                .add("servingQuarantineNotice='" + servingQuarantineNotice + "'")
                .add("detailLocation='" + detailLocation + "'")
                .add("quarantineStartDate='" + quarantineStartDate + "'")
                .add("quarantineEndDate='" + quarantineEndDate + "'")
                .add("isHealthWorker='" + isHealthWorker + "'")
                .add("occupationOfHealthWorker='" + occupationOfHealthWorker + "'")
                .add("medicalSpecialty='" + medicalSpecialty + "'")
                .add("natureOfDuty='" + natureOfDuty + "'")
                .add("addressOfHealthcareFacility='" + addressOfHealthcareFacility + "'")
                .add("healthAuthorityName='" + healthAuthorityName + "'")
                .add("healthcareFacilityHavePPE='" + healthcareFacilityHavePPE + "'")
                .add("closeContactWithQuarantined='" + closeContactWithQuarantined + "'")
                .add("natureOfWorkForPatients='" + natureOfWorkForPatients + "'")
                .add("voluntaryOrCompulsoryLeave='" + voluntaryOrCompulsoryLeave + "'")
                .add("dateOfLeave=" + dateOfLeave)
                .add("detailLeaveInformation='" + detailLeaveInformation + "'")
                .add("currentlyGoodHealth='" + currentlyGoodHealth + "'")
                .add("healthDetails='" + healthDetails + "'")
                .add("covidAdditionalDetail='" + covidAdditionalDetail + "'")
                .add("covidDeclaration='" + covidDeclaration + "'")
                .add("everTestedPositiveForCovid='" + everTestedPositiveForCovid + "'")
                .add("madeFullFunctionalRecovery='" + madeFullFunctionalRecovery + "'")
                .add("lastDateOfPositiveDiagnosisYear='" + lastDateOfPositiveDiagnosisYear + "'")
                .add("lastDateOfPositiveDiagnosisMonth='" + lastDateOfPositiveDiagnosisMonth + "'")
                .add("modeOfTreatmentCovid='" + modeOfTreatmentCovid + "'")
                .add("covidRecoveryPeriod='" + covidRecoveryPeriod + "'")
                .add("haveCovid19RelatedComplications='" + haveCovid19RelatedComplications + "'")
                .add("covidComplicationDetails='" + covidComplicationDetails + "'")
                .add("haveYouBeenVaccinatedForCovid='" + haveYouBeenVaccinatedForCovid + "'")
                .add("covidVaccinationStatus='" + covidVaccinationStatus + "'")
                .add("vaccinatedSince='" + vaccinatedSince + "'")
                .add("haveComplicationPostVaccination='" + haveComplicationPostVaccination + "'")
                .add("covidPostVaccinationComplicationDetails='" + covidPostVaccinationComplicationDetails + "'")
                .add("isNewCovidQuestioApplicable='" + isNewCovidQuestionApplicable + "'")
                .add("covidFlag='" + covidFlag + "'")
                .add("isMechanicalVentilatorRequired='" + isMechanicalVentilatorRequired + "'")
                .add("covidAnnexureApplicable='" + covidAnnexureApplicable + "'")
                .toString();

    }
}
