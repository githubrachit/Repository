package com.mli.mpro.location.clientAllPolicyDetails.model;

import com.mli.mpro.utils.Utility;

public class PolicyDetailsData {

	private String clientId;
	private String transactionId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PolicyDetailsData [clientId=" + clientId + ", transactionId=" + transactionId + "]";
	}
}
