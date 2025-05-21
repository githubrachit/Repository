package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.Map;

public class LocationResponsePayload {
    private LocationDetails locationDetails;
    private List<LocationDetails> listLocationDetails;
    @Sensitive(MaskType.ADDRESS)
    private List<String> countries;
    @Sensitive(MaskType.ADDRESS)
    private List<String> states;
    @Sensitive(MaskType.ADDRESS)
    private List<String> cities;
    private List<Object> phoneCodeList;
    private List<String> companies;
    private Map<String, String> stateCityByPincode;

    public LocationResponsePayload() {

    }

    /*
     * public LocationResponsePayload(LocationDetails locationDetails,
     * List<LocationDetails> listLocationDetails) { super(); this.locationDetails =
     * locationDetails; this.listLocationDetails = listLocationDetails; }
     */

    public LocationDetails getLocationDetails() {
	return locationDetails;
    }

    public void setLocationDetails(LocationDetails locationDetails) {
	this.locationDetails = locationDetails;
    }

    public List<LocationDetails> getListLocationDetails() {
	return listLocationDetails;
    }

    public void setListLocationDetails(List<LocationDetails> listLocationDetails) {
	this.listLocationDetails = listLocationDetails;
    }

    public List<String> getStates() {
	return states;
    }

    public void setStates(List<String> states) {
	this.states = states;
    }

    public List<String> getCountries() {
	return countries;
    }

    public void setCountries(List<String> countries) {
	this.countries = countries;
    }

    public List<String> getCities() {
	return cities;
    }

    public void setCities(List<String> cities) {
	this.cities = cities;
    }

    public List<Object> getPhCodeList() {
	return phoneCodeList;
    }

    public void setPhCodeList(List<Object> phoneCodeList) {
	this.phoneCodeList = phoneCodeList;
    }
    

    public List<Object> getPhoneCodeList() {
        return phoneCodeList;
    }

    public void setPhoneCodeList(List<Object> phoneCodeList) {
        this.phoneCodeList = phoneCodeList;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }
    
    public Map<String, String> getStateCityByPincode() {
  		return stateCityByPincode;
  	}

  	public void setStateCityByPincode(Map<String, String> stateCityByPincode) {
  		this.stateCityByPincode = stateCityByPincode;
  	}

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "LocationResponsePayload [locationDetails=" + locationDetails + ", listLocationDetails=" + listLocationDetails + ", countries=" + countries
		+ ", states=" + states + ", cities=" + cities + ", phoneCodeList=" + phoneCodeList + ", companies=" + companies + ", stateCityByPincode="+ stateCityByPincode +"]";
    }


}