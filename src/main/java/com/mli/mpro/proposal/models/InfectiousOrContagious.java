
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "hepatitisB", "hepatitisC", "hivInfection", "aidsRelated", "anySTDDisease", "anyGynaecologicalDisorder", "specifyDetails" })
public class InfectiousOrContagious {

    @JsonProperty("hepatitisB")
    private String hepatitisB;
    @JsonProperty("hepatitisC")
    private String hepatitisC;
    @JsonProperty("hivInfection")
    private String hivInfection;
    @JsonProperty("aidsRelated")
    private String aidsRelated;
    @JsonProperty("anySTDDisease")
    private String anySTDDisease;
    @JsonProperty("anyGynaecologicalDisorder")
    private String anyGynaecologicalDisorder;
    @JsonProperty("specifyDetails")
    private String specifyDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InfectiousOrContagious() {
    }

    /**
     * 
     * @param aidsRelated
     * @param anySTDDisease
     * @param hepatitisB
     * @param specifyDetails
     * @param anyGynaecologicalDisorder
     * @param hepatitisC
     * @param hivInfection
     */
    public InfectiousOrContagious(String hepatitisB, String hepatitisC, String hivInfection, String aidsRelated, String anySTDDisease,
	    String anyGynaecologicalDisorder, String specifyDetails) {
	super();
	this.hepatitisB = hepatitisB;
	this.hepatitisC = hepatitisC;
	this.hivInfection = hivInfection;
	this.aidsRelated = aidsRelated;
	this.anySTDDisease = anySTDDisease;
	this.anyGynaecologicalDisorder = anyGynaecologicalDisorder;
	this.specifyDetails = specifyDetails;
    }

    @JsonProperty("hepatitisB")
    public String getHepatitisB() {
	return hepatitisB;
    }

    @JsonProperty("hepatitisB")
    public void setHepatitisB(String hepatitisB) {
	this.hepatitisB = hepatitisB;
    }

    @JsonProperty("hepatitisC")
    public String getHepatitisC() {
	return hepatitisC;
    }

    @JsonProperty("hepatitisC")
    public void setHepatitisC(String hepatitisC) {
	this.hepatitisC = hepatitisC;
    }

    @JsonProperty("hivInfection")
    public String getHivInfection() {
	return hivInfection;
    }

    @JsonProperty("hivInfection")
    public void setHivInfection(String hivInfection) {
	this.hivInfection = hivInfection;
    }

    @JsonProperty("aidsRelated")
    public String getAidsRelated() {
	return aidsRelated;
    }

    @JsonProperty("aidsRelated")
    public void setAidsRelated(String aidsRelated) {
	this.aidsRelated = aidsRelated;
    }

    @JsonProperty("anySTDDisease")
    public String getAnySTDDisease() {
	return anySTDDisease;
    }

    @JsonProperty("anySTDDisease")
    public void setAnySTDDisease(String anySTDDisease) {
	this.anySTDDisease = anySTDDisease;
    }

    @JsonProperty("anyGynaecologicalDisorder")
    public String getAnyGynaecologicalDisorder() {
	return anyGynaecologicalDisorder;
    }

    @JsonProperty("anyGynaecologicalDisorder")
    public void setAnyGynaecologicalDisorder(String anyGynaecologicalDisorder) {
	this.anyGynaecologicalDisorder = anyGynaecologicalDisorder;
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
        return "InfectiousOrContagious{" +
                "hepatitisB='" + hepatitisB + '\'' +
                ", hepatitisC='" + hepatitisC + '\'' +
                ", hivInfection='" + hivInfection + '\'' +
                ", aidsRelated='" + aidsRelated + '\'' +
                ", anySTDDisease='" + anySTDDisease + '\'' +
                ", anyGynaecologicalDisorder='" + anyGynaecologicalDisorder + '\'' +
                ", specifyDetails='" + specifyDetails + '\'' +
                '}';
    }
}
