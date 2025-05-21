
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cancer", "tumorAndMalignantGrowth", "leukemia", "anyBloodDisorder", "specifyDetails" })
public class BloodAndCellular {

    @JsonProperty("cancer")
    private String cancer;
    @JsonProperty("tumorAndMalignantGrowth")
    private String tumorAndMalignantGrowth;
    @JsonProperty("leukemia")
    private String leukemia;
    @JsonProperty("anyBloodDisorder")
    private String anyBloodDisorder;
    @JsonProperty("specifyDetails")
    private String specifyDetails;

    @JsonProperty("hivCancerTumorHistory")
    private String hivCancerTumorHistory;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BloodAndCellular() {
    }

    /**
     * 
     * @param specifyDetails
     * @param leukemia
     * @param tumorAndMalignantGrowth
     * @param anyBloodDisorder
     * @param cancer
     */
    public BloodAndCellular(String cancer, String tumorAndMalignantGrowth, String leukemia, String anyBloodDisorder, String specifyDetails, String hivCancerTumorHistory) {
	super();
	this.cancer = cancer;
	this.tumorAndMalignantGrowth = tumorAndMalignantGrowth;
	this.leukemia = leukemia;
	this.anyBloodDisorder = anyBloodDisorder;
	this.specifyDetails = specifyDetails;
    this.hivCancerTumorHistory = hivCancerTumorHistory;
    }

    @JsonProperty("cancer")
    public String getCancer() {
	return cancer;
    }

    @JsonProperty("cancer")
    public void setCancer(String cancer) {
	this.cancer = cancer;
    }

    @JsonProperty("tumorAndMalignantGrowth")
    public String getTumorAndMalignantGrowth() {
	return tumorAndMalignantGrowth;
    }

    @JsonProperty("tumorAndMalignantGrowth")
    public void setTumorAndMalignantGrowth(String tumorAndMalignantGrowth) {
	this.tumorAndMalignantGrowth = tumorAndMalignantGrowth;
    }

    @JsonProperty("leukemia")
    public String getLeukemia() {
	return leukemia;
    }

    @JsonProperty("leukemia")
    public void setLeukemia(String leukemia) {
	this.leukemia = leukemia;
    }

    @JsonProperty("anyBloodDisorder")
    public String getAnyBloodDisorder() {
	return anyBloodDisorder;
    }

    @JsonProperty("anyBloodDisorder")
    public void setAnyBloodDisorder(String anyBloodDisorder) {
	this.anyBloodDisorder = anyBloodDisorder;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("hivCancerTumorHistory")
    public String getHivCancerTumorHistory() {
        return hivCancerTumorHistory;
    }
    @JsonProperty("hivCancerTumorHistory")
    public void setHivCancerTumorHistory(String hivCancerTumorHistory) {
        this.hivCancerTumorHistory = hivCancerTumorHistory;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "BloodAndCellular [cancer=" + cancer + ", tumorAndMalignantGrowth=" + tumorAndMalignantGrowth + ", leukemia=" + leukemia + ", anyBloodDisorder="
		+ anyBloodDisorder + ", specifyDetails=" + specifyDetails + "]";
    }

}
