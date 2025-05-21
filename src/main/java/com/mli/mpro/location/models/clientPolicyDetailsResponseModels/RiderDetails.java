package com.mli.mpro.location.models.clientPolicyDetailsResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "riderCode", "riderSumAssured", "riderCoverStatusCode", "termOfRider" })
public class RiderDetails {

    @JsonProperty("riderCode")
    private String riderCode;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("riderSumAssured")
    private String riderSumAssured;
    @JsonProperty("riderCoverStatusCode")
    private String riderCoverStatusCode;
    @JsonProperty("termOfRider")
    private String termOfRider;

    public RiderDetails(String riderCode, String riderSumAssured, String riderCoverStatusCode, String termOfRider) {
	super();
	this.riderCode = riderCode;
	this.riderSumAssured = riderSumAssured;
	this.riderCoverStatusCode = riderCoverStatusCode;
	this.termOfRider = termOfRider;
    }

    public RiderDetails() {
	super();
    }

    @JsonProperty("riderCode")
    public String getRiderCode() {
	return riderCode;
    }

    @JsonProperty("riderCode")
    public void setRiderCode(String riderCode) {
	this.riderCode = riderCode;
    }

    @JsonProperty("riderSumAssured")
    public String getRiderSumAssured() {
	return riderSumAssured;
    }

    @JsonProperty("riderSumAssured")
    public void setRiderSumAssured(String riderSumAssured) {
	this.riderSumAssured = riderSumAssured;
    }

    @JsonProperty("riderCoverStatusCode")
    public String getRiderCoverStatusCode() {
	return riderCoverStatusCode;
    }

    @JsonProperty("riderCoverStatusCode")
    public void setRiderCoverStatusCode(String riderCoverStatusCode) {
	this.riderCoverStatusCode = riderCoverStatusCode;
    }

    @JsonProperty("termOfRider")
    public String getTermOfRider() {
	return termOfRider;
    }

    @JsonProperty("termOfRider")
    public void setTermOfRider(String termOfRider) {
	this.termOfRider = termOfRider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "RiderDetail [riderCode=" + riderCode + ", riderSumAssured=" + riderSumAssured + ", riderCoverStatusCode=" + riderCoverStatusCode
		+ ", termOfRider=" + termOfRider + "]";
    }

}
