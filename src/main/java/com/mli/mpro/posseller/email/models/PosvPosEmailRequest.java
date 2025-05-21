package com.mli.mpro.posseller.email.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PosvPosEmailRequest {
	
	@JsonProperty("transactionId")
	private long transactionId;
	@Sensitive(MaskType.POLICY_NUM)
	@JsonProperty("policyNumber")
	private String policyNumber;
	@Sensitive(MaskType.NAME)
	@JsonProperty("agentName")
	private String agentName;
	@Sensitive(MaskType.EMAIL)
	@JsonProperty("agentEmail")
	private String agentEmail;
	@Sensitive(MaskType.NAME)
	@JsonProperty("policyHolderName")
	private String policyHolderName;
	@Sensitive(MaskType.NAME)
	@JsonProperty("policyHolderEmail")
	private String policyHolderEmail;
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentEmail() {
		return agentEmail;
	}
	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}
	public String getPolicyHolderName() {
		return policyHolderName;
	}
	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}
	public String getPolicyHolderEmail() {
		return policyHolderEmail;
	}
	public void setPolicyHolderEmail(String policyHolderEmail) {
		this.policyHolderEmail = policyHolderEmail;
	}
	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PosvPosEmailRequest [transactionId=" + transactionId + ", policyNumber=" + policyNumber + ", agentName="
				+ agentName + ", agentEmail=" + agentEmail + ", policyHolderName=" + policyHolderName
				+ ", policyHolderEmail=" + policyHolderEmail + "]";
	}

}
