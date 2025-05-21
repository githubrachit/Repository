
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "everBeenhospitalized", "everBeenhospitalizedDetails", "everBeenAbsentFromWork", "everBeenAbsentFromWorkDetails" })
public class HospitalizationAndAbsenceFromWork {

    @JsonProperty("everBeenhospitalized")
    private String everBeenhospitalized;
    @JsonProperty("everBeenhospitalizedDetails")
    private String everBeenhospitalizedDetails;
    @JsonProperty("everBeenAbsentFromWork")
    private String everBeenAbsentFromWork;
    @JsonProperty("everBeenAbsentFromWorkDetails")
    private String everBeenAbsentFromWorkDetails;
    @JsonProperty("isFeverHosptalization")
    private String isFeverHosptalization;
    @JsonProperty("isFoodPoisonHospt")
    private String isFoodPoisonHospt;
    @JsonProperty("isAccidentHospt")
    private String isAccidentHospt;
    @JsonProperty("isStonePileHospt")
    private String isStonePileHospt;
    @JsonProperty("isTyphDengHospt")
    private String isTyphDengHospt;
    @JsonProperty("isSinusColdHospt")
    private String isSinusColdHospt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HospitalizationAndAbsenceFromWork() {
    }

    /**
     * 
     * @param everBeenAbsentFromWork
     * @param everBeenAbsentFromWorkDetails
     * @param everBeenhospitalizedDetails
     * @param everBeenhospitalized
     */
    public HospitalizationAndAbsenceFromWork(String everBeenhospitalized, String everBeenhospitalizedDetails, String everBeenAbsentFromWork,
	    String everBeenAbsentFromWorkDetails) {
	super();
	this.everBeenhospitalized = everBeenhospitalized;
	this.everBeenhospitalizedDetails = everBeenhospitalizedDetails;
	this.everBeenAbsentFromWork = everBeenAbsentFromWork;
	this.everBeenAbsentFromWorkDetails = everBeenAbsentFromWorkDetails;
    }

    @JsonProperty("everBeenhospitalized")
    public String getEverBeenhospitalized() {
	return everBeenhospitalized;
    }

    @JsonProperty("everBeenhospitalized")
    public void setEverBeenhospitalized(String everBeenhospitalized) {
	this.everBeenhospitalized = everBeenhospitalized;
    }

    @JsonProperty("everBeenhospitalizedDetails")
    public String getEverBeenhospitalizedDetails() {
	return everBeenhospitalizedDetails;
    }

    @JsonProperty("everBeenhospitalizedDetails")
    public void setEverBeenhospitalizedDetails(String everBeenhospitalizedDetails) {
	this.everBeenhospitalizedDetails = everBeenhospitalizedDetails;
    }

    @JsonProperty("everBeenAbsentFromWork")
    public String getEverBeenAbsentFromWork() {
	return everBeenAbsentFromWork;
    }

    @JsonProperty("everBeenAbsentFromWork")
    public void setEverBeenAbsentFromWork(String everBeenAbsentFromWork) {
	this.everBeenAbsentFromWork = everBeenAbsentFromWork;
    }

    @JsonProperty("everBeenAbsentFromWorkDetails")
    public String getEverBeenAbsentFromWorkDetails() {
	return everBeenAbsentFromWorkDetails;
    }

    @JsonProperty("everBeenAbsentFromWorkDetails")
    public void setEverBeenAbsentFromWorkDetails(String everBeenAbsentFromWorkDetails) {
	this.everBeenAbsentFromWorkDetails = everBeenAbsentFromWorkDetails;
    }

    public String getIsFeverHosptalization() {
        return isFeverHosptalization;
    }

    public HospitalizationAndAbsenceFromWork setIsFeverHosptalization(String isFeverHosptalization) {
        this.isFeverHosptalization = isFeverHosptalization;
        return this;
    }

    public String getIsFoodPoisonHospt() {
        return isFoodPoisonHospt;
    }

    public HospitalizationAndAbsenceFromWork setIsFoodPoisonHospt(String isFoodPoisonHospt) {
        this.isFoodPoisonHospt = isFoodPoisonHospt;
        return this;
    }

    public String getIsAccidentHospt() {
        return isAccidentHospt;
    }

    public HospitalizationAndAbsenceFromWork setIsAccidentHospt(String isAccidentHospt) {
        this.isAccidentHospt = isAccidentHospt;
        return this;
    }

    public String getIsStonePileHospt() {
        return isStonePileHospt;
    }

    public HospitalizationAndAbsenceFromWork setIsStonePileHospt(String isStonePileHospt) {
        this.isStonePileHospt = isStonePileHospt;
        return this;
    }

    public String getIsTyphDengHospt() {
        return isTyphDengHospt;
    }

    public HospitalizationAndAbsenceFromWork setIsTyphDengHospt(String isTyphDengHospt) {
        this.isTyphDengHospt = isTyphDengHospt;
        return this;
    }

    public String getIsSinusColdHospt() {
        return isSinusColdHospt;
    }

    public HospitalizationAndAbsenceFromWork setIsSinusColdHospt(String isSinusColdHospt) {
        this.isSinusColdHospt = isSinusColdHospt;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "HospitalizationAndAbsenceFromWork{" +
                "everBeenhospitalized='" + everBeenhospitalized + '\'' +
                ", everBeenhospitalizedDetails='" + everBeenhospitalizedDetails + '\'' +
                ", everBeenAbsentFromWork='" + everBeenAbsentFromWork + '\'' +
                ", everBeenAbsentFromWorkDetails='" + everBeenAbsentFromWorkDetails + '\'' +
                ", isFeverHosptalization='" + isFeverHosptalization + '\'' +
                ", isFoodPoisonHospt='" + isFoodPoisonHospt + '\'' +
                ", isAccidentHospt='" + isAccidentHospt + '\'' +
                ", isStonePileHospt='" + isStonePileHospt + '\'' +
                ", isTyphDengHospt='" + isTyphDengHospt + '\'' +
                ", isSinusColdHospt='" + isSinusColdHospt + '\'' +
                '}';
    }
}
