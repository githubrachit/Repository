package com.mli.mpro.location.ivc.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaInputRequest {
    @JsonProperty("request")
    private SoaRequest request;

    public SoaRequest getRequest() {
        return request;
    }

    public void setRequest(SoaRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "InputRequest{" +
                "request=" + request +
                '}';
    }
}