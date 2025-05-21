package com.mli.mpro.tmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class CustomerDetailsResponse {

    @JsonProperty("statuscode")
    private String statuscode = "";
    @JsonProperty("status")
    private String status = "";
    @JsonProperty("response")
    private String response = "";
    @Sensitive(MaskType.NAME)
    @JsonProperty("customerFirstName")
    private String customerFirstName = "";
    @Sensitive(MaskType.NAME)
    @JsonProperty("customerLastName")
    private String customerLastName = "";
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNumber")
    private String mobileNumber = "";
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId = "";
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob = "";
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pan")
    private String pan = "";
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender = "";
    @JsonProperty("nationality")
    private String nationality = "";
    @Sensitive(MaskType.GENDER)
    @JsonProperty("maritalStatus")
    private String maritalStatus = "";
    @JsonProperty("education")
    private String education = "";
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualIncome")
    private long annualIncome = 0L;
    @JsonProperty("occupationType")
    private String occupationType = "";
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("communicationAddress")
    private String communicationAddress = "";
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("communicationCity")
    private String communicationCity = "";
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("communicationState")
    private String communicationState = "";
    @JsonProperty("communicationCountry")
    private String communicationCountry = "";
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("communicationPincode")
    private String communicationPincode = "";
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentAddress")
    private String permanentAddress = "";
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentCity")
    private String permanentCity = "";
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permanentState")
    private String permanentState = "";
    @JsonProperty("permanentCountry")
    private String permanentCountry = "";
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("permanentPincode")
    private String permanentPincode = "";
    @Sensitive(MaskType.BANK_ACC_HOLDER_NAME)
    @JsonProperty("accountHolderName")
    private String accountHolderName = "";
    @JsonProperty("kycFlag")
    private String kycFlag = "";
    @JsonProperty("ckycNo")
    private String ckycNo = "";
    @JsonProperty("customerClassification")
    private String customerClassification = "";


    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public long getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(long annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(String occupationType) {
        this.occupationType = occupationType;
    }

    public String getCommunicationAddress() {
        return communicationAddress;
    }

    public void setCommunicationAddress(String communicationAddress) {
        this.communicationAddress = communicationAddress;
    }

    public String getCommunicationCity() {
        return communicationCity;
    }

    public void setCommunicationCity(String communicationCity) {
        this.communicationCity = communicationCity;
    }

    public String getCommunicationState() {
        return communicationState;
    }

    public void setCommunicationState(String communicationState) {
        this.communicationState = communicationState;
    }

    public String getCommunicationCountry() {
        return communicationCountry;
    }

    public void setCommunicationCountry(String communicationCountry) {
        this.communicationCountry = communicationCountry;
    }

    public String getCommunicationPincode() {
        return communicationPincode;
    }

    public void setCommunicationPincode(String communicationPincode) {
        this.communicationPincode = communicationPincode;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentState() {
        return permanentState;
    }

    public void setPermanentState(String permanentState) {
        this.permanentState = permanentState;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public String getPermanentPincode() {
        return permanentPincode;
    }

    public void setPermanentPincode(String permanentPincode) {
        this.permanentPincode = permanentPincode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getKycFlag() {
        return kycFlag;
    }

    public void setKycFlag(String kycFlag) {
        this.kycFlag = kycFlag;
    }

    public String getCkycNo() {
        return ckycNo;
    }

    public void setCkycNo(String ckycNo) {
        this.ckycNo = ckycNo;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerClassification() {
        return customerClassification;
    }

    public void setCustomerClassification(String customerClassification) {
        this.customerClassification = customerClassification;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "CustomerDetailsResponse{" +
                "statuscode='" + statuscode + '\'' +
                ", status='" + status + '\'' +
                ", response='" + response + '\'' +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", pan='" + pan + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", education='" + education + '\'' +
                ", annualIncome=" + annualIncome +
                ", occupationType='" + occupationType + '\'' +
                ", communicationAddress='" + communicationAddress + '\'' +
                ", communicationCity='" + communicationCity + '\'' +
                ", communicationState='" + communicationState + '\'' +
                ", communicationCountry='" + communicationCountry + '\'' +
                ", communicationPincode='" + communicationPincode + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", permanentCity='" + permanentCity + '\'' +
                ", permanentState='" + permanentState + '\'' +
                ", permanentCountry='" + permanentCountry + '\'' +
                ", permanentPincode='" + permanentPincode + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", kycFlag='" + kycFlag + '\'' +
                ", ckycNo='" + ckycNo + '\'' +
                ", customerClassification='" + customerClassification + '\'' +
                '}';
    }
}

