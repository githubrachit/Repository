package com.mli.mpro.location.policyNumberValidate.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoaOutputResponse {
    @JsonProperty("response")
    private SoaResponse soaResponse;

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
        return "SoaOutputResponse{" +
                "soaResponse=" + soaResponse +
                '}';
    }
}
