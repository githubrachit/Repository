package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MedicalGeoLocationDetails {
    @JsonProperty("pinCode")
    private String pinCode;
    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("house")
    private String house;
    @JsonProperty("area")
    private String area;

    public MedicalGeoLocationDetails() {
    }

    public MedicalGeoLocationDetails(String pinCode, String state, String city, String house, String area) {
        this.pinCode = pinCode;
        this.state = state;
        this.city = city;
        this.house = house;
        this.area = area;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    @Override
    public String toString() {
        return "MedicalGeoLocationDetails{" +
                "pinCode='" + pinCode + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", house='" + house + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
