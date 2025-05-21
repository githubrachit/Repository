package com.mli.mpro.location.policyNumberValidate.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaInputRequest {

    @JsonProperty("request")
    private SoaRequest soaRequest;

    public SoaInputRequest() {
    }

    public SoaInputRequest(SoaRequest soaRequest) {
        this.soaRequest = soaRequest;
    }

    public SoaRequest getRequest() {
        return soaRequest;
    }

    public void setRequest(SoaRequest soaRequest) {
        this.soaRequest = soaRequest;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaInputRequest{" +
                "soaRequest=" + soaRequest +
                '}';
    }
}
