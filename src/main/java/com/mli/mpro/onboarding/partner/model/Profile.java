package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

import java.util.List;

@JsonPropertyOrder({"panNumber", "previousPolicyDetails", "name", "dob", "gender", "clientId"})
public class Profile {
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;

    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("previousPolicyDetails")
    private List<PreviousPolicyDetails> previousPolicyDetails;
    @Sensitive(MaskType.NAME)
    @JsonProperty("name")
    private List<Name> name;

    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private String dob;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("clientId")
    private String clientId;

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }


    public List<PreviousPolicyDetails> getPreviousPolicyDetails() {
        return previousPolicyDetails;
    }

    public void setPreviousPolicyDetails(List<PreviousPolicyDetails> previousPolicyDetails) {
        this.previousPolicyDetails = previousPolicyDetails;
    }

    public List<Name> getName() {
        return name;
    }

    public void setName(List<Name> name) {
        this.name = name;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "panNumber='" + panNumber + '\'' +
                ", previousPolicyDetails=" + previousPolicyDetails +
                ", name=" + name +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
