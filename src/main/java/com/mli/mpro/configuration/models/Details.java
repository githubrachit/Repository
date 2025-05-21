package com.mli.mpro.configuration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

public class Details {
    @JsonProperty("afypDetails")
    private AfypDetails afypDetails;
    @JsonProperty("replacementSaleDetails")
    private ReplacementSaleDetails replacementSaleDetails;
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("plan")
    private String plan;
    @JsonProperty("uin")
    private String uin;
    @Sensitive(MaskType.NAME)
    @JsonProperty("spName")
    private String spName;
    @JsonProperty("designation")
    private String designation;
    @JsonProperty("employeeCode")
    private String employeeCode;
    @JsonProperty("dueDate")
    private String dueDate;
    @JsonProperty("totalRequireModelPremium")
    private String totalRequireModelPremium;
    @Sensitive(MaskType.NAME)
    @JsonProperty("proposerName")
    private String proposerName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("insurerName")
    private String insurerName;
    @JsonProperty("uniqueId")
    private  String uniqueId;


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public AfypDetails getAfypDetails() {
        return afypDetails;
    }

    public void setAfypDetails(AfypDetails afypDetails) {
        this.afypDetails = afypDetails;
    }

    public ReplacementSaleDetails getReplacementSaleDetails() {
        return replacementSaleDetails;
    }

    public void setReplacementSaleDetails(ReplacementSaleDetails replacementSaleDetails) {
        this.replacementSaleDetails = replacementSaleDetails;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotalRequireModelPremium() {
        return totalRequireModelPremium;
    }

    public void setTotalRequireModelPremium(String totalRequireModelPremium) {
        this.totalRequireModelPremium = totalRequireModelPremium;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    @Override
    public String toString() {
        return "Details{" +
                "afypDetails=" + afypDetails +
                ", replacementSaleDtails=" + replacementSaleDetails +
                ", policyNumber='" + policyNumber + '\'' +
                ", plan='" + plan + '\'' +
                ", uin='" + uin + '\'' +
                ", spName='" + spName + '\'' +
                ", designation='" + designation + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", totalRequireModelPremium='" + totalRequireModelPremium + '\'' +
                ", proposerName='" + proposerName + '\'' +
                ", insurerName='" + insurerName + '\'' +
                '}';
    }
}

