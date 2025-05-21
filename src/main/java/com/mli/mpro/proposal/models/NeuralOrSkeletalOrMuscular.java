
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "anyAilmentOfBonesOrjoints", "anyDisorderOfSpine", "anyDisorderOfMuscle", "anyDisorderOfENT", "specifyDetails" })
public class NeuralOrSkeletalOrMuscular {

    @JsonProperty("anyAilmentOfBonesOrjoints")
    private String anyAilmentOfBonesOrjoints;
    @JsonProperty("anyDisorderOfSpine")
    private String anyDisorderOfSpine;
    @JsonProperty("anyDisorderOfMuscle")
    private String anyDisorderOfMuscle;
    @JsonProperty("anyDisorderOfENT")
    private String anyDisorderOfENT;
    @JsonProperty("specifyDetails")
    private String specifyDetails;
    @JsonProperty("isBackPainSprainExcercise")
    private String isBackPainSprainExcercise;
    @JsonProperty("isFractureHistory")
    private String isFractureHistory;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NeuralOrSkeletalOrMuscular() {
    }

    /**
     * 
     * @param anyDisorderOfSpine
     * @param anyDisorderOfMuscle
     * @param specifyDetails
     * @param anyAilmentOfBonesOrjoints
     * @param anyDisorderOfENT
     */
    public NeuralOrSkeletalOrMuscular(String anyAilmentOfBonesOrjoints, String anyDisorderOfSpine, String anyDisorderOfMuscle, String anyDisorderOfENT,
	    String specifyDetails) {
	super();
	this.anyAilmentOfBonesOrjoints = anyAilmentOfBonesOrjoints;
	this.anyDisorderOfSpine = anyDisorderOfSpine;
	this.anyDisorderOfMuscle = anyDisorderOfMuscle;
	this.anyDisorderOfENT = anyDisorderOfENT;
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("anyAilmentOfBonesOrjoints")
    public String getAnyAilmentOfBonesOrjoints() {
	return anyAilmentOfBonesOrjoints;
    }

    @JsonProperty("anyAilmentOfBonesOrjoints")
    public void setAnyAilmentOfBonesOrjoints(String anyAilmentOfBonesOrjoints) {
	this.anyAilmentOfBonesOrjoints = anyAilmentOfBonesOrjoints;
    }

    @JsonProperty("anyDisorderOfSpine")
    public String getAnyDisorderOfSpine() {
	return anyDisorderOfSpine;
    }

    @JsonProperty("anyDisorderOfSpine")
    public void setAnyDisorderOfSpine(String anyDisorderOfSpine) {
	this.anyDisorderOfSpine = anyDisorderOfSpine;
    }

    @JsonProperty("anyDisorderOfMuscle")
    public String getAnyDisorderOfMuscle() {
	return anyDisorderOfMuscle;
    }

    @JsonProperty("anyDisorderOfMuscle")
    public void setAnyDisorderOfMuscle(String anyDisorderOfMuscle) {
	this.anyDisorderOfMuscle = anyDisorderOfMuscle;
    }

    @JsonProperty("anyDisorderOfENT")
    public String getAnyDisorderOfENT() {
	return anyDisorderOfENT;
    }

    @JsonProperty("anyDisorderOfENT")
    public void setAnyDisorderOfENT(String anyDisorderOfENT) {
	this.anyDisorderOfENT = anyDisorderOfENT;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    public String getIsBackPainSprainExcercise() {
        return isBackPainSprainExcercise;
    }

    public NeuralOrSkeletalOrMuscular setIsBackPainSprainExcercise(String isBackPainSprainExcercise) {
        this.isBackPainSprainExcercise = isBackPainSprainExcercise;
        return this;
    }

    public String getIsFractureHistory() {
        return isFractureHistory;
    }

    public NeuralOrSkeletalOrMuscular setIsFractureHistory(String isFractureHistory) {
        this.isFractureHistory = isFractureHistory;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "NeuralOrSkeletalOrMuscular{" +
                "anyAilmentOfBonesOrjoints='" + anyAilmentOfBonesOrjoints + '\'' +
                ", anyDisorderOfSpine='" + anyDisorderOfSpine + '\'' +
                ", anyDisorderOfMuscle='" + anyDisorderOfMuscle + '\'' +
                ", anyDisorderOfENT='" + anyDisorderOfENT + '\'' +
                ", specifyDetails='" + specifyDetails + '\'' +
                ", isBackPainSprainExcercise='" + isBackPainSprainExcercise + '\'' +
                ", isFractureHistory='" + isFractureHistory + '\'' +
                '}';
    }
}
