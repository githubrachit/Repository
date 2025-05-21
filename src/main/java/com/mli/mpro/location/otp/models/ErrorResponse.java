package com.mli.mpro.location.otp.models;

import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;

public class ErrorResponse {

    private Date timestamp;

    private int errorCode;

    private String errorMessage;

    public static class Builder {
        private String errorMessage;
        private int errorCode;

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder errorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.errorMessage = this.errorMessage;
            errorResponse.errorCode = this.errorCode;
            return errorResponse;
        }
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

    public String getErrorMessage() {
	return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ErrorResponse [timestamp=" + timestamp + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
    }

    public ErrorResponse(Date timestamp, int errorCode, List<String> errorMessages) {
	super();
	this.timestamp = timestamp;
	this.errorCode = errorCode;
	this.errorMessage = errorMessage;
    }

    public ErrorResponse() {
    }
}
