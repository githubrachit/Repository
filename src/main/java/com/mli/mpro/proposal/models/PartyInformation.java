
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "partyType", "basicDetails", "personalIdentification" })
public class PartyInformation {

    @JsonProperty("partyType")
    private String partyType;
    @JsonProperty("basicDetails")
    private BasicDetails basicDetails;
    @JsonProperty("personalIdentification")
    private PersonalIdentification personalIdentification;
    @JsonProperty("partyDetails")
    private PartyDetails partyDetails;
    @JsonProperty("bankDetails")
    private BankDetails bankDetails;
    @JsonProperty("secondaryBankAccountDetails")
    private BankDetails secondaryBankAccountDetails;
    @JsonProperty("BSE500Company")
    private String BSE500Company;
    
    public PartyInformation() {
    }


    public PartyInformation(String partyType, BasicDetails basicDetails, PersonalIdentification personalIdentification, PartyDetails partyDetails,
	    BankDetails bankDetails, String bSE500Company) {
	super();
	this.partyType = partyType;
	this.basicDetails = basicDetails;
	this.personalIdentification = personalIdentification;
	this.partyDetails = partyDetails;
	this.bankDetails = bankDetails;
	BSE500Company = bSE500Company;
    }

    public BasicDetails getBasicDetails() {
	return basicDetails;
    }

    public void setBasicDetails(BasicDetails basicDetails) {
	this.basicDetails = basicDetails;
    }

    public PersonalIdentification getPersonalIdentification() {
	return personalIdentification;
    }

    public void setPersonalIdentification(PersonalIdentification personalIdentification) {
	this.personalIdentification = personalIdentification;
    }

    public String getPartyType() {
	return partyType;
    }

    public void setPartyType(String partyType) {
	this.partyType = partyType;
    }

    public PartyDetails getPartyDetails() {
	return partyDetails;
    }

    public void setPartyDetails(PartyDetails partyDetails) {
	this.partyDetails = partyDetails;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public String getBSE500Company() {
        return BSE500Company;
    }


    public void setBSE500Company(String bSE500Company) {
        BSE500Company = bSE500Company;
    }

    public BankDetails getSecondaryBankAccountDetails() {
        return secondaryBankAccountDetails;
    }

    public void setSecondaryBankAccountDetails(BankDetails secondaryBankAccountDetails) {
        this.secondaryBankAccountDetails = secondaryBankAccountDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PartyInformation [partyType=" + partyType + ", basicDetails=" + basicDetails + ", personalIdentification=" + personalIdentification
		+ ", partyDetails=" + partyDetails + ", bankDetails=" + bankDetails + ", BSE500Company=" + BSE500Company + "]";
    }

}