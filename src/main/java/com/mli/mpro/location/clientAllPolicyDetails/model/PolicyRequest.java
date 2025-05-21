package com.mli.mpro.location.clientAllPolicyDetails.model;

import com.mli.mpro.utils.Utility;

public class PolicyRequest {
	
	private PolicyDetailsData data;

	public PolicyDetailsData getData() {
		return data;
	}

	public void setData(PolicyDetailsData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		if (Utility.isCalledFromLogs(Thread.currentThread())) {
			return Utility.toString(this);
		}
		return "PolicyRequest [data=" + data + "]";
	}

}
