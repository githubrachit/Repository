package com.mli.mpro.location.labslist.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ResponsePayload {
	
    private String rating;
    @JsonAlias("labId")
    private String labID;
    private String labName;
    @Sensitive(MaskType.ADDRESS)
    private String labAddress;
    @Sensitive(MaskType.ADDRESS)
    private String stateName;
    @Sensitive(MaskType.ADDRESS)
    private String city;
    @Sensitive(MaskType.PINCODE)
    private String pinCode;
    @Sensitive(MaskType.ADDRESS)
    private String homeVisitAvailability;


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLabID() {
        return labID;
    }

    public void setLabID(String labID) {
        this.labID = labID;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabAddress() {
        return labAddress;
    }

    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getHomeVisitAvailability() {
        return homeVisitAvailability;
    }

    public void setHomeVisitAvailability(String homeVisitAvailability) {
        this.homeVisitAvailability = homeVisitAvailability;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "ResponsePayload{" +
                "rating='" + rating + '\'' +
                ", labID='" + labID + '\'' +
                ", labName='" + labName + '\'' +
                ", labAddress='" + labAddress + '\'' +
                ", stateName='" + stateName + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", homeVisitAvailability='" + homeVisitAvailability + '\'' +
                '}';
    }
}
