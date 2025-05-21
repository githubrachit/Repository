package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class DemandDraftDetails {

    @Sensitive(MaskType.CHEQUE_NUM)
    @JsonProperty("demandDraftNumber")
    private String demandDraftNumber;
    @JsonProperty("demandDraftDate")
    private Date demandDraftDate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("demandDraftAmount")
    private double demandDraftAmount;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("demandDraftPayableAt")
    private String demandDraftPayableAt;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("demandDraftBankName")
    private String demandDraftBankName;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("demandDraftMicr")
    private long demandDraftMicr;

    public DemandDraftDetails() {
        // default constructure to fix the sonar issue
    }

    public String getDemandDraftNumber() {
	return demandDraftNumber;
    }

    public void setDemandDraftNumber(String demandDraftNumber) {
	this.demandDraftNumber = demandDraftNumber;
    }

    public Date getDemandDraftDate() {
	return demandDraftDate;
    }

    public void setDemandDraftDate(Date demandDraftDate) {
	this.demandDraftDate = demandDraftDate;
    }

    public double getDemandDraftAmount() {
	return demandDraftAmount;
    }

    public void setDemandDraftAmount(double demandDraftAmount) {
	this.demandDraftAmount = demandDraftAmount;
    }

    public String getDemandDraftPayableAt() {
	return demandDraftPayableAt;
    }

    public void setDemandDraftPayableAt(String demandDraftPayableAt) {
	this.demandDraftPayableAt = demandDraftPayableAt;
    }

    public String getDemandDraftBankName() {
	return demandDraftBankName;
    }

    public void setDemandDraftBankName(String demandDraftBankName) {
	this.demandDraftBankName = demandDraftBankName;
    }

    public long getDemandDraftMicr() {
	return demandDraftMicr;
    }

    public void setDemandDraftMICR(long demandDraftMicr) {
	this.demandDraftMicr = demandDraftMicr;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "DemandDraftDetails [demandDraftNumber=" + demandDraftNumber + ", demandDraftDate=" + demandDraftDate + ", demandDraftAmount="
		+ demandDraftAmount + ", demandDraftPayableAt=" + demandDraftPayableAt + ", demandDraftBankName=" + demandDraftBankName + ", demandDraftMICR="
		+ demandDraftMicr + "]";
    }
}
