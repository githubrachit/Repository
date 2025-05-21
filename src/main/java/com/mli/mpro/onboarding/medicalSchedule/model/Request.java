package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "header", "category", "payload" })
public class Request {

    @JsonProperty("header")
    private Header header;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("payload")
    private Payload payload;

    public Request(Header header, Category category, Payload payload) {
	super();
	this.header = header;
	this.category = category;
	this.payload = payload;
    }

    public Request() {
	super();
    }

    @JsonProperty("header")
    public Header getHeader() {
	return header;
    }

    @JsonProperty("header")
    public void setHeader(Header header) {
	this.header = header;
    }

    @JsonProperty("category")
    public Category getCategory() {
	return category;
    }

    @JsonProperty("category")
    public void setCategory(Category category) {
	this.category = category;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
	return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
	this.payload = payload;
    }


}
