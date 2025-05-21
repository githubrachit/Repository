package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author manish on 25/01/21
 */
public class HighBloodPressureQuestionnaire {

    @JsonProperty("highBloodPressureDiagnosedDate")
    private String highBloodPressureDiagnosedDate;
    @JsonProperty("medicationDetails")
    private String medicationDetails;
    @JsonProperty("followUpsForHighBloodPressure")
    private String followUpsForHighBloodPressure;
    @JsonProperty("followUpsOtherForHighBloodPressure")
    private String followUpsOtherForHighBloodPressure;
    @JsonProperty("lastBloodPressureReading")
    private String lastBloodPressureReading;

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

    public HighBloodPressureQuestionnaire() {}

    public String getHighBloodPressureDiagnosedDate() {
        return highBloodPressureDiagnosedDate;
    }

    public void setHighBloodPressureDiagnosedDate(String highBloodPressureDiagnosedDate) {
        this.highBloodPressureDiagnosedDate = highBloodPressureDiagnosedDate;
    }

    public String getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    public String getFollowUpsForHighBloodPressure() {
        return followUpsForHighBloodPressure;
    }

    public void setFollowUpsForHighBloodPressure(String followUpsForHighBloodPressure) {
        this.followUpsForHighBloodPressure = followUpsForHighBloodPressure;
    }

    public String getFollowUpsOtherForHighBloodPressure() {
        return followUpsOtherForHighBloodPressure;
    }

    public void setFollowUpsOtherForHighBloodPressure(String followUpsOtherForHighBloodPressure) {
        this.followUpsOtherForHighBloodPressure = followUpsOtherForHighBloodPressure;
    }

    public String getLastBloodPressureReading() {
        return lastBloodPressureReading;
    }

    public void setLastBloodPressureReading(String lastBloodPressureReading) {
        this.lastBloodPressureReading = lastBloodPressureReading;
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
        return new StringJoiner(", ", HighBloodPressureQuestionnaire.class.getSimpleName() + "[", "]")
                .add("highBloodPressureDiagnosedDate='" + highBloodPressureDiagnosedDate + "'")
                .add("medicationDetails='" + medicationDetails + "'")
                .add("followUpsForHighBloodPressure='" + followUpsForHighBloodPressure + "'")
                .add("followUpsOtherForHighBloodPressure='" + followUpsOtherForHighBloodPressure + "'")
                .add("lastBloodPressureReading='" + lastBloodPressureReading + "'")
                .add("diagnosedDate='" + diagnosedDate + "'")
                .add("areYouOnTreatment='" + areYouOnTreatment + "'")
                .add("medicalTestApplicable='" + medicalTestApplicable + "'")
                .add("medicalTestDocConsultationDate='" + medicalTestDocConsultationDate + "'")
                .add("highBloodPressureComplications='" + highBloodPressureComplications + "'")
                .add("medicalTestDetails='" + medicalTestDetails + "'")
                .toString();
    }
}
