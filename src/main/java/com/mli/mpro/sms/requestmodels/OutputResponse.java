package com.mli.mpro.sms.requestmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class OutputResponse {

	@JsonProperty("MliSmsService")
	private MliSmsService mliSmsService;

	@JsonProperty("MliSmsService")
	public MliSmsService getMliSmsService() {
		return mliSmsService;
	}

	@JsonProperty("MliSmsService")
	public void setMliSmsService(MliSmsService mliSmsService) {
		this.mliSmsService = mliSmsService;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "OutputResponse [mliSmsService=" + mliSmsService + "]";
	}

}
