package com.mli.mpro.location.viewPolicy.models;

import com.mli.mpro.utils.Utility;

public class ViewPolicyInputRequest {
	
	private ViewPolicyRequest request;

	public ViewPolicyRequest getRequest() {
		return request;
	}

	public void setRequest(ViewPolicyRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ViewPolicyInputRequest []";
	}
}
