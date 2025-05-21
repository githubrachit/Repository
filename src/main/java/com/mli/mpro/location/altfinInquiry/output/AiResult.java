package com.mli.mpro.location.altfinInquiry.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AiResult {
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("aiIncomeBand")
    private String aiIncomeBand;
    @JsonProperty("aiEstimatedIncome")
    private String aiEstimatedIncome;
    @JsonProperty("estimationModelUsed")
    private String estimationModelUsed;
    @JsonProperty("deliquencyFlag")
    private String deliquencyFlag;
    @JsonProperty("entryTimeStamp")
    private String entryTimeStamp;
    @JsonProperty("resultStatus")
    private String resultStatus;
    @JsonProperty("apiStatus")
    private String apiStatus;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("confidenceScore")
    private String confidenceScore;
    @JsonProperty("consideredVendorName")
    private String consideredVendorName;
    @JsonProperty("consideredAIModule")
    private String consideredAIModule;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getAiIncomeBand() {
        return aiIncomeBand;
    }

    public void setAiIncomeBand(String aiIncomeBand) {
        this.aiIncomeBand = aiIncomeBand;
    }

    public String getAiEstimatedIncome() {
        return aiEstimatedIncome;
    }

    public void setAiEstimatedIncome(String aiEstimatedIncome) {
        this.aiEstimatedIncome = aiEstimatedIncome;
    }

    public String getEstimationModelUsed() {
        return estimationModelUsed;
    }

    public void setEstimationModelUsed(String estimationModelUsed) {
        this.estimationModelUsed = estimationModelUsed;
    }

    public String getDeliquencyFlag() {
        return deliquencyFlag;
    }

    public void setDeliquencyFlag(String deliquencyFlag) {
        this.deliquencyFlag = deliquencyFlag;
    }

    public String getEntryTimeStamp() {
        return entryTimeStamp;
    }

    public void setEntryTimeStamp(String entryTimeStamp) {
        this.entryTimeStamp = entryTimeStamp;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(String confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getConsideredVendorName() {
        return consideredVendorName;
    }

    public void setConsideredVendorName(String consideredVendorName) {
        this.consideredVendorName = consideredVendorName;
    }

    public String getConsideredAIModule() {
        return consideredAIModule;
    }

    public void setConsideredAIModule(String consideredAIModule) {
        this.consideredAIModule = consideredAIModule;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AiResult{" +
                "policyNumber='" + policyNumber + '\'' +
                ", aiIncomeBand='" + aiIncomeBand + '\'' +
                ", aiEstimatedIncome='" + aiEstimatedIncome + '\'' +
                ", estimationModelUsed='" + estimationModelUsed + '\'' +
                ", deliquencyFlag='" + deliquencyFlag + '\'' +
                ", entryTimeStamp='" + entryTimeStamp + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", apiStatus='" + apiStatus + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", confidenceScore='" + confidenceScore + '\'' +
                ", consideredVendorName='" + consideredVendorName + '\'' +
                ", consideredAIModule='" + consideredAIModule + '\'' +
                '}';
    }
}
