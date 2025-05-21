package com.mli.mpro.location.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "yblPasaDetails")

public class YblPasaDetails {
    @JsonProperty("uniqueId")
    private String uniqueId;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;
    @JsonProperty("custId")
    private String custId;
    @JsonProperty("offerType")
    private String offerType;
    @JsonProperty("pasaCategory1")
    private String pasaCategory1;
    @JsonProperty("categoryCode1")
    private String categoryCode1;
    @JsonProperty("pasaCategory2")
    private String pasaCategory2;
    @JsonProperty("categoryCode2")
    private String categoryCode2;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("sumAssured")
    private String sumAssured;
    @JsonProperty("premium")
    private String premium;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("expiry")
    private String expiry;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getPasaCategory1() {
        return pasaCategory1;
    }

    public void setPasaCategory1(String pasaCategory1) {
        this.pasaCategory1 = pasaCategory1;
    }

    public String getCategoryCode1() {
        return categoryCode1;
    }

    public void setCategoryCode1(String categoryCode1) {
        this.categoryCode1 = categoryCode1;
    }

    public String getPasaCategory2() {
        return pasaCategory2;
    }

    public void setPasaCategory2(String pasaCategory2) {
        this.pasaCategory2 = pasaCategory2;
    }

    public String getCategoryCode2() {
        return categoryCode2;
    }

    public void setCategoryCode2(String categoryCode2) {
        this.categoryCode2 = categoryCode2;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "YblPasaDetails{" +
                "uniqueId='" + uniqueId + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", custId='" + custId + '\'' +
                ", offerType='" + offerType + '\'' +
                ", pasaCategory1='" + pasaCategory1 + '\'' +
                ", categoryCode1='" + categoryCode1 + '\'' +
                ", pasaCategory2='" + pasaCategory2 + '\'' +
                ", categoryCode2='" + categoryCode2 + '\'' +
                ", sumAssured='" + sumAssured + '\'' +
                ", premium='" + premium + '\'' +
                ", channel='" + channel + '\'' +
                ", expiry='" + expiry + '\'' +
                '}';
    }
}
