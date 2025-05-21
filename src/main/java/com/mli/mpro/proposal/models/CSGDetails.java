package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/**
 * This class is used to store specific variables required for CSG-MPro
 * integration.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CSGDetails {
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("incomeRange")
    private String incomeRange;

    @JsonProperty("recommendedProducts")
    private String recommendedProducts;
    @JsonProperty("isPSMSelected")
    private String isPSMSelected;
    @JsonProperty("illustrationPDF")
    private String illustrationPDF;
    @JsonProperty("irpQ1Option")
    private String irpQ1Option;
    @JsonProperty("irpQ2Option")
    private String irpQ2Option;
    @JsonProperty("irpQ3Option")
    private String irpQ3Option;
    @JsonProperty("irpQ4Option")
    private String irpQ4Option;
    @JsonProperty("sourceOfSale")
    private String sourceOfSale;
    @JsonProperty("commissionShare")
    private List<CommissionShare> commissionShare;
    @JsonProperty("irpScore")
    private String irpScore;
    
    public CSGDetails() {
    }

    public CSGDetails(String incomeRange, String recommendedProducts, String isPSMSelected, String illustrationPDF, String irpQ1Option, String irpQ2Option,
	    String irpQ3Option, String irpQ4Option, String sourceOfSale, List<CommissionShare> commissionShare, String irpScore) {
	super();
	this.incomeRange = incomeRange;
	this.recommendedProducts = recommendedProducts;
	this.isPSMSelected = isPSMSelected;
	this.illustrationPDF = illustrationPDF;
	this.irpQ1Option = irpQ1Option;
	this.irpQ2Option = irpQ2Option;
	this.irpQ3Option = irpQ3Option;
	this.irpQ4Option = irpQ4Option;
	this.sourceOfSale = sourceOfSale;
	this.commissionShare = commissionShare;
	this.irpScore = irpScore;
    }

    

    public CSGDetails(CSGDetails csgDetails) {
	// TODO Auto-generated constructor stub
    }

    public String getIncomeRange() {
	return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
	this.incomeRange = incomeRange;
    }

    public String getRecommendedProducts() {
	return recommendedProducts;
    }

    public void setRecommendedProducts(String recommendedProducts) {
	this.recommendedProducts = recommendedProducts;
    }

    public String getIsPSMSelected() {
	return isPSMSelected;
    }

    public void setIsPSMSelected(String isPSMSelected) {
	this.isPSMSelected = isPSMSelected;
    }

    public String getIllustrationPDF() {
	return illustrationPDF;
    }

    public void setIllustrationPDF(String illustrationPDF) {
	this.illustrationPDF = illustrationPDF;
    }

    public String getIrpQ1Option() {
	return irpQ1Option;
    }

    public void setIrpQ1Option(String irpQ1Option) {
	this.irpQ1Option = irpQ1Option;
    }

    public String getIrpQ2Option() {
	return irpQ2Option;
    }

    public void setIrpQ2Option(String irpQ2Option) {
	this.irpQ2Option = irpQ2Option;
    }

    public String getIrpQ3Option() {
	return irpQ3Option;
    }

    public void setIrpQ3Option(String irpQ3Option) {
	this.irpQ3Option = irpQ3Option;
    }

    public String getIrpQ4Option() {
	return irpQ4Option;
    }

    public void setIrpQ4Option(String irpQ4Option) {
	this.irpQ4Option = irpQ4Option;
    }

    public String getSourceOfSale() {
	return sourceOfSale;
    }

    public void setSourceOfSale(String sourceOfSale) {
	this.sourceOfSale = sourceOfSale;
    }

    public List<CommissionShare> getCommissionShare() {
	return commissionShare;
    }

    public void setCommissionShare(List<CommissionShare> commissionShare) {
	this.commissionShare = commissionShare;
    }

    public String getIrpScore() {
        return irpScore;
    }

    public void setIrpScore(String irpScore) {
        this.irpScore = irpScore;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "CSGDetails [incomeRange=" + incomeRange + ", recommendedProducts=" + recommendedProducts + ", isPSMSelected=" + isPSMSelected
		+ ", illustrationPDF=" + illustrationPDF + ", irpQ1Option=" + irpQ1Option + ", irpQ2Option=" + irpQ2Option + ", irpQ3Option=" + irpQ3Option
		+ ", irpQ4Option=" + irpQ4Option + ", sourceOfSale=" + sourceOfSale + ", commissionShare=" + commissionShare + ", irpScore=" + irpScore + "]";
    }
}
