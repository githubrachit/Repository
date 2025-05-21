
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "familyMember", "ageAtDiagnosis", "medicalProblem" })
public class FamilyDiagnosedWithDiseasesDetail {

    @JsonProperty("familyMember")
    private String familyMember;
    @JsonProperty("ageAtDiagnosis")
    private String ageAtDiagnosis;
    @JsonProperty("medicalProblem")
    private String medicalProblem;
    @JsonProperty("othersMedicalProblem")
    private String othersMedicalProblem;
    @JsonProperty("deceasedStatus")
    private String deceasedStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FamilyDiagnosedWithDiseasesDetail() {
    }

    /**
     * 
     * @param ageAtDiagnosis
     * @param familyMember
     * @param medicalProblem
     */
    public FamilyDiagnosedWithDiseasesDetail(String familyMember, String ageAtDiagnosis, String medicalProblem) {
	super();
	this.familyMember = familyMember;
	this.ageAtDiagnosis = ageAtDiagnosis;
	this.medicalProblem = medicalProblem;
    }

    @JsonProperty("familyMember")
    public String getFamilyMember() {
	return familyMember;
    }

    @JsonProperty("familyMember")
    public void setFamilyMember(String familyMember) {
	this.familyMember = familyMember;
    }

    @JsonProperty("ageAtDiagnosis")
    public String getAgeAtDiagnosis() {
	return ageAtDiagnosis;
    }

    @JsonProperty("ageAtDiagnosis")
    public void setAgeAtDiagnosis(String ageAtDiagnosis) {
	this.ageAtDiagnosis = ageAtDiagnosis;
    }

    @JsonProperty("medicalProblem")
    public String getMedicalProblem() {
	return medicalProblem;
    }

    @JsonProperty("medicalProblem")
    public void setMedicalProblem(String medicalProblem) {
	this.medicalProblem = medicalProblem;
    }

    @JsonProperty("othersMedicalProblem")
    public String getOthersMedicalProblem() {
        return othersMedicalProblem;
    }

    @JsonProperty("othersMedicalProblem")
    public void setOthersMedicalProblem(String othersMedicalProblem) {
        this.othersMedicalProblem = othersMedicalProblem;
    }

    @JsonProperty("deceasedStatus")
    public String getDeceasedStatus() {
        return deceasedStatus;
    }

    @JsonProperty("deceasedStatus")
    public void setDeceasedStatus(String deceasedStatus) {
        this.deceasedStatus = deceasedStatus;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "FamilyDiagnosedWithDiseasesDetail [familyMember=" + familyMember + ", ageAtDiagnosis=" + ageAtDiagnosis + ", medicalProblem=" + medicalProblem
		+ "]";
    }

}
