package com.mli.mpro.onboarding.partner.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplacementSaleSOARequestPayload extends SOARequestPayload {

	@JsonProperty("requestId")
	private String requestId;
	
	@JsonProperty("transactionId")
	private String transactionId;
	
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
	
	

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

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

	@Override
	public String toString() {
		           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "SplittingorReplacementRequestPayload [proposerClientId=" + proposerClientId + ", insuredClientId="
				+ insuredClientId + ", currentPolicyNumber=" + currentPolicyNumber + ", currentPolicySignDate="
				+ currentPolicySignDate + ", uinNo=" + uinNo + "]";
	}

	
	
	
	

}
