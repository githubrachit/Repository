package com.mli.mpro.productRestriction.models;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restrictionData")
public class RestrictionData {

    @Id
    private String id;
    @NotNull
    private String productId;
    @NotNull
    private String channel;
    @NotNull
    private String customerClassification;
    private int minimumIncome;
    private int nriMinimumIncome;
    /*FUL2-6814 OTP and SmTP Stop Rules - Phase 1:
    Removed logic build for JIRA FUL2-5522 because of new
    requirment and added new below 4 required tags*/
    private int sparcSalIncome;
    private int sparcSelfEmployedIncome;
    private int sparcAgeLimit;
    private int minimumHighSchoolIncome;
    private int minimumEducationIncome;
    private String shouldAllowSmoker;
    private double minimumSumAssured;
    private List<String> educations;
    private List<String> occupations;
    private List<String> nriEducations;
    private List<String> nriOccupations;
    /*FUL2-13711 Income Criteria Change for SmTP and OTP*/
    private int salAssessedIncomeForOneCroreSumAssured;
    private int salAssessedIncomeForTwoCroreSumAssured;
    private int selfEmployedAssessedIncome;
    @Transient
    private List<String> fieldsToUpdate;
    //FULL2_OTP and SmTP Stop Rules - Phase 2 | Housewife
    private double housewifeMinSumAssured;
    private double housewifeMaxSumAssured;
    private double spouseAnnualIncome;
    //SJB 
    private double maximumSumAssured;
    private double sumAssured;
    private List<String> ageIncomeBand;
    private double minimumHighSchoolSumAssured;

    
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

    public String getCustomerClassification() {
	return customerClassification;
    }

    public void setCustomerClassification(String customerClassification) {
	this.customerClassification = customerClassification;
    }

    public int getMinimumIncome() {
	return minimumIncome;
    }

    public void setMinimumIncome(int minimumIncome) {
	this.minimumIncome = minimumIncome;
    }

    public int getSparcSalIncome() {
        return sparcSalIncome;
    }

    public void setSparcSalIncome(int sparcSalIncome) {
        this.sparcSalIncome = sparcSalIncome;
    }

    public int getSparcSelfEmployedIncome() {
        return sparcSelfEmployedIncome;
    }

    public void setSparcSelfEmployedIncome(int sparcSelfEmployedIncome) {
        this.sparcSelfEmployedIncome = sparcSelfEmployedIncome;
    }

    public int getSparcAgeLimit() {
        return sparcAgeLimit;
    }

    public void setSparcAgeLimit(int sparcAgeLimit) {
        this.sparcAgeLimit = sparcAgeLimit;
    }

    public int getMinimumHighSchoolIncome() {
        return minimumHighSchoolIncome;
    }

    public void setMinimumHighSchoolIncome(int minimumHighSchoolIncome) {
        this.minimumHighSchoolIncome = minimumHighSchoolIncome;
    }

    public int getMinimumEducationIncome() {
	return minimumEducationIncome;
    }

    public void setMinimumEducationIncome(int minimumEducationIncome) {
	this.minimumEducationIncome = minimumEducationIncome;
    }

    public String getShouldAllowSmoker() {
	return shouldAllowSmoker;
    }

    public void setShouldAllowSmoker(String shouldAllowSmoker) {
	this.shouldAllowSmoker = shouldAllowSmoker;
    }

    public double getMinimumSumAssured() {
	return minimumSumAssured;
    }

    public void setMinimumSumAssured(double minimumSumAssured) {
	this.minimumSumAssured = minimumSumAssured;
    }
    public List<String> getEducations() {
	return educations;
    }

    public void setEducations(List<String> educations) {
	this.educations = educations;
    }

    public List<String> getOccupations() {
	return occupations;
    }

    public void setOccupations(List<String> occupations) {
	this.occupations = occupations;
    }

    public int getSalAssessedIncomeForOneCroreSumAssured() {
        return salAssessedIncomeForOneCroreSumAssured;
    }

    public void setSalAssessedIncomeForOneCroreSumAssured(int salAssessedIncomeForOneCroreSumAssured) {
        this.salAssessedIncomeForOneCroreSumAssured = salAssessedIncomeForOneCroreSumAssured;
    }

    public int getSalAssessedIncomeForTwoCroreSumAssured() {
        return salAssessedIncomeForTwoCroreSumAssured;
    }

    public void setSalAssessedIncomeForTwoCroreSumAssured(int salAssessedIncomeForTwoCroreSumAssured) {
        this.salAssessedIncomeForTwoCroreSumAssured = salAssessedIncomeForTwoCroreSumAssured;
    }

    public int getSelfEmployedAssessedIncome() {
        return selfEmployedAssessedIncome;
    }

    public void setSelfEmployedAssessedIncome(int selfEmployedAssessedIncome) {
        this.selfEmployedAssessedIncome = selfEmployedAssessedIncome;
    }

    public List<String> getFieldsToUpdate() {
	return fieldsToUpdate;
    }

    public void setFieldsToUpdate(List<String> fieldsToUpdate) {
	this.fieldsToUpdate = fieldsToUpdate;
    }

	public double getHousewifeMinSumAssured() {
		return housewifeMinSumAssured;
	}

	public void setHousewifeMinSumAssured(double housewifeMinSumAssured) {
		this.housewifeMinSumAssured = housewifeMinSumAssured;
	}

	public double getHousewifeMaxSumAssured() {
		return housewifeMaxSumAssured;
	}

	public void setHousewifeMaxSumAssured(double housewifeMaxSumAssured) {
		this.housewifeMaxSumAssured = housewifeMaxSumAssured;
	}

	public double getSpouseAnnualIncome() {
		return spouseAnnualIncome;
	}

	public void setSpouseAnnualIncome(double spouseAnnualIncome) {
		this.spouseAnnualIncome = spouseAnnualIncome;
	}

	public List<String> getAgeIncomeBand() {
		return ageIncomeBand;
	}

	public void setAgeIncomeBand(List<String> ageIncomeBand) {
		this.ageIncomeBand = ageIncomeBand;
	}

	public double getMaximumSumAssured() {
		return maximumSumAssured;
	}

	public void setMaximumSumAssured(double maximumSumAssured) {
		this.maximumSumAssured = maximumSumAssured;
	}

	public double getMinimumHighSchoolSumAssured() {
		return minimumHighSchoolSumAssured;
	}

	public void setMinimumHighSchoolSumAssured(double minimumHighSchoolSumAssured) {
		this.minimumHighSchoolSumAssured = minimumHighSchoolSumAssured;
	}

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public int getNriMinimumIncome() {
        return nriMinimumIncome;
    }

    public void setNriMinimumIncome(int nriMinimumIncome) {
        this.nriMinimumIncome = nriMinimumIncome;
    }

    public List<String> getNriEducations() {
        return nriEducations;
    }

    public void setNriEducations(List<String> nriEducations) {
        this.nriEducations = nriEducations;
    }

    public List<String> getNriOccupations() {
        return nriOccupations;
    }

    public void setNriOccupations(List<String> nriOccupations) {
        this.nriOccupations = nriOccupations;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "RestrictionData{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", channel='" + channel + '\'' +
                ", customerClassification='" + customerClassification + '\'' +
                ", minimumIncome=" + minimumIncome +
                ", nriMinimumIncome=" + nriMinimumIncome +
                ", sparcSalIncome=" + sparcSalIncome +
                ", sparcSelfEmployedIncome=" + sparcSelfEmployedIncome +
                ", sparcAgeLimit=" + sparcAgeLimit +
                ", minimumHighSchoolIncome=" + minimumHighSchoolIncome +
                ", minimumEducationIncome=" + minimumEducationIncome +
                ", shouldAllowSmoker='" + shouldAllowSmoker + '\'' +
                ", minimumSumAssured=" + minimumSumAssured +
                ", educations=" + educations +
                ", occupations=" + occupations +
                ", nriEducations=" + nriEducations +
                ", nriOccupations=" + nriOccupations +
                ", salAssessedIncomeForOneCroreSumAssured=" + salAssessedIncomeForOneCroreSumAssured +
                ", salAssessedIncomeForTwoCroreSumAssured=" + salAssessedIncomeForTwoCroreSumAssured +
                ", selfEmployedAssessedIncome=" + selfEmployedAssessedIncome +
                ", fieldsToUpdate=" + fieldsToUpdate +
                ", housewifeMinSumAssured=" + housewifeMinSumAssured +
                ", housewifeMaxSumAssured=" + housewifeMaxSumAssured +
                ", spouseAnnualIncome=" + spouseAnnualIncome +
                ", maximumSumAssured=" + maximumSumAssured +
                ", sumAssured=" + sumAssured +
                ", ageIncomeBand=" + ageIncomeBand +
                ", minimumHighSchoolSumAssured=" + minimumHighSchoolSumAssured +
                '}';
    }
}
