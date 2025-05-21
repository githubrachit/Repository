package com.mli.mpro.location.altfinInquiry.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class InputRequest {

    @JsonProperty("policyNumber")
    private String policyNumber;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "InputRequest{" +
                "policyNumber='" + policyNumber + '\'' +
                '}';
    }
}
