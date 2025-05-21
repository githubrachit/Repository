
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "panNumber", "isPANExist", "isPanValidated", "idDobValidated", "creditScore", "incomeRange", "panOcrStatus", "payorPanOcrStatus",
	"panValidationService" })
public class PanDetails {

    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    public String panNumber;
    @JsonProperty("isPANExist")
    public boolean isPANExist;
    @JsonProperty("isPanValidated")
    public boolean isPanValidated;
    @JsonProperty("isDobValidated")
    public boolean isDobValidated;
    @JsonProperty("creditScore")
    public String creditScore;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("incomeRange")
    public String incomeRange;
    @JsonProperty("PANOcrFlag")
    private boolean panOcrFlag;
    @JsonProperty("panOcrStatus")
    private String panOcrStatus;
    @JsonProperty("payorPanOcrStatus")
    private String payorPanOcrStatus;
    @JsonProperty("panValidationService")
    private String panValidationService;
    @JsonProperty("panAadhaarLinkStatus")
    private String panAadhaarLinkStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PanDetails() {
    }

    public PanDetails(String panNumber, boolean isPANExist, boolean isPanValidated, boolean isDobValidated, String creditScore, String incomeRange,
	    boolean panOcrFlag, String panOcrStatus, String payorPanOcrStatus, String panValidationService, String panAadhaarLinkStatus) {

	super();
	this.panNumber = panNumber;
	this.isPANExist = isPANExist;
	this.isPanValidated = isPanValidated;
	this.isDobValidated = isDobValidated;
	this.creditScore = creditScore;
	this.incomeRange = incomeRange;
	this.panOcrFlag = panOcrFlag;
	this.panOcrStatus = panOcrStatus;
	this.payorPanOcrStatus = payorPanOcrStatus;
	this.panValidationService = panValidationService;
	this.panAadhaarLinkStatus = panAadhaarLinkStatus;
    }

    public String getPanNumber() {
	return panNumber;
    }

    public void setPanNumber(String panNumber) {
	this.panNumber = panNumber;
    }

    public boolean isPANExist() {
	return isPANExist;
    }

    public void setPANExist(boolean isPANExist) {
	this.isPANExist = isPANExist;
    }

    public boolean isPanValidated() {
	return isPanValidated;
    }

    public void setPanValidated(boolean isPanValidated) {
	this.isPanValidated = isPanValidated;
    }

    public boolean isDobValidated() {
	return isDobValidated;
    }

    public void setDobValidated(boolean isDobValidated) {
	this.isDobValidated = isDobValidated;
    }

    public String getCreditScore() {
	return creditScore;
    }

    public void setCreditScore(String creditScore) {
	this.creditScore = creditScore;
    }

    public String getIncomeRange() {
	return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
	this.incomeRange = incomeRange;
    }

    public boolean isPanOcrFlag() {
	return panOcrFlag;
    }

    public void setPanOcrFlag(boolean pANOcrFlag) {
	panOcrFlag = pANOcrFlag;
    }

    public String getPanOcrStatus() {
	return panOcrStatus;
    }

    public void setPanOcrStatus(String panOcrStatus) {
	this.panOcrStatus = panOcrStatus;
    }

    public String getPayorPanOcrStatus() {
	return payorPanOcrStatus;
    }

    public void setPayorPanOcrStatus(String payorPanOcrStatus) {
	this.payorPanOcrStatus = payorPanOcrStatus;
    }

    public String getPanValidationService() {
	return panValidationService;
    }

    public void setPanValidationService(String panValidationService) {
	this.panValidationService = panValidationService;
    }
    public String getPanAadhaarLinkStatus() {
        return panAadhaarLinkStatus;
    }

    public void setPanAadhaarLinkStatus(String panAadhaarLinkStatus) {
        this.panAadhaarLinkStatus = panAadhaarLinkStatus;
    }


    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PanDetails [panNumber=" + panNumber + ", isPANExist=" + isPANExist + ", isPanValidated=" + isPanValidated + ", isDobValidated=" + isDobValidated
		+ ", creditScore=" + creditScore + ", incomeRange=" + incomeRange + ", panOcrFlag=" + panOcrFlag + ", panOcrStatus=" + panOcrStatus
		+ ",payorPanOcrStatus=" + payorPanOcrStatus + ", panValidationService=" + panValidationService + ", panAadhaarLinkStatus=" + panAadhaarLinkStatus + "]";

    }
}
