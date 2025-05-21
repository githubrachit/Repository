package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "packageName", "testName" })
public class TestCase {

    @JsonProperty("packageName")
    private String packageName;
    @JsonProperty("testName")
    private String testName;

    public TestCase(String packageName, String testName) {
	super();
	this.packageName = packageName;
	this.testName = testName;
    }

    public TestCase() {
	super();
    }

    @JsonProperty("packageName")
    public String getPackageName() {
	return packageName;
    }

    @JsonProperty("packageName")
    public void setPackageName(String packageName) {
	this.packageName = packageName;
    }

    @JsonProperty("testName")
    public String getTestName() {
	return testName;
    }

    @JsonProperty("testName")
    public void setTestName(String testName) {
	this.testName = testName;
    }

}
