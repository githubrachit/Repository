
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "familyDiagnosedWithDiseases", "anyCriminalCharges", "specifyDetails", "familyDiagnosedWithDiseasesDetails" })
public class FamilyOrCriminalHistory {

    @JsonProperty("familyDiagnosedWithDiseasesBefore60")
    private String familyDiagnosedWithDiseasesBefore60;
    @JsonProperty("anyCriminalCharges")
    private String anyCriminalCharges;
    @JsonProperty("specifyDetails")
    private String specifyDetails;
    @JsonProperty("isNewFamilyHisApplicable")
    private String isNewFamilyHisApplicable;
    @JsonProperty("familyDiagnosedWithDiseasesDetails")
    private List<FamilyDiagnosedWithDiseasesDetail> familyDiagnosedWithDiseasesDetails = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FamilyOrCriminalHistory() {
    }

    /**
     *
     * @param specifyDetails
     * @param anyCriminalCharges
     * @param familyDiagnosedWithDiseasesDetails
     */
    public FamilyOrCriminalHistory(String familyDiagnosedWithDiseasesBefore60, String anyCriminalCharges, String specifyDetails,
	    List<FamilyDiagnosedWithDiseasesDetail> familyDiagnosedWithDiseasesDetails) {
	super();
	this.familyDiagnosedWithDiseasesBefore60 = familyDiagnosedWithDiseasesBefore60;
	this.anyCriminalCharges = anyCriminalCharges;
	this.specifyDetails = specifyDetails;
	this.familyDiagnosedWithDiseasesDetails = familyDiagnosedWithDiseasesDetails;
    }


    @JsonProperty("familyDiagnosedWithDiseasesBefore60")
    public String getFamilyDiagnosedWithDiseasesBefore60() {
	return familyDiagnosedWithDiseasesBefore60;
    }

    @JsonProperty("familyDiagnosedWithDiseasesBefore60")
    public void setFamilyDiagnosedWithDiseasesBefore60(String familyDiagnosedWithDiseasesBefore60) {
	this.familyDiagnosedWithDiseasesBefore60 = familyDiagnosedWithDiseasesBefore60;

    }

    @JsonProperty("anyCriminalCharges")
    public String getAnyCriminalCharges() {
	return anyCriminalCharges;
    }

    @JsonProperty("anyCriminalCharges")
    public void setAnyCriminalCharges(String anyCriminalCharges) {
	this.anyCriminalCharges = anyCriminalCharges;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("familyDiagnosedWithDiseasesDetails")
    public List<FamilyDiagnosedWithDiseasesDetail> getFamilyDiagnosedWithDiseasesDetails() {
	return familyDiagnosedWithDiseasesDetails;
    }

    @JsonProperty("familyDiagnosedWithDiseasesDetails")
    public void setFamilyDiagnosedWithDiseasesDetails(List<FamilyDiagnosedWithDiseasesDetail> familyDiagnosedWithDiseasesDetails) {
	this.familyDiagnosedWithDiseasesDetails = familyDiagnosedWithDiseasesDetails;
    }

    @JsonProperty("isNewFamilyHisApplicable")
    public String getIsNewFamilyHisApplicable() {
        return isNewFamilyHisApplicable;
    }

    @JsonProperty("isNewFamilyHisApplicable")
    public void setIsNewFamilyHisApplicable(String isNewFamilyHisApplicable) {
        this.isNewFamilyHisApplicable = isNewFamilyHisApplicable;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "FamilyOrCriminalHistory [familyDiagnosedWithDiseases=" + familyDiagnosedWithDiseasesBefore60 + ", anyCriminalCharges=" + anyCriminalCharges
		+ ", specifyDetails=" + specifyDetails + ", familyDiagnosedWithDiseasesDetails=" + familyDiagnosedWithDiseasesDetails + "]";
    }

}
