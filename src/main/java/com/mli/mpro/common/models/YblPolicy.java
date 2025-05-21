package com.mli.mpro.common.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class YblPolicy {

    private String policyNumber;
    @Sensitive(MaskType.NAME)
    private String insurerName;
    private String currentStatus;
    @Sensitive(MaskType.PAN_NUM)
    private String panNumber;
    @Sensitive(MaskType.AMOUNT)
    private String premiumAmount;
    private String flag;

    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(String premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Data{" +
                "insurerName='" + insurerName + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", premiumAmount='" + premiumAmount + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
