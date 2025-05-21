package com.mli.mpro.pasa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class PasaProducts {
	
	@JsonProperty("productId")
	private String productId;
	@JsonProperty("productName")
	private String productName;
	@JsonProperty("productType")
    private String productType;
	@JsonProperty("premiumBackOption")
	private String premiumBackOption;
	@JsonProperty("lifeStageEventBenefit")
	private String lifeStageEventBenefit;
	@JsonProperty("deathBenefit")
	private String deathBenefit;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getPremiumBackOption() {
		return premiumBackOption;
	}
	public void setPremiumBackOption(String premiumBackOption) {
		this.premiumBackOption = premiumBackOption;
	}
	public String getLifeStageEventBenefit() {
		return lifeStageEventBenefit;
	}
	public void setLifeStageEventBenefit(String lifeStageEventBenefit) {
		this.lifeStageEventBenefit = lifeStageEventBenefit;
	}
	public String getDeathBenefit() {
		return deathBenefit;
	}
	public void setDeathBenefit(String deathBenefit) {
		this.deathBenefit = deathBenefit;
	}
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "PasaProducts [productId=" + productId + ", productName=" + productName + ", productType=" + productType
				+ ", premiumBackOption=" + premiumBackOption + ", lifeStageEventBenefit=" + lifeStageEventBenefit
				+ ", deathBenefit=" + deathBenefit + "]";
	}

	
}
