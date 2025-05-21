package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "soaCorrelationId", "soaAppId" })
public class Header {

    @JsonProperty("soaCorrelationId")
    private String soaCorrelationId;
    @JsonProperty("soaAppId")
    private String soaAppId;

    public Header() {
	super();
    }

    public Header(String soaCorrelationId, String soaAppId) {
	super();
	this.soaCorrelationId = soaCorrelationId;
	this.soaAppId = soaAppId;
    }

    @JsonProperty("soaCorrelationId")
    public String getSoaCorrelationId() {
	return soaCorrelationId;
    }

    @JsonProperty("soaCorrelationId")
    public void setSoaCorrelationId(String soaCorrelationId) {
	this.soaCorrelationId = soaCorrelationId;
    }

    @JsonProperty("soaAppId")
    public String getSoaAppId() {
	return soaAppId;
    }

    @JsonProperty("soaAppId")
    public void setSoaAppId(String soaAppId) {
	this.soaAppId = soaAppId;
    }


}