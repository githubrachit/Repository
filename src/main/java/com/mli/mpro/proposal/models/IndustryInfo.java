package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class IndustryInfo {

    @JsonProperty("isPostedOnDefenceLocation")
    private boolean isPostedOnDefenceLocation;
    @JsonProperty("isProfessionalDiver")
    private boolean isProfessionalDiver;
    @JsonProperty("isBasedAtOffshore")
    private boolean isBasedAtOffshore;
    @JsonProperty("isFlying")
    private boolean isFlying;
    @JsonProperty("typeOfAirCraft")
    private String typeOfAirCraft;
    @JsonProperty("navyAreaWorking")
    private String navyAreaWorking;
    @JsonProperty("isWorkingInsideMine")
    private boolean isWorkingInsideMine;
    @JsonProperty("anyIllnessRelatedToOccupation")
    private boolean anyIllnessRelatedToOccupation;
    @JsonProperty("natureOfRole")
    private String natureOfRole;
    @JsonProperty("diveLocation")
    private String diveLocation;

    public IndustryInfo() {
    }

    public IndustryInfo(boolean isPostedOnDefenceLocation, boolean isProfessionalDiver, boolean isBasedAtOffshore, boolean isFlying, String typeOfAirCraft,
	    String navyAreaWorking, boolean isWorkingInsideMine, boolean anyIllnessRelatedToOccupation, String natureOfRole, String diveLocation) {
	super();
	this.isPostedOnDefenceLocation = isPostedOnDefenceLocation;
	this.isProfessionalDiver = isProfessionalDiver;
	this.isBasedAtOffshore = isBasedAtOffshore;
	this.isFlying = isFlying;
	this.typeOfAirCraft = typeOfAirCraft;
	this.navyAreaWorking = navyAreaWorking;
	this.isWorkingInsideMine = isWorkingInsideMine;
	this.anyIllnessRelatedToOccupation = anyIllnessRelatedToOccupation;
	this.natureOfRole = natureOfRole;
	this.diveLocation = diveLocation;
    }

    public boolean isPostedOnDefenceLocation() {
	return isPostedOnDefenceLocation;
    }

    public void setPostedOnDefenceLocation(boolean isPostedOnDefenceLocation) {
	this.isPostedOnDefenceLocation = isPostedOnDefenceLocation;
    }

    public boolean isProfessionalDiver() {
	return isProfessionalDiver;
    }

    public void setProfessionalDiver(boolean isProfessionalDiver) {
	this.isProfessionalDiver = isProfessionalDiver;
    }

    public boolean isBasedAtOffshore() {
	return isBasedAtOffshore;
    }

    public void setBasedAtOffshore(boolean isBasedAtOffshore) {
	this.isBasedAtOffshore = isBasedAtOffshore;
    }

    public boolean isFlying() {
	return isFlying;
    }

    public void setFlying(boolean isFlying) {
	this.isFlying = isFlying;
    }

    public String getTypeOfAirCraft() {
	return typeOfAirCraft;
    }

    public void setTypeOfAirCraft(String typeOfAirCraft) {
	this.typeOfAirCraft = typeOfAirCraft;
    }

    public String getNavyAreaWorking() {
	return navyAreaWorking;
    }

    public void setNavyAreaWorking(String navyAreaWorking) {
	this.navyAreaWorking = navyAreaWorking;
    }

    public boolean isWorkingInsideMine() {
	return isWorkingInsideMine;
    }

    public void setWorkingInsideMine(boolean isWorkingInsideMine) {
	this.isWorkingInsideMine = isWorkingInsideMine;
    }

    public boolean isAnyIllnessRelatedToOccupation() {
	return anyIllnessRelatedToOccupation;
    }

    public void setAnyIllnessRelatedToOccupation(boolean anyIllnessRelatedToOccupation) {
	this.anyIllnessRelatedToOccupation = anyIllnessRelatedToOccupation;
    }

    public String getNatureOfRole() {
	return natureOfRole;
    }

    public void setNatureOfRole(String natureOfRole) {
	this.natureOfRole = natureOfRole;
    }

    public String getDiveLocation() {
	return diveLocation;
    }

    public void setDiveLocation(String diveLocation) {
	this.diveLocation = diveLocation;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "IndustryInfo [isPostedOnDefenceLocation=" + isPostedOnDefenceLocation + ", isProfessionalDiver=" + isProfessionalDiver + ", isBasedAtOffshore="
		+ isBasedAtOffshore + ", isFlying=" + isFlying + ", typeOfAirCraft=" + typeOfAirCraft + ", navyAreaWorking=" + navyAreaWorking
		+ ", isWorkingInsideMine=" + isWorkingInsideMine + ", anyIllnessRelatedToOccupation=" + anyIllnessRelatedToOccupation + ", natureOfRole="
		+ natureOfRole + ", diveLocation=" + diveLocation + "]";
    }

}
