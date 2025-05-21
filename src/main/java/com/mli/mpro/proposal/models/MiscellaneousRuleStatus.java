package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class MiscellaneousRuleStatus {

    private String status;

    public MiscellaneousRuleStatus() {
	super();
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "MiscellaneousRuleStatus [status=" + status + "]";
    }

}
