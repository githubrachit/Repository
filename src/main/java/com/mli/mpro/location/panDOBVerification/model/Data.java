package com.mli.mpro.location.panDOBVerification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class Data {
	@Sensitive(MaskType.PAN_NUM)
    private String panCardNumber;
	@Sensitive(MaskType.DOB)
    private String dob;
	@Sensitive(MaskType.FIRST_NAME)
    private String firstName;
	@Sensitive(MaskType.LAST_NAME)
    private String lastName;
    private String agentId;
    private String transactionId;
    @JsonProperty("ValidationType")
    private String validationType;
    
	public String getPanCardNumber() {
		return panCardNumber;
	}
	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getValidationType() {
		return validationType;
	}
	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Data [panCardNumber=" + panCardNumber + ", dob=" + dob + ", firstName=" + firstName + ", agentId="
				+ agentId + ", transactionId=" + transactionId + ", validationType=" + validationType + "]";
	}
}
