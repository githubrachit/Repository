package com.mli.mpro.yblAccount.models;

import com.mli.mpro.utils.Utility;

public class ResponsePayload {
	
	private yblAccountDetails yblAccountDetails;
	
	public ResponsePayload() {
	}

	public yblAccountDetails getYblAccountDetails() {
		return yblAccountDetails;
	}

	public void setYblAccountDetails(yblAccountDetails yblAccountDetails) {
		this.yblAccountDetails = yblAccountDetails;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ResponsePayload [yblAccountDetails=" + yblAccountDetails + "]";
	}
}
