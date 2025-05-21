package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AadhaarDetails {
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("isAadhaarValidated")
    private boolean isAadhaarValidated;
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
    @JsonProperty("aadhaarOcrStatus")
    private String aadhaarOcrStatus;
    @JsonProperty("payorAadhaarOcrStatus")
    private String payorAadhaarOcrStatus;
     public AadhaarDetails() {
    }

    public AadhaarDetails(String aadhaarNumber, boolean isAadhaarValidated, boolean isAadhaarExist, boolean aadhaarOCRFlag, String aadhaarValidationType,
	    boolean prePopulationByAadhaarOtp, boolean prePopulationByAadhaarOcr, DontHaveAadhaar reasonForDontHaveAadhaar,String aadhaarOcrStatus,
	    String payorAadhaarOcrStatus) {
	super();
	this.aadhaarNumber = aadhaarNumber;
	this.isAadhaarValidated = isAadhaarValidated;
	this.isAadhaarExist = isAadhaarExist;
	this.aadhaarOCRFlag = aadhaarOCRFlag;
	this.aadhaarValidationType = aadhaarValidationType;
	this.prePopulationByAadhaarOtp = prePopulationByAadhaarOtp;
	this.prePopulationByAadhaarOcr = prePopulationByAadhaarOcr;
	this.reasonForDontHaveAadhaar = reasonForDontHaveAadhaar;
	this.aadhaarOcrStatus = aadhaarOcrStatus;
	this.payorAadhaarOcrStatus = payorAadhaarOcrStatus;
    }

    public String getAadhaarNumber() {
	return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
	this.aadhaarNumber = aadhaarNumber;
    }

    public boolean isAadhaarValidated() {
	return isAadhaarValidated;
    }

    public void setAadhaarValidated(boolean isAadhaarValidated) {
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
    
    public String getAadhaarOcrStatus() {
		return aadhaarOcrStatus;
	}

	public void setAadhaarOcrStatus(String aadhaarOcrStatus) {
		this.aadhaarOcrStatus = aadhaarOcrStatus;
	}
	

	public String getPayorAadhaarOcrStatus() {
		return payorAadhaarOcrStatus;
	}

	public void setPayorAadhaarOcrStatus(String payorAadhaarOcrStatus) {
		this.payorAadhaarOcrStatus = payorAadhaarOcrStatus;
	}

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AadhaarDetails{" +
                "aadhaarNumber='" + aadhaarNumber + '\'' +
                ", isAadhaarValidated=" + isAadhaarValidated +
                ", isAadhaarExist=" + isAadhaarExist +
                ", aadhaarOCRFlag=" + aadhaarOCRFlag +
                ", aadhaarValidationType='" + aadhaarValidationType + '\'' +
                ", prePopulationByAadhaarOtp=" + prePopulationByAadhaarOtp +
                ", prePopulationByAadhaarOcr=" + prePopulationByAadhaarOcr +
                ", reasonForDontHaveAadhaar=" + reasonForDontHaveAadhaar +
                ", aadhaarOcrStatus='" + aadhaarOcrStatus + '\'' +
                ", payorAadhaarOcrStatus='" + payorAadhaarOcrStatus + '\'' +
                '}';
    }
}
