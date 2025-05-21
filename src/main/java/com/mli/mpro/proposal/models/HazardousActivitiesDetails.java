
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "parachuting", "handGliding", "scubaDiving", "mountaineering", "carRacing", "flying", "others", "othersDetails" })
public class HazardousActivitiesDetails {

    @JsonProperty("parachuting")
    private String parachuting;
    @JsonProperty("handGliding")
    private String handGliding;
    @JsonProperty("scubaDiving")
    private String scubaDiving;
    @JsonProperty("mountaineering")
    private String mountaineering;
    @JsonProperty("carRacing")
    private String carRacing;
    @JsonProperty("flying")
    private String flying;
    @JsonProperty("others")
    private String others;
    @JsonProperty("othersDetails")
    private String othersDetails;
    @JsonProperty("hazardousActivityExtent")
    private String hazardousActivityExtent;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HazardousActivitiesDetails() {
    }

    /**
     * 
     * @param flying
     * @param others
     * @param carRacing
     * @param othersDetails
     * @param mountaineering
     * @param scubaDiving
     * @param parachuting
     * @param handGliding
     */

    public HazardousActivitiesDetails(String parachuting, String handGliding, String scubaDiving, String mountaineering, String carRacing, String flying,
	    String others, String othersDetails, String hazardActivityExtent) {
	super();
	this.parachuting = parachuting;
	this.handGliding = handGliding;
	this.scubaDiving = scubaDiving;
	this.mountaineering = mountaineering;
	this.carRacing = carRacing;
	this.flying = flying;
	this.others = others;
	this.othersDetails = othersDetails;
	this.hazardousActivityExtent = hazardActivityExtent;
    }

    @JsonProperty("parachuting")
    public String getParachuting() {
	return parachuting;
    }

    @JsonProperty("parachuting")
    public void setParachuting(String parachuting) {
	this.parachuting = parachuting;
    }

    @JsonProperty("handGliding")
    public String getHandGliding() {
	return handGliding;
    }

    @JsonProperty("handGliding")
    public void setHandGliding(String handGliding) {
	this.handGliding = handGliding;
    }

    @JsonProperty("scubaDiving")
    public String getScubaDiving() {
	return scubaDiving;
    }

    @JsonProperty("scubaDiving")
    public void setScubaDiving(String scubaDiving) {
	this.scubaDiving = scubaDiving;
    }

    @JsonProperty("mountaineering")
    public String getMountaineering() {
	return mountaineering;
    }

    @JsonProperty("mountaineering")
    public void setMountaineering(String mountaineering) {
	this.mountaineering = mountaineering;
    }

    @JsonProperty("carRacing")
    public String getCarRacing() {
	return carRacing;
    }

    @JsonProperty("carRacing")
    public void setCarRacing(String carRacing) {
	this.carRacing = carRacing;
    }

    @JsonProperty("flying")
    public String getFlying() {
	return flying;
    }

    @JsonProperty("flying")
    public void setFlying(String flying) {
	this.flying = flying;
    }

    @JsonProperty("others")
    public String getOthers() {
	return others;
    }

    @JsonProperty("others")
    public void setOthers(String others) {
	this.others = others;
    }

    @JsonProperty("othersDetails")
    public String getOthersDetails() {
	return othersDetails;
    }

    @JsonProperty("othersDetails")
    public void setOthersDetails(String othersDetails) {
	this.othersDetails = othersDetails;
    }

    @JsonProperty("hazardousActivityExtent")
    public String getHazardousActivityExtent() {
	return hazardousActivityExtent;
    }

    @JsonProperty("hazardousActivityExtent")
    public void setHazardousActivityExtent(String hazardActivityExtent) {
	this.hazardousActivityExtent = hazardActivityExtent;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "HazardousActivitiesDetails [parachuting=" + parachuting + ", handGliding=" + handGliding + ", scubaDiving=" + scubaDiving + ", mountaineering="
		+ mountaineering + ", carRacing=" + carRacing + ", flying=" + flying + ", others=" + others + ", othersDetails=" + othersDetails
		+ ", hazardousActivityExtent=" + hazardousActivityExtent + "]";
    }

}
