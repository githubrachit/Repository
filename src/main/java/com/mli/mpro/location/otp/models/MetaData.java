package com.mli.mpro.location.otp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaData {
    @JsonProperty("env")
    private String env;
    @JsonProperty("requestId")
    private String requestId;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Metadata [env=" + env + ", requestId=" + requestId + "]";
    }
}
