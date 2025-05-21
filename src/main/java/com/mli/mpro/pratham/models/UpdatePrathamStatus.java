package com.mli.mpro.pratham.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class UpdatePrathamStatus {
	
	private String prathamStatus;
	
	private long transactionId;

	@Sensitive(MaskType.POLICY_NUM)
	private String policyNumber;

	public String getPrathamStatus() {
		return prathamStatus;
	}

	public void setPrathamStatus(String prathamStatus) {
		this.prathamStatus = prathamStatus;
	}

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

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "UpdatePrathamStatus [prathamStatus=" + prathamStatus + ", transactionId=" + transactionId
				+ ", policyNumber=" + policyNumber + "]";
	}

}
