package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {
    @JsonProperty("soaAppId")
    public String getSoaAppId() {
        return this.soaAppId; }
    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId; }
    String soaAppId;
    @JsonProperty("soaCorrelationId")
    public String getSoaCorrelationId() {
        return this.soaCorrelationId; }
    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId; }
    String soaCorrelationId;
}
