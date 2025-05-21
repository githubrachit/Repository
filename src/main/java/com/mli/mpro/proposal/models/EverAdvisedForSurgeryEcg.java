package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EverAdvisedForSurgeryEcg {

    @JsonProperty("isEverAdvisedSurg")
    private String isEverAdvisedSurg;
    @JsonProperty("isAccidentSurg")
    private String isAccidentSurg;
    @JsonProperty("isAppendHist")
    private String isAppendHist;
    @JsonProperty("isPileSurgHist")
    private String isPileSurgHist;
    @JsonProperty("isMriScanHist")
    private String isMriScanHist;
    @JsonProperty("isGallStoneHist")
    private String isGallStoneHist;
    @JsonProperty("isAnnualTestDone")
    private String isAnnualTestDone;
    @JsonProperty("isFluBloodDone")
    private String isFluBloodDone;
    @JsonProperty("isPregBloodDone")
    private String isPregBloodDone;
    @JsonProperty("isBloodDonatHist")
    private String isBloodDonatHist;
    @JsonProperty("isInsertnSurgery")
    private String isInsertnSurgery;
    @JsonProperty("isDnsSurgery")
    private String isDnsSurgery;
    @JsonProperty("isCataSurgHist")
    private String isCataSurgHist;
    @JsonProperty("isLasikCorrection")
    private String isLasikCorrection;
    @JsonProperty("anyOtherSurgeryDetails")
    private String anyOtherSurgeryDetails;

    public String getIsEverAdvisedSurg() {
        return isEverAdvisedSurg;
    }

    public void setIsEverAdvisedSurg(String isEverAdvisedSurg) {
        this.isEverAdvisedSurg = isEverAdvisedSurg;
    }

    public String getIsAccidentSurg() {
        return isAccidentSurg;
    }

    public void setIsAccidentSurg(String isAccidentSurg) {
        this.isAccidentSurg = isAccidentSurg;
    }

    public String getIsAppendHist() {
        return isAppendHist;
    }

    public void setIsAppendHist(String isAppendHist) {
        this.isAppendHist = isAppendHist;
    }

    public String getIsPileSurgHist() {
        return isPileSurgHist;
    }

    public void setIsPileSurgHist(String isPileSurgHist) {
        this.isPileSurgHist = isPileSurgHist;
    }

    public String getIsMriScanHist() {
        return isMriScanHist;
    }

    public void setIsMriScanHist(String isMriScanHist) {
        this.isMriScanHist = isMriScanHist;
    }

    public String getIsGallStoneHist() {
        return isGallStoneHist;
    }

    public void setIsGallStoneHist(String isGallStoneHist) {
        this.isGallStoneHist = isGallStoneHist;
    }

    public String getIsAnnualTestDone() {
        return isAnnualTestDone;
    }

    public void setIsAnnualTestDone(String isAnnualTestDone) {
        this.isAnnualTestDone = isAnnualTestDone;
    }

    public String getIsFluBloodDone() {
        return isFluBloodDone;
    }

    public void setIsFluBloodDone(String isFluBloodDone) {
        this.isFluBloodDone = isFluBloodDone;
    }

    public String getIsPregBloodDone() {
        return isPregBloodDone;
    }

    public void setIsPregBloodDone(String isPregBloodDone) {
        this.isPregBloodDone = isPregBloodDone;
    }

    public String getIsBloodDonatHist() {
        return isBloodDonatHist;
    }

    public void setIsBloodDonatHist(String isBloodDonatHist) {
        this.isBloodDonatHist = isBloodDonatHist;
    }

    public String getIsInsertnSurgery() {
        return isInsertnSurgery;
    }

    public void setIsInsertnSurgery(String isInsertnSurgery) {
        this.isInsertnSurgery = isInsertnSurgery;
    }

    public String getIsDnsSurgery() {
        return isDnsSurgery;
    }

    public void setIsDnsSurgery(String isDnsSurgery) {
        this.isDnsSurgery = isDnsSurgery;
    }

    public String getIsCataSurgHist() {
        return isCataSurgHist;
    }

    public void setIsCataSurgHist(String isCataSurgHist) {
        this.isCataSurgHist = isCataSurgHist;
    }

    public String getIsLasikCorrection() {
        return isLasikCorrection;
    }

    public void setIsLasikCorrection(String isLasikCorrection) {
        this.isLasikCorrection = isLasikCorrection;
    }

    public String getAnyOtherSurgeryDetails() {
        return anyOtherSurgeryDetails;
    }

    public void setAnyOtherSurgeryDetails(String anyOtherSurgeryDetails) {
        this.anyOtherSurgeryDetails = anyOtherSurgeryDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "EverAdvisedForSurgeryEcg{" +
                "isEverAdvisedSurg='" + isEverAdvisedSurg + '\'' +
                ", isAccidentSurg='" + isAccidentSurg + '\'' +
                ", isAppendHist='" + isAppendHist + '\'' +
                ", isPileSurgHist='" + isPileSurgHist + '\'' +
                ", isMriScanHist='" + isMriScanHist + '\'' +
                ", isGallStoneHist='" + isGallStoneHist + '\'' +
                ", isAnnualTestDone='" + isAnnualTestDone + '\'' +
                ", isFluBloodDone='" + isFluBloodDone + '\'' +
                ", isPregBloodDone='" + isPregBloodDone + '\'' +
                ", isBloodDonatHist='" + isBloodDonatHist + '\'' +
                ", isInsertnSurgery='" + isInsertnSurgery + '\'' +
                ", isDnsSurgery='" + isDnsSurgery + '\'' +
                ", isCataSurgHist='" + isCataSurgHist + '\'' +
                ", isLasikCorrection='" + isLasikCorrection + '\'' +
                ", anyOtherSurgeryDetails='" + anyOtherSurgeryDetails + '\'' +
                '}';
    }
}
