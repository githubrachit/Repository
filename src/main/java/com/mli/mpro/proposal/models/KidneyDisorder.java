package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KidneyDisorder {

    @JsonProperty("isKidneyRenalDisorder")
    private String isKidneyRenalDisorder;
    @JsonProperty("isUtiHistory")
    private String isUtiHistory;
    @JsonProperty("isKidneySurgHis")
    private String isKidneySurgHis;
    @JsonProperty("isKidStoneHis")
    private String isKidStoneHis;
    @JsonProperty("specifyKidneyDetails")
    private String specifyKidneyDetails;
    @JsonProperty("chronicKidneyDisease")
    private String chronicKidneyDisease;

    public String getIsKidneyRenalDisorder() {
        return isKidneyRenalDisorder;
    }

    public void setIsKidneyRenalDisorder(String isKidneyRenalDisorder) {
        this.isKidneyRenalDisorder = isKidneyRenalDisorder;
    }

    public String getIsUtiHistory() {
        return isUtiHistory;
    }

    public void setIsUtiHistory(String isUtiHistory) {
        this.isUtiHistory = isUtiHistory;
    }

    public String getIsKidneySurgHis() {
        return isKidneySurgHis;
    }

    public void setIsKidneySurgHis(String isKidneySurgHis) {
        this.isKidneySurgHis = isKidneySurgHis;
    }

    public String getIsKidStoneHis() {
        return isKidStoneHis;
    }

    public void setIsKidStoneHis(String isKidStoneHis) {
        this.isKidStoneHis = isKidStoneHis;
    }

    public String getSpecifyKidneyDetails() {
        return specifyKidneyDetails;
    }

    public void setSpecifyKidneyDetails(String specifyKidneyDetails) {
        this.specifyKidneyDetails = specifyKidneyDetails;
    }

    public String getChronicKidneyDisease() {
        return chronicKidneyDisease;
    }

    public void setChronicKidneyDisease(String chronicKidneyDisease) {
        this.chronicKidneyDisease = chronicKidneyDisease;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "KidneyDisorder{" +
                "isKidneyRenalDisorder='" + isKidneyRenalDisorder + '\'' +
                ", isUtiHistory='" + isUtiHistory + '\'' +
                ", isKidneySurgHis='" + isKidneySurgHis + '\'' +
                ", isKidStoneHis='" + isKidStoneHis + '\'' +
                ", specifyKidneyDetails='" + specifyKidneyDetails + '\'' +
                ", chronicKidneyDisease='" + chronicKidneyDisease + '\'' +
                '}';
    }
}
