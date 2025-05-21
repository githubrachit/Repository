package com.mli.mpro.location.panDOBVerification.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class SoaResponsePayload {
    private String validationType;
    private String transTrackingId;
    private String equoteNumber;
    private List<String> policyNo;
    private String statusCode;
    private String statusDesc;
    @Sensitive(MaskType.PAN_NUM)
    private String pan;
    private String panStatus;
    @Sensitive(MaskType.DOB)
    private String dob;
    private String dobStatus;
    @Sensitive(MaskType.NAME)
    private String name;
    private String nameStatus;
    @Sensitive(MaskType.ADDRESS)
    private String address;
    private String addressStatus;
    @Sensitive(MaskType.PINCODE)
    private String pinCode;
    private String pinCodeStatus;
    @Sensitive(MaskType.MOBILE)
    private String mobile;
    private String mobileStatus;
    @Sensitive(MaskType.EMAIL)
    private String emailId;
    private String emailIdStatus;
    private String occuptionClass;
    @Sensitive(MaskType.AMOUNT)
    private String creditScore;
    @Sensitive(MaskType.AMOUNT)
    private String estmtdIncome;
    private String byteArray;
    private String xml;
    private List<String> linkRemarks;
    private String panAadhaarLinked;

    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public String getTransTrackingId() {
        return transTrackingId;
    }

    public void setTransTrackingId(String transTrackingId) {
        this.transTrackingId = transTrackingId;
    }

    public String getEquoteNumber() {
        return equoteNumber;
    }

    public void setEquoteNumber(String equoteNumber) {
        this.equoteNumber = equoteNumber;
    }

    public List<String> getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(List<String> policyNo) {
        this.policyNo = policyNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPanStatus() {
        return panStatus;
    }

    public void setPanStatus(String panStatus) {
        this.panStatus = panStatus;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDobStatus() {
        return dobStatus;
    }

    public void setDobStatus(String dobStatus) {
        this.dobStatus = dobStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPinCodeStatus() {
        return pinCodeStatus;
    }

    public void setPinCodeStatus(String pinCodeStatus) {
        this.pinCodeStatus = pinCodeStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailIdStatus() {
        return emailIdStatus;
    }

    public void setEmailIdStatus(String emailIdStatus) {
        this.emailIdStatus = emailIdStatus;
    }

    public String getOccuptionClass() {
        return occuptionClass;
    }

    public void setOccuptionClass(String occuptionClass) {
        this.occuptionClass = occuptionClass;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getEstmtdIncome() {
        return estmtdIncome;
    }

    public void setEstmtdIncome(String estmtdIncome) {
        this.estmtdIncome = estmtdIncome;
    }

    public String getByteArray() {
        return byteArray;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public List<String> getLinkRemarks() {
        return linkRemarks;
    }

    public void setLinkRemarks(List<String> linkRemarks) {
        this.linkRemarks = linkRemarks;
    }

    public String getPanAadhaarLinked() {
        return panAadhaarLinked;
    }

    public void setPanAadhaarLinked(String panAadhaarLinked) {
        this.panAadhaarLinked = panAadhaarLinked;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaResponsePayload{" +
                "validationType='" + validationType + '\'' +
                ", transTrackingId='" + transTrackingId + '\'' +
                ", equoteNumber='" + equoteNumber + '\'' +
                ", policyNo=" + policyNo +
                ", statusCode='" + statusCode + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", pan='" + pan + '\'' +
                ", panStatus='" + panStatus + '\'' +
                ", dob='" + dob + '\'' +
                ", dobStatus='" + dobStatus + '\'' +
                ", name='" + name + '\'' +
                ", nameStatus='" + nameStatus + '\'' +
                ", address='" + address + '\'' +
                ", addressStatus='" + addressStatus + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", pinCodeStatus='" + pinCodeStatus + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobileStatus='" + mobileStatus + '\'' +
                ", emailId='" + emailId + '\'' +
                ", emailIdStatus='" + emailIdStatus + '\'' +
                ", occuptionClass='" + occuptionClass + '\'' +
                ", creditScore='" + creditScore + '\'' +
                ", estmtdIncome='" + estmtdIncome + '\'' +
                ", byteArray='" + byteArray + '\'' +
                ", xml='" + xml + '\'' +
                ", linkRemarks='" + linkRemarks + '\'' +
                ", panAadhaarLinked='" + panAadhaarLinked + '\'' +
                '}';
    }
}
