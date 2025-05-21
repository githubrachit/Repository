package com.mli.mpro.sms.responsemodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "generalResponse" })
public class ResponseHeader {

	@JsonProperty("generalResponse")
	private GeneralResponse generalResponse;

	@JsonProperty("generalResponse")
	public GeneralResponse getGeneralResponse() {
		return generalResponse;
	}

	@JsonProperty("generalResponse")
	public void setGeneralResponse(GeneralResponse generalResponse) {
		this.generalResponse = generalResponse;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ResponseHeader [generalResponse=" + generalResponse + "]";
	}

}
