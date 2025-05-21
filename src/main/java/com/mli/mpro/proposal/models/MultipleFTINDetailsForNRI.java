package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ftinNumber", "ftinIssuingCountry" })
public class MultipleFTINDetailsForNRI {

    @JsonProperty("ftinNumber")
    private String ftinNumber;
    @JsonProperty("ftinIssuingCountry")
    private String ftinIssuingCountry;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MultipleFTINDetailsForNRI() {
    }

    /**
     * 
     * @param ftinNumber
     * @param ftinIssuingCountry
     */
    public MultipleFTINDetailsForNRI(String ftinNumber, String ftinIssuingCountry) {
	super();
	this.ftinNumber = ftinNumber;
	this.ftinIssuingCountry = ftinIssuingCountry;
    }

    @JsonProperty("ftinNumber")
    public String getFtinNumber() {
	return ftinNumber;
    }

    @JsonProperty("ftinNumber")
    public void setFtinNumber(String ftinNumber) {
	this.ftinNumber = ftinNumber;
    }

    @JsonProperty("ftinIssuingCountry")
    public String getFtinIssuingCountry() {
	return ftinIssuingCountry;
    }

    @JsonProperty("ftinIssuingCountry")
    public void setFtinIssuingCountry(String ftinIssuingCountry) {
	this.ftinIssuingCountry = ftinIssuingCountry;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "MultipleFTINDetailsForNRI [ftinNumber=" + ftinNumber + ", ftinIssuingCountry=" + ftinIssuingCountry + "]";
    }

}