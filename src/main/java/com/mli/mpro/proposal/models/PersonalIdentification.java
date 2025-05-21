
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "phone", "aadhaarDetails", "enrollment", "pan", "email" })
public class PersonalIdentification {

    @JsonProperty("phone")
    private List<Phone> phone = null;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarDetails")
    private AadhaarDetails aadhaarDetails;
    @JsonProperty("enrollment")
    private Enrollment enrollment;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panDetails")
    private PanDetails panDetails;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("email")
    private String email;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("PRANNumber")
    @JsonAlias({"PRANNumber", "pranNumber"})
    private String pranNumber;
    //FUL2-181304 ABHA NO
    @JsonProperty("abhaId")
    private String abhaId;
    @JsonProperty("insurerAbhaId")
    private String insurerAbhaId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PersonalIdentification() {
    }

    /**
     * @param email
     * @param phone
     * @param enrollment
     * @param aadhaarDetails
     * @param pan
     */

    public PersonalIdentification(List<Phone> phone, AadhaarDetails aadhaarDetails, Enrollment enrollment, PanDetails panDetails, String email,String abhaId,String insurerAbhaId) {
	super();
	this.phone = phone;
	this.aadhaarDetails = aadhaarDetails;
	this.enrollment = enrollment;
	this.panDetails = panDetails;
	this.email = email;
    this.abhaId = abhaId;
    this.insurerAbhaId = insurerAbhaId;
    }

    public List<Phone> getPhone() {
	return phone;
    }

    public void setPhone(List<Phone> phone) {
	this.phone = phone;
    }

    public Enrollment getEnrollment() {
	return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
	this.enrollment = enrollment;
    }

    public PanDetails getPanDetails() {
        return panDetails;
    }

    public void setPanDetails(PanDetails panDetails) {
        this.panDetails = panDetails;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public AadhaarDetails getAadhaarDetails() {
        return aadhaarDetails;
    }

    public void setAadhaarDetails(AadhaarDetails aadhaarDetails) {
        this.aadhaarDetails = aadhaarDetails;
    }

    public String getPranNumber() {
		return pranNumber;
	}

	public void setPranNumber(String pranNumber) {
		this.pranNumber = pranNumber;
	}

    public String getAbhaId() {
        return abhaId;
    }

    public void setAbhaId(String abhaId) {
        this.abhaId = abhaId;
    }

    public String getInsurerAbhaId() {
        return insurerAbhaId;
    }

    public void setInsurerAbhaId(String insurerAbhaId) {
        this.insurerAbhaId = insurerAbhaId;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PersonalIdentification [phone=" + phone + ", aadhaarDetails=" + aadhaarDetails + ", enrollment=" + enrollment + ", pan=" + panDetails
		+ ", email=" + email + ", pranNumber=" + pranNumber+ ", abhaId=" + abhaId+ ", insurerAbhaId=" + insurerAbhaId+ "]";
    }

}