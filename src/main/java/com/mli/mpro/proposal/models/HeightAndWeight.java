
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "heightMetric", "height", "weight", "weightChangeReason", "weightChangeReasonOthers" })
public class HeightAndWeight {

    @JsonProperty("heightMetric")
    private String heightMetric;
    @JsonProperty("height")
    private String height;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("weightChangeReason")
    private String weightChangeReason;
    @JsonProperty("weightChangeReasonOthers")
    private String weightChangeReasonOthers;
    @JsonProperty("isWeightChanged")
    private String isWeightChanged;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HeightAndWeight() {
    }

    /**
     * 
     * @param heightMetric
     * @param weight
     * @param height
     * @param weightChangeReasonOthers
     * @param weightChangeReason
     */
    public HeightAndWeight(String heightMetric, String height, String weight, String weightChangeReason, String weightChangeReasonOthers, String isWeightChanged) {
	super();
	this.heightMetric = heightMetric;
	this.height = height;
	this.weight = weight;
	this.weightChangeReason = weightChangeReason;
	this.weightChangeReasonOthers = weightChangeReasonOthers;
	this.isWeightChanged = isWeightChanged;
    }

    @JsonProperty("heightMetric")
    public String getHeightMetric() {
	return heightMetric;
    }

    @JsonProperty("heightMetric")
    public void setHeightMetric(String heightMetric) {
	this.heightMetric = heightMetric;
    }

    @JsonProperty("height")
    public String getHeight() {
	return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
	this.height = height;
    }

    @JsonProperty("weight")
    public String getWeight() {
	return weight;
    }

    @JsonProperty("weight")
    public void setWeight(String weight) {
	this.weight = weight;
    }

    @JsonProperty("weightChangeReason")
    public String getWeightChangeReason() {
	return weightChangeReason;
    }

    @JsonProperty("weightChangeReason")
    public void setWeightChangeReason(String weightChangeReason) {
	this.weightChangeReason = weightChangeReason;
    }

    @JsonProperty("weightChangeReasonOthers")
    public String getWeightChangeReasonOthers() {
	return weightChangeReasonOthers;
    }

    @JsonProperty("weightChangeReasonOthers")
    public void setWeightChangeReasonOthers(String weightChangeReasonOthers) {
	this.weightChangeReasonOthers = weightChangeReasonOthers;
    }

    @JsonProperty("isWeightChanged")
    public String getIsWeightChanged() {
        return isWeightChanged;
    }

    @JsonProperty("isWeightChanged")
    public void setIsWeightChanged(String isWeightChanged) {
        this.isWeightChanged = isWeightChanged;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "HeightAndWeight [heightMetric=" + heightMetric + ", height=" + height + ", weight=" + weight + ", weightChangeReason=" + weightChangeReason
		+ ", weightChangeReasonOthers=" + weightChangeReasonOthers + ", isWeightChanged=" + isWeightChanged + "]";
    }

}
