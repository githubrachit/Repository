package com.mli.mpro.location.models.clientPolicyDetailsResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.RiderDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "policyNo", "planCode", "baseCoverStatusCode", "sumAssured", "annualPremium", "premiumPayingTerm", "issueDate", "premiumPaidToDate",
	"lastMedicalDate", "lastMedicalCategoryRecd", "atrMarked", "transferToExternalSystemCode", "claimPaidRequestedOnAnyPolicyCode",
	"claimPaidRequestedOnAnyRiderCode", "proposerTotalAnnualPremium", "monthlyIncome", "uwDecision", "modelPremium", "counterOfferStatus", "channelCode",
	"emrMultExtraFlatExtraCases", "replacementSale", "isProposer", "isInsured", "riderDetails" })
public class Payload {

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNo")
    private String policyNo;
    @JsonProperty("planCode")
    private String planCode;
    @JsonProperty("baseCoverStatusCode")
    private String baseCoverStatusCode;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("sumAssured")
    private String sumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualPremium")
    private String annualPremium;
    @JsonProperty("premiumPayingTerm")
    private String premiumPayingTerm;
    @JsonProperty("issueDate")
    private String issueDate;
    @JsonProperty("premiumPaidToDate")
    private String premiumPaidToDate;
    @JsonProperty("lastMedicalDate")
    private String lastMedicalDate;
    @JsonProperty("lastMedicalCategoryRecd")
    private String lastMedicalCategoryRecd;
    @JsonProperty("atrMarked")
    private String atrMarked;
    @JsonProperty("transferToExternalSystemCode")
    private String transferToExternalSystemCode;
    @JsonProperty("claimPaidRequestedOnAnyPolicyCode")
    private String claimPaidRequestedOnAnyPolicyCode;
    @JsonProperty("claimPaidRequestedOnAnyRiderCode")
    private String claimPaidRequestedOnAnyRiderCode;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("proposerTotalAnnualPremium")
    private String proposerTotalAnnualPremium;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("monthlyIncome")
    private String monthlyIncome;
    @JsonProperty("uwDecision")
    private String uwDecision;
    @JsonProperty("modelPremium")
    private String modelPremium;
    @JsonProperty("counterOfferStatus")
    private String counterOfferStatus;
    @JsonProperty("channelCode")
    private String channelCode;
    @JsonProperty("emrMultExtraFlatExtraCases")
    private String emrMultExtraFlatExtraCases;
    @JsonProperty("replacementSale")
    private String replacementSale;
    @JsonProperty("isProposer")
    private String isProposer;
    @JsonProperty("isInsured")
    private String isInsured;
    @JsonProperty("riderDetails")
    private ArrayList<RiderDetails> riderDetails;
    private String deathBenefit;

    public Payload() {
	super();
    }

    public Payload(String policyNo, String planCode, String baseCoverStatusCode, String sumAssured, String annualPremium, String premiumPayingTerm,
                   String issueDate, String premiumPaidToDate, String monthlyIncome, String modelPremium, String channelCode, String isProposer, String isInsured,
                   ArrayList<RiderDetails> riderDetails, String deathBenefit) {
	super();
	this.policyNo = policyNo;
	this.planCode = planCode;
	this.baseCoverStatusCode = baseCoverStatusCode;
	this.sumAssured = sumAssured;
	this.annualPremium = annualPremium;
	this.premiumPayingTerm = premiumPayingTerm;
	this.issueDate = issueDate;
	this.premiumPaidToDate = premiumPaidToDate;
	this.monthlyIncome = monthlyIncome;
	this.modelPremium = modelPremium;
	this.channelCode = channelCode;
	this.isProposer = isProposer;
	this.isInsured = isInsured;
	this.riderDetails = riderDetails;
	this.deathBenefit = deathBenefit;
    }

    public Payload(String policyNo, String baseCoverStatusCode, String atrMarked, String transferToExternalSystemCode, String claimPaidRequestedOnAnyPolicyCode,
	    String claimPaidRequestedOnAnyRiderCode, String uwDecision, String counterOfferStatus, String channelCode, String emrMultExtraFlatExtraCases) {
	super();
	this.policyNo = policyNo;
	this.baseCoverStatusCode = baseCoverStatusCode;
	this.atrMarked = atrMarked;
	this.transferToExternalSystemCode = transferToExternalSystemCode;
	this.claimPaidRequestedOnAnyPolicyCode = claimPaidRequestedOnAnyPolicyCode;
	this.claimPaidRequestedOnAnyRiderCode = claimPaidRequestedOnAnyRiderCode;
	this.uwDecision = uwDecision;
	this.counterOfferStatus = counterOfferStatus;
	this.channelCode = channelCode;
	this.emrMultExtraFlatExtraCases = emrMultExtraFlatExtraCases;
    }

    @JsonProperty("policyNo")
    public String getPolicyNo() {
	return policyNo;
    }

    @JsonProperty("policyNo")
    public void setPolicyNo(String policyNo) {
	this.policyNo = policyNo;
    }

    @JsonProperty("planCode")
    public String getPlanCode() {
	return planCode;
    }

    @JsonProperty("planCode")
    public void setPlanCode(String planCode) {
	this.planCode = planCode;
    }

    @JsonProperty("baseCoverStatusCode")
    public String getBaseCoverStatusCode() {
	return baseCoverStatusCode;
    }

    @JsonProperty("baseCoverStatusCode")
    public void setBaseCoverStatusCode(String baseCoverStatusCode) {
	this.baseCoverStatusCode = baseCoverStatusCode;
    }

    @JsonProperty("sumAssured")
    public String getSumAssured() {
	return sumAssured;
    }

    @JsonProperty("sumAssured")
    public void setSumAssured(String sumAssured) {
	this.sumAssured = sumAssured;
    }

    @JsonProperty("annualPremium")
    public String getAnnualPremium() {
	return annualPremium;
    }

    @JsonProperty("annualPremium")
    public void setAnnualPremium(String annualPremium) {
	this.annualPremium = annualPremium;
    }

    @JsonProperty("premiumPayingTerm")
    public String getPremiumPayingTerm() {
	return premiumPayingTerm;
    }

    @JsonProperty("premiumPayingTerm")
    public void setPremiumPayingTerm(String premiumPayingTerm) {
	this.premiumPayingTerm = premiumPayingTerm;
    }

    @JsonProperty("issueDate")
    public String getIssueDate() {
	return issueDate;
    }

    @JsonProperty("issueDate")
    public void setIssueDate(String issueDate) {
	this.issueDate = issueDate;
    }

    @JsonProperty("premiumPaidToDate")
    public String getPremiumPaidToDate() {
	return premiumPaidToDate;
    }

    @JsonProperty("premiumPaidToDate")
    public void setPremiumPaidToDate(String premiumPaidToDate) {
	this.premiumPaidToDate = premiumPaidToDate;
    }

    @JsonProperty("lastMedicalDate")
    public String getLastMedicalDate() {
	return lastMedicalDate;
    }

    @JsonProperty("lastMedicalDate")
    public void setLastMedicalDate(String lastMedicalDate) {
	this.lastMedicalDate = lastMedicalDate;
    }

    @JsonProperty("lastMedicalCategoryRecd")
    public String getLastMedicalCategoryRecd() {
	return lastMedicalCategoryRecd;
    }

    @JsonProperty("lastMedicalCategoryRecd")
    public void setLastMedicalCategoryRecd(String lastMedicalCategoryRecd) {
	this.lastMedicalCategoryRecd = lastMedicalCategoryRecd;
    }

    @JsonProperty("atrMarked")
    public String getAtrMarked() {
	return atrMarked;
    }

    @JsonProperty("atrMarked")
    public void setAtrMarked(String atrMarked) {
	this.atrMarked = atrMarked;
    }

    @JsonProperty("transferToExternalSystemCode")
    public String getTransferToExternalSystemCode() {
	return transferToExternalSystemCode;
    }

    @JsonProperty("transferToExternalSystemCode")
    public void setTransferToExternalSystemCode(String transferToExternalSystemCode) {
	this.transferToExternalSystemCode = transferToExternalSystemCode;
    }

    @JsonProperty("claimPaidRequestedOnAnyPolicyCode")
    public String getClaimPaidRequestedOnAnyPolicyCode() {
	return claimPaidRequestedOnAnyPolicyCode;
    }

    @JsonProperty("claimPaidRequestedOnAnyPolicyCode")
    public void setClaimPaidRequestedOnAnyPolicyCode(String claimPaidRequestedOnAnyPolicyCode) {
	this.claimPaidRequestedOnAnyPolicyCode = claimPaidRequestedOnAnyPolicyCode;
    }

    @JsonProperty("claimPaidRequestedOnAnyRiderCode")
    public String getClaimPaidRequestedOnAnyRiderCode() {
	return claimPaidRequestedOnAnyRiderCode;
    }

    @JsonProperty("claimPaidRequestedOnAnyRiderCode")
    public void setClaimPaidRequestedOnAnyRiderCode(String claimPaidRequestedOnAnyRiderCode) {
	this.claimPaidRequestedOnAnyRiderCode = claimPaidRequestedOnAnyRiderCode;
    }

    @JsonProperty("proposerTotalAnnualPremium")
    public String getProposerTotalAnnualPremium() {
	return proposerTotalAnnualPremium;
    }

    @JsonProperty("proposerTotalAnnualPremium")
    public void setProposerTotalAnnualPremium(String proposerTotalAnnualPremium) {
	this.proposerTotalAnnualPremium = proposerTotalAnnualPremium;
    }

    @JsonProperty("monthlyIncome")
    public String getMonthlyIncome() {
	return monthlyIncome;
    }

    @JsonProperty("monthlyIncome")
    public void setMonthlyIncome(String monthlyIncome) {
	this.monthlyIncome = monthlyIncome;
    }

    @JsonProperty("uwDecision")
    public String getUwDecision() {
	return uwDecision;
    }

    @JsonProperty("uwDecision")
    public void setUwDecision(String uwDecision) {
	this.uwDecision = uwDecision;
    }

    @JsonProperty("modelPremium")
    public String getModelPremium() {
	return modelPremium;
    }

    @JsonProperty("modelPremium")
    public void setModelPremium(String modelPremium) {
	this.modelPremium = modelPremium;
    }

    @JsonProperty("counterOfferStatus")
    public String getCounterOfferStatus() {
	return counterOfferStatus;
    }

    @JsonProperty("counterOfferStatus")
    public void setCounterOfferStatus(String counterOfferStatus) {
	this.counterOfferStatus = counterOfferStatus;
    }

    @JsonProperty("channelCode")
    public String getChannelCode() {
	return channelCode;
    }

    @JsonProperty("channelCode")
    public void setChannelCode(String channelCode) {
	this.channelCode = channelCode;
    }

    @JsonProperty("emrMultExtraFlatExtraCases")
    public String getEmrMultExtraFlatExtraCases() {
	return emrMultExtraFlatExtraCases;
    }

    @JsonProperty("emrMultExtraFlatExtraCases")
    public void setEmrMultExtraFlatExtraCases(String emrMultExtraFlatExtraCases) {
	this.emrMultExtraFlatExtraCases = emrMultExtraFlatExtraCases;
    }

    @JsonProperty("replacementSale")
    public String getReplacementSale() {
	return replacementSale;
    }

    @JsonProperty("replacementSale")
    public void setReplacementSale(String replacementSale) {
	this.replacementSale = replacementSale;
    }

    @JsonProperty("isProposer")
    public String getIsProposer() {
	return isProposer;
    }

    @JsonProperty("isProposer")
    public void setIsProposer(String isProposer) {
	this.isProposer = isProposer;
    }

    @JsonProperty("isInsured")
    public String getIsInsured() {
	return isInsured;
    }

    @JsonProperty("isInsured")
    public void setIsInsured(String isInsured) {
	this.isInsured = isInsured;
    }

    @JsonProperty("riderDetails")
    public List<RiderDetails> getRiderDetails() {
	return riderDetails;
    }

    @JsonProperty("riderDetails")
    public void setRiderDetails(ArrayList<RiderDetails> riderDetails) {
	this.riderDetails = riderDetails;
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

	return "Payload [policyNo=" + policyNo + ", planCode=" + planCode + ", baseCoverStatusCode=" + baseCoverStatusCode + ", sumAssured=" + sumAssured
		+ ", annualPremium=" + annualPremium + ", premiumPayingTerm=" + premiumPayingTerm + ", issueDate=" + issueDate + ", premiumPaidToDate="
		+ premiumPaidToDate + ", lastMedicalDate=" + lastMedicalDate + ", lastMedicalCategoryRecd=" + lastMedicalCategoryRecd + ", atrMarked="
		+ atrMarked + ", transferToExternalSystemCode=" + transferToExternalSystemCode + ", claimPaidRequestedOnAnyPolicyCode="
		+ claimPaidRequestedOnAnyPolicyCode + ", claimPaidRequestedOnAnyRiderCode=" + claimPaidRequestedOnAnyRiderCode + ", proposerTotalAnnualPremium="
		+ proposerTotalAnnualPremium + ", monthlyIncome=" + monthlyIncome + ", uwDecision=" + uwDecision + ", modelPremium=" + modelPremium
		+ ", counterOfferStatus=" + counterOfferStatus + ", channelCode=" + channelCode + ", emrMultExtraFlatExtraCases=" + emrMultExtraFlatExtraCases
		+ ", replacementSale=" + replacementSale + ", isProposer=" + isProposer + ", isInsured=" + isInsured + ", riderDetails=" + riderDetails
		+ ", deathBenefit=" + deathBenefit + "]";
    }

}
