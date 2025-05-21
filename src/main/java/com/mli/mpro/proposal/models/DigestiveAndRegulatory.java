
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "stomachOrintestinalDisorder", "jaundiceOrliverDisorder", "anyKidneyDisorders", "specifyDetails" })
public class DigestiveAndRegulatory {

    @JsonProperty("stomachOrintestinalDisorder")
    private String stomachOrintestinalDisorder;
    @JsonProperty("jaundiceOrliverDisorder")
    private String jaundiceOrliverDisorder;
    @JsonProperty("anyKidneyDisorders")
    private String anyKidneyDisorders;
    @JsonProperty("specifyDetails")
    private String specifyDetails;
    @JsonProperty("isJaundHistory")
    private String isJaundHistory;
    @JsonProperty("isStoneHistory")
    private String isStoneHistory;
    @JsonProperty("chronicLiverDisease")
    private String chronicLiverDisease;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DigestiveAndRegulatory() {
    }

    /**
     * 
     * @param stomachOrintestinalDisorder
     * @param specifyDetails
     * @param jaundiceOrliverDisorder
     * @param anyKidneyDisorders
     */
    public DigestiveAndRegulatory(String stomachOrintestinalDisorder, String jaundiceOrliverDisorder, String anyKidneyDisorders, String specifyDetails) {
	super();
	this.stomachOrintestinalDisorder = stomachOrintestinalDisorder;
	this.jaundiceOrliverDisorder = jaundiceOrliverDisorder;
	this.anyKidneyDisorders = anyKidneyDisorders;
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("stomachOrintestinalDisorder")
    public String getStomachOrintestinalDisorder() {
	return stomachOrintestinalDisorder;
    }

    @JsonProperty("stomachOrintestinalDisorder")
    public void setStomachOrintestinalDisorder(String stomachOrintestinalDisorder) {
	this.stomachOrintestinalDisorder = stomachOrintestinalDisorder;
    }

    @JsonProperty("jaundiceOrliverDisorder")
    public String getJaundiceOrliverDisorder() {
	return jaundiceOrliverDisorder;
    }

    @JsonProperty("jaundiceOrliverDisorder")
    public void setJaundiceOrliverDisorder(String jaundiceOrliverDisorder) {
	this.jaundiceOrliverDisorder = jaundiceOrliverDisorder;
    }

    @JsonProperty("anyKidneyDisorders")
    public String getAnyKidneyDisorders() {
	return anyKidneyDisorders;
    }

    @JsonProperty("anyKidneyDisorders")
    public void setAnyKidneyDisorders(String anyKidneyDisorders) {
	this.anyKidneyDisorders = anyKidneyDisorders;
    }

    @JsonProperty("specifyDetails")
    public String getSpecifyDetails() {
	return specifyDetails;
    }

    @JsonProperty("specifyDetails")
    public void setSpecifyDetails(String specifyDetails) {
	this.specifyDetails = specifyDetails;
    }

    public String getIsJaundHistory() {
        return isJaundHistory;
    }

    public DigestiveAndRegulatory setIsJaundHistory(String isJaundHistory) {
        this.isJaundHistory = isJaundHistory;
        return this;
    }

    public String getIsStoneHistory() {
        return isStoneHistory;
    }

    public DigestiveAndRegulatory setIsStoneHistory(String isStoneHistory) {
        this.isStoneHistory = isStoneHistory;
        return this;
    }

    public String getChronicLiverDisease() {
        return chronicLiverDisease;
    }

    public DigestiveAndRegulatory setChronicLiverDisease(String chronicLiverDisease) {
        this.chronicLiverDisease = chronicLiverDisease;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "DigestiveAndRegulatory{" +
                "stomachOrintestinalDisorder='" + stomachOrintestinalDisorder + '\'' +
                ", jaundiceOrliverDisorder='" + jaundiceOrliverDisorder + '\'' +
                ", anyKidneyDisorders='" + anyKidneyDisorders + '\'' +
                ", specifyDetails='" + specifyDetails + '\'' +
                ", isJaundHistory='" + isJaundHistory + '\'' +
                ", isStoneHistory='" + isStoneHistory + '\'' +
                ", chronicLiverDisease='" + chronicLiverDisease + '\'' +
                '}';
    }
}
