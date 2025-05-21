package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class ResponseAadhaarDetails {

    @Sensitive(MaskType.NAME)
    @JsonProperty("name")
    private String name;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("phone")
    private String phone;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("email")
    private String email;
    @JsonProperty("image")
    private String image;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("careOf")
    private String careOf;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("house")
    private String house;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("street")
    private String street;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("landmark")
    private String landmark;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("location")
    private String location;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("pinCode")
    private String pinCode;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("postOffice")
    private String postOffice;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("villOrCity")
    private String villOrCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("subDist")
    private String subDist;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("dist")
    private String dist;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("state")
    private String state;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getVillOrCity() {
        return villOrCity;
    }

    public void setVillOrCity(String villOrCity) {
        this.villOrCity = villOrCity;
    }

    public String getSubDist() {
        return subDist;
    }

    public void setSubDist(String subDist) {
        this.subDist = subDist;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }

        return "ResponseAadhaarDetails{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", dob='" + dob + '\'' +
                ", careOf='" + careOf + '\'' +
                ", house='" + house + '\'' +
                ", street='" + street + '\'' +
                ", landmark='" + landmark + '\'' +
                ", location='" + location + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", postOffice='" + postOffice + '\'' +
                ", villOrCity='" + villOrCity + '\'' +
                ", subDist='" + subDist + '\'' +
                ", dist='" + dist + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
