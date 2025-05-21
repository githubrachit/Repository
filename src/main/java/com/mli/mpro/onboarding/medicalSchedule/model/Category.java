package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "categoryName", "RequestType","vendor" })
public class Category {

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("RequestType")
    private String requestType;

    @JsonProperty("vendor")
    private String vendor;

    public Category() {
	super();
    }

    public Category(String categoryName, String requestType,String vendor) {
	super();
	this.categoryName = categoryName;
	this.requestType = requestType;
	this.vendor=vendor;
    }

    @JsonProperty("categoryName")
    public String getCategoryName() {
	return categoryName;
    }

    @JsonProperty("categoryName")
    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }

    @JsonProperty("RequestType")
    public String getRequestType() {
	return requestType;
    }

    @JsonProperty("RequestType")
    public void setRequestType(String requestType) {
	this.requestType = requestType;
    }

    @JsonProperty("vendor")
    public String getVendor() {
		return vendor;
	}
    @JsonProperty("vendor")
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}
