package com.mli.mpro.sms.responsemodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "responseHeader", "responseBody" })
public class MliSmsServiceResponse {

	@JsonProperty("responseHeader")
	private ResponseHeader responseHeader;
	@JsonProperty("responseBody")
	private ResponseBody responseBody;

	@JsonProperty("responseHeader")
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	@JsonProperty("responseHeader")
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	@JsonProperty("responseBody")
	public ResponseBody getResponseBody() {
		return responseBody;
	}

	@JsonProperty("responseBody")
	public void setResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "MliSmsServiceResponse{" +
				"responseHeader=" + responseHeader +
				", responseBody=" + responseBody +
				'}';
	}
}
