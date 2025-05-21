package com.mli.mpro.location.clientAllPolicyDetails.model;

import com.mli.mpro.utils.Utility;

public class PolicyDetailsRequest {

	private PolicyRequest request;

	public PolicyRequest getRequest() {
		return request;
	}

	public void setRequest(PolicyRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PolicyDetailsRequest []";
	}
}
