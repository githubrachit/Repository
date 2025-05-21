package com.mli.mpro.location.viewPolicy.models;

import com.mli.mpro.utils.Utility;

public class RequestData {
	
	private String policyNumber;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RequestData [policyNumber=" + policyNumber + "]";
	}
}
