package com.mli.mpro.location.ruralurbanrequestmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"policyNumber", "currentAddressLine1", "currentAddressLine2", "currentAddressLine3"
        , "landMark", "country"  , "City", "State", "Pincode"})
public class Payload {
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("currentAddressLine1")
    private String currentAddressLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("currentAddressLine2")
    private String currentAddressLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("currentAddressLine3")
    private String currentAddressLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("landMark")
    private String landMark;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("City")
    private String city;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("State")
    private String state;
    @Sensitive(MaskType.COUNTRY_OF_BIRTH)
    @JsonProperty("country")
    private String country;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("Pincode")
    private String pincode;

    public Payload() {
        super();
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getCurrentAddressLine1() {
        return currentAddressLine1;
    }

    public void setCurrentAddressLine1(String currentAddressLine1) {
        this.currentAddressLine1 = currentAddressLine1;
    }

    public String getCurrentAddressLine2() {
        return currentAddressLine2;
    }

    public void setCurrentAddressLine2(String currentAddressLine2) {
        this.currentAddressLine2 = currentAddressLine2;
    }

    public String getCurrentAddressLine3() {
        return currentAddressLine3;
    }

    public void setCurrentAddressLine3(String currentAddressLine3) {
        this.currentAddressLine3 = currentAddressLine3;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "policyNumber='" + policyNumber + '\'' +
                ", currentAddressLine1='" + currentAddressLine1 + '\'' +
                ", currentAddressLine2='" + currentAddressLine2 + '\'' +
                ", currentAddressLine3='" + currentAddressLine3 + '\'' +
                ", landMark='" + landMark + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }
}
