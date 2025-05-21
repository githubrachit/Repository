
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "highBloodSugar", "diabetes", "thyroid", "anyOtherHormonalDisorder", "specifyDetails", "highBloodSugarAndDiabetesDetails" })
public class Hormonal {

    @JsonProperty("highBloodSugar")
    private String highBloodSugar;
    @JsonProperty("diabetes")
    private String diabetes;
    @JsonProperty("thyroid")
    private String thyroid;
    @JsonProperty("anyOtherHormonalDisorder")
    private String anyOtherHormonalDisorder;
    @JsonProperty("specifyDetails")
    private String specifyDetails;
    @JsonProperty("highBloodSugarAndDiabetesDetails")
    private HighBloodSugarAndDiabetesDetails highBloodSugarAndDiabetesDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Hormonal() {
    }

    /**
     * 
     * @param highBloodSugar
     * @param specifyDetails
     * @param thyroid
     * @param diabetes
     * @param anyOtherHormonalDisorder
     * @param highBloodSugarAndDiabetesDetails
     */
    public Hormonal(String highBloodSugar, String diabetes, String thyroid, String anyOtherHormonalDisorder, String specifyDetails,
	    HighBloodSugarAndDiabetesDetails highBloodSugarAndDiabetesDetails) {
	super();
	this.highBloodSugar = highBloodSugar;
	this.diabetes = diabetes;
	this.thyroid = thyroid;
	this.anyOtherHormonalDisorder = anyOtherHormonalDisorder;
	this.specifyDetails = specifyDetails;
	this.highBloodSugarAndDiabetesDetails = highBloodSugarAndDiabetesDetails;
    }

    @JsonProperty("highBloodSugar")
    public String getHighBloodSugar() {
	return highBloodSugar;
    }

    @JsonProperty("highBloodSugar")
    public void setHighBloodSugar(String highBloodSugar) {
	this.highBloodSugar = highBloodSugar;
    }

    @JsonProperty("diabetes")
    public String getDiabetes() {
	return diabetes;
    }

    @JsonProperty("diabetes")
    public void setDiabetes(String diabetes) {
	this.diabetes = diabetes;
    }

    @JsonProperty("thyroid")
    public String getThyroid() {
	return thyroid;
    }

    @JsonProperty("thyroid")
    public void setThyroid(String thyroid) {
	this.thyroid = thyroid;
    }

    @JsonProperty("anyOtherHormonalDisorder")
    public String getAnyOtherHormonalDisorder() {
	return anyOtherHormonalDisorder;
    }

    @JsonProperty("anyOtherHormonalDisorder")
    public void setAnyOtherHormonalDisorder(String anyOtherHormonalDisorder) {
	this.anyOtherHormonalDisorder = anyOtherHormonalDisorder;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("highBloodSugarAndDiabetesDetails")
    public HighBloodSugarAndDiabetesDetails getHighBloodSugarAndDiabetesDetails() {
	return highBloodSugarAndDiabetesDetails;
    }

    @JsonProperty("highBloodSugarAndDiabetesDetails")
    public void setHighBloodSugarAndDiabetesDetails(HighBloodSugarAndDiabetesDetails highBloodSugarAndDiabetesDetails) {
	this.highBloodSugarAndDiabetesDetails = highBloodSugarAndDiabetesDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Hormonal{" +
                "highBloodSugar='" + highBloodSugar + '\'' +
                ", diabetes='" + diabetes + '\'' +
                ", thyroid='" + thyroid + '\'' +
                ", anyOtherHormonalDisorder='" + anyOtherHormonalDisorder + '\'' +
                ", specifyDetails='" + specifyDetails + '\'' +
                ", highBloodSugarAndDiabetesDetails=" + highBloodSugarAndDiabetesDetails +
                '}';
    }
}
