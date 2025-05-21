package com.mli.mpro.yblAccount.models;

import com.mli.mpro.utils.Utility;

public class RequestPayload {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "RequestPayload [type=" + type + "]";
	}

}
