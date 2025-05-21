
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "whenWasItDiagnosed", "medicationDetails", "followStrictDiet", "followupapappAfterLastConsultation", "lastReading",
	"everHadNumbnessOrTingling" })
public class HighBloodSugarAndDiabetesDetails {

    @JsonProperty("whenWasItDiagnosed")
    private String whenWasItDiagnosed;
    @JsonProperty("medicationDetails")
    private String medicationDetails;
    @JsonProperty("followStrictDiet")
    private String followStrictDiet;
    @JsonProperty("followupapappAfterLastConsultation")
    private String followupapappAfterLastConsultation;
    @JsonProperty("lastReading")
    private String lastReading;
    @JsonProperty("everHadNumbnessOrTingling")
    private String everHadNumbnessOrTingling;
    @JsonProperty("managingDiabThrough")
    private String managingDiabThrough;
    @JsonProperty("diabPeriod")
    private String diabPeriod;

    @JsonProperty("typeOfDiabetes")
    private String typeOfDiabetes;

    @JsonProperty("diabetesDiagnosedDate")
    private String diabetesDiagnosedDate;

    @JsonProperty("diabeticComplications")
    private List<String> diabeticComplications;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HighBloodSugarAndDiabetesDetails() {
    }

    /**
     * 
     * @param whenWasItDiagnosed
     * @param medicationDetails
     * @param everHadNumbnessOrTingling
     * @param followupapappAfterLastConsultation
     * @param lastReading
     * @param followStrictDiet
     */
    public HighBloodSugarAndDiabetesDetails(String whenWasItDiagnosed, String medicationDetails, String followStrictDiet,
	    String followupapappAfterLastConsultation, String lastReading, String everHadNumbnessOrTingling, String typeOfDiabetes, String diabetesDiagnosedDate, List<String> diabeticComplications) {
	super();
	this.whenWasItDiagnosed = whenWasItDiagnosed;
	this.medicationDetails = medicationDetails;
	this.followStrictDiet = followStrictDiet;
	this.followupapappAfterLastConsultation = followupapappAfterLastConsultation;
	this.lastReading = lastReading;
	this.everHadNumbnessOrTingling = everHadNumbnessOrTingling;
    this.typeOfDiabetes=typeOfDiabetes;
    this.diabetesDiagnosedDate=diabetesDiagnosedDate;
    this.diabeticComplications=diabeticComplications;
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

    @JsonProperty("followStrictDiet")
    public String getFollowStrictDiet() {
	return followStrictDiet;
    }

    @JsonProperty("followStrictDiet")
    public void setFollowStrictDiet(String followStrictDiet) {
	this.followStrictDiet = followStrictDiet;
    }

    @JsonProperty("followupapappAfterLastConsultation")
    public String getFollowupapappAfterLastConsultation() {
	return followupapappAfterLastConsultation;
    }

    @JsonProperty("followupapappAfterLastConsultation")
    public void setFollowupapappAfterLastConsultation(String followupapappAfterLastConsultation) {
	this.followupapappAfterLastConsultation = followupapappAfterLastConsultation;
    }

    @JsonProperty("lastReading")
    public String getLastReading() {
	return lastReading;
    }

    @JsonProperty("lastReading")
    public void setLastReading(String lastReading) {
	this.lastReading = lastReading;
    }

    @JsonProperty("everHadNumbnessOrTingling")
    public String getEverHadNumbnessOrTingling() {
	return everHadNumbnessOrTingling;
    }

    @JsonProperty("everHadNumbnessOrTingling")
    public void setEverHadNumbnessOrTingling(String everHadNumbnessOrTingling) {
	this.everHadNumbnessOrTingling = everHadNumbnessOrTingling;
    }

    public String getManagingDiabThrough() {
        return managingDiabThrough;
    }

    public HighBloodSugarAndDiabetesDetails setManagingDiabThrough(String managingDiabThrough) {
        this.managingDiabThrough = managingDiabThrough;
        return this;
    }

    public String getDiabPeriod() {
        return diabPeriod;
    }

    public HighBloodSugarAndDiabetesDetails setDiabPeriod(String diabPeriod) {
        this.diabPeriod = diabPeriod;
        return this;
    }

    public String getTypeOfDiabetes() {
        return typeOfDiabetes;
    }

    public void setTypeOfDiabetes(String typeOfDiabetes) {
        this.typeOfDiabetes = typeOfDiabetes;
    }

    public String getDiabetesDiagnosedDate() {
        return diabetesDiagnosedDate;
    }

    public void setDiabetesDiagnosedDate(String diabetesDiagnosedDate) {
        this.diabetesDiagnosedDate = diabetesDiagnosedDate;
    }

    public List<String> getDiabeticComplications() {
        return diabeticComplications;
    }

    public void setDiabeticComplications(List<String> diabeticComplications) {
        this.diabeticComplications = diabeticComplications;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "HighBloodSugarAndDiabetesDetails{" +
                "whenWasItDiagnosed='" + whenWasItDiagnosed + '\'' +
                ", medicationDetails='" + medicationDetails + '\'' +
                ", followStrictDiet='" + followStrictDiet + '\'' +
                ", followupapappAfterLastConsultation='" + followupapappAfterLastConsultation + '\'' +
                ", lastReading='" + lastReading + '\'' +
                ", everHadNumbnessOrTingling='" + everHadNumbnessOrTingling + '\'' +
                ", managingDiabThrough='" + managingDiabThrough + '\'' +
                ", diabPeriod='" + diabPeriod + '\'' +
                ", typeOfDiabetes='" + typeOfDiabetes + '\'' +
                ", diabetesDiagnosedDate='" + diabetesDiagnosedDate + '\'' +
                ", diabeticComplications='" + diabeticComplications + '\'' +
                '}';
    }
}
