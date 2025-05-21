package com.mli.mpro.location.ivc.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaResponsePayload {
    @JsonProperty("businessMsg")
    private String businessMsg;

    public String getBusinessMsg() {
        return businessMsg;
    }

    public void setBusinessMsg(String businessMsg) {
        this.businessMsg = businessMsg;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SoaResponsePayload{" +
                "businessMsg='" + businessMsg + '\'' +
                '}';
    }
}
