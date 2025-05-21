package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class PSMDetails {

    private String isExistingLICover;
    private String existingLICover;
    private String riskAppetite;
    private List<String> recommendedProducts;
    private String recommendedProductName;
    @Sensitive(MaskType.AMOUNT)
    private String totalSumAssured;
    private String bypass;

    public String getTotalSumAssured() {
	return totalSumAssured;
    }

    public void setTotalSumAssured(String totalSumAssured) {
	this.totalSumAssured = totalSumAssured;
    }

    public String getBypass() {
	return bypass;
    }

    public void setBypass(String bypass) {
	this.bypass = bypass;
    }

    public String getIsExistingLICover() {
	return isExistingLICover;
    }

    public void setIsExistingLICover(String isExistingLICover) {
	this.isExistingLICover = isExistingLICover;
    }

    public String getExistingLICover() {
	return existingLICover;
    }

    public void setExistingLICover(String existingLICover) {
	this.existingLICover = existingLICover;
    }

    public String getRiskAppetite() {
        return riskAppetite;
    }

    public void setRiskAppetite(String riskAppetite) {
        this.riskAppetite = riskAppetite;
    }

    public List<String> getRecommendedProducts() {
	return recommendedProducts;
    }

    public void setRecommendedProducts(List<String> recommendedProducts) {
	this.recommendedProducts = recommendedProducts;
    }

    public String getRecommendedProductName() {
	return recommendedProductName;
    }

    public void setRecommendedProductName(String recommendedProductName) {
	this.recommendedProductName = recommendedProductName;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PSMDetails{" + "isExistingLICover='" + isExistingLICover + '\'' + ", existingLICover='" + existingLICover + '\'' + ", riskAppetite='" + riskAppetite + '\'' + ", recommendedProducts="
		+ recommendedProducts + ", recommendedProductName='" + recommendedProductName + '\'' + ", totalSumAssured='" + totalSumAssured + '\''
		+ ", bypass='" + bypass + '\'' + '}';
    }
}
