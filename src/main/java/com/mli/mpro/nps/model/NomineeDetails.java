package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class NomineeDetails {

    @JsonProperty("nomSrNo")
    private String nomSrNo;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("nomFirstName")
    private String nomFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("nomMiddleName")
    private String nomMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("nomLastName")
    private String nomLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("nomDOB")
    private String nomDOB;

    @JsonProperty("nomRelation")
    private String nomRelation;

    @JsonProperty("nomPercentage")
    private String nomPercentage;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine1")
    private String nomAddressLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine2")
    private String nomAddressLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine3")
    private String nomAddressLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine4")
    private String nomAddressLine4;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomState")
    private String nomState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomCountry")
    private String nomCountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("nomPin")
    private String nomPin;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("nomMobile")
    private String nomMobile;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("nomAlternateContact")
    private String nomAlternateContact;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("nomEmail")
    private String nomEmail;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("guardianFirstName")
    private String guardianFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("guardianMiddleName")
    private String guardianMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("guardianLastName")
    private String guardianLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("guardianDOB")
    private String guardianDOB;

    public NomineeDetails() {
        //default constructor
    }

    public String getNomSrNo() {
        return nomSrNo;
    }

    public void setNomSrNo(String nomSrNo) {
        this.nomSrNo = nomSrNo;
    }

    public String getNomFirstName() {
        return nomFirstName;
    }

    public void setNomFirstName(String nomFirstName) {
        this.nomFirstName = nomFirstName;
    }

    public String getNomMiddleName() {
        return nomMiddleName;
    }

    public void setNomMiddleName(String nomMiddleName) {
        this.nomMiddleName = nomMiddleName;
    }

    public String getNomLastName() {
        return nomLastName;
    }

    public void setNomLastName(String nomLastName) {
        this.nomLastName = nomLastName;
    }

    public String getNomDOB() {
        return nomDOB;
    }

    public void setNomDOB(String nomDOB) {
        this.nomDOB = nomDOB;
    }

    public String getNomRelation() {
        return nomRelation;
    }

    public void setNomRelation(String nomRelation) {
        this.nomRelation = nomRelation;
    }

    public String getNomPercentage() {
        return nomPercentage;
    }

    public void setNomPercentage(String nomPercentage) {
        this.nomPercentage = nomPercentage;
    }

    public String getNomAddressLine1() {
        return nomAddressLine1;
    }

    public void setNomAddressLine1(String nomAddressLine1) {
        this.nomAddressLine1 = nomAddressLine1;
    }

    public String getNomAddressLine2() {
        return nomAddressLine2;
    }

    public void setNomAddressLine2(String nomAddressLine2) {
        this.nomAddressLine2 = nomAddressLine2;
    }

    public String getNomAddressLine3() {
        return nomAddressLine3;
    }

    public void setNomAddressLine3(String nomAddressLine3) {
        this.nomAddressLine3 = nomAddressLine3;
    }

    public String getNomAddressLine4() {
        return nomAddressLine4;
    }

    public void setNomAddressLine4(String nomAddressLine4) {
        this.nomAddressLine4 = nomAddressLine4;
    }

    public String getNomState() {
        return nomState;
    }

    public void setNomState(String nomState) {
        this.nomState = nomState;
    }

    public String getNomCountry() {
        return nomCountry;
    }

    public void setNomCountry(String nomCountry) {
        this.nomCountry = nomCountry;
    }

    public String getNomPin() {
        return nomPin;
    }

    public void setNomPin(String nomPin) {
        this.nomPin = nomPin;
    }

    public String getNomMobile() {
        return nomMobile;
    }

    public void setNomMobile(String nomMobile) {
        this.nomMobile = nomMobile;
    }

    public String getNomAlternateContact() {
        return nomAlternateContact;
    }

    public void setNomAlternateContact(String nomAlternateContact) {
        this.nomAlternateContact = nomAlternateContact;
    }

    public String getNomEmail() {
        return nomEmail;
    }

    public void setNomEmail(String nomEmail) {
        this.nomEmail = nomEmail;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public void setGuardianFirstName(String guardianFirstName) {
        this.guardianFirstName = guardianFirstName;
    }

    public String getGuardianMiddleName() {
        return guardianMiddleName;
    }

    public void setGuardianMiddleName(String guardianMiddleName) {
        this.guardianMiddleName = guardianMiddleName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public void setGuardianLastName(String guardianLastName) {
        this.guardianLastName = guardianLastName;
    }

    public String getGuardianDOB() {
        return guardianDOB;
    }

    public void setGuardianDOB(String guardianDOB) {
        this.guardianDOB = guardianDOB;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "NomineeDetails{" +
                "nomSrNo='" + nomSrNo + '\'' +
                ", nomFirstName='" + nomFirstName + '\'' +
                ", nomMiddleName='" + nomMiddleName + '\'' +
                ", nomLastName='" + nomLastName + '\'' +
                ", nomDOB='" + nomDOB + '\'' +
                ", nomRelation='" + nomRelation + '\'' +
                ", nomPercentage='" + nomPercentage + '\'' +
                ", nomAddressLine1='" + nomAddressLine1 + '\'' +
                ", nomAddressLine2='" + nomAddressLine2 + '\'' +
                ", nomAddressLine3='" + nomAddressLine3 + '\'' +
                ", nomAddressLine4='" + nomAddressLine4 + '\'' +
                ", nomState='" + nomState + '\'' +
                ", nomCountry='" + nomCountry + '\'' +
                ", nomPin='" + nomPin + '\'' +
                ", nomMobile='" + nomMobile + '\'' +
                ", nomAlternateContact='" + nomAlternateContact + '\'' +
                ", nomEmail='" + nomEmail + '\'' +
                ", guardianFirstName='" + guardianFirstName + '\'' +
                ", guardianMiddleName='" + guardianMiddleName + '\'' +
                ", guardianLastName='" + guardianLastName + '\'' +
                ", guardianDOB='" + guardianDOB + '\'' +
                '}';
    }
}
