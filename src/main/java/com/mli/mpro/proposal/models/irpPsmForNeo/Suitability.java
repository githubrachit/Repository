package com.mli.mpro.proposal.models.irpPsmForNeo;

public class Suitability {
    public String age;

    public String buyingFor;

    public String annualIncome;

    public String financialAndFamilyGoals;

    public String goalHorizon;

    public String paymentPreference;

    public String riskAppetite;

    // getters and setters
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getBuyingFor() {
        return buyingFor;
    }
    public void setBuyingFor(String buyingFor) {
        this.buyingFor = buyingFor;
    }
    public String getAnnualIncome() {
        return annualIncome;
    }
    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }
    public String getFinancialAndFamilyGoals() {
        return financialAndFamilyGoals;
    }
    public void setFinancialAndFamilyGoals(String financialAndFamilyGoals) {
        this.financialAndFamilyGoals = financialAndFamilyGoals;
    }
    public String getGoalHorizon() {
        return goalHorizon;
    }
    public void setGoalHorizon(String goalHorizon) {
        this.goalHorizon = goalHorizon;
    }
    public String getPaymentPreference() {
        return paymentPreference;
    }
    public void setPaymentPreference(String paymentPreference) {
        this.paymentPreference = paymentPreference;
    }
    public String getRiskAppetite() {
        return riskAppetite;
    }
    public void setRiskAppetite(String riskAppetite) {
        this.riskAppetite = riskAppetite;
    }
    //toString
    @Override
    public String toString() {
        return "Suitability [age=" + age + ", annualIncome=" + annualIncome + ", buyingFor=" + buyingFor
                + ", financialAndFamilyGoals=" + financialAndFamilyGoals + ", goalHorizon=" + goalHorizon
                + ", paymentPreference=" + paymentPreference + ", riskAppetite=" + riskAppetite + "]";
    }

}
