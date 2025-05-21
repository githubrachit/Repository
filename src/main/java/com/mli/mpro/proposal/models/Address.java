
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "proofType", "addressType", "addressDetails" })
public class Address {

    @JsonProperty("proofType")
    private String proofType;
    @JsonProperty("addressType")
    private String addressType;
    @JsonProperty("addressDetails")
    private AddressDetails addressDetails;
    /*FUL2-11558 Changes of cKYC for individual : proofNumber & proofExpiryDate added as per new requirement*/
    @Sensitive(MaskType.ADDRESS_PROOF_NUM)
    @JsonProperty("proofNumber")
    private String proofNumber;
    @JsonProperty("proofExpiryDate")
    private String proofExpiryDate;
    @JsonProperty("isPRASameAsCRA")
    private String isPRASameAsCRA;


    /**
     * No args constructor for use in serialization
     * 
     */
    public Address() {
    }

    /**
     * 
     * @param addressDetails
     * @param addressType
     * @param proofType
     */
    public Address(String proofType, String addressType, AddressDetails addressDetails) {
	super();
	this.proofType = proofType;
	this.addressType = addressType;
	this.addressDetails = addressDetails;
    }

    @JsonProperty("proofType")
    public String getProofType() {
	return proofType;
    }

    @JsonProperty("proofType")
    public void setProofType(String proofType) {
	this.proofType = proofType;
    }

    @JsonProperty("addressType")
    public String getAddressType() {
	return addressType;
    }

    @JsonProperty("addressType")
    public void setAddressType(String addressType) {
	this.addressType = addressType;
    }

    @JsonProperty("addressDetails")
    public AddressDetails getAddressDetails() {
	return addressDetails;
    }

    @JsonProperty("addressDetails")
    public void setAddressDetails(AddressDetails addressDetails) {
	this.addressDetails = addressDetails;
    }

    public String getProofNumber() {
        return proofNumber;
    }

    public void setProofNumber(String proofNumber) {
        this.proofNumber = proofNumber;
    }

    public String getProofExpiryDate() {
        return proofExpiryDate;
    }

    public void setProofExpiryDate(String proofExpiryDate) {
        this.proofExpiryDate = proofExpiryDate;
    }

    public String getIsPRASameAsCRA() {
        return isPRASameAsCRA;
    }

    public void setIsPRASameAsCRA(String isPRASameAsCRA) {
        this.isPRASameAsCRA = isPRASameAsCRA;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "Address [proofType=" + proofType + ", addressType=" + addressType + ", addressDetails=" + addressDetails + ", proofNumber=" + proofNumber
		+ ", proofExpiryDate=" + proofExpiryDate + ", isPRASameAsCRA=" + isPRASameAsCRA +"]";
    }

}
