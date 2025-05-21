package com.mli.mpro.sms.requestmodels;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestHeader", "requestBody" })
public class MliSmsService {

	@JsonProperty("requestHeader")
	private RequestHeader requestHeader;
	@JsonProperty("requestBody")
	private RequestBody requestBody;

	@JsonProperty("requestHeader")
	public RequestHeader getRequestHeader() {
		return requestHeader;
	}

	@JsonProperty("requestHeader")
	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	@JsonProperty("requestBody")
	public RequestBody getRequestBody() {
		return requestBody;
	}

	@JsonProperty("requestBody")
	public void setRequestBody(RequestBody requestBody) {
		this.requestBody = requestBody;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "MliSmsService{" +
				"requestHeader=" + requestHeader +
				", requestBody=" + requestBody +
				'}';
	}
}
