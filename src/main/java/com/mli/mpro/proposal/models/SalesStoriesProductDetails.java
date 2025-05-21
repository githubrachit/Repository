package com.mli.mpro.proposal.models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class SalesStoriesProductDetails {

    @JsonProperty("salesReferenceId")
    private String salesReferenceId;

    @JsonProperty("primaryTransactionId")
    private long primaryTransactionId;

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("secondaryPolicyNum")
    private String secondaryPolicyNum;

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("primaryPolicyNumber")
    private String primaryPolicyNumber;

    @JsonProperty("secondaryMongoId")
    private String secondaryMongoId;

    @JsonProperty("secondaryTransactionId")
    private long secondaryTransactionId;

    @JsonProperty("isSalesProduct")
    private String isSalesProduct;

    @JsonProperty("productDetails")
    private List<ProductDetails> productDetails = null;
    
    @JsonProperty("medicalGridStatusSec")
    private String medicalGridStatusSec;
    
    @JsonProperty("financialGridStatusSec")
    private String financialGridStatusSec;

    public SalesStoriesProductDetails() {

    }

    public SalesStoriesProductDetails(String salesReferenceId, long primaryTransactionId, String secondaryPolicyNum, String primaryPolicyNumber,
	    String secondaryMongoId, long secondaryTransactionId, String isSalesProduct, List<ProductDetails> productDetails) {
	super();
	this.salesReferenceId = salesReferenceId;
	this.primaryTransactionId = primaryTransactionId;
	this.secondaryPolicyNum = secondaryPolicyNum;
	this.primaryPolicyNumber = primaryPolicyNumber;
	this.secondaryMongoId = secondaryMongoId;
	this.secondaryTransactionId = secondaryTransactionId;
	this.isSalesProduct = isSalesProduct;
	this.productDetails = productDetails;
    }

    public SalesStoriesProductDetails(String salesReferenceId, String secondaryMongoId, long secondaryTransactionId, String secondaryPolicyNum) {

	this.salesReferenceId = salesReferenceId;
	this.secondaryPolicyNum = secondaryPolicyNum;
	this.secondaryMongoId = secondaryMongoId;
	this.secondaryTransactionId = secondaryTransactionId;

    }

    public SalesStoriesProductDetails(SalesStoriesProductDetails salesStoriesProductDetails) {
	List<ProductDetails> productDetailsList = salesStoriesProductDetails.productDetails;

	productDetailsList = (productDetailsList != null && productDetailsList.size() != 0) ? productDetailsList.stream().collect(Collectors.toList())
		: productDetailsList;

	this.productDetails = productDetailsList;
	this.isSalesProduct = salesStoriesProductDetails.isSalesProduct;
    }

    public String getSalesReferenceId() {
	return salesReferenceId;
    }

    public void setSalesReferenceId(String salesReferenceId) {
	this.salesReferenceId = salesReferenceId;
    }

    public long getPrimaryTransactionId() {
	return primaryTransactionId;
    }

    public void setPrimaryTransactionId(long primaryTransactionId) {
	this.primaryTransactionId = primaryTransactionId;
    }

    public String getSecondaryPolicyNum() {
	return secondaryPolicyNum;
    }

    public void setSecondaryPolicyNum(String secondaryPolicyNum) {
	this.secondaryPolicyNum = secondaryPolicyNum;
    }

    public String getSecondaryMongoId() {
	return secondaryMongoId;
    }

    public void setSecondaryMongoId(String secondaryMongoId) {
	this.secondaryMongoId = secondaryMongoId;
    }

    public long getSecondaryTransactionId() {
	return secondaryTransactionId;
    }

    public void setSecondaryTransactionId(long secondaryTransactionId) {
	this.secondaryTransactionId = secondaryTransactionId;
    }

    public String getIsSalesProduct() {
	return isSalesProduct;
    }

    public void setIsSalesProduct(String isSalesProduct) {
	this.isSalesProduct = isSalesProduct;
    }

    public String getPrimaryPolicyNumber() {
	return primaryPolicyNumber;
    }

    public void setPrimaryPolicyNumber(String primaryPolicyNumber) {
	this.primaryPolicyNumber = primaryPolicyNumber;
    }

    public List<ProductDetails> getProductDetails() {
	return productDetails;
    }

    public void setProductDetails(List<ProductDetails> productDetails) {
	this.productDetails = productDetails;
    }
    

    public String getMedicalGridStatusSec() {
        return medicalGridStatusSec;
    }

    public void setMedicalGridStatusSec(String medicalGridStatusSec) {
        this.medicalGridStatusSec = medicalGridStatusSec;
    }

    public String getFinancialGridStatusSec() {
        return financialGridStatusSec;
    }

    public void setFinancialGridStatusSec(String financialGridStatusSec) {
        this.financialGridStatusSec = financialGridStatusSec;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "SalesStoriesProductDetails [salesReferenceId=" + salesReferenceId + ", primaryTransactionId=" + primaryTransactionId + ", secondaryPolicyNum="
		+ secondaryPolicyNum + ", primaryPolicyNumber=" + primaryPolicyNumber + ", secondaryMongoId=" + secondaryMongoId + ", secondaryTransactionId="
		+ secondaryTransactionId + ", isSalesProduct=" + isSalesProduct + ", productDetails=" + productDetails + ", medicalGridStatusSec="
		+ medicalGridStatusSec + ", financialGridStatusSec=" + financialGridStatusSec + "]";
    }


}
