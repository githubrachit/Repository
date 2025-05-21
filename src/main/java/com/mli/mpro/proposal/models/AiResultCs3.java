package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;

public class AiResultCs3 {
    @JsonProperty("polId")
    private String polId;
    @JsonProperty("entryTimestamp")
    private String entryTimestamp;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("salaryTagCount")
    private String salaryTagCount;
    @JsonProperty("salaryInconsistentFlag")
    private String salaryInconsistentFlag;
    @JsonProperty("monthsCount")
    private String monthsCount;
    @JsonProperty("medicalFlag")
    private String medicalFlag;
    @JsonProperty("pharmacyFlag")
    private String pharmacyFlag;
    @JsonProperty("liquorFlag")
    private String liquorFlag;
    @JsonProperty("otherInconsistencyFlag")
    private String otherInconsistencyFlag;
    @JsonProperty("aiEstimatedIncome")
    private String aiEstimatedIncome;
    @JsonProperty("lifeInsFlag")
    private String lifeInsFlag;
    @JsonProperty("finQCDiscrepancy")
    private ArrayList<String> finQCDiscrepancy;
    @JsonProperty("aiRecommendation")
    private ArrayList<String> aiRecommendation;

    public String getPolId() {
        return polId;
    }

    public void setPolId(String polId) {
        this.polId = polId;
    }

    public String getEntryTimestamp() {
        return entryTimestamp;
    }

    public void setEntryTimestamp(String entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSalaryTagCount() {
        return salaryTagCount;
    }

    public void setSalaryTagCount(String salaryTagCount) {
        this.salaryTagCount = salaryTagCount;
    }

    public String getSalaryInconsistentFlag() {
        return salaryInconsistentFlag;
    }

    public void setSalaryInconsistentFlag(String salaryInconsistentFlag) {
        this.salaryInconsistentFlag = salaryInconsistentFlag;
    }

    public String getMonthsCount() {
        return monthsCount;
    }

    public void setMonthsCount(String monthsCount) {
        this.monthsCount = monthsCount;
    }

    public String getMedicalFlag() {
        return medicalFlag;
    }

    public void setMedicalFlag(String medicalFlag) {
        this.medicalFlag = medicalFlag;
    }

    public String getPharmacyFlag() {
        return pharmacyFlag;
    }

    public void setPharmacyFlag(String pharmacyFlag) {
        this.pharmacyFlag = pharmacyFlag;
    }

    public String getLiquorFlag() {
        return liquorFlag;
    }

    public void setLiquorFlag(String liquorFlag) {
        this.liquorFlag = liquorFlag;
    }

    public String getOtherInconsistencyFlag() {
        return otherInconsistencyFlag;
    }

    public void setOtherInconsistencyFlag(String otherInconsistencyFlag) {
        this.otherInconsistencyFlag = otherInconsistencyFlag;
    }

    public String getAiEstimatedIncome() {
        return aiEstimatedIncome;
    }

    public void setAiEstimatedIncome(String aiEstimatedIncome) {
        this.aiEstimatedIncome = aiEstimatedIncome;
    }

    public String getLifeInsFlag() {
        return lifeInsFlag;
    }

    public void setLifeInsFlag(String lifeInsFlag) {
        this.lifeInsFlag = lifeInsFlag;
    }

    public ArrayList<String> getFinQCDiscrepancy() {
        return finQCDiscrepancy;
    }

    public void setFinQCDiscrepancy(ArrayList<String> finQCDiscrepancy) {
        this.finQCDiscrepancy = finQCDiscrepancy;
    }

    public ArrayList<String> getAiRecommendation() {
        return aiRecommendation;
    }

    public void setAiRecommendation(ArrayList<String> aiRecommendation) {
        this.aiRecommendation = aiRecommendation;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AiResultCs3{" +
                "polId='" + polId + '\'' +
                ", entryTimestamp='" + entryTimestamp + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", salaryTagCount='" + salaryTagCount + '\'' +
                ", salaryInconsistentFlag='" + salaryInconsistentFlag + '\'' +
                ", monthsCount='" + monthsCount + '\'' +
                ", medicalFlag='" + medicalFlag + '\'' +
                ", pharmacyFlag='" + pharmacyFlag + '\'' +
                ", liquorFlag='" + liquorFlag + '\'' +
                ", otherInconsistencyFlag='" + otherInconsistencyFlag + '\'' +
                ", aiEstimatedIncome='" + aiEstimatedIncome + '\'' +
                ", lifeInsFlag='" + lifeInsFlag + '\'' +
                ", finQCDiscrepancy=" + finQCDiscrepancy +
                ", aiRecommendation=" + aiRecommendation +
                '}';
    }
}
