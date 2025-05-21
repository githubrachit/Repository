package com.mli.mpro.location.models.soaCloudModels;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class BaseAddDetail {
        @JsonProperty("addrLine1")
        private String addrLine1;

        @JsonProperty("addrLine2")
        private String addrLine2;

        @JsonProperty("addrLine3")
        private String addrLine3;

        @JsonProperty("city")
        private String city;

        @JsonProperty("state")
        private String state;

        @JsonProperty("pinCode")
        private String pinCode;

        @JsonProperty("addrType")
        private String addrType;

        @JsonProperty("country")
        private String country;

        @JsonProperty("addrChangeDt")
        private String addrChangeDt;

        @JsonProperty("telePhone1")
        private String telePhone1;

        @JsonProperty("telePhone2")
        private String telePhone2;

        @JsonProperty("stateCd")
        private String stateCd;

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public String getAddrLine3() {
        return addrLine3;
    }

    public void setAddrLine3(String addrLine3) {
        this.addrLine3 = addrLine3;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddrType() {
        return addrType;
    }

    public void setAddrType(String addrType) {
        this.addrType = addrType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddrChangeDt() {
        return addrChangeDt;
    }

    public void setAddrChangeDt(String addrChangeDt) {
        this.addrChangeDt = addrChangeDt;
    }

    public String getTelePhone1() {
        return telePhone1;
    }

    public void setTelePhone1(String telePhone1) {
        this.telePhone1 = telePhone1;
    }

    public String getTelePhone2() {
        return telePhone2;
    }

    public void setTelePhone2(String telePhone2) {
        this.telePhone2 = telePhone2;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "BaseAddDetail{" +
                "addrLine1='" + addrLine1 + '\'' +
                ", addrLine2='" + addrLine2 + '\'' +
                ", addrLine3='" + addrLine3 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", addrType='" + addrType + '\'' +
                ", country='" + country + '\'' +
                ", addrChangeDt='" + addrChangeDt + '\'' +
                ", telePhone1='" + telePhone1 + '\'' +
                ", telePhone2='" + telePhone2 + '\'' +
                ", stateCd='" + stateCd + '\'' +
                '}';
    }
}
