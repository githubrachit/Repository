package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "addrType",
    "city",
    "addrLine1",
    "addrLine2",
    "addrLine3",
    "pinCode",
    "state",
    "country"
})
public class AddressDetail
{
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("addrType")
    private String addrType;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("city")
    private String city;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("addrLine1")
    private String addrLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("addrLine2")
    private String addrLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("addrLine3")
    private String addrLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("pinCode")
    private String pinCode;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("state")
    private String state;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("country")
    private String country;

    @JsonProperty("addrType")
    public String getAddrType() {
        return addrType;
    }

    @JsonProperty("addrType")
    public void setAddrType(String addrType) {
        this.addrType = addrType;
    }

    public AddressDetail withAddrType(String addrType) {
        this.addrType = addrType;
        return this;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public AddressDetail withCity(String city) {
        this.city = city;
        return this;
    }

    @JsonProperty("addrLine1")
    public String getAddrLine1() {
        return addrLine1;
    }

    @JsonProperty("addrLine1")
    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public AddressDetail withAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
        return this;
    }

    @JsonProperty("addrLine2")
    public String getAddrLine2() {
        return addrLine2;
    }

    @JsonProperty("addrLine2")
    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public AddressDetail withAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
        return this;
    }

    @JsonProperty("addrLine3")
    public String getAddrLine3() {
        return addrLine3;
    }

    @JsonProperty("addrLine3")
    public void setAddrLine3(String addrLine3) {
        this.addrLine3 = addrLine3;
    }

    public AddressDetail withAddrLine3(String addrLine3) {
        this.addrLine3 = addrLine3;
        return this;
    }

    @JsonProperty("pinCode")
    public String getPinCode() {
        return pinCode;
    }

    @JsonProperty("pinCode")
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public AddressDetail withPinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public AddressDetail withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public AddressDetail withCountry(String country) {
        this.country = country;
        return this;
    }

	@Override
	public String toString() {

        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "AddressDetail [addrType=" + addrType + ", city=" + city + ", addrLine1=" + addrLine1 + ", addrLine2="
				+ addrLine2 + ", addrLine3=" + addrLine3 + ", pinCode=" + pinCode + ", state=" + state + ", country="
				+ country + "]";
	}

  

}
