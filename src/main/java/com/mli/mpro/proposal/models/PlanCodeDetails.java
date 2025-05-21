package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "planCode")
public class PlanCodeDetails {

    @JsonProperty("planCode")
    private String planCode;
    @JsonProperty("planDesc")
    private String planDesc;
    @JsonProperty("coverageTerm")
    private String coverageTerm;
    @JsonProperty("ppt")
    private String ppt;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("premiumType")
    private String premiumType;

    public PlanCodeDetails() {
    }

    public PlanCodeDetails(String planCode, String planDesc, String coverageTerm, String ppt, String productCode, String premiumType) {
	super();
	this.planCode = planCode;
	this.planDesc = planDesc;
	this.coverageTerm = coverageTerm;
	this.ppt = ppt;
	this.productCode = productCode;
	this.premiumType = premiumType;
    }

    public String getPlanCode() {
	return planCode;
    }

    public void setPlanCode(String planCode) {
	this.planCode = planCode;
    }

    public String getPlanDesc() {
	return planDesc;
    }

    public void setPlanDesc(String planDesc) {
	this.planDesc = planDesc;
    }

    public String getCoverageTerm() {
	return coverageTerm;
    }

    public void setCoverageTerm(String coverageTerm) {
	this.coverageTerm = coverageTerm;
    }

    public String getPpt() {
	return ppt;
    }

    public void setPpt(String ppt) {
	this.ppt = ppt;
    }

    public String getProductCode() {
	return productCode;
    }

    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    public String getPremiumType() {
	return premiumType;
    }

    public void setPremiumType(String premiumType) {
	this.premiumType = premiumType;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PlanCodeDetails [planCode=" + planCode + ", planDesc=" + planDesc + ", coverageTerm=" + coverageTerm + ", ppt=" + ppt + ", productCode="
		+ productCode + ", premiumType=" + premiumType + "]";
    }

}
