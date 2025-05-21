package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"soaCorrelationId", "soaAppId"})
public class Header {
    @JsonProperty("soaCorrelationId")
    @JsonAlias("requestId")
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

    @Override
    public String toString() {
        return "Header{" + "soaCorrelationId='" + soaCorrelationId + '\'' + ", soaAppId='" + soaAppId + '\'' + '}';
    }
}
