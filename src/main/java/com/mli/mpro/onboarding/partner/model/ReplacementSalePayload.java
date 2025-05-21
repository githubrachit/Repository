package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplacementSalePayload {

	@JsonProperty("proposerClientId")
	private String proposerClientId;

	@JsonProperty("insuredClientId")
	private String insuredClientId;

    @Sensitive(MaskType.POLICY_NUM)
	@JsonProperty("currentPolicyNumber")
	private String currentPolicyNumber;
	
	@JsonProperty("currentPolicySignDate")
	private String currentPolicySignDate;
	
	@JsonProperty("uinNo")
	private String uinNo;

	@JsonProperty("transactionId")
	private String transactionId;

	public String getProposerClientId() {
		return proposerClientId;
	}

	public void setProposerClientId(String proposerClientId) {
		this.proposerClientId = proposerClientId;
	}

	public String getInsuredClientId() {
		return insuredClientId;
	}

	public void setInsuredClientId(String insuredClientId) {
		this.insuredClientId = insuredClientId;
	}

	public String getCurrentPolicyNumber() {
		return currentPolicyNumber;
	}

	public void setCurrentPolicyNumber(String currentPolicyNumber) {
		this.currentPolicyNumber = currentPolicyNumber;
	}

	public String getCurrentPolicySignDate() {
		return currentPolicySignDate;
	}

	public void setCurrentPolicySignDate(String currentPolicySignDate) {
		this.currentPolicySignDate = currentPolicySignDate;
	}

	public String getUinNo() {
		return uinNo;
	}

	public void setUinNo(String uinNo) {
		this.uinNo = uinNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "ReplacementSalePayload{" +
				"proposerClientId='" + proposerClientId + '\'' +
				", insuredClientId='" + insuredClientId + '\'' +
				", currentPolicyNumber='" + currentPolicyNumber + '\'' +
				", currentPolicySignDate='" + currentPolicySignDate + '\'' +
				", uinNo='" + uinNo + '\'' +
				", transactionId='" + transactionId + '\'' +
				'}';
	}


}
