package com.mli.mpro.yblAccount.models;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.common.models.Metadata;
import com.mli.mpro.utils.Utility;

public class Request {

	@JsonProperty("metadata")
	private Metadata metadata;

	@JsonProperty("requestData")
	@Valid
	private RequestData requestdata;

	public Request() {
	}

	public Request(Metadata metadata, @Valid RequestData requestdata) {
		super();
		this.metadata = metadata;
		this.requestdata = requestdata;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public RequestData getRequestdata() {
		return requestdata;
	}

	public void setRequestdata(RequestData requestdata) {
		this.requestdata = requestdata;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Request [metadata=" + metadata + ", requestdata=" + requestdata + "]";
	}

}
