package com.mli.mpro.emailPolicyPack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaResponse {

    @JsonProperty("responseData")
   private  SoaResponseData soaResponseData;

    public SoaResponseData getSoaResponseData() {
        return soaResponseData;
    }

    public void setSoaResponseData(SoaResponseData soaResponseData) {
        this.soaResponseData = soaResponseData;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SoaResponse{" +
                "soaResponseData=" + soaResponseData +
                '}';
    }
}
