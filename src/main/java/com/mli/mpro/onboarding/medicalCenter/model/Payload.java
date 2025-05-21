package com.mli.mpro.onboarding.medicalCenter.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Date;

public class Payload {
    private String policyNumber="";
    private String quoteId="";
    @Sensitive(MaskType.PINCODE)
    private String pincode;
    private String vendor;

    public Payload() {

    }

    public Payload(String policyNumber, String quoteId, String pincode, String vendor) {
        this.policyNumber = policyNumber;
        this.quoteId = quoteId;
        this.pincode = pincode;
        this.vendor = vendor;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "policyNumber='" + policyNumber + '\'' +
                ", quoteId='" + quoteId + '\'' +
                ", pincode='" + pincode + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}
