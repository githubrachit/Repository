package com.mli.mpro.location.policyNumberValidate.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Header {

    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String  soaAppId;

    public Header() {
    }

    public Header(String soaCorrelationId, String soaAppId) {
        this.soaCorrelationId = soaCorrelationId;
        this.soaAppId = soaAppId;
    }

    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    public String getSoaAppId() {
        return soaAppId;
    }

    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Header{" +
                "soaCorrelationId='" + soaCorrelationId + '\'' +
                ", soaAppId='" + soaAppId + '\'' +
                '}';
    }
}
