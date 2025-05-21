package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecifyHabit {

    @JsonProperty("everConsumeType")
    private String everConsumeType;
    @JsonProperty("consumeTobaccoCurrentlyOrOcc")
    private String consumeTobaccoCurrentlyOrOcc;
    @JsonProperty("moreThanTwentyOrChewTen")
    private String moreThanTwentyOrChewTen;
    @JsonProperty("drinkLiquorThreeDaysOrMore")
    private String drinkLiquorThreeDaysOrMore;
    @JsonProperty("everAdvisedToQuit")
    private String everAdvisedToQuit;
    @JsonProperty("isConsumeAnyIllegalDrugs")
    private String isConsumeAnyIllegalDrugs;
    @JsonProperty("specifyConsDetails")
    private String specifyConsDetails;
    @JsonProperty("isCigaretteSelected")
    private String isCigaretteSelected;
    @JsonProperty("cigaretteQuantity")
    private String cigaretteQuantity;
    @JsonProperty("cigaretteFrequency")
    private String cigaretteFrequency;
    @JsonProperty("isSachetGutkaPanSelected")
    private String isSachetGutkaPanSelected;
    @JsonProperty("sachetGutkaPanQuantity")
    private String sachetGutkaPanQuantity;
    @JsonProperty("sachetGutkaPanFrequency")
    private String sachetGutkaPanFrequency;
    @JsonProperty("everConsumedAlcohol")
    private String everConsumedAlcohol;
    @JsonProperty("isBeerSelected")
    private String isBeerSelected;
    @JsonProperty("beerQuantity")
    private String beerQuantity;
    @JsonProperty("beerFrequency")
    private String beerFrequency;
    @JsonProperty("isWineSelected")
    private String isWineSelected;
    @JsonProperty("wineQuantity")
    private String wineQuantity;
    @JsonProperty("wineFrequency")
    private String wineFrequency;
    @JsonProperty("isHardLiquorSelected")
    private String isHardLiquorSelected;
    @JsonProperty("hardLiquorQuantity")
    private String hardLiquorQuantity;
    @JsonProperty("hardLiquorFrequency")
    private String hardLiquorFrequency;

    public String getEverConsumeType() {
        return everConsumeType;
    }

    public void setEverConsumeType(String everConsumeType) {
        this.everConsumeType = everConsumeType;
    }

    public String getConsumeTobaccoCurrentlyOrOcc() {
        return consumeTobaccoCurrentlyOrOcc;
    }

    public void setConsumeTobaccoCurrentlyOrOcc(String consumeTobaccoCurrentlyOrOcc) {
        this.consumeTobaccoCurrentlyOrOcc = consumeTobaccoCurrentlyOrOcc;
    }

    public String getMoreThanTwentyOrChewTen() {
        return moreThanTwentyOrChewTen;
    }

    public void setMoreThanTwentyOrChewTen(String moreThanTwentyOrChewTen) {
        this.moreThanTwentyOrChewTen = moreThanTwentyOrChewTen;
    }

    public String getDrinkLiquorThreeDaysOrMore() {
        return drinkLiquorThreeDaysOrMore;
    }

    public void setDrinkLiquorThreeDaysOrMore(String drinkLiquorThreeDaysOrMore) {
        this.drinkLiquorThreeDaysOrMore = drinkLiquorThreeDaysOrMore;
    }

    public String getEverAdvisedToQuit() {
        return everAdvisedToQuit;
    }

    public void setEverAdvisedToQuit(String everAdvisedToQuit) {
        this.everAdvisedToQuit = everAdvisedToQuit;
    }

    public String getIsConsumeAnyIllegalDrugs() {
        return isConsumeAnyIllegalDrugs;
    }

    public void setIsConsumeAnyIllegalDrugs(String isConsumeAnyIllegalDrugs) {
        this.isConsumeAnyIllegalDrugs = isConsumeAnyIllegalDrugs;
    }

    public String getSpecifyConsDetails() {
        return specifyConsDetails;
    }

    public void setSpecifyConsDetails(String specifyConsDetails) {
        this.specifyConsDetails = specifyConsDetails;
    }

    public String getCigaretteQuantity() {
        return cigaretteQuantity;
    }

    public void setCigaretteQuantity(String cigaretteQuantity) {
        this.cigaretteQuantity = cigaretteQuantity;
    }

    public String getCigaretteFrequency() {
        return cigaretteFrequency;
    }

    public void setCigaretteFrequency(String cigaretteFrequency) {
        this.cigaretteFrequency = cigaretteFrequency;
    }

    public String getIsSachetGutkaPanSelected() {
        return isSachetGutkaPanSelected;
    }

    public void setIsSachetGutkaPanSelected(String isSachetGutkaPanSelected) {
        this.isSachetGutkaPanSelected = isSachetGutkaPanSelected;
    }

    public String getSachetGutkaPanQuantity() {
        return sachetGutkaPanQuantity;
    }

    public void setSachetGutkaPanQuantity(String sachetGutkaPanQuantity) {
        this.sachetGutkaPanQuantity = sachetGutkaPanQuantity;
    }

    public String getSachetGutkaPanFrequency() {
        return sachetGutkaPanFrequency;
    }

    public void setSachetGutkaPanFrequency(String sachetGutkaPanFrequency) {
        this.sachetGutkaPanFrequency = sachetGutkaPanFrequency;
    }

    public String getEverConsumedAlcohol() {
        return everConsumedAlcohol;
    }

    public void setEverConsumedAlcohol(String everConsumedAlcohol) {
        this.everConsumedAlcohol = everConsumedAlcohol;
    }

    public String getIsBeerSelected() {
        return isBeerSelected;
    }

    public void setIsBeerSelected(String isBeerSelected) {
        this.isBeerSelected = isBeerSelected;
    }

    public String getBeerQuantity() {
        return beerQuantity;
    }

    public void setBeerQuantity(String beerQuantity) {
        this.beerQuantity = beerQuantity;
    }

    public String getBeerFrequency() {
        return beerFrequency;
    }

    public void setBeerFrequency(String beerFrequency) {
        this.beerFrequency = beerFrequency;
    }

    public String getIsWineSelected() {
        return isWineSelected;
    }

    public void setIsWineSelected(String isWineSelected) {
        this.isWineSelected = isWineSelected;
    }

    public String getWineQuantity() {
        return wineQuantity;
    }

    public void setWineQuantity(String wineQuantity) {
        this.wineQuantity = wineQuantity;
    }

    public String getWineFrequency() {
        return wineFrequency;
    }

    public void setWineFrequency(String wineFrequency) {
        this.wineFrequency = wineFrequency;
    }

    public String getIsHardLiquorSelected() {
        return isHardLiquorSelected;
    }

    public void setIsHardLiquorSelected(String isHardLiquorSelected) {
        this.isHardLiquorSelected = isHardLiquorSelected;
    }

    public String getHardLiquorQuantity() {
        return hardLiquorQuantity;
    }

    public void setHardLiquorQuantity(String hardLiquorQuantity) {
        this.hardLiquorQuantity = hardLiquorQuantity;
    }

    public String getHardLiquorFrequency() {
        return hardLiquorFrequency;
    }

    public void setHardLiquorFrequency(String hardLiquorFrequency) {
        this.hardLiquorFrequency = hardLiquorFrequency;
    }

    public String getIsCigaretteSelected() {
        return isCigaretteSelected;
    }

    public void setIsCigaretteSelected(String isCigaretteSelected) {
        this.isCigaretteSelected = isCigaretteSelected;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SpecifyHabit{" +
                "everConsumeType='" + everConsumeType + '\'' +
                ", consumeTobaccoCurrentlyOrOcc='" + consumeTobaccoCurrentlyOrOcc + '\'' +
                ", moreThanTwentyOrChewTen='" + moreThanTwentyOrChewTen + '\'' +
                ", drinkLiquorThreeDaysOrMore='" + drinkLiquorThreeDaysOrMore + '\'' +
                ", everAdvisedToQuit='" + everAdvisedToQuit + '\'' +
                ", isConsumeAnyIllegalDrugs='" + isConsumeAnyIllegalDrugs + '\'' +
                ", specifyConsDetails='" + specifyConsDetails + '\'' +
                '}';
    }
}
