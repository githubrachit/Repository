package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class IibDetails {

    @JsonProperty("policyNo")
    private String policyNo;
    @Sensitive(MaskType.NAME)
    @JsonProperty("insurerName")
    private String insurerName;
    @JsonProperty("policyIssuanceDate")
    private String policyIssuanceDate;
    @JsonProperty("policyStatus")
    private String policyStatus;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("policySumAssured")
    private String policySumAssured;
    @JsonProperty("policyType")
    private String policyType;
    @JsonProperty("isConsentProvided")
    private String isConsentProvided;
    @JsonProperty("policyRemarks")
    private String policyRemarks;


    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    public String getPolicyIssuanceDate() {
        return policyIssuanceDate;
    }

    public void setPolicyIssuanceDate(String policyIssuanceDate) {
        this.policyIssuanceDate = policyIssuanceDate;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getPolicySumAssured() {
        return policySumAssured;
    }

    public void setPolicySumAssured(String policySumAssured) {
        this.policySumAssured = policySumAssured;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getIsConsentProvided() {
        return isConsentProvided;
    }

    public void setIsConsentProvided(String isConsentProvided) {
        this.isConsentProvided = isConsentProvided;
    }

    public String getPolicyRemarks() {
        return policyRemarks;
    }

    public void setPolicyRemarks(String policyRemarks) {
        this.policyRemarks = policyRemarks;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "IibDetails{" +
                "policyNo='" + policyNo + '\'' +
                ", insurerName='" + insurerName + '\'' +
                ", policyIssuanceDate='" + policyIssuanceDate + '\'' +
                ", policyStatus='" + policyStatus + '\'' +
                ", policySumAssured='" + policySumAssured + '\'' +
                ", policyType='" + policyType + '\'' +
                ", isConsentProvided=" + isConsentProvided +
                ", policyRemarks='" + policyRemarks + '\'' +
                '}';
    }
}
