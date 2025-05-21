package com.mli.mpro.posseller.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "policyNumber", "agentName", "agentEmail", "mobileNumber", "documentNames" })
public class Payload {

	@Sensitive(MaskType.POLICY_NUM)
	@JsonProperty("policyNumber")
	private String policyNumber;
	@Sensitive(MaskType.NAME)
	@JsonProperty("agentName")
	private String agentName;
	@Sensitive(MaskType.EMAIL)
	@JsonProperty("agentEmail")
	private String agentEmail;
	@Sensitive(MaskType.MOBILE)
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	@JsonProperty("documentNames")
	private List<String> documentNames;

	@JsonProperty("policyNumber")
	public String getPolicyNumber() {
		return policyNumber;
	}

	@JsonProperty("policyNumber")
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	@JsonProperty("agentName")
	public String getAgentName() {
		return agentName;
	}

	@JsonProperty("agentName")
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@JsonProperty("agentEmail")
	public String getAgentEmail() {
		return agentEmail;
	}

	@JsonProperty("agentEmail")
	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}

	@JsonProperty("mobileNumber")
	public String getMobileNumber() {
		return mobileNumber;
	}

	@JsonProperty("mobileNumber")
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@JsonProperty("documentNames")
	public List<String> getDocumentNames() {
		return documentNames;
	}

	@JsonProperty("documentNames")
	public void setDocumentNames(List<String> documentNames) {
		this.documentNames = documentNames;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "Payload [policyNumber=" + policyNumber + ", agentName=" + agentName + ", agentEmail=" + agentEmail
				+ ", mobileNumber=" + mobileNumber + ", documentNames=" + documentNames + "]";
	}

}
