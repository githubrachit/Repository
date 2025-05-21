
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "partyType","newDiabeticQuestion", "travelAndAdventure", "heightAndWeight", "habit", "health", "familyOrCriminalHistory", "insuranceDetails", "parentsDetails",
        "covidQuestionnaire" })
public class LifeStyleDetails {

    @JsonProperty("partyType")
    private String partyType;

    @JsonProperty("newDiabeticQuestion")
    private String newDiabeticQuestion;

    @JsonProperty("travelAndAdventure")
    private TravelAndAdventure travelAndAdventure;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("heightAndWeight")
    private HeightAndWeight heightAndWeight;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("habit")
    private Habit habit;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("health")
    private Health health;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("familyOrCriminalHistory")
    private FamilyOrCriminalHistory familyOrCriminalHistory;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("insuranceDetails")
    private InsuranceDetails insuranceDetails;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("parentsDetails")
    private ParentsDetails parentsDetails;
    @JsonProperty("covidQuestionnaire")
    @Sensitive(MaskType.MASK_ALL)
    private CovidQuestionnaire covidQuestionnaire;
    @JsonProperty("diabeticQuestionnaire")
    @Sensitive(MaskType.MASK_ALL)
    private DiabeticQuestionnaire diabeticQuestionnaire;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("highBloodPressureQuestionnaire")
    private HighBloodPressureQuestionnaire highBloodPressureQuestionnaire;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("respiratoryDisorderQuestionnaire")
    private RespiratoryDisorderQuestionnaire respiratoryDisorderQuestionnaire;

    //FUL2-116960 swiss re changes
    @JsonProperty("insuranceSumAssuredDetails")
    private InsuranceSumAssuredDetails insuranceSumAssuredDetails;

    @JsonProperty("newHypertensionQuestion")
    private String newHypertensionQuestion;

    @JsonProperty("newRespiratoryQuestion")
    private String newRespiratoryQuestion;

    @JsonProperty("isHivCancerHistoryApplicable")
    private String isHivCancerHistoryApplicable;

    public InsuranceSumAssuredDetails getInsuranceSumAssuredDetails() {
        return insuranceSumAssuredDetails;
    }

    public void setInsuranceSumAssuredDetails(InsuranceSumAssuredDetails insuranceSumAssuredDetails) {
        this.insuranceSumAssuredDetails = insuranceSumAssuredDetails;
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public LifeStyleDetails() {
    }

    public LifeStyleDetails(String partyType,String newDiabeticQuestion, TravelAndAdventure travelAndAdventure, HeightAndWeight heightAndWeight, Habit habit, Health health,
	    FamilyOrCriminalHistory familyOrCriminalHistory, InsuranceDetails insuranceDetails, ParentsDetails parentsDetails, String newHypertensionQuestion, String newRespiratoryQuestion, String isHivCancerHistoryApplicable) {
	super();
	this.partyType = partyType;
    this.newDiabeticQuestion=newDiabeticQuestion;
	this.travelAndAdventure = travelAndAdventure;
	this.heightAndWeight = heightAndWeight;
	this.habit = habit;
	this.health = health;
	this.familyOrCriminalHistory = familyOrCriminalHistory;
	this.insuranceDetails = insuranceDetails;
	this.parentsDetails = parentsDetails;
    this.newHypertensionQuestion = newHypertensionQuestion;
    this.newRespiratoryQuestion = newRespiratoryQuestion;
    this.isHivCancerHistoryApplicable = isHivCancerHistoryApplicable;
    }

    @JsonProperty("travelAndAdventure")
    public TravelAndAdventure getTravelAndAdventure() {
	return travelAndAdventure;
    }

    @JsonProperty("travelAndAdventure")
    public void setTravelAndAdventure(TravelAndAdventure travelAndAdventure) {
	this.travelAndAdventure = travelAndAdventure;
    }

    @JsonProperty("heightAndWeight")
    public HeightAndWeight getHeightAndWeight() {
	return heightAndWeight;
    }

    @JsonProperty("heightAndWeight")
    public void setHeightAndWeight(HeightAndWeight heightAndWeight) {
	this.heightAndWeight = heightAndWeight;
    }

    @JsonProperty("habit")
    public Habit getHabit() {
	return habit;
    }

    @JsonProperty("habit")
    public void setHabit(Habit habit) {
	this.habit = habit;
    }

    @JsonProperty("health")
    public Health getHealth() {
	return health;
    }

    @JsonProperty("health")
    public void setHealth(Health health) {
	this.health = health;
    }

    @JsonProperty("familyOrCriminalHistory")
    public FamilyOrCriminalHistory getFamilyOrCriminalHistory() {
	return familyOrCriminalHistory;
    }

    @JsonProperty("familyOrCriminalHistory")
    public void setFamilyOrCriminalHistory(FamilyOrCriminalHistory familyOrCriminalHistory) {
	this.familyOrCriminalHistory = familyOrCriminalHistory;
    }

    @JsonProperty("insuranceDetails")
    public InsuranceDetails getInsuranceDetails() {
	return insuranceDetails;
    }

    @JsonProperty("insuranceDetails")
    public void setInsuranceDetails(InsuranceDetails insuranceDetails) {
	this.insuranceDetails = insuranceDetails;
    }

    public String getPartyType() {
	return partyType;
    }

    public void setPartyType(String partyType) {
	this.partyType = partyType;
    }

    public String getNewDiabeticQuestion() {
        return newDiabeticQuestion;
    }

    public void setNewDiabeticQuestion(String newDiabeticQuestion) {
        this.newDiabeticQuestion = newDiabeticQuestion;
    }

    public ParentsDetails getParentsDetails() {
	return parentsDetails;
    }

    public void setParentsDetails(ParentsDetails parentsDetails) {
	this.parentsDetails = parentsDetails;
    }

    public CovidQuestionnaire getCovidQuestionnaire() { return covidQuestionnaire; }

    public void setCovidQuestionnaire(CovidQuestionnaire covidQuestionnaire) { this.covidQuestionnaire = covidQuestionnaire; }

    public DiabeticQuestionnaire getDiabeticQuestionnaire() { return diabeticQuestionnaire; }

    public void setDiabeticQuestionnaire(DiabeticQuestionnaire diabeticQuestionnaire) {
        this.diabeticQuestionnaire = diabeticQuestionnaire;
    }

    public HighBloodPressureQuestionnaire getHighBloodPressureQuestionnaire() {
        return highBloodPressureQuestionnaire;
    }

    public void setHighBloodPressureQuestionnaire(HighBloodPressureQuestionnaire highBloodPressureQuestionnaire) {
        this.highBloodPressureQuestionnaire = highBloodPressureQuestionnaire;
    }

    public RespiratoryDisorderQuestionnaire getRespiratoryDisorderQuestionnaire() {
        return respiratoryDisorderQuestionnaire;
    }

    public void setRespiratoryDisorderQuestionnaire(RespiratoryDisorderQuestionnaire respiratoryDisorderQuestionnaire) {
        this.respiratoryDisorderQuestionnaire = respiratoryDisorderQuestionnaire;
    }

    public String getNewHypertensionQuestion() {
        return newHypertensionQuestion;
    }

    public void setNewHypertensionQuestion(String newHypertensionQuestion) {
        this.newHypertensionQuestion = newHypertensionQuestion;
    }

    public String getNewRespiratoryQuestion() {
        return newRespiratoryQuestion;
    }

    public void setNewRespiratoryQuestion(String newRespiratoryQuestion) {
        this.newRespiratoryQuestion = newRespiratoryQuestion;
    }

    public String getIsHivCancerHistoryApplicable() {
        return isHivCancerHistoryApplicable;
    }

    public void setIsHivCancerHistoryApplicable(String isHivCancerHistoryApplicable) {
        this.isHivCancerHistoryApplicable = isHivCancerHistoryApplicable;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "LifeStyleDetails [partyType=" + partyType + ",newDiabeticQuestion=" + newDiabeticQuestion + ", travelAndAdventure=" + travelAndAdventure + ", heightAndWeight=" + heightAndWeight + ", habit="
		+ habit + ", health=" + health + ", familyOrCriminalHistory=" + familyOrCriminalHistory + ", insuranceDetails=" + insuranceDetails
		+ ", parentsDetails=" + parentsDetails + ", covidQuestionnaire=" + covidQuestionnaire + ", diabeticQuestionnaire=" + diabeticQuestionnaire
        + ", highBloodPressureQuestionnaire=" + highBloodPressureQuestionnaire + ", respiratoryDisorderQuestionnaire=" + respiratoryDisorderQuestionnaire+"]" + ", newHypertensionQuestion=" + newHypertensionQuestion + "]";
    }

}
