package com.mli.mpro.location.models.soaCloudModels.master360RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.location.models.soaCloudModels.master360ResponseModels.Header;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

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
	 * @param header the header to set
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
	 * @param payload the payload to set
	 */
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "Request [header=" + header + ", payload=" + payload + "]";
	}
	
	

}
