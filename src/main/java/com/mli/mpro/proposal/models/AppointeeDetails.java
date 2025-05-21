
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "guardianNameOfNominee", "guardianIsYour", "relationwithNominee", "relationwithNomineeOthers" })
public class AppointeeDetails {

    @Sensitive(MaskType.NAME)
    @JsonProperty("guardianNameOfNominee")
    private String guardianNameOfNominee;
    @JsonProperty("guardianIsYour")
    private String guardianIsYour;
    @JsonProperty("relationwithNominee")
    private String relationwithNominee;
    @JsonProperty("relationwithNomineeOthers")
    private String relationwithNomineeOthers;
    @Sensitive(MaskType.NAME)
    @JsonProperty("guardianName")
    private String guardianName;
    @JsonProperty("guardianRelation")
    private String guardianRelation;
    @JsonProperty("guardianSpecifyRelationship")
    private String guardianSpecifyRelationship;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("appointeeGender")
    private String appointeeGender;
    @Sensitive(MaskType.DOB)
    private java.util.Date appointeeDOB;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("appointeeEmail")
    private String appointeeEmail;
    @JsonProperty("appointeePhoneDetails")
    private List<Phone> appointeePhoneDetails;
    @JsonProperty("appointeeAddress")
    private List<Address> appointeeAddress;
    @JsonProperty("appointeeBankDetails")
    private Bank appointeeBankDetails;
    @JsonProperty("appointeeNationality")
    private AppointeeNationality appointeeNationality;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AppointeeDetails() {
    }

    /**
     * @param guardianNameOfNominee
     * @param relationwithNomineeOthers
     * @param relationwithNominee
     * @param guardianIsYour
     */
    public AppointeeDetails(String guardianNameOfNominee, String guardianIsYour, String relationwithNominee, String relationwithNomineeOthers) {
	super();
	this.guardianNameOfNominee = guardianNameOfNominee;
	this.guardianIsYour = guardianIsYour;
	this.relationwithNominee = relationwithNominee;
	this.relationwithNomineeOthers = relationwithNomineeOthers;
    }

    public String getGuardianNameOfNominee() {
	return guardianNameOfNominee;
    }

    public void setGuardianNameOfNominee(String guardianNameOfNominee) {
	this.guardianNameOfNominee = guardianNameOfNominee;
    }

    public String getGuardianIsYour() {
	return guardianIsYour;
    }

    public void setGuardianIsYour(String guardianIsYour) {
	this.guardianIsYour = guardianIsYour;
    }

    public String getRelationwithNominee() {
	return relationwithNominee;
    }

    public void setRelationwithNominee(String relationwithNominee) {
	this.relationwithNominee = relationwithNominee;
    }

    public String getRelationwithNomineeOthers() {
	return relationwithNomineeOthers;
    }

    public void setRelationwithNomineeOthers(String relationwithNomineeOthers) {
	this.relationwithNomineeOthers = relationwithNomineeOthers;
    }

  public String getGuardianName() {
    return guardianName;
  }

  public void setGuardianName(String guardianName) {
    this.guardianName = guardianName;
  }

  public String getGuardianRelation() {
    return guardianRelation;
  }

  public void setGuardianRelation(String guardianRelation) {
    this.guardianRelation = guardianRelation;
  }

    public String getGuardianSpecifyRelationship() {
    return guardianSpecifyRelationship;
  }

  public void setGuardianSpecifyRelationship(String guardianSpecifyRelationship) {
    this.guardianSpecifyRelationship = guardianSpecifyRelationship;
  }

    public String getAppointeeGender() {
        return appointeeGender;
    }

    public void setAppointeeGender(String appointeeGender) {
        this.appointeeGender = appointeeGender;
    }

    public Date getAppointeeDOB() {
        return appointeeDOB;
    }

    public void setAppointeeDOB(Date appointeeDOB) {
        this.appointeeDOB = appointeeDOB;
    }

    public String getAppointeeEmail() {
        return appointeeEmail;
    }

    public void setAppointeeEmail(String appointeeEmail) {
        this.appointeeEmail = appointeeEmail;
    }

    public List<Phone> getAppointeePhoneDetails() {
        return appointeePhoneDetails;
    }

    public void setAppointeePhoneDetails(List<Phone> appointeePhoneDetails) {
        this.appointeePhoneDetails = appointeePhoneDetails;
    }

    public List<Address> getAppointeeAddress() {
        return appointeeAddress;
    }

    public void setAppointeeAddress(List<Address> appointeeAddress) {
        this.appointeeAddress = appointeeAddress;
    }

    public Bank getAppointeeBankDetails() {
        return appointeeBankDetails;
    }

    public void setAppointeeBankDetails(Bank appointeeBankDetails) {
        this.appointeeBankDetails = appointeeBankDetails;
    }

    public AppointeeNationality getAppointeeNationality() {
        return appointeeNationality;
    }

    public void setAppointeeNationality(AppointeeNationality appointeeNationality) {
        this.appointeeNationality = appointeeNationality;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AppointeeDetails[" +
                "guardianNameOfNominee='" + guardianNameOfNominee + '\'' +
                ", guardianIsYour='" + guardianIsYour + '\'' +
                ", relationwithNominee='" + relationwithNominee + '\'' +
                ", relationwithNomineeOthers='" + relationwithNomineeOthers + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", guardianRelation='" + guardianRelation + '\'' +
                ", guardianSpecifyRelationship='" + guardianSpecifyRelationship + '\'' +
                ", appointeeGender='" + appointeeGender + '\'' +
                "appointeeBankDetails=" + appointeeBankDetails +
                ", appointeeEmail='" + appointeeEmail + '\'' +
                ", appointeePhoneDetails=" + appointeePhoneDetails +
                ", nomineeAddress=" + appointeeAddress +
                ']';
    }
}
