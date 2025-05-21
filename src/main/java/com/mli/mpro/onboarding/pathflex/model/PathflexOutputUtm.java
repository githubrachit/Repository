package com.mli.mpro.onboarding.pathflex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PathflexOutputUtm {

    @JsonProperty("staticagentid")
    private String staticAgentId;
    @JsonProperty("partnerlogodisplay")
    private String partnerLogoDisplay;
    @JsonProperty("advisorid")
    private String advisorId;
    @JsonProperty("lesecondarychannel")
    private String leSecondaryChannel;
    @JsonProperty("journeytype")
    private String journeyType;
    @JsonProperty("lechannelname")
    private String leChannelName;
    @JsonProperty("continuejourneyurl")
    private String continueJourneyUrl;
    @JsonProperty("channelcode")
    private String channelCode;
    @JsonProperty("lechannelcode")
    private String leChannelCode;
    @JsonProperty("productenabled")
    private String productEnabled;
    @JsonProperty("renewaltype")
    private String renewalType;
    @JsonProperty("customerdatafetch")
    private String customerDataFetch;
    @JsonProperty("paymentmode")
    private String paymentMode;
    @JsonProperty("formtype")
    private String formType;
    @JsonProperty("jvremoval")
    private String jvRemoval;
    @JsonProperty("pasaenablement")
    private String pasaEnablement;
    @JsonProperty("advisoridverbiage")
    private String advisorIdVerbiage;
    @JsonProperty("journeytypeproductcode")
    private String journeyTypeProductCode;
    @JsonProperty("posvapplicability")
    private String posvApplicability;
    @JsonProperty("paymenttype")
    private String paymentType;
    @JsonProperty("smsdailylimit")
    private String smsDailyLimit;
    @JsonProperty("smsinstancelimit")
    private String smsInstanceLimit;
    @JsonProperty("cooldownperiod")
    private String coolDownPeriod;
    @JsonProperty("sourcechannel")
    private String sourceChannel;
    @JsonProperty("diyjourneytype")
    private String diyJourneyType;
    @JsonProperty("dolphinpushchannelcode")
    private String dolphinPushChannelCode;
    @JsonProperty("influencerchannel")
    private String influencerChannel;
    @JsonProperty("psmgrid")
    private String psmGrid;
    @JsonProperty("productcommissionability")
    private String productCommissionability;

    @JsonProperty("advisoridviacampaignid")
    private String advisoridviacampaignid;

    @JsonProperty("localjourneyresume")
    private String localjourneyresume;

    public String getLocalJourneyResume() {
        return localjourneyresume;
    }

    public void setLocalJourneyResume(String localjourneyresume) {
        this.localjourneyresume = localjourneyresume;
    }

    public String getAdvisoridviacampaignid() {
        return advisoridviacampaignid;
    }

    public void setAdvisoridviacampaignid(String advisoridviacampaignid) {
        this.advisoridviacampaignid = advisoridviacampaignid;
    }

    public String getStaticAgentId() {
        return staticAgentId;
    }

    public void setStaticAgentId(String staticAgentId) {
        this.staticAgentId = staticAgentId;
    }

    public String getPartnerLogoDisplay() {
        return partnerLogoDisplay;
    }

    public void setPartnerLogoDisplay(String partnerLogoDisplay) {
        this.partnerLogoDisplay = partnerLogoDisplay;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getLeSecondaryChannel() {
        return leSecondaryChannel;
    }

    public void setLeSecondaryChannel(String leSecondaryChannel) {
        this.leSecondaryChannel = leSecondaryChannel;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getLeChannelName() {
        return leChannelName;
    }

    public void setLeChannelName(String leChannelName) {
        this.leChannelName = leChannelName;
    }

    public String getContinueJourneyUrl() {
        return continueJourneyUrl;
    }

    public void setContinueJourneyUrl(String continueJourneyUrl) {
        this.continueJourneyUrl = continueJourneyUrl;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getLeChannelCode() {
        return leChannelCode;
    }

    public void setLeChannelCode(String leChannelCode) {
        this.leChannelCode = leChannelCode;
    }

    public String getProductEnabled() {
        return productEnabled;
    }

    public void setProductEnabled(String productEnabled) {
        this.productEnabled = productEnabled;
    }

    public String getRenewalType() {
        return renewalType;
    }

    public void setRenewalType(String renewalType) {
        this.renewalType = renewalType;
    }

    public String getCustomerDataFetch() {
        return customerDataFetch;
    }

    public void setCustomerDataFetch(String customerDataFetch) {
        this.customerDataFetch = customerDataFetch;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getJvRemoval() { return jvRemoval; }

    public void setJvRemoval(String jvRemoval) { this.jvRemoval = jvRemoval; }

    public String getAdvisorIdVerbiage() {
        return advisorIdVerbiage;
    }

    public void setAdvisorIdVerbiage(String advisorIdVerbiage) {
        this.advisorIdVerbiage = advisorIdVerbiage;
    }

    public String getJourneyTypeProductCode() {
        return journeyTypeProductCode;
    }

    public void setJourneyTypeProductCode(String journeyTypeProductCode) {
        this.journeyTypeProductCode = journeyTypeProductCode;
    }

    public String getPosvApplicability() {
        return posvApplicability;
    }

    public void setPosvApplicability(String posvApplicability) {
        this.posvApplicability = posvApplicability;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSmsDailyLimit() {
        return smsDailyLimit;
    }

    public void setSmsDailyLimit(String smsDailyLimit) {
        this.smsDailyLimit = smsDailyLimit;
    }

    public String getSmsInstanceLimit() {
        return smsInstanceLimit;
    }

    public void setSmsInstanceLimit(String smsInstanceLimit) {
        this.smsInstanceLimit = smsInstanceLimit;
    }

    public String getCoolDownPeriod() {
        return coolDownPeriod;
    }

    public void setCoolDownPeriod(String coolDownPeriod) {
        this.coolDownPeriod = coolDownPeriod;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getDiyJourneyType() {
        return diyJourneyType;
    }

    public void setDiyJourneyType(String diyJourneyType) {
        this.diyJourneyType = diyJourneyType;
    }

    public String getDolphinPushChannelCode() {
        return dolphinPushChannelCode;
    }

    public void setDolphinPushChannelCode(String dolphinPushChannelCode) {
        this.dolphinPushChannelCode = dolphinPushChannelCode;
    }

    public String getInfluencerChannel() {
        return influencerChannel;
    }

    public void setInfluencerChannel(String influencerChannel) {
        this.influencerChannel = influencerChannel;
    }

    public String getPsmGrid() {
        return psmGrid;
    }

    public void setPsmGrid(String psmGrid) {
        this.psmGrid = psmGrid;
    }

    public String getProductCommissionability() {
        return productCommissionability;
    }

    public void setProductCommissionability(String productCommissionability) {
        this.productCommissionability = productCommissionability;
    }
    public String getPasaEnablement() { return pasaEnablement; }

    public void setPasaEnablement(String pasaEnablement) { this.pasaEnablement = pasaEnablement; }

    @Override
    public String toString() {
        return "PathflexOutputUtm{" +
                "staticAgentId='" + staticAgentId + '\'' +
                ", partnerLogoDisplay='" + partnerLogoDisplay + '\'' +
                ", advisorId='" + advisorId + '\'' +
                ", leSecondaryChannel='" + leSecondaryChannel + '\'' +
                ", journeyType='" + journeyType + '\'' +
                ", leChannelName='" + leChannelName + '\'' +
                ", continueJourneyUrl='" + continueJourneyUrl + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", leChannelCode='" + leChannelCode + '\'' +
                ", productEnabled='" + productEnabled + '\'' +
                ", renewalType='" + renewalType + '\'' +
                ", customerDataFetch='" + customerDataFetch + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", formType='" + formType + '\'' +
                ", jvRemoval='" + jvRemoval + '\'' +
                ", pasaEnablement='" + pasaEnablement + '\'' +
                ", advisorIdVerbiage='" + advisorIdVerbiage + '\'' +
                ", journeyTypeProductCode='" + journeyTypeProductCode + '\'' +
                ", posvApplicability='" + posvApplicability + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", smsDailyLimit='" + smsDailyLimit + '\'' +
                ", smsInstanceLimit='" + smsInstanceLimit + '\'' +
                ", coolDownPeriod='" + coolDownPeriod + '\'' +
                ", sourceChannel='" + sourceChannel + '\'' +
                ", diyJourneyType='" + diyJourneyType + '\'' +
                ", dolphinPushChannelCode='" + dolphinPushChannelCode + '\'' +
                ", influencerChannel='" + influencerChannel + '\'' +
                ", psmGrid='" + psmGrid + '\'' +
                ", productCommissionability='" + productCommissionability + '\'' +
                ", advisoridviacampaignid='" + advisoridviacampaignid + '\'' +
                ",localjourneyresume='"+localjourneyresume+'\''+
                '}';
    }

}
