package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class ContactDetail {
    @JsonProperty("emalId")
    private String emalId;

    @JsonProperty("faxNum")
    private String faxNum;

    @JsonProperty("mobNum")
    private String mobNum;

    @JsonProperty("lastContactNum")
    private String lastContactNum;

    @JsonProperty("dncStatus")
    private String dncStatus;

    @JsonProperty("dndStatus")
    private String dndStatus;

    @JsonProperty("currentLandline1")
    private String currentLandline1;

    @JsonProperty("permanentLandline1")
    private String permanentLandline1;

    @JsonProperty("workLandline1")
    private String workLandline1;

    @JsonProperty("currentLandline2")
    private String currentLandline2;

    @JsonProperty("permanentLandline2")
    private String permanentLandline2;

    @JsonProperty("workLandline2")
    private String workLandline2;

    @JsonProperty("greenMailFlag")
    private String greenMailFlag;

    @JsonProperty("lastEmail")
    private String lastEmail;

    @JsonProperty("lastMobile")
    private String lastMobile;

    @JsonProperty("phone1")
    private String phone1;

    @JsonProperty("phone2")
    private String phone2;

    @JsonProperty("addresslastupdateddate")
    private String addresslastupdateddate;

    @JsonProperty("altMobNum")
    private String altMobNum;

    @JsonProperty("emailLastUpdatedDate")
    private String emailLastUpdatedDate;

    @JsonProperty("mobileLastUpdatedDate")
    private String mobileLastUpdatedDate;

    public String getEmalId() {
        return emalId;
    }

    public void setEmalId(String emalId) {
        this.emalId = emalId;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public String getLastContactNum() {
        return lastContactNum;
    }

    public void setLastContactNum(String lastContactNum) {
        this.lastContactNum = lastContactNum;
    }

    public String getDncStatus() {
        return dncStatus;
    }

    public void setDncStatus(String dncStatus) {
        this.dncStatus = dncStatus;
    }

    public String getDndStatus() {
        return dndStatus;
    }

    public void setDndStatus(String dndStatus) {
        this.dndStatus = dndStatus;
    }

    public String getCurrentLandline1() {
        return currentLandline1;
    }

    public void setCurrentLandline1(String currentLandline1) {
        this.currentLandline1 = currentLandline1;
    }

    public String getPermanentLandline1() {
        return permanentLandline1;
    }

    public void setPermanentLandline1(String permanentLandline1) {
        this.permanentLandline1 = permanentLandline1;
    }

    public String getWorkLandline1() {
        return workLandline1;
    }

    public void setWorkLandline1(String workLandline1) {
        this.workLandline1 = workLandline1;
    }

    public String getCurrentLandline2() {
        return currentLandline2;
    }

    public void setCurrentLandline2(String currentLandline2) {
        this.currentLandline2 = currentLandline2;
    }

    public String getPermanentLandline2() {
        return permanentLandline2;
    }

    public void setPermanentLandline2(String permanentLandline2) {
        this.permanentLandline2 = permanentLandline2;
    }

    public String getWorkLandline2() {
        return workLandline2;
    }

    public void setWorkLandline2(String workLandline2) {
        this.workLandline2 = workLandline2;
    }

    public String getGreenMailFlag() {
        return greenMailFlag;
    }

    public void setGreenMailFlag(String greenMailFlag) {
        this.greenMailFlag = greenMailFlag;
    }

    public String getLastEmail() {
        return lastEmail;
    }

    public void setLastEmail(String lastEmail) {
        this.lastEmail = lastEmail;
    }

    public String getLastMobile() {
        return lastMobile;
    }

    public void setLastMobile(String lastMobile) {
        this.lastMobile = lastMobile;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddresslastupdateddate() {
        return addresslastupdateddate;
    }

    public void setAddresslastupdateddate(String addresslastupdateddate) {
        this.addresslastupdateddate = addresslastupdateddate;
    }

    public String getAltMobNum() {
        return altMobNum;
    }

    public void setAltMobNum(String altMobNum) {
        this.altMobNum = altMobNum;
    }

    public String getEmailLastUpdatedDate() {
        return emailLastUpdatedDate;
    }

    public void setEmailLastUpdatedDate(String emailLastUpdatedDate) {
        this.emailLastUpdatedDate = emailLastUpdatedDate;
    }

    public String getMobileLastUpdatedDate() {
        return mobileLastUpdatedDate;
    }

    public void setMobileLastUpdatedDate(String mobileLastUpdatedDate) {
        this.mobileLastUpdatedDate = mobileLastUpdatedDate;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ContactDetail{" +
                "emalId='" + emalId + '\'' +
                ", faxNum='" + faxNum + '\'' +
                ", mobNum='" + mobNum + '\'' +
                ", lastContactNum='" + lastContactNum + '\'' +
                ", dncStatus='" + dncStatus + '\'' +
                ", dndStatus='" + dndStatus + '\'' +
                ", currentLandline1='" + currentLandline1 + '\'' +
                ", permanentLandline1='" + permanentLandline1 + '\'' +
                ", workLandline1='" + workLandline1 + '\'' +
                ", currentLandline2='" + currentLandline2 + '\'' +
                ", permanentLandline2='" + permanentLandline2 + '\'' +
                ", workLandline2='" + workLandline2 + '\'' +
                ", greenMailFlag='" + greenMailFlag + '\'' +
                ", lastEmail='" + lastEmail + '\'' +
                ", lastMobile='" + lastMobile + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", addresslastupdateddate='" + addresslastupdateddate + '\'' +
                ", altMobNum='" + altMobNum + '\'' +
                ", emailLastUpdatedDate='" + emailLastUpdatedDate + '\'' +
                ", mobileLastUpdatedDate='" + mobileLastUpdatedDate + '\'' +
                '}';
    }
}
