package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.model.Header;
import com.mli.mpro.onboarding.model.MsgInfo;

public class SOAResponse {

	@JsonProperty("header")
	Header header; 
    
	@JsonProperty("msgInfo")
	private MsgInfo msginfo;
	/*
	 * @JsonProperty("payload") private payload<T> payload;
	 */
	
	/*
	 * @JsonProperty("payload") private SOAResponsePayload payload;
	 * 
	 * public SOAResponsePayload getPayload() {
	 * System.out.println("SOAResponse getPayload called"); return payload; }
	 * 
	 * public void setPayload(SOAResponsePayload payload) {
	 * System.out.println("SOAResponse SetPayload called"); this.payload = payload;
	 * }
	 */
    	
	/*
	 * public payload<T> getPayload() { return payload; }
	 * 
	 * public void setPayload(payload<T> payload) { this.payload = payload; }
	 */

	public SOAResponse() {
		// TODO Auto-generated constructor stub
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public MsgInfo getMsginfo() {
		return msginfo;
	}

	public void setMsginfo(MsgInfo msginfo) {
		this.msginfo = msginfo;
	}

}
