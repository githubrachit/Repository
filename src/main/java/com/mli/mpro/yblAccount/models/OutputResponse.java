package com.mli.mpro.yblAccount.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.common.models.Metadata;
import com.mli.mpro.utils.Utility;

public class OutputResponse {

	@JsonProperty("metadata")
	private Metadata metadata;
	
	@JsonProperty("responseData")
	private ResponseData responseData;

	public OutputResponse() {
	}

	public OutputResponse(Metadata metadata, ResponseData responseData) {
		super();
		this.metadata = metadata;
		this.responseData = responseData;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "OutputResponse [metadata=" + metadata + ", responseData=" + responseData + "]";
	}
	
	
}
