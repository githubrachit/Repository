
package com.mli.mpro.proposal.models;

import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "partiesInformation", "wishToHoldEIAAccount", "existingEIAOrNewEIA", "existingEIANumber", "existingEIANumberRepositoryName",
	"preferredInsuranceRepositoryName", "pepDetails" })
public class EmploymentDetails {

    @JsonProperty("partiesInformation")
    private List<PartyInformation> partiesInformation = null;
    @JsonProperty("isEIAExist")
    private boolean isEIAExist;
    @JsonProperty("existingEIANumber")
    private String existingEIANumber;
    @JsonProperty("existingEIANumberRepositoryName")
    private String existingEIANumberRepositoryName;
    @JsonProperty("isEIANumberExist")
    private boolean isEIANumberExist;
    @JsonProperty("newEIANumber")
    private String newEIANumber;
    @JsonProperty("preferredInsuranceRepositoryName")
    private String preferredInsuranceRepositoryName;
    @JsonProperty("isPoliticallyExposed")
    private boolean isPoliticallyExposed;
    @JsonProperty("pepDetails")
    private PEPDetails pepDetails;
    @JsonProperty("wishToHoldEIAAccount")
    private String wishToHoldEIAAccount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EmploymentDetails() {
    }

    public EmploymentDetails(List<PartyInformation> partiesInformation, boolean isEIAExist, String existingEIANumber, String existingEIANumberRepositoryName,
	    boolean isEIANumberExist, String newEIANumber, String preferredInsuranceRepositoryName, boolean isPoliticallyExposed, PEPDetails pepDetails) {
	super();
	this.partiesInformation = partiesInformation;
	this.isEIAExist = isEIAExist;
	this.existingEIANumber = existingEIANumber;
	this.existingEIANumberRepositoryName = existingEIANumberRepositoryName;
	this.isEIANumberExist = isEIANumberExist;
	this.newEIANumber = newEIANumber;
	this.preferredInsuranceRepositoryName = preferredInsuranceRepositoryName;
	this.isPoliticallyExposed = isPoliticallyExposed;
	this.pepDetails = pepDetails;
    }

    public EmploymentDetails(EmploymentDetails employmentDetails) {
	// TODO Auto-generated constructor stub
    }

    public List<PartyInformation> getPartiesInformation() {
	return partiesInformation;
    }

    public void setPartiesInformation(List<PartyInformation> partiesInformation) {
	this.partiesInformation = partiesInformation;
    }

    public boolean isEIAExist() {
	return isEIAExist;
    }

    public void setEIAExist(boolean isEIAExist) {
	this.isEIAExist = isEIAExist;
    }

    public String getExistingEIANumber() {
	return existingEIANumber;
    }

    public void setExistingEIANumber(String existingEIANumber) {
	this.existingEIANumber = existingEIANumber;
    }

    public String getExistingEIANumberRepositoryName() {
	return existingEIANumberRepositoryName;
    }

    public void setExistingEIANumberRepositoryName(String existingEIANumberRepositoryName) {
	this.existingEIANumberRepositoryName = existingEIANumberRepositoryName;
    }

    public String getNewEIANumber() {
	return newEIANumber;
    }

    public void setNewEIANumber(String newEIANumber) {
	this.newEIANumber = newEIANumber;
    }

    public String getPreferredInsuranceRepositoryName() {
	return preferredInsuranceRepositoryName;
    }

    public void setPreferredInsuranceRepositoryName(String preferredInsuranceRepositoryName) {
	this.preferredInsuranceRepositoryName = preferredInsuranceRepositoryName;
    }

    public boolean isPoliticallyExposed() {
	return isPoliticallyExposed;
    }

    public void setPoliticallyExposed(boolean isPoliticallyExposed) {
	this.isPoliticallyExposed = isPoliticallyExposed;
    }

    public PEPDetails getPepDetails() {
	return pepDetails;
    }

    public void setPepDetails(PEPDetails pepDetails) {
	this.pepDetails = pepDetails;
    }

    public boolean isEIANumberExist() {
	return isEIANumberExist;
    }

    public void setEIANumberExist(boolean isEIANumberExist) {
	this.isEIANumberExist = isEIANumberExist;
    }

    public String getWishToHoldEIAAccount() {
        return wishToHoldEIAAccount;
    }

    public EmploymentDetails setWishToHoldEIAAccount(String wishToHoldEIAAccount) {
        this.wishToHoldEIAAccount = wishToHoldEIAAccount;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", EmploymentDetails.class.getSimpleName() + "[", "]")
                .add("partiesInformation=" + partiesInformation)
                .add("isEIAExist=" + isEIAExist)
                .add("existingEIANumber='" + existingEIANumber + "'")
                .add("existingEIANumberRepositoryName='" + existingEIANumberRepositoryName + "'")
                .add("isEIANumberExist=" + isEIANumberExist)
                .add("newEIANumber='" + newEIANumber + "'")
                .add("preferredInsuranceRepositoryName='" + preferredInsuranceRepositoryName + "'")
                .add("isPoliticallyExposed=" + isPoliticallyExposed)
                .add("pepDetails=" + pepDetails)
                .add("wishToHoldEIAAccount='" + wishToHoldEIAAccount + "'")
                .toString();
    }
}