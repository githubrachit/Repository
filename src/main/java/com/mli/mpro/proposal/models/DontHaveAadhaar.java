package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class DontHaveAadhaar {
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("neverAppliedState")
    private String neverAppliedState;

    public DontHaveAadhaar() {
    }

    public DontHaveAadhaar(String reason, String neverAppliedState) {
	super();
	this.reason = reason;
	this.neverAppliedState = neverAppliedState;
    }

    public String getReason() {
	return reason;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

    public String getNeverAppliedState() {
	return neverAppliedState;
    }

    public void setNeverAppliedState(String neverAppliedState) {
	this.neverAppliedState = neverAppliedState;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "DontHaveAadhaar [reason=" + reason + ", neverAppliedState=" + neverAppliedState + "]";
    }

}
