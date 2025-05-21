package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {

    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String soaAppId;

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
}
