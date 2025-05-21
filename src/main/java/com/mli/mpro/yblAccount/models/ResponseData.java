package com.mli.mpro.yblAccount.models;

import com.mli.mpro.utils.Utility;

public class ResponseData {

	private String message;
	private int statusCode;

	private ResponsePayload payload;

	public ResponseData() {

	}

	public ResponseData(String message, int statusCode, ResponsePayload payload) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public ResponsePayload getPayload() {
		return payload;
	}

	public void setPayload(ResponsePayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "ResponseData [message=" + message + ", statusCode=" + statusCode + ", payload=" + payload + "]";
	}

}
