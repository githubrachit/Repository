
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "whenWasItDiagnosed", "symptomsDetails", "howOftenSymptomsOccur", "medicationDetails", "haveYouTakenSteriods" })
public class AsthmaDetails {

    @JsonProperty("whenWasItDiagnosed")
    private String whenWasItDiagnosed;
    @JsonProperty("symptomsDetails")
    private String symptomsDetails;
    @JsonProperty("howOftenSymptomsOccur")
    private String howOftenSymptomsOccur;
    @JsonProperty("medicationDetails")
    private String medicationDetails;
    @JsonProperty("haveYouTakenSteriods")
    private String haveYouTakenSteriods;

    @JsonProperty("frequencyAndSeverity")
    private String frequencyAndSeverity;
    @JsonProperty("typeOfTreatment")
    private String typeOfTreatment;
    @JsonProperty("everAdmittedToHospital")
    private String everAdmittedToHospital;
    @JsonProperty("everSmokedCigOrTobacco")
    private String everSmokedCigOrTobacco;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AsthmaDetails() {
    }

    /**
     * 
     * @param howOftenSymptomsOccur
     * @param whenWasItDiagnosed
     * @param symptomsDetails
     * @param medicationDetails
     * @param haveYouTakenSteriods
     */
    public AsthmaDetails(String whenWasItDiagnosed, String symptomsDetails, String howOftenSymptomsOccur, String medicationDetails,
	    String haveYouTakenSteriods,String frequencyAndSeverity,String typeOfTreatment,String everAdmittedToHospital,String everSmokedCigOrTobacco) {
	super();
	this.whenWasItDiagnosed = whenWasItDiagnosed;
	this.symptomsDetails = symptomsDetails;
	this.howOftenSymptomsOccur = howOftenSymptomsOccur;
	this.medicationDetails = medicationDetails;
	this.haveYouTakenSteriods = haveYouTakenSteriods;
    this.frequencyAndSeverity = frequencyAndSeverity;
    this.typeOfTreatment = typeOfTreatment;
    this.everAdmittedToHospital = everAdmittedToHospital;
    this.everSmokedCigOrTobacco = everSmokedCigOrTobacco;
    }

    @JsonProperty("whenWasItDiagnosed")
    public String getWhenWasItDiagnosed() {
	return whenWasItDiagnosed;
    }

    @JsonProperty("whenWasItDiagnosed")
    public void setWhenWasItDiagnosed(String whenWasItDiagnosed) {
	this.whenWasItDiagnosed = whenWasItDiagnosed;
    }

    @JsonProperty("symptomsDetails")
    public String getSymptomsDetails() {
	return symptomsDetails;
    }

    @JsonProperty("symptomsDetails")
    public void setSymptomsDetails(String symptomsDetails) {
	this.symptomsDetails = symptomsDetails;
    }

    @JsonProperty("howOftenSymptomsOccur")
    public String getHowOftenSymptomsOccur() {
	return howOftenSymptomsOccur;
    }

    @JsonProperty("howOftenSymptomsOccur")
    public void setHowOftenSymptomsOccur(String howOftenSymptomsOccur) {
	this.howOftenSymptomsOccur = howOftenSymptomsOccur;
    }

    @JsonProperty("medicationDetails")
    public String getMedicationDetails() {
	return medicationDetails;
    }

    @JsonProperty("medicationDetails")
    public void setMedicationDetails(String medicationDetails) {
	this.medicationDetails = medicationDetails;
    }

    @JsonProperty("haveYouTakenSteriods")
    public String getHaveYouTakenSteriods() {
	return haveYouTakenSteriods;
    }

    @JsonProperty("haveYouTakenSteriods")
    public void setHaveYouTakenSteriods(String haveYouTakenSteriods) {
	this.haveYouTakenSteriods = haveYouTakenSteriods;
    }
    @JsonProperty("frequencyAndSeverity")
    public String getFrequencyAndSeverity() {
        return frequencyAndSeverity;
    }
    @JsonProperty("frequencyAndSeverity")
    public void setFrequencyAndSeverity(String frequencyAndSeverity) {
        this.frequencyAndSeverity = frequencyAndSeverity;
    }
    @JsonProperty("typeOfTreatment")
    public String getTypeOfTreatment() {
        return typeOfTreatment;
    }
    @JsonProperty("typeOfTreatment")
    public void setTypeOfTreatment(String typeOfTreatment) {
        this.typeOfTreatment = typeOfTreatment;
    }
    @JsonProperty("everAdmittedToHospital")
    public String getEverAdmittedToHospital() {
        return everAdmittedToHospital;
    }
    @JsonProperty("everAdmittedToHospital")
    public void setEverAdmittedToHospital(String everAdmittedToHospital) {
        this.everAdmittedToHospital = everAdmittedToHospital;
    }
    @JsonProperty("everSmokedCigOrTobacco")
    public String getEverSmokedCigOrTobacco() {
        return everSmokedCigOrTobacco;
    }
    @JsonProperty("everSmokedCigOrTobacco")
    public void setEverSmokedCigOrTobacco(String everSmokedCigOrTobacco) {
        this.everSmokedCigOrTobacco = everSmokedCigOrTobacco;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "AsthmaDetails [whenWasItDiagnosed=" + whenWasItDiagnosed + ", symptomsDetails=" + symptomsDetails + ", howOftenSymptomsOccur="
		+ howOftenSymptomsOccur + ", medicationDetails=" + medicationDetails + ", haveYouTakenSteriods=" + haveYouTakenSteriods + "]";
    }

}
