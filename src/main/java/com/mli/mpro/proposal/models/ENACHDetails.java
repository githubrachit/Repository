package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

/* POJO for Enach details mapping */
public class ENACHDetails {

    @JsonProperty("NPCICode")
    private String NPCICode;
    @JsonProperty("utilityCode")
    private String utilityCode;
    @Sensitive(MaskType.NAME)
    @JsonProperty("serviceProviderName")
    private String serviceProviderName;
    @JsonProperty("recurringFrequency")
    private String recurringFrequency;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("mandateAmount")
    private String mandateAmount;
    @JsonProperty("firstCollectionDate")
    private Date firstCollectionDate;
    @JsonProperty("finalCollectionDate")
    private Date finalCollectionDate;
    @JsonProperty("enachStatus")
    private String enachStatus;
    @JsonProperty("maxLifeRefNo")
    private String maxLifeRefNo;
    @JsonProperty("amountType")
    private String amountType;
    @JsonProperty("ingenicoMandateId")
    private String ingenicoMandateId;
    @JsonProperty("ingenicoTransactionId")
    private String ingenicoTransactionId;
    @JsonProperty("ingenicoBankCode")
    private String ingenicoBankCode;
    @JsonProperty("sponsorBank")
    private String sponsorBank;
    @JsonProperty("sponsorBankCode")
    private String sponsorBankCode;
    @JsonProperty("NPCICategoryName")
    private String NPCICategoryName;

    public String getNPCICode() {
	return NPCICode;
    }

    public void setNPCICode(String nPCICode) {
	NPCICode = nPCICode;
    }

    public String getUtilityCode() {
	return utilityCode;
    }

    public void setUtilityCode(String utilityCode) {
	this.utilityCode = utilityCode;
    }

    public String getServiceProviderName() {
	return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
	this.serviceProviderName = serviceProviderName;
    }

    public String getRecurringFrequency() {
	return recurringFrequency;
    }

    public void setRecurringFrequency(String recurringFrequency) {
	this.recurringFrequency = recurringFrequency;
    }

    public String getMandateAmount() {
	return mandateAmount;
    }

    public void setMandateAmount(String mandateAmount) {
	this.mandateAmount = mandateAmount;
    }

    public Date getFirstCollectionDate() {
	return firstCollectionDate;
    }

    public void setFirstCollectionDate(Date firstCollectionDate) {
	this.firstCollectionDate = firstCollectionDate;
    }

    public Date getFinalCollectionDate() {
	return finalCollectionDate;
    }

    public void setFinalCollectionDate(Date finalCollectionDate) {
	this.finalCollectionDate = finalCollectionDate;
    }

    public String getEnachStatus() {
        return enachStatus;
    }

    public void setEnachStatus(String enachStatus) {
        this.enachStatus = enachStatus;
    }

    public String getMaxLifeRefNo() {
        return maxLifeRefNo;
    }

    public void setMaxLifeRefNo(String maxLifeRefNo) {
        this.maxLifeRefNo = maxLifeRefNo;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getIngenicoMandateId() {
        return ingenicoMandateId;
    }

    public void setIngenicoMandateId(String ingenicoMandateId) {
        this.ingenicoMandateId = ingenicoMandateId;
    }

    public String getIngenicoTransactionId() {
        return ingenicoTransactionId;
    }

    public void setIngenicoTransactionId(String ingenicoTransactionId) {
        this.ingenicoTransactionId = ingenicoTransactionId;
    }

    public String getIngenicoBankCode() {
        return ingenicoBankCode;
    }

    public void setIngenicoBankCode(String ingenicoBankCode) {
        this.ingenicoBankCode = ingenicoBankCode;
    }

    public String getSponsorBank() {
        return sponsorBank;
    }

    public void setSponsorBank(String sponsorBank) {
        this.sponsorBank = sponsorBank;
    }

    public String getSponsorBankCode() {
        return sponsorBankCode;
    }

    public void setSponsorBankCode(String sponsorBankCode) {
        this.sponsorBankCode = sponsorBankCode;
    }

    public String getNPCICategoryName() {
        return NPCICategoryName;
    }

    public void setNPCICategoryName(String NPCICategoryName) {
        this.NPCICategoryName = NPCICategoryName;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ENACHDetails{" +
                "NPCICode='" + NPCICode + '\'' +
                ", utilityCode='" + utilityCode + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", recurringFrequency='" + recurringFrequency + '\'' +
                ", mandateAmount='" + mandateAmount + '\'' +
                ", firstCollectionDate=" + firstCollectionDate +
                ", finalCollectionDate=" + finalCollectionDate +
                ", enachStatus='" + enachStatus + '\'' +
                ", maxLifeRefNo='" + maxLifeRefNo + '\'' +
                ", amountType='" + amountType + '\'' +
                ", ingenicoMandateId='" + ingenicoMandateId + '\'' +
                ", ingenicoTransactionId='" + ingenicoTransactionId + '\'' +
                ", ingenicoBankCode='" + ingenicoBankCode + '\'' +
                ", sponsorBank='" + sponsorBank + '\'' +
                ", sponsorBankCode='" + sponsorBankCode + '\'' +
                ", NPCICategoryName='" + NPCICategoryName + '\'' +
                '}';
    }
}
