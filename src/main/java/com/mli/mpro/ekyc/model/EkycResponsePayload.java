package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class EkycResponsePayload {

    @JsonProperty("ekycStatus")
    private String ekycStatus;
    @JsonProperty("msg")
    private String msg;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("requestType")
    private String requestType;
    @JsonProperty("partyType")
    private String partyType;
    @JsonProperty("proposerEkycFailure")
    private String proposerEkycFailure;
    @JsonProperty("insurerEkycFailure")
    private String insurerEkycFailure;
    @JsonProperty("payorEkycFailure")
    private String payorEkycFailure;

    @JsonProperty("proposerEkycTypeOfFailure")
    private String proposerEkycTypeOfFailure;
    @JsonProperty("insurerEkycTypeOfFailure")
    private String insurerEkycTypeOfFailure;
    @JsonProperty("payorEkycTypeOfFailure")
    private String payorEkycTypeOfFailure;

    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("aadhaarDetail")
    private ResponseAadhaarDetails aadhaarDetail;

    @JsonProperty("validationErrorMsg")
    private String validationErrorMsg;

    public EkycResponsePayload(String ekycStatus,String msg ,String aadhaarNumber, String requestType, String partyType,String policyNumber, ResponseAadhaarDetails aadhaarDetail) {
        this.ekycStatus = ekycStatus;
        this.msg=msg;
        this.aadhaarNumber = aadhaarNumber;
        this.requestType = requestType;
        this.partyType=partyType;
        this.policyNumber = policyNumber;
        this.aadhaarDetail = aadhaarDetail;
    }

    public String getValidationErrorMsg() {
        return validationErrorMsg;
    }

    public void setValidationErrorMsg(String validationErrorMsg) {
        this.validationErrorMsg = validationErrorMsg;
    }

    public EkycResponsePayload() {
    }

    public String getProposerEkycTypeOfFailure() {
        return proposerEkycTypeOfFailure;
    }

    public void setProposerEkycTypeOfFailure(String proposerEkycTypeOfFailure) {
        this.proposerEkycTypeOfFailure = proposerEkycTypeOfFailure;
    }

    public String getInsurerEkycTypeOfFailure() {
        return insurerEkycTypeOfFailure;
    }

    public void setInsurerEkycTypeOfFailure(String insurerEkycTypeOfFailure) {
        this.insurerEkycTypeOfFailure = insurerEkycTypeOfFailure;
    }

    public String getPayorEkycTypeOfFailure() {
        return payorEkycTypeOfFailure;
    }

    public void setPayorEkycTypeOfFailure(String payorEkycTypeOfFailure) {
        this.payorEkycTypeOfFailure = payorEkycTypeOfFailure;
    }

    public String getEkycStatus() {
        return ekycStatus;
    }

    public void setEkycStatus(String ekycStatus) {
        this.ekycStatus = ekycStatus;
    }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }


    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public ResponseAadhaarDetails getAadhaarDetail() {
        return aadhaarDetail;
    }

    public void setAadhaarDetail(ResponseAadhaarDetails aadhaarDetail) {
        this.aadhaarDetail = aadhaarDetail;
    }

    public String getProposerEkycFailure() { return proposerEkycFailure; }

    public void setProposerEkycFailure(String proposerEkycFailure) { this.proposerEkycFailure = proposerEkycFailure; }

    public String getInsurerEkycFailure() { return insurerEkycFailure; }

    public void setInsurerEkycFailure(String insurerEkycFailure) { this.insurerEkycFailure = insurerEkycFailure; }

    public String getPayorEkycFailure() { return payorEkycFailure; }

    public void setPayorEkycFailure(String payorEkycFailure) { this.payorEkycFailure = payorEkycFailure; }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "EkycResponsePayload{" +
                "ekycStatus='" + ekycStatus + '\'' +
                ", aadhaarNumber='" + aadhaarNumber + '\'' +
                ", msg='" + msg + '\'' +
                ", requestType='" + requestType + '\'' +
                ", proposerEkycFailure='" + proposerEkycFailure + '\'' +
                ", insuredEkycFailure='" + insurerEkycFailure + '\'' +
                ", payorEkycFailure='" + payorEkycFailure + '\'' +
                ", partyType='" + partyType + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", aadhaarDetail=" + aadhaarDetail + '\'' +
                ", validationErrorMsg=" + validationErrorMsg +
                '}';
    }
}
