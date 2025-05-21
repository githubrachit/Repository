package com.mli.mpro.location.models.clientPolicyDetailsRequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.neo.models.Header;
import com.mli.mpro.utils.Utility;

public class Request {

	@JsonProperty("header")
	private Header header;
	@JsonProperty("payload")
	private Payload payload;

	/**
	 * @return the header
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
	}

	/**
	 * @return the payload
	 */
	public Payload getPayload() {
		return payload;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "Request [header=" + header + ", payload=" + payload + "]";
	}

}
