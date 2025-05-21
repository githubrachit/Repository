package com.mli.mpro.sms.responsemodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

public class OutputResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "MliSmsServiceResponse" })

	@JsonProperty("MliSmsServiceResponse")
	private MliSmsServiceResponse mliSmsServiceResponse;

	@JsonProperty("MliSmsServiceResponse")
	public MliSmsServiceResponse getMliSmsServiceResponse() {
		return mliSmsServiceResponse;
	}

	@JsonProperty("MliSmsServiceResponse")
	public void setMliSmsServiceResponse(MliSmsServiceResponse mliSmsServiceResponse) {
		this.mliSmsServiceResponse = mliSmsServiceResponse;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "OutputResponse [mliSmsServiceResponse=" + mliSmsServiceResponse + "]";
	}

}
