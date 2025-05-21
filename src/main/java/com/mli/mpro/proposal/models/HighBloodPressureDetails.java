
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "whenWasItDiagnosed", "medicationDetails", "followupAfterLastConsultation", "lastReading" })
public class HighBloodPressureDetails {

    @JsonProperty("whenWasItDiagnosed")
    private String whenWasItDiagnosed;
    @JsonProperty("medicationDetails")
    private String medicationDetails;
    @JsonProperty("followupAfterLastConsultation")
    private String followupAfterLastConsultation;
    @JsonProperty("lastReading")
    private String lastReading;

    @JsonProperty("diagnosedDate")
    private String diagnosedDate;
    @JsonProperty("areYouOnTreatment")
    private String areYouOnTreatment;
    @JsonProperty("medicalTestApplicable")
    private String medicalTestApplicable;
    @JsonProperty("medicalTestDocConsultationDate")
    private String medicalTestDocConsultationDate;
    @JsonProperty("highBloodPressureComplications")
    private List<String> highBloodPressureComplications;
    @JsonProperty("medicalTestDetails")
    private List<String> medicalTestDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HighBloodPressureDetails() {
    }

    /**
     * 
     * @param whenWasItDiagnosed
     * @param medicationDetails
     * @param followupAfterLastConsultation
     * @param lastReading
     */
    public HighBloodPressureDetails(String whenWasItDiagnosed, String medicationDetails, String followupAfterLastConsultation, String lastReading, String diagnosedDate, String areYouOnTreatment, String medicalTestApplicable, String medicalTestDocConsultationDate, List<String> highBloodPressureComplications, List<String> medicalTestDetails) {
	super();
	this.whenWasItDiagnosed = whenWasItDiagnosed;
	this.medicationDetails = medicationDetails;
	this.followupAfterLastConsultation = followupAfterLastConsultation;
	this.lastReading = lastReading;
    this.diagnosedDate=diagnosedDate;
    this.areYouOnTreatment=areYouOnTreatment;
    this.medicalTestApplicable=medicalTestApplicable;
    this.medicalTestDocConsultationDate=medicalTestDocConsultationDate;
    this.highBloodPressureComplications=highBloodPressureComplications;
    this.medicalTestDetails=medicalTestDetails;
    }

    @JsonProperty("whenWasItDiagnosed")
    public String getWhenWasItDiagnosed() {
	return whenWasItDiagnosed;
    }

    @JsonProperty("whenWasItDiagnosed")
    public void setWhenWasItDiagnosed(String whenWasItDiagnosed) {
	this.whenWasItDiagnosed = whenWasItDiagnosed;
    }

    @JsonProperty("medicationDetails")
    public String getMedicationDetails() {
	return medicationDetails;
    }

    @JsonProperty("medicationDetails")
    public void setMedicationDetails(String medicationDetails) {
	this.medicationDetails = medicationDetails;
    }

    @JsonProperty("followupAfterLastConsultation")
    public String getFollowupAfterLastConsultation() {
	return followupAfterLastConsultation;
    }

    @JsonProperty("followupAfterLastConsultation")
    public void setFollowupAfterLastConsultation(String followupAfterLastConsultation) {
	this.followupAfterLastConsultation = followupAfterLastConsultation;
    }

    @JsonProperty("lastReading")
    public String getLastReading() {
	return lastReading;
    }

    @JsonProperty("lastReading")
    public void setLastReading(String lastReading) {
	this.lastReading = lastReading;
    }

    @JsonProperty("diagnosedDate")
    public String getDiagnosedDate() {
        return diagnosedDate;
    }
    @JsonProperty("diagnosedDate")
    public void setDiagnosedDate(String diagnosedDate) {
        this.diagnosedDate = diagnosedDate;
    }
    @JsonProperty("areYouOnTreatment")
    public String getAreYouOnTreatment() {
        return areYouOnTreatment;
    }
    @JsonProperty("areYouOnTreatment")
    public void setAreYouOnTreatment(String areYouOnTreatment) {
        this.areYouOnTreatment = areYouOnTreatment;
    }
    @JsonProperty("medicalTestApplicable")
    public String getMedicalTestApplicable() {
        return medicalTestApplicable;
    }
    @JsonProperty("medicalTestApplicable")
    public void setMedicalTestApplicable(String medicalTestApplicable) {
        this.medicalTestApplicable = medicalTestApplicable;
    }
    @JsonProperty("medicalTestDocConsultationDate")
    public String getMedicalTestDocConsultationDate() {
        return medicalTestDocConsultationDate;
    }
    @JsonProperty("medicalTestDocConsultationDate")
    public void setMedicalTestDocConsultationDate(String medicalTestDocConsultationDate) {
        this.medicalTestDocConsultationDate = medicalTestDocConsultationDate;
    }
    @JsonProperty("highBloodPressureComplications")
    public List<String> getHighBloodPressureComplications() {
        return highBloodPressureComplications;
    }
    @JsonProperty("highBloodPressureComplications")
    public void setHighBloodPressureComplications(List<String> highBloodPressureComplications) {
        this.highBloodPressureComplications = highBloodPressureComplications;
    }
    @JsonProperty("medicalTestDetails")
    public List<String> getMedicalTestDetails() {
        return medicalTestDetails;
    }
    @JsonProperty("medicalTestDetails")
    public void setMedicalTestDetails(List<String> medicalTestDetails) {
        this.medicalTestDetails = medicalTestDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "HighBloodPressureDetails{" +
                "whenWasItDiagnosed='" + whenWasItDiagnosed + '\'' +
                ", medicationDetails='" + medicationDetails + '\'' +
                ", followupAfterLastConsultation='" + followupAfterLastConsultation + '\'' +
                ", lastReading='" + lastReading + '\'' +
                ", diagnosedDate='" + diagnosedDate + '\'' +
                ", areYouOnTreatment='" + areYouOnTreatment + '\'' +
                ", medicalTestApplicable='" + medicalTestApplicable + '\'' +
                ", medicalTestDocConsultationDate='" + medicalTestDocConsultationDate + '\'' +
                ", highBloodPressureComplications='" + highBloodPressureComplications + '\'' +
                ", medicalTestDetails='" + medicalTestDetails + '\'' +
                '}';
    }
}
