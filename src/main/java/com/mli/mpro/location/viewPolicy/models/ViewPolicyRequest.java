package com.mli.mpro.location.viewPolicy.models;

import com.mli.mpro.utils.Utility;

public class ViewPolicyRequest {
	
	private RequestData data;

	public RequestData getData() {
		return data;
	}

	public void setData(RequestData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ViewPolicyRequest []";
	}
}
