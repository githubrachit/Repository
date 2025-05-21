package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;


import java.util.List;


public class DedupeAPIPayload extends SOARequestPayload{

    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;

    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String dob;

    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNo")
    private String panNo;

    @JsonProperty("eiaNumber")
    private String eiaNumber;

    @JsonProperty("ckycNumber")
    private String ckycNumber;

    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNo")
    private List<String> mobileNo;

    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private List<String> emailId;


    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("prevPolicy")
    private List<String> prevPolicy;

    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("voterId")
    private List<String> voterId;

    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("drivingLicense")
    private List<String> drivingLicense;

    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("passport")
    private List<String> passport;

    @JsonProperty("name")
    private List<Name> name;

    @JsonProperty("fatherName")
    private List<FatherName> fatherName;

    @JsonProperty("bankDetails")
    private List<BankDetails> bankDetails;

    @JsonProperty("addresses")
    private List<Addresses> addresses;
    @JsonProperty("planCode")
    private String planCode;
    @JsonProperty("sourceChannel")
    private String sourceChannel;

    private String transactionId;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getEiaNumber() {
        return eiaNumber;
    }

    public void setEiaNumber(String eiaNumber) {
        this.eiaNumber = eiaNumber;
    }

    public String getCkycNumber() {
        return ckycNumber;
    }

    public void setCkycNumber(String ckycNumber) {
        this.ckycNumber = ckycNumber;
    }

    public List<String> getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(List<String> mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<String> getEmailId() {
        return emailId;
    }

    public void setEmailId(List<String> emailId) {
        this.emailId = emailId;
    }

    public List<String> getPrevPolicy() {
        return prevPolicy;
    }

    public void setPrevPolicy(List<String> prevPolicy) {
        this.prevPolicy = prevPolicy;
    }

    public List<String> getVoterId() {
        return voterId;
    }

    public void setVoterId(List<String> voterId) {
        this.voterId = voterId;
    }

    public List<String> getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(List<String> drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public List<String> getPassport() {
        return passport;
    }

    public void setPassport(List<String> passport) {
        this.passport = passport;
    }

    public List<Name> getName() {
        return name;
    }

    public void setName(List<Name> name) {
        this.name = name;
    }

    public List<FatherName> getFatherName() {
        return fatherName;
    }

    public void setFatherName(List<FatherName> fatherName) {
        this.fatherName = fatherName;
    }

    public List<BankDetails> getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(List<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "DedupeAPIPayload{" +
                "gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", panNo='" + panNo + '\'' +
                ", eiaNumber='" + eiaNumber + '\'' +
                ", ckycNumber='" + ckycNumber + '\'' +
                ", mobileNo=" + mobileNo +
                ", emailId=" + emailId +
                ", prevPolicy=" + prevPolicy +
                ", voterId=" + voterId +
                ", drivingLicense=" + drivingLicense +
                ", passport=" + passport +
                ", name=" + name +
                ", fatherName=" + fatherName +
                ", bankDetails=" + bankDetails +
                ", addresses=" + addresses +
                ", planCode='" + planCode + '\'' +
                ", sourceChannel='" + sourceChannel + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
