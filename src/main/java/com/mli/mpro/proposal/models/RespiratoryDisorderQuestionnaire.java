package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

/**
 * @author manish on 02/02/21
 */
public class RespiratoryDisorderQuestionnaire {

    @JsonProperty("respiratoryDisorderDiagnosedDate")
    private String respiratoryDisorderDiagnosedDate;

    @JsonProperty("symptomsDescription")
    private String symptomsDescription;

    @JsonProperty("followUpsForRespiratoryDisorder")
    private String followUpsForRespiratoryDisorder;

    @JsonProperty("followUpsOtherForRespiratoryDisorder")
    private String followUpsOtherForRespiratoryDisorder;

    @JsonProperty("medicationDetails")
    private String medicationDetails;

    @JsonProperty("steroidsTaken")
    private String steroidsTaken;

    @JsonProperty("steroidsTakenDetail")
    private String steroidsTakenDetail;

    public String getRespiratoryDisorderDiagnosedDate() {
        return respiratoryDisorderDiagnosedDate;
    }

    public void setRespiratoryDisorderDiagnosedDate(String respiratoryDisorderDiagnosedDate) {
        this.respiratoryDisorderDiagnosedDate = respiratoryDisorderDiagnosedDate;
    }

    public String getSymptomsDescription() {
        return symptomsDescription;
    }

    public void setSymptomsDescription(String symptomsDescription) {
        this.symptomsDescription = symptomsDescription;
    }

    public String getFollowUpsForRespiratoryDisorder() {
        return followUpsForRespiratoryDisorder;
    }

    public void setFollowUpsForRespiratoryDisorder(String followUpsForRespiratoryDisorder) {
        this.followUpsForRespiratoryDisorder = followUpsForRespiratoryDisorder;
    }

    public String getFollowUpsOtherForRespiratoryDisorder() {
        return followUpsOtherForRespiratoryDisorder;
    }

    public void setFollowUpsOtherForRespiratoryDisorder(String followUpsOtherForRespiratoryDisorder) {
        this.followUpsOtherForRespiratoryDisorder = followUpsOtherForRespiratoryDisorder;
    }

    public String getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    public String getSteroidsTaken() {
        return steroidsTaken;
    }

    public void setSteroidsTaken(String steroidsTaken) {
        this.steroidsTaken = steroidsTaken;
    }

    public String getSteroidsTakenDetail() {
        return steroidsTakenDetail;
    }

    public void setSteroidsTakenDetail(String steroidsTakenDetail) {
        this.steroidsTakenDetail = steroidsTakenDetail;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", RespiratoryDisorderQuestionnaire.class.getSimpleName() + "[", "]")
                .add("respiratoryDisorderDiagnosedDate='" + respiratoryDisorderDiagnosedDate + "'")
                .add("symptomsDescription='" + symptomsDescription + "'")
                .add("followUpsForRespiratoryDisorder='" + followUpsForRespiratoryDisorder + "'")
                .add("followUpsOtherForRespiratoryDisorder='" + followUpsOtherForRespiratoryDisorder + "'")
                .add("medicationDetails='" + medicationDetails + "'")
                .add("steroidsTaken='" + steroidsTaken + "'")
                .add("steroidsTakenDetail='" + steroidsTakenDetail + "'")
                .toString();
    }
}
