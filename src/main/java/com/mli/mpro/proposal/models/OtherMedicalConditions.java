
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "anyCongenitalAnomaly", "anyCongenitalAnomalyDetails", "otherMedicalCondition", "otherMedicalConditionDetails" })
public class OtherMedicalConditions {

    @JsonProperty("anyCongenitalAnomaly")
    private String anyCongenitalAnomaly;
    @JsonProperty("anyCongenitalAnomalyDetails")
    private String anyCongenitalAnomalyDetails;
    @JsonProperty("otherMedicalCondition")
    private String otherMedicalCondition;
    @JsonProperty("otherMedicalConditionDetails")
    private String otherMedicalConditionDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OtherMedicalConditions() {
    }

    /**
     * 
     * @param anyCongenitalAnomalyDetails
     * @param anyCongenitalAnomaly
     * @param otherMedicalCondition
     * @param otherMedicalConditionDetails
     */
    public OtherMedicalConditions(String anyCongenitalAnomaly, String anyCongenitalAnomalyDetails, String otherMedicalCondition,
	    String otherMedicalConditionDetails) {
	super();
	this.anyCongenitalAnomaly = anyCongenitalAnomaly;
	this.anyCongenitalAnomalyDetails = anyCongenitalAnomalyDetails;
	this.otherMedicalCondition = otherMedicalCondition;
	this.otherMedicalConditionDetails = otherMedicalConditionDetails;
    }

    @JsonProperty("anyCongenitalAnomaly")
    public String getAnyCongenitalAnomaly() {
	return anyCongenitalAnomaly;
    }

    @JsonProperty("anyCongenitalAnomaly")
    public void setAnyCongenitalAnomaly(String anyCongenitalAnomaly) {
	this.anyCongenitalAnomaly = anyCongenitalAnomaly;
    }

    @JsonProperty("anyCongenitalAnomalyDetails")
    public String getAnyCongenitalAnomalyDetails() {
	return anyCongenitalAnomalyDetails;
    }

    @JsonProperty("anyCongenitalAnomalyDetails")
    public void setAnyCongenitalAnomalyDetails(String anyCongenitalAnomalyDetails) {
	this.anyCongenitalAnomalyDetails = anyCongenitalAnomalyDetails;
    }

    @JsonProperty("otherMedicalCondition")
    public String getOtherMedicalCondition() {
	return otherMedicalCondition;
    }

    @JsonProperty("otherMedicalCondition")
    public void setOtherMedicalCondition(String otherMedicalCondition) {
	this.otherMedicalCondition = otherMedicalCondition;
    }

    @JsonProperty("otherMedicalConditionDetails")
    public String getOtherMedicalConditionDetails() {
	return otherMedicalConditionDetails;
    }

    @JsonProperty("otherMedicalConditionDetails")
    public void setOtherMedicalConditionDetails(String otherMedicalConditionDetails) {
	this.otherMedicalConditionDetails = otherMedicalConditionDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "OtherMedicalConditions{" +
                "anyCongenitalAnomaly='" + anyCongenitalAnomaly + '\'' +
                ", anyCongenitalAnomalyDetails='" + anyCongenitalAnomalyDetails + '\'' +
                ", otherMedicalCondition='" + otherMedicalCondition + '\'' +
                ", otherMedicalConditionDetails='" + otherMedicalConditionDetails + '\'' +
                '}';
    }
}
