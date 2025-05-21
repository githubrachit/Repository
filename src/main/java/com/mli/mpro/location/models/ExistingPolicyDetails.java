package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ExistingPolicyDetails {
    private String policyNumber;
    private String policyStatus;
    private String companyName;
    private String productType;
    @Sensitive(MaskType.AMOUNT)
    private String sumAssured;
    private String recordLastUpdated;
    private String isPolicyBelongsToUser;
    private String rejectDesc;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getRecordLastUpdated() {
        return recordLastUpdated;
    }

    public void setRecordLastUpdated(String recordLastUpdated) {
        this.recordLastUpdated = recordLastUpdated;
    }

    public String getIsPolicyBelongsToUser() {
        return isPolicyBelongsToUser;
    }

    public void setIsPolicyBelongsToUser(String isPolicyBelongsToUser) {
        this.isPolicyBelongsToUser = isPolicyBelongsToUser;
    }

    public String getRejectDesc() {
        return rejectDesc;
    }

    public void setRejectDesc(String rejectDesc) {
        this.rejectDesc = rejectDesc;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "PolicyDetail{" +
                "policyNumber='" + policyNumber + '\'' +
                ", policyStatus='" + policyStatus + '\'' +
                ", companyName='" + companyName + '\'' +
                ", productType='" + productType + '\'' +
                ", sumAssured='" + sumAssured + '\'' +
                ", recordLastUpdated='" + recordLastUpdated + '\'' +
                ", isPolicyBelongsToUser='" + isPolicyBelongsToUser + '\'' +
                ", rejectDesc='" + rejectDesc + '\'' +
                '}';
    }
}
