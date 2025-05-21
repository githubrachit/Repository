package com.mli.mpro.location.altfinInquiry.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.validation.constraints.Pattern;

public class AltfinPayload {

    @JsonProperty("policyNumber")
    @Pattern(regexp = "^[0-9]{0,9}$", message = "Policy number must be exactly 9 digits.")
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
        return "AltfinPayload{" +
                "policyNumber='" + policyNumber + '\'' +
                '}';
    }
}
