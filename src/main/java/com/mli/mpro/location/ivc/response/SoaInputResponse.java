package com.mli.mpro.location.ivc.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaInputResponse {
    @JsonProperty("response")
    private SoaResponse response;

    public SoaResponse getResponse() {
        return response;
    }

    public void setResponse(SoaResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SoaInputResponse{" +
                "response=" + response +
                '}';
    }
}
