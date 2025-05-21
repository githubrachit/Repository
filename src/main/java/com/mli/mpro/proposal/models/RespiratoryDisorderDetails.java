
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "whenWasItDiagnosed", "symptomsDetails", "howOftenSymptomsOccur", "medicationDetails", "haveYouTakenSteriods" })
public class RespiratoryDisorderDetails {

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

    /**
     * No args constructor for use in serialization
     * 
     */
    public RespiratoryDisorderDetails() {
    }

    /**
     * 
     * @param howOftenSymptomsOccur
     * @param whenWasItDiagnosed
     * @param symptomsDetails
     * @param medicationDetails
     * @param haveYouTakenSteriods
     */
    public RespiratoryDisorderDetails(String whenWasItDiagnosed, String symptomsDetails, String howOftenSymptomsOccur, String medicationDetails,
	    String haveYouTakenSteriods) {
	super();
	this.whenWasItDiagnosed = whenWasItDiagnosed;
	this.symptomsDetails = symptomsDetails;
	this.howOftenSymptomsOccur = howOftenSymptomsOccur;
	this.medicationDetails = medicationDetails;
	this.haveYouTakenSteriods = haveYouTakenSteriods;
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

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RespiratoryDisorderDetails{" +
                "whenWasItDiagnosed='" + whenWasItDiagnosed + '\'' +
                ", symptomsDetails='" + symptomsDetails + '\'' +
                ", howOftenSymptomsOccur='" + howOftenSymptomsOccur + '\'' +
                ", medicationDetails='" + medicationDetails + '\'' +
                ", haveYouTakenSteriods='" + haveYouTakenSteriods + '\'' +
                '}';
    }
}
