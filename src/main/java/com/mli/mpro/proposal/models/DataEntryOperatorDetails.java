package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class DataEntryOperatorDetails {

    @JsonProperty("deoBranchData")
    private String deoBranchData;
    @JsonProperty("deoCode")
    private String deoCode;

    @Sensitive(MaskType.EMAIL)
    @JsonProperty("deoEmail")
    private String deoEmail;

    @JsonProperty("deoId")
    private String deoId;
    @JsonProperty("deoLicenseStartdate")
    private String deoLicenseStartdate;
    @JsonProperty("deoLocation")
    private String deoLocation;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("deoMobileNumber")
    private String deoMobileNumber;
    @JsonProperty("deoRole")
    private String deoRole;
    @JsonProperty("deoStatus")
    private String deoStatus;

    /**
     * 
     */
    public DataEntryOperatorDetails() {
        // Auto-generated constructor stub
    }

    /**
     * @param deoBranchData
     * @param deoCode
     * @param deoEmail
     * @param deoId
     * @param deoLicenseStartdate
     * @param deoLocation
     * @param deoMobileNumber
     * @param deoRole
     * @param deoStatus
     */
    public DataEntryOperatorDetails(DataEntryOperatorDetails dataEntryOperatorDetails) {
        if (dataEntryOperatorDetails != null) {
            this.deoBranchData = dataEntryOperatorDetails.deoBranchData;
            this.deoCode = dataEntryOperatorDetails.deoCode;
            this.deoEmail = dataEntryOperatorDetails.deoEmail;
            this.deoId = dataEntryOperatorDetails.deoId;
            this.deoLicenseStartdate = dataEntryOperatorDetails.deoLicenseStartdate;
            this.deoLocation = dataEntryOperatorDetails.deoLocation;
            this.deoMobileNumber = dataEntryOperatorDetails.deoMobileNumber;
            this.deoRole = dataEntryOperatorDetails.deoRole;
            this.deoStatus = dataEntryOperatorDetails.deoStatus;
        }
    }

    public String getDeoBranchData() {
        return deoBranchData;
    }

    public void setDeoBranchData(String deoBranchData) {
        this.deoBranchData = deoBranchData;
    }

    public String getDeoCode() {
        return deoCode;
    }

    public void setDeoCode(String deoCode) {
        this.deoCode = deoCode;
    }

    public String getDeoEmail() {
        return deoEmail;
    }

    public void setDeoEmail(String deoEmail) {
        this.deoEmail = deoEmail;
    }

    public String getDeoId() {
        return deoId;
    }

    public void setDeoId(String deoId) {
        this.deoId = deoId;
    }

    public String getDeoLicenseStartdate() {
        return deoLicenseStartdate;
    }

    public void setDeoLicenseStartdate(String deoLicenseStartdate) {
        this.deoLicenseStartdate = deoLicenseStartdate;
    }

    public String getDeoLocation() {
        return deoLocation;
    }

    public void setDeoLocation(String deoLocation) {
        this.deoLocation = deoLocation;
    }

    public String getDeoMobileNumber() {
        return deoMobileNumber;
    }

    public void setDeoMobileNumber(String deoMobileNumber) {
        this.deoMobileNumber = deoMobileNumber;
    }

    public String getDeoRole() {
        return deoRole;
    }

    public void setDeoRole(String deoRole) {
        this.deoRole = deoRole;
    }

    public String getDeoStatus() {
        return deoStatus;
    }

    public void setDeoStatus(String deoStatus) {
        this.deoStatus = deoStatus;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "DataEntryOperatorDetails [deoBranchData=" + deoBranchData + ", deoCode=" + deoCode + ", deoEmail="
                + deoEmail + ", deoId=" + deoId + ", deoLicenseStartdate=" + deoLicenseStartdate + ", deoLocation="
                + deoLocation + ", deoMobileNumber=" + deoMobileNumber + ", deoRole=" + deoRole + ", deoStatus="
                + deoStatus + "]";
    }
}
