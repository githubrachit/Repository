package com.mli.mpro.tpp.backflow.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

/**
 * @author ravishankar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message" })
public class ResponsePayload {

    @JsonProperty("message")
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponsePayload() {
    }

    /**
     * 
     * @param message
     */
    public ResponsePayload(String message) {
	super();
	this.message = message;
    }

    @JsonProperty("message")
    public String getMessage() {
	return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
	this.message = message;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ResponsePayload [message=" + message + "]";
    }

}