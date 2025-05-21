package com.mli.mpro.location.models.clientPolicyDetailsResponseModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.neo.models.Header;
import com.mli.mpro.neo.models.MsgInfo;
import com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.Payload;
import com.mli.mpro.utils.Utility;

public class Response {

	private Header header;

	private MsgInfo msgInfo;

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
	 * @return the msgInfo
	 */
	public MsgInfo getMsgInfo() {
		return msgInfo;
	}

	/**
	 * @param msgInfo
	 *            the msgInfo to set
	 */
	public void setMsgInfo(MsgInfo msgInfo) {
		this.msgInfo = msgInfo;
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
		return "Response [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
	}

}
