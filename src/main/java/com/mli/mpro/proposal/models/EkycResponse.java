package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class EkycResponse
{
    @Sensitive(MaskType.NAME)
    @JsonProperty("name")
    public String name;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    public String gender;
    @Sensitive(MaskType.DOB)
    @JsonProperty("DOB")
    public String DOB;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("phone")
    public String phone;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("email")
    public String email;
    @JsonProperty("careOf")
    public String careOf;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("house")
    public String house;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("street")
    public String street;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("landmark")
    public String landmark;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("location")
    public String location;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("pinCode")
    public String pinCode;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("postOffice")
    public String postOffice;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("villOrCity")
    public String villOrCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("subDist")
    public String subDist;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("dist")
    public String dist;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("state")
    public String state;
    @JsonProperty("photograph")
    public String photograph;
    @JsonProperty("ekycResponseCode")
    public String ekycResponseCode;

    public EkycResponse() {
    }

    public EkycResponse(String name, String gender, String DOB, String phone, String email, String careOf,
                        String house, String street, String landmark, String location, String pinCode,
                        String postOffice, String villOrCity, String subDist, String dist, String state,
                        String photograph, String ekycResponseCode ) {
        this.name = name;
        this.gender = gender;
        this.DOB = DOB;
        this.phone = phone;
        this.email = email;
        this.careOf = careOf;
        this.house = house;
        this.street = street;
        this.landmark = landmark;
        this.location = location;
        this.pinCode = pinCode;
        this.postOffice = postOffice;
        this.villOrCity = villOrCity;
        this.subDist = subDist;
        this.dist = dist;
        this.state = state;
        this.photograph = photograph;
        this.ekycResponseCode = ekycResponseCode;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("DOB")
    public String getDOB() {
        return DOB;
    }

    @JsonProperty("DOB")
    public void setDOB(String DOB)
    {
        this.DOB = DOB;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("careOf")
    public String getCareOf() {
        return careOf;
    }

    @JsonProperty("careOf")
    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    @JsonProperty("house")
    public String getHouse() {
        return house;
    }

    @JsonProperty("house")
    public void setHouse(String house) {
        this.house = house;
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("landmark")
    public String getLandmark() {
        return landmark;
    }

    @JsonProperty("landmark")
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("pinCode")
    public String getPinCode() {
        return pinCode;
    }

    @JsonProperty("pinCode")
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @JsonProperty("postOffice")
    public String getPostOffice() {
        return postOffice;
    }

    @JsonProperty("postOffice")
    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    @JsonProperty("villOrCity")
    public String getVillOrCity() {
        return villOrCity;
    }

    @JsonProperty("villOrCity")
    public void setVillOrCity(String villOrCity) {
        this.villOrCity = villOrCity;
    }

    @JsonProperty("subDist")
    public String getSubDist() {
        return subDist;
    }

    @JsonProperty("subDist")
    public void setSubDist(String subDist) {
        this.subDist = subDist;
    }

    @JsonProperty("dist")
    public String getDist() {
        return dist;
    }

    @JsonProperty("dist")
    public void setDist(String dist) {
        this.dist = dist;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("photograph")
    public String getPhotograph() {
        return photograph;
    }

    @JsonProperty("photograph")
    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public String getEkycResponseCode() {
        return ekycResponseCode;
    }

    public void setEkycResponseCode(String ekycResponseCode) {
        this.ekycResponseCode = ekycResponseCode;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "EkycResponse{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
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
                ", photograph='" + photograph + '\'' +
                ", ekycResponseCode='" + ekycResponseCode + '\'' +
                '}';
    }
}
