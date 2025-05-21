package com.mli.mpro.underwriting.clientPolicyDetailsResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "riderCode", "riderSumAssured", "riderCoverStatusCode", "termOfRider", "riderClientId", "riderCoverStatusCodeDesc"})
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
    @JsonProperty("riderClientId")
    private String riderClientId;
    @JsonProperty("riderCoverStatusCodeDesc")
    private String riderCoverStatusCodeDesc;
    
    @JsonProperty("riderClientId")
    public String getRiderClientId() {
		return riderClientId;
	}
    
    @JsonProperty("riderClientId")
	public void setRiderClientId(String riderClientId) {
		this.riderClientId = riderClientId;
	}
    
	@JsonProperty("riderCoverStatusCodeDesc")
	public String getRiderCoverStatusCodeDesc() {
		return riderCoverStatusCodeDesc;
	}
	
	@JsonProperty("riderCoverStatusCodeDesc")
	public void setRiderCoverStatusCodeDesc(String riderCoverStatusCodeDesc) {
		this.riderCoverStatusCodeDesc = riderCoverStatusCodeDesc;
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

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "RiderDetails [riderCode=" + riderCode + ", riderSumAssured=" + riderSumAssured
				+ ", riderCoverStatusCode=" + riderCoverStatusCode + ", termOfRider=" + termOfRider + ", riderClientId="
				+ riderClientId + ", riderCoverStatusCodeDesc=" + riderCoverStatusCodeDesc + "]";
	}
}
