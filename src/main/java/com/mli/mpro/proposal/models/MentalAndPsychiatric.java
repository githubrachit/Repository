
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "mentalOrPsychiatricAilment", "nervousSystemDisease", "specifyDetails" })
public class MentalAndPsychiatric {

    @JsonProperty("mentalOrPsychiatricAilment")
    private String mentalOrPsychiatricAilment;
    @JsonProperty("nervousSystemDisease")
    private String nervousSystemDisease;
    @JsonProperty("specifyDetails")
    private String specifyDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MentalAndPsychiatric() {
    }

    /**
     * 
     * @param specifyDetails
     * @param nervousSystemDisease
     * @param mentalOrPsychiatricAilment
     */
    public MentalAndPsychiatric(String mentalOrPsychiatricAilment, String nervousSystemDisease, String specifyDetails) {
	super();
	this.mentalOrPsychiatricAilment = mentalOrPsychiatricAilment;
	this.nervousSystemDisease = nervousSystemDisease;
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("mentalOrPsychiatricAilment")
    public String getMentalOrPsychiatricAilment() {
	return mentalOrPsychiatricAilment;
    }

    @JsonProperty("mentalOrPsychiatricAilment")
    public void setMentalOrPsychiatricAilment(String mentalOrPsychiatricAilment) {
	this.mentalOrPsychiatricAilment = mentalOrPsychiatricAilment;
    }

    @JsonProperty("nervousSystemDisease")
    public String getNervousSystemDisease() {
	return nervousSystemDisease;
    }

    @JsonProperty("nervousSystemDisease")
    public void setNervousSystemDisease(String nervousSystemDisease) {
	this.nervousSystemDisease = nervousSystemDisease;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MentalAndPsychiatric{" +
                "mentalOrPsychiatricAilment='" + mentalOrPsychiatricAilment + '\'' +
                ", nervousSystemDisease='" + nervousSystemDisease + '\'' +
                ", specifyDetails='" + specifyDetails + '\'' +
                '}';
    }
}
