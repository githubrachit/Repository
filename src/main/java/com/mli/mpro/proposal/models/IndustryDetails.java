package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

public class IndustryDetails {

    @JsonProperty("industryType")
    private String industryType;
    @JsonProperty("industryTypeOthers")
    private String industryTypeOthers;
    @JsonProperty("industryInfo")
    private IndustryInfo industryInfo;

    public IndustryDetails() {
    }

    public IndustryDetails(String industryType, IndustryInfo industryInfo) {
	super();
	this.industryType = industryType;
	this.industryInfo = industryInfo;
    }

    public String getIndustryType() {
	return industryType;
    }

    public void setIndustryType(String industryType) {
	this.industryType = industryType;
    }

    public IndustryInfo getIndustryInfo() {
	return industryInfo;
    }

    public void setIndustryInfo(IndustryInfo industryInfo) {
	this.industryInfo = industryInfo;
    }

    public String getIndustryTypeOthers() { return industryTypeOthers; }

    public void setIndustryTypeOthers(String industryTypeOthers) { this.industryTypeOthers = industryTypeOthers; }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", IndustryDetails.class.getSimpleName() + "[", "]")
                .add("industryType='" + industryType + "'")
                .add("industryTypeOthers='" + industryTypeOthers + "'")
                .add("industryInfo=" + industryInfo)
                .toString();
    }

}
