package com.mli.mpro.common.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class BranchDetails {

    private String branchId;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    private String branchName;
    @Sensitive(MaskType.ADDRESS)
    private String addrLine1;
    private String addrLine2;
    private String addrLine3;
    @Sensitive(MaskType.PINCODE)
    private String pinCode;
    @Sensitive(MaskType.ADDRESS)
    private String city;
    @Sensitive(MaskType.ADDRESS)
    private String state;
    @Sensitive(MaskType.MOBILE)
    private String contactNo;
    private String latitude;
    private String longitude;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public String getAddrLine3() {
        return addrLine3;
    }

    public void setAddrLine3(String addrLine3) {
        this.addrLine3 = addrLine3;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "BranchDetails{" +
                "branchId='" + branchId + '\'' +
                ", branchName='" + branchName + '\'' +
                ", addrLine1='" + addrLine1 + '\'' +
                ", addrLine2='" + addrLine2 + '\'' +
                ", addrLine3='" + addrLine3 + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
