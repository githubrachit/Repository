package com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class PremAndCommissionDtls {

    @JsonProperty("month")
    private String month;
    @JsonProperty("paidCases")
    private String paidCases;
    @JsonProperty("firstYearPremium")
    private String firstYearPremium;
    @JsonProperty("annualFirstYearPremium")
    private String annualFirstYearPremium;
    @JsonProperty("modalFirstYearPremium")
    private String modalFirstYearPremium;
    @JsonProperty("firstYearCommission")
    private String firstYearCommission;
    @JsonProperty("renewalYearCommission")
    private String renewalYearCommission;
    @JsonProperty("weightageFirstYearPremium")
    private String weightageFirstYearPremium;
    @JsonProperty("compensation")
    private String compensation;
    @JsonProperty("agentPersistency")
    private String agentPersistency;
    @JsonProperty("propAgentPersistency13M")
    private String propAgentPersistency13M;
    @JsonProperty("propAgentPersistency25M")
    private String propAgentPersistency25M;
    @JsonProperty("propAgentPersistencyComb13M25M")
    private String propAgentPersistencyComb13M25M;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPaidCases() {
        return paidCases;
    }

    public void setPaidCases(String paidCases) {
        this.paidCases = paidCases;
    }

    public String getFirstYearPremium() {
        return firstYearPremium;
    }

    public void setFirstYearPremium(String firstYearPremium) {
        this.firstYearPremium = firstYearPremium;
    }

    public String getAnnualFirstYearPremium() {
        return annualFirstYearPremium;
    }

    public void setAnnualFirstYearPremium(String annualFirstYearPremium) {
        this.annualFirstYearPremium = annualFirstYearPremium;
    }

    public String getModalFirstYearPremium() {
        return modalFirstYearPremium;
    }

    public void setModalFirstYearPremium(String modalFirstYearPremium) {
        this.modalFirstYearPremium = modalFirstYearPremium;
    }

    public String getRenewalYearCommission() {
        return renewalYearCommission;
    }

    public void setRenewalYearCommission(String renewalYearCommission) {
        this.renewalYearCommission = renewalYearCommission;
    }

    public String getWeightageFirstYearPremium() {
        return weightageFirstYearPremium;
    }

    public void setWeightageFirstYearPremium(String weightageFirstYearPremium) {
        this.weightageFirstYearPremium = weightageFirstYearPremium;
    }

    public String getFirstYearCommission() {
        return firstYearCommission;
    }

    public void setFirstYearCommission(String firstYearCommission) {
        this.firstYearCommission = firstYearCommission;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getAgentPersistency() {
        return agentPersistency;
    }

    public void setAgentPersistency(String agentPersistency) {
        this.agentPersistency = agentPersistency;
    }

    public String getPropAgentPersistency13M() {
        return propAgentPersistency13M;
    }

    public void setPropAgentPersistency13M(String propAgentPersistency13M) {
        this.propAgentPersistency13M = propAgentPersistency13M;
    }

    public String getPropAgentPersistency25M() {
        return propAgentPersistency25M;
    }

    public void setPropAgentPersistency25M(String propAgentPersistency25M) {
        this.propAgentPersistency25M = propAgentPersistency25M;
    }

    public String getPropAgentPersistencyComb13M25M() {
        return propAgentPersistencyComb13M25M;
    }

    public void setPropAgentPersistencyComb13M25M(String propAgentPersistencyComb13M25M) {
        this.propAgentPersistencyComb13M25M = propAgentPersistencyComb13M25M;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "PremAndCommissionDtls{" +
                "month='" + month + '\'' +
                ", paidCases='" + paidCases + '\'' +
                ", firstYearPremium='" + firstYearPremium + '\'' +
                ", annualFirstYearPremium='" + annualFirstYearPremium + '\'' +
                ", modalFirstYearPremium='" + modalFirstYearPremium + '\'' +
                ", firstYearCommission='" + firstYearCommission + '\'' +
                ", renewalYearCommission='" + renewalYearCommission + '\'' +
                ", weightageFirstYearPremium='" + weightageFirstYearPremium + '\'' +
                ", compensation='" + compensation + '\'' +
                ", agentPersistency='" + agentPersistency + '\'' +
                ", propAgentPersistency13M='" + propAgentPersistency13M + '\'' +
                ", propAgentPersistency25M='" + propAgentPersistency25M + '\'' +
                ", propAgentPersistencyComb13M25M='" + propAgentPersistencyComb13M25M + '\'' +
                '}';
    }
}
