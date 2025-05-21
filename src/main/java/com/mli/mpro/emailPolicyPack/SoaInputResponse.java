package com.mli.mpro.emailPolicyPack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaInputResponse {

    @JsonProperty("response")
    private  SoaResponse soaResponse;

    public SoaResponse getSoaResponse() {
        return soaResponse;
    }

    public void setSoaResponse(SoaResponse soaResponse) {
        this.soaResponse = soaResponse;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaInputResponse{" +
                "soaResponse=" + soaResponse +
                '}';
    }
}
