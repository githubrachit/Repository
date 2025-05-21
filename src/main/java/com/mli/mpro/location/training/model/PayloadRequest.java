package com.mli.mpro.location.training.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.location.common.soa.model.Header;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayloadRequest {

	private Header header;

	private RequestData requestData;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public RequestData getRequestData() {
		return requestData;
	}

	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}

	@Override
	public String toString() {
		return "PayloadRequest [header=" + header + ", requestData=" + requestData + "]";
	}
}
