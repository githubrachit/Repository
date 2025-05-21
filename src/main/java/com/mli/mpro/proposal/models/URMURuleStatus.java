package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class URMURuleStatus {

    @JsonProperty("result")
    private String result;

    @JsonProperty("kickOutMsg")
    private String kickOutMsg;

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getKickOutMsg() {
	return kickOutMsg;
    }

    public void setKickOutMsg(String kickOutMsg) {
	this.kickOutMsg = kickOutMsg;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "Payload [result=" + result + ", kickOutMsg=" + kickOutMsg + "]";
    }
}
