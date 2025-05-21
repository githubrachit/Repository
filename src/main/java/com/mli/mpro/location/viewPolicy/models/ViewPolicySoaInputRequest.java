package com.mli.mpro.location.viewPolicy.models;

import com.mli.mpro.utils.Utility;

public class ViewPolicySoaInputRequest {
	
	private SoaRequest request;

	public SoaRequest getRequest() {
		return request;
	}

	public void setRequest(SoaRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ViewPolicySoaInputRequest [request=" + request + "]";
	}

}
