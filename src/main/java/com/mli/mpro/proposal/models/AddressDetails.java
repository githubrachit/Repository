
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "houseNo", "area", "landmark", "city", "state", "pinCode", "country", "village", "permanentVoterIdOcrStatus",
	"permanentAadhaarOcrStatus","communicationVoterIdOcrStatus","communicationAadhaarOcrStatus"})
public class AddressDetails {
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("houseNo")
    private String houseNo;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("area")
    private String area;
    @JsonProperty("landmark")
    private String landmark;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("city")
    private String city;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("state")
    private String state;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("pinCode")
    private String pinCode;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("country")
    private String country;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("village")
    private String village;
    @JsonProperty("permanentVoterIdOcrStatus")
    private String permanentVoterIdOcrStatus;
    @JsonProperty("permanentAadhaarOcrStatus")
    private String permanentAadhaarOcrStatus;
    @JsonProperty("communicationVoterIdOcrStatus")
    private String communicationVoterIdOcrStatus;
    @JsonProperty("communicationAadhaarOcrStatus")
    private String communicationAadhaarOcrStatus;
    @JsonProperty("voterIdOcrStatus")
    private String voterIdOcrStatus;
    @JsonProperty("aadhaarOcrStatus")
    private String aadhaarOcrStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddressDetails() {
    }

    /**
     * 
     * @param houseNo
     * @param pinCode
     * @param landmark
     * @param area
     * @param state
     * @param city
     * @param country
     */
    public AddressDetails(String houseNo, String area, String landmark, String city, String state, String pinCode, String country,
    		String permanentVoterIdOcrStatus, String permanentAadhaarOcrStatus,String communicationVoterIdOcrStatus ,
    		String communicationAadhaarOcrStatus,	    String voterIdOcrStatus, String aadhaarOcrStatus) {
	super();
	this.houseNo = houseNo;
	this.area = area;
	this.landmark = landmark;
	this.city = city;
	this.state = state;
	this.pinCode = pinCode;
	this.country = country;
	this.village = village;
	this.voterIdOcrStatus = voterIdOcrStatus;
	this.aadhaarOcrStatus = aadhaarOcrStatus;
	this.permanentVoterIdOcrStatus = permanentVoterIdOcrStatus;
	this.permanentAadhaarOcrStatus = permanentAadhaarOcrStatus;
	this.communicationVoterIdOcrStatus = communicationVoterIdOcrStatus;
	this.communicationAadhaarOcrStatus = communicationAadhaarOcrStatus;
    }

    @JsonProperty("houseNo")
    public String getHouseNo() {
	return houseNo;
    }

    @JsonProperty("houseNo")
    public void setHouseNo(String houseNo) {
	this.houseNo = houseNo;
    }

    @JsonProperty("area")
    public String getArea() {
	return area;
    }

    @JsonProperty("area")
    public void setArea(String area) {
	this.area = area;
    }

    @JsonProperty("landmark")
    public String getLandmark() {
	return landmark;
    }

    @JsonProperty("landmark")
    public void setLandmark(String landmark) {
	this.landmark = landmark;
    }

    @JsonProperty("city")
    public String getCity() {
	return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
	this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
	return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
	this.state = state;
    }

    @JsonProperty("pinCode")
    public String getPinCode() {
	return pinCode;
    }

    @JsonProperty("pinCode")
    public void setPinCode(String pinCode) {
	this.pinCode = pinCode;
    }

    @JsonProperty("country")
    public String getCountry() {
	return country;
    }

    public String getVillage() {
	return village;
    }

    public void setVillage(String village) {
	this.village = village;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
	this.country = country;
    }
    
   
	public String getPermanentVoterIdOcrStatus() {
		return permanentVoterIdOcrStatus;
	}

	public void setPermanentVoterIdOcrStatus(String permanentVoterIdOcrStatus) {
		this.permanentVoterIdOcrStatus = permanentVoterIdOcrStatus;
	}

	public String getPermanentAadhaarOcrStatus() {
		return permanentAadhaarOcrStatus;
	}

	public void setPermanentAadhaarOcrStatus(String permanentAadhaarOcrStatus) {
		this.permanentAadhaarOcrStatus = permanentAadhaarOcrStatus;
	}

	public String getCommunicationVoterIdOcrStatus() {
		return communicationVoterIdOcrStatus;
	}

	public void setCommunicationVoterIdOcrStatus(String communicationVoterIdOcrStatus) {
		this.communicationVoterIdOcrStatus = communicationVoterIdOcrStatus;
	}

	public String getCommunicationAadhaarOcrStatus() {
		return communicationAadhaarOcrStatus;
	}

	public void setCommunicationAadhaarOcrStatus(String communicationAadhaarOcrStatus) {
		this.communicationAadhaarOcrStatus = communicationAadhaarOcrStatus;
	}

    public String getVoterIdOcrStatus() {
	return voterIdOcrStatus;
    }

    public void setVoterIdOcrStatus(String voterIdOcrStatus) {
	this.voterIdOcrStatus = voterIdOcrStatus;
    }

    public String getAadhaarOcrStatus() {
	return aadhaarOcrStatus;
    }

    public void setAadhaarOcrStatus(String aadhaarOcrStatus) {
	this.aadhaarOcrStatus = aadhaarOcrStatus;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "AddressDetails [houseNo=" + houseNo + ", area=" + area + ", landmark=" + landmark + ", city=" + city + ", state=" + state + ", pinCode="
		+ pinCode + ", country=" + country + ", village=" + village + ",permanentVoterIdOcrStatus=" + permanentVoterIdOcrStatus +",permanentAadhaarOcrStatus=" + permanentAadhaarOcrStatus +
		",communicationVoterIdOcrStatus="+ communicationVoterIdOcrStatus + ",communicationAadhaarOcrStatus="+ communicationAadhaarOcrStatus + ", voterIdOcrStatus=" + voterIdOcrStatus + ", aadhaarOcrStatus="
		+ aadhaarOcrStatus + "]";
    }

}
