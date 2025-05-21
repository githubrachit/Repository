package com.mli.mpro.proposal.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"productId", "productName", "sumAssuredOption", "sumAssured", "sumAssuredAvailable", "smokinghabit", "maturityAge", "policyTerm",
        "premimumType", "premiumPaymentTerm", "dividendOption", "dividendAdjustment", "modeOfPayment", "effectiveDateOfCoverage", "desiredAnnualIncome",
        "annualIncome", "planCode", "riderDetails", "fundSelection", "secondAnnuitantDob", "secondAnnuitantTitle", "secondAnnuitantName", "secondAnnuitantSex",
        "annuityOption","deathBenefitForAnnuity", "customerDiscount", "incomePeriod", "premiumBackOption", "lifeStageEventBenefit", "employeeDiscount", "agentDiscount", "webDiscount",
        "customerDiscount", "isDiabetic", "smokingHabits", "variant", "coverMultiple", "incomePayoutFrequency"})
public class ProductInfo {

    @JsonProperty("productId")
    private String productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("sumAssuredOption")
    private String sumAssuredOption;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("sumAssured")
    private String sumAssured;
    @JsonProperty("sumAssuredAvailable")
    private String sumAssuredAvailable;
    @JsonProperty("isSmoker")
    private boolean isSmoker;
    @JsonProperty("maturityAge")
    private String maturityAge;
    @JsonProperty("policyTerm")
    private String policyTerm;
    @JsonProperty("premiumType")
    private String premiumType;
    @JsonProperty("premiumPaymentTerm")
    private String premiumPaymentTerm;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("premiumCommitment")
    private String premiumCommitment;
    @JsonProperty("dividendOption")
    private String dividendOption;
    @JsonProperty("dividendAdjustment")
    private String dividendAdjustment;
    @JsonProperty("modeOfPayment")
    private String modeOfPayment;
    @JsonProperty("effectiveDateOfCoverage")
    private String effectiveDateOfCoverage;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualIncome")
    private long annualIncome;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("desiredAnnualIncome")
    private long desiredAnnualIncome;
    @Sensitive(MaskType.DOB)
    @JsonProperty("childDob")
    private Date childDob;
    @JsonProperty("gstEffectiveDate")
    private String gstEffectiveDate;
    @JsonProperty("vestingAge")
    private String vestingAge;
    @JsonProperty("isSaveMoreTomorrow")
    private boolean isSaveTommorow;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("initialPremium")
    private String initialPremium;

    @JsonProperty("riskClass")
    private String riskClass;

    @JsonProperty("riskProfile")
    private String riskProfile;
    // field added for plan code generation
    @JsonProperty("planCode")
    private String planCode;

    @JsonProperty("riderDetails")
    private List<RiderDetails> riderDetails = null;

    @JsonProperty("fundSelection")
    private FundSelection fundSelection;

    @JsonProperty("productIllustrationResponse")
    private ProductIllustrationResponse productIllustrationResponse;

    @JsonProperty("existingProductType")
    private String existingProductType;

    @JsonProperty("planCodeTPP")
    private String planCodeTPP;
    @JsonProperty("planCodePOSV")
    private String planCodePOSV;
    @JsonProperty("planCodeMFSA")
    private String planCodeMFSA;
    @Sensitive(MaskType.DOB)
    @JsonProperty("secondAnnuitantDob")
    private String secondAnnuitantDob;
    @JsonProperty("secondAnnuitantTitle")
    private String secondAnnuitantTitle;
    @Sensitive(MaskType.NAME)
    @JsonProperty("secondAnnuitantName")
    private String secondAnnuitantName;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("secondAnnuitantSex")
    private String secondAnnuitantSex;
    @JsonProperty("annuityOption")
    private String annuityOption;
    @JsonProperty("deathBenefitForAnnuity")
    private String deathBenefitForAnnuity;
    @JsonProperty("deathBenefit")
    private String deathBenefit;
    @JsonProperty("incomePeriod")
    private String incomePeriod;
    @JsonProperty("premiumBackOption")
    private String premiumBackOption;
    @JsonProperty("premiumBreakOption")
    private String premiumBreakOption;
    @JsonProperty("firstPremiumBreakOption")
    private String firstPremiumBreakOption;
    @JsonProperty("secondPremiumBreakOption")
    private String secondPremiumBreakOption;
    @JsonProperty("lifeStageEventBenefit")
    private String lifeStageEventBenefit;
    @JsonProperty("customerDiscount")
    private String customerDiscount;
    @JsonProperty("isDiabetic")
    private String isDiabetic;
    @JsonProperty("smokingHabits")
    private String smokingHabits;
    // Fields added for flex invest plus story
    @JsonProperty("variant")
    private String variant;
    @JsonProperty("coverMultiple")
    private String coverMultiple;
    //FUL2-6977 Smart Wealth Plan in mPRO - LE and UI Changes
    @JsonProperty("incomePayoutFrequency")
    private String incomePayoutFrequency;
    @JsonProperty("incomePayoutOption")
    private String incomePayoutOption;
    // NR-706 Fields added for Smart Wealth Plan (SWP)
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("benefitMonthlyIncome")
    private String benefitMonthlyIncome;
    @JsonProperty("benefitReturnFrequency")
    private String benefitReturnFrequency;
    @JsonProperty("terminalBenefit")
    private String terminalBenefit;

    //FUL2-16865 Fields added for GLIP Product
    @JsonProperty("annuityType")
    private String annuityType;
    @JsonProperty("defermentPeriod")
    private String defermentPeriod;
    @JsonProperty("secondAnnuitantRelationship")
    private String secondAnnuitantRelationship;
    @JsonProperty("secondAnnuitantRelationshipWhenOthers")
    private String secondAnnuitantRelationshipWhenOthers;
    @JsonProperty("isSecondAnnuitantPANExist")
    private boolean isSecondAnnuitantPANExist;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("secondAnnuitantPanNumber")
    private String secondAnnuitantPanNumber;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annuityAmount")
    private String annuityAmount;
    @JsonProperty("isSecondAnnuitantPanValidated")
    private boolean isSecondAnnuitantPanValidated;
    @JsonProperty("isSecondAnnuitantDobValidated")
    private boolean isSecondAnnuitantDobValidated;
    @JsonProperty("secondAnnuitantPanAadhaarStatus")
    private String secondAnnuitantPanAadhaarStatus;
    @JsonProperty("limitedTerm")
    private String limitedTerm;
    private String smartWithdrawalPayoutPercentage;
    private String smartWithdrawalPlan;
    private String smartWithdrawalPayoutMode;
    private int smartWithdrawalPayoutStartYear;

    private String pcb;
    
    private String cashBonus;
    
    @JsonProperty("familyIncomeOption")
    private String familyIncomeOption;
    
    //FUL2-61169 Smart Secure Easy Solution
    @JsonProperty("isSSESProduct")
    private String isSSESProduct;
    @JsonProperty("SSESSolveOption")
    private String SSESSolveOption;
    
    //FUL2-75008 Smart Wealth Advantage Guarantee Plan - Product details section changes
    @JsonProperty("incomeStartYear")
    private String incomeStartYear;
    @JsonProperty("premiumOffset")
    private String premiumOffset;
    @JsonProperty("desiredDateOfIncomePayout")
    private String desiredDateOfIncomePayout;
    @JsonProperty("desiredDate")
    private String desiredDate;
    @JsonProperty("posProductSelection")
    private String posProductSelection;
    @JsonProperty("solveOption")
    private String solveOption;

    @JsonProperty("isPosp")
    private String isPosp;

    //FUL2-25820_FUL2-104961_ssp_joint_life
    @JsonProperty("secondAnnuitantRiskClass")
    private String secondAnnuitantRiskClass;

    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("secondAnnuitantSumAssured")
    private String secondAnnuitantSumAssured;

    @JsonProperty("isJointLife")
    private String isJointLife;

    @JsonProperty("planCodeSecondary")
    private String planCodeSecondary;

    //FUL2-116960
    @JsonProperty("SSESNatureOfDuties")
    private String SSESNatureOfDuties;
    @JsonProperty("sspNatureOfDuties")
    private String sspNatureOfDuties;

    @JsonProperty("isSspJLRiderSelected")
    private String isSspJLRiderSelected;
    @JsonProperty("jLPremiumPaymentTerm")
    private String jLPremiumPaymentTerm;

    //FUL2-159684 For SWAG Pension Plan
    @JsonProperty("earlyROPPercentage")
    private String earlyROPPercentage;
    @JsonProperty("guaranteeAnnuityPeriod")
    private String guaranteeAnnuityPeriod;
    @JsonProperty("milestoneAge")
    private String milestoneAge;
    @JsonProperty("increasingAnnuityPercentage")
    private String increasingAnnuityPercentage;
    @JsonProperty("increasingAnnuityFrequency")
    private String increasingAnnuityFrequency;
    @JsonProperty("returnOfPremium")
    private String returnOfPremium;
    @JsonProperty("returnOfPremiumPercentage")
    private String returnOfPremiumPercentage;
    @JsonProperty("isEarlyWealthPlus")
    private String isEarlyWealthPlus;
    @JsonProperty("proportionOfAnnuityLastSurvivor")
    private String proportionOfAnnuityLastSurvivor;
    @JsonProperty("returnOfPremium1stDeath")
    private String returnOfPremium1stDeath;
    @JsonProperty("returnOfPremium2ndDeath")
    private String returnOfPremium2ndDeath;
    @JsonProperty("defermentPeriodMonth")
    private String defermentPeriodMonth;
	@JsonProperty("deferredPeriod")
    private String deferredPeriod;
    @JsonProperty("deathBenefitMultiple")
    private String deathBenefitMultiple;
    @JsonProperty("customerReminderConsent")
    private String customerReminderConsent;
    
	// FUL2-194198 For SWAG ELITE Plan
	@JsonProperty("survivalBenefitPeriod")
	private String survivalBenefitPeriod;
	@JsonProperty("pcbRiderSelection")
    private String pcbRiderSelection;
    @JsonProperty("applicableCisRiders")
    private List<String> applicableCisRiders;
    @JsonProperty("deferralAccrualOption")
    private String deferralAccrualOption;
    @JsonProperty("isWellnessProgram")
    private String isWellnessProgram;
    @JsonProperty("incomeCover")
    private String incomeCover;
    @JsonProperty("sumAssuredBooster")
    private String sumAssuredBooster;

    public String getDeferralAccrualOption() {
        return deferralAccrualOption;
    }

    public void setDeferralAccrualOption(String deferralAccrualOption) {
        this.deferralAccrualOption = deferralAccrualOption;
    }

    public String getEarlyROPPercentage() {
        return earlyROPPercentage;
    }

    public void setEarlyROPPercentage(String earlyROPPercentage) {
        this.earlyROPPercentage = earlyROPPercentage;
    }

    public String getGuaranteeAnnuityPeriod() {
        return guaranteeAnnuityPeriod;
    }

    public void setGuaranteeAnnuityPeriod(String guaranteeAnnuityPeriod) {
        this.guaranteeAnnuityPeriod = guaranteeAnnuityPeriod;
    }

    public String getMilestoneAge() {
        return milestoneAge;
    }

    public void setMilestoneAge(String milestoneAge) {
        this.milestoneAge = milestoneAge;
    }

    public String getIncreasingAnnuityPercentage() {
        return increasingAnnuityPercentage;
    }

    public void setIncreasingAnnuityPercentage(String increasingAnnuityPercentage) {
        this.increasingAnnuityPercentage = increasingAnnuityPercentage;
    }

    public String getIncreasingAnnuityFrequency() {
        return increasingAnnuityFrequency;
    }

    public void setIncreasingAnnuityFrequency(String increasingAnnuityFrequency) {
        this.increasingAnnuityFrequency = increasingAnnuityFrequency;
    }

    public String getReturnOfPremium() {
        return returnOfPremium;
    }

    public void setReturnOfPremium(String returnOfPremium) {
        this.returnOfPremium = returnOfPremium;
    }

    public String getReturnOfPremiumPercentage() {
        return returnOfPremiumPercentage;
    }

    public void setReturnOfPremiumPercentage(String returnOfPremiumPercentage) {
        this.returnOfPremiumPercentage = returnOfPremiumPercentage;
    }

    public String getIsEarlyWealthPlus() {
        return isEarlyWealthPlus;
    }

    public void setIsEarlyWealthPlus(String isEarlyWealthPlus) {
        this.isEarlyWealthPlus = isEarlyWealthPlus;
    }

    public String getProportionOfAnnuityLastSurvivor() {
        return proportionOfAnnuityLastSurvivor;
    }

    public void setProportionOfAnnuityLastSurvivor(String proportionOfAnnuityLastSurvivor) {
        this.proportionOfAnnuityLastSurvivor = proportionOfAnnuityLastSurvivor;
    }

    public String getReturnOfPremium1stDeath() {
        return returnOfPremium1stDeath;
    }

    public void setReturnOfPremium1stDeath(String returnOfPremium1stDeath) {
        this.returnOfPremium1stDeath = returnOfPremium1stDeath;
    }

    public String getReturnOfPremium2ndDeath() {
        return returnOfPremium2ndDeath;
    }

    public void setReturnOfPremium2ndDeath(String returnOfPremium2ndDeath) {
        this.returnOfPremium2ndDeath = returnOfPremium2ndDeath;
    }

    public String getDefermentPeriodMonth() {
        return defermentPeriodMonth;
    }

    public void setDefermentPeriodMonth(String defermentPeriodMonth) {
        this.defermentPeriodMonth = defermentPeriodMonth;
    }

    public String getSSESNatureOfDuties() {
        return SSESNatureOfDuties;
    }

    public void setSSESNatureOfDuties(String SSESNatureOfDuties) {
        this.SSESNatureOfDuties = SSESNatureOfDuties;
    }

    public String getSspNatureOfDuties() {
        return sspNatureOfDuties;
    }

    public void setSspNatureOfDuties(String sspNatureOfDuties) {
        this.sspNatureOfDuties = sspNatureOfDuties;
    }

    /**
     * No args constructor for use in serialization
     */

    public ProductInfo() {
    }

    public String getProductId() {
        return productId;
    }

    public String getPremiumBreakOption() {
        return premiumBreakOption;
    }

    public void setPremiumBreakOption(String premiumBreakOption) {
        this.premiumBreakOption = premiumBreakOption;
    }

    public String getFirstPremiumBreakOption() {
        return firstPremiumBreakOption;
    }

    public void setFirstPremiumBreakOption(String firstPremiumBreakOption) {
        this.firstPremiumBreakOption = firstPremiumBreakOption;
    }

    public String getSecondPremiumBreakOption() {
        return secondPremiumBreakOption;
    }

    public void setSecondPremiumBreakOption(String secondPremiumBreakOption) {
        this.secondPremiumBreakOption = secondPremiumBreakOption;
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

    public String getSumAssuredOption() {
        return sumAssuredOption;
    }

    public void setSumAssuredOption(String sumAssuredOption) {
        this.sumAssuredOption = sumAssuredOption;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getSumAssuredAvailable() {
        return sumAssuredAvailable;
    }

    public void setSumAssuredAvailable(String sumAssuredAvailable) {
        this.sumAssuredAvailable = sumAssuredAvailable;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public void setSmoker(boolean isSmoker) {
        this.isSmoker = isSmoker;
    }

    public String getMaturityAge() {
        return maturityAge;
    }

    public void setMaturityAge(String maturityAge) {
        this.maturityAge = maturityAge;
    }

    public String getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(String policyTerm) {
        this.policyTerm = policyTerm;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }

    public String getPremiumCommitment() {
        return premiumCommitment;
    }

    public void setPremiumCommitment(String premiumCommitment) {
        this.premiumCommitment = premiumCommitment;
    }

    public String getDividendOption() {
        return dividendOption;
    }

    public void setDividendOption(String dividendOption) {
        this.dividendOption = dividendOption;
    }

    public String getDividendAdjustment() {
        return dividendAdjustment;
    }

    public void setDividendAdjustment(String dividendAdjustment) {
        this.dividendAdjustment = dividendAdjustment;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getEffectiveDateOfCoverage() {
        return effectiveDateOfCoverage;
    }

    public void setEffectiveDateOfCoverage(String effectiveDateOfCoverage) {
        this.effectiveDateOfCoverage = effectiveDateOfCoverage;
    }

    public long getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(long annualIncome) {
        this.annualIncome = annualIncome;
    }

    public long getDesiredAnnualIncome() {
        return desiredAnnualIncome;
    }

    public void setDesiredAnnualIncome(long desiredAnnualIncome) {
        this.desiredAnnualIncome = desiredAnnualIncome;
    }

    public List<RiderDetails> getRiderDetails() {
        return riderDetails;
    }

    public void setRiderDetails(List<RiderDetails> riderDetails) {
        this.riderDetails = riderDetails;
    }

    public FundSelection getFundSelection() {
        return fundSelection;
    }

    public void setFundSelection(FundSelection fundSelection) {
        this.fundSelection = fundSelection;
    }

    public ProductIllustrationResponse getProductIllustrationResponse() {
        return productIllustrationResponse;
    }

    public void setProductIllustrationResponse(ProductIllustrationResponse productIllustrationResponse) {
        this.productIllustrationResponse = productIllustrationResponse;
    }

    public String getGstEffectiveDate() {
        return gstEffectiveDate;
    }

    public void setGstEffectiveDate(String gstEffectiveDate) {
        this.gstEffectiveDate = gstEffectiveDate;
    }

    public String getVestingAge() {
        return vestingAge;
    }

    public void setVestingAge(String vestingAge) {
        this.vestingAge = vestingAge;
    }

    public String getRiskClass() {
        return riskClass;
    }

    public void setRiskClass(String riskClass) {
        this.riskClass = riskClass;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    public Date getChildDob() {
        return childDob;
    }

    public void setChildDob(Date childDob) {
        this.childDob = childDob;
    }

    public String getPremiumPaymentTerm() {
        return premiumPaymentTerm;
    }

    public void setPremiumPaymentTerm(String premiumPaymentTerm) {
        this.premiumPaymentTerm = premiumPaymentTerm;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getExistingProductType() {
        return existingProductType;
    }

    public void setExistingProductType(String existingProductType) {
        this.existingProductType = existingProductType;
    }

    public String getPlanCodeTPP() {
        return planCodeTPP;
    }

    public void setPlanCodeTPP(String planCodeTPP) {
        this.planCodeTPP = planCodeTPP;
    }

    public String getPlanCodePOSV() {
        return planCodePOSV;
    }

    public void setPlanCodePOSV(String planCodePOSV) {
        this.planCodePOSV = planCodePOSV;
    }

    public String getPlanCodeMFSA() {
        return planCodeMFSA;
    }

    public void setPlanCodeMFSA(String planCodeMFSA) {
        this.planCodeMFSA = planCodeMFSA;
    }

    public boolean isSaveTommorow() {
        return isSaveTommorow;
    }

    public void setSaveTommorow(boolean isSaveTommorow) {
        this.isSaveTommorow = isSaveTommorow;
    }

    public String getSecondAnnuitantDob() {
        return secondAnnuitantDob;
    }

    public void setSecondAnnuitantDob(String secondAnnuitantDob) {
        this.secondAnnuitantDob = secondAnnuitantDob;
    }

    public String getSecondAnnuitantTitle() {
        return secondAnnuitantTitle;
    }

    public void setSecondAnnuitantTitle(String secondAnnuitantTitle) {
        this.secondAnnuitantTitle = secondAnnuitantTitle;
    }

    public String getSecondAnnuitantName() {
        return secondAnnuitantName;
    }

    public void setSecondAnnuitantName(String secondAnnuitantName) {
        this.secondAnnuitantName = secondAnnuitantName;
    }

    public String getSecondAnnuitantSex() {
        return secondAnnuitantSex;
    }

    public void setSecondAnnuitantSex(String secondAnnuitantSex) {
        this.secondAnnuitantSex = secondAnnuitantSex;
    }

    public String getAnnuityOption() {
        return annuityOption;
    }

    public void setAnnuityOption(String annuityOption) {
        this.annuityOption = annuityOption;
    }

    public String getInitialPremium() {
        return initialPremium;
    }

    public void setInitialPremium(String initialPremium) {
        this.initialPremium = initialPremium;
    }

    public String getDeathBenefit() {
        return deathBenefit;
    }

    public void setDeathBenefit(String deathBenefit) {
        this.deathBenefit = deathBenefit;
    }

    public String getIncomePeriod() {
        return incomePeriod;
    }

    public void setIncomePeriod(String incomePeriod) {
        this.incomePeriod = incomePeriod;
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

    public String getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(String customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

    public String getIsDiabetic() {
        return isDiabetic;
    }

    public void setIsDiabetic(String isDiabetic) {
        this.isDiabetic = isDiabetic;
    }

    public String getSmokingHabits() {
        return smokingHabits;
    }

    public void setSmokingHabits(String smokingHabits) {
        this.smokingHabits = smokingHabits;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getCoverMultiple() {
        return coverMultiple;
    }

    public void setCoverMultiple(String coverMultiple) {
        this.coverMultiple = coverMultiple;
    }

    public String getIncomePayoutFrequency() {
        return incomePayoutFrequency;
    }

    public void setIncomePayoutFrequency(String incomePayoutFrequency) {
        this.incomePayoutFrequency = incomePayoutFrequency;
    }

    public String getIncomePayoutOption() {
        return incomePayoutOption;
    }

    public void setIncomePayoutOption(String incomePayoutOption) {
        this.incomePayoutOption = incomePayoutOption;
    }

    public String getBenefitMonthlyIncome() { return benefitMonthlyIncome; }

    public void setBenefitMonthlyIncome(String benefitMonthlyIncome) { this.benefitMonthlyIncome = benefitMonthlyIncome; }

    public String getBenefitReturnFrequency() { return benefitReturnFrequency; }

    public void setBenefitReturnFrequency(String benefitReturnFrequency) { this.benefitReturnFrequency = benefitReturnFrequency; }

    public String getTerminalBenefit() { return terminalBenefit; }

    public void setTerminalBenefit(String terminalBenefit) { this.terminalBenefit = terminalBenefit; }

    public String getDeathBenefitForAnnuity() {
        return deathBenefitForAnnuity;
    }

    public void setDeathBenefitForAnnuity(String deathBenefitForAnnuity) {
        this.deathBenefitForAnnuity = deathBenefitForAnnuity;
    }

    public String getAnnuityType() {
        return annuityType;
    }

    public void setAnnuityType(String annuityType) {
        this.annuityType = annuityType;
    }

    public String getDefermentPeriod() {
        return defermentPeriod;
    }

    public void setDefermentPeriod(String defermentPeriod) {
        this.defermentPeriod = defermentPeriod;
    }

    public String getSecondAnnuitantRelationship() {
        return secondAnnuitantRelationship;
    }

    public void setSecondAnnuitantRelationship(String secondAnnuitantRelationship) {
        this.secondAnnuitantRelationship = secondAnnuitantRelationship;
    }

    public String getSecondAnnuitantRelationshipWhenOthers() {
        return secondAnnuitantRelationshipWhenOthers;
    }

    public void setSecondAnnuitantRelationshipWhenOthers(String secondAnnuitantRelationshipWhenOthers) {
        this.secondAnnuitantRelationshipWhenOthers = secondAnnuitantRelationshipWhenOthers;
    }

    public boolean getIsSecondAnnuitantPANExist() {
        return isSecondAnnuitantPANExist;
    }

    public void setIsSecondAnnuitantPANExist(boolean isSecondAnnuitantPANExist) {
        this.isSecondAnnuitantPANExist = isSecondAnnuitantPANExist;
    }

    public String getSecondAnnuitantPanNumber() {
        return secondAnnuitantPanNumber;
    }

    public void setSecondAnnuitantPanNumber(String secondAnnuitantPanNumber) {
        this.secondAnnuitantPanNumber = secondAnnuitantPanNumber;
    }

    public String getAnnuityAmount() {
        return annuityAmount;
    }

    public void setAnnuityAmount(String annuityAmount) {
        this.annuityAmount = annuityAmount;
    }

    public boolean isSecondAnnuitantPANExist() {
        return isSecondAnnuitantPANExist;
    }

    public void setSecondAnnuitantPANExist(boolean secondAnnuitantPANExist) {
        isSecondAnnuitantPANExist = secondAnnuitantPANExist;
    }

    public boolean isSecondAnnuitantPanValidated() {
        return isSecondAnnuitantPanValidated;
    }

    public void setSecondAnnuitantPanValidated(boolean secondAnnuitantPanValidated) {
        isSecondAnnuitantPanValidated = secondAnnuitantPanValidated;
    }

    public boolean isSecondAnnuitantDobValidated() {
        return isSecondAnnuitantDobValidated;
    }

    public void setSecondAnnuitantDobValidated(boolean secondAnnuitantDobValidated) {
        isSecondAnnuitantDobValidated = secondAnnuitantDobValidated;
    }

    public String getSecondAnnuitantPanAadhaarStatus() {
        return secondAnnuitantPanAadhaarStatus;
    }

    public void setSecondAnnuitantPanAadhaarStatus(String secondAnnuitantPanAadhaarStatus) {
        this.secondAnnuitantPanAadhaarStatus = secondAnnuitantPanAadhaarStatus;
    }

    public String getPcb() {
		return pcb;
	}

	public void setPcb(String pcb) {
		this.pcb = pcb;
	}

	public String getCashBonus() {
		return cashBonus;
	}

	public void setCashBonus(String cashBonus) {
		this.cashBonus = cashBonus;
	}

    public String getSmartWithdrawalPayoutPercentage() {
        return smartWithdrawalPayoutPercentage;
    }

    public void setSmartWithdrawalPayoutPercentage(String smartWithdrawalPayoutPercentage) {
        this.smartWithdrawalPayoutPercentage = smartWithdrawalPayoutPercentage;
    }

    public String getSmartWithdrawalPlan() {
        return smartWithdrawalPlan;
    }

    public void setSmartWithdrawalPlan(String smartWithdrawalPlan) {
        this.smartWithdrawalPlan = smartWithdrawalPlan;
    }

    public String getSmartWithdrawalPayoutMode() {
        return smartWithdrawalPayoutMode;
    }

    public void setSmartWithdrawalPayoutMode(String smartWithdrawalPayoutMode) {
        this.smartWithdrawalPayoutMode = smartWithdrawalPayoutMode;
    }

    public int getSmartWithdrawalPayoutStartYear() {
        return smartWithdrawalPayoutStartYear;
    }

    public void setSmartWithdrawalPayoutStartYear(int smartWithdrawalPayoutStartYear) {
        this.smartWithdrawalPayoutStartYear = smartWithdrawalPayoutStartYear;
    }

    public String getFamilyIncomeOption() {
		return familyIncomeOption;
	}

	public void setFamilyIncomeOption(String familyIncomeOption) {
		this.familyIncomeOption = familyIncomeOption;
	}

    public String getIsSSESProduct() {
		return isSSESProduct;
	}

	public void setIsSSESProduct(String isSSESProduct) {
		this.isSSESProduct = isSSESProduct;
	}

	public String getSSESSolveOption() {
		return SSESSolveOption;
	}

	public void setSSESSolveOption(String sSESSolveOption) {
		SSESSolveOption = sSESSolveOption;
	}

	public String getPosProductSelection() {
    	return this.posProductSelection;
    }

    public void setPosProductSelection(String posProductSelection) {
    	this.posProductSelection = posProductSelection;
    }

    public String getSolveOption() {
		return solveOption;
	}

	public void setSolveOption(String solveOption) {
		this.solveOption = solveOption;
	}

	public String getIncomeStartYear() {
    	return this.incomeStartYear;
    }

    public void setIncomeStartYear(String incomeStartYear) {
    	this.incomeStartYear = incomeStartYear;
    }

    public String getPremiumOffset() {
    	return this.premiumOffset;
    }

    public void setPremiumOffset(String premiumOffset) {
    	this.premiumOffset = premiumOffset;
    }

    public String getDesiredDateOfIncomePayout() {
    	return this.desiredDateOfIncomePayout;
    }

    public void setDesiredDateOfIncomePayout(String desiredDateOfIncomePayout) {
    	this.desiredDateOfIncomePayout = desiredDateOfIncomePayout;
    }

    public String getDesiredDate() {
    	return this.desiredDate;
    }

    public void setDesiredDate(String desiredDate) {
    	this.desiredDate = desiredDate;
    }

    public String getIsPosp() {
        return isPosp;
    }

    public void setIsPosp(String isPosp) {
        this.isPosp = isPosp;
    }

	public String getSecondAnnuitantRiskClass() {
		return secondAnnuitantRiskClass;
	}

	public void setSecondAnnuitantRiskClass(String secondAnnuitantRiskClass) {
		this.secondAnnuitantRiskClass = secondAnnuitantRiskClass;
	}

	public String getSecondAnnuitantSumAssured() {
		return secondAnnuitantSumAssured;
	}

	public void setSecondAnnuitantSumAssured(String secondAnnuitantSumAssured) {
		this.secondAnnuitantSumAssured = secondAnnuitantSumAssured;
	}

	public String getIsJointLife() {
		return isJointLife;
	}

	public void setIsJointLife(String isJointLife) {
		this.isJointLife = isJointLife;
	}

	public String getPlanCodeSecondary() {
		return planCodeSecondary;
	}

	public void setPlanCodeSecondary(String planCodeSecondary) {
		this.planCodeSecondary = planCodeSecondary;
	}

    public String getIsSspJLRiderSelected() {
        return isSspJLRiderSelected;
    }

    public void setIsSspJLRiderSelected(String isSspJLRiderSelected) {
        this.isSspJLRiderSelected = isSspJLRiderSelected;
    }

    public String getjLPremiumPaymentTerm() {
        return jLPremiumPaymentTerm;
    }

    public void setjLPremiumPaymentTerm(String jLPremiumPaymentTerm) {
        this.jLPremiumPaymentTerm = jLPremiumPaymentTerm;
    }

    public String getLimitedTerm() {
        return limitedTerm;
    }

    public void setLimitedTerm(String limitedTerm) {
        this.limitedTerm = limitedTerm;
    }
    
    public String getDeferredPeriod() {
		return deferredPeriod;
	}

	public void setDeferredPeriod(String deferredPeriod) {
		this.deferredPeriod = deferredPeriod;
	}

    public String getDeathBenefitMultiple() {
        return deathBenefitMultiple;
    }

    public void setDeathBenefitMultiple(String deathBenefitMultiple) {
        this.deathBenefitMultiple = deathBenefitMultiple;
    }


    public String getCustomerReminderConsent() {
        return customerReminderConsent;
    }

    public void setCustomerReminderConsent(String customerReminderConsent) {
        this.customerReminderConsent = customerReminderConsent;
    }
    
	public String getSurvivalBenefitPeriod() {
		return survivalBenefitPeriod;
	}

	public void setSurvivalBenefitPeriod(String survivalBenefitPeriod) {
		this.survivalBenefitPeriod = survivalBenefitPeriod;
	}

    public String getPcbRiderSelection() {
        return pcbRiderSelection;
    }

    public void setPcbRiderSelection(String pcbRiderSelection) {
        this.pcbRiderSelection = pcbRiderSelection;
    }

    public List<String> getApplicableCisRiders() {
        return applicableCisRiders;
    }

    public void setApplicableCisRiders(List<String> applicableCisRiders) {
        this.applicableCisRiders = applicableCisRiders;
    }

    public String getIsWellnessProgram() {
        return isWellnessProgram;
    }

    public void setIsWellnessProgram(String isWellnessProgram) {
        this.isWellnessProgram = isWellnessProgram;
    }

    public String getIncomeCover() {
        return incomeCover;
    }

    public void setIncomeCover(String incomeCover) {
        this.incomeCover = incomeCover;
    }

    public String getSumAssuredBooster() {
        return sumAssuredBooster;
    }

    public void setSumAssuredBooster(String sumAssuredBooster) {
        this.sumAssuredBooster = sumAssuredBooster;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", sumAssuredOption='" + sumAssuredOption + '\'' +
                ", sumAssured='" + sumAssured + '\'' +
                ", sumAssuredAvailable='" + sumAssuredAvailable + '\'' +
                ", isSmoker=" + isSmoker +
                ", maturityAge='" + maturityAge + '\'' +
                ", policyTerm='" + policyTerm + '\'' +
                ", premiumType='" + premiumType + '\'' +
                ", premiumPaymentTerm='" + premiumPaymentTerm + '\'' +
                ", premiumCommitment='" + premiumCommitment + '\'' +
                ", dividendOption='" + dividendOption + '\'' +
                ", dividendAdjustment='" + dividendAdjustment + '\'' +
                ", modeOfPayment='" + modeOfPayment + '\'' +
                ", effectiveDateOfCoverage='" + effectiveDateOfCoverage + '\'' +
                ", annualIncome=" + annualIncome +
                ", desiredAnnualIncome=" + desiredAnnualIncome +
                ", childDob=" + childDob +
                ", gstEffectiveDate='" + gstEffectiveDate + '\'' +
                ", vestingAge='" + vestingAge + '\'' +
                ", isSaveTommorow=" + isSaveTommorow +
                ", initialPremium='" + initialPremium + '\'' +
                ", riskClass='" + riskClass + '\'' +
                ", riskProfile='" + riskProfile + '\'' +
                ", planCode='" + planCode + '\'' +
                ", riderDetails=" + riderDetails +
                ", fundSelection=" + fundSelection +
                ", productIllustrationResponse=" + productIllustrationResponse +
                ", existingProductType='" + existingProductType + '\'' +
                ", planCodeTPP='" + planCodeTPP + '\'' +
                ", planCodePOSV='" + planCodePOSV + '\'' +
                ", planCodeMFSA='" + planCodeMFSA + '\'' +
                ", secondAnnuitantDob='" + secondAnnuitantDob + '\'' +
                ", secondAnnuitantTitle='" + secondAnnuitantTitle + '\'' +
                ", secondAnnuitantName='" + secondAnnuitantName + '\'' +
                ", secondAnnuitantSex='" + secondAnnuitantSex + '\'' +
                ", annuityOption='" + annuityOption + '\'' +
                ", deathBenefitForAnnuity='" + deathBenefitForAnnuity + '\'' +
                ", deathBenefit='" + deathBenefit + '\'' +
                ", incomePeriod='" + incomePeriod + '\'' +
                ", premiumBackOption='" + premiumBackOption + '\'' +
                ", premiumBreakOption='" + premiumBreakOption + '\'' +
                ", firstPremiumBreakOption='" + firstPremiumBreakOption + '\'' +
                ", secondPremiumBreakOption='" + secondPremiumBreakOption + '\'' +
                ", lifeStageEventBenefit='" + lifeStageEventBenefit + '\'' +
                ", customerDiscount='" + customerDiscount + '\'' +
                ", isDiabetic='" + isDiabetic + '\'' +
                ", smokingHabits='" + smokingHabits + '\'' +
                ", variant='" + variant + '\'' +
                ", coverMultiple='" + coverMultiple + '\'' +
                ", incomePayoutFrequency='" + incomePayoutFrequency + '\'' +
                ", incomePayoutOption='" + incomePayoutOption + '\'' +
                ", benefitMonthlyIncome='" + benefitMonthlyIncome + '\'' +
                ", benefitReturnFrequency='" + benefitReturnFrequency + '\'' +
                ", terminalBenefit='" + terminalBenefit + '\'' +
                ", annuityType='" + annuityType + '\'' +
                ", defermentPeriod='" + defermentPeriod + '\'' +
                ", secondAnnuitantRelationship='" + secondAnnuitantRelationship + '\'' +
                ", secondAnnuitantRelationshipWhenOthers='" + secondAnnuitantRelationshipWhenOthers + '\'' +
                ", isSecondAnnuitantPANExist=" + isSecondAnnuitantPANExist +
                ", secondAnnuitantPanNumber='" + secondAnnuitantPanNumber + '\'' +
                ", annuityAmount='" + annuityAmount + '\'' +
                ", isSecondAnnuitantPanValidated=" + isSecondAnnuitantPanValidated +
                ", isSecondAnnuitantDobValidated=" + isSecondAnnuitantDobValidated +
                ", secondAnnuitantPanAadhaarStatus='" + secondAnnuitantPanAadhaarStatus + '\'' +
                ", smartWithdrawalPayoutPercentage='" + smartWithdrawalPayoutPercentage + '\'' +
                ", smartWithdrawalPlan='" + smartWithdrawalPlan + '\'' +
                ", smartWithdrawalPayoutMode='" + smartWithdrawalPayoutMode + '\'' +
                ", smartWithdrawalPayoutStartYear=" + smartWithdrawalPayoutStartYear +
                ", pcb='" + pcb + '\'' +
                ", cashBonus='" + cashBonus + '\'' +
                ", familyIncomeOption='" + familyIncomeOption + '\'' +
                ", isSSESProduct='" + isSSESProduct + '\'' +
                ", SSESSolveOption='" + SSESSolveOption + '\'' +
                ", incomeStartYear='" + incomeStartYear + '\'' +
                ", premiumOffset='" + premiumOffset + '\'' +
                ", desiredDateOfIncomePayout='" + desiredDateOfIncomePayout + '\'' +
                ", desiredDate='" + desiredDate + '\'' +
                ", posProductSelection='" + posProductSelection + '\'' +
                ", solveOption='" + solveOption + '\'' +
                ", isPosp='" + isPosp + '\'' +
                ", isSspJLRiderSelected='" + isSspJLRiderSelected + '\'' +
                ", jLPremiumPaymentTerm='" + jLPremiumPaymentTerm + '\'' +
                ", earlyROPPercentage='" + earlyROPPercentage + '\'' +
                ", guaranteeAnnuityPeriod='" + guaranteeAnnuityPeriod + '\'' +
                ", milestoneAge='" + milestoneAge + '\'' +
                ", increasingAnnuityPercentage='" + increasingAnnuityPercentage + '\'' +
                ", increasingAnnuityFrequency='" + increasingAnnuityFrequency + '\'' +
                ", returnOfPremium='" + returnOfPremium + '\'' +
                ", returnOfPremiumPercentage='" + returnOfPremiumPercentage + '\'' +
                ", isEarlyWealthPlus='" + isEarlyWealthPlus + '\'' +
                ", proportionOfAnnuityLastSurvivor='" + proportionOfAnnuityLastSurvivor + '\'' +
                ", returnOfPremium1stDeath='" + returnOfPremium1stDeath + '\'' +
                ", returnOfPremium2ndDeath='" + returnOfPremium2ndDeath + '\'' +
                ", defermentPeriodMonth='" + defermentPeriodMonth + '\'' +
                ", deferredPeriod='" + deferredPeriod + '\'' +
                ", deathBenefitMultiple='" + deathBenefitMultiple +'\''+
                ", customerReminderConsent='"+customerReminderConsent +'\''+
                ", survivalBenefitPeriod='" + survivalBenefitPeriod + '\'' +
                ",applicableCisRiders='" +applicableCisRiders+ '\'' +
                ",isWellnessProgram='" +isWellnessProgram+ '\'' +
                ",incomeCover='" +incomeCover+ '\'' +
                ",sumAssuredBooster='" +sumAssuredBooster+ '\'' +
                '}';
    }
}
