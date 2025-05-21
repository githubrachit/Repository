package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {
    @JsonProperty("AppId")
    public String appId;
    @JsonProperty("CorrelationId")
    public String correlationId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Header() {
    }

    public Header(String appId, String correlationId) {
        super();
        this.appId = appId;
        this.correlationId = correlationId;
    }


}
