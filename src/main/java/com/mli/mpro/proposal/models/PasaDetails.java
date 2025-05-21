package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class PasaDetails {
	
	@JsonProperty("isPasaEligible")
	private String isPasaEligible;
	@JsonProperty("isYblPasaEligible")
	private String isYblPasaEligible;
	@JsonProperty("isPasaApplied")
	private String isPasaApplied;
	@JsonProperty("uniqueId")
	private String uniqueId;
	@JsonProperty("custId")
	private String custId;
	@JsonProperty("offerType")
	private String offerType;
	@JsonProperty("pasaCategory1")
	private String pasaCategory1;
	@JsonProperty("categoryCode1")
	private String categoryCode1;
	@JsonProperty("pasaCategory2")
	private String pasaCategory2;
	@JsonProperty("categoryCode2")
	private String categoryCode2;
	@JsonProperty("premium")
	private String premium;

	public String getIsYblPasaEligible() {
		return isYblPasaEligible;
	}

	public void setIsYblPasaEligible(String isYblPasaEligible) {
		this.isYblPasaEligible = isYblPasaEligible;
	}

	public String getIsPasaEligible() {
		return isPasaEligible;
	}
	public void setIsPasaEligible(String isPasaEligible) {
		this.isPasaEligible = isPasaEligible;
	}
	public String getIsPasaApplied() {
		return isPasaApplied;
	}
	public void setIsPasaApplied(String isPasaApplied) {
		this.isPasaApplied = isPasaApplied;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getPasaCategory1() {
		return pasaCategory1;
	}

	public void setPasaCategory1(String pasaCategory1) {
		this.pasaCategory1 = pasaCategory1;
	}

	public String getCategoryCode1() {
		return categoryCode1;
	}

	public void setCategoryCode1(String categoryCode1) {
		this.categoryCode1 = categoryCode1;
	}

	public String getPasaCategory2() {
		return pasaCategory2;
	}

	public void setPasaCategory2(String pasaCategory2) {
		this.pasaCategory2 = pasaCategory2;
	}

	public String getCategoryCode2() {
		return categoryCode2;
	}

	public void setCategoryCode2(String categoryCode2) {
		this.categoryCode2 = categoryCode2;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}


	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "PasaDetails{" +
				"isPasaEligible='" + isPasaEligible + '\'' +
				", isPasaApplied='" + isPasaApplied + '\'' +
				", uniqueId='" + uniqueId + '\'' +
				", custId='" + custId + '\'' +
				", offerType='" + offerType + '\'' +
				", pasaCategory1='" + pasaCategory1 + '\'' +
				", categoryCode1='" + categoryCode1 + '\'' +
				", pasaCategory2='" + pasaCategory2 + '\'' +
				", categoryCode2='" + categoryCode2 + '\'' +
				", premium='" + premium + '\'' +
				", isYblPasaEligible=" + isYblPasaEligible +
				'}';
	}
	

}
