package com.mli.mpro.brmsBroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class Payload {
    @Sensitive(MaskType.BRANCH_CODE)
    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("sellerDesignationCode")
    private String sellerDesignationCode;

    @JsonProperty("campaignID")
    private String campaignID;

    @JsonProperty("channelIndicatorCAT")
    private String channelIndicatorCAT;

    @JsonProperty("channelIndicatorDefence")
    private String channelIndicatorDefence;

    @JsonProperty("customerClasificationCode")
    private String customerClasificationCode;

    @JsonProperty("dateOfAccountOpening")
    private String dateOfAccountOpening;

    @JsonProperty("daysSinceAgentOnboarding")
    private String daysSinceAgentOnboarding;

    @JsonProperty("locationID")
    private String locationID;

    @JsonProperty("nisSource")
    private String nisSource;

    @JsonProperty("sellerCode")
    private String sellerCode;

    @JsonProperty("sellerPersistancy")
    private String sellerPersistancy;

    @JsonProperty("sourcingSystem")
    private String sourcingSystem;

    @JsonProperty("telesales")
    private String telesales;

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

    @JsonProperty("channelIndicatorJ3")
    private String channelIndicatorJ3;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSellerDesignationCode() {
        return sellerDesignationCode;
    }

    public void setSellerDesignationCode(String sellerDesignationCode) {
        this.sellerDesignationCode = sellerDesignationCode;
    }

    public String getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(String campaignID) {
        this.campaignID = campaignID;
    }

    public String getChannelIndicatorCAT() {
        return channelIndicatorCAT;
    }

    public void setChannelIndicatorCAT(String channelIndicatorCAT) {
        this.channelIndicatorCAT = channelIndicatorCAT;
    }

    public String getChannelIndicatorDefence() {
        return channelIndicatorDefence;
    }

    public void setChannelIndicatorDefence(String channelIndicatorDefence) {
        this.channelIndicatorDefence = channelIndicatorDefence;
    }

    public String getCustomerClasificationCode() {
        return customerClasificationCode;
    }

    public void setCustomerClasificationCode(String customerClasificationCode) {
        this.customerClasificationCode = customerClasificationCode;
    }

    public String getDateOfAccountOpening() {
        return dateOfAccountOpening;
    }

    public void setDateOfAccountOpening(String dateOfAccountOpening) {
        this.dateOfAccountOpening = dateOfAccountOpening;
    }

    public String getDaysSinceAgentOnboarding() {
        return daysSinceAgentOnboarding;
    }

    public void setDaysSinceAgentOnboarding(String daysSinceAgentOnboarding) {
        this.daysSinceAgentOnboarding = daysSinceAgentOnboarding;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getNisSource() {
        return nisSource;
    }

    public void setNisSource(String nisSource) {
        this.nisSource = nisSource;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSellerPersistancy() {
        return sellerPersistancy;
    }

    public void setSellerPersistancy(String sellerPersistancy) {
        this.sellerPersistancy = sellerPersistancy;
    }

    public String getSourcingSystem() {
        return sourcingSystem;
    }

    public void setSourcingSystem(String sourcingSystem) {
        this.sourcingSystem = sourcingSystem;
    }

    public String getTelesales() {
        return telesales;
    }

    public void setTelesales(String telesales) {
        this.telesales = telesales;
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

    public String getChannelIndicatorJ3() {
        return channelIndicatorJ3;
    }

    public void setChannelIndicatorJ3(String channelIndicatorJ3) {
        this.channelIndicatorJ3 = channelIndicatorJ3;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "Payload{" +
                "branchCode='" + branchCode + '\'' +
                ", channel='" + channel + '\'' +
                ", sellerDesignationCode='" + sellerDesignationCode + '\'' +
                ", campaignID='" + campaignID + '\'' +
                ", channelIndicatorCAT='" + channelIndicatorCAT + '\'' +
                ", channelIndicatorDefence='" + channelIndicatorDefence + '\'' +
                ", customerClasificationCode='" + customerClasificationCode + '\'' +
                ", dateOfAccountOpening='" + dateOfAccountOpening + '\'' +
                ", daysSinceAgentOnboarding='" + daysSinceAgentOnboarding + '\'' +
                ", locationID='" + locationID + '\'' +
                ", nisSource='" + nisSource + '\'' +
                ", sellerCode='" + sellerCode + '\'' +
                ", sellerPersistancy='" + sellerPersistancy + '\'' +
                ", sourcingSystem='" + sourcingSystem + '\'' +
                ", telesales='" + telesales + '\'' +
                ", tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", tag3='" + tag3 + '\'' +
                ", tag4='" + tag4 + '\'' +
                ", tag5='" + tag5 + '\'' +
                ", channelIndicatorJ3='" + channelIndicatorJ3 + '\'' +
                '}';
    }
}
