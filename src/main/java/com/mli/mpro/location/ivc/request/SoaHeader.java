package com.mli.mpro.location.ivc.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class SoaHeader {
    @JsonProperty("correlationId")
    private String correlationId;
    @JsonProperty("appId")
    private String appId;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SoaHeader{" +
                "correlationId='" + correlationId + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}