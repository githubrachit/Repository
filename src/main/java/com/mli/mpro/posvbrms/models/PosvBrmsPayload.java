package com.mli.mpro.posvbrms.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PosvBrmsPayload {


    @JsonProperty("mortalityAiResponse")
    private String mortalityAiResponse;

    @JsonProperty("persistencyAiResponse")
    private String persistencyAiResponse;

    @JsonProperty("proposerIncome")
    private String proposerIncome;

    @JsonProperty("proposerEducation")
    private String proposerEducation;

    @JsonProperty("proposerOccupation")
    private String proposerOccupation;

    @JsonProperty("cxAiResponse")
    private String cxAiResponse;

    @JsonProperty("sourcingSystem")
    private String sourcingSystem;

    @JsonProperty("nisSourced")
    private String nisSourced;

    @JsonProperty("telesales")
    private String telesales;

    @JsonProperty("physicalJourney")
    private String physicalJourney;

    @JsonProperty("formType")
    private String formType;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("channelDefence")
    private String channelDefence;

    @JsonProperty("channelCat")
    private String channelCat;

    @JsonProperty("npsIndicator")
    private String npsIndicator;

    @JsonProperty("industry")
    private String industry;

    @JsonProperty("proposerAge")
    private String proposerAge;

    @JsonProperty("insuredAge")
    private String insuredAge;

    @JsonProperty("urmuResponse")
    private String urmuResponse;

    @JsonProperty("customerClassificationCode")
    private String customerClassificationCode;

    @JsonProperty("bankingSince")
    private String bankingSince;

    @JsonProperty("sourcingType")
    private String sourcingType;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("solId")
    private String solId;

    @JsonProperty("sellerPersistency")
    private String sellerPersistency;

    @JsonProperty("sellerCode")
    private String sellerCode;

    @JsonProperty("productType")
    private String productType;

    @JsonProperty("afyp")
    private String afyp;

    @JsonProperty("ppt")
    private String ppt;

    @JsonProperty("proposerNationality")
    private String proposerNationality;

    @JsonProperty("tag1")
    private String tag1;

    @JsonProperty("tag2")
    private String tag2;

    @JsonProperty("tag3")
    private String tag3;

    @JsonProperty("tag4")
    private String tag4;

    @JsonProperty("tag5")
    private String tag5;

    @JsonProperty("tag6")
    private String tag6;

    @JsonProperty("tag7")
    private String tag7;

    @JsonProperty("tag8")
    private String tag8;

    @JsonProperty("tag9")
    private String tag9;

    @JsonProperty("tag10")
    private String tag10;

    @JsonProperty("mortalityAiScore")
    private String mortalityAiScore;

    @JsonProperty("persistencyAiScore")
    private String persistencyAiScore;


    public String getMortalityAiResponse() {
        return mortalityAiResponse;
    }

    public void setMortalityAiResponse(String mortalityAiResponse) {
        this.mortalityAiResponse = mortalityAiResponse;
    }

    public String getPersistencyAiResponse() {
        return persistencyAiResponse;
    }

    public void setPersistencyAiResponse(String persistencyAiResponse) {
        this.persistencyAiResponse = persistencyAiResponse;
    }

    public String getProposerIncome() {
        return proposerIncome;
    }

    public void setProposerIncome(String proposerIncome) {
        this.proposerIncome = proposerIncome;
    }

    public String getProposerEducation() {
        return proposerEducation;
    }

    public void setProposerEducation(String proposerEducation) {
        this.proposerEducation = proposerEducation;
    }

    public String getProposerOccupation() {
        return proposerOccupation;
    }

    public void setProposerOccupation(String proposerOccupation) {
        this.proposerOccupation = proposerOccupation;
    }

    public String getCxAiResponse() {
        return cxAiResponse;
    }

    public void setCxAiResponse(String cxAiResponse) {
        this.cxAiResponse = cxAiResponse;
    }

    public String getSourcingSystem() {
        return sourcingSystem;
    }

    public void setSourcingSystem(String sourcingSystem) {
        this.sourcingSystem = sourcingSystem;
    }

    public String getNisSourced() {
        return nisSourced;
    }

    public void setNisSourced(String nisSourced) {
        this.nisSourced = nisSourced;
    }

    public String getTelesales() {
        return telesales;
    }

    public void setTelesales(String telesales) {
        this.telesales = telesales;
    }

    public String getPhysicalJourney() {
        return physicalJourney;
    }

    public void setPhysicalJourney(String physicalJourney) {
        this.physicalJourney = physicalJourney;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelDefence() {
        return channelDefence;
    }

    public void setChannelDefence(String channelDefence) {
        this.channelDefence = channelDefence;
    }

    public String getChannelCat() {
        return channelCat;
    }

    public void setChannelCat(String channelCat) {
        this.channelCat = channelCat;
    }

    public String getNpsIndicator() {
        return npsIndicator;
    }

    public void setNpsIndicator(String npsIndicator) {
        this.npsIndicator = npsIndicator;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getProposerAge() {
        return proposerAge;
    }

    public void setProposerAge(String proposerAge) {
        this.proposerAge = proposerAge;
    }

    public String getUrmuResponse() {
        return urmuResponse;
    }

    public void setUrmuResponse(String urmuResponse) {
        this.urmuResponse = urmuResponse;
    }

    public String getCustomerClassificationCode() {
        return customerClassificationCode;
    }

    public void setCustomerClassificationCode(String customerClassificationCode) {
        this.customerClassificationCode = customerClassificationCode;
    }

    public String getSourcingType() {
        return sourcingType;
    }

    public void setSourcingType(String sourcingType) {
        this.sourcingType = sourcingType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getSolId() {
        return solId;
    }

    public void setSolId(String solId) {
        this.solId = solId;
    }

    public String getSellerPersistency() {
        return sellerPersistency;
    }

    public void setSellerPersistency(String sellerPersistency) {
        this.sellerPersistency = sellerPersistency;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getAfyp() {
        return afyp;
    }

    public void setAfyp(String afyp) {
        this.afyp = afyp;
    }

    public String getPpt() {
        return ppt;
    }

    public void setPpt(String ppt) {
        this.ppt = ppt;
    }

    public String getProposerNationality() {
        return proposerNationality;
    }

    public void setProposerNationality(String proposerNationality) {
        this.proposerNationality = proposerNationality;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4() {
        return tag4;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5() {
        return tag5;
    }

    public void setTag5(String tag5) {
        this.tag5 = tag5;
    }

    public String getTag6() {
        return tag6;
    }

    public void setTag6(String tag6) {
        this.tag6 = tag6;
    }

    public String getTag7() {
        return tag7;
    }

    public void setTag7(String tag7) {
        this.tag7 = tag7;
    }

    public String getTag8() {
        return tag8;
    }

    public void setTag8(String tag8) {
        this.tag8 = tag8;
    }

    public String getTag9() {
        return tag9;
    }

    public void setTag9(String tag9) {
        this.tag9 = tag9;
    }

    public String getTag10() {
        return tag10;
    }

    public void setTag10(String tag10) {
        this.tag10 = tag10;
    }

    public String getMortalityAiScore() {
        return mortalityAiScore;
    }

    public void setMortalityAiScore(String mortalityAiScore) {
        this.mortalityAiScore = mortalityAiScore;
    }

    public String getPersistencyAiScore() {
        return persistencyAiScore;
    }

    public void setPersistencyAiScore(String persistencyAiScore) {
        this.persistencyAiScore = persistencyAiScore;
    }

    public String getBankingSince() {
        return bankingSince;
    }

    public void setBankingSince(String bankingSince) {
        this.bankingSince = bankingSince;
    }

    public String getInsuredAge() {
        return insuredAge;
    }

    public void setInsuredAge(String insuredAge) {
        this.insuredAge = insuredAge;
    }

    @Override
    public String toString() {
        return "PosvBrmsPayload{" +
                "mortalityAiResponse='" + mortalityAiResponse + '\'' +
                ", persistencyAiResponse='" + persistencyAiResponse + '\'' +
                ", proposerIncome='" + proposerIncome + '\'' +
                ", proposerEducation='" + proposerEducation + '\'' +
                ", proposerOccupation='" + proposerOccupation + '\'' +
                ", cxAiResponse='" + cxAiResponse + '\'' +
                ", sourcingSystem='" + sourcingSystem + '\'' +
                ", nisSourced='" + nisSourced + '\'' +
                ", telesales='" + telesales + '\'' +
                ", physicalJourney='" + physicalJourney + '\'' +
                ", formType='" + formType + '\'' +
                ", channel='" + channel + '\'' +
                ", channelDefence='" + channelDefence + '\'' +
                ", channelCat='" + channelCat + '\'' +
                ", npsIndicator='" + npsIndicator + '\'' +
                ", industry='" + industry + '\'' +
                ", proposerAge='" + proposerAge + '\'' +
                ", urmuResponse='" + urmuResponse + '\'' +
                ", customerClassificationCode='" + customerClassificationCode + '\'' +
                ", dateOfAccountOpening='" + bankingSince + '\'' +
                ", sourcingType='" + sourcingType + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", solId='" + solId + '\'' +
                ", sellerPersistency='" + sellerPersistency + '\'' +
                ", sellerCode='" + sellerCode + '\'' +
                ", productType='" + productType + '\'' +
                ", afyp='" + afyp + '\'' +
                ", ppt='" + ppt + '\'' +
                ", proposerNationality='" + proposerNationality + '\'' +
                ", tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", tag3='" + tag3 + '\'' +
                ", tag4='" + tag4 + '\'' +
                ", tag5='" + tag5 + '\'' +
                ", tag6='" + tag6 + '\'' +
                ", tag7='" + tag7 + '\'' +
                ", tag8='" + tag8 + '\'' +
                ", tag9='" + tag9 + '\'' +
                ", tag10='" + tag10 + '\'' +
                ", mortalityAiScore='" + mortalityAiScore + '\'' +
                ", persistencyAiScore='" + persistencyAiScore + '\'' +
                ", insuredAge='" + insuredAge + '\'' +
                '}';
    }
}
