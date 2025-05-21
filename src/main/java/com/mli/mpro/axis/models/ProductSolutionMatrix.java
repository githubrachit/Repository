
package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "incomeRange", "lifeStage", "goalSelected", "currentCostOfGoal", "currentAgeOfInsured", "dateOfIllustration", "lumpSumSavingsForGoal",
	"recurringMonthlySavingsForGoal", "expectedAnnualRateOfInflation", "expectedAnnualReturnOnInvestment", "futureCostOfGoal", "suggestedSavingsPA",
	"commitmentPA", "productsRecommended", "psmReportField", "afyp" })
public class ProductSolutionMatrix {
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("incomeRange")
    private String incomeRange;
    @JsonProperty("lifeStage")
    private String lifeStage;
    @JsonProperty("goalSelected")
    private String goalSelected;
    @JsonProperty("currentCostOfGoal")
    private String currentCostOfGoal;
    @JsonProperty("currentAgeOfInsured")
    private String currentAgeOfInsured;
    @JsonProperty("dateOfIllustration")
    private String dateOfIllustration;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("lumpSumSavingsForGoal")
    private String lumpSumSavingsForGoal;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("recurringMonthlySavingsForGoal")
    private String recurringMonthlySavingsForGoal;
    @JsonProperty("expectedAnnualRateOfInflation")
    private String expectedAnnualRateOfInflation;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("expectedAnnualReturnOnInvestment")
    private String expectedAnnualReturnOnInvestment;
    @JsonProperty("futureCostOfGoal")
    private String futureCostOfGoal;
    @JsonProperty("suggestedSavingsPA")
    private String suggestedSavingsPA;
    @JsonProperty("commitmentPA")
    private String commitmentPA;
    @JsonProperty("productsRecommended")
    private String productsRecommended;
    @JsonProperty("psmReportField")
    private String psmReportField;
    @JsonProperty("afyp")
    private String afyp;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductSolutionMatrix() {
    }

    /**
     * 
     * @param currentCostOfGoal
     * @param dateOfIllustration
     * @param futureCostOfGoal
     * @param productsRecommended
     * @param goalSelected
     * @param commitmentPA
     * @param lifeStage
     * @param expectedAnnualRateOfInflation
     * @param suggestedSavingsPA
     * @param psmReportField
     * @param lumpSumSavingsForGoal
     * @param expectedAnnualReturnOnInvestment
     * @param afyp
     * @param incomeRange
     * @param currentAgeOfInsured
     * @param recurringMonthlySavingsForGoal
     */
    public ProductSolutionMatrix(String incomeRange, String lifeStage, String goalSelected, String currentCostOfGoal, String currentAgeOfInsured,
	    String dateOfIllustration, String lumpSumSavingsForGoal, String recurringMonthlySavingsForGoal, String expectedAnnualRateOfInflation,
	    String expectedAnnualReturnOnInvestment, String futureCostOfGoal, String suggestedSavingsPA, String commitmentPA, String productsRecommended,
	    String psmReportField, String afyp) {
	super();
	this.incomeRange = incomeRange;
	this.lifeStage = lifeStage;
	this.goalSelected = goalSelected;
	this.currentCostOfGoal = currentCostOfGoal;
	this.currentAgeOfInsured = currentAgeOfInsured;
	this.dateOfIllustration = dateOfIllustration;
	this.lumpSumSavingsForGoal = lumpSumSavingsForGoal;
	this.recurringMonthlySavingsForGoal = recurringMonthlySavingsForGoal;
	this.expectedAnnualRateOfInflation = expectedAnnualRateOfInflation;
	this.expectedAnnualReturnOnInvestment = expectedAnnualReturnOnInvestment;
	this.futureCostOfGoal = futureCostOfGoal;
	this.suggestedSavingsPA = suggestedSavingsPA;
	this.commitmentPA = commitmentPA;
	this.productsRecommended = productsRecommended;
	this.psmReportField = psmReportField;
	this.afyp = afyp;
    }

    @JsonProperty("incomeRange")
    public String getIncomeRange() {
	return incomeRange;
    }

    @JsonProperty("incomeRange")
    public void setIncomeRange(String incomeRange) {
	this.incomeRange = incomeRange;
    }

    @JsonProperty("lifeStage")
    public String getLifeStage() {
	return lifeStage;
    }

    @JsonProperty("lifeStage")
    public void setLifeStage(String lifeStage) {
	this.lifeStage = lifeStage;
    }

    @JsonProperty("goalSelected")
    public String getGoalSelected() {
	return goalSelected;
    }

    @JsonProperty("goalSelected")
    public void setGoalSelected(String goalSelected) {
	this.goalSelected = goalSelected;
    }

    @JsonProperty("currentCostOfGoal")
    public String getCurrentCostOfGoal() {
	return currentCostOfGoal;
    }

    @JsonProperty("currentCostOfGoal")
    public void setCurrentCostOfGoal(String currentCostOfGoal) {
	this.currentCostOfGoal = currentCostOfGoal;
    }

    @JsonProperty("currentAgeOfInsured")
    public String getCurrentAgeOfInsured() {
	return currentAgeOfInsured;
    }

    @JsonProperty("currentAgeOfInsured")
    public void setCurrentAgeOfInsured(String currentAgeOfInsured) {
	this.currentAgeOfInsured = currentAgeOfInsured;
    }

    @JsonProperty("dateOfIllustration")
    public String getDateOfIllustration() {
	return dateOfIllustration;
    }

    @JsonProperty("dateOfIllustration")
    public void setDateOfIllustration(String dateOfIllustration) {
	this.dateOfIllustration = dateOfIllustration;
    }

    @JsonProperty("lumpSumSavingsForGoal")
    public String getLumpSumSavingsForGoal() {
	return lumpSumSavingsForGoal;
    }

    @JsonProperty("lumpSumSavingsForGoal")
    public void setLumpSumSavingsForGoal(String lumpSumSavingsForGoal) {
	this.lumpSumSavingsForGoal = lumpSumSavingsForGoal;
    }

    @JsonProperty("recurringMonthlySavingsForGoal")
    public String getRecurringMonthlySavingsForGoal() {
	return recurringMonthlySavingsForGoal;
    }

    @JsonProperty("recurringMonthlySavingsForGoal")
    public void setRecurringMonthlySavingsForGoal(String recurringMonthlySavingsForGoal) {
	this.recurringMonthlySavingsForGoal = recurringMonthlySavingsForGoal;
    }

    @JsonProperty("expectedAnnualRateOfInflation")
    public String getExpectedAnnualRateOfInflation() {
	return expectedAnnualRateOfInflation;
    }

    @JsonProperty("expectedAnnualRateOfInflation")
    public void setExpectedAnnualRateOfInflation(String expectedAnnualRateOfInflation) {
	this.expectedAnnualRateOfInflation = expectedAnnualRateOfInflation;
    }

    @JsonProperty("expectedAnnualReturnOnInvestment")
    public String getExpectedAnnualReturnOnInvestment() {
	return expectedAnnualReturnOnInvestment;
    }

    @JsonProperty("expectedAnnualReturnOnInvestment")
    public void setExpectedAnnualReturnOnInvestment(String expectedAnnualReturnOnInvestment) {
	this.expectedAnnualReturnOnInvestment = expectedAnnualReturnOnInvestment;
    }

    @JsonProperty("futureCostOfGoal")
    public String getFutureCostOfGoal() {
	return futureCostOfGoal;
    }

    @JsonProperty("futureCostOfGoal")
    public void setFutureCostOfGoal(String futureCostOfGoal) {
	this.futureCostOfGoal = futureCostOfGoal;
    }

    @JsonProperty("suggestedSavingsPA")
    public String getSuggestedSavingsPA() {
	return suggestedSavingsPA;
    }

    @JsonProperty("suggestedSavingsPA")
    public void setSuggestedSavingsPA(String suggestedSavingsPA) {
	this.suggestedSavingsPA = suggestedSavingsPA;
    }

    @JsonProperty("commitmentPA")
    public String getCommitmentPA() {
	return commitmentPA;
    }

    @JsonProperty("commitmentPA")
    public void setCommitmentPA(String commitmentPA) {
	this.commitmentPA = commitmentPA;
    }

    @JsonProperty("productsRecommended")
    public String getProductsRecommended() {
	return productsRecommended;
    }

    @JsonProperty("productsRecommended")
    public void setProductsRecommended(String productsRecommended) {
	this.productsRecommended = productsRecommended;
    }

    @JsonProperty("psmReportField")
    public String getPsmReportField() {
	return psmReportField;
    }

    @JsonProperty("psmReportField")
    public void setPsmReportField(String psmReportField) {
	this.psmReportField = psmReportField;
    }

    @JsonProperty("afyp")
    public String getAfyp() {
	return afyp;
    }

    @JsonProperty("afyp")
    public void setAfyp(String afyp) {
	this.afyp = afyp;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ProductSolutionMatrix [incomeRange=" + incomeRange + ", lifeStage=" + lifeStage + ", goalSelected=" + goalSelected + ", currentCostOfGoal="
		+ currentCostOfGoal + ", currentAgeOfInsured=" + currentAgeOfInsured + ", dateOfIllustration=" + dateOfIllustration + ", lumpSumSavingsForGoal="
		+ lumpSumSavingsForGoal + ", recurringMonthlySavingsForGoal=" + recurringMonthlySavingsForGoal + ", expectedAnnualRateOfInflation="
		+ expectedAnnualRateOfInflation + ", expectedAnnualReturnOnInvestment=" + expectedAnnualReturnOnInvestment + ", futureCostOfGoal="
		+ futureCostOfGoal + ", suggestedSavingsPA=" + suggestedSavingsPA + ", commitmentPA=" + commitmentPA + ", productsRecommended="
		+ productsRecommended + ", psmReportField=" + psmReportField + ", afyp=" + afyp + "]";
    }

}
