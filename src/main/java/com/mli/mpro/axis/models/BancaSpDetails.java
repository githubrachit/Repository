package com.mli.mpro.axis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class BancaSpDetails {
    @Sensitive(MaskType.NAME)
    @JsonProperty("specifiedPersonName")
    private String specifiedPersonName;
    @JsonProperty("specifiedPersonCode")
    private String specifiedPersonCode;
    @JsonProperty("amlStatus")
    private Boolean amlStatus;
    @JsonProperty("amlTrainingExpirationDate")
    private String amlTrainingExpirationDate;
    @JsonProperty("ulipStatus")
    private Boolean ulipStatus;
    @JsonProperty("ulipTrainingExpirationDate")
    private String ulipTrainingExpirationDate;
    public BancaSpDetails() {
	super();
    }
    public BancaSpDetails(String specifiedPersonName, String specifiedPersonCode, Boolean amlStatus, String amlTrainingExpirationDate, Boolean ulipStatus,
	    String ulipTrainingExpirationDate) {
	super();
	this.specifiedPersonName = specifiedPersonName;
	this.specifiedPersonCode = specifiedPersonCode;
	this.amlStatus = amlStatus;
	this.amlTrainingExpirationDate = amlTrainingExpirationDate;
	this.ulipStatus = ulipStatus;
	this.ulipTrainingExpirationDate = ulipTrainingExpirationDate;
    }
    public String getSpecifiedPersonName() {
        return specifiedPersonName;
    }
    public void setSpecifiedPersonName(String specifiedPersonName) {
        this.specifiedPersonName = specifiedPersonName;
    }
    public String getSpecifiedPersonCode() {
        return specifiedPersonCode;
    }
    public void setSpecifiedPersonCode(String specifiedPersonCode) {
        this.specifiedPersonCode = specifiedPersonCode;
    }
    public Boolean getAmlStatus() {
        return amlStatus;
    }
    public void setAmlStatus(Boolean amlStatus) {
        this.amlStatus = amlStatus;
    }
    public String getAmlTrainingExpirationDate() {
        return amlTrainingExpirationDate;
    }
    public void setAmlTrainingExpirationDate(String amlTrainingExpirationDate) {
        this.amlTrainingExpirationDate = amlTrainingExpirationDate;
    }
    public Boolean getUlipStatus() {
        return ulipStatus;
    }
    public void setUlipStatus(Boolean ulipStatus) {
        this.ulipStatus = ulipStatus;
    }
    public String getUlipTrainingExpirationDate() {
        return ulipTrainingExpirationDate;
    }
    public void setUlipTrainingExpirationDate(String ulipTrainingExpirationDate) {
        this.ulipTrainingExpirationDate = ulipTrainingExpirationDate;
    }
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "BancaSpDetails [specifiedPersonName=" + specifiedPersonName + ", specifiedPersonCode=" + specifiedPersonCode + ", amlStatus=" + amlStatus
		+ ", amlTrainingExpirationDate=" + amlTrainingExpirationDate + ", ulipStatus=" + ulipStatus + ", ulipTrainingExpirationDate="
		+ ulipTrainingExpirationDate + "]";
    }    
}
