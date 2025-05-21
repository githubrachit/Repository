package com.mli.mpro.underwriting.clientPolicyDetailsResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "policyNo", "planCode", "baseCoverStatusCode", "sumAssured", "annualPremium", "premiumPayingTerm", "issueDate", "premiumPaidToDate",
        "lastMedicalDate", "lastMedicalCategoryRecd", "atrMarked", "transferToExternalSystemCode", "claimPaidRequestedOnAnyPolicyCode",
        "claimPaidRequestedOnAnyRiderCode", "proposerTotalAnnualPremium", "monthlyIncome", "uwDecision", "modelPremium", "counterOfferStatus", "channelCode",
        "emrMultExtraFlatExtraCases", "replacementSale", "isProposer", "isInsured", "riderDetails", "deathBenefitOption", "lifeEvent", "gdb", "policyTerm", "atrComments", "reasonForCoDecCanPst"})
public class PolicyDetails {

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
    @Sensitive(MaskType.AMOUNT)
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
    //FUL2-37114: CPD changes
    @JsonProperty("initiativeType")
    private String initiativeType;
    @JsonProperty("baseCoverStatusCodeDesc")
    private String baseCoverStatusCodeDesc;
    //FUL2-140425 Client Level AFYP Logic correction
    @JsonProperty("planType")
    private String planType;
    @JsonProperty("isPayor")
    private String isPayor;
    @JsonProperty("batchDate")
    private String batchDate;
    @JsonProperty("category")
    private String category;
    @JsonProperty("modeOfPayment")
    private String modeOfPayment;
    @JsonProperty("dueDateCTP")
    private String dueDateCtp;
    @JsonProperty("isPremiumDue")
    private String isPremiumDue;
    @JsonProperty("dueAmountCTP")
    private String dueAmountCtp;
    @JsonProperty("isFYSame")
    private String isFySame;
    @JsonProperty("isFYPConsider")
    private String isFypConsider;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("insuredIncome")
    private String insuredIncome;
    @JsonProperty("insuredOccupation")
    private String insuredOccupation;
    @JsonProperty("insuredEducation")
    private String insuredEducation;
    @JsonProperty("insuredSmokerClass")
    private String insuredSmokerClass;
    @JsonProperty("craPinCode")
    private String craPinCode;
    @JsonProperty("revivalPeriodEndDate")
    private String revivalPeriodEndDate;
    @JsonProperty("riderDetails")
    private ArrayList<RiderDetails> riderDetails;

    /*FUL2-32184 we need to consider gdb value instead of sum assured for max SA check for Axis and YBL POS*/
    @JsonProperty("gdb")
    private String gdb;

    @JsonProperty("deathBenefitOption")
    private String deathBenefitOption;

    @JsonProperty("lifeEvent")
    private String lifeEvent;

    @JsonProperty("policyTerm")
    private String policyTerm;

    @JsonProperty("atrComments")
    private String atrComments;

    @JsonProperty("reasonForCoDecCanPst")
    private String reasonForCoDecCanPst;
    
    @JsonProperty("pep")
    private String pep;
    
    @JsonProperty("fatfCountry")
    private String fatfCountry;
    
    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("policyMedCat")
    private String policyMedCat;

    @JsonProperty("neftQcTag")
    private String neftQcTag;
    
    @JsonProperty("pep")
    public String getPep() {
		return pep;
	}

    @JsonProperty("pep")
	public void setPep(String pep) {
		this.pep = pep;
	}

    @JsonProperty("fatfCountry")
	public String getFatfCountry() {
		return fatfCountry;
	}

    @JsonProperty("fatfCountry")
	public void setFatfCountry(String fatfCountry) {
		this.fatfCountry = fatfCountry;
	}

    @JsonProperty("countryName")
	public String getCountryName() {
		return countryName;
	}

    @JsonProperty("countryName")
	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

    @JsonProperty("initiativeType")
    public String getInitiativeType() {
        return initiativeType;
    }

    @JsonProperty("initiativeType")
    public void setInitiativeType(String initiativeType) {
        this.initiativeType = initiativeType;
    }

    @JsonProperty("baseCoverStatusCodeDesc")
    public String getBaseCoverStatusCodeDesc() {
        return baseCoverStatusCodeDesc;
    }

    @JsonProperty("baseCoverStatusCodeDesc")
    public void setBaseCoverStatusCodeDesc(String baseCoverStatusCodeDesc) {
        this.baseCoverStatusCodeDesc = baseCoverStatusCodeDesc;
    }

    @JsonProperty("riderDetails")
    public List<RiderDetails> getRiderDetails() {
        return riderDetails;
    }

    @JsonProperty("riderDetails")
    public void setRiderDetails(ArrayList<RiderDetails> riderDetails) {
        this.riderDetails = riderDetails;
    }

    public String getGdb() {
        return gdb;
    }

    public void setGdb(String gdb) {
        this.gdb = gdb;
    }

    @JsonProperty("deathBenefitOption")
    public String getDeathBenefitOption() {
        return deathBenefitOption;
    }
    @JsonProperty("deathBenefitOption")
    public void setDeathBenefitOption(String deathBenefitOption) {
        this.deathBenefitOption = deathBenefitOption;
    }

    @JsonProperty("lifeEvent")
    public String getLifeEvent() {
        return lifeEvent;
    }

    @JsonProperty("lifeEvent")
    public void setLifeEvent(String lifeEvent) {
        this.lifeEvent = lifeEvent;
    }

    @JsonProperty("policyTerm")
    public String getPolicyTerm() {
        return policyTerm;
    }

    @JsonProperty("policyTerm")
    public void setPolicyTerm(String policyTerm) {
        this.policyTerm = policyTerm;
    }
    @JsonProperty("atrComments")
    public String getAtrComments() {
        return atrComments;
    }

    @JsonProperty("atrComments")
    public void setAtrComments(String atrComments) {
        this.atrComments = atrComments;
    }

    @JsonProperty("reasonForCoDecCanPst")
    public String getReasonForCoDecCanPst() {
        return reasonForCoDecCanPst;
    }


    @JsonProperty("reasonForCoDecCanPst")
    public void setReasonForCoDecCanPst(String reasonForCoDecCanPst) {
        this.reasonForCoDecCanPst = reasonForCoDecCanPst;
    }

    @JsonProperty("planType")
    public String getPlanType() {
        return planType;
    }

    @JsonProperty("planType")
    public void setPlanType(String planType) {
        this.planType = planType;
    }

    @JsonProperty("isPayor")
    public String getIsPayor() {
        return isPayor;
    }

    @JsonProperty("isPayor")
    public void setIsPayor(String isPayor) {
        this.isPayor = isPayor;
    }

    @JsonProperty("batchDate")
    public String getBatchDate() {
        return batchDate;
    }

    @JsonProperty("batchDate")
    public void setBatchDate(String batchDate) {
        this.batchDate = batchDate;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("modeOfPayment")
    public String getModeOfPayment() {
        return modeOfPayment;
    }

    @JsonProperty("modeOfPayment")
    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    @JsonProperty("dueDateCTP")
    public String getDueDateCtp() {
        return dueDateCtp;
    }

    @JsonProperty("dueDateCTP")
    public void setDueDateCtp(String dueDateCtp) {
        this.dueDateCtp = dueDateCtp;
    }

    @JsonProperty("isPremiumDue")
    public String getIsPremiumDue() {
        return isPremiumDue;
    }

    @JsonProperty("isPremiumDue")
    public void setIsPremiumDue(String isPremiumDue) {
        this.isPremiumDue = isPremiumDue;
    }

    @JsonProperty("dueAmountCTP")
    public String getDueAmountCtp() {
        return dueAmountCtp;
    }

    @JsonProperty("dueAmountCTP")
    public void setDueAmountCtp(String dueAmountCtp) {
        this.dueAmountCtp = dueAmountCtp;
    }

    @JsonProperty("isFYSame")
    public String getIsFySame() {
        return isFySame;
    }

    @JsonProperty("isFYSame")
    public void setIsFySame(String isFySame) {
        this.isFySame = isFySame;
    }

    @JsonProperty("isFYPConsider")
    public String getIsFypConsider() {
        return isFypConsider;
    }

    @JsonProperty("isFYPConsider")
    public void setIsFypConsider(String isFypConsider) {
        this.isFypConsider = isFypConsider;
    }

    @JsonProperty("insuredIncome")
    public String getInsuredIncome() {
        return insuredIncome;
    }

    @JsonProperty("insuredIncome")
    public void setInsuredIncome(String insuredIncome) {
        this.insuredIncome = insuredIncome;
    }

    @JsonProperty("insuredOccupation")
    public String getInsuredOccupation() {
        return insuredOccupation;
    }

    @JsonProperty("insuredOccupation")
    public void setInsuredOccupation(String insuredOccupation) {
        this.insuredOccupation = insuredOccupation;
    }

    @JsonProperty("insuredEducation")
    public String getInsuredEducation() {
        return insuredEducation;
    }

    @JsonProperty("insuredEducation")
    public void setInsuredEducation(String insuredEducation) {
        this.insuredEducation = insuredEducation;
    }

    @JsonProperty("insuredSmokerClass")
    public String getInsuredSmokerClass() {
        return insuredSmokerClass;
    }

    @JsonProperty("insuredSmokerClass")
    public void setInsuredSmokerClass(String insuredSmokerClass) {
        this.insuredSmokerClass = insuredSmokerClass;
    }

    @JsonProperty("craPinCode")
    public String getCraPinCode() {
        return craPinCode;
    }

    @JsonProperty("craPinCode")
    public void setCraPinCode(String craPinCode) {
        this.craPinCode = craPinCode;
    }

    @JsonProperty("revivalPeriodEndDate")
    public String getRevivalPeriodEndDate() {
        return revivalPeriodEndDate;
    }

    @JsonProperty("revivalPeriodEndDate")
    public void setRevivalPeriodEndDate(String revivalPeriodEndDate) {
        this.revivalPeriodEndDate = revivalPeriodEndDate;
    }

    @JsonProperty("policyMedCat")
    public String getPolicyMedCat() {
        return policyMedCat;
    }

    @JsonProperty("policyMedCat")
    public void setPolicyMedCat(String policyMedCat) {
        this.policyMedCat = policyMedCat;
    }

    @JsonProperty("neftQctag")
    public String getNeftQcTag() {
        return neftQcTag;
    }

    @JsonProperty("neftQcTag")
    public void setNeftQcTag(String neftQcTag) {
        this.neftQcTag = neftQcTag;
    }

	@Override
	public String toString() {
		 if (Utility.isCalledFromLogs(Thread.currentThread())) {
	            return Utility.toString(this);
	        }
		return "PolicyDetails [policyNo=" + policyNo + ", planCode=" + planCode + ", baseCoverStatusCode="
				+ baseCoverStatusCode + ", sumAssured=" + sumAssured + ", annualPremium=" + annualPremium
				+ ", premiumPayingTerm=" + premiumPayingTerm + ", issueDate=" + issueDate + ", premiumPaidToDate="
				+ premiumPaidToDate + ", lastMedicalDate=" + lastMedicalDate + ", lastMedicalCategoryRecd="
				+ lastMedicalCategoryRecd + ", atrMarked=" + atrMarked + ", transferToExternalSystemCode="
				+ transferToExternalSystemCode + ", claimPaidRequestedOnAnyPolicyCode="
				+ claimPaidRequestedOnAnyPolicyCode + ", claimPaidRequestedOnAnyRiderCode="
				+ claimPaidRequestedOnAnyRiderCode + ", proposerTotalAnnualPremium=" + proposerTotalAnnualPremium
				+ ", monthlyIncome=" + monthlyIncome + ", uwDecision=" + uwDecision + ", modelPremium=" + modelPremium
				+ ", counterOfferStatus=" + counterOfferStatus + ", channelCode=" + channelCode
				+ ", emrMultExtraFlatExtraCases=" + emrMultExtraFlatExtraCases + ", replacementSale=" + replacementSale
				+ ", isProposer=" + isProposer + ", isInsured=" + isInsured + ", initiativeType=" + initiativeType
				+ ", baseCoverStatusCodeDesc=" + baseCoverStatusCodeDesc + ", planType=" + planType + ", isPayor="
				+ isPayor + ", batchDate=" + batchDate + ", category=" + category + ", modeOfPayment=" + modeOfPayment
				+ ", dueDateCtp=" + dueDateCtp + ", isPremiumDue=" + isPremiumDue + ", dueAmountCtp=" + dueAmountCtp
				+ ", isFySame=" + isFySame + ", isFypConsider=" + isFypConsider + ", insuredIncome=" + insuredIncome
				+ ", insuredOccupation=" + insuredOccupation + ", insuredEducation=" + insuredEducation
				+ ", insuredSmokerClass=" + insuredSmokerClass + ", craPinCode=" + craPinCode
				+ ", revivalPeriodEndDate=" + revivalPeriodEndDate + ", riderDetails=" + riderDetails + ", gdb=" + gdb
				+ ", deathBenefitOption=" + deathBenefitOption + ", lifeEvent=" + lifeEvent + ", policyTerm="
				+ policyTerm + ", atrComments=" + atrComments + ", reasonForCoDecCanPst=" + reasonForCoDecCanPst
				+ ", pep=" + pep + ", fatfCountry=" + fatfCountry + ", countryName=" + countryName 
                +  ", policyMedCat=" + policyMedCat + ", neftQcTag=" + neftQcTag +"]";
	}
}
