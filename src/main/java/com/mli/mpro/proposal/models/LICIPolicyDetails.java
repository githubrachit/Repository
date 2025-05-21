package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class LICIPolicyDetails {

    @JsonProperty("isLIIssued")
    private boolean isLIIssued;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalSumAssuredForLI")
    private String totalSumAssuredForLI;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalSumAssuredForCI")
    private String totalSumAssuredForCI;
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

    public LICIPolicyDetails() {
    }

    public LICIPolicyDetails(boolean isLIIssued, String totalSumAssuredForLI, String totalSumAssuredForCI, String nameOfInsuranceCompany, String policyNumber,
	    String totalSumAssured, String policyStatus, String typeOfPolicy) {
	super();
	this.isLIIssued = isLIIssued;
	this.totalSumAssuredForLI = totalSumAssuredForLI;
	this.totalSumAssuredForCI = totalSumAssuredForCI;
	this.nameOfInsuranceCompany = nameOfInsuranceCompany;
	this.policyNumber = policyNumber;
	this.totalSumAssured = totalSumAssured;
	this.policyStatus = policyStatus;
	this.typeOfPolicy = typeOfPolicy;
    }

    public boolean isLIIssued() {
	return isLIIssued;
    }

    public void setLIIssued(boolean isLIIssued) {
	this.isLIIssued = isLIIssued;
    }

    public String getTotalSumAssuredForLI() {
	return totalSumAssuredForLI;
    }

    public void setTotalSumAssuredForLI(String totalSumAssuredForLI) {
	this.totalSumAssuredForLI = totalSumAssuredForLI;
    }

    public String getTotalSumAssuredForCI() {
	return totalSumAssuredForCI;
    }

    public void setTotalSumAssuredForCI(String totalSumAssuredForCI) {
	this.totalSumAssuredForCI = totalSumAssuredForCI;
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
	return "LICIPolicyDetails [isLIIssued=" + isLIIssued + ", totalSumAssuredForLI=" + totalSumAssuredForLI + ", totalSumAssuredForCI="
		+ totalSumAssuredForCI + ", nameOfInsuranceCompany=" + nameOfInsuranceCompany + ", policyNumber=" + policyNumber + ", totalSumAssured="
		+ totalSumAssured + ", policyStatus=" + policyStatus + ", typeOfPolicy=" + typeOfPolicy + "]";
    }
}