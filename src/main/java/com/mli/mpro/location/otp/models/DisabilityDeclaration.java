package com.mli.mpro.location.otp.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class DisabilityDeclaration {
    @Sensitive(MaskType.NAME)
    private String customerName;
    @Sensitive(MaskType.MOBILE)
    private String mobileNo;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "customerName=" + customerName +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
