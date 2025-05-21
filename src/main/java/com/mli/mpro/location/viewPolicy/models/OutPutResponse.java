package com.mli.mpro.location.viewPolicy.models;

import com.mli.mpro.onboarding.model.SOAResponse;
import com.mli.mpro.utils.Utility;

public class OutPutResponse {
	
	private SOAResponse result;

	public SOAResponse getResult() {
		return result;
	}

	public void setResult(SOAResponse result) {
		this.result = result;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "OutPutResponse [result=" + result + "]";
	}
	
}
