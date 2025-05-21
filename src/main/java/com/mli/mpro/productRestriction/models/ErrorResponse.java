package com.mli.mpro.productRestriction.models;

/**
 * @author arpita
 *
 */

import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;

public class ErrorResponse {

    private Date timestamp;

    private int errorCode;

    private List<String> errorMessages;

    private List<Object> errors;

    public ErrorResponse() {

    }
    public ErrorResponse(int errorCode, List<Object> errors) {
        super();
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public ErrorResponse(Date timestamp, int errorCode, List<String> errorMessages) {
	super();
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

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ErrorResponse [timestamp=" + timestamp + ", errorCode=" + errorCode + ", errorMessages=" + errorMessages + "]";
    }

}
