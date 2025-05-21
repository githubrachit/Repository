package com.mli.mpro.agentSelfIdentifiedSkip;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class PolicyDetails {

    public String policyNumber;
    @Sensitive(MaskType.FIRST_NAME)
    public String firstName;
    @Sensitive(MaskType.LAST_NAME)
    public String lastName;
    @Sensitive(MaskType.PAN_NUM)
    public String panNumber;
    @Sensitive(MaskType.DOB)
    public String dob;
    @Sensitive(MaskType.MOBILE)
    public String mobileNumber;
    @Sensitive(MaskType.EMAIL)
    public String email;
    public String updatedByUserId;
    public String remark;
    public String updatedTime;



    public String getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(String updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PolicyDetails() {
    }

    public PolicyDetails(String policyNumber, String firstName, String lastName, String panNumber, String dob, String mobileNumber, String email, String updatedByUserId, String remark, String updatedTime) {
        this.policyNumber = policyNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.panNumber = panNumber;
        this.dob = dob;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.updatedByUserId = updatedByUserId;
        this.remark = remark;
        this.updatedTime = updatedTime;

    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "PolicyDetails{" +
                "policyNumber='" + policyNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", dob='" + dob + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", updatedByUserId='" + updatedByUserId + '\'' +
                ", remark='" + remark + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }
}
