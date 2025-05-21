package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "response" })
public class ResponseData {

    @JsonProperty("response")
    private Response response;

    public ResponseData() {
	super();
    }

    @JsonProperty("response")
    public Response getResponse() {
	return response;
    }

    @JsonProperty("response")
    public void setResponse(Response response) {
	this.response = response;
    }


}