package com.mli.mpro.location.panDOBVerification.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;

public class SoaPayload {
    private String equoteNumber;
    @Sensitive(MaskType.LAST_NAME)
    private String lastName;
    @Sensitive(MaskType.MASK_ALL)
    private String occupation;
    @Sensitive(MaskType.MASK_ALL)
    private String education;
    @Sensitive(MaskType.GENDER)
    private String gender;
    @Sensitive(MaskType.PINCODE)
    private String postalCode;
    private String validationType;
    private List<String> policyNo;
    @Sensitive(MaskType.AMOUNT)
    private String sumAssured;
    @Sensitive(MaskType.ADDRESS)
    private String street;
    @Sensitive(MaskType.ADDRESS)
    private String houseNo;
    @Sensitive(MaskType.ADDRESS)
    private String state;
    @Sensitive(MaskType.ADDRESS)
    private String landmark;
    @Sensitive(MaskType.PAN_NUM)
    private String pan;
    @Sensitive(MaskType.EMAIL)
    private String email;
    private String leadId;
    @Sensitive(MaskType.ADDRESS)
    private String postOffice;
    private String transTrackingId;
    @Sensitive(MaskType.AMOUNT)
    private String declaredIncome;
    @Sensitive(MaskType.ADDRESS)
    private String villCity;
    @Sensitive(MaskType.MOBILE)
    private String mobileNo;
    private String crmUpdate;
    @Sensitive(MaskType.FIRST_NAME)
    private String firstName;
    private String careOf;
    @Sensitive(MaskType.DOB)
    private String dob;
    @Sensitive(MaskType.ADDRESS)
    private String district;
    @Sensitive(MaskType.MIDDLE_NAME)
    private String middleName;
    @Sensitive(MaskType.ADDRESS)
    private String location;
    @Sensitive(MaskType.ADDRESS)
    private String stateCode;
    @Sensitive(MaskType.ADDRESS)
    private String subDistrict;

   
    public String getEquoteNumber() {
        return equoteNumber;
    }

    public void setEquoteNumber(String equoteNumber) {
        this.equoteNumber = equoteNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public List<String> getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(List<String> policyNo) {
        this.policyNo = policyNo;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getTransTrackingId() {
        return transTrackingId;
    }

    public void setTransTrackingId(String transTrackingId) {
        this.transTrackingId = transTrackingId;
    }

    public String getDeclaredIncome() {
        return declaredIncome;
    }

    public void setDeclaredIncome(String declaredIncome) {
        this.declaredIncome = declaredIncome;
    }

    public String getVillCity() {
        return villCity;
    }

    public void setVillCity(String villCity) {
        this.villCity = villCity;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCrmUpdate() {
        return crmUpdate;
    }

    public void setCrmUpdate(String crmUpdate) {
        this.crmUpdate = crmUpdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaPayload{" +
                "equoteNumber='" + equoteNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", occupation='" + occupation + '\'' +
                ", education='" + education + '\'' +
                ", gender='" + gender + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", validationType='" + validationType + '\'' +
                ", policyNo=" + policyNo +
                ", sumAssured='" + sumAssured + '\'' +
                ", street='" + street + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", state='" + state + '\'' +
                ", landmark='" + landmark + '\'' +
                ", pan='" + pan + '\'' +
                ", email='" + email + '\'' +
                ", leadId='" + leadId + '\'' +
                ", postOffice='" + postOffice + '\'' +
                ", transTrackingId='" + transTrackingId + '\'' +
                ", declaredIncome='" + declaredIncome + '\'' +
                ", villCity='" + villCity + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", crmUpdate='" + crmUpdate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", careOf='" + careOf + '\'' +
                ", dob='" + dob + '\'' +
                ", district='" + district + '\'' +
                ", middleName='" + middleName + '\'' +
                ", location='" + location + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", subDistrict='" + subDistrict + '\'' +
                '}';
    }
}
