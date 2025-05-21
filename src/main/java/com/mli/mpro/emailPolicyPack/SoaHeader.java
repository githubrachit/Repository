package com.mli.mpro.emailPolicyPack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaHeader {
    @JsonProperty("soaMsgVersion")
    private String soaMsgVersion;
    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String soaAppId;

    public String getSoaMsgVersion() {
        return soaMsgVersion;
    }

    public void setSoaMsgVersion(String soaMsgVersion) {
        this.soaMsgVersion = soaMsgVersion;
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
        return "SoaHeader{" +
                "soaMsgVersion='" + soaMsgVersion + '\'' +
                ", soaCorrelationId='" + soaCorrelationId + '\'' +
                ", soaAppId='" + soaAppId + '\'' +
                '}';
    }
}
