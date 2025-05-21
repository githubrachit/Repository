package com.mli.mpro.common.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ResumeJourneyRequest {
    private String policyNumber;
    @Sensitive(MaskType.DOB)
    private String dob;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResumeJourneyRequest{" +
                "policyNumber='" + policyNumber + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
