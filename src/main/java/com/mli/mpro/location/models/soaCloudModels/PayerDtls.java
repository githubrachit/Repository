package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayerDtls {
    @JsonProperty("policyNo")
    private String policyNo;

    @JsonProperty("cvgNum")
    private String cvgNum;

    @JsonProperty("planName")
    private String planName;

    @JsonProperty("cvgSumAssured")
    private String cvgSumAssured;

    @JsonProperty("cvgStatusCd")
    private String cvgStatusCd;

    @JsonProperty("relwithOwnClient")
    private String relwithOwnClient;

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getCvgNum() {
        return cvgNum;
    }

    public void setCvgNum(String cvgNum) {
        this.cvgNum = cvgNum;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCvgSumAssured() {
        return cvgSumAssured;
    }

    public void setCvgSumAssured(String cvgSumAssured) {
        this.cvgSumAssured = cvgSumAssured;
    }

    public String getCvgStatusCd() {
        return cvgStatusCd;
    }

    public void setCvgStatusCd(String cvgStatusCd) {
        this.cvgStatusCd = cvgStatusCd;
    }

    public String getRelwithOwnClient() {
        return relwithOwnClient;
    }

    public void setRelwithOwnClient(String relwithOwnClient) {
        this.relwithOwnClient = relwithOwnClient;
    }

    @Override
    public String toString() {
        return "Payerdetails{" +
                "policyNo='" + policyNo + '\'' +
                ", cvgNum='" + cvgNum + '\'' +
                ", planName='" + planName + '\'' +
                ", cvgSumAssured='" + cvgSumAssured + '\'' +
                ", cvgStatusCd='" + cvgStatusCd + '\'' +
                ", relwithOwnClient='" + relwithOwnClient + '\'' +
                '}';
    }
}
