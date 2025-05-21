package com.mli.mpro.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.mli.mpro.common.models.Response;

public class UserHandledException extends Exception {

    private static final long serialVersionUID = 1L;
    private List<String> errorMessages;
    private HttpStatus httpstatus;
    private transient Response response;
    private boolean statusCodeOK = false;

    /**
     * @return the errorMessages
     */
    public List<String> getErrorMessages() {
	return errorMessages;
    }

    /**
     * @param errorMessages the errorMessages to set
     */
    public void setErrorMessages(List<String> errorMessages) {
	this.errorMessages = errorMessages;
    }

    /**
     * @return the httpstatus
     */
    public HttpStatus getHttpstatus() {
	return httpstatus;
    }

    /**
     * @param httpstatus the httpstatus to set
     */
    public void setHttpstatus(HttpStatus httpstatus) {
	this.httpstatus = httpstatus;
    }

    /**
     * @return the response
     */
    public Response getResponse() {
	return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(Response response) {
	this.response = response;
    }

    public boolean isStatusCodeOK() {
        return statusCodeOK;
    }

    public void setStatusCodeOK(boolean statusCodeOK) {
        this.statusCodeOK = statusCodeOK;
    }

    // Parameterized constructor
    public UserHandledException(List<String> errorMessages, HttpStatus httpstatus, boolean statusCodeOK) {
        super();
        this.errorMessages = errorMessages;
        this.httpstatus = httpstatus;
        this.statusCodeOK = statusCodeOK;
    }

    // Parameterized constructor
    public UserHandledException(Response response, List<String> errorMessages, HttpStatus httpstatus) {
	super();
	this.response = response;
	this.errorMessages = errorMessages;
	this.httpstatus = httpstatus;
    }

    public UserHandledException(List<String> errorMessages, HttpStatus httpstatus) {
        super();
        this.errorMessages = errorMessages;
        this.httpstatus = httpstatus;
    }

    public UserHandledException() {
    }

    @Override
    public String toString() {
	return "UserHandledException [errorMessages=" + errorMessages + ", httpstatus=" + httpstatus + ", response=" + response + "]";
    }

}
