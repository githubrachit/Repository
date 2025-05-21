package com.mli.mpro.proposal.models;

public class PfPermanentAddress {
    private String houseNo;
    private String road;
    private String landmark;
    private String village;
    private String city;
    private String pinCode;
    private String state;
    private String country;
    
    public PfPermanentAddress() {
        // Default constructor
    }

    public PfPermanentAddress(String houseNo, String road, String landmark, String village, String city, String pinCode, String state, String country) {
        this.houseNo = houseNo;
        this.road = road;
        this.landmark = landmark;
        this.village = village;
        this.city = city;
        this.pinCode = pinCode;
        this.state = state;
        this.country = country;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
