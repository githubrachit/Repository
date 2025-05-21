package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "overallProductStatus", "overallPSMStatus", "overallHealthStatus", "replacementSale", "generatedOTPDate", "submittedOTPDate" })
public class PosvStatus {

    @JsonProperty("overallProductStatus")
    private String overallProductStatus;
    @JsonProperty("overallPSMStatus")
    private String overallPSMStatus;
    @JsonProperty("overallHealthStatus")
    private String overallHealthStatus;
    @JsonProperty("replacementSale")
    private String replacementSale;
    @JsonProperty("generatedOTPDate")
    private Date generatedOTPDate;
    @JsonProperty("submittedOTPDate")
    private Date submittedOTPDate;
    @JsonProperty("authorizedSignatoryOTPSubmittedDate")
    private Date authorizedSignatoryOTPSubmittedDate;
	
    /**
     * No args constructor for use in serialization
     * 
     */
    public PosvStatus() {
    }

    /**
     * 
     * @param replacementSale
     * @param submittedOTPDate
     * @param overallProductStatus
     * @param overallHealthStatus
     * @param generatedOTPDate
     * @param overallPSMStatus
     */
    public PosvStatus(String overallProductStatus, String overallPSMStatus, String overallHealthStatus, String replacementSale, Date generatedOTPDate,
	    Date submittedOTPDate) {
	super();
	this.overallProductStatus = overallProductStatus;
	this.overallPSMStatus = overallPSMStatus;
	this.overallHealthStatus = overallHealthStatus;
	this.replacementSale = replacementSale;
	this.generatedOTPDate = generatedOTPDate;
	this.submittedOTPDate = submittedOTPDate;
    }

    @JsonProperty("overallProductStatus")
    public String getOverallProductStatus() {
	return overallProductStatus;
    }

    @JsonProperty("overallProductStatus")
    public void setOverallProductStatus(String overallProductStatus) {
	this.overallProductStatus = overallProductStatus;
    }

    @JsonProperty("overallPSMStatus")
    public String getOverallPSMStatus() {
	return overallPSMStatus;
    }

    @JsonProperty("overallPSMStatus")
    public void setOverallPSMStatus(String overallPSMStatus) {
	this.overallPSMStatus = overallPSMStatus;
    }

    @JsonProperty("overallHealthStatus")
    public String getOverallHealthStatus() {
	return overallHealthStatus;
    }

    @JsonProperty("overallHealthStatus")
    public void setOverallHealthStatus(String overallHealthStatus) {
	this.overallHealthStatus = overallHealthStatus;
    }

    @JsonProperty("replacementSale")
    public String getReplacementSale() {
	return replacementSale;
    }

    @JsonProperty("replacementSale")
    public void setReplacementSale(String replacementSale) {
	this.replacementSale = replacementSale;
    }

    @JsonProperty("generatedOTPDate")
    public Date getGeneratedOTPDate() {
	return generatedOTPDate;
    }

    @JsonProperty("generatedOTPDate")
    public void setGeneratedOTPDate(Date generatedOTPDate) {
	this.generatedOTPDate = generatedOTPDate;
    }

    @JsonProperty("submittedOTPDate")
    public Date getSubmittedOTPDate() {
	return submittedOTPDate;
    }

    @JsonProperty("submittedOTPDate")
    public void setSubmittedOTPDate(Date submittedOTPDate) {
	this.submittedOTPDate = submittedOTPDate;
    }

    @JsonProperty("authorizedSignatoryOTPSubmittedDate")
    public Date getAuthorizedSignatoryOTPSubmittedDate() {
		return authorizedSignatoryOTPSubmittedDate;
	}

    @JsonProperty("authorizedSignatoryOTPSubmittedDate")
	public void setAuthorizedSignatoryOTPSubmittedDate(Date authorizedSignatoryOTPSubmittedDate) {
		this.authorizedSignatoryOTPSubmittedDate = authorizedSignatoryOTPSubmittedDate;
	}

	@Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PosvStatus [overallProductStatus=" + overallProductStatus + ", overallPSMStatus=" + overallPSMStatus + ", overallHealthStatus="
		+ overallHealthStatus + ", replacementSale=" + replacementSale + ", generatedOTPDate=" + generatedOTPDate + ", submittedOTPDate="
		+ submittedOTPDate + ", authorizedSignatoryOTPSubmittedDate=" + authorizedSignatoryOTPSubmittedDate +"]";
    }

}