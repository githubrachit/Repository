package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuoteDetails {

    private String sumAssured;
    private String corpusValue;
    private String totalPremiumExcGST;
    private String policyTerm;
    private String policyPaymentTerm;
    private String terminalBenefit;
    private String benefitMonthlyIncome;
    private String incomePeriod;
    private String benefitReturnFrequency;
    private String defermentPeriod;
    private String currentPlanPremium;
    private String planServiceTax;
    private String totalPremium;
    private String incomeCover;
    private String survivalBenefit;
    private String maturityBenefit;
    private String monthlyIncomeInception;
    private String isStaffOrSellerDiscountApplicable;
    private String guaranteedDeathBenefit ;

    public String getIsStaffOrSellerDiscountApplicable() {
        return isStaffOrSellerDiscountApplicable;
    }

    public void setIsStaffOrSellerDiscountApplicable(String isStaffOrSellerDiscountApplicable) {
        this.isStaffOrSellerDiscountApplicable = isStaffOrSellerDiscountApplicable;
    }

    public String getSurvivalBenefit() {
        return survivalBenefit;
    }

    public void setSurvivalBenefit(String survivalBenefit) {
        this.survivalBenefit = survivalBenefit;
    }

    public String getMaturityBenefit() {
        return maturityBenefit;
    }

    public void setMaturityBenefit(String maturityBenefit) {
        this.maturityBenefit = maturityBenefit;
    }

    public String getMonthlyIncomeInception() {
        return monthlyIncomeInception;
    }

    public void setMonthlyIncomeInception(String monthlyIncomeInception) {
        this.monthlyIncomeInception = monthlyIncomeInception;
    }

    public String getIncomeCover() {
        return incomeCover;
    }

    public void setIncomeCover(String incomeCover) {
        this.incomeCover = incomeCover;
    }

    public String getBenefitMonthlyIncome() {
        return benefitMonthlyIncome;
    }

    public void setBenefitMonthlyIncome(String benefitMonthlyIncome) {
        this.benefitMonthlyIncome = benefitMonthlyIncome;
    }

    public String getIncomePeriod() {
        return incomePeriod;
    }

    public void setIncomePeriod(String incomePeriod) {
        this.incomePeriod = incomePeriod;
    }

    public String getBenefitReturnFrequency() {
        return benefitReturnFrequency;
    }

    public void setBenefitReturnFrequency(String benefitReturnFrequency) {
        this.benefitReturnFrequency = benefitReturnFrequency;
    }

    public String getDefermentPeriod() {
        return defermentPeriod;
    }

    public void setDefermentPeriod(String defermentPeriod) {
        this.defermentPeriod = defermentPeriod;
    }

    public String getTerminalBenefit() {
        return terminalBenefit;
    }

    public void setTerminalBenefit(String terminalBenefit) {
        this.terminalBenefit = terminalBenefit;
    }

        public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getCorpusValue() {
        return corpusValue;
    }

    public void setCorpusValue(String corpusValue) {
        this.corpusValue = corpusValue;
    }

    public String getTotalPremiumExcGST() {
        return totalPremiumExcGST;
    }

    public void setTotalPremiumExcGST(String totalPremiumExcGST) {
        this.totalPremiumExcGST = totalPremiumExcGST;
    }

    public String getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(String policyTerm) {
        this.policyTerm = policyTerm;
    }

    public String getPolicyPaymentTerm() {
        return policyPaymentTerm;
    }

    public void setPolicyPaymentTerm(String policyPaymentTerm) {
        this.policyPaymentTerm = policyPaymentTerm;
    }

    public String getCurrentPlanPremium() { return currentPlanPremium; }

    public void setCurrentPlanPremium(String currentPlanPremium) { this.currentPlanPremium = currentPlanPremium; }

    public String getPlanServiceTax() { return planServiceTax; }

    public void setPlanServiceTax(String planServiceTax) { this.planServiceTax = planServiceTax; }

    public String getTotalPremium() { return totalPremium; }

    public void setTotalPremium(String totalPremium) { this.totalPremium = totalPremium; }

    public String getGuaranteedDeathBenefit() {
        return guaranteedDeathBenefit;
    }

    public void setGuaranteedDeathBenefit(String guaranteedDeathBenefit) {
        this.guaranteedDeathBenefit = guaranteedDeathBenefit;
    }
}
