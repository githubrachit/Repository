
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "aadhaarNumber", "isAadhaarValidated", "isAadhaarExist", "aadhaarOCRFlag", "aadhaarValidationType", "prePopulationByAadhaarOtp",
	"reasonForDontHaveAadhaar" })
public class Aadhaar {
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("isAadhaarValidated")
    private String isAadhaarValidated;
    @JsonProperty("isAadhaarExist")
    private boolean isAadhaarExist;
    @JsonProperty("aadhaarOCRFlag")
    private boolean aadhaarOCRFlag;
    @JsonProperty("aadhaarValidationType")
    private String aadhaarValidationType;
    @JsonProperty("prePopulationByAadhaarOtp")
    private boolean prePopulationByAadhaarOtp;
    @JsonProperty("prePopulationByAadhaarOcr")
    private boolean prePopulationByAadhaarOcr;
    @JsonProperty("reasonForDontHaveAadhaar")
    private DontHaveAadhaar reasonForDontHaveAadhaar;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Aadhaar() {
    }

    public Aadhaar(String aadhaarNumber, String isAadhaarValidated, boolean isAadhaarExist, boolean aadhaarOCRFlag, String aadhaarValidationType,
	    boolean prePopulationByAadhaarOtp, boolean prePopulationByAadhaarOcr, DontHaveAadhaar reasonForDontHaveAadhaar) {
	super();
	this.aadhaarNumber = aadhaarNumber;
	this.isAadhaarValidated = isAadhaarValidated;
	this.isAadhaarExist = isAadhaarExist;
	this.aadhaarOCRFlag = aadhaarOCRFlag;
	this.aadhaarValidationType = aadhaarValidationType;
	this.prePopulationByAadhaarOtp = prePopulationByAadhaarOtp;
	this.prePopulationByAadhaarOcr = prePopulationByAadhaarOcr;
	this.reasonForDontHaveAadhaar = reasonForDontHaveAadhaar;
    }

    public String getAadhaarNumber() {
	return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
	this.aadhaarNumber = aadhaarNumber;
    }

    public String getIsAadhaarValidated() {
	return isAadhaarValidated;
    }

    public void setIsAadhaarValidated(String isAadhaarValidated) {
	this.isAadhaarValidated = isAadhaarValidated;
    }

    public boolean isAadhaarExist() {
	return isAadhaarExist;
    }

    public void setAadhaarExist(boolean isAadhaarExist) {
	this.isAadhaarExist = isAadhaarExist;
    }

    public boolean isAadhaarOCRFlag() {
	return aadhaarOCRFlag;
    }

    public void setAadhaarOCRFlag(boolean aadhaarOCRFlag) {
	this.aadhaarOCRFlag = aadhaarOCRFlag;
    }

    public String getAadhaarValidationType() {
	return aadhaarValidationType;
    }

    public void setAadhaarValidationType(String aadhaarValidationType) {
	this.aadhaarValidationType = aadhaarValidationType;
    }

    public boolean isPrePopulationByAadhaarOtp() {
	return prePopulationByAadhaarOtp;
    }

    public void setPrePopulationByAadhaarOtp(boolean prePopulationByAadhaarOtp) {
	this.prePopulationByAadhaarOtp = prePopulationByAadhaarOtp;
    }

    public boolean isPrePopulationByAadhaarOcr() {
	return prePopulationByAadhaarOcr;
    }

    public void setPrePopulationByAadhaarOcr(boolean prePopulationByAadhaarOcr) {
	this.prePopulationByAadhaarOcr = prePopulationByAadhaarOcr;
    }

    public DontHaveAadhaar getReasonForDontHaveAadhaar() {
	return reasonForDontHaveAadhaar;
    }

    public void setReasonForDontHaveAadhaar(DontHaveAadhaar reasonForDontHaveAadhaar) {
	this.reasonForDontHaveAadhaar = reasonForDontHaveAadhaar;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Aadhaar [aadhaarNumber=" + aadhaarNumber + ", isAadhaarValidated=" + isAadhaarValidated + ", isAadhaarExist=" + isAadhaarExist
		+ ", aadhaarOCRFlag=" + aadhaarOCRFlag + ", aadhaarValidationType=" + aadhaarValidationType + ", prePopulationByAadhaarOtp="
		+ prePopulationByAadhaarOtp + ", prePopulationByAadhaarOcr=" + prePopulationByAadhaarOcr + ", reasonForDontHaveAadhaar="
		+ reasonForDontHaveAadhaar + "]";
    }

}