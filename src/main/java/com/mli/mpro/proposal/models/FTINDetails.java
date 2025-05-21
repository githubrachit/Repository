package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isFTINExist", "multipleFTINDetailsForNRI", "typeOfForeignIdentification", "identificationNumber" })
public class FTINDetails {

    @JsonProperty("isFTINExist")
    private Boolean isFTINExist;
    @JsonProperty("multipleFTINDetailsForNRI")
    private List<MultipleFTINDetailsForNRI> multipleFTINDetailsForNRI = null;
    @JsonProperty("typeOfForeignIdentification")
    private String typeOfForeignIdentification;
    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("identificationNumber")
    private String identificationNumber;
    private String issuingCountry;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FTINDetails() {
    }

    /**
     * 
     * @param typeOfForeignIdentification
     * @param multipleFTINDetailsForNRI
     * @param isFTINExist
     * @param identificationNumber
     */
    public FTINDetails(Boolean isFTINExist, List<MultipleFTINDetailsForNRI> multipleFTINDetailsForNRI, String typeOfForeignIdentification,
	    String identificationNumber) {
	super();
	this.isFTINExist = isFTINExist;
	this.multipleFTINDetailsForNRI = multipleFTINDetailsForNRI;
	this.typeOfForeignIdentification = typeOfForeignIdentification;
	this.identificationNumber = identificationNumber;
    }

    @JsonProperty("isFTINExist")
    public Boolean getIsFTINExist() {
	return isFTINExist;
    }

    @JsonProperty("isFTINExist")
    public void setIsFTINExist(Boolean isFTINExist) {
	this.isFTINExist = isFTINExist;
    }

    @JsonProperty("multipleFTINDetailsForNRI")
    public List<MultipleFTINDetailsForNRI> getMultipleFTINDetailsForNRI() {
	return multipleFTINDetailsForNRI;
    }

    @JsonProperty("multipleFTINDetailsForNRI")
    public void setMultipleFTINDetailsForNRI(List<MultipleFTINDetailsForNRI> multipleFTINDetailsForNRI) {
	this.multipleFTINDetailsForNRI = multipleFTINDetailsForNRI;
    }

    @JsonProperty("typeOfForeignIdentification")
    public String getTypeOfForeignIdentification() {
	return typeOfForeignIdentification;
    }

    @JsonProperty("typeOfForeignIdentification")
    public void setTypeOfForeignIdentification(String typeOfForeignIdentification) {
	this.typeOfForeignIdentification = typeOfForeignIdentification;
    }

    @JsonProperty("identificationNumber")
    public String getIdentificationNumber() {
	return identificationNumber;
    }

    @JsonProperty("identificationNumber")
    public void setIdentificationNumber(String identificationNumber) {
	this.identificationNumber = identificationNumber;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "FTINDetails [isFTINExist=" + isFTINExist + ", multipleFTINDetailsForNRI=" + multipleFTINDetailsForNRI + ", typeOfForeignIdentification="
		+ typeOfForeignIdentification + ", identificationNumber=" + identificationNumber + "]";
    }

}