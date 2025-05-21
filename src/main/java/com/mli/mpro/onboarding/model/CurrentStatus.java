package com.mli.mpro.onboarding.model;

import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload;

public class CurrentStatus {
    
    private String policyNumber;
    private String systemType;
    private String infoType;
    private String wiStatus; 
    private String wiSubStatus;
    private String wiStatusDate;
    private String customerMilestone;
    private String customerSubMilestone;
    private String sellerMilestone;
    private String sellerSubMilestone;
    private com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.ResponsePayload dolphinResponse;
    
    public CurrentStatus() {
       //Constructor
    }

    public String getPolicyNumber() {
        return policyNumber;
    }
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    public String getSystemType() {
        return systemType;
    }
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
    public String getInfoType() {
        return infoType;
    }
    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }
    public String getWiStatus() {
        return wiStatus;
    }
    public void setWiStatus(String wiStatus) {
        this.wiStatus = wiStatus;
    }
    public String getWiSubStatus() {
        return wiSubStatus;
    }
    public void setWiSubStatus(String wiSubStatus) {
        this.wiSubStatus = wiSubStatus;
    }
    public String getWiStatusDate() {
        return wiStatusDate;
    }
    public void setWiStatusDate(String wiStatusDate) {
        this.wiStatusDate = wiStatusDate;
    }
    public String getCustomerMilestone() {
        return customerMilestone;
    }
    public void setCustomerMilestone(String customerMilestone) {
        this.customerMilestone = customerMilestone;
    }
    public String getCustomerSubMilestone() {
        return customerSubMilestone;
    }
    public void setCustomerSubMilestone(String customerSubMilestone) {
        this.customerSubMilestone = customerSubMilestone;
    }

    public String getSellerMilestone() {
        return sellerMilestone;
    }

    public void setSellerMilestone(String sellerMilestone) {
        this.sellerMilestone = sellerMilestone;
    }

    public String getSellerSubMilestone() {
        return sellerSubMilestone;
    }

    public void setSellerSubMilestone(String sellerSubMilestone) {
        this.sellerSubMilestone = sellerSubMilestone;
    }

    public ResponsePayload getDolphinResponse() { return dolphinResponse; }

    public void setDolphinResponse(ResponsePayload dolphinResponse) { this.dolphinResponse = dolphinResponse; }

    @Override
    public String toString() {
        return "CurrentStatus{" +
                "policyNumber='" + policyNumber + '\'' +
                ", systemType='" + systemType + '\'' +
                ", infoType='" + infoType + '\'' +
                ", wiStatus='" + wiStatus + '\'' +
                ", wiSubStatus='" + wiSubStatus + '\'' +
                ", wiStatusDate='" + wiStatusDate + '\'' +
                ", customerMilestone='" + customerMilestone + '\'' +
                ", customerSubMilestone='" + customerSubMilestone + '\'' +
                ", sellerMilestone='" + sellerMilestone + '\'' +
                ", sellerSubMilestone='" + sellerSubMilestone + '\'' +
                ", dolphinResponse=" + dolphinResponse +
                '}';
    }


}
