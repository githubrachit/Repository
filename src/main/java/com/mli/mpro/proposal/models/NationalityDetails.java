
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nationality", "nriDetails" })
public class NationalityDetails {

    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("nriDetails")
    private NRIDetails nriDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NationalityDetails() {
    }

    /**
     * 
     * @param nationality
     * @param nriDetails
     */
    public NationalityDetails(String nationality, NRIDetails nriDetails) {
	super();
	this.nationality = nationality;
	this.nriDetails = nriDetails;
    }

    @JsonProperty("nationality")
    public String getNationality() {
	return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(String nationality) {
	this.nationality = nationality;
    }

    @JsonProperty("nriDetails")
    public NRIDetails getNriDetails() {
	return nriDetails;
    }

    @JsonProperty("nriDetails")
    public void setNriDetails(NRIDetails nriDetails) {
	this.nriDetails = nriDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "NationalityDetails [nationality=" + nationality + ", nriDetails=" + nriDetails + "]";
    }

}
