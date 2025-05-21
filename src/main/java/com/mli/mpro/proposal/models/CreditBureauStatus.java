package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

public class CreditBureauStatus {

    @JsonProperty("proposerCreditScore")
    private String proposerCreditScore;
    @JsonProperty("proposerIncomeRange")
    private String proposerIncomeRange;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pan")
    private String pan;
    @JsonProperty("panStatus")
    private String panStatus;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("dobStatus")
    private String dobStatus;
    @Sensitive(MaskType.NAME)
    @JsonProperty("name")
    private String name;
    @JsonProperty("nameStatus")
    private String nameStatus;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("address")
    private String address;
    @JsonProperty("addressStatus")
    private String addressStatus;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("pinCode")
    private String pinCode;
    @JsonProperty("pinCodeStatus")
    private String pinCodeStatus;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("mobileStatus")
    private String mobileStatus;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId;
    @JsonProperty("emailIdStatus")
    private String emailIdStatus;
    @JsonProperty("occuptionClass")
    private String occuptionClass;
    @JsonProperty("byteArr")
    private String byteArr;

    public CreditBureauStatus() {
        super();
    }

    public CreditBureauStatus(String proposerCreditScore, String proposerIncomeRange) {
        super();
        this.proposerCreditScore = proposerCreditScore;
        this.proposerIncomeRange = proposerIncomeRange;
    }

    public String getProposerCreditScore() {
        return proposerCreditScore;
    }

    public CreditBureauStatus setProposerCreditScore(String proposerCreditScore) {
        this.proposerCreditScore = proposerCreditScore;
        return this;
    }

    public String getProposerIncomeRange() {
        return proposerIncomeRange;
    }

    public CreditBureauStatus setProposerIncomeRange(String proposerIncomeRange) {
        this.proposerIncomeRange = proposerIncomeRange;
        return this;
    }

    public String getPan() {
        return pan;
    }

    public CreditBureauStatus setPan(String pan) {
        this.pan = pan;
        return this;
    }

    public String getPanStatus() {
        return panStatus;
    }

    public CreditBureauStatus setPanStatus(String panStatus) {
        this.panStatus = panStatus;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public CreditBureauStatus setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public String getDobStatus() {
        return dobStatus;
    }

    public CreditBureauStatus setDobStatus(String dobStatus) {
        this.dobStatus = dobStatus;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreditBureauStatus setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public CreditBureauStatus setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreditBureauStatus setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public CreditBureauStatus setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
        return this;
    }

    public String getPinCode() {
        return pinCode;
    }

    public CreditBureauStatus setPinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public String getPinCodeStatus() {
        return pinCodeStatus;
    }

    public CreditBureauStatus setPinCodeStatus(String pinCodeStatus) {
        this.pinCodeStatus = pinCodeStatus;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public CreditBureauStatus setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public CreditBureauStatus setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public CreditBureauStatus setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getEmailIdStatus() {
        return emailIdStatus;
    }

    public CreditBureauStatus setEmailIdStatus(String emailIdStatus) {
        this.emailIdStatus = emailIdStatus;
        return this;
    }

    public String getOccuptionClass() {
        return occuptionClass;
    }

    public CreditBureauStatus setOccuptionClass(String occuptionClass) {
        this.occuptionClass = occuptionClass;
        return this;
    }

    public String getByteArr() {
        return byteArr;
    }

    public CreditBureauStatus setByteArr(String byteArr) {
        this.byteArr = byteArr;
        return this;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("address = " + address)
                .add("addressStatus = " + addressStatus)
                .add("byteArr = " + byteArr)
                .add("dob = " + dob)
                .add("dobStatus = " + dobStatus)
                .add("emailId = " + emailId)
                .add("emailIdStatus = " + emailIdStatus)
                .add("mobile = " + mobile)
                .add("mobileStatus = " + mobileStatus)
                .add("name = " + name)
                .add("nameStatus = " + nameStatus)
                .add("occuptionClass = " + occuptionClass)
                .add("pan = " + pan)
                .add("panStatus = " + panStatus)
                .add("pinCode = " + pinCode)
                .add("pinCodeStatus = " + pinCodeStatus)
                .add("proposerCreditScore = " + proposerCreditScore)
                .add("proposerIncomeRange = " + proposerIncomeRange)
                .toString();
    }
}
