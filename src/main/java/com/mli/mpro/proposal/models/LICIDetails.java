package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class LICIDetails {

    @JsonProperty("nameOfInsuranceCompany")
    private String nameOfInsuranceCompany;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalSumAssured")
    private String totalSumAssured;
    @JsonProperty("policyStatus")
    private String policyStatus;
    @JsonProperty("typeOfPolicy")
    private String typeOfPolicy;

    public LICIDetails() {
    }

    public LICIDetails(String nameOfInsuranceCompany, String policyNumber, String totalSumAssured, String policyStatus, String typeOfPolicy) {
	super();
	this.nameOfInsuranceCompany = nameOfInsuranceCompany;
	this.policyNumber = policyNumber;
	this.totalSumAssured = totalSumAssured;
	this.policyStatus = policyStatus;
	this.typeOfPolicy = typeOfPolicy;
    }

    public String getNameOfInsuranceCompany() {
	return nameOfInsuranceCompany;
    }

    public void setNameOfInsuranceCompany(String nameOfInsuranceCompany) {
	this.nameOfInsuranceCompany = nameOfInsuranceCompany;
    }

    public String getPolicyNumber() {
	return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    public String getTotalSumAssured() {
	return totalSumAssured;
    }

    public void setTotalSumAssured(String totalSumAssured) {
	this.totalSumAssured = totalSumAssured;
    }

    public String getPolicyStatus() {
	return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
	this.policyStatus = policyStatus;
    }

    public String getTypeOfPolicy() {
	return typeOfPolicy;
    }

    public void setTypeOfPolicy(String typeOfPolicy) {
	this.typeOfPolicy = typeOfPolicy;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "EverLIIssuedPendingLapsedRejectedPostponedDetail [nameOfInsuranceCompany=" + nameOfInsuranceCompany + ", policyNumber=" + policyNumber
		+ ", totalSumAssured=" + totalSumAssured + ", policyStatus=" + policyStatus + ", typeOfPolicy=" + typeOfPolicy + "]";
    }

}
