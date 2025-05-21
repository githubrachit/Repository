package com.mli.mpro.onboarding.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {

    private String correlationId;
    private String appId;

    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String soaAppId;

    public Header() {
    }
    public Header(String correlationId, String appId) {
        this.correlationId = correlationId;
        this.appId = appId;
    }
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
        return "Header [correlationId=" + correlationId + ", appId=" + appId + "]";
    }


}
