
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "doYouParticipateInHazardousActivities", "travelOrResideAbroad", "hazardousActivitiesDetails", "travelOrResideAbroadDetails" })
public class TravelAndAdventure {

    @JsonProperty("doYouParticipateInHazardousActivities")
    private String doYouParticipateInHazardousActivities;
    @JsonProperty("travelOrResideAbroad")
    private String travelOrResideAbroad;
    @JsonProperty("hazardousActivitiesDetails")
    private HazardousActivitiesDetails hazardousActivitiesDetails;
    @JsonProperty("travelOrResideAbroadDetails")
    private TravelOrResideAbroadDetails travelOrResideAbroadDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TravelAndAdventure() {
    }

    /**
     * 
     * @param travelOrResideAbroad
     * @param hazardousActivitiesDetails
     * @param doYouParticipateInHazardousActivities
     * @param travelOrResideAbroadDetails
     */
    public TravelAndAdventure(String doYouParticipateInHazardousActivities, String travelOrResideAbroad, HazardousActivitiesDetails hazardousActivitiesDetails,
	    TravelOrResideAbroadDetails travelOrResideAbroadDetails) {
	super();
	this.doYouParticipateInHazardousActivities = doYouParticipateInHazardousActivities;
	this.travelOrResideAbroad = travelOrResideAbroad;
	this.hazardousActivitiesDetails = hazardousActivitiesDetails;
	this.travelOrResideAbroadDetails = travelOrResideAbroadDetails;
    }

    @JsonProperty("doYouParticipateInHazardousActivities")
    public String getDoYouParticipateInHazardousActivities() {
	return doYouParticipateInHazardousActivities;
    }

    @JsonProperty("doYouParticipateInHazardousActivities")
    public void setDoYouParticipateInHazardousActivities(String doYouParticipateInHazardousActivities) {
	this.doYouParticipateInHazardousActivities = doYouParticipateInHazardousActivities;
    }

    @JsonProperty("travelOrResideAbroad")
    public String getTravelOrResideAbroad() {
	return travelOrResideAbroad;
    }

    @JsonProperty("travelOrResideAbroad")
    public void setTravelOrResideAbroad(String travelOrResideAbroad) {
	this.travelOrResideAbroad = travelOrResideAbroad;
    }

    @JsonProperty("hazardousActivitiesDetails")
    public HazardousActivitiesDetails getHazardousActivitiesDetails() {
	return hazardousActivitiesDetails;
    }

    @JsonProperty("hazardousActivitiesDetails")
    public void setHazardousActivitiesDetails(HazardousActivitiesDetails hazardousActivitiesDetails) {
	this.hazardousActivitiesDetails = hazardousActivitiesDetails;
    }

    @JsonProperty("travelOrResideAbroadDetails")
    public TravelOrResideAbroadDetails getTravelOrResideAbroadDetails() {
	return travelOrResideAbroadDetails;
    }

    @JsonProperty("travelOrResideAbroadDetails")
    public void setTravelOrResideAbroadDetails(TravelOrResideAbroadDetails travelOrResideAbroadDetails) {
	this.travelOrResideAbroadDetails = travelOrResideAbroadDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "TravelAndAdventure [doYouParticipateInHazardousActivities=" + doYouParticipateInHazardousActivities + ", travelOrResideAbroad="
		+ travelOrResideAbroad + ", hazardousActivitiesDetails=" + hazardousActivitiesDetails + ", travelOrResideAbroadDetails="
        + travelOrResideAbroadDetails + "]";
    }

}
