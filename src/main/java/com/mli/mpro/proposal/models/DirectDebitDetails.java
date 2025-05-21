package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class DirectDebitDetails {
    @JsonProperty("requiredRefNumber")
    private String requiredRefNumber;
    @JsonProperty("externalRefNumber")
    private String externalRefNumber;
    @JsonProperty("isOverriden")
    private String isOverriden;
    @JsonProperty("isServiceChargeApplied")
    private String isServiceChargeApplied;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("replyCode")
    private String replyCode;
    @JsonProperty("replyText")
    private String replyText;
    @JsonProperty("spReturnValue")
    private String spReturnValue;
    @JsonProperty("status")
    private String status;
    @JsonProperty("userReferenceNumber")
    private String userReferenceNumber;
    @JsonProperty("directDebitOtpSent")
    private String directDebitOtpSent;
    @JsonProperty("directDebitOtpValidated")
    private String directDebitOtpValidated;
    @JsonProperty("directDebitRenewals")
	private String directDebitRenewals;
    @Sensitive(MaskType.AMOUNT)
	@JsonProperty("initialPremium")
	private String initialPremium;
    @Sensitive(MaskType.AMOUNT)
	@JsonProperty("renewalPremium")
	private String renewalPremium;

    public DirectDebitDetails() {
	super();
    }

    
    public DirectDebitDetails(String requiredRefNumber, String externalRefNumber, String isOverriden, String isServiceChargeApplied, String memo,
	    String replyCode, String replyText, String spReturnValue, String status, String userReferenceNumber, String directDebitOtpSent,
	    String directDebitOtpValidated, String directDebitRenewals, String initialPremium, String renewalPremium) {
	super();
	this.requiredRefNumber = requiredRefNumber;
	this.externalRefNumber = externalRefNumber;
	this.isOverriden = isOverriden;
	this.isServiceChargeApplied = isServiceChargeApplied;
	this.memo = memo;
	this.replyCode = replyCode;
	this.replyText = replyText;
	this.spReturnValue = spReturnValue;
	this.status = status;
	this.userReferenceNumber = userReferenceNumber;
	this.directDebitOtpSent = directDebitOtpSent;
	this.directDebitOtpValidated = directDebitOtpValidated;
	this.directDebitRenewals = directDebitRenewals;
	this.initialPremium = initialPremium;
	this.renewalPremium = renewalPremium;
    }


    public String getRequiredRefNumber() {
	return requiredRefNumber;
    }

    public void setRequiredRefNumber(String requiredRefNumber) {
	this.requiredRefNumber = requiredRefNumber;
    }

    public String getExternalRefNumber() {
	return externalRefNumber;
    }

    public void setExternalRefNumber(String externalRefNumber) {
	this.externalRefNumber = externalRefNumber;
    }

    public String getIsOverriden() {
	return isOverriden;
    }

    public void setIsOverriden(String isOverriden) {
	this.isOverriden = isOverriden;
    }

    public String getIsServiceChargeApplied() {
	return isServiceChargeApplied;
    }

    public void setIsServiceChargeApplied(String isServiceChargeApplied) {
	this.isServiceChargeApplied = isServiceChargeApplied;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

    public String getReplyCode() {
	return replyCode;
    }

    public void setReplyCode(String replyCode) {
	this.replyCode = replyCode;
    }

    public String getReplyText() {
	return replyText;
    }

    public void setReplyText(String replyText) {
	this.replyText = replyText;
    }

    public String getSpReturnValue() {
	return spReturnValue;
    }

    public void setSpReturnValue(String spReturnValue) {
	this.spReturnValue = spReturnValue;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getUserReferenceNumber() {
	return userReferenceNumber;
    }

    public void setUserReferenceNumber(String userReferenceNumber) {
	this.userReferenceNumber = userReferenceNumber;
    }
    

    public String getDirectDebitOtpSent() {
        return directDebitOtpSent;
    }

    public void setDirectDebitOtpSent(String directDebitOtpSent) {
        this.directDebitOtpSent = directDebitOtpSent;
    }

    public String getDirectDebitOtpValidated() {
        return directDebitOtpValidated;
    }

    public void setDirectDebitOtpValidated(String directDebitOtpValidated) {
        this.directDebitOtpValidated = directDebitOtpValidated;
    }
    
    public String getDirectDebitRenewals() {
		return directDebitRenewals;
	}

	public void setDirectDebitRenewals(String directDebitRenewals) {
		this.directDebitRenewals = directDebitRenewals;
	}

	public String getInitialPremium() {
		return initialPremium;
	}

	public void setInitialPremium(String initialPremium) {
		this.initialPremium = initialPremium;
	}

	public String getRenewalPremium() {
		return renewalPremium;
	}

	public void setRenewalPremium(String renewalPremium) {
		this.renewalPremium = renewalPremium;
	}

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "DirectDebitDetails [requiredRefNumber=" + requiredRefNumber + ", externalRefNumber=" + externalRefNumber + ", isOverriden=" + isOverriden
		+ ", isServiceChargeApplied=" + isServiceChargeApplied + ", memo=" + memo + ", replyCode=" + replyCode + ", replyText=" + replyText
		+ ", spReturnValue=" + spReturnValue + ", status=" + status + ", userReferenceNumber=" + userReferenceNumber + ", directDebitRenewals="
		+ directDebitRenewals + ", initialPremium=" + initialPremium + ", renewalPremium=" + renewalPremium
		+ "]";
    }

}
