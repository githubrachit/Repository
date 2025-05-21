
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "asthma", "anyOtherRespiratoryDisorder", "tuberculosis", "specifyDetails", "asthmaDetails", "respiratoryDisorderDetails" })
public class Respiratory {

    @JsonProperty("asthma")
    private String asthma;
    @JsonProperty("anyOtherRespiratoryDisorder")
    private String anyOtherRespiratoryDisorder;
    @JsonProperty("tuberculosis")
    private String tuberculosis;
    @JsonProperty("specifyDetails")
    private String specifyDetails;
    @JsonProperty("asthmaDetails")
    private AsthmaDetails asthmaDetails;
    @JsonProperty("respiratoryDisorderDetails")
    private RespiratoryDisorderDetails respiratoryDisorderDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Respiratory() {
    }

    /**
     * 
     * @param respiratoryDisorderDetails
     * @param tuberculosis
     * @param specifyDetails
     * @param asthma
     * @param asthmaDetails
     * @param anyOtherRespiratoryDisorder
     */
    public Respiratory(String asthma, String anyOtherRespiratoryDisorder, String tuberculosis, String specifyDetails, AsthmaDetails asthmaDetails,
	    RespiratoryDisorderDetails respiratoryDisorderDetails) {
	super();
	this.asthma = asthma;
	this.anyOtherRespiratoryDisorder = anyOtherRespiratoryDisorder;
	this.tuberculosis = tuberculosis;
	this.specifyDetails = specifyDetails;
	this.asthmaDetails = asthmaDetails;
	this.respiratoryDisorderDetails = respiratoryDisorderDetails;
    }

    @JsonProperty("asthma")
    public String getAsthma() {
	return asthma;
    }

    @JsonProperty("asthma")
    public void setAsthma(String asthma) {
	this.asthma = asthma;
    }

    @JsonProperty("anyOtherRespiratoryDisorder")
    public String getAnyOtherRespiratoryDisorder() {
	return anyOtherRespiratoryDisorder;
    }

    @JsonProperty("anyOtherRespiratoryDisorder")
    public void setAnyOtherRespiratoryDisorder(String anyOtherRespiratoryDisorder) {
	this.anyOtherRespiratoryDisorder = anyOtherRespiratoryDisorder;
    }

    @JsonProperty("tuberculosis")
    public String getTuberculosis() {
	return tuberculosis;
    }

    @JsonProperty("tuberculosis")
    public void setTuberculosis(String tuberculosis) {
	this.tuberculosis = tuberculosis;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("asthmaDetails")
    public AsthmaDetails getAsthmaDetails() {
	return asthmaDetails;
    }

    @JsonProperty("asthmaDetails")
    public void setAsthmaDetails(AsthmaDetails asthmaDetails) {
	this.asthmaDetails = asthmaDetails;
    }

    @JsonProperty("respiratoryDisorderDetails")
    public RespiratoryDisorderDetails getRespiratoryDisorderDetails() {
	return respiratoryDisorderDetails;
    }

    @JsonProperty("respiratoryDisorderDetails")
    public void setRespiratoryDisorderDetails(RespiratoryDisorderDetails respiratoryDisorderDetails) {
	this.respiratoryDisorderDetails = respiratoryDisorderDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Respiratory{" +
                "asthma='" + asthma + '\'' +
                ", anyOtherRespiratoryDisorder='" + anyOtherRespiratoryDisorder + '\'' +
                ", tuberculosis='" + tuberculosis + '\'' +
                ", specifyDetails='" + specifyDetails + '\'' +
                ", asthmaDetails=" + asthmaDetails +
                ", respiratoryDisorderDetails=" + respiratoryDisorderDetails +
                '}';
    }
}
