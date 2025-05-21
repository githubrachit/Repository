package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ParentsDetails {
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalInsuranceCover")
    private String totalInsuranceCover;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualIncome")
    private String annualIncome;

    public ParentsDetails() {
    }

    public ParentsDetails(String totalInsuranceCover, String annualIncome) {
	super();
	this.totalInsuranceCover = totalInsuranceCover;
	this.annualIncome = annualIncome;
    }

    public String getTotalInsuranceCover() {
	return totalInsuranceCover;
    }

    public void setTotalInsuranceCover(String totalInsuranceCover) {
	this.totalInsuranceCover = totalInsuranceCover;
    }

    public String getAnnualIncome() {
	return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
	this.annualIncome = annualIncome;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ParentsDetails [totalInsuranceCover=" + totalInsuranceCover + ", annualIncome=" + annualIncome + "]";
    }

}
