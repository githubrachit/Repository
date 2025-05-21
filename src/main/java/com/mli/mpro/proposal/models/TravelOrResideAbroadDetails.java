
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "country", "city", "purpose", "purposeOthers", "durationOfStay" })
public class TravelOrResideAbroadDetails {

    @JsonProperty("CICountryTobeVisited")
    public List<String> CICountryTobeVisited;
    @JsonProperty("city")
    private String city;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("purposeOthers")
    private String purposeOthers;
    @JsonProperty("durationOfStay")
    private String durationOfStay;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TravelOrResideAbroadDetails() {
    }

    /**
     *
     */
   

  

    public List<String> getCICountryTobeVisited() {
        return CICountryTobeVisited;
    }

    public TravelOrResideAbroadDetails(List<String> CICountryTobeVisited, String city, String purpose, String purposeOthers, String durationOfStay) {
	super();
	this.CICountryTobeVisited = CICountryTobeVisited;
	this.city = city;
	this.purpose = purpose;
	this.purposeOthers = purposeOthers;
	this.durationOfStay = durationOfStay;
    }

    public void setCICountryTobeVisited(List<String> CICountryTobeVisited) {
        this.CICountryTobeVisited = CICountryTobeVisited;
    }

    @JsonProperty("city")
    public String getCity() {
	return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
	this.city = city;
    }

    @JsonProperty("purpose")
    public String getPurpose() {
	return purpose;
    }

    @JsonProperty("purpose")
    public void setPurpose(String purpose) {
	this.purpose = purpose;
    }

    @JsonProperty("purposeOthers")
    public String getPurposeOthers() {
	return purposeOthers;
    }

    @JsonProperty("purposeOthers")
    public void setPurposeOthers(String purposeOthers) {
	this.purposeOthers = purposeOthers;
    }

    @JsonProperty("durationOfStay")
    public String getDurationOfStay() {
	return durationOfStay;
    }

    @JsonProperty("durationOfStay")
    public void setDurationOfStay(String durationOfStay) {
	this.durationOfStay = durationOfStay;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "TravelOrResideAbroadDetails{" +
                "CICountryTobeVisited=" + CICountryTobeVisited +
                ", city='" + city + '\'' +
                ", purpose='" + purpose + '\'' +
                ", purposeOthers='" + purposeOthers + '\'' +
                ", durationOfStay='" + durationOfStay + '\'' +
                '}';
    }
}
