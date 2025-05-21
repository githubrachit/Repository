package com.mli.mpro.auditservice.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class ErrorResponse {

	@JsonProperty("timestamp")
    private Date timestamp;

	@JsonProperty("errorCode")
    private int errorCode;

	@JsonProperty("errorMessages")
    private List<String> errorMessages;
	
	public ErrorResponse() {
		super();
	}

    public ErrorResponse(Date timestamp, int errorCode, List<String> errorMessages) {
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }

    public Date getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
    }

    public int getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
    }

    public List<String> getErrorMessages() {
	return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
	this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ErrorResponse [timestamp=" + timestamp + ", errorCode=" + errorCode + ", errorMessages=" + errorMessages + "]";
    }

}
