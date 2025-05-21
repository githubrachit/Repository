
package com.mli.mpro.proposal.models;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "passportNumber", "typeOfVisa", "visaValidTill", "passportIssuingCountry", "currentCountryOfResidence", "countryVisitedFrequently",
	"countryOfBirth", "uidIssuingCountry", "countryOfResidenceAsPertaxLaws", "fTinDetails", "issuingCountry", "recentVisitDateToIndia",
	"passportExpiryDate" })
public class NRIDetails {
    @Sensitive(MaskType.PASSPORT)
    @JsonProperty("passportNumber")
    public String passportNumber;
    @JsonProperty("typeOfVisa")
    public String typeOfVisa;
    @JsonProperty("visaExpiryDate")
    private Date visaExpiryDate;
    @JsonProperty("passportIssuingCountry")
    public String passportIssuingCountry;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("currentCountryOfResidence")
    public String currentCountryOfResidence;
    @JsonProperty("countryVisitedFrequently")
    public List<String> countryVisitedFrequently;
    @Sensitive(MaskType.COUNTRY_OF_BIRTH)
    @JsonProperty("countryOfBirth")
    public String countryOfBirth;
    @JsonProperty("uidIssuingCountry")
    public String uidIssuingCountry;
    @JsonProperty("countryOfResidenceAsPerTaxLaw")
    private String countryOfResidenceAsPerTaxLaw;
    @JsonProperty("FTINDetails")
    public FTINDetails ftinDetails;
    @JsonProperty("issuingCountry")
    public String issuingCountry;
    @JsonProperty("recentVisitDateToIndia")
    private Date recentVisitDateToIndia;
    @JsonProperty("applicationSource")
    private String applicationSource;
    @JsonProperty("passportExpiryDate")
    private Date passportExpiryDate;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("overseasFullAddress")
    private String overseasFullAddress;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("telephoneNumberNotPertainingToIndia")
    private String telephoneNumberNotPertainingToIndia;
    @JsonProperty("doyoufileforTaxinmorethanonecontry")
    private String doyoufileforTaxinmorethanonecontry;
    @JsonProperty("ftinOrPan")
    private String ftinOrPan;
    @Sensitive(MaskType.PLACE_OF_BIRTH)
    @JsonProperty("cityOfBirth")
    private String cityOfBirth;
    @JsonProperty("citizenship")
    private String citizenship;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NRIDetails() {
    }

    public NRIDetails(String passportNumber, String typeOfVisa, Date visaExpiryDate, String passportIssuingCountry, String currentCountryOfResidence,
	    List<String> countryVisitedFrequently, String countryOfBirth, String uidIssuingCountry, String countryOfResidenceAsPerTaxLaw,
	    FTINDetails ftinDetails, String issuingCountry, Date recentVisitDateToIndia, String applicationSource, Date passportExpiryDate) {
	super();
	this.passportNumber = passportNumber;
	this.typeOfVisa = typeOfVisa;
	this.visaExpiryDate = visaExpiryDate;
	this.passportIssuingCountry = passportIssuingCountry;
	this.currentCountryOfResidence = currentCountryOfResidence;
	this.countryVisitedFrequently = countryVisitedFrequently;
	this.countryOfBirth = countryOfBirth;
	this.uidIssuingCountry = uidIssuingCountry;
	this.countryOfResidenceAsPerTaxLaw = countryOfResidenceAsPerTaxLaw;
	this.ftinDetails = ftinDetails;
	this.issuingCountry = issuingCountry;
	this.recentVisitDateToIndia = recentVisitDateToIndia;
	this.applicationSource = applicationSource;
	this.passportExpiryDate = passportExpiryDate;
    }

    public String getPassportNumber() {
	return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
	this.passportNumber = passportNumber;
    }

    public String getTypeOfVisa() {
	return typeOfVisa;
    }

    public void setTypeOfVisa(String typeOfVisa) {
	this.typeOfVisa = typeOfVisa;
    }

    public Date getVisaExpiryDate() {
	return visaExpiryDate;
    }

    public void setVisaExpiryDate(Date visaExpirtyDate) {
	this.visaExpiryDate = visaExpirtyDate;
    }

    public String getPassportIssuingCountry() {
	return passportIssuingCountry;
    }

    public void setPassportIssuingCountry(String passportIssuingCountry) {
	this.passportIssuingCountry = passportIssuingCountry;
    }

    public String getCurrentCountryOfResidence() {
	return currentCountryOfResidence;
    }

    public void setCurrentCountryOfResidence(String currentCountryOfResidence) {
	this.currentCountryOfResidence = currentCountryOfResidence;
    }

    public List<String> getCountryVisitedFrequently() {
	return countryVisitedFrequently;
    }

    public void setCountryVisitedFrequently(List<String> countryVisitedFrequently) {
	this.countryVisitedFrequently = countryVisitedFrequently;
    }

    public String getCountryOfBirth() {
	return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
	this.countryOfBirth = countryOfBirth;
    }

    public String getUidIssuingCountry() {
	return uidIssuingCountry;
    }

    public void setUidIssuingCountry(String uidIssuingCountry) {
	this.uidIssuingCountry = uidIssuingCountry;
    }

    public String getCountryOfResidenceAsPerTaxLaw() {
	return countryOfResidenceAsPerTaxLaw;
    }

    public void setCountryOfResidenceAsPerTaxLaw(String countryOfResidenceAsPerTaxLaw) {
	this.countryOfResidenceAsPerTaxLaw = countryOfResidenceAsPerTaxLaw;
    }

    public FTINDetails getFtinDetails() {
	return ftinDetails;
    }

    public void setFtinDetails(FTINDetails ftinDetails) {
	this.ftinDetails = ftinDetails;
    }

    public String getIssuingCountry() {
	return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
	this.issuingCountry = issuingCountry;
    }

    public Date getRecentVisitDateToIndia() {
	return recentVisitDateToIndia;
    }

    public void setRecentVisitDateToIndia(Date recentVisitDateToIndia) {
	this.recentVisitDateToIndia = recentVisitDateToIndia;
    }

    public String getApplicationSource() {
	return applicationSource;
    }

    public void setApplicationSource(String applicationSource) {
	this.applicationSource = applicationSource;
    }

    public Date getPassportExpiryDate() {
	return passportExpiryDate;
    }

    public void setPassportExpiryDate(Date passportExpiryDate) {
	this.passportExpiryDate = passportExpiryDate;
    }

    public String getOverseasFullAddress() {
        return overseasFullAddress;
    }

    public NRIDetails setOverseasFullAddress(String overseasFullAddress) {
        this.overseasFullAddress = overseasFullAddress;
        return this;
    }

    public String getTelephoneNumberNotPertainingToIndia() {
        return telephoneNumberNotPertainingToIndia;
    }

    public NRIDetails setTelephoneNumberNotPertainingToIndia(String telephoneNumberNotPertainingToIndia) {
        this.telephoneNumberNotPertainingToIndia = telephoneNumberNotPertainingToIndia;
        return this;
    }

    public String getDoyoufileforTaxinmorethanonecontry() {
        return doyoufileforTaxinmorethanonecontry;
    }

    public NRIDetails setDoyoufileforTaxinmorethanonecontry(String doyoufileforTaxinmorethanonecontry) {
        this.doyoufileforTaxinmorethanonecontry = doyoufileforTaxinmorethanonecontry;
        return this;
    }

    public String getFtinOrPan() {
        return ftinOrPan;
    }

    public NRIDetails setFtinOrPan(String ftinOrPan) {
        this.ftinOrPan = ftinOrPan;
        return this;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public NRIDetails setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
        return this;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public NRIDetails setCitizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", NRIDetails.class.getSimpleName() + "[", "]")
                .add("passportNumber='" + passportNumber + "'")
                .add("typeOfVisa='" + typeOfVisa + "'")
                .add("visaExpiryDate=" + visaExpiryDate)
                .add("passportIssuingCountry='" + passportIssuingCountry + "'")
                .add("currentCountryOfResidence='" + currentCountryOfResidence + "'")
                .add("countryVisitedFrequently=" + countryVisitedFrequently)
                .add("countryOfBirth='" + countryOfBirth + "'")
                .add("uidIssuingCountry='" + uidIssuingCountry + "'")
                .add("countryOfResidenceAsPerTaxLaw='" + countryOfResidenceAsPerTaxLaw + "'")
                .add("ftinDetails=" + ftinDetails)
                .add("issuingCountry='" + issuingCountry + "'")
                .add("recentVisitDateToIndia=" + recentVisitDateToIndia)
                .add("applicationSource='" + applicationSource + "'")
                .add("passportExpiryDate=" + passportExpiryDate)
                .add("overseasFullAddress='" + overseasFullAddress + "'")
                .add("telephoneNumberNotPertainingToIndia='" + telephoneNumberNotPertainingToIndia + "'")
                .add("doyoufileforTaxinmorethanonecontry='" + doyoufileforTaxinmorethanonecontry + "'")
                .add("ftinOrPan='" + ftinOrPan + "'")
                .add("citizenship='" + citizenship + "'")
                .add("cityOfBirth='" + cityOfBirth + "'")
                .toString();
    }
}