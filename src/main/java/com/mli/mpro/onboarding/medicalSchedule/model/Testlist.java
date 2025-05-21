package com.mli.mpro.onboarding.medicalSchedule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "testCase" })
public class Testlist {

    @JsonProperty("testCase")
    private List<TestCase> testCase;
    
    public Testlist(List<TestCase> testCase) {
	super();
	this.testCase = testCase;
    }

    public Testlist() {
	super();
    }

    @JsonProperty("testCase")
    public List<TestCase> getTestCase() {
	return testCase;
    }

    @JsonProperty("testCase")
    public void setTestCase(List<TestCase> testCase) {
	this.testCase = testCase;
    }



}
