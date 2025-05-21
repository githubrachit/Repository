package com.mli.mpro.common.models;

import com.mli.mpro.auditservice.models.ErrorResponse;
import com.mli.mpro.utils.Utility;

import java.util.HashMap;

public class UIOutputResponse {
	private HashMap response;
	private ErrorResponse errorResponse;

	public UIOutputResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public UIOutputResponse() {

	}

	public UIOutputResponse(HashMap responses) {
		super();
		this.response = responses;
	}

	public HashMap getResponse() {
		return response;
	}

	public void setResponse(HashMap responses) {
		this.response = responses;
	}

	@Override
	public String toString() {
		if(Utility.isCalledFromLogs(Thread.currentThread())){
			return Utility.toString(this);
		}
		return "OutputResponse [response=" + response + "errorResponse=" + errorResponse +  "]";
	}
}