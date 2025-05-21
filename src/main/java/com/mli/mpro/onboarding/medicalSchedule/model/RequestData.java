package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "request" })
public class RequestData {

    @JsonProperty("request")
    private Request request;

    public RequestData(Request request) {
	super();
	this.request = request;
    }

    public RequestData() {
	super();
    }

    @JsonProperty("request")
    public Request getRequest() {
	return request;
    }

    @JsonProperty("request")
    public void setRequest(Request request) {
	this.request = request;
    }


}
