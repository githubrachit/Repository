
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "maritalStatus", "maidenName", "spouseOccupation", "spouseAnnualIncome", "totalInsuranceCoverOnSpouse", "isPregnant", "pregnantSince" })
public class MarriageDetails {

    @JsonProperty("maritalStatus")
    private String maritalStatus;
    @Sensitive(MaskType.NAME)
    @JsonProperty("maidenName")
    private String maidenName;
    @JsonProperty("spouseOccupation")
    private String spouseOccupation;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("spouseAnnualIncome")
    private String spouseAnnualIncome;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalInsuranceCoverOnSpouse")
    private String totalInsuranceCoverOnSpouse;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("isPregnant")
    private boolean isPregnant;
    @JsonProperty("pregnantSince")
    private String pregnantSince;
    @JsonProperty("anyComplicationToPregnancy")
    private boolean anyComplicationToPregnancy;
    @JsonProperty("pregnancyComplicationDetails")
    private String pregnancyComplicationDetails;
    @JsonProperty("previousPregComp")
    private String previousPregComp;
    @JsonProperty("prevPregCompDetails")
    private String prevPregCompDetails;
    @JsonProperty("prevAnyComplicationToPregnancy")
    private String prevAnyComplicationToPregnancy;
    @JsonProperty("prevPregnancyComplicationDetails")
    private String prevPregnancyComplicationDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MarriageDetails() {
    }

    public MarriageDetails(String maritalStatus, String maidenName, String spouseOccupation, String spouseAnnualIncome, String totalInsuranceCoverOnSpouse,
	    boolean isPregnant, String pregnantSince, boolean anyComplicationToPregnancy, String pregnancyComplicationDetails) {
	super();
	this.maritalStatus = maritalStatus;
	this.maidenName = maidenName;
	this.spouseOccupation = spouseOccupation;
	this.spouseAnnualIncome = spouseAnnualIncome;
	this.totalInsuranceCoverOnSpouse = totalInsuranceCoverOnSpouse;
	this.isPregnant = isPregnant;
	this.pregnantSince = pregnantSince;
	this.anyComplicationToPregnancy = anyComplicationToPregnancy;
	this.pregnancyComplicationDetails = pregnancyComplicationDetails;
    this.prevAnyComplicationToPregnancy = getPrevAnyComplicationToPregnancy();
    this.prevPregnancyComplicationDetails = getPregnancyComplicationDetails();
    }

    public String getMaritalStatus() {
	return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
	this.maritalStatus = maritalStatus;
    }

    public String getMaidenName() {
	return maidenName;
    }

    public void setMaidenName(String maidenName) {
	this.maidenName = maidenName;
    }

    public String getSpouseOccupation() {
	return spouseOccupation;
    }

    public void setSpouseOccupation(String spouseOccupation) {
	this.spouseOccupation = spouseOccupation;
    }

    public String getSpouseAnnualIncome() {
	return spouseAnnualIncome;
    }

    public void setSpouseAnnualIncome(String spouseAnnualIncome) {
	this.spouseAnnualIncome = spouseAnnualIncome;
    }

    public String getTotalInsuranceCoverOnSpouse() {
	return totalInsuranceCoverOnSpouse;
    }

    public void setTotalInsuranceCoverOnSpouse(String totalInsuranceCoverOnSpouse) {
	this.totalInsuranceCoverOnSpouse = totalInsuranceCoverOnSpouse;
    }

    public boolean isPregnant() {
	return isPregnant;
    }

    public void setPregnant(boolean isPregnant) {
	this.isPregnant = isPregnant;
    }

    public String getPregnantSince() {
	return pregnantSince;
    }

    public void setPregnantSince(String pregnantSince) {
	this.pregnantSince = pregnantSince;
    }

    public boolean isAnyComplicationToPregnancy() {
	return anyComplicationToPregnancy;
    }

    public void setAnyComplicationToPregnancy(boolean anyComplicationToPregnancy) {
	this.anyComplicationToPregnancy = anyComplicationToPregnancy;
    }

    public String getPregnancyComplicationDetails() {
	return pregnancyComplicationDetails;
    }

    public void setPregnancyComplicationDetails(String pregnancyComplicationDetails) {
	this.pregnancyComplicationDetails = pregnancyComplicationDetails;
    }

    public String getPreviousPregComp() {
        return previousPregComp;
    }

    public void setPreviousPregComp(String previousPregComp) {
        this.previousPregComp = previousPregComp;
    }

    public String getPrevPregCompDetails() {
        return prevPregCompDetails;
    }

    public void setPrevPregCompDetails(String prevPregCompDetails) {
        this.prevPregCompDetails = prevPregCompDetails;
    }

    public String getPrevAnyComplicationToPregnancy() {
        return prevAnyComplicationToPregnancy;
    }

    public void setPrevAnyComplicationToPregnancy(String prevAnyComplicationToPregnancy) {
        this.prevAnyComplicationToPregnancy = prevAnyComplicationToPregnancy;
    }

    public String getPrevPregnancyComplicationDetails() {
        return prevPregnancyComplicationDetails;
    }

    public void setPrevPregnancyComplicationDetails(String prevPregnancyComplicationDetails) {
        this.prevPregnancyComplicationDetails = prevPregnancyComplicationDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "MarriageDetails [maritalStatus=" + maritalStatus + ", maidenName=" + maidenName + ", spouseOccupation=" + spouseOccupation
		+ ", spouseAnnualIncome=" + spouseAnnualIncome + ", totalInsuranceCoverOnSpouse=" + totalInsuranceCoverOnSpouse + ", isPregnant=" + isPregnant
		+ ", pregnantSince=" + pregnantSince + ", anyComplicationToPregnancy=" + anyComplicationToPregnancy + ", pregnancyComplicationDetails="
		+ pregnancyComplicationDetails + ", prevAnyComplicationToPregnancy=" + prevAnyComplicationToPregnancy + ", prevPregnancyComplicationDetails=" + prevPregnancyComplicationDetails + "]";
    }

}
