package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

public class MatchStats {

    @JsonProperty("matchType")
    private String matchType;

    @JsonProperty("matchScore")
    private String matchScore;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private Object panNumber;
    @Sensitive(MaskType.NAME)
    @JsonProperty("name")
    private String name;

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(String matchScore) {
        this.matchScore = matchScore;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(Object panNumber) {
        this.panNumber = panNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MatchStats{" +
                "matchType='" + matchType + '\'' +
                ", matchScore='" + matchScore + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", panNumber=" + panNumber +
                ", name='" + name + '\'' +
                '}';
    }
}
