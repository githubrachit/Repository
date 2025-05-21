
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "highBloodPressure", "chestPain", "heartAttack", "stroke", "anyOtherHeartCondition", "specifyDetails", "highBloodPressureDetails" })
public class Cardio {

    @JsonProperty("highBloodPressure")
    private String highBloodPressure;
    @JsonProperty("chestPain")
    private String chestPain;
    @JsonProperty("heartAttack")
    private String heartAttack;
    @JsonProperty("stroke")
    private String stroke;
    @JsonProperty("anyOtherHeartCondition")
    private String anyOtherHeartCondition;
    @JsonProperty("specifyDetails")
    private String specifyDetails;
    @JsonProperty("highBloodPressureDetails")
    private HighBloodPressureDetails highBloodPressureDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Cardio() {
    }

    /**
     * 
     * @param stroke
     * @param highBloodPressureDetails
     * @param specifyDetails
     * @param highBloodPressure
     * @param anyOtherHeartCondition
     * @param heartAttack
     * @param chestPain
     */
    public Cardio(String highBloodPressure, String chestPain, String heartAttack, String stroke, String anyOtherHeartCondition, String specifyDetails,
	    HighBloodPressureDetails highBloodPressureDetails) {
	super();
	this.highBloodPressure = highBloodPressure;
	this.chestPain = chestPain;
	this.heartAttack = heartAttack;
	this.stroke = stroke;
	this.anyOtherHeartCondition = anyOtherHeartCondition;
	this.specifyDetails = specifyDetails;
	this.highBloodPressureDetails = highBloodPressureDetails;
    }

    @JsonProperty("highBloodPressure")
    public String getHighBloodPressure() {
	return highBloodPressure;
    }

    @JsonProperty("highBloodPressure")
    public void setHighBloodPressure(String highBloodPressure) {
	this.highBloodPressure = highBloodPressure;
    }

    @JsonProperty("chestPain")
    public String getChestPain() {
	return chestPain;
    }

    @JsonProperty("chestPain")
    public void setChestPain(String chestPain) {
	this.chestPain = chestPain;
    }

    @JsonProperty("heartAttack")
    public String getHeartAttack() {
	return heartAttack;
    }

    @JsonProperty("heartAttack")
    public void setHeartAttack(String heartAttack) {
	this.heartAttack = heartAttack;
    }

    @JsonProperty("stroke")
    public String getStroke() {
	return stroke;
    }

    @JsonProperty("stroke")
    public void setStroke(String stroke) {
	this.stroke = stroke;
    }

    @JsonProperty("anyOtherHeartCondition")
    public String getAnyOtherHeartCondition() {
	return anyOtherHeartCondition;
    }

    @JsonProperty("anyOtherHeartCondition")
    public void setAnyOtherHeartCondition(String anyOtherHeartCondition) {
	this.anyOtherHeartCondition = anyOtherHeartCondition;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("highBloodPressureDetails")
    public HighBloodPressureDetails getHighBloodPressureDetails() {
	return highBloodPressureDetails;
    }

    @JsonProperty("highBloodPressureDetails")
    public void setHighBloodPressureDetails(HighBloodPressureDetails highBloodPressureDetails) {
	this.highBloodPressureDetails = highBloodPressureDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "Cardio [highBloodPressure=" + highBloodPressure + ", chestPain=" + chestPain + ", heartAttack=" + heartAttack + ", stroke=" + stroke
		+ ", anyOtherHeartCondition=" + anyOtherHeartCondition + ", specifyDetails=" + specifyDetails + ", highBloodPressureDetails="
		+ highBloodPressureDetails + "]";
    }

}
