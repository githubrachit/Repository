package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"preferredDate", "preferredTime", "visitType", "labDetails"})
public class MedicalShedulingDetails {

    @JsonProperty("preferredDate")
    private Date preferredDate;
    @JsonProperty("preferredTime")
    private String preferredTime;
    @JsonProperty("visitType")
    private String visitType;
    @JsonProperty("scheduleStatus")
    private String scheduleStatus;
    @JsonProperty("labDetails")
    private LabDetails labDetails;
    @JsonProperty("labIndex")
    private String labIndex;
    @JsonProperty("isMedicalRequired")
    private String isMedicalRequired;
    @JsonProperty("medicalCategory")
    private String medicalCategory;
    @JsonProperty("packageName")
    private String packageName;
    @JsonProperty("testName")
    private List<String> testName = null;
    @JsonProperty("leadCreatedInTPASuccess")
    private String leadCreatedInTPASuccess;
    @JsonProperty("isPinCodeChanged")
    private String isPinCodeChanged;
    @JsonProperty("isNRIAvailableInIndia")
    private String isNRIAvailableInIndia;
    @JsonProperty("pinCodeChangeReason")
    private String pinCodeChangeReason;
    @JsonProperty("medicalGeoLocationDetails")
    private MedicalGeoLocationDetails medicalGeoLocationDetails;


    public MedicalShedulingDetails() {
        super();
    }

    @JsonProperty("preferredDate")
    public Date getPreferredDate() {
        return preferredDate;
    }

    @JsonProperty("preferredDate")
    public void setPreferredDate(Date preferredDate) {
        this.preferredDate = preferredDate;
    }

    @JsonProperty("preferredTime")
    public String getPreferredTime() {
        return preferredTime;
    }

    @JsonProperty("preferredTime")
    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    @JsonProperty("visitType")
    public String getVisitType() {
        return visitType;
    }

    @JsonProperty("visitType")
    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    @JsonProperty("labDetails")
    public LabDetails getLabDetails() {
        return labDetails;
    }

    @JsonProperty("labDetails")
    public void setLabDetails(LabDetails labDetails) {
        this.labDetails = labDetails;
    }

    @JsonProperty("scheduleStatus")
    public String getScheduleStatus() {
        return scheduleStatus;
    }

    @JsonProperty("scheduleStatus")
    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    @JsonProperty("labIndex")
    public String getLabIndex() {
        return labIndex;
    }

    @JsonProperty("labIndex")
    public void setLabIndex(String labIndex) {
        this.labIndex = labIndex;
    }

    @JsonProperty("isMedicalRequired")
    public String getIsMedicalRequired() {
        return isMedicalRequired;
    }

    @JsonProperty("isMedicalRequired")
    public void setIsMedicalRequired(String isMedicalRequired) {
        this.isMedicalRequired = isMedicalRequired;
    }

    @JsonProperty("medicalCategory")
    public String getMedicalCategory() {
        return medicalCategory;
    }

    @JsonProperty("medicalCategory")
    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
    }

    @JsonProperty("packageName")
    public String getPackageName() {
        return packageName;
    }

    @JsonProperty("packageName")
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @JsonProperty("testName")
    public List<String> getTestName() {
        return testName;
    }

    @JsonProperty("testName")
    public void setTestName(List<String> testName) {
        this.testName = testName;
    }

    @JsonProperty("leadCreatedInTPASuccess")
    public String getLeadCreatedInTPASuccess() {
        return leadCreatedInTPASuccess;
    }

    @JsonProperty("leadCreatedInTPASuccess")
    public void setLeadCreatedInTPASuccess(String leadCreatedInTPASuccess) {
        this.leadCreatedInTPASuccess = leadCreatedInTPASuccess;
    }

    public String getIsPinCodeChanged() {
        return isPinCodeChanged;
    }

    public void setIsPinCodeChanged(String isPinCodeChanged) {
        this.isPinCodeChanged = isPinCodeChanged;
    }

    public String getIsNRIAvailableInIndia() {
        return isNRIAvailableInIndia;
    }

    public void setIsNRIAvailableInIndia(String isNRIAvailableInIndia) {
        this.isNRIAvailableInIndia = isNRIAvailableInIndia;
    }

    public String getPinCodeChangeReason() {
        return pinCodeChangeReason;
    }

    public void setPinCodeChangeReason(String pinCodeChangeReason) {
        this.pinCodeChangeReason = pinCodeChangeReason;
    }

    public MedicalGeoLocationDetails getMedicalGeoLocationDetails() {
        return medicalGeoLocationDetails;
    }

    public void setMedicalGeoLocationDetails(MedicalGeoLocationDetails medicalGeoLocationDetails) {
        this.medicalGeoLocationDetails = medicalGeoLocationDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", MedicalShedulingDetails.class.getSimpleName() + "[", "]")
                .add("preferredDate=" + preferredDate)
                .add("preferredTime='" + preferredTime + "'")
                .add("visitType='" + visitType + "'")
                .add("scheduleStatus='" + scheduleStatus + "'")
                .add("labDetails=" + labDetails)
                .add("labIndex='" + labIndex + "'")
                .add("isMedicalRequired='" + isMedicalRequired + "'")
                .add("medicalCategory='" + medicalCategory + "'")
                .add("packageName='" + packageName + "'")
                .add("testName=" + testName)
                .add("leadCreatedInTPASuccess='" + leadCreatedInTPASuccess + "'")
                .add("isPinCodeChanged='" + isPinCodeChanged + "'")
                .add("isNRIAvailableInIndia='" + isNRIAvailableInIndia + "'")
                .add("pinCodeChangeReason='" + pinCodeChangeReason + "'")
                .add("medicalGeoLocationDetails='" + medicalGeoLocationDetails + "'")
                .toString();
    }
}
