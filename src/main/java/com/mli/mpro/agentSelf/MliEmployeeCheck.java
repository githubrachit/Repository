package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
public class MliEmployeeCheck {
    @JsonProperty("employeeId")
    private String employeeId;
   @JsonProperty("employeeStatus")
    private String employeeStatus;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
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

    @Override
    public String toString() {
        return "MliEmployeeCheck{" +
                "employeeId='" + employeeId + '\'' +
                ", mliEmployeeStatus='" + employeeStatus + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
