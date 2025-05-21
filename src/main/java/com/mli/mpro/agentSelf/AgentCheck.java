package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import java.util.Date;

public class AgentCheck {
    @JsonProperty("agentId")
    private String agentId;
    @JsonProperty("agentStatus")
    private String agentStatus;
    @JsonProperty("agentRole")
    private String agentRole;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("firstName")
    private String firstName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("lastName")
    private String lastName;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("designation")
    private String designation;
    @JsonProperty("amlTrainingDate")
    private String amlTrainingDate;
    @JsonProperty("ulipTrainingDate")
    private String ulipTrainingDate;
    @JsonProperty("agentExistance")
    private String agentExistance;
    private Date amlTrainingDateInternal;
    private Date ulipTrainingDateInternal;
    private Date dobInternal;

    public Date getAmlTrainingDateInternal() {
        return amlTrainingDateInternal;
    }

    public void setAmlTrainingDateInternal(Date amlTrainingDateInternal) {
        this.amlTrainingDateInternal = amlTrainingDateInternal;
    }

    public Date getUlipTrainingDateInternal() {
        return ulipTrainingDateInternal;
    }

    public void setUlipTrainingDateInternal(Date ulipTrainingDateInternal) {
        this.ulipTrainingDateInternal = ulipTrainingDateInternal;
    }

    public Date getDobInternal() {
        return dobInternal;
    }

    public void setDobInternal(Date dobInternal) {
        this.dobInternal = dobInternal;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getAgentRole() {
        return agentRole;
    }

    public void setAgentRole(String agentRole) {
        this.agentRole = agentRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAmlTrainingDate() {
        return amlTrainingDate;
    }

    public void setAmlTrainingDate(String amlTrainingDate) {
        this.amlTrainingDate = amlTrainingDate;
    }

    public String getUlipTrainingDate() {
        return ulipTrainingDate;
    }

    public void setUlipTrainingDate(String ulipTrainingDate) {
        this.ulipTrainingDate = ulipTrainingDate;
    }

    public String getAgentExistance() {
        return agentExistance;
    }

    public void setAgentExistance(String agentExistance) {
        this.agentExistance = agentExistance;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgentCheck{" +
                "agentId='" + agentId + '\'' +
                ", agentStatus='" + agentStatus + '\'' +
                ", agentRole='" + agentRole + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", designation='" + designation + '\'' +
                ", amlTrainingDate='" + amlTrainingDate + '\'' +
                ", ulipTrainingDate='" + ulipTrainingDate + '\'' +
                ", agentExistance='" + agentExistance + '\'' +
                ", amlTrainingDateInternal=" + amlTrainingDateInternal +
                ", ulipTrainingDateInternal=" + ulipTrainingDateInternal +
                ", dobInternal=" + dobInternal +
                '}';
    }
}
